package personal.xiaoq.groovytest;

import com.google.common.collect.MapMaker;
import groovy.lang.GroovyClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-01-02 18:20
 * @Version: V1.0.0
 */
@Slf4j
public class GroovyTest {

    public static void main(String[] args) throws IOException {
        //解析Groovy模板文件
        ConcurrentMap<String, String> concurrentHashMap = new MapMaker().concurrencyLevel(8).initialCapacity(128)
                .makeMap();
        final String path = "classpath*:groovy_temp/*.groovy_template";
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Arrays.stream(resolver.getResources(path))
                .parallel()
                .forEach(resource -> {
                    try {
                        String fileName = resource.getFilename();
                        InputStream input = resource.getInputStream();
                        InputStreamReader reader = new InputStreamReader(input);
                        BufferedReader br = new BufferedReader(reader);
                        StringBuilder template = new StringBuilder();
                        for (String line; (line = br.readLine()) != null; ) {
                            template.append(line).append("\n");
                        }
                        concurrentHashMap.put(fileName, template.toString());
                    } catch (Exception e) {
                        log.error("resolve file failed", e);
                    }
                });
        String scriptBuilder = concurrentHashMap.get("test.groovy_template");
        String scriptClassName = "testGroovy";
        //这一部分String的获取逻辑进行可配置化
        String StrategyLogicUnit = "if(context.amount>=20000){\n" +
                "            context.nextScenario='A'\n" +
                "            return true\n" +
                "        }\n" +
                "        ";
        String fullScript = String.format(scriptBuilder, scriptClassName, StrategyLogicUnit);
        GroovyClassLoader classLoader = new GroovyClassLoader();
        Class<EngineGroovyModuleRule> aClass = classLoader.parseClass(fullScript);
        Context context = new Context();
        context.setAmount(30000);
        try {
            EngineGroovyModuleRule engineGroovyModuleRule = aClass.newInstance();
            log.info("Groovy Script returns:{} ",engineGroovyModuleRule.run(context)==false);
            log.info("Next Scenario is {}",context.getNextScenario());
        }
        catch (Exception e){
            log.error("error...");
        }

    }
}
