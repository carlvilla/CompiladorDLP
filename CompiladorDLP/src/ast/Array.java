package ast;

import visitor.*;

//	array:tipo -> litent:litent  tipo:tipo ;

public class Array extends AbstractTipo {

	public Array(Litent litent, Tipo tipo) {
		this.litent = litent;
		this.tipo = tipo;

		searchForPositions(litent, tipo);	// Obtener linea/columna a partir de los hijos
	}

	public Array(Object litent, Object tipo) {
		this.litent = (Litent) litent;
		this.tipo = (Tipo) tipo;

		searchForPositions(litent, tipo);	// Obtener linea/columna a partir de los hijos
	}

	public Litent getLitent() {
		return litent;
	}
	public void setLitent(Litent litent) {
		this.litent = litent;
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
	
	public int getSize(){
		return ((litent.getValor())*tipo.getSize());
	}

	private Litent litent; //Tamaño del array
	private Tipo tipo;
	@Override
	public char getSufijo() {
		return tipo.getSufijo();
	}

	@Override
	public String getNombreMAPL() {
		// TODO Auto-generated method stub
		return null;
	}
}

