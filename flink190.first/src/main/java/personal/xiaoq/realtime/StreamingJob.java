/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package personal.xiaoq.realtime;

import com.alibaba.otter.canal.protocol.FlatMessage;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumerBase;
import org.apache.flink.table.api.TableConfig;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import personal.xiaoq.realtime.sink.MyPrintSinkFunction;
import personal.xiaoq.realtime.sink.MyPrintSinkOutWriter;
import personal.xiaoq.utils.DateTimeUtils;
import personal.xiaoq.utils.GsonUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="http://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class StreamingJob {

	public static void main(String[] args) throws Exception {
		// set up the streaming execution environment
		final StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();

		ParameterTool parameterTool = ParameterTool.fromMap(new HashMap<String,String>(){{
			put("bootstrap-servers", "172.16.55.23:9092,172.16.55.39:9092,172.16.55.24:9092");
			put("group-id", "for_test_xiaoq");
			put("jobName", "test_streaming_table_job");
			put("dateEscape", "20191019120000");
		}});
		LocalDateTime sourceDateNode = DateTimeUtils.parseTime(parameterTool.get("dateEscape"), DateTimeUtils.TimeFormat.SHORT_SECOND_PATTERN_NONE);;

		TableConfig tableConfig = TableConfig.getDefault();


		//kafka 配置
		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers", parameterTool.get("bootstrap-servers","172.16.55.23:9092,172.16.55.39:9092,172.16.55.24:9092"));
		properties.put("group.id", parameterTool.get("group-id","for_test_xiaoq"));

		//kafka 连接
		FlinkKafkaConsumerBase<String> flinkKafkaConsumerBase =
				new FlinkKafkaConsumer("xiaoqTest", new SimpleStringSchema(), properties)
						.setCommitOffsetsOnCheckpoints(true).setStartFromEarliest();
		final OutputTag<FlatMessage> ddlOutputTag = new OutputTag<FlatMessage>("ddl"){};

		//test data print
		//流处理
		SingleOutputStreamOperator<FlatMessage> dmlSingleOutputStreamOperator = streamExecutionEnvironment
				.addSource(flinkKafkaConsumerBase).uid("111111").name("consume")
				.map(s -> {
					FlatMessage flatMessage = GsonUtils.gsonPrettyCreate().fromJson(s, FlatMessage.class);
					return flatMessage;
				})
				.returns(TypeInformation.of(FlatMessage.class))

				.filter(flatMessage -> {
					if (sourceDateNode != null) {
						long ts = flatMessage.getTs();
						LocalDateTime date = DateTimeUtils.getDateTimeOfTimestamp(ts);
						return DateTimeUtils.compareLocalDateTime(date, sourceDateNode);
					} else {
						return false;
					}
				})
				.process(new ProcessFunction<FlatMessage, FlatMessage>() {
					@Override
					public void processElement(FlatMessage flatMessage, Context ctx, Collector<FlatMessage> out) throws Exception {
						//System.out.println(value);
						List<FlatMessage> output = new ArrayList<>();
						if (flatMessage.getIsDdl())
							ctx.output(ddlOutputTag, flatMessage);
						else
							out.collect(flatMessage);
					}
				});

		//test
		//dml主流输出
		dmlSingleOutputStreamOperator.addSink(new MyPrintSinkFunction<FlatMessage>().connect(new MyPrintSinkOutWriter<FlatMessage>() {
			@Override
			public String serialize(FlatMessage record) {
				return  GsonUtils.gsonNormalCreate().toJson(record);
			}
		}));
		//统计变化成绩,只关注于score栏位
		/*dmlSingleOutputStreamOperator.
				filter(s -> "xiaoqTest".equals(s.getDatabase()) && "t_base_info".equals(s.getTable()))
				.map(new MapFunction<FlatMessage, Tuple2<Integer,Double>>() {
				});*/
		//统计变化的成绩
		/*dmlSingleOutputStreamOperator.map(s->{
			Optional.ofNullable(s.getOld()).ifPresent(list->list.stream().forEach(map->{
					map.entrySet()
					.stream()
					.filter(e-> StringUtils.isNotBlank(e.getKey())).forEach((key,value)->key.equals("score")?);
			}));

		};
*/


		//ddl side流输出
		//可做元数据变化处理
		DataStream<FlatMessage> ddlDataStream = dmlSingleOutputStreamOperator.getSideOutput(ddlOutputTag);
		ddlDataStream.addSink(new MyPrintSinkFunction<FlatMessage>().connect(new MyPrintSinkOutWriter<FlatMessage>() {
			@Override
			public String serialize(FlatMessage record) {
				return  GsonUtils.gsonNormalCreate().toJson(record);
			}
		}));






		streamExecutionEnvironment.execute("Flink Streaming Java API Skeleton");
	}
}
