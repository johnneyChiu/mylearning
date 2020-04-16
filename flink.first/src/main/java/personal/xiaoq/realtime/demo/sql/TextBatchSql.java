package personal.xiaoq.realtime.demo.sql;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableConfig;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.api.java.internal.BatchTableEnvironmentImpl;
/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-04-11 12:36
 * @Version: V1.0.0
 */

public class TextBatchSql {
    public static void main(String[] args) throws Exception {
        //获取执行环境
        ExecutionEnvironment bsEnv = ExecutionEnvironment.getExecutionEnvironment();
        //重启策略
        bsEnv.setRestartStrategy(new RestartStrategies.FailureRateRestartStrategyConfiguration(3, Time.minutes(3), Time.minutes(3)));

        EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useAnyPlanner().inBatchMode().build();
        //创建一个table执行环境
        BatchTableEnvironment fsTableEnv = BatchTableEnvironment.create(bsEnv,TableConfig.getDefault());
        //读取文本源
        DataSet<String> dataSet = bsEnv.readTextFile("D:\\personal\\test\\user.txt");
        //数据转换
        DataSet<Tuple2<Integer, String>> map = dataSet.map(new MapFunction<String, Tuple2<Integer, String>>() {
            @Override
            public Tuple2<Integer, String> map(String value) throws Exception {
                String[] splitStr = value.split(",");
                return Tuple2.of(Integer.valueOf(splitStr[0]), splitStr[1]);
            }
        });
        //dateSet
        Table table = fsTableEnv.fromDataSet(map, "uid,name");
        //注册为一个表
        fsTableEnv.createTemporaryView("user1", table);

        //逻辑处理
        Table table1 = fsTableEnv.sqlQuery("select * from user1");

        DataSet<Tuple2<Integer,String>> tupleDataStream = fsTableEnv.toDataSet(table1,TypeInformation.of(new TypeHint<Tuple2<Integer, String>>(){}));

        tupleDataStream.print();

        bsEnv.execute("txt dataset job");
    }
}
