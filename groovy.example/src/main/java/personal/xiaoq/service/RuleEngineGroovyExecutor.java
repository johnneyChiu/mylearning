package personal.xiaoq.service;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:通过名称获取实例，并编译脚本缓存
 * @Date: Created on 2020-01-15 13:50
 * @Version: V1.0.0
 */
public interface RuleEngineGroovyExecutor<T> {

    /**
     * 获取脚本执行实例
     */
    T getInstance(String name);

    /**
     * 编译脚本并缓存
     */
    void praseAndCache(String name, String script,String templateName);

}
