package ast;

import visitor.*;

//	litent:expresion -> valor:String ;

public class Litent extends AbstractExpresion {

	public Litent(String valor) {
		this.valor = valor;
	}

	public Litent(Object valor) {
		this.valor = (valor instanceof Token) ? ((Token)valor).getLexeme() : (String) valor;

		searchForPositions(valor);	// Obtener linea/columna a partir de los hijos
	}

	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String valor;
}

