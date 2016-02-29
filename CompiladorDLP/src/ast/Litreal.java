package ast;

import visitor.*;

//	litreal:expresion -> string:String ;

public class Litreal extends AbstractExpresion {

	public Litreal(String string) {
		this.string = string;
	}

	public Litreal(Object string) {
		this.string = (string instanceof Token) ? ((Token)string).getLexeme() : (String) string;

		searchForPositions(string);	// Obtener linea/columna a partir de los hijos
	}

	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String string;
}

