package generacionDeCodigo;

import java.io.PrintWriter;
import java.io.Writer;

import ast.Tipo;
import ast.Var;
import visitor.DefaultVisitor;

public class DireccionVisitor extends DefaultVisitor {
	
	private PrintWriter writer;
	private String sourceFile;

	public DireccionVisitor(Writer writer, String sourceFile) {
		this.writer = new PrintWriter(writer);
		this.sourceFile = sourceFile;
	}
	
	
	//	class Var { String nombre; }
	public Object visit(Var node, Object param) {
		genera("pusha " + node.getDefinicion().getDireccion());
		return null;
	}
	
	// Método auxiliar recomendado -------------
	private void genera(String instruccion) {
		writer.println(instruccion);
	}

	private void genera(String instruccion, Tipo tipo) {
		genera(instruccion + tipo.getSufijo());
	}
	
}
