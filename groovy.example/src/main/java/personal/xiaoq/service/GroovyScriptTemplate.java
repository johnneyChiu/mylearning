package personal.xiaoq.service;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-01-15 10:11
 * @Version: V1.0.0
 */
@Slf4j
@Service
public class GroovyScriptTemplate implements InitializingBean {
    private static final Map<String, String> SCRIPT_TEMPLATE_MAP = Maps.newConcurrentMap();

    public  String  getScript(String fileName){
        String template = SCRIPT_TEMPLATE_MAP.get(fileName);
        if(StringUtils.isEmpty(template)){
            throw new RuntimeException(String.format("请添加脚本模板: %s 到resources目录下", fileName));
        }
        return template;
    }

    private void scriptTemplate() throws IOException {

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

                        SCRIPT_TEMPLATE_MAP.put(fileName, template.toString());
                        log.info("load script template :{}", resource.getURL());
                    } catch (Exception e) {
                        log.error("read file failed", e);
                    }
                });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.scriptTemplate();
    }

}
