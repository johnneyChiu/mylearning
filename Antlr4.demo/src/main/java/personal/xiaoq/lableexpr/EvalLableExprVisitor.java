package personal.xiaoq.lableexpr;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-09-02 17:53
 * @Version: V1.0.0
 */
public class EvalLableExprVisitor extends LabledExprBaseVisitor<Double> {


    Map<String, Double> memory = new HashMap<String, Double>();


    // ID '=' expr ';'
    @Override
    public Double visitAssign(LabledExprParser.AssignContext ctx){
        String id = ctx.ID().getText();
        Double value = visit(ctx.expr());
        memory.put(id, value);
        return value;
    }

    // expr ';'
    @Override
    public Double visitCal_stat(LabledExprParser.Cal_statContext ctx){
        Double value = visit(ctx.expr());
        return value;
    }

    // 'print' '(' expr ')' ';'
    @Override
    public Double visitPrint(LabledExprParser.PrintContext ctx){
        Double value = visit(ctx.expr());
        DecimalFormat df = new DecimalFormat("#.###");
        System.out.println(df.format(value));
        return value;
    }

    // <assco=right>expr POW expr
    @Override
    public Double visitPow_expr(LabledExprParser.Pow_exprContext ctx){
        Double truth = visit(ctx.expr(0));
        Double power = visit(ctx.expr(1));
        return Math.pow(truth, power);
    }

    // expr op=(MUL|DIV) expr
    @Override
    public Double visitMd_expr(LabledExprParser.Md_exprContext ctx){
        Double left = visit(ctx.expr(0));
        Double right = visit(ctx.expr(1));
        if (ctx.op.getType() == LabledExprParser.MUL) return left * right;
        else if (ctx.op.getType() == LabledExprParser.DIV){
            if (new BigDecimal(right).compareTo(new BigDecimal(0.0)) == 0){
                return 0.0;
            }
            return left / right;
        }
        else {
            int left_int = (new Double(left)).intValue();
            int right_int = (new Double(right).intValue());
            if (new BigDecimal(right_int).compareTo(new BigDecimal(right)) == 0
                    && new BigDecimal(left_int).compareTo(new BigDecimal(left_int)) == 0){
                return Double.valueOf(left_int % right_int);
            }
            System.out.println("Mod\'%\' operations should be integer.");
            return 0.0;
        }
    }

    // expr op=(ADD|SUB) expr
    @Override
    public Double visitAs_expr(LabledExprParser.As_exprContext ctx){
        Double left = visit(ctx.expr(0));
        Double right = visit(ctx.expr(1));
        if (ctx.op.getType() == LabledExprParser.ADD) return left + right;
        else return left - right;
    }

    // sign=(ADD|SUB)? NUM
    @Override
    public Double visitNumber(LabledExprParser.NumberContext ctx){
        int child = ctx.getChildCount();
        Double value = Double.valueOf(ctx.NUM().getText());
        if (child == 2 && ctx.sign.getType() == LabledExprParser.SUB){
            return 0 - value;
        }
        return value;
    }

    // ID
    @Override
    public Double visitId(LabledExprParser.IdContext ctx){
        String id = ctx.ID().getText();
        if (memory.containsKey(id)) {
            return memory.get(id);
        }
        System.out.println("undefined identifier \"" + id + "\".");
        return 0.0;
    }

    //'(' expr ')'
    @Override
    public Double visitParens(LabledExprParser.ParensContext ctx){
        Double value = visit(ctx.expr());
        return value;
    }
}
