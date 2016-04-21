package generacionDeCodigo;

import java.io.PrintWriter;

import ast.Litchar;
import ast.Litent;
import ast.Litreal;
import ast.Tipo;
import ast.Var;
import visitor.DefaultVisitor;

public class ValorVisitor extends DefaultVisitor{
	
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
	
	
	
	
	// Método auxiliar recomendado -------------
		private void genera(String instruccion) {
	//		writer.println(instruccion);
		}

		private void genera(String instruccion, Tipo tipo) {
			genera(instruccion + tipo.getSufijo());
		}

		private PrintWriter writer;
		private String sourceFile;



}
