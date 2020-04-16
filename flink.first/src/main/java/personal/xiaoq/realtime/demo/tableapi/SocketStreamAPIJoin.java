package personal.xiaoq.realtime.demo.tableapi;

import com.google.gson.Gson;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import personal.xiaoq.realtime.base.AddressData;
import personal.xiaoq.realtime.base.StationRequest;
import personal.xiaoq.utils.DateTimeUtils;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-04-02 11:34
 * @Version: V1.0.0
 */
public class SocketStreamAPIJoin {


    public static void main(String[] args) throws Exception {
        //first  nc -l -p 9000
        //second nc localhost 9000


        String hostName,hostName1;
        int port,port1;
        String planner;
        long allowedLateness = 10 * 1000;
        final ParameterTool params = ParameterTool.fromArgs(args);
        try {
            hostName = params.has("hostname") ? params.get("hostname") : "localhost";
            port = params.has("port") ? params.getInt("port") : 9000;
            hostName1 = params.has("hostname1") ? params.get("hostname1") : "localhost";
            port1 = params.has("port1") ? params.getInt("port1") : 9001;
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
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.getConfig().setAutoWatermarkInterval(2000);

        // get input data by connecting to the socket
        DataStream<String> text = env.socketTextStream(hostName, port, "\n");
/**
 *
 * {"evTime":1585034152492,"interruptCode":"B01","interruptMsg":"离线计算-无中转城市","requestId":"565f3d5ef47705fd92be40c70d09c135","transferType":"TF","mount":23.12}
 * {"evTime":1585034153492,"interruptCode":"D02","interruptMsg":"回头路规则过滤了所有方案","requestId":"36d1496be24ad63d1d1d6e6bbfaf1e4d","transferType":"TB","mount":43.12}
 * {"evTime":1585034154492,"interruptCode":"B01","interruptMsg":"离线计算-无中转城市","requestId":"565f3d5ef47705fd92be40c70d09c135","transferType":"FT","mount":21.12}
 * {"evTime":1585034155492,"interruptCode":"D01","interruptMsg":"组装方案无结果","requestId":"2c0971622719a1dbf0734e871b761ee8","transferType":"BT","mount":22.13}
 * {"evTime":1585034156492,"interruptCode":"B01","interruptMsg":"离线计算-无中转城市","requestId":"c4c13b340ea4d7eb5f63c0e4add8f07f","transferType":"BT","mount":34.12}
 * {"evTime":1585034157492,"interruptCode":"D01","interruptMsg":"组装方案无结果","requestId":"23b029808b3942d80e174f58156247f2","transferType":"TB","mount":13.12}
 * {"evTime":1585034158492,"interruptCode":"B01","interruptMsg":"离线计算-无中转城市","requestId":"dc3f0fcad202fb9e1319a25710b5644b","transferType":"FF","mount":221.12}
 * {"evTime":1585034159492,"interruptCode":"D01","interruptMsg":"组装方案无结果","requestId":"3f1c4fdd99ec9ec0ac4f1a13a4fb4346","transferType":"TB","mount":234.12}
 * {"evTime":1585034160492,"interruptCode":"D01","interruptMsg":"组装方案无结果","requestId":"490eebfeb61cff2c14ae8e36e3314d32","transferType":"TB","mount":263.12}
 * {"evTime":1585034161492,"interruptCode":"E03","interruptMsg":"供应商响应异常-余票请求无结果","requestId":"ddf69e8249857dcae485cea3a4cdf1bf","transferType":"BT","mount":216.12}
 * {"evTime":1585034162492,"interruptCode":"C01","interruptMsg":"两程无余票","requestId":"8292ae585d1ca1df23008b131f172087","transferType":"BT","mount":71.12}
 * {"evTime":1585034163492,"interruptCode":"C02","interruptMsg":"第一程无余票","requestId":"4ea56235b025e9d3a0eae62ae0d3093e","transferType":"FF","mount":72.12}
 * {"evTime":1585034164492,"interruptCode":"D01","interruptMsg":"组装方案无结果","requestId":"00485e6984ee90c33a60878129ae6a39","transferType":"TT","mount":83.12}
 * {"evTime":1585034165492,"interruptCode":"B01","interruptMsg":"离线计算-无中转城市","requestId":"eebe79196da3227845460c0bd8125e92","transferType":"FT","mount":23.12}
 * {"evTime":1585034166492,"interruptCode":"C02","interruptMsg":"第一程无余票","requestId":"c600026a13731f297176ea714e04747c","transferType":"BT","mount":22.12}
 * {"evTime":1585034167492,"interruptCode":"B01","interruptMsg":"离线计算-无中转城市","requestId":"7fb3d6625885de1464d9e650a170c5ed","transferType":"TT","mount":13.12}
 * {"evTime":1585034168492,"interruptCode":"D01","interruptMsg":"组装方案无结果","requestId":"eebe79196da3227845460c0bd8125e92","transferType":"TB","mount":241.12}
 * {"evTime":1585034169492,"interruptCode":"C02","interruptMsg":"第一程无余票","requestId":"09519ed35de88a3bc177febe81ba82fa","transferType":"BT","mount":26.14}
 * {"evTime":1585034170492,"interruptCode":"E03","interruptMsg":"供应商响应异常-余票请求无结果","requestId":"b66b20e1b52ed90fae861c86a647b524","transferType":"TB","mount":88.12}
 * {"evTime":1585034171492,"interruptCode":"D01","interruptMsg":"组装方案无结果","requestId":"6f5022b233488c0ec5b657f702d4a490","transferType":"BT","mount":99.12}
 * {"evTime":1585034172492,"interruptCode":"C02","interruptMsg":"第一程无余票","requestId":"da140baa4fe6499d97e862241b07a8e0","transferType":"TB","mount":96.12}
 * {"evTime":1585034173492,"interruptCode":"B01","interruptMsg":"离线计算-无中转城市","requestId":"95dd55f6fd34c2d3f3a62d74960f8aa6","transferType":"TB","mount":42.12}
 * {"evTime":1585034174492,"interruptCode":"C02","interruptMsg":"第一程无余票","requestId":"da140baa4fe6499d97e862241b07a8e0","transferType":"BT","mount":53.12}
 * {"evTime":1585034175492,"interruptCode":"D01","interruptMsg":"组装方案无结果","requestId":"ba6c5bc19e3727cf88b3949fa0cfcf3f","transferType":"TB","mount":16.12}
 * {"evTime":1585034176492,"interruptCode":"B01","interruptMsg":"离线计算-无中转城市","requestId":"d187f28174fb3bcfe5bc7a25a760d251","transferType":"BT","mount":77.12}
 * {"evTime":1585034177492,"interruptCode":"C01","interruptMsg":"两程无余票","requestId":"27e6892c41cc6c7eee2bb5cddac3c304","transferType":"BT","mount":12.12}
 *
 */
        SingleOutputStreamOperator<StationRequest> stationRequest =
                text.map(wordLine -> new Gson().fromJson(wordLine, StationRequest.class))
                        .returns(Types.POJO(StationRequest.class))
                        .setParallelism(1)
                        .name("stationRequest")

                        .assignTimestampsAndWatermarks(new AssignerWithPunctuatedWatermarks<StationRequest>() {
                            private Long currentMaxTimestamp = 0l;
                            //水印延迟时间
                            private final long maxOutOfOrderness = 3000;

                            @Nullable
                            @Override
                            public Watermark checkAndGetNextWatermark(StationRequest lastElement, long extractedTimestamp) {
                                Long watermark = currentMaxTimestamp - maxOutOfOrderness;
                                System.out.println("交易表水印时间:" + DateTimeUtils.parseTime(DateTimeUtils.getDateTimeOfTimestamp(watermark), DateTimeUtils.TimeFormat.LONG_DATE_PATTERN_LINE));
                                return new Watermark(watermark);

                            }

                            @Override
                            public long extractTimestamp(StationRequest element, long previousElementTimestamp) {
                                long timestamp = element.getEvTime();
                                //System.out.println("交易表当前时间:" + DateTimeUtils.parseTime(DateTimeUtils.getDateTimeOfTimestamp(timestamp), DateTimeUtils.TimeFormat.LONG_DATE_PATTERN_LINE));
                                currentMaxTimestamp = Math.max(timestamp, currentMaxTimestamp);
                                return timestamp;
                            }
                        });
        //周期性水印
        /*.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks<StationRequest>() {
            private final long maxOutOfOrderness = 3000; // 3.0 seconds

            private long currentMaxTimestamp;

            @Override
            public long extractTimestamp(StationRequest element, long previousElementTimestamp) {
                long timestamp = element.getEvTime();
                System.out.println("当前时间:"+DateTimeUtils.parseTime(DateTimeUtils.getDateTimeOfTimestamp(timestamp), DateTimeUtils.TimeFormat.LONG_DATE_PATTERN_LINE));

                currentMaxTimestamp = Math.max(timestamp, currentMaxTimestamp);
                return timestamp;
            }

            @Override
            public Watermark getCurrentWatermark() {
                // return the watermark as current highest timestamp minus the out-of-orderness bound
                System.out.println("水印时间:"+DateTimeUtils.parseTime(DateTimeUtils.getDateTimeOfTimestamp(currentMaxTimestamp-maxOutOfOrderness), DateTimeUtils.TimeFormat.LONG_DATE_PATTERN_LINE));
                return new Watermark(currentMaxTimestamp - maxOutOfOrderness);
            }
        })*/
         /*       .keyBy("interruptCode")
                .window(TumblingEventTimeWindows.of(Time.seconds(3)))
                .sum("mount").setParallelism(4);
        stationRequest

                .print();*/
        DataStream<String> text1 = env.socketTextStream(hostName1, port1, "\n");
        SingleOutputStreamOperator<AddressData> addressDataStream =
                text1.map(wordLine -> new Gson().fromJson(wordLine, AddressData.class))
                        .returns(Types.POJO(AddressData.class))
                        .name("addressDataStream")
                        .assignTimestampsAndWatermarks(
                                new BoundedOutOfOrdernessTimestampExtractor<AddressData>(Time.of(1, TimeUnit.SECONDS)) {
                                    @Override
                                    public long extractTimestamp(AddressData element) {
                                        return element.getEvTime();
                                    }
                                });

        stationRequest.join(addressDataStream)
                .where(new KeySelector<StationRequest, String>() {
                    @Override
                    public String getKey(StationRequest value) throws Exception {
                        return value.getInterruptCode();
                    }
                })
                .equalTo(new KeySelector<AddressData, String>() {
                    @Override
                    public String getKey(AddressData value) throws Exception {
                        return value.getInterruptCode();
                    }
                })
                .window(TumblingEventTimeWindows.of(Time.seconds(2)))
                .apply(new JoinFunction<StationRequest, AddressData, Tuple3<String, String, String>>() {
                    @Override
                    public Tuple3<String, String, String> join(StationRequest first, AddressData second) throws Exception {
                        return Tuple3.of(first.getInterruptCode(), first.getInterruptMsg(), second.getAddress());
                    }
                })
                .print();
        env.execute("test");

    }

}
