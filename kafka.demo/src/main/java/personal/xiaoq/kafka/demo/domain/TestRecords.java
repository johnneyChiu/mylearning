package personal.xiaoq.kafka.demo.domain;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-10-10 15:09
 * @Version: V1.0.0
 */
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TestRecords implements Serializable {
    private static final long serialVersionUID = -1036804105785325122L;
    private String threadId;
    private int id;
    private BigDecimal money;
    private long startTime;
}