package ast;

import java.util.*;
import visitor.*;

//	funcion:elemento -> string:String  parametro:parametro*  tipo:tipo  atributo:atributo*  sentencia:sentencia* ;

public class Funcion extends AbstractElemento {

	public Funcion(String string, List<Parametro> parametro, Tipo tipo, List<Atributo> atributo, List<Sentencia> sentencia) {
		this.string = string;
		this.parametro = parametro;
		this.tipo = tipo;
		this.atributo = atributo;
		this.sentencia = sentencia;

		searchForPositions(parametro, tipo, atributo, sentencia);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public Funcion(Object string, Object parametro, Object tipo, Object atributo, Object sentencia) {
		this.string = (string instanceof Token) ? ((Token)string).getLexeme() : (String) string;
		this.parametro = (List<Parametro>) parametro;
		this.tipo = (Tipo) tipo;
		this.atributo = (List<Atributo>) atributo;
		this.sentencia = (List<Sentencia>) sentencia;

		searchForPositions(string, parametro, tipo, atributo, sentencia);	// Obtener linea/columna a partir de los hijos
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

	public List<Atributo> getAtributo() {
		return atributo;
	}
	public void setAtributo(List<Atributo> atributo) {
		this.atributo = atributo;
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
	private List<Atributo> atributo;
	private List<Sentencia> sentencia;
}

