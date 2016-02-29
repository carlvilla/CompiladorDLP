package ast;

import java.util.*;
import visitor.*;

//	funcion:elemento -> string:String  parametro:parametro*  tipo:tipo  sentencia:sentencia* ;

public class Funcion extends AbstractElemento {

	public Funcion(String string, List<Parametro> parametro, Tipo tipo, List<Sentencia> sentencia) {
		this.string = string;
		this.parametro = parametro;
		this.tipo = tipo;
		this.sentencia = sentencia;

		searchForPositions(parametro, tipo, sentencia);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public Funcion(Object string, Object parametro, Object tipo, Object sentencia) {
		this.string = (string instanceof Token) ? ((Token)string).getLexeme() : (String) string;
		this.parametro = (List<Parametro>) parametro;
		this.tipo = (Tipo) tipo;
		this.sentencia = (List<Sentencia>) sentencia;

		searchForPositions(string, parametro, tipo, sentencia);	// Obtener linea/columna a partir de los hijos
	}

	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}

	public List<Parametro> getParametro() {
		return parametro;
	}
	public void setParametro(List<Parametro> parametro) {
		this.parametro = parametro;
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
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

	private String string;
	private List<Parametro> parametro;
	private Tipo tipo;
	private List<Sentencia> sentencia;
}

