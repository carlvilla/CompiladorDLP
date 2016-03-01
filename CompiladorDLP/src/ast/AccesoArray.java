package ast;

import visitor.*;

//	accesoArray:expresion -> contenedor:expresion  posicion:expresion ;

public class AccesoArray extends AbstractExpresion {

	public AccesoArray(Expresion contenedor, Expresion posicion) {
		this.contenedor = contenedor;
		this.posicion = posicion;

		searchForPositions(contenedor, posicion);	// Obtener linea/columna a partir de los hijos
	}

	public AccesoArray(Object contenedor, Object posicion) {
		this.contenedor = (Expresion) contenedor;
		this.posicion = (Expresion) posicion;

		searchForPositions(contenedor, posicion);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getContenedor() {
		return contenedor;
	}
	public void setContenedor(Expresion contenedor) {
		this.contenedor = contenedor;
	}

	public Expresion getPosicion() {
		return posicion;
	}
	public void setPosicion(Expresion posicion) {
		this.posicion = posicion;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion contenedor;
	private Expresion posicion;
}

