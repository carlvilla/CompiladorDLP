package ast;

import visitor.*;

//	definicion -> nombre:String  tipo:tipo ;

public class Definicion extends AbstractTraceable implements AST {

	public Definicion(String nombre, Tipo tipo) {
		this.nombre = nombre;
		this.tipo = tipo;

		searchForPositions(tipo);	// Obtener linea/columna a partir de los hijos
	}

	public Definicion(Object nombre, Object tipo) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.tipo = (Tipo) tipo;

		searchForPositions(nombre, tipo);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private Tipo tipo;
	private int direccion;
	
	public int getDireccion() {
		return direccion;
	}
	
	public void setDireccion(int dir){
		this.direccion = dir;
	}
	
}

