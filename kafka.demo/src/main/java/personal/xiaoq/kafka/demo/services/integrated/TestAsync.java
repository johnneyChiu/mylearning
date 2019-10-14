package personal.xiaoq.kafka.demo.services.integrated;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personal.xiaoq.common.domain.BaseSendData;
import personal.xiaoq.kafka.demo.services.basic.Testkafka;
import personal.xiaoq.utils.BigDataParserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.LongStream;

import static personal.xiaoq.utils.BigDataParserUtils.BEFORE_MAP;
import static personal.xiaoq.utils.RandomUtils.getRandomIntInRange;


/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-01-11 17:57
 * @Version: V1.0.0
 */
@Service
@Slf4j
public class TestAsync {

    @Autowired
    private Testkafka testkafka;


    public void doSendKafka(){
        LongStream.range(1,10000000L).forEach(i->testkafka.sendMessage(i));
    }
    public void doPrintTest(){
        LongStream.range(1,1000L).forEach(i->testkafka.testMessage(i));
    }
    public void doFutureTest(){
        LongStream.range(1,1000L).forEach(i-> {
            try {
                log.info("Another is {}",testkafka.testFutureMessage(i).get());
            } catch (InterruptedException e) {
                log.error("InterruptedException:{}",e);
            } catch (ExecutionException e) {
                log.error("ExecutionException:{}",e);
            }
        });
    }



}
