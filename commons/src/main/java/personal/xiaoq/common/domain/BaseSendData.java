package personal.xiaoq.common.domain;

import lombok.*;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-01-17 11:11
 * @Version: V1.0.0
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BaseSendData implements Serializable {

    private static final long serialVersionUID = -7959395198976545587L;
    private long eventTimestamp;
    private String tableName;
    private String dataBaseName;
    //1 insert,2 update,3 delete
    private int eventType=0;
    private Map<String, Object> data;


}
