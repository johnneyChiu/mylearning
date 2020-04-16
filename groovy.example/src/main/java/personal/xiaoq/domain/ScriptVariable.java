package personal.xiaoq.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import personal.xiaoq.common.VariableType;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-01-14 13:53
 * @Version: V1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Resource
public class ScriptVariable implements Serializable {

    private static final long serialVersionUID = 8253783017477958881L;

    /**
     * 变量名称
     */
    private String name;

    /**
     * 变量类型
     */
    private VariableType type;
}
