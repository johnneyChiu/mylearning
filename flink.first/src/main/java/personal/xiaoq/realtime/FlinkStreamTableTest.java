package personal.xiaoq.realtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.Json;
import org.apache.flink.table.descriptors.Kafka;
import org.apache.flink.table.descriptors.Schema;
import org.apache.flink.table.sinks.CsvTableSink;
import org.apache.flink.table.sinks.TableSink;

import java.time.LocalDateTime;
import java.util.HashMap;

import static personal.xiaoq.realtime.schema.JsonSchema.JSON_SCHEMA;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-10-14 11:17
 * @Version: V1.0.0
 */
public class FlinkStreamTableTest {



    public static int test(String[] args) throws Exception{
        return 1;
    }

    public static void main(String[] args) throws Exception {

        //table operate
        EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(bsEnv, bsSettings);
        //设置无更改状态最小保存时间，最大保存时间
        tableEnv.getConfig().setIdleStateRetentionTime(Time.days(1), Time.days(2));
        ParameterTool parameterTool = ParameterTool.fromMap(new HashMap<String,String>(){{
            put("bootstrap-servers", "172.16.55.23:9092,172.16.55.39:9092,172.16.55.24:9092");
            put("group-id", "for_test_xiaoq1");
            put("jobName", "test_streaming_table_job");
            put("dateEscape", "20191019120000");
            put("zookeeper.connect", "");
        }});


        //stream to table
        //Table dmlTable = tableEnv.fromDataStream(dmlSingleOutputStreamOperator);
        tableEnv
                .connect(
                        new Kafka()
                                .version("0.11")
                                .topic("test-input")
                                .startFromEarliest()
                                .properties(parameterTool.getProperties())
                )
                .withFormat(new Json().jsonSchema(JSON_SCHEMA)
                .failOnMissingField(false))
                .withSchema(new Schema().field("database","VARCHAR")
                        .field("table","VARCHAR")
                        .field("ts","TIMESTAMP")
                        .field("es","TIMESTAMP").proctime()
                        )
                .inUpsertMode()
                .registerTableSource("dml");


        //注册表
       // tableEnv.registerTable("dml",dmlTable);
        Table tableDetail=tableEnv.sqlQuery("select `database`,`table`,ts,es as table_count from dml ");

        //define field Names and types
        //sink to csv
        String[] fieldNames = {"database","table_name","ts","es", "counts"};
        TypeInformation[] filedTypes = {Types.STRING, Types.LONG};
        TableSink csvSink =
                new CsvTableSink("D:\\personal\\github\\mylearning\\flink190.first\\src\\main\\resources\\sink\\xiaoq.csv", "|", 1, FileSystem.WriteMode.OVERWRITE)
                .configure(fieldNames,filedTypes);
        tableEnv.registerTableSink("csvTableSink",csvSink);
        tableDetail.insertInto("csvTableSink");



        //tableEnv.toRetractStream(tableDetail, Types.LONG);
        //

        bsEnv.execute(parameterTool.get("jobName","test_xiaoq"));


    }



}


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
class BaseInfo {
    private int id;
    private String name;
    private String idCard;
    private double score;
    private double capital;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
