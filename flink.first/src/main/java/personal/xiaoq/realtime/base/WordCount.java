package personal.xiaoq.realtime.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-03-09 16:25
 * @Version: V1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WordCount implements Serializable {
    private String word;
    private Long count;
}
