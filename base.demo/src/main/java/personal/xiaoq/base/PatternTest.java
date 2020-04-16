package personal.xiaoq.base;

import java.util.regex.Pattern;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-04-10 16:41
 * @Version: V1.0.0
 */
public class PatternTest {
    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        String test = "^(([^<>()\\[\\]\\.,;:\\s@\"\"]+(\\.[^<>()\\[\\]\\.,;:\\s@\"\"]+)*)|(\"\".+\"\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        boolean b = Pattern.matches(test, "110120180122000500000002");
        System.out.println(String.format("test result %b ,time :%d ",test,System.currentTimeMillis()-l));
    }
}
