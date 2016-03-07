package ast;

import visitor.*;

//	entreParentesis:expresion -> contenido:expresion ;

public class EntreParentesis extends AbstractExpresion {

	public EntreParentesis(Expresion contenido) {
		this.contenido = contenido;

		searchForPositions(contenido);	// Obtener linea/columna a partir de los hijos
	}

	public EntreParentesis(Object contenido) {
		this.contenido = (Expresion) contenido;

		searchForPositions(contenido);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getContenido() {
		return contenido;
	}
	public void setContenido(Expresion contenido) {
		this.contenido = contenido;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion contenido;
}

