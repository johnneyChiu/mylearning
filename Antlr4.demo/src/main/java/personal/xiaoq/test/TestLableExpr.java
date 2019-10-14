package personal.xiaoq.test;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import personal.xiaoq.lableexpr.EvalLableExprVisitor;
import personal.xiaoq.lableexpr.LabledExprLexer;
import personal.xiaoq.lableexpr.LabledExprParser;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-09-02 17:14
 * @Version: V1.0.0
 */
public class TestLableExpr {

    public static void main(String[] args) {
        CharStream input= CharStreams.fromString("10*(10+10);");

        LabledExprLexer labledExprLexer = new LabledExprLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(labledExprLexer);

        LabledExprParser labledExprParser = new LabledExprParser(tokens);
        ParseTree tree = labledExprParser.prog();

        System.out.println(tree.toStringTree(labledExprParser));

        //visitor遍历
        EvalLableExprVisitor visitor = new EvalLableExprVisitor();
        visitor.visit(tree);


    }

}
