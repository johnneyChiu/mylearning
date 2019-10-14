package personal.xiaoq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import personal.xiaoq.kafka.demo.services.integrated.TestAsync;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@Slf4j
public class KakfaDemoApplication
{
    public static void main( String[] args )
    {

        SpringApplication springApplication = new SpringApplication(KakfaDemoApplication.class);
        ConfigurableApplicationContext context = springApplication.run(args);
        ((TestAsync) context.getBean("testAsync")).doFutureTest();
    }
}
