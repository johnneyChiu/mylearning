package personal.xiaoq.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:策略、变量、脚本
 * @Date: Created on 2020-01-14 13:49
 * @Version: V1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StrategyScriptEntity implements Serializable {

    private static final long serialVersionUID = 2591046502318483238L;
    /**
     * 策略id
     */
    private Integer strategyId;

    /**
     * 脚本内容
     */
    private String script;

    /**
     * 变量名称，类型json
     */
    private String variables;

    /**
     * 作者
     */
    private String author;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
