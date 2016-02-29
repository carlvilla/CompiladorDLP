package ast;

import java.util.*;
import visitor.*;

//	if:sentencia -> condic:expresion  cierto:sentencia*  falso:sentencia* ;

public class If extends AbstractSentencia {

	public If(Expresion condic, List<Sentencia> cierto, List<Sentencia> falso) {
		this.condic = condic;
		this.cierto = cierto;
		this.falso = falso;

		searchForPositions(condic, cierto, falso);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public If(Object condic, Object cierto, Object falso) {
		this.condic = (Expresion) condic;
		this.cierto = (List<Sentencia>) cierto;
		this.falso = (List<Sentencia>) falso;

		searchForPositions(condic, cierto, falso);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getCondic() {
		return condic;
	}
	public void setCondic(Expresion condic) {
		this.condic = condic;
	}

	public List<Sentencia> getCierto() {
		return cierto;
	}
	public void setCierto(List<Sentencia> cierto) {
		this.cierto = cierto;
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
	private List<Sentencia> cierto;
	private List<Sentencia> falso;
}

