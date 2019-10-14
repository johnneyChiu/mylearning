package personal.xiaoq.utils;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-01-17 16:14
 * @Version: V1.0.0
 */
public class BigDataTopicCreate {

    public static String generateTopicName(String pre,String schema,String table){
        return String.format("%s%s_%s", pre, schema, table);
    }
}
