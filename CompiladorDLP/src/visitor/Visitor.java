package visitor;

import ast.*;

public interface Visitor {
	public Object visit(Programa node, Object param);
	public Object visit(Funcion node, Object param);
	public Object visit(Struct node, Object param);
	public Object visit(Atributo node, Object param);
	public Object visit(Definicion node, Object param);
	public Object visit(Parametro node, Object param);
	public Object visit(If node, Object param);
	public Object visit(Read node, Object param);
	public Object visit(Return node, Object param);
	public Object visit(Print node, Object param);
	public Object visit(While node, Object param);
	public Object visit(InvocarSentencia node, Object param);
	public Object visit(ExpresionBinaria node, Object param);
	public Object visit(Litent node, Object param);
	public Object visit(Litchar node, Object param);
	public Object visit(Litreal node, Object param);
	public Object visit(Var node, Object param);
	public Object visit(Cast node, Object param);
	public Object visit(ExpresionUnaria node, Object param);
	public Object visit(ExpresionLogica node, Object param);
	public Object visit(AccesoArray node, Object param);
	public Object visit(AccesoStruct node, Object param);
	public Object visit(EntreParentesis node, Object param);
	public Object visit(InvocarFuncion node, Object param);
	public Object visit(Array node, Object param);
	public Object visit(Tipoint node, Object param);
	public Object visit(Tiporeal node, Object param);
	public Object visit(Tipochar node, Object param);
	public Object visit(Tipoident node, Object param);
}
