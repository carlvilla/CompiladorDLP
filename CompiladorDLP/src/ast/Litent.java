package ast;

import visitor.*;

//	litent:expresion -> string:String ;

public class Litent extends AbstractExpresion {

	public Litent(String string) {
		this.string = string;
	}

	public Litent(Object string) {
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

