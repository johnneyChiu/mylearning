package personal.xiaoq.realtime.demo.sql;

import com.google.gson.Gson;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;
import personal.xiaoq.realtime.base.StationRequest;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-03-24 14:52
 * @Version: V1.0.0
 */
public class SocketStreamSql {

    public static void main(String[] args) throws Exception {
        //first  nc -l -p 9000
        //second nc localhost 9000


        /**
         *
         * {"evTime":1585034152492,"interruptCode":"B01","interruptMsg":"离线计算-无中转城市","requestId":"565f3d5ef47705fd92be40c70d09c135","transferType":"TF"}
         * {"evTime":1585034153492,"interruptCode":"D02","interruptMsg":"回头路规则过滤了所有方案","requestId":"36d1496be24ad63d1d1d6e6bbfaf1e4d","transferType":"TB"}
         * {"evTime":1585034154492,"interruptCode":"B01","interruptMsg":"离线计算-无中转城市","requestId":"565f3d5ef47705fd92be40c70d09c135","transferType":"FT"}
         * {"evTime":1585034155492,"interruptCode":"D01","interruptMsg":"组装方案无结果","requestId":"2c0971622719a1dbf0734e871b761ee8","transferType":"BT"}
         * {"evTime":1585034156492,"interruptCode":"B01","interruptMsg":"离线计算-无中转城市","requestId":"c4c13b340ea4d7eb5f63c0e4add8f07f","transferType":"BT"}
         * {"evTime":1585034157492,"interruptCode":"D01","interruptMsg":"组装方案无结果","requestId":"23b029808b3942d80e174f58156247f2","transferType":"TB"}
         * {"evTime":1585034158492,"interruptCode":"B01","interruptMsg":"离线计算-无中转城市","requestId":"dc3f0fcad202fb9e1319a25710b5644b","transferType":"FF"}
         * {"evTime":1585034159492,"interruptCode":"D01","interruptMsg":"组装方案无结果","requestId":"3f1c4fdd99ec9ec0ac4f1a13a4fb4346","transferType":"TB"}
         * {"evTime":1585034160492,"interruptCode":"D01","interruptMsg":"组装方案无结果","requestId":"490eebfeb61cff2c14ae8e36e3314d32","transferType":"TB"}
         * {"evTime":1585034161492,"interruptCode":"E03","interruptMsg":"供应商响应异常-余票请求无结果","requestId":"ddf69e8249857dcae485cea3a4cdf1bf","transferType":"BT"}
         * {"evTime":1585034162492,"interruptCode":"C01","interruptMsg":"两程无余票","requestId":"8292ae585d1ca1df23008b131f172087","transferType":"BT"}
         * {"evTime":1585034163492,"interruptCode":"C02","interruptMsg":"第一程无余票","requestId":"4ea56235b025e9d3a0eae62ae0d3093e","transferType":"FF"}
         * {"evTime":1585034164492,"interruptCode":"D01","interruptMsg":"组装方案无结果","requestId":"00485e6984ee90c33a60878129ae6a39","transferType":"TT"}
         * {"evTime":1585034165492,"interruptCode":"B01","interruptMsg":"离线计算-无中转城市","requestId":"eebe79196da3227845460c0bd8125e92","transferType":"FT"}
         * {"evTime":1585034166492,"interruptCode":"C02","interruptMsg":"第一程无余票","requestId":"c600026a13731f297176ea714e04747c","transferType":"BT"}
         * {"evTime":1585034167492,"interruptCode":"B01","interruptMsg":"离线计算-无中转城市","requestId":"7fb3d6625885de1464d9e650a170c5ed","transferType":"TT"}
         * {"evTime":1585034168492,"interruptCode":"D01","interruptMsg":"组装方案无结果","requestId":"eebe79196da3227845460c0bd8125e92","transferType":"TB"}
         * {"evTime":1585034169492,"interruptCode":"C02","interruptMsg":"第一程无余票","requestId":"09519ed35de88a3bc177febe81ba82fa","transferType":"BT"}
         * {"evTime":1585034170492,"interruptCode":"E03","interruptMsg":"供应商响应异常-余票请求无结果","requestId":"b66b20e1b52ed90fae861c86a647b524","transferType":"TB"}
         * {"evTime":1585034171492,"interruptCode":"D01","interruptMsg":"组装方案无结果","requestId":"6f5022b233488c0ec5b657f702d4a490","transferType":"BT"}
         * {"evTime":1585034172492,"interruptCode":"C02","interruptMsg":"第一程无余票","requestId":"da140baa4fe6499d97e862241b07a8e0","transferType":"TB"}
         * {"evTime":1585034173492,"interruptCode":"B01","interruptMsg":"离线计算-无中转城市","requestId":"95dd55f6fd34c2d3f3a62d74960f8aa6","transferType":"TB"}
         * {"evTime":1585034174492,"interruptCode":"C02","interruptMsg":"第一程无余票","requestId":"da140baa4fe6499d97e862241b07a8e0","transferType":"BT"}
         * {"evTime":1585034175492,"interruptCode":"D01","interruptMsg":"组装方案无结果","requestId":"ba6c5bc19e3727cf88b3949fa0cfcf3f","transferType":"TB"}
         * {"evTime":1585034176492,"interruptCode":"B01","interruptMsg":"离线计算-无中转城市","requestId":"d187f28174fb3bcfe5bc7a25a760d251","transferType":"BT"}
         * {"evTime":1585034177492,"interruptCode":"C01","interruptMsg":"两程无余票","requestId":"27e6892c41cc6c7eee2bb5cddac3c304","transferType":"BT"}
         *
         *
         */

        String hostName;
        int port;
        String planner;
        final ParameterTool params = ParameterTool.fromArgs(args);
        try {
            hostName = params.has("hostname") ? params.get("hostname") : "localhost";
            port = params.has("port") ? params.getInt("port") : 9000;
            planner = params.has("planner") ? params.get("planner") : "flink";
        } catch (Exception e) {
            System.err.println("No port specified. Please run 'SocketWindowWordCount " +
                    "--hostname <hostname> --port <port>', where hostname (localhost by default) " +
                    "and port is the address of the text server");
            System.err.println("To start a simple text server, run 'netcat -l <port>' and " +
                    "type the input text into the command line");
            return;
        }

        // get the execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // get input data by connecting to the socket
        DataStream<String> text = env.socketTextStream(hostName, port, "\n");

        SingleOutputStreamOperator<StationRequest> stationRequest = text.map(wordLine -> new Gson().fromJson(wordLine, StationRequest.class))
                .returns(Types.POJO(StationRequest.class))
                .name("map");


        StreamTableEnvironment tEnv;
        if (Objects.equals(planner, "blink")) {	// use blink planner in streaming mode
            EnvironmentSettings settings = EnvironmentSettings.newInstance()
                    .useBlinkPlanner()
                    .inStreamingMode()
                    .build();
            tEnv = StreamTableEnvironment.create(env, settings);
        } else if (Objects.equals(planner, "flink")) {	// use flink planner in streaming mode
            tEnv = StreamTableEnvironment.create(env);
        } else {
            System.err.println("The planner is incorrect. Please run 'StreamSQLExample --planner <planner>', " +
                    "where planner (it is either flink or blink, and the default is flink) indicates whether the " +
                    "example uses flink planner or blink planner.");
            return;
        }
        tEnv.createTemporaryView("EventRequest",stationRequest,"evTime, interruptCode, interruptMsg,requestId,transferType");

        Table table = tEnv.sqlQuery(
                "SELECT interruptCode, count(interruptCode) as frequency FROM EventRequest GROUP BY interruptCode");

        tEnv.toRetractStream(table, Row.class).print();
        tEnv.execute("test sql streaming");
    }

}
