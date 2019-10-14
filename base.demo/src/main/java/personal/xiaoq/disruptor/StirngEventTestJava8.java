package personal.xiaoq.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-10-11 16:03
 * @Version: V1.0.0
 */
public class StirngEventTestJava8 {
    public static void main(String[] args) throws InterruptedException {

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        Disruptor<StringEvent> disruptor = new Disruptor<>(StringEvent::new, bufferSize, Executors.defaultThreadFactory());


        // 可以使用lambda来注册一个EventHandler
        disruptor.handleEventsWith((event, sequence, endOfBatch) -> System.out.println("Event: " + event.getValue()))
                //清除对象
                .then((event, sequence, endOfBatch) -> event=null);
        // Start the Disruptor, starts all threads running
        disruptor.start();
        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<StringEvent> ringBuffer = disruptor.getRingBuffer();
        StringEventProducer producer = new StringEventProducer(ringBuffer);

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            if(l>200)
                break;
            bb.put(0, (byte)l);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.setValue(buffer.toString()), bb);
            Thread.sleep(1000);
        }
      /*  StringBuilder stringBuilder = new StringBuilder();
       while(true){
           ringBuffer.publishEvent((event, sequence, buffer) -> event.setValue(buffer.toString()), stringBuilder);
       }*/

      //最好的方法在并发环境下提高性能是坚持单独写原则（ Single Writer Principle）。如果你的业务场景中只有一个线程写入数据到Disruptor，那么你可以设置成单生产者来提升性能：
        disruptor = new Disruptor(
                StringEvent::new, bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
    }
}
