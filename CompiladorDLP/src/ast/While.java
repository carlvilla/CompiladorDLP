package ast;

import java.util.*;
import visitor.*;

//	while:sentencia -> expresion:expresion  sentencia:sentencia* ;

public class While extends AbstractSentencia {

	public While(Expresion expresion, List<Sentencia> sentencia) {
		this.expresion = expresion;
		this.sentencia = sentencia;

		searchForPositions(expresion, sentencia);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public While(Object expresion, Object sentencia) {
		this.expresion = (Expresion) expresion;
		this.sentencia = (List<Sentencia>) sentencia;

		searchForPositions(expresion, sentencia);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getExpresion() {
		return expresion;
	}
	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}

	public List<Sentencia> getSentencia() {
		return sentencia;
	}
	public void setSentencia(List<Sentencia> sentencia) {
		this.sentencia = sentencia;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion expresion;
	private List<Sentencia> sentencia;
}

