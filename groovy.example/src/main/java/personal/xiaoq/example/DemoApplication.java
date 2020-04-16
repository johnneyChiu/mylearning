package personal.xiaoq.example;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import personal.xiaoq.groovytest.Context;
import personal.xiaoq.service.EngineGroovyModuleRule;
import personal.xiaoq.service.GroovyScriptService;
import personal.xiaoq.service.RuleEngineGroovyExecutor;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-01-13 15:23
 * @Version: V1.0.0
 */
@SpringBootApplication(scanBasePackages = "personal.xiaoq")

@Slf4j
public class DemoApplication {

    public static void main(String[] args) {
        //SpringApplication.run(DemoApplication.class, args);

        String json = "{\n" +
                "    \"store\": {\n" +
                "        \"book\": [\n" +
                "            {\n" +
                "                \"category\": \"reference\",\n" +
                "                \"author\": \"Nigel Rees\",\n" +
                "                \"title\": \"Sayings of the Century\",\n" +
                "                \"price\": 8.95\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Evelyn Waugh\",\n" +
                "                \"title\": \"Sword of Honour\",\n" +
                "                \"price\": 12.99\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Herman Melville\",\n" +
                "                \"title\": \"Moby Dick\",\n" +
                "                \"isbn\": \"0-553-21311-3\",\n" +
                "                \"price\": 8.99\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"J. R. R. Tolkien\",\n" +
                "                \"title\": \"The Lord of the Rings\",\n" +
                "                \"isbn\": \"0-395-19395-8\",\n" +
                "                \"price\": 22.99\n" +
                "            }\n" +
                "        ],\n" +
                "        \"bicycle\": {\n" +
                "            \"color\": \"red\",\n" +
                "            \"price\": 19.95\n" +
                "        }\n" +
                "    },\n" +
                "    \"expensive\": 10\n" +
                "}";

        SpringApplication springApplication = new SpringApplication(DemoApplication.class);
        ConfigurableApplicationContext context = springApplication.run(args);
        log.info("this bean is {}",context.getBean(GroovyScriptService.class));
        //load script
        log.info("load {} scripts",(context.getBean(GroovyScriptService.class)).loadScript());

        RuleEngineGroovyExecutor<EngineGroovyModuleRule> ruleRuleEngineGroovyExecutor =
                context.getBean(RuleEngineGroovyExecutor.class);
        Context myContext = new Context();
        myContext.setAmount(30000);
        System.out.println(ruleRuleEngineGroovyExecutor.getInstance("enginesScript_1").run(myContext, new Object()));;


        /**
         * jsonpath 解析数据
         */
        System.out.println(ruleRuleEngineGroovyExecutor.getInstance("enginesScript_2").run(json,"$.store.book[?(@.price < 10)]"));





        ///context.close();
    }


}
