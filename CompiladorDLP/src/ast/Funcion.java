package ast;

import java.util.*;
import visitor.*;

//	funcion:elemento -> string:String  parametro:parametro*  tipo:tipo  atributo:atributo*  sentencia:sentencia* ;

public class Funcion extends AbstractElemento {

	public Funcion(String string, List<Definicion> parametros, Tipo tipo, List<Atributo> atributo, List<Sentencia> sentencia) {
		this.string = string;
		this.parametros = parametros;
		this.tipo = tipo;
		this.atributo = atributo;
		this.sentencia = sentencia;

		searchForPositions(parametros, tipo, atributo, sentencia);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public Funcion(Object string, Object parametro, Object tipo, Object atributo, Object sentencia) {
		this.string = (string instanceof Token) ? ((Token)string).getLexeme() : (String) string;
		this.parametros = (List<Definicion>) parametro;
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

	public List<Definicion> getParametros() {
		return parametros;
	}
	public void setParametros(List<Definicion> parametro) {
		this.parametros = parametro;
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
	private List<Definicion> parametros;
	private Tipo tipo;
	private List<Atributo> atributo;
	private List<Sentencia> sentencia;
}

