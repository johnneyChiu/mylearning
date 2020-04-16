package personal.xiaoq.common;

import lombok.Getter;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-01-14 14:05
 * @Version: V1.0.0
 */

@Getter
public enum VariableType {

    STRING(0,"String类型"),

    INT(1,"int类型");

    private final int value ;
    private final String desc ;

    VariableType(int value ,String desc){
        this.value = value ;
        this.desc = desc;
    }

}
