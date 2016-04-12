package ast;

import visitor.*;

//	return:sentencia -> expresion:expresion ;

public class Return extends AbstractSentencia {

	public Return(Expresion expresion) {
		this.expresion = expresion;

		searchForPositions(expresion);	// Obtener linea/columna a partir de los hijos
	}

	public Return(Object expresion) {
		
		//Esta comprobación se utiliza para poder obtener la posición de un error
		//en el caso de que esté implicado un return que no devuelve ninguna expresión
		if(! (expresion instanceof Token))
			this.expresion = (Expresion) expresion;

		searchForPositions(expresion);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getExpresion() {
		return expresion;
	}
	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion expresion;
}

