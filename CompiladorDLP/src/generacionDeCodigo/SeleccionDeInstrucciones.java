package generacionDeCodigo;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import ast.Atributo;
import ast.Expresion;
import ast.ExpresionBinaria;
import ast.Funcion;
import ast.If;
import ast.InvocarSentencia;
import ast.Parametro;
import ast.Print;
import ast.Programa;
import ast.Return;
import ast.Sentencia;
import ast.Tipo;
import ast.Tipovoid;
import ast.While;
import visitor.DefaultVisitor;

public class SeleccionDeInstrucciones extends DefaultVisitor {

	private ValorVisitor valorVisitor;
	private DireccionVisitor direccionVisitor;

	private Map<String, String> instrucciones = new HashMap<String, String>();
	private int contadorIf = 0;
	private int contadorWhile = 0;

	public SeleccionDeInstrucciones(Writer writer, String sourceFile) {
		this.writer = new PrintWriter(writer);
		this.sourceFile = sourceFile;
		definirInstrucciones();
		valorVisitor = new ValorVisitor(writer, sourceFile, instrucciones);
		direccionVisitor = new DireccionVisitor(writer, sourceFile);
	}

	private void definirInstrucciones() {
		instrucciones.put("+", "add");
		instrucciones.put("-", "sub");
		instrucciones.put("*", "mul");
		instrucciones.put("/", "div");
		instrucciones.put("==", "eq");
		instrucciones.put("!=", "ne");
		instrucciones.put(">", "gt");
		instrucciones.put(">=", "ge");
		instrucciones.put("<", "lt");
		instrucciones.put("<=", "le");

	}

	/*
	 * Poner aquí los visit necesarios. Si se ha usado VGen solo hay que
	 * copiarlos de la clase 'visitor/_PlantillaParaVisitors.txt'.
	 */

	// Ejemplo:
	//
	// public Object visit(Programa node, Object param) {
	// genera("#source \"" + sourceFile + "\"");
	// genera("call main");
	// genera("halt");
	// super.visit(node, param); // Recorrer los hijos
	// return null;
	// }

	// class Programa { List<Elemento> elemento; }
	public Object visit(Programa node, Object param) {
		genera("#source \"" + sourceFile + "\"");
		genera("call main");
		genera("halt");
		visitChildren(node.getElemento(), param);
		
		return null;
	}

	// class Print { Expresion expresion; }
	public Object visit(Print node, Object param) {
		genera("#line " + node.getEnd().getLine());
		node.getExpresion().accept(valorVisitor, param);
		genera("out", node.getExpresion().getTipo());

		return null;
	}

	// class ExpresionBinaria { Expresion left; String string; Expresion right;
	// }
	public Object visit(ExpresionBinaria node, Object param) {

		genera("#line " + node.getEnd().getLine());
		node.getLeft().accept(direccionVisitor, param);
		node.getRight().accept(valorVisitor, param);
		genera("store", node.getTipo());

		return null;
	}
	
	
//	class While { Expresion expresion;  List<Sentencia> sentencia; }
	public Object visit(While node, Object param) {

		contadorWhile++;
		int contadorLocal = contadorWhile;
		
		genera("#line " + node.getEnd().getLine());
		genera("inicio_while"+contadorLocal +" :");
		node.getExpresion().accept(valorVisitor, param);
		genera("jz fin_while"+contadorLocal);
		for(Sentencia sent:node.getSentencia()){
			sent.accept(this, param);
		}
		genera("jmp inicio_while"+contadorLocal);
		genera("fin_while"+contadorLocal+" :");
		
		return null;
	}
	
//	class If { Expresion condic;  List<Sentencia> verdadero;  List<Sentencia> falso; }
	public Object visit(If node, Object param) {

		
	    contadorIf++;
	    int contadorLocal = contadorIf;
	    
	    genera("#line " + node.getEnd().getLine());
	    node.getCondic().accept(valorVisitor, param);
	    genera("jz else"+contadorLocal );
	    for(Sentencia sent: node.getVerdadero()){
			sent.accept(this, param);
		}
	    
	    genera("jmp fin_if"+contadorLocal);
	    genera("else"+contadorLocal+" :");
	    
	    for(Sentencia sent: node.getFalso()){
			sent.accept(this, param);
		}
	    
	    genera("fin_if"+contadorLocal+" :");
	   

	   return null;
	}
	
	

	//	class Funcion { String string;  List<Parametro> parametro;  Tipo tipo;  List<Atributo> atributo;  List<Sentencia> sentencia; }
	public Object visit(Funcion node, Object param) {

		genera("#line " + node.getEnd().getLine());
		genera(node.getString()+" :");
		
		int sizeAtributos = 0;
		for(Atributo atributo:node.getAtributo()){
			sizeAtributos+=atributo.getDefinicion().getTipo().getSize();
		}
		
		genera("enter "+sizeAtributos);
		for(Sentencia sent:node.getSentencia()){
			sent.accept(this, param);
		}
		
		int sizeParametros = 0;
		for(Parametro parametro:node.getParametro()){
			sizeParametros+=parametro.getTipo().getSize();
		}
		
		if(node.getTipo() instanceof Tipovoid)
			genera("ret 0,"+sizeAtributos+","+sizeParametros);
		
		
		return null;
	}
	
	

	//	class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {
		
		
		genera("#line " + node.getEnd().getLine());
		node.getExpresion().accept(valorVisitor, param);
		
		int sizeAtributos = 0;
		for(Atributo atributo:node.getMiFuncion().getAtributo()){
			sizeAtributos+=atributo.getDefinicion().getTipo().getSize();
		}
		
		int sizeParametros = 0;
		for(Parametro parametro:node.getMiFuncion().getParametro()){
			sizeParametros+=parametro.getTipo().getSize();
		}
			
		genera("ret "+node.getMiFuncion().getTipo().getSize()+","+sizeAtributos+","+sizeParametros);
		return null;
	}

	
	
	
	
	//	class InvocarSentencia { String string;  List<Expresion> expresion; }
	public Object visit(InvocarSentencia node, Object param) {

		genera("#line " + node.getEnd().getLine());

		if (node.getExpresion() != null)
			for (Expresion child : node.getExpresion())
				child.accept(this, param);

		return null;
	}

	// Método auxiliar recomendado -------------
	private void genera(String instruccion) {
		writer.println(instruccion);
	}

	private void genera(String instruccion, Tipo tipo) {
		genera(instruccion + tipo.getSufijo());
	}

	private PrintWriter writer;
	private String sourceFile;
}
