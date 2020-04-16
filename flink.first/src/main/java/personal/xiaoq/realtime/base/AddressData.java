package personal.xiaoq.realtime.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-04-02 11:41
 * @Version: V1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddressData {
    private String interruptCode;
    private String address;
    private Long evTime;
}
