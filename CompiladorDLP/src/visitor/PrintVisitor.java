package visitor;

import ast.*;

/*
Plantilla para Visitors.
Para crear un nuevo Visitor cortar y pegar este código y ya se tendrá un visitor que compila y 
que al ejecutarlo recorrerá todo el árbol (sin hacer nada aún en él).
Solo quedará añadir a cada método visit aquello adicional que tenga que realizar sobre su nodo del AST.
*/

public class PrintVisitor extends DefaultVisitor {

	// ---------------------------------------------------------
	// Tareas a realizar en cada método visit:
	//
	// Si en algún método visit NO SE QUIERE HACER NADA más que recorrer los hijos entonces se puede 
	// borrar (dicho método se heredará de DefaultVisitor con el código de recorrido).
	//
	// Lo siguiente es para cuando se quiera AÑADIR alguna funcionalidad adicional a un visit:
	//
	// - El código que aparece en cada método visit es aquel que recorre los hijos. Es el mismo código
	//		que está implementado en el padre (DefaultVisitor). Por tanto la llamada a 'super.visit' y el
	//		resto del código del método hacen lo mismo (por ello 'super.visit' está comentado).
	//
	// - Lo HABITUAL será borrar todo el código de recorrido dejando solo la llamada a 'super.visit'. De esta
	//		manera cada método visit se puede centrar en la tarea que tiene que realizar sobre su nodo del AST.
	//
	// - La razón de que aparezca el código de recorrido de los hijos es por si se necesita realizar alguna
	//		tarea DURANTE el mismo (por ejemplo ir comprobando su tipo). En este caso ya se tiene implementado
	//		dicho recorrido y solo habrá que incrustar las acciones adicionales en el mismo. En este caso
	//		la llamada a 'super.visit' deberá ser borrada.
	// ---------------------------------------------------------


	//	class Programa { List<Elemento> elemento; }
	public Object visit(Programa node, Object param) {

		// super.visit(node, param);

		if (node.getElemento() != null)
			for (Elemento child : node.getElemento())
				child.accept(this, param);

		return null;
	}

	//	class Funcion { String string;  List<Parametro> parametro;  Tipo tipo;  List<Sentencia> sentencia; }
	public Object visit(Funcion node, Object param) {

		// super.visit(node, param);
		
		System.out.print(node.getString()+"(");

		if (node.getParametro() != null){
			int size = node.getParametro().size();
			for (int i=0;i<size;i++){
				node.getParametro().get(i).accept(this, param);
				if(size > 1 && i < size - 1){
					System.out.print(",");
				}
				
			}
		}
		
		System.out.print(")");

		if (node.getTipo() != null){
			System.out.print(":");
			node.getTipo().accept(this, param);
		}
		
		System.out.println("{");
		
		if (node.getAtributo() != null)
			for (Atributo child : node.getAtributo()){
				child.accept(this, param);
			}
				
		

		if (node.getSentencia() != null)
			for (Sentencia child : node.getSentencia()){
				child.accept(this, param);
			}
		System.out.println("}");

		return null;
	}

	//	class Struct { String string;  List<Definicion> definicion; }
	public Object visit(Struct node, Object param) {

		// super.visit(node, param);
		
		System.out.println("struct "+node.getString()+" {");

		if (node.getDefinicion() != null)
			for (Definicion child : node.getDefinicion()){
				child.accept(this, param);
			}
		
		System.out.println("};");

		return null;
	}

	//	class Atributo { Definicion definicion; }
	public Object visit(Atributo node, Object param) {

		// super.visit(node, param);
		System.out.print("var ");

		if (node.getDefinicion() != null)
			node.getDefinicion().accept(this, param);
		
		return null;
	}

	//	class Definicion { String nombre;  Tipo tipo; }
	public Object visit(Definicion node, Object param) {

		// super.visit(node, param);
		
		System.out.print(node.getNombre()+":");

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		System.out.println(";");
		
		
		return null;
	}

	//	class Parametro { String string;  Tipo tipo; }
	public Object visit(Parametro node, Object param) {

		// super.visit(node, param);
		System.out.print(node.getString()+":");

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

	//	class If { Expresion condic;  List<Sentencia> verdadero;  List<Sentencia> falso; }
	public Object visit(If node, Object param) {

		// super.visit(node, param);
		System.out.print("if(");

		if (node.getCondic() != null)
			node.getCondic().accept(this, param);
		
		System.out.println("){");

		if (node.getVerdadero() != null)
			for (Sentencia child : node.getVerdadero()){
				child.accept(this, param);
			}
		
		System.out.println("}");
		
		if (node.getFalso() != null){
			System.out.println("else {");
			for (Sentencia child : node.getFalso()){
				child.accept(this, param);
			}
		
		System.out.println("}");
		
		}

		return null;
	}

	//	class Read { Expresion expresion; }
	public Object visit(Read node, Object param) {

		// super.visit(node, param);
		System.out.print("read ");

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		
		System.out.println(";");

		return null;
	}

	//	class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {

		// super.visit(node, param);
		
		System.out.print("return ");

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);

		System.out.println(";");
		
		return null;
	}

	//	class Print { Expresion expresion; }
	public Object visit(Print node, Object param) {

		// super.visit(node, param);
		
		System.out.print("print ");

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		
		System.out.println(";");
		

		return null;
	}

	//	class While { Expresion expresion;  List<Sentencia> sentencia; }
	public Object visit(While node, Object param) {

		// super.visit(node, param);
		
		System.out.print("while(");

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		
		System.out.println("){");

		if (node.getSentencia() != null)
			for (Sentencia child : node.getSentencia()){
				child.accept(this, param);
			}
		
		System.out.println("}");

		return null;
	}

	//	class Invocar { String string;  List<Expresion> expresion; }
	public Object visit(Invocar node, Object param) {

		// super.visit(node, param);
		System.out.print(node.getString()+"(");

		if (node.getExpresion() != null)
			for (Expresion child : node.getExpresion())
				child.accept(this, param);
		
		System.out.print(")");

		return null;
	}
	
	//	class InvocarSentencia { Invocar invocar; }
	public Object visit(InvocarSentencia node, Object param) {

		// super.visit(node, param);

		if (node.getInvocar() != null)
			node.getInvocar().accept(this, param);
		
		System.out.println(";");

		return null;
	}

	//	class ExpresionBinaria { Expresion left;  String string;  Expresion right; }
	public Object visit(ExpresionBinaria node, Object param) {

		// super.visit(node, param);

		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		
		System.out.print(" "+node.getString()+" ");

		if (node.getRight() != null)
			node.getRight().accept(this, param);
		
		if(node.getString().equals("=")){
			System.out.println(";");
		}
		

		return null;
	}

	//	class Litent { int valor; }
	public Object visit(Litent node, Object param) {
		System.out.print(node.getValor());
		return null;
	}

	//	class Litchar { String valor; }
	public Object visit(Litchar node, Object param) {
		System.out.print(node.getValor());
		return null;
	}

	//	class Litreal { String valor; }
	public Object visit(Litreal node, Object param) {
		System.out.print(node.getValor());
		return null;
	}

	//	class Var { String nombre; }
	public Object visit(Var node, Object param) {
		System.out.print(node.getNombre());
		return null;
	}

	//	class Cast { Tipo tipo;  Expresion expresion; }
	public Object visit(Cast node, Object param) {

		// super.visit(node, param);
		
		System.out.print("cast<");

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		
		System.out.print(">(");

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		
		System.out.print(")");

		return null;
	}

	//	class ExpresionUnaria { Expresion expresion; }
	public Object visit(ExpresionUnaria node, Object param) {

		// super.visit(node, param);
		
		System.out.print("!");

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);

		return null;
	}

	//	class AccesoArray { Expresion contenedor;  Expresion posicion; }
	public Object visit(AccesoArray node, Object param) {

		// super.visit(node, param);
		
		
		if (node.getContenedor() != null)
			node.getContenedor().accept(this, param);
		
		System.out.print("[");

		if (node.getPosicion() != null)
			node.getPosicion().accept(this, param);
		
		System.out.print("]");

		return null;
	}

	//	class AccesoStruct { Expresion contenedor;  String atributo; }
	public Object visit(AccesoStruct node, Object param) {

		// super.visit(node, param);

		if (node.getContenedor() != null)
			node.getContenedor().accept(this, param);
		
		System.out.print("."+node.getAtributo());

		return null;
	}

	//	class Array { Litent litent;  Tipo tipo; }
	public Object visit(Array node, Object param) {

		// super.visit(node, param);
		System.out.print("[");

		if (node.getLitent() != null)
			node.getLitent().accept(this, param);
		
		System.out.print("]");

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

	//	class Tipoint {  }
	public Object visit(Tipoint node, Object param) {
		System.out.print("int");
		return null;
	}

	//	class Tiporeal {  }
	public Object visit(Tiporeal node, Object param) {
		System.out.print("float");
		return null;
	}

	//	class Tipochar {  }
	public Object visit(Tipochar node, Object param) {
		System.out.print("char");
		return null;
	}

	//	class Tipoident { String tipo; }
	public Object visit(Tipoident node, Object param) {
		System.out.print(node.getTipo());
		return null;
	}
	
	//	class EntreParentesis { Expresion contenido; }
	public Object visit(EntreParentesis node, Object param) {

		System.out.print("(");

		if (node.getContenido() != null)
			node.getContenido().accept(this, param);
		
		System.out.print(")");

		return null;
	}
}
