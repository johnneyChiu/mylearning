package personal.xiaoq.realtime.demo.sql;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.Csv;
import org.apache.flink.table.descriptors.FileSystem;
import org.apache.flink.table.descriptors.Schema;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-04-11 12:36
 * @Version: V1.0.0
 */
public class TextStreamSql {
    public static void main(String[] args) throws Exception {
        //获取执行环境
        StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        //重启策略
        bsEnv.setRestartStrategy(new RestartStrategies.FailureRateRestartStrategyConfiguration(3, Time.minutes(3), Time.minutes(3)));

        EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useAnyPlanner().inStreamingMode().build();
        //创建一个table执行环境
        StreamTableEnvironment fsTableEnv = StreamTableEnvironment.create(bsEnv, bsSettings);

        //读取文本源
        DataStream<String> userStream = bsEnv.readTextFile("D:\\personal\\test\\user.txt");
        //数据转换
        SingleOutputStreamOperator<Tuple2<Integer, String>> mapStream = userStream.map(new MapFunction<String, Tuple2<Integer, String>>() {
            @Override
            public Tuple2<Integer, String> map(String value) throws Exception {
                String[] splitStr = value.split(",");
                return Tuple2.of(Integer.valueOf(splitStr[0]), splitStr[1]);
            }
        });
        //流转表
        Table table = fsTableEnv.fromDataStream(mapStream, "uid,name");
        //注册为一个表
        fsTableEnv.createTemporaryView("user1", table);


        /**
         * 输出到文件系统
         */
        /*
        //逻辑处理
        Table table1 = fsTableEnv.sqlQuery("select * from user1");


        final Schema schema = new Schema()
                .field("count", DataTypes.INT())
                .field("word", DataTypes.STRING());

        fsTableEnv.connect(new FileSystem().path("D:\\personal\\test\\ttt.txt"))
                .withFormat(new Csv())
                .withSchema(schema)
                .createTemporaryTable("MySource1");
        table1.insertInto("MySource1");

        DataStream<Tuple2<Integer,String>> tupleDataStream = fsTableEnv
                .toAppendStream(table1,TypeInformation.of(new TypeHint<Tuple2<Integer, String>>(){}));
*/
        //tupleDataStream.print();

        /**
         * 输出到数据库表
         */
        fsTableEnv.sqlUpdate(
                "CREATE TABLE flink_sink_user1 (\n" +
                        "    uid int,\n" +
                        "    name varchar\n" +
                        ") WITH (\n" +
                        "    'connector.type' = 'jdbc',\n" +
                        "    'connector.url' = 'jdbc:mysql://172.16.55.39:3306/xiaoqTest',\n" +
                        "    'connector.table' = 'flink_sink_user1',\n" +
                        "    'connector.username' = 'root',\n" +
                        "    'connector.password' = 'root',\n" +
                        "    'connector.write.flush.max-rows' = '1'\n" +
                        ")"
        );
        String flink_sink_user1 = "INSERT INTO flink_sink_user1 \n" +
                "select uid,name \n" +
                "FROM user1\n";
        fsTableEnv.sqlUpdate(
                flink_sink_user1
        );
        bsEnv.execute("txt Stream job");
    }
}
