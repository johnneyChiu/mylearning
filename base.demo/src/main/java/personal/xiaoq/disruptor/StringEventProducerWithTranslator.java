package personal.xiaoq.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import lombok.Data;

import java.nio.ByteBuffer;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-10-10 17:20
 * @Version: V1.0.0
 */
@Data
public class StringEventProducerWithTranslator {

    //一个translator可以看做一个事件初始化器，publicEvent方法会调用它

    //填充Event
    private static final EventTranslatorOneArg<StringEvent, ByteBuffer> TRANSLATOR =
            new EventTranslatorOneArg<StringEvent, ByteBuffer>() {
                public void translateTo(StringEvent event, long sequence, ByteBuffer bb) {
                    event.setValue(bb.toString());
                }
            };

    private final RingBuffer<StringEvent> ringBuffer;


    public void onData(ByteBuffer bb) {
        ringBuffer.publishEvent(TRANSLATOR, bb);
    }
}
