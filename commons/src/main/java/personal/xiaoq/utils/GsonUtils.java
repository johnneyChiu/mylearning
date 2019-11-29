package personal.xiaoq.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-01-14 12:35
 * @Version: V1.0.0
 */
public class GsonUtils{

    public static Gson gsonPrettyCreate() {
        return new GsonBuilder()
                .setLenient()// json宽松
                .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .serializeNulls() //智能null
                .setPrettyPrinting()// 调教格式
                .disableHtmlEscaping() //默认是GSON把HTML 转义的
                .enableComplexMapKeySerialization()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }
    public static Gson gsonNormalCreate() {
        return new GsonBuilder()
                .setLenient()// json宽松
                .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .serializeNulls() //智能null
                .disableHtmlEscaping() //默认是GSON把HTML 转义的
                .enableComplexMapKeySerialization()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

}
