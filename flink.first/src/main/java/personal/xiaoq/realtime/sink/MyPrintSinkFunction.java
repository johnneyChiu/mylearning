package personal.xiaoq.realtime.sink;

import lombok.Data;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.operators.StreamingRuntimeContext;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-11-13 15:46
 * @Version: V1.0.0
 */
@Data
public class MyPrintSinkFunction<IN> extends RichSinkFunction<IN> {


    private  MyPrintSinkOutWriter<IN> writer;

    public MyPrintSinkFunction() {
    }

    public MyPrintSinkFunction(MyPrintSinkOutWriter<IN> writer) {
        this.writer = writer;
    }

    public SinkFunction<IN> connect(MyPrintSinkOutWriter<IN> writer) {
        this.writer = writer;
        return this;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        StreamingRuntimeContext context = (StreamingRuntimeContext) getRuntimeContext();
        writer.open(context.getIndexOfThisSubtask(), context.getNumberOfParallelSubtasks());
    }

    @Override
    public void invoke(IN value, Context context) throws Exception {
        writer.write(value);
    }

    @Override
    public String toString() {
        return writer.toString();
    }


}
