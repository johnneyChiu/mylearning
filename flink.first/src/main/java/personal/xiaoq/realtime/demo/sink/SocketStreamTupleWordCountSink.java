package personal.xiaoq.realtime.demo.sink;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.util.Collector;

import java.util.Arrays;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-03-09 15:29
 * @Version: V1.0.0
 */

public class SocketStreamTupleWordCountSink {

    public static void main(String[] args) throws Exception {

        //first  nc -l -p 9000
        //second nc localhost 9000
        // in flink spark hive impala hive
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
        SingleOutputStreamOperator<Tuple2<String, Long>> splitWord =
                text.flatMap((String wordLine, Collector<Tuple2<String, Long>> out) ->
                Arrays.stream(wordLine.split(" ")).forEach(s -> {
                    out.collect(Tuple2.of(s, 1L));
                }))
                .returns(Types.TUPLE(Types.STRING, Types.LONG));

        //splitWord.print();

        KeyedStream<Tuple2<String, Long>, Tuple> wordKeyBySteam = splitWord.keyBy(0);

       // wordKeyBySteam.print();


        SingleOutputStreamOperator<Tuple2<String, Long>> sumStream = wordKeyBySteam.sum(1);
        sumStream.addSink(new RichSinkFunction<Tuple2<String, Long>>() {
            @Override
            public void invoke(Tuple2<String, Long> value, Context context) throws Exception {
                int index = getRuntimeContext().getIndexOfThisSubtask();
                System.out.println(index+">"+value);
            }
        });

        env.execute(params.has("jobname") ? params.get("jobname") : "testSocketJob");

    }

}
