package personal.xiaoq.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import personal.xiaoq.domain.StrategyScriptEntity;
import personal.xiaoq.service.StrategyScriptDataSouce;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-01-20 15:26
 * @Version: V1.0.0
 */
@Slf4j
@Service
public class StrategyScriptDataSourceImpl implements StrategyScriptDataSouce {
    @Override
    public StrategyScriptEntity queryByStrategyId(Integer strategyId) {
        return null;
    }

    @Override
    public List<StrategyScriptEntity> queryByStrategyIds(List<Integer> strategyIds) {
        return null;
    }

    @Override
    public boolean saveScript(StrategyScriptEntity strategyScriptEntity) {
        return false;
    }

    @Override
    public List<StrategyScriptEntity> queryAll() {

        return Arrays.asList(StrategyScriptEntity.builder()
                        .author("xiaoq")
                        .script("if(context.amount>=20000){\n" +
                                "            context.nextScenario='A'\n" +
                                "            return true\n" +
                                "        }\n" +
                                "        ")
                        .createTime(new Date())
                        .strategyId(1)
                        .variables("123")
                        .updateTime(new Date())
                        .build(),
                StrategyScriptEntity.builder()
                        .author("xiaoq")
                        .script("println JsonPath.parse(context).read(result)\n" +
                                "            return true ")
                        .createTime(new Date())
                        .strategyId(2)
                        .variables("123")
                        .updateTime(new Date())
                        .build()

        );
    }

}
