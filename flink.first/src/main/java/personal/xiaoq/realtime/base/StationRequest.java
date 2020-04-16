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
 * @Date: Created on 2020-03-25 9:56
 * @Version: V1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StationRequest implements Serializable {
    private Long evTime;
    private String interruptCode;
    private String interruptMsg;
    private String requestId;
    private String transferType;
    private Double mount;

}
