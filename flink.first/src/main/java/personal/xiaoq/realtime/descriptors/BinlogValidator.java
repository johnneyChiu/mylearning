package personal.xiaoq.realtime.descriptors;

import lombok.NoArgsConstructor;
import org.apache.flink.table.descriptors.DescriptorProperties;
import org.apache.flink.table.descriptors.FormatDescriptorValidator;
import org.apache.flink.table.api.ValidationException;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-11-20 16:50
 * @Version: V1.0.0
 */
@NoArgsConstructor
public class BinlogValidator extends FormatDescriptorValidator {

    public static final String FORMAT_TYPE_VALUE = "binlog";
    public static final String FORMAT_SCHEMA = "format.schema";
    public static final String FORMAT_BINLOG_SCHEMA = "format.binlog-schema";
    public static final String FORMAT_FAIL_ON_MISSING_FIELD = "format.fail-on-missing-field";


    @Override
    public void validate(DescriptorProperties properties) {
        super.validate(properties);
        properties.validateBoolean(FORMAT_DERIVE_SCHEMA, true);

        final boolean deriveSchema = properties.getOptionalBoolean(FORMAT_DERIVE_SCHEMA).orElse(false);
        final boolean hasSchema = properties.containsKey(FORMAT_SCHEMA);
        final boolean hasSchemaString = properties.containsKey(FORMAT_BINLOG_SCHEMA);
        if (deriveSchema && (hasSchema || hasSchemaString)) {
            throw new ValidationException(
                    "Format cannot define a schema and derive from the table's schema at the same time.");
        } else if (!deriveSchema && hasSchema && hasSchemaString) {
            throw new ValidationException("A definition of both a schema and JSON schema is not allowed.");
        } else if (!deriveSchema && !hasSchema && !hasSchemaString) {
            throw new ValidationException("A definition of a schema or JSON schema is required.");
        } else if (hasSchema) {
            properties.validateType(FORMAT_SCHEMA, true, false);
        } else if (hasSchemaString) {
            properties.validateString(FORMAT_BINLOG_SCHEMA, false, 1);
        }

        properties.validateBoolean(FORMAT_FAIL_ON_MISSING_FIELD, true);
    }
}
