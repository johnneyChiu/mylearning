// Generated from D:/personal/github/mylearning/Antlr4.demo/src/main/resources\LabledExpr.g4 by ANTLR 4.7.2
package personal.xiaoq.lableexpr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LabledExprParser}.
 */
public interface LabledExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LabledExprParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(LabledExprParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link LabledExprParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(LabledExprParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code cal_stat}
	 * labeled alternative in {@link LabledExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterCal_stat(LabledExprParser.Cal_statContext ctx);
	/**
	 * Exit a parse tree produced by the {@code cal_stat}
	 * labeled alternative in {@link LabledExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitCal_stat(LabledExprParser.Cal_statContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assign}
	 * labeled alternative in {@link LabledExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterAssign(LabledExprParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assign}
	 * labeled alternative in {@link LabledExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitAssign(LabledExprParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code print}
	 * labeled alternative in {@link LabledExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterPrint(LabledExprParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code print}
	 * labeled alternative in {@link LabledExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitPrint(LabledExprParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code number}
	 * labeled alternative in {@link LabledExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNumber(LabledExprParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code number}
	 * labeled alternative in {@link LabledExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNumber(LabledExprParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parens}
	 * labeled alternative in {@link LabledExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParens(LabledExprParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link LabledExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParens(LabledExprParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code as_expr}
	 * labeled alternative in {@link LabledExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAs_expr(LabledExprParser.As_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code as_expr}
	 * labeled alternative in {@link LabledExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAs_expr(LabledExprParser.As_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code id}
	 * labeled alternative in {@link LabledExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterId(LabledExprParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code id}
	 * labeled alternative in {@link LabledExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitId(LabledExprParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code md_expr}
	 * labeled alternative in {@link LabledExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMd_expr(LabledExprParser.Md_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code md_expr}
	 * labeled alternative in {@link LabledExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMd_expr(LabledExprParser.Md_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pow_expr}
	 * labeled alternative in {@link LabledExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPow_expr(LabledExprParser.Pow_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pow_expr}
	 * labeled alternative in {@link LabledExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPow_expr(LabledExprParser.Pow_exprContext ctx);
}