package personal.xiaoq.realtime.map;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.RichMapFunction;


@Slf4j
public class SourceMap<T> extends RichMapFunction<String, T> {
    @Override
    public T map(String s) throws Exception {
        return null;
    }

    /*

    private Class<T> clazz;

    public SourceMap(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T map(String value) {
        T t = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject event = parser.parse(value).getAsJsonObject();
            String type = event.get("type").getAsString();
            JsonObject data = event.get("data").getAsJsonArray().get(0).getAsJsonObject();
            String dataJson = data.toString();
            t=GsonUtils.gsonCreate().fromJson(dataJson, clazz);
            switch (type) {
                case "INSERT":
                    setOperationType(t, 0);
                    break;
                case "UPDATE":
                    setOperationType(t, 1);
                    if (t instanceof OsFdlInfoEntity) {
                        JsonObject old = event.get("old").getAsJsonArray().get(0).getAsJsonObject();
                        if (old.get("capital") != null) {  //如果修改是金额字段 则求更新前后的差值
                            BigDecimal oldCapital = old.get("capital").getAsBigDecimal();
                            BigDecimal newCapital = ((OsFdlInfoEntity) t).getCapital();
                            ((OsFdlInfoEntity) t).setCapital(newCapital.subtract(oldCapital));
                        }
                    }
                    break;
                case "DELETE":
                    setOperationType(t, 2);
                    //如果删除了一条交易记录，则交易金额 取反 计算
                    if (t instanceof OsFdlInfoEntity) {
                        ((OsFdlInfoEntity) t).setCapital(((OsFdlInfoEntity) t).getCapital().multiply(new BigDecimal(-1)));
                    }
                    break;
            }
        } catch (Exception e) {
            log.error("异常记录 : {}", value);
            log.error("source map 异常: {}", e);
            return t;
        }
        return t;
    }

    public T setOperationType(T t, int operationType) {
        if (t instanceof OsFdlInfoEntity) {
            OsFdlInfoEntity obj = (OsFdlInfoEntity) t;
            obj.setOperationType(operationType);
            t = (T) obj;
        }

        if (t instanceof TbCustEntity) {
            TbCustEntity obj = (TbCustEntity) t;
            obj.setOperationType(operationType);
            t = (T) obj;
        }
        if (t instanceof TbEleAcctEntity) {
            TbEleAcctEntity obj = (TbEleAcctEntity) t;
            obj.setOperationType(operationType);
            t = (T) obj;
        }
        return t;
    }
*/


}
