package personal.xiaoq.disruptor;

import com.lmax.disruptor.RingBuffer;
import lombok.AllArgsConstructor;

import java.nio.ByteBuffer;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-10-10 16:51
 * @Version: V1.0.0
 */
@AllArgsConstructor
public class StringEventProducer {

    private final RingBuffer<StringEvent> ringBuffer;

    public void onData(ByteBuffer bb) {
        //可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
        long sequence = ringBuffer.next();
        try {
            //用上面的索引取出一个空的事件用于填充
            StringEvent event = ringBuffer.get(sequence);// for the sequence
            event.setValue(new String(new char[]{bb.getChar(0)}));
        } finally {
            //发布事件
            ringBuffer.publish(sequence);
        }
    }
}


