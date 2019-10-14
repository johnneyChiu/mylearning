// Generated from D:/personal/github/mylearning/Antlr4.demo/src/main/resources\LabledExpr.g4 by ANTLR 4.7.2
package personal.xiaoq.lableexpr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LabledExprParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LabledExprVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LabledExprParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(LabledExprParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code cal_stat}
	 * labeled alternative in {@link LabledExprParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCal_stat(LabledExprParser.Cal_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assign}
	 * labeled alternative in {@link LabledExprParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(LabledExprParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code print}
	 * labeled alternative in {@link LabledExprParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(LabledExprParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code number}
	 * labeled alternative in {@link LabledExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(LabledExprParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link LabledExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(LabledExprParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code as_expr}
	 * labeled alternative in {@link LabledExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAs_expr(LabledExprParser.As_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code id}
	 * labeled alternative in {@link LabledExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(LabledExprParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code md_expr}
	 * labeled alternative in {@link LabledExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMd_expr(LabledExprParser.Md_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pow_expr}
	 * labeled alternative in {@link LabledExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPow_expr(LabledExprParser.Pow_exprContext ctx);
}