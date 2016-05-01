package generacionDeCodigo;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import ast.AccesoArray;
import ast.AccesoStruct;
import ast.Array;
import ast.Definicion;
import ast.Litent;
import ast.Tipoident;
import ast.Var;
import visitor.DefaultVisitor;

public class DireccionVisitor extends DefaultVisitor {
	
	private PrintWriter writer;

	public DireccionVisitor(Writer writer, String sourceFile) {
		this.writer = new PrintWriter(writer);
	}
	
	
	//	class Var { String nombre; }
	public Object visit(Var node, Object param) {
		Definicion def = node.getDefinicion();
		
		if(def.getDireccion() < 0 || def.esParametro()){
			genera("pusha BP");
			genera("push " + node.getDefinicion().getDireccion());
			genera("add");
		}
		else{
			genera("pusha " + node.getDefinicion().getDireccion());
		}
		
		return null;
	}
	
//	class AccesoArray { Expresion contenedor;  Expresion posicion; }
	public Object visit(AccesoArray node, Object param) {

		Array array = ((Array)((Var)node.getContenedor()).getDefinicion().getTipo());
		
		genera("pusha "+((Var)node.getContenedor()).getDefinicion().getDireccion());
		genera("push "+((Litent)node.getPosicion()).getValor());
		genera("push "+ array.getTipo().getSize());
		genera("mul");
		genera("add");
		
		
		return null;
	}
	
	
//	class AccesoStruct { Expresion contenedor;  String atributo; }
	public Object visit(AccesoStruct node, Object param) {

		genera("pusha "+((Var)node.getContenedor()).getDefinicion().getDireccion());
		
		List<Definicion> atributosStruct = ((Tipoident)(node.getContenedor())
				.getTipo()).getDefinicion().getDefinicion();
		
		for(Definicion definicion:atributosStruct){
			if(definicion.getNombre().equals(node.getAtributo())){
				genera("push "+definicion.getDireccion());
				genera("add");
				break;
			}
			
		}
		
		return null;

		
	}
	
	// M�todo auxiliar recomendado -------------
	private void genera(String instruccion) {
		writer.println(instruccion);
	}
/*
	private void genera(String instruccion, Tipo tipo) {
		genera(instruccion + tipo.getSufijo());
	}
	*/
	
}
