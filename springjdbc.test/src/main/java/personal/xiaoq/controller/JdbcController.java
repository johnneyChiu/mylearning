package personal.xiaoq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-09-17 17:57
 * @Version: V1.0.0
 */
@RestController
@RequestMapping("/jdbc")
public class JdbcController {


    @Autowired
    JdbcTemplate jdbcTemplate;
    @GetMapping("/list")
    public List<Map<String, Object>> queryUsers(){
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from oauth_client_details");
        return list;
    }

}
