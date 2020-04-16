package personal.xiaoq.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-01-19 11:15
 * @Version: V1.0.0
 */
@Resource(name = "test")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Test implements InitializingBean, DisposableBean {
    private int id;
    private String name;


    @Override
    public void destroy() throws Exception {
        System.out.println(this.name+"被销毁了");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this.name+"被初始化了");
    }
}
