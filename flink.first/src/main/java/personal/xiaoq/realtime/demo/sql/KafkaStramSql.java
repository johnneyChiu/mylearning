package personal.xiaoq.realtime.demo.sql;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.Csv;
import org.apache.flink.table.descriptors.FileSystem;
import org.apache.flink.table.descriptors.Schema;
import org.apache.flink.types.Row;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-04-08 10:37
 * @Version: V1.0.0
 */
public class KafkaStramSql {


    public static void main(String[] args) {
        // EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        /*EnvironmentSettings fsSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build();
        StreamExecutionEnvironment fsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment fsTableEnv = StreamTableEnvironment.create(fsEnv, fsSettings);*/
        StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment fsTableEnv = StreamTableEnvironment.create(bsEnv, bsSettings);

        String orders = "CREATE TABLE orders (\n" +
                "    ev_time BIGINT,\n" +
                "    interrupt_code STRING,\n" +
                "    interrupt_msg STRING,\n" +
                "    request_id STRING,\n" +
                "    transfer_type STRING,\n" +
                "    mount INT\n" +
                ") WITH (\n" +
                "    'connector.type' = 'kafka',\n" +
                "    'connector.version' = 'universal',\n" +
                "    'connector.topic' = 'flink_test_area',\n" +
                "    'connector.startup-mode' = 'earliest-offset',\n" +
                //"    'connector.group-id' = 'test1',\n" +
                "    'connector.properties.0.key' = 'zookeeper.connect',\n" +
                "    'connector.properties.0.value' = 'cdhmaster01:2181',\n" +
                "    'connector.properties.1.key' = 'bootstrap.servers',\n" +
                "    'connector.properties.1.value' = 'cdhslave03:9092',\n" +
                "    'update-mode' = 'append',\n" +
                "    'format.type' = 'json',\n" +
                "    'format.derive-schema' = 'true'\n" +
                ")";
        fsTableEnv.sqlUpdate(orders);
        Table table = fsTableEnv.sqlQuery("select * from orders");

        String areas = "CREATE TABLE area (\n" +
                "    ev_time BIGINT,\n" +
                "    interrupt_code STRING,\n" +
                "    address STRING\n" +
                ") WITH (\n" +
                "    'connector.type' = 'kafka',\n" +
                "    'connector.version' = 'universal',\n" +
                "    'connector.topic' = 'flink_test_orders',\n" +
                "    'connector.startup-mode' = 'earliest-offset',\n" +
                //"    'connector.group-id' = 'test2',\n" +
                "    'connector.properties.0.key' = 'zookeeper.connect',\n" +
                "    'connector.properties.0.value' = 'cdhmaster01:2181',\n" +
                "    'connector.properties.1.key' = 'bootstrap.servers',\n" +
                "    'connector.properties.1.value' = 'cdhslave03:9092',\n" +
                "    'update-mode' = 'append',\n" +
                "    'format.type' = 'json',\n" +
                "    'format.derive-schema' = 'true'\n" +
                ")";

        fsTableEnv.sqlUpdate(areas);
        //Table table1 = fsTableEnv.sqlQuery("SELECT * from " + tableSource + " where interruptCode='B01'");

        Table table1=fsTableEnv.sqlQuery("SELECT a.interrupt_code,a.interrupt_msg, a.request_id FROM orders a ");

        fsTableEnv.toRetractStream(table1, Row.class).addSink(new SinkFunction<Tuple2<Boolean, Row>>() {
            @Override
            public void invoke(Tuple2<Boolean, Row> value,Context context) throws Exception {
                System.out.println(value);
            }
        });
       /* fsTableEnv.sqlUpdate(
                "CREATE TABLE sink (\n" +
                        "    interrupt_code STRING,\n" +
                        "    cnt BIGINT\n" +
                        ") WITH (\n" +
                        "    'connector.type' = 'jdbc',\n" +
                        "    'connector.url' = 'jdbc:mysql://172.16.55.39:3306/test',\n" +
                        "    'connector.table' = 'flink_sink_test',\n" +
                        "    'connector.username' = 'root',\n" +
                        "    'connector.password' = 'root',\n" +
                        "    'connector.write.flush.max-rows' = '1'\n" +
                        ")"
        );

        String sink = "INSERT INTO sink\n" +
                "SELECT\n" +
                "  cast(interrupt_code as STRING),\n" +
                "  COUNT(*) AS cnt\n" +
                "FROM source\n" +
                "GROUP BY cast(interrupt_code as STRING)";
        System.out.println(sink);

        fsTableEnv.sqlUpdate(
                sink
        );*/

     /*   Table table = fsTableEnv.sqlQuery("select cast(user_id as bigint) as user_id,count(*) cnt from source group by user_id");
        fsTableEnv.toRetractStream(table, Row.class).addSink(new SinkFunction<Tuple2<Boolean, Row>>() {
            @Override
            public void invoke(Tuple2<Boolean, Row> value) throws Exception {
                System.out.println(value);
            }
        });*/
        /*final Schema schema = new Schema()
                .field("interrupt_code", DataTypes.STRING())
                .field("interrupt_msg", DataTypes.STRING())
                .field("address", DataTypes.STRING());
        fsTableEnv.connect(new FileSystem().path("D:\\personal\\test"))
                .withSchema(schema)
                .withFormat(new Csv().fieldDelimiter('|').deriveSchema())
                .createTemporaryTable("RubberOrders");
        fsTableEnv.sqlUpdate(
                "INSERT INTO RubberOrders SELECT a.interrupt_code,a.interrupt_msg, b.address FROM orders a left join area  b on a.interrupt_code=b.interrupt_code");
        String explanation = fsTableEnv.explain(false);
        System.out.println(explanation);*/
        try {
            fsTableEnv.execute("StreamTableDemo");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
