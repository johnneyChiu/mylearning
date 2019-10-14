package personal.xiaoq.test;


import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import personal.xiaoq.demo.DemoBaseVisitor;
import personal.xiaoq.demo.DemoLexer;
import personal.xiaoq.demo.DemoParser;
import personal.xiaoq.hplsql.HplsqlBaseListener;
import personal.xiaoq.hplsql.HplsqlLexer;
import personal.xiaoq.hplsql.HplsqlListener;
import personal.xiaoq.hplsql.HplsqlParser;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-08-13 14:38
 * @Version: V1.0.0
 */
public class TestDemo {

    public static void run(String expr) throws Exception{

        //对每一个输入的字符串，构造一个 ANTLRStringStream 流 in
        ANTLRInputStream in = new ANTLRInputStream(expr);

        //用 in 构造词法分析器 lexer，词法分析的作用是产生记号
        HplsqlLexer lexer = new  HplsqlLexer(in);

        //用词法分析器 lexer 构造一个记号流 tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        //再使用 tokens 构造语法分析器 parser,至此已经完成词法分析和语法分析的准备工作
        HplsqlParser parser = new HplsqlParser(tokens);

        //最终调用语法分析器的规则 prog，完成对表达式的验证
        ParseTree tree = parser.block();
        /*HplsqlBaseVisitor eval = new HplsqlBaseVisitor();
        eval.visit(tree);
        System.out.println(tree.toStringTree(parser));*/
        HplsqlListener hplsqlListener = new HplsqlBaseListener();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(hplsqlListener, tree);
        System.out.println();
    }




    public static void run2(String expr) throws Exception{

        //对每一个输入的字符串，构造一个 ANTLRStringStream 流 in
        ANTLRInputStream in = new ANTLRInputStream(expr);

        //用 in 构造词法分析器 lexer，词法分析的作用是产生记号
        DemoLexer lexer = new DemoLexer(in);

        //用词法分析器 lexer 构造一个记号流 tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        //再使用 tokens 构造语法分析器 parser,至此已经完成词法分析和语法分析的准备工作
        DemoParser parser = new DemoParser(tokens);

        //最终调用语法分析器的规则 prog，完成对表达式的验证
        ParseTree tree = parser.prog();
        DemoBaseVisitor eval = new DemoBaseVisitor();
        eval.visit(tree);
        System.out.println(tree.toStringTree(parser));
    }


    public static void main(String[] args) throws Exception{

        String[] testStr = {
                "select a.prod,b.name,sum(price) from" +
                        " tableA as a left join tableB as b on a.id=b.id where a.age>10 group by a.prod,b.name"
        };

        for (String s:testStr){
            System.out.println("Input expr:"+s);
            run(s);
        }
    }

}
