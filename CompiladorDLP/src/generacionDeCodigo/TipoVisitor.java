package generacionDeCodigo;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import ast.AccesoStruct;
import ast.Definicion;
import ast.Tipoident;
import ast.Var;
import visitor.DefaultVisitor;

public class TipoVisitor extends DefaultVisitor {

	private PrintWriter writer;
	private String sourceFile;
	
	public TipoVisitor(Writer writer, String sourceFile) {
		this.writer = new PrintWriter(writer);
		this.sourceFile = sourceFile;
	}
	

	//	class AccesoStruct { Expresion contenedor;  String atributo; }
	public Object visit(AccesoStruct node, Object param) {
		//Devolvemos tipo Struct
		if(param==null)
			return ((Tipoident)node.getContenedor().getTipo());		
		
		//Devolvemos tipo de algún atributo del struct
		
		List<Definicion> definiciones = ((Tipoident)(node.getContenedor()).getTipo()).getDefinicion().getDefinicion();
		
			for(Definicion def:definiciones){
				if(def.getNombre().equals((node.getAtributo()))){
					return def.getTipo();
			}
		}
			
		return null;
		
	}
	

//	class Var { String nombre; }
	public Object visit(Var node, Object param) {
		return node.getTipo();
	}
	

}
