package personal.xiaoq.calcite.tutorial;

import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.dialect.OracleSqlDialect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;

/**
 * @author quxiucheng
 * @date 2019-04-22 11:45:00
 */
public class SqlParserHiveSample {

    public static void main(String[] args) throws SqlParseException {
        parserSql();
       // parserExp();

    }

    /**
     * 解析SQL
     * @throws SqlParseException
     */
    public static void parserSql() throws SqlParseException {
        // 解析配置 - oracle设置
        SqlParser.Config config = SqlParser.configBuilder().setLex(Lex.ORACLE).build();
        // 创建解析器
        SqlParser parser = SqlParser.create("", config);
        // Sql语句
        String sql = "select a,b,max(a,b) as c from emps where id = 1";
        // 解析sql
        SqlNode sqlNode = parser.parseQuery(sql);

        // 还原某个方言的SQL
        System.out.println(sqlNode.toSqlString(OracleSqlDialect.DEFAULT));


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


/*


    public AggConverter(Blackboard bb, SqlSelect select) {
        this.bb = bb;
        this.aggregatingSelectScope =
                (AggregatingSelectScope) bb.getValidator().getSelectScope(select);

        // Collect all expressions used in the select list so that aggregate
        // calls can be named correctly.
        final SqlNodeList selectList = select.getSelectList();
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
            if (name == null) {
                name = validator.deriveAlias(selectItem, i);
            }
            nameMap.put(selectItem.toString(), name);
        }
    }
*/




}
