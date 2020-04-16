package personal.xiaoq.realtime.schema;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-12-05 14:40
 * @Version: V1.0.0
 */
public class JsonSchema {

    public static  final String JSON_SCHEMA =" {\n" +
            "   \"definitions\": {},\n" +
            "   \"$schema\": \"http://json-schema.org/draft-07/schema#\",\n" +
            "   \"$id\": \"http://example.com/root.json\",\n" +
            "   \"type\": \"object\",\n" +
            "   \"title\": \"The Root Schema\",\n" +
            "   \"required\": [\n" +
            "     \"id\",\n" +
            "     \"database\",\n" +
            "     \"table\",\n" +
            "     \"pkNames\",\n" +
            "     \"isDdl\",\n" +
            "     \"type\",\n" +
            "     \"es\",\n" +
            "     \"ts\",\n" +
            "     \"sql\",\n" +
            "     \"sqlType\",\n" +
            "     \"mysqlType\",\n" +
            "     \"data\",\n" +
            "     \"old\"\n" +
            "   ],\n" +
            "   \"properties\": {\n" +
            "     \"id\": {\n" +
            "       \"$id\": \"#/properties/id\",\n" +
            "       \"type\": \"string\",\n" +
            "       \"title\": \"The Id Schema\",\n" +
            "       \"default\": \"\",\n" +
            "       \"examples\": [\n" +
            "         \"208\"\n" +
            "       ],\n" +
            "       \"pattern\": \"^(.*)$\"\n" +
            "     },\n" +
            "     \"database\": {\n" +
            "       \"$id\": \"#/properties/database\",\n" +
            "       \"type\": \"string\",\n" +
            "       \"title\": \"The Database Schema\",\n" +
            "       \"default\": \"\",\n" +
            "       \"examples\": [\n" +
            "         \"xiaoqTest\"\n" +
            "       ],\n" +
            "       \"pattern\": \"^(.*)$\"\n" +
            "     },\n" +
            "     \"table\": {\n" +
            "       \"$id\": \"#/properties/table\",\n" +
            "       \"type\": \"string\",\n" +
            "       \"title\": \"The Table Schema\",\n" +
            "       \"default\": \"\",\n" +
            "       \"examples\": [\n" +
            "         \"t_base_info\"\n" +
            "       ],\n" +
            "       \"pattern\": \"^(.*)$\"\n" +
            "     },\n" +
            "     \"pkNames\": {\n" +
            "       \"$id\": \"#/properties/pkNames\",\n" +
            "       \"type\": \"array\",\n" +
            "       \"title\": \"The Pknames Schema\",\n" +
            "       \"items\": {\n" +
            "         \"$id\": \"#/properties/pkNames/items\",\n" +
            "         \"type\": \"string\",\n" +
            "         \"title\": \"The Items Schema\",\n" +
            "         \"default\": \"\",\n" +
            "         \"examples\": [\n" +
            "           \"id\"\n" +
            "         ],\n" +
            "         \"pattern\": \"^(.*)$\"\n" +
            "       }\n" +
            "     },\n" +
            "     \"isDdl\": {\n" +
            "       \"$id\": \"#/properties/isDdl\",\n" +
            "       \"type\": \"boolean\",\n" +
            "       \"title\": \"The Isddl Schema\",\n" +
            "       \"default\": false,\n" +
            "       \"examples\": [\n" +
            "         false\n" +
            "       ]\n" +
            "     },\n" +
            "     \"type\": {\n" +
            "       \"$id\": \"#/properties/type\",\n" +
            "       \"type\": \"string\",\n" +
            "       \"title\": \"The Type Schema\",\n" +
            "       \"default\": \"\",\n" +
            "       \"examples\": [\n" +
            "         \"UPDATE\"\n" +
            "       ],\n" +
            "       \"pattern\": \"^(.*)$\"\n" +
            "     },\n" +
            "     \"es\": {\n" +
            "       \"$id\": \"#/properties/es\",\n" +
            "       \"type\": \"string\",\n" +
            "       \"title\": \"The Es Schema\",\n" +
            "       \"default\": \"\",\n" +
            "       \"examples\": [\n" +
            "         \"1575256778000\"\n" +
            "       ],\n" +
            "       \"pattern\": \"^(.*)$\"\n" +
            "     },\n" +
            "     \"ts\": {\n" +
            "       \"$id\": \"#/properties/ts\",\n" +
            "       \"type\": \"string\",\n" +
            "       \"title\": \"The Ts Schema\",\n" +
            "       \"default\": \"\",\n" +
            "       \"examples\": [\n" +
            "         \"1575256778733\"\n" +
            "       ],\n" +
            "       \"pattern\": \"^(.*)$\"\n" +
            "     },\n" +
            "     \"sql\": {\n" +
            "       \"$id\": \"#/properties/sql\",\n" +
            "       \"type\": \"string\",\n" +
            "       \"title\": \"The Sql Schema\",\n" +
            "       \"default\": \"\",\n" +
            "       \"examples\": [\n" +
            "         \"\"\n" +
            "       ],\n" +
            "       \"pattern\": \"^(.*)$\"\n" +
            "     },\n" +
            "     \"sqlType\": {\n" +
            "       \"$id\": \"#/properties/sqlType\",\n" +
            "       \"type\": \"object\",\n" +
            "       \"title\": \"The Sqltype Schema\",\n" +
            "       \"required\": [\n" +
            "         \"id\",\n" +
            "         \"name\",\n" +
            "         \"id_card\",\n" +
            "         \"score\",\n" +
            "         \"capital\",\n" +
            "         \"create_time\",\n" +
            "         \"update_time\"\n" +
            "       ],\n" +
            "       \"properties\": {\n" +
            "         \"id\": {\n" +
            "           \"$id\": \"#/properties/sqlType/properties/id\",\n" +
            "           \"type\": \"integer\",\n" +
            "           \"title\": \"The Id Schema\",\n" +
            "           \"default\": 0,\n" +
            "           \"examples\": [\n" +
            "             4\n" +
            "           ]\n" +
            "         },\n" +
            "         \"name\": {\n" +
            "           \"$id\": \"#/properties/sqlType/properties/name\",\n" +
            "           \"type\": \"integer\",\n" +
            "           \"title\": \"The Name Schema\",\n" +
            "           \"default\": 0,\n" +
            "           \"examples\": [\n" +
            "             12\n" +
            "           ]\n" +
            "         },\n" +
            "         \"id_card\": {\n" +
            "           \"$id\": \"#/properties/sqlType/properties/id_card\",\n" +
            "           \"type\": \"integer\",\n" +
            "           \"title\": \"The Id_card Schema\",\n" +
            "           \"default\": 0,\n" +
            "           \"examples\": [\n" +
            "             12\n" +
            "           ]\n" +
            "         },\n" +
            "         \"score\": {\n" +
            "           \"$id\": \"#/properties/sqlType/properties/score\",\n" +
            "           \"type\": \"integer\",\n" +
            "           \"title\": \"The Score Schema\",\n" +
            "           \"default\": 0,\n" +
            "           \"examples\": [\n" +
            "             3\n" +
            "           ]\n" +
            "         },\n" +
            "         \"capital\": {\n" +
            "           \"$id\": \"#/properties/sqlType/properties/capital\",\n" +
            "           \"type\": \"integer\",\n" +
            "           \"title\": \"The Capital Schema\",\n" +
            "           \"default\": 0,\n" +
            "           \"examples\": [\n" +
            "             3\n" +
            "           ]\n" +
            "         },\n" +
            "         \"create_time\": {\n" +
            "           \"$id\": \"#/properties/sqlType/properties/create_time\",\n" +
            "           \"type\": \"integer\",\n" +
            "           \"title\": \"The Create_time Schema\",\n" +
            "           \"default\": 0,\n" +
            "           \"examples\": [\n" +
            "             93\n" +
            "           ]\n" +
            "         },\n" +
            "         \"update_time\": {\n" +
            "           \"$id\": \"#/properties/sqlType/properties/update_time\",\n" +
            "           \"type\": \"integer\",\n" +
            "           \"title\": \"The Update_time Schema\",\n" +
            "           \"default\": 0,\n" +
            "           \"examples\": [\n" +
            "             93\n" +
            "           ]\n" +
            "         }\n" +
            "       }\n" +
            "     },\n" +
            "     \"mysqlType\": {\n" +
            "       \"$id\": \"#/properties/mysqlType\",\n" +
            "       \"type\": \"object\",\n" +
            "       \"title\": \"The Mysqltype Schema\",\n" +
            "       \"required\": [\n" +
            "         \"id\",\n" +
            "         \"name\",\n" +
            "         \"id_card\",\n" +
            "         \"score\",\n" +
            "         \"capital\",\n" +
            "         \"create_time\",\n" +
            "         \"update_time\"\n" +
            "       ],\n" +
            "       \"properties\": {\n" +
            "         \"id\": {\n" +
            "           \"$id\": \"#/properties/mysqlType/properties/id\",\n" +
            "           \"type\": \"string\",\n" +
            "           \"title\": \"The Id Schema\",\n" +
            "           \"default\": \"\",\n" +
            "           \"examples\": [\n" +
            "             \"int(0)\"\n" +
            "           ],\n" +
            "           \"pattern\": \"^(.*)$\"\n" +
            "         },\n" +
            "         \"name\": {\n" +
            "           \"$id\": \"#/properties/mysqlType/properties/name\",\n" +
            "           \"type\": \"string\",\n" +
            "           \"title\": \"The Name Schema\",\n" +
            "           \"default\": \"\",\n" +
            "           \"examples\": [\n" +
            "             \"varchar(255)\"\n" +
            "           ],\n" +
            "           \"pattern\": \"^(.*)$\"\n" +
            "         },\n" +
            "         \"id_card\": {\n" +
            "           \"$id\": \"#/properties/mysqlType/properties/id_card\",\n" +
            "           \"type\": \"string\",\n" +
            "           \"title\": \"The Id_card Schema\",\n" +
            "           \"default\": \"\",\n" +
            "           \"examples\": [\n" +
            "             \"varchar(255)\"\n" +
            "           ],\n" +
            "           \"pattern\": \"^(.*)$\"\n" +
            "         },\n" +
            "         \"score\": {\n" +
            "           \"$id\": \"#/properties/mysqlType/properties/score\",\n" +
            "           \"type\": \"string\",\n" +
            "           \"title\": \"The Score Schema\",\n" +
            "           \"default\": \"\",\n" +
            "           \"examples\": [\n" +
            "             \"decimal(10,2)\"\n" +
            "           ],\n" +
            "           \"pattern\": \"^(.*)$\"\n" +
            "         },\n" +
            "         \"capital\": {\n" +
            "           \"$id\": \"#/properties/mysqlType/properties/capital\",\n" +
            "           \"type\": \"string\",\n" +
            "           \"title\": \"The Capital Schema\",\n" +
            "           \"default\": \"\",\n" +
            "           \"examples\": [\n" +
            "             \"decimal(30,2)\"\n" +
            "           ],\n" +
            "           \"pattern\": \"^(.*)$\"\n" +
            "         },\n" +
            "         \"create_time\": {\n" +
            "           \"$id\": \"#/properties/mysqlType/properties/create_time\",\n" +
            "           \"type\": \"string\",\n" +
            "           \"title\": \"The Create_time Schema\",\n" +
            "           \"default\": \"\",\n" +
            "           \"examples\": [\n" +
            "             \"datetime(6)\"\n" +
            "           ],\n" +
            "           \"pattern\": \"^(.*)$\"\n" +
            "         },\n" +
            "         \"update_time\": {\n" +
            "           \"$id\": \"#/properties/mysqlType/properties/update_time\",\n" +
            "           \"type\": \"string\",\n" +
            "           \"title\": \"The Update_time Schema\",\n" +
            "           \"default\": \"\",\n" +
            "           \"examples\": [\n" +
            "             \"datetime(6)\"\n" +
            "           ],\n" +
            "           \"pattern\": \"^(.*)$\"\n" +
            "         }\n" +
            "       }\n" +
            "     },\n" +
            "     \"data\": {\n" +
            "       \"$id\": \"#/properties/data\",\n" +
            "       \"type\": \"array\",\n" +
            "       \"title\": \"The Data Schema\",\n" +
            "       \"items\": {\n" +
            "         \"$id\": \"#/properties/data/items\",\n" +
            "         \"type\": \"object\",\n" +
            "         \"title\": \"The Items Schema\",\n" +
            "         \"required\": [\n" +
            "           \"id\",\n" +
            "           \"name\",\n" +
            "           \"id_card\",\n" +
            "           \"score\",\n" +
            "           \"capital\",\n" +
            "           \"create_time\",\n" +
            "           \"update_time\"\n" +
            "         ],\n" +
            "         \"properties\": {\n" +
            "           \"id\": {\n" +
            "             \"$id\": \"#/properties/data/items/properties/id\",\n" +
            "             \"type\": \"string\",\n" +
            "             \"title\": \"The Id Schema\",\n" +
            "             \"default\": \"\",\n" +
            "             \"examples\": [\n" +
            "               \"1\"\n" +
            "             ],\n" +
            "             \"pattern\": \"^(.*)$\"\n" +
            "           },\n" +
            "           \"name\": {\n" +
            "             \"$id\": \"#/properties/data/items/properties/name\",\n" +
            "             \"type\": \"string\",\n" +
            "             \"title\": \"The Name Schema\",\n" +
            "             \"default\": \"\",\n" +
            "             \"examples\": [\n" +
            "               \"xiaoq\"\n" +
            "             ],\n" +
            "             \"pattern\": \"^(.*)$\"\n" +
            "           },\n" +
            "           \"id_card\": {\n" +
            "             \"$id\": \"#/properties/data/items/properties/id_card\",\n" +
            "             \"type\": \"string\",\n" +
            "             \"title\": \"The Id_card Schema\",\n" +
            "             \"default\": \"\",\n" +
            "             \"examples\": [\n" +
            "               \"500223\"\n" +
            "             ],\n" +
            "             \"pattern\": \"^(.*)$\"\n" +
            "           },\n" +
            "           \"score\": {\n" +
            "             \"$id\": \"#/properties/data/items/properties/score\",\n" +
            "             \"type\": \"string\",\n" +
            "             \"title\": \"The Score Schema\",\n" +
            "             \"default\": \"\",\n" +
            "             \"examples\": [\n" +
            "               \"100.66\"\n" +
            "             ],\n" +
            "             \"pattern\": \"^(.*)$\"\n" +
            "           },\n" +
            "           \"capital\": {\n" +
            "             \"$id\": \"#/properties/data/items/properties/capital\",\n" +
            "             \"type\": \"null\",\n" +
            "             \"title\": \"The Capital Schema\",\n" +
            "             \"default\": null,\n" +
            "             \"examples\": [\n" +
            "               null\n" +
            "             ]\n" +
            "           },\n" +
            "           \"create_time\": {\n" +
            "             \"$id\": \"#/properties/data/items/properties/create_time\",\n" +
            "             \"type\": \"string\",\n" +
            "             \"title\": \"The Create_time Schema\",\n" +
            "             \"default\": \"\",\n" +
            "             \"examples\": [\n" +
            "               \"2019-10-23 19:27:44.000000\"\n" +
            "             ],\n" +
            "             \"pattern\": \"^(.*)$\"\n" +
            "           },\n" +
            "           \"update_time\": {\n" +
            "             \"$id\": \"#/properties/data/items/properties/update_time\",\n" +
            "             \"type\": \"string\",\n" +
            "             \"title\": \"The Update_time Schema\",\n" +
            "             \"default\": \"\",\n" +
            "             \"examples\": [\n" +
            "               \"2019-10-23 19:27:47.000000\"\n" +
            "             ],\n" +
            "             \"pattern\": \"^(.*)$\"\n" +
            "           }\n" +
            "         }\n" +
            "       }\n" +
            "     },\n" +
            "     \"old\": {\n" +
            "       \"$id\": \"#/properties/old\",\n" +
            "       \"type\": \"array\",\n" +
            "       \"title\": \"The Old Schema\",\n" +
            "       \"items\": {\n" +
            "         \"$id\": \"#/properties/old/items\",\n" +
            "         \"type\": \"object\",\n" +
            "         \"title\": \"The Items Schema\",\n" +
            "         \"required\": [\n" +
            "           \"score\"\n" +
            "         ],\n" +
            "         \"properties\": {\n" +
            "           \"score\": {\n" +
            "             \"$id\": \"#/properties/old/items/properties/score\",\n" +
            "             \"type\": \"string\",\n" +
            "             \"title\": \"The Score Schema\",\n" +
            "             \"default\": \"\",\n" +
            "             \"examples\": [\n" +
            "               \"100.55\"\n" +
            "             ],\n" +
            "             \"pattern\": \"^(.*)$\"\n" +
            "           }\n" +
            "         }\n" +
            "       }\n" +
            "     }\n" +
            "   }\n" +
            " }";
}
