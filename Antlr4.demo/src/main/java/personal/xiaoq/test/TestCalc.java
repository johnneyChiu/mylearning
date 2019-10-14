package personal.xiaoq.test;


import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import personal.xiaoq.calc.CalcLexer;
import personal.xiaoq.calc.CalcParser;
import personal.xiaoq.calc.EvalVisitor;

import java.io.InputStream;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-08-28 16:10
 * @Version: V1.0.0
 */
public class TestCalc {

    public static CharStream calTest(String[] args) throws Exception {
        CharStream input;
        if(args.length == 1) {
            String fileName = String.valueOf(args[0]);
            input = CharStreams.fromFileName(fileName);
        }else if(args.length > 1 || args.length < 0){
            throw  new Exception("the number of arguments is false, Please only give the source file or nothing and then you input your text");
        }else {
            InputStream is = System.in;
            input = CharStreams.fromStream(is);
        }
        return input;

    }


    public static CharStream calTest()  {
        return CharStreams.fromString("10 + (10*10)");


    }

    public static void main(String[] args) throws Exception {

        CalcLexer lexer = new CalcLexer(calTest());
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CalcParser parser = new CalcParser(tokens);
        ParseTree tree = parser.prog();
        EvalVisitor eval = new EvalVisitor();
        eval.visit(tree);
        System.out.println(tree.toStringTree(parser));
    }

}
