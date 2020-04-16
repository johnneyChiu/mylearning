package personal.xiaoq.service;

import personal.xiaoq.domain.StrategyScriptEntity;

import java.util.List;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:获取执行策略脚本
 * @Date: Created on 2020-01-15 13:52
 * @Version: V1.0.0
 */
public interface StrategyScriptDataSouce {


    /**
     * 根据策略id查询对应表达式片段信息
     */
    StrategyScriptEntity queryByStrategyId(Integer strategyId);


    List<StrategyScriptEntity> queryByStrategyIds(List<Integer> strategyIds);
    /**
     * 保存策略的执行片段
     */
    boolean saveScript(StrategyScriptEntity strategyScriptEntity);

    /**
     * 查询所有的表达式片段
     */
    List<StrategyScriptEntity> queryAll();
}
