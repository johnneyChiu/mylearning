package personal.xiaoq.config;

import org.springframework.context.annotation.Configuration;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-02-05 15:08
 * @Version: V1.0.0
 */
@Configuration
public class HttpSessionConfig {
    /**
     *  解决redis集群环境没有开启Keyspace notifications导致的
     *
     *  Error creating bean with name 'enableRedisKeyspaceNotificationsInitializer' defined in class path resource
     *
     * */
   /* @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }*/

}
