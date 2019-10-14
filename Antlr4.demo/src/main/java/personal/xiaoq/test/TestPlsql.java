package personal.xiaoq.test;


import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import personal.xiaoq.plsql.*;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-08-26 17:57
 * @Version: V1.0.0
 */
public class TestPlsql {

    public static void main(String[] args) throws IOException {
        //System.out.println(TestPlsql.class.getResource(""));
        ANTLRInputStream input = new ANTLRInputStream(
                new FileInputStream("D:\\personal\\github\\mylearning\\Antlr4.demo\\src\\main\\resources\\PR_CL_INVOICE_TBL.sql"));

        PlSqlLexer lexer = new PlSqlLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PlSqlParser parser = new PlSqlParser(tokens);
        parser.setBuildParseTree(true);
        ParseTree tree = parser.sql_script();

        System.out.println(tree.toStringTree(parser));

        //listener 遍历
        ParseTreeWalker walker=new ParseTreeWalker();
        PlSqlBaseListener plSqlBaseListener = new PlSqlBaseListener();
        //walker.walk(plSqlBaseListener, tree);


        //visitor遍历
        PlSqlBaseVisitor plSqlBaseVisitor = new MyDefineVisitor();
        plSqlBaseVisitor.visit(tree);




      /*  STGroupFile stg = new STGroupFile("test.stg");
        stg.registerModelAdaptor(ParserRuleContext.class, new ContextModelAdapter());
        ST t = stg.getInstanceOf("plsql_block");
        t.add("block", tree);
        System.out.println(t.render());*/

    }

}
