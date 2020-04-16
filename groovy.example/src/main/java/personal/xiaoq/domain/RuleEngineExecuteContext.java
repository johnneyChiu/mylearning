package personal.xiaoq.domain;

import lombok.Data;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Map;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-01-14 16:03
 * @Version: V1.0.0
 */
@Data
@Resource
public class RuleEngineExecuteContext implements Serializable {


    private static final long serialVersionUID = -1534663460465842867L;
    /**
     * 业务元id,例如订单id,案件id等
     */
    private String bizId;

    /**
     * 业务id,比如产品id或者产品组id
     */
    private String proId;

    /**
     * 策略组ID,供使用方使用一个产品对应多个策略组的情况下使用(比如进行分流策略时)
     */
    private Integer groupId;

    /**
     * 上下文数据
     */
    private Map<String,Object> data;

    /**
     * 是否是初始节点,用于第一个节点自动迭代
     */
    private boolean init = false;

}
