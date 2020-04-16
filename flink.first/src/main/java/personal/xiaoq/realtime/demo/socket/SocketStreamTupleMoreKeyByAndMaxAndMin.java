package personal.xiaoq.realtime.demo.socket;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-03-09 15:29
 * @Version: V1.0.0
 */

public class SocketStreamTupleMoreKeyByAndMaxAndMin {

    public static void main(String[] args) throws Exception {

        //first  nc -l -p 9000
        //second nc localhost 9000

        //CQ,chongqing,2019,1000
        //CQ,chongqing,2018,666
        //CQ,chongqing,2017,800
        //SC,chengdu,2019,900
        //SC,chengdu,2018,800
        //SC,chengdu,2017,880
        //BG,beijing,2019,1500
        //BG,beijing,2018,1200
        //BG,beijing,2017,980
        // the host and the port to connect to

        final String hostname;
        final int port;
        final ParameterTool params = ParameterTool.fromArgs(args);
        try {
            hostname = params.has("hostname") ? params.get("hostname") : "localhost";
            port = params.has("port") ? params.getInt("port") : 9000;
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
        DataStream<String> text = env.socketTextStream(hostname, port, "\n");
        //测试打印
        text.print();
        SingleOutputStreamOperator<Tuple4<String,String,String, Double>> splitWord =
                text.flatMap((String wordLine, Collector<Tuple4<String,String,String, Double>> out) ->{
                    String[] splitsWord = wordLine.split(",");
                    out.collect(Tuple4.of(splitsWord[0],splitsWord[1],splitsWord[2],Double.valueOf(splitsWord[3])));
                })
                .returns(Types.TUPLE(Types.STRING,Types.STRING,Types.STRING, Types.DOUBLE));

        //splitWord.print();

        KeyedStream<Tuple4<String,String,String, Double>, Tuple> wordKeyBySteam = splitWord.keyBy(0,1);

       // wordKeyBySteam.print();


        SingleOutputStreamOperator<Tuple4<String, String, String, Double>> maxStream = wordKeyBySteam.max(3);
        maxStream.print();

        SingleOutputStreamOperator<Tuple4<String, String, String, Double>> minStream = wordKeyBySteam.min(3);
        minStream.print();

        env.execute(params.has("jobname") ? params.get("jobname") : "testSocketJob");

    }

}
