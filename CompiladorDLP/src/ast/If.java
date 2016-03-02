package ast;

import java.util.*;
import visitor.*;

//	if:sentencia -> condic:expresion  verdadero:sentencia*  falso:sentencia* ;

public class If extends AbstractSentencia {

	public If(Expresion condic, List<Sentencia> verdadero, List<Sentencia> falso) {
		this.condic = condic;
		this.verdadero = verdadero;
		this.falso = falso;

		searchForPositions(condic, verdadero, falso);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public If(Object condic, Object verdadero, Object falso) {
		this.condic = (Expresion) condic;
		this.verdadero = (List<Sentencia>) verdadero;
		this.falso = (List<Sentencia>) falso;

		searchForPositions(condic, verdadero, falso);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getCondic() {
		return condic;
	}
	public void setCondic(Expresion condic) {
		this.condic = condic;
	}

	public List<Sentencia> getVerdadero() {
		return verdadero;
	}
	public void setVerdadero(List<Sentencia> verdadero) {
		this.verdadero = verdadero;
	}

	public List<Sentencia> getFalso() {
		return falso;
	}
	public void setFalso(List<Sentencia> falso) {
		this.falso = falso;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion condic;
	private List<Sentencia> verdadero;
	private List<Sentencia> falso;
}

