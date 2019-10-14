package personal.xiaoq.disruptor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description: 事件传递
 * @Date: Created on 2019-10-10 16:35
 * @Version: V1.0.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StringEvent {
    private String value;
}
