package generacionDeCodigo;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import ast.AccesoArray;
import ast.AccesoStruct;
import ast.Array;
import ast.Definicion;
import ast.Tipo;
import ast.Tipoident;
import ast.Var;
import visitor.DefaultVisitor;

public class DireccionVisitor extends DefaultVisitor {
	
	private PrintWriter writer;
	private ValorVisitor valorVisitor;

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
			
		node.getContenedor().accept(this, param);
		node.getPosicion().accept(valorVisitor, param);
		//Tipo tipo = (Tipo) node.getContenedor().accept(sizeVisitor, null);
	
		genera("push "+ ((Array)node.getContenedor().getTipo()).getTipo().getSize());
		
		genera("mul");
		genera("add");
		
		
		return null;
	}
	
	
//	class AccesoStruct { Expresion contenedor;  String atributo; }
	public Object visit(AccesoStruct node, Object param) {

		//Direccion contenedor
		node.getContenedor().accept(this, param);
		
	//	genera("pusha "+((Var)node.getContenedor()).getDefinicion().getDireccion());
		
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
	
	// Método auxiliar recomendado -------------
	private void genera(String instruccion) {
		writer.println(instruccion);
	}
/*
	private void genera(String instruccion, Tipo tipo) {
		genera(instruccion + tipo.getSufijo());
	}
	*/


	public void setValorVisitor(ValorVisitor valorVisitor) {
		this.valorVisitor = valorVisitor;
		
	}
	
}
