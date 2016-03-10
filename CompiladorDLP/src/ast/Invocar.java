package ast;

import java.util.*;
import visitor.*;

//	invocar:sentencia, expresion -> string:String  expresion:expresion* ;

public class Invocar extends AbstractTraceable implements Sentencia, Expresion {

	public Invocar(String string, List<Expresion> expresion) {
		this.string = string;
		this.expresion = expresion;

		searchForPositions(expresion);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public Invocar(Object string, Object expresion) {
		this.string = (string instanceof Token) ? ((Token)string).getLexeme() : (String) string;
		this.expresion = (List<Expresion>) expresion;

		searchForPositions(string, expresion);	// Obtener linea/columna a partir de los hijos
	}

	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}

	public List<Expresion> getExpresion() {
		return expresion;
	}
	public void setExpresion(List<Expresion> expresion) {
		this.expresion = expresion;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String string;
	private List<Expresion> expresion;
	
	public void setDefinicion(Funcion definicion) {
		this.defFuncion = definicion;
	
	}
	
	public Funcion getDefinicion() {
		return defFuncion;
	}
	
	Funcion defFuncion;
}

