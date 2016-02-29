package ast;

import java.util.*;
import visitor.*;

//	programa -> elemento:elemento* ;

public class Programa extends AbstractTraceable implements AST {

	public Programa(List<Elemento> elemento) {
		this.elemento = elemento;

		searchForPositions(elemento);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public Programa(Object elemento) {
		this.elemento = (List<Elemento>) elemento;

		searchForPositions(elemento);	// Obtener linea/columna a partir de los hijos
	}

	public List<Elemento> getElemento() {
		return elemento;
	}
	public void setElemento(List<Elemento> elemento) {
		this.elemento = elemento;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private List<Elemento> elemento;
}

