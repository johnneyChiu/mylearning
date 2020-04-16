package personal.xiaoq.common;

import org.springframework.util.DigestUtils;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-01-15 17:25
 * @Version: V1.0.0
 */
public class MD5Utils {
    public static String getStringMD5(String input){
        //TODO ADD MD5 Conversion
        return DigestUtils.md5DigestAsHex(input.getBytes());
    }
}
