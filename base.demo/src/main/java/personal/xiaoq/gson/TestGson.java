package personal.xiaoq.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import personal.xiaoq.utils.GsonUtils;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-11-14 16:08
 * @Version: V1.0.0
 */
public class TestGson {
    public static void main(String[] args) {
        Person person = Person.builder().id(123).name("xiaoq").build();
        Gson gson = new GsonBuilder().setLenient().enableComplexMapKeySerialization()
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .serializeNulls()

                .create();
        Gson gson2 = GsonUtils.gsonPrettyCreate();
        Gson gson3 = new Gson();

        System.out.println(gson.toJson(person));
        System.out.println(gson2.toJson(person));
        System.out.println(gson3.toJson(person));

    }
}

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
class Person{
    private int id;
    private String name;
}
