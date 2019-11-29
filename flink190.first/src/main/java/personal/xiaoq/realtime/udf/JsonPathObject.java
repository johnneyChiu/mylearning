package personal.xiaoq.realtime.udf;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang.StringUtils;
import org.apache.flink.table.functions.ScalarFunction;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-11-18 14:36
 * @Version: V1.0.0
 */
public class JsonPathObject extends ScalarFunction {

    public String eval(String jsonStr,String jPath){
        String res=null;

        if(StringUtils.isBlank(jsonStr)&&StringUtils.isBlank(jPath))
            return res;
        if(JsonPath.isPathDefinite(jPath))
            res=JsonPath.read(jsonStr, jPath);


        return res;
    }



}
