package personal.xiaoq.plsql;

import java.util.List;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-08-29 11:07
 * @Version: V1.0.0
 */
public class MyDefineVisitor<String> extends PlSqlBaseVisitor<String>{


    @Override
    public String visitSql_script(PlSqlParser.Sql_scriptContext ctx) {
        return super.visitSql_script(ctx);
    }

    @Override
    public String visitSql_statement(PlSqlParser.Sql_statementContext ctx) {
        return super.visitSql_statement(ctx);
    }

    @Override
    public String visitInsert_into_clause(PlSqlParser.Insert_into_clauseContext ctx) {
        System.out.println(ctx.INTO().toString());
        List<PlSqlParser.Column_nameContext> list = ctx.column_name();
        list.stream().forEach(x->System.out.println(x.id().getText()));

        return super.visitInsert_into_clause(ctx);
    }

    @Override
    public String visitInto_clause_variable(PlSqlParser.Into_clause_variableContext ctx) {
        System.out.println(ctx.variable_name());
        return super.visitInto_clause_variable(ctx);
    }

    @Override
    public String visitInto_clause(PlSqlParser.Into_clauseContext ctx) {

        return super.visitInto_clause(ctx);
    }
}
