package generacionDeCodigo;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ast.AccesoArray;
import ast.AccesoStruct;
import ast.Array;
import ast.Cast;
import ast.Definicion;
import ast.ExpresionBinaria;
import ast.ExpresionLogica;
import ast.Litchar;
import ast.Litent;
import ast.Litreal;
import ast.Struct;
import ast.Tipo;
import ast.Tipoident;
import ast.Tipoint;
import ast.Var;
import visitor.DefaultVisitor;
import visitor.Visitor;

public class ValorVisitor extends DefaultVisitor{
	
	private Map<String, String> instrucciones = new HashMap<String, String>();
	Visitor direccionVisitor;
	
	public ValorVisitor(Writer writer, String sourceFile,Map<String,String> instrucciones) {
		this.writer = new PrintWriter(writer);
		this.sourceFile = sourceFile;
		this.instrucciones = instrucciones;
		this.direccionVisitor = new DireccionVisitor(writer, sourceFile);
	}

	
//	class Litent { int valor; }
	public Object visit(Litent node, Object param) {
		genera("push " + node.getValor());
		return null;
	}

	//	class Litchar { String valor; }
	public Object visit(Litchar node, Object param) {
		genera("pushb " + node.getValor());
		return null;
	}

	//	class Litreal { String valor; }
	public Object visit(Litreal node, Object param) {
		genera("pushf " + node.getValor());
		return null;
	}

	//	class Var { String nombre; }
	public Object visit(Var node, Object param) {
		genera("pusha " + node.getDefinicion().getDireccion());
		genera("Load",node.getTipo());
		
		return null;
	}
	
//	class ExpresionBinaria { Expresion left;  String string;  Expresion right; }
	public Object visit(ExpresionBinaria node, Object param) {


		if(node.getString()!="="){
			node.getLeft().accept(this, param);
			node.getRight().accept(this, param);
			genera(instrucciones.get(node.getString()),node.getTipo());
		}
		
		return null;
	}
	
	
//	class AccesoArray { Expresion contenedor;  Expresion posicion; }
	public Object visit(AccesoArray node, Object param) {

		Array array = ((Array)((Var)node.getContenedor()).getDefinicion().getTipo());
		
		//Obtenemos dirección en array
		node.accept(direccionVisitor, param);
		
		//Cargamos el valor que se encuentra en la posición obtenida
		genera("Load",array.getTipo());
	
		return null;
	}

	//	class AccesoStruct { Expresion contenedor;  String atributo; }
	public Object visit(AccesoStruct node, Object param) {

		//Dirección variable struct
		node.accept(direccionVisitor, param);
		
		//Cargamos valor struct
		List<Definicion> atributosStruct = ((Tipoident)(node.getContenedor())
				.getTipo()).getDefinicion().getDefinicion();
		
		for(Definicion definicion:atributosStruct){
			if(definicion.getNombre().equals(node.getAtributo())){
				genera("load",definicion.getTipo());
				break;
			}
			
		}
		
		return null;

		
	}
	
	//	class Cast { Tipo tipo;  Expresion expresion; }
	public Object visit(Cast node, Object param) {

		node.getExpresion().accept(this, param);
		
		genera(node.getExpresion().getTipo().getSufijo()+"2"
				,node.getTipo());
		

		return null;
	}
	
//	class ExpresionLogica { Expresion left;  String string;  Expresion right; }
	public Object visit(ExpresionLogica node, Object param) {

		node.getLeft().accept(this, param);
		node.getRight().accept(this, param);
		genera(instrucciones.get(node.getString()));
		return null;
	}
	
	
	
	
	// Método auxiliar recomendado -------------
		private void genera(String instruccion) {
			writer.println(instruccion);
		}

		private void genera(String instruccion, Tipo tipo) {
			genera(instruccion + tipo.getSufijo());
		}

		private PrintWriter writer;// = new PrintWriter(System.out);;
		private String sourceFile;



}
