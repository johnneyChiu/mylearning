package personal.xiaoq.kafka.demo.services.basic;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import personal.xiaoq.kafka.demo.domain.TestRecords;
import personal.xiaoq.utils.GsonUtils;

import java.math.BigDecimal;

import static personal.xiaoq.utils.RandomUtils.getRandomDbouleInRange;
import static personal.xiaoq.utils.RandomUtils.getRandomIntInRange;


/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-01-11 16:26
 * @Version: V1.0.0
 */
@Service
@Slf4j
public class Testkafka {

    private final KafkaTemplate kafkaTemplate;

    @Autowired
    public Testkafka(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Async("myExecutor")
    public void sendMessage(Long i) {
        TestRecords testRecords = TestRecords.builder()
                .threadId("thread_" + i)
                .id(getRandomIntInRange(1, 100))
                .money(BigDecimal.valueOf(getRandomDbouleInRange(-500, 500)))
                .startTime(System.currentTimeMillis())
                .build();
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send("topic6", GsonUtils.gsonPrettyCreate().toJson(testRecords));
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("kafka send message error,ex={},topic={},data={}", throwable, "topic4", testRecords);
            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                log.info("kafka send message successfully,topic={},data={}", "topic4", testRecords);
            }
        });

    }



    @Async("myExecutor")
    public void testMessage(Long i) {
        TestRecords testRecords = TestRecords.builder()
                .threadId("thread_" + i)
                .id(getRandomIntInRange(1, 100))
                .money(BigDecimal.valueOf(getRandomDbouleInRange(-500, 500)))
                .startTime(System.currentTimeMillis())
                .build();
        log.info("data is {}", GsonUtils.gsonPrettyCreate().toJson(testRecords));
    }

    @Async("myExecutor")
    public ListenableFuture<TestRecords> testFutureMessage(Long i) {
        TestRecords testRecords = TestRecords.builder()
                .threadId("thread_" + i)
                .id(getRandomIntInRange(1, 100))
                .money(BigDecimal.valueOf(getRandomDbouleInRange(-500, 500)))
                .startTime(System.currentTimeMillis())
                .build();
        return new AsyncResult<>(testRecords);
    }

    @KafkaListener(topics = "topic4",groupId = "xiaoq")
    public void getData(ConsumerRecord<String, String> record) {
        log.info("consumer data:{}", record.value());
    }


}
