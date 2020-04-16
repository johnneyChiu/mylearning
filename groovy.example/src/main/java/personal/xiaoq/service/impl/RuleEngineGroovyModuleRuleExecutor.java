package personal.xiaoq.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import groovy.lang.GroovyClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personal.xiaoq.common.MD5Utils;
import personal.xiaoq.service.EngineGroovyModuleRule;
import personal.xiaoq.service.GroovyScriptTemplate;
import personal.xiaoq.service.RuleEngineGroovyExecutor;

import java.util.Map;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-01-15 17:02
 * @Version: V1.0.0
 */
@Slf4j
@Service
public class RuleEngineGroovyModuleRuleExecutor implements
        RuleEngineGroovyExecutor<EngineGroovyModuleRule> {
    private Map<String, Class<EngineGroovyModuleRule>> nameAndClass = Maps.newConcurrentMap();

    private Map<String, String> nameAndMd5 = Maps.newConcurrentMap();

    @Autowired
    private GroovyScriptTemplate groovyScriptTemplate;

    @Override
    public EngineGroovyModuleRule getInstance(String name) {
        try {
            Class<EngineGroovyModuleRule> aClass = nameAndClass.get(name);
            if (aClass == null) {
                throw new IllegalArgumentException(String.format("script:%s not load", name));
            }
            return aClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void praseAndCache(String name, String script, String templateName) {
        try {
            Preconditions.checkNotNull(name);
            Preconditions.checkNotNull(script);
            String scriptBuilder = groovyScriptTemplate.getScript(templateName);
            String scriptClassName = RuleEngineGroovyModuleRuleExecutor.class.getSimpleName() + "_" + name;
            String fullScript = String.format(scriptBuilder, scriptClassName, script);

            String oldMd5 = nameAndMd5.get(name);
            String newMd5 = MD5Utils.getStringMD5(fullScript);
            if (oldMd5 != null && oldMd5.equals(newMd5)) {
                return;
            }

            GroovyClassLoader classLoader = new GroovyClassLoader();
            Class aClass = classLoader.parseClass(fullScript);
            log.info("collection-engine load script:{} finish", name);
            nameAndClass.put(name, aClass);
            nameAndMd5.put(name, newMd5);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }



}
