package personal.xiaoq.realtime.demo.tableapi;

import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.Json;
import org.apache.flink.table.descriptors.Kafka;
import org.apache.flink.table.descriptors.Schema;
import org.apache.flink.types.Row;

import java.util.HashMap;

import static personal.xiaoq.realtime.schema.JsonSchema.JSON_SCHEMA;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-04-10 10:42
 * @Version: V1.0.0
 */
public class KafkaStreamAPI {

    public static void main(String[] args) {
        StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment fsTableEnv = StreamTableEnvironment.create(bsEnv, bsSettings);


        fsTableEnv.getConfig().setIdleStateRetentionTime(Time.days(1), Time.days(2));

        ParameterTool parameterTool = ParameterTool.fromMap(new HashMap<String,String>(){{
            put("bootstrap.servers", "cdhslave03:9092");
            //put("group-id", "for_test_xiaoq1");
            put("group.id", "test_streaming_table_job");
            //put("dateEscape", "20191019120000");
            put("zookeeper.connect", "cdhmaster01:2181");
        }});
        fsTableEnv
                .connect(
                        new Kafka()
                                .version("universal")
                                .topic("flink_test_area")
                                .startFromEarliest()
                                .properties(parameterTool.getProperties())
                )
                .withFormat(new Json()
                        .failOnMissingField(false))
                .withSchema(new Schema().field("evTime", "BIGINT")
                        .field("interruptMsg", "VARCHAR")

                        .field("requestId", "VARCHAR")
                        .field("transferType", "VARCHAR")
                        .field("mount", "INT"))
                .inAppendMode()
                .createTemporaryTable("test")
        ;

        Table table = fsTableEnv.sqlQuery("select interruptMsg from test");
        DataStream<Row> rowDataStream = fsTableEnv.toAppendStream(table, Row.class);
        rowDataStream.print();

    }
}
