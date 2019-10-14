package personal.xiaoq.common.domain;

import lombok.*;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-01-17 12:26
 * @Version: V1.0.0
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseData<T> {
    private String name;

    private T t;

}
