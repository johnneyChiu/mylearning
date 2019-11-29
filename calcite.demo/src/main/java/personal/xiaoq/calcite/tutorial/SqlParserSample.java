package personal.xiaoq.calcite.tutorial;

import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;

/**
 * @author quxiucheng
 * @date 2019-04-22 11:45:00
 */
public class SqlParserSample {

    public static void main(String[] args) throws SqlParseException {
        parserSql();
        //parserExp();

    }

    /**
     * 解析SQL
     * @throws SqlParseException
     */
    public static void parserSql() throws SqlParseException {
        // 解析配置 - mysql设置
        SqlParser.Config mysqlConfig = SqlParser.configBuilder().setLex(Lex.MYSQL).build();
        // 创建解析器
        SqlParser parser = SqlParser.create("", mysqlConfig);
        // Sql语句
        String sql = "select a.prod,b.name,sum(price) from  tableA a join (select name,count(name) from es where sad>100 group by name)  b on a.id=b.id where a.age>10 group by a.prod,b.name";
        //String sql = "delete from tableA where a>10";
        // 解析sql
        //SqlNode sqlNode = parser.parseQuery(sql);
        // 还原某个方言的SQL
        /*//System.out.println(sqlNode.toSqlString(OracleSqlDialect.DEFAULT));
        final SqlNodeList selectList = ((SqlSelect) sqlNode).getSelectList();
        for (int i = 0; i < selectList.size(); i++) {
            SqlNode selectItem = selectList.get(i);
            String name = null;
            if (SqlUtil.isCallTo(
                    selectItem,
                    SqlStdOperatorTable.AS)) {
                final SqlCall call = (SqlCall) selectItem;
                selectItem = call.operand(0);
                name = call.operand(1).toString();
            }
            *//*if (name == null) {
                name = validator.deriveAlias(selectItem, i);
            }
            nameMap.put(selectItem.toString(), name);*//*
            System.out.println(String.format("字段:%s 名称:%s", selectItem.toSqlString(OracleSqlDialect.DEFAULT), name));
        }*/


    }

    /**
     * 解析表达式
     * @throws SqlParseException
     */
   /* public static void parserExp() throws SqlParseException {
        // 解析配置 - mysql设置
        SqlParser.Config mysqlConfig = SqlParser.configBuilder().setLex(Lex.MYSQL).build();
        String exp = "id = 1 and name='1'";
        SqlParser expressionParser = SqlParser.create(exp, mysqlConfig);
        // Sql语句
        // 解析sql
        SqlNode sqlNode = expressionParser.parseExpression();
        // 还原某个方言的SQL
        System.out.println(sqlNode.toSqlString(OracleSqlDialect.DEFAULT));
    }*/







}
