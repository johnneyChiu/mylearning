package personal.xiaoq.realtime.descriptors;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.table.descriptors.DescriptorProperties;
import org.apache.flink.table.descriptors.FormatDescriptor;
import org.apache.flink.table.utils.TypeStringUtils;
import org.apache.flink.types.Row;
import org.apache.flink.util.Preconditions;

import java.util.Map;

import static personal.xiaoq.realtime.descriptors.BinlogValidator.*;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description: binlog descriptor
 * @Date: Created on 2019-11-19 17:16
 * @Version: V1.0.0
 */
public class BinlogDescriptor extends FormatDescriptor {

    private Boolean failOnMissingField;
    private Boolean deriveSchema;
    private String binlogSchema;
    private String schema;


    public BinlogDescriptor() {
        super(FORMAT_TYPE_VALUE,1);
    }

    /**
     *
     * @param failOnMissingField failOnMissingField If set to true, the operation fails if there is a missing field.
     * 	                         If set to false, a missing field is set to null.
     * @return
     */
    public BinlogDescriptor failOnMissingField(boolean failOnMissingField) {
        this.failOnMissingField = failOnMissingField;
        return this;
    }


    public BinlogDescriptor binlogSchema(String binlogSchema){
        Preconditions.checkNotNull(binlogSchema);
        this.binlogSchema = binlogSchema;
        this.schema = null;
        this.deriveSchema = null;
        return this;

    }

    //TODO TypeStringUtils
    public BinlogDescriptor schema(TypeInformation<Row> schemaType){
        Preconditions.checkNotNull(schemaType);
        this.schema = TypeStringUtils.writeTypeInfo(schemaType);
        this.binlogSchema = null;
        this.deriveSchema = null;
        return this;
    }

    public BinlogDescriptor deviceSchema(){
        this.deriveSchema = true;
        this.schema = null;
        this.binlogSchema = null;
        return this;
    }


    @Override
    protected Map<String, String> toFormatProperties() {
        final DescriptorProperties properties = new DescriptorProperties();

        if (deriveSchema != null) {
            properties.putBoolean(FORMAT_DERIVE_SCHEMA, deriveSchema);
        }

        if (binlogSchema != null) {
            properties.putString(FORMAT_BINLOG_SCHEMA, binlogSchema);
        }

        if (schema != null) {
            properties.putString(FORMAT_SCHEMA, schema);
        }

        if (failOnMissingField != null) {
            properties.putBoolean(FORMAT_FAIL_ON_MISSING_FIELD, failOnMissingField);
        }

        return properties.asMap();
    }


}
