package ast;

import visitor.*;

//	tipoident:tipo -> tipo:String ;

public class Tipoident extends AbstractTipo {

	public Tipoident(String tipo) {
		this.tipo = tipo;
	}

	public Tipoident(Object tipo) {
		this.tipo = (tipo instanceof Token) ? ((Token)tipo).getLexeme() : (String) tipo;

		searchForPositions(tipo);	// Obtener linea/columna a partir de los hijos
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String tipo;

	public void setDefinicion(Struct definicion) {
		this.definicion = definicion;
	}
	
	public Struct getDefinicion(){
		return definicion;
	}
	
	Struct definicion;

	@Override
	public int getSize() {
		int size = 0;
		for(Definicion def:definicion.getDefinicion()){
			size+=def.getTipo().getSize();
		}
		
		return size;
	}

	@Override
	public char getSufijo() {
		return 0;
	}

	@Override
	public String getNombreMAPL() {
		return null;
	}


	
	
}

