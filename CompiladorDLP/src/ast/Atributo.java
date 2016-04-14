package ast;

import visitor.*;

//	atributo:elemento -> definicion:definicion ;

public class Atributo extends AbstractElemento {

	public Atributo(Definicion definicion) {
		this.definicion = definicion;

		searchForPositions(definicion);	// Obtener linea/columna a partir de los hijos
	}

	public Atributo(Object definicion) {
		this.definicion = (Definicion) definicion;

		searchForPositions(definicion);	// Obtener linea/columna a partir de los hijos
	}

	public Definicion getDefinicion() {
		return definicion;
	}
	public void setDefinicion(Definicion definicion) {
		this.definicion = definicion;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Definicion definicion;

	
}

