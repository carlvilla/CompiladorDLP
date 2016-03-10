package ast;

import visitor.*;

//	var:expresion -> nombre:String ;

public class Var extends AbstractExpresion {

	public Var(String nombre) {
		this.nombre = nombre;
	}

	public Var(Object nombre) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;

		searchForPositions(nombre);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;

	public void setDefinicion(Definicion definicion) {
		this.definicion = definicion;
	}
	
	public Definicion getDefinicion() {
		return definicion;		
	}

	Definicion definicion;

}




