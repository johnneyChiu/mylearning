package personal.xiaoq.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.IntStream;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-10-11 19:29
 * @Version: V1.0.0
 */
public class MessageEventTest {

    public static void main(String[] args) {

    }


    public void nomal(){
        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        Disruptor<MessageEvent<String>> disruptor = new Disruptor<>(MessageEvent::new, bufferSize, DaemonThreadFactory.INSTANCE,
                ProducerType.SINGLE, new BlockingWaitStrategy());


        disruptor.handleEventsWith(((event, sequence, endOfBatch) -> System.out.println(event.getMessage())))
                .then(((event, sequence, endOfBatch) -> event = null));
        disruptor.start();
        final RingBuffer<MessageEvent<String>> ringBuffer = disruptor.getRingBuffer();
        IntStream.range(1,20).forEach(i->ringBuffer.publishEvent((event, sequence) -> event.setMessage(i+" hello Disruptor")));

    }

    public void createRingBuffer(){
        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;
        final RingBuffer<MessageEvent<String>> ringBuffer = RingBuffer.createSingleProducer(MessageEvent::new, bufferSize, new BlockingWaitStrategy());
        //can not use this function
        // Disruptor disruptor = new Disruptor<>(ringBuffer, DaemonThreadFactory.INSTANCE);
    }


}


@Data
@NoArgsConstructor
class MessageEvent<T>{

    private T message;

}


