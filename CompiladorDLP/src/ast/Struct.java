package ast;

import java.util.*;
import visitor.*;

//	struct:elemento -> string:String  definicion:definicion* ;

public class Struct extends AbstractElemento {

	public Struct(String string, List<Definicion> definicion) {
		this.string = string;
		this.definicion = definicion;

		searchForPositions(definicion);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public Struct(Object string, Object definicion) {
		this.string = (string instanceof Token) ? ((Token)string).getLexeme() : (String) string;
		this.definicion = (List<Definicion>) definicion;

		searchForPositions(string, definicion);	// Obtener linea/columna a partir de los hijos
	}

	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}

	public List<Definicion> getDefinicion() {
		return definicion;
	}
	public void setDefinicion(List<Definicion> definicion) {
		this.definicion = definicion;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String string;
	private List<Definicion> definicion;
}

