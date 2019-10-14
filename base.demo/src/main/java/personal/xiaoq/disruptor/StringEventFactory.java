package personal.xiaoq.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description: 工厂实例化
 * @Date: Created on 2019-10-10 16:37
 * @Version: V1.0.0
 */
public class StringEventFactory implements EventFactory {


    @Override
    public Object newInstance() {
        return new StringEvent();
    }
}
