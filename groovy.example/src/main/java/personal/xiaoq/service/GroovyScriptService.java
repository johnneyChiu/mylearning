package personal.xiaoq.service;

import personal.xiaoq.domain.RuleEngineExecuteContext;
import personal.xiaoq.domain.ScriptVariable;
import personal.xiaoq.domain.StrategyScriptEntity;

import java.util.List;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:业务脚本
 * @Date: Created on 2020-01-14 13:44
 * @Version: V1.0.0
 */
public interface GroovyScriptService {
    /**
     * 框架执行核心方法
     * @param context
     * @param strategyId
     * @return
     */
    Object  fragmentEval(RuleEngineExecuteContext context, Integer strategyId);

    /**
     * 判定结果
     * @param context
     * @param strategyId
     * @return
     */
    boolean booleanScript(RuleEngineExecuteContext context,Integer strategyId);

    /**
     * 存储执行脚本片段和变量
     * @param strategyId
     * @param script
     * @param variables
     * @param author
     */
    void saveVariables(Integer strategyId, String script, List<ScriptVariable> variables, String author);

    /**
     * 从存储源获取变量的处理以及脚本的处理
     * @param strategyId
     * @return
     */
    StrategyScriptEntity queryByStrategyId(Integer strategyId);

    /**
     * 从存储源获取变量的处理以及脚本的处理
     * @param strategyIds
     * @return
     */
    List<StrategyScriptEntity> queryByStrategyIds(List<Integer> strategyIds);

    /**
     * 测试脚本
     * @param scriptText
     * @return
     */
    /*boolean scriptTest(String scriptText);
*/

    /**
     * 加载数据库所有脚本
     */
    int loadScript();

    /**
     * 加载数据库所有未停用的脚本
     * @param isInit 是否初始化调用
     * @return 成功加载脚本个数
     */
    int loadValidScript(boolean isInit);

}
