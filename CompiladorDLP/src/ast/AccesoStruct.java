package ast;

import visitor.*;

//	accesoStruct:expresion -> contenedor:expresion  atributo:String ;

public class AccesoStruct extends AbstractExpresion {

	public AccesoStruct(Expresion contenedor, String atributo) {
		this.contenedor = contenedor;
		this.atributo = atributo;

		searchForPositions(contenedor);	// Obtener linea/columna a partir de los hijos
	}

	public AccesoStruct(Object contenedor, Object atributo) {
		this.contenedor = (Expresion) contenedor;
		this.atributo = (atributo instanceof Token) ? ((Token)atributo).getLexeme() : (String) atributo;

		searchForPositions(contenedor, atributo);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getContenedor() {
		return contenedor;
	}
	public void setContenedor(Expresion contenedor) {
		this.contenedor = contenedor;
	}

	public String getAtributo() {
		return atributo;
	}
	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion contenedor;
	private String atributo;
}

