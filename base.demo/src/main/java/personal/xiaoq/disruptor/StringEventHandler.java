package personal.xiaoq.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description: 消费处理
 * @Date: Created on 2019-10-10 16:48
 * @Version: V1.0.0
 */
public class StringEventHandler implements EventHandler<StringEvent> {
    @Override
    public void onEvent(StringEvent event, long sequence, boolean endOfBatch)  {
        System.out.println(event.getValue());
    }
}
