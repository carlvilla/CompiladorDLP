package semantico;

import java.util.List;

import javax.swing.tree.AbstractLayoutCache.NodeDimensions;

import ast.*;
import main.*;
import visitor.*;

public class ComprobacionDeTipos extends DefaultVisitor {

	public ComprobacionDeTipos(GestorErrores gestor) {
		this.gestorErrores = gestor;
	}

	/*
	 * Poner aquí los visit necesarios. Si se ha usado VGen solo hay que
	 * copiarlos de la clase 'visitor/_PlantillaParaVisitors.txt'.
	 */

	// class Programa { List<Elemento> elemento; }
	public Object visit(Programa node, Object param) {

		// super.visit(node, param);

		if (node.getElemento() != null)
			for (Elemento child : node.getElemento())
				child.accept(this, param);

		return null;
	}

	// class Funcion { String string; List<Parametro> parametro; Tipo tipo;
	// List<Atributo> atributo; List<Sentencia> sentencia; }
	public Object visit(Funcion node, Object param) {

		// super.visit(node, param);

		if (node.getParametro() != null)
			for (Parametro parametro : node.getParametro()) {
				predicado(simple(parametro.getTipo()), "Función:Los parámetros deben de ser de tipo simple",
						node.getStart());
			}

		predicado(simple(node.getTipo()) || node.getTipo() instanceof Tipovoid,
				"Función:El tipo de retorno de la función debe ser simple o void", node.getStart());

		if (node.getParametro() != null)
			for (Parametro child : node.getParametro())
				child.accept(this, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		if (node.getAtributo() != null)
			for (Atributo child : node.getAtributo())
				child.accept(this, param);

		if (node.getSentencia() != null)
			for (Sentencia child : node.getSentencia()) {
				child.setMiFuncion((Funcion) node);
				child.accept(this, param);
			}

		return null;
	}

	// class Struct { String string; List<Definicion> definicion; }
	public Object visit(Struct node, Object param) {

		// super.visit(node, param);

		if (node.getDefinicion() != null)
			for (Definicion child : node.getDefinicion())
				child.accept(this, param);

		return null;
	}

	// class Atributo { Definicion definicion; }
	public Object visit(Atributo node, Object param) {

		// super.visit(node, param);

		if (node.getDefinicion() != null)
			node.getDefinicion().accept(this, param);

		return null;
	}

	// class Definicion { String nombre; Tipo tipo; }
	public Object visit(Definicion node, Object param) {

		// super.visit(node, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

	// class Parametro { String string; Tipo tipo; }
	public Object visit(Parametro node, Object param) {

		// super.visit(node, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

	// class If { Expresion condic; List<Sentencia> verdadero; List<Sentencia>
	// falso; }
	public Object visit(If node, Object param) {

		if (node.getCondic() != null)
			node.getCondic().accept(this, param);

		if (node.getVerdadero() != null)
			for (Sentencia child : node.getVerdadero()) {
				child.setMiFuncion(node.getMiFuncion());
				child.accept(this, param);
			}

		if (node.getFalso() != null)
			for (Sentencia child : node.getFalso()) {
				child.setMiFuncion(node.getMiFuncion());
				child.accept(this, param);

			}

		predicado(node.getCondic().getTipo() instanceof Tipoint, "If:El tipo de la condición debe ser Tipoint",
				node.getStart());

		return null;
	}

	// class Read { Expresion expresion; }
	public Object visit(Read node, Object param) {

		// super.visit(node, param);

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);

		predicado(simple(node.getExpresion().getTipo()), "Read:El tipo de la expresión debe ser simple",
				node.getStart());

		predicado(node.getExpresion().getModificable(), "Read:La expresión debe ser modificable", node.getStart());

		return null;
	}

	// class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {

		// super.visit(node, param);

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);

		if (node.getMiFuncion().getTipo() instanceof Tipovoid) {

			predicado(node.getExpresion().getTipo() instanceof Tipovoid, "return:El tipo de retorno de la función "
					+ "es void por lo que el return no debe devolver ningún objeto", node.getStart());

		}

		else {

			predicado(
					node.getExpresion() != null
							&& comprobarTipos(node.getExpresion().getTipo(), node.getMiFuncion().getTipo()),
					"Return: El tipo definido en la función del objeto devuelto "
							+ "no coincide con el tipo del objeto devuelto por el return",
					node.getStart());

		}

		return null;
	}

	// class Print { Expresion expresion; }
	public Object visit(Print node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);

		//Si la expresión pasada es erronea no se puede determinar un tipo para esta,
		//en tal caso el tipo de la expresión será nulo y ya se habrá mostrado el 
		//error pertinente
		if(node.getExpresion().getTipo()!=null)
		predicado(simple(node.getExpresion().getTipo()), "Print:El tipo de la expresión debe ser simple",
				node.getStart());

		return null;
	}

	// class While { Expresion expresion; List<Sentencia> sentencia; }
	public Object visit(While node, Object param) {

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);

		if (node.getSentencia() != null)
			for (Sentencia child : node.getSentencia()) {
				child.setMiFuncion(node.getMiFuncion());
				child.accept(this, param);
			}

		predicado(node.getExpresion().getTipo() instanceof Tipoint, "While:El tipo de la condición debe ser Tipoint",
				node.getStart());

		return null;
	}

	// class InvocarSentencia { String string; List<Expresion> expresion; }
	public Object visit(InvocarSentencia node, Object param) {

		// super.visit(node, param);

		if (node.getExpresion() != null)
			for (Expresion child : node.getExpresion())
				child.accept(this, param);

		predicado(node.getExpresion().size() == node.getDefinicion().getParametro().size(),
				"Invocar Sentencia:El número de parámetros pasados a la función no "
						+ "coincide con el número de parámetros que tiene definidos la función",
				node.getStart());

		if (node.getExpresion().size() == node.getDefinicion().getParametro().size())
			for (int i = 0; i < node.getExpresion().size(); i++) {

				Tipo invocacion = node.getExpresion().get(i).getTipo();
				Tipo funcion = node.getDefinicion().getParametro().get(i).getTipo();

				predicado(invocacion.getClass().isInstance(funcion),
						"Invocar Sentencia: Los tipos de los parámetros definidos en la invocación de "
								+ "la función no coinciden con los definidos en la función",
						node.getStart());
			}

		// }

		return null;
	}

	public Object visit(InvocarFuncion node, Object param) {

		// super.visit(node, param);

		if (node.getExpresion() != null)
			for (Expresion child : node.getExpresion())
				child.accept(this, param);

		// if(node.getDefinicion().getParametro()!=null){
		predicado(node.getExpresion().size() == node.getDefinicion().getParametro().size(),
				"Invocar Funcion:El número de parámetros pasados a la función no "
						+ "coincide con el número de parámetros que tiene definidos la función",
				node.getStart());

		if (node.getExpresion().size() == node.getDefinicion().getParametro().size())
			for (int i = 0; i < node.getExpresion().size(); i++) {

				Tipo invocacion = node.getExpresion().get(i).getTipo();
				Tipo funcion = node.getDefinicion().getParametro().get(i).getTipo();

				predicado(invocacion.getClass().isInstance(funcion),
						"Invocar Sentencia: Los tipos de los parámetros definidos en la invocación de "
								+ "la función no coinciden con los definidos en la función",
						node.getStart());
			}
		// }
		/*
		 * else{ predicado(node.getExpresion().size() == 0,
		 * "Invocar Funcion:No se pueden pasar parámetros a una función que no tiene "
		 * + "ninguno definido", node.getStart()); }
		 * 
		 */
		node.setTipo(node.getDefinicion().getTipo());

		return null;
	}

	// class ExpresionBinaria { Expresion left; String string; Expresion right;
	// }
	public Object visit(ExpresionBinaria node, Object param) {

		// super.visit(node, param);

		if (node.getLeft() != null)	
			node.getLeft().accept(this, param);

		if (node.getRight() != null)
			node.getRight().accept(this, param);

		/*
		 * predicado(comprobarTipos(node.getLeft().getTipo(),node.getRight().
		 * getTipo()),
		 * "Expresion Binaria:Los tipos de las expresión a la izquierda y derecha del operador"
		 * + " no coinciden", node.getStart());
		 */

		if (!node.getString().equals("=")) {
			predicado((node.getLeft().getTipo() instanceof Tipoint || node.getLeft().getTipo() instanceof Tiporeal)
					&& node.getLeft().getTipo().getClass().isInstance(node.getRight().getTipo()),
					"Expresion Binaria: Las expresiones a la derecha e izquierda del operador"
							+ " deben de ser tipoint o tiporeal en una operación aritmética",
					node.getStart());
		}

		else {

			predicado(node.getLeft().getModificable(),
					"Expresion Binaria: En una asignación se tiene que poder modificar "
							+ "el valor de la expresión de la izquierda",
					node.getStart());

			//En el caso de que la expresión de la izquierda o derecha sea incorrecta y no se haya podido 
			//resolver su tipo, este será nulo y ya se habrá lanzado el error pertinente
			if(node.getLeft().getTipo()!=null && node.getRight().getTipo()!=null)
			predicado(comprobarTipos(node.getLeft().getTipo(), node.getRight().getTipo()),
					"Expresion Binaria:Los tipos de las expresión a la izquierda y derecha del operador"
							+ " deben coincidir en una asignación",
					node.getStart());

		}

		node.setTipo(node.getLeft().getTipo());
		node.setModificable(false);

		return null;
	}

	// class Litent { int valor; }
	public Object visit(Litent node, Object param) {
		node.setTipo(new Tipoint());
		node.setModificable(false);
		return null;
	}

	// class Litchar { String valor; }
	public Object visit(Litchar node, Object param) {
		node.setTipo(new Tipochar());
		node.setModificable(false);
		return null;
	}

	// class Litreal { String valor; }
	public Object visit(Litreal node, Object param) {
		node.setTipo(new Tiporeal());
		node.setModificable(false);
		return null;
	}

	// class Var { String nombre; }
	public Object visit(Var node, Object param) {
		node.setTipo(node.getDefinicion().getTipo());
		node.setModificable(true);
		return null;
	}

	// class Cast { Tipo tipo; Expresion expresion; }
	public Object visit(Cast node, Object param) {

		// super.visit(node, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);

		predicado(simple(node.getTipo()), "Cast: El tipo a convertir debe ser simple", node.getStart());

		predicado(simple(node.getExpresion().getTipo()), "Cast: El tipo del objeto a convertir debe ser simple", node.getStart());

		predicado(!node.getTipo().getClass().isInstance(node.getExpresion().getTipo()),
				"Cast: Deben ser distintos tipos", node.getStart());

		node.setModificable(false);

		return null;
	}

	// class ExpresionUnaria { Expresion expresion; }
	public Object visit(ExpresionUnaria node, Object param) {

		// super.visit(node, param);
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);

		predicado(comprobarTipos(node.getExpresion().getTipo(), new Tipoint()),
				"Expresion unaria:El tipo de la expresion debe ser Tipoint", node.getStart());

		node.setTipo(new Tipoint());
		node.setModificable(false);

		return null;
	}

	// class ExpresionLogica { Expresion left; String string; Expresion right; }
	public Object visit(ExpresionLogica node, Object param) {

		// super.visit(node, param);

		if (node.getLeft() != null)
			node.getLeft().accept(this, param);

		if (node.getRight() != null)
			node.getRight().accept(this, param);

		predicado(node.getLeft().getTipo().getClass().isInstance(node.getRight().getTipo()),
				"Expresion lógica:El tipo de los dos operandos debe ser igual", node.getStart());

		predicado(node.getLeft().getTipo() instanceof Tipoint,
				"Expresion lógica:El tipo de los operandos debe ser Tipoint", node.getStart());

		node.setTipo(new Tipoint());
		node.setModificable(false);

		return null;
	}

	// class AccesoArray { Expresion contenedor; Expresion posicion; }
	public Object visit(AccesoArray node, Object param) {

		// super.visit(node, param);

		if (node.getContenedor() != null)
			node.getContenedor().accept(this, param);

		if (node.getPosicion() != null)
			node.getPosicion().accept(this, param);

		predicado(node.getContenedor().getTipo() instanceof Array, "Acceso Array:El tipo del objeto debe ser Array",
				node.getStart());

		predicado(node.getPosicion().getTipo() instanceof Tipoint, "Acceso Array:El tipo de la posición debe ser Tipoint",
				node.getStart());
		if (node.getContenedor().getTipo() instanceof Array)
			node.setTipo(((Array) node.getContenedor().getTipo()).getTipo());

		node.setModificable(false);

		return null;
	}

	// class AccesoStruct { Expresion contenedor; String atributo; }
	public Object visit(AccesoStruct node, Object param) {

		// super.visit(node, param);

		if (node.getContenedor() != null)
			node.getContenedor().accept(this, param);

		predicado(node.getContenedor().getTipo() instanceof Tipoident,
				"Acceso Struct:El tipo del contenedor debe ser Tipoident", node.getStart());
 
		if (node.getContenedor().getTipo() instanceof Tipoident) {
			List<Definicion> definiciones = ((Tipoident)(node.getContenedor().getTipo())).getDefinicion().getDefinicion();

			for (Definicion def : definiciones) {
				if (def.getNombre().equals(node.getAtributo())) {
					node.setTipo(def.getTipo());
				}
			}
			
			predicado(node.getTipo()!=null, "Acceso Struct:Campo no definido", node.getStart());
			
		}
		
		node.setModificable(false);

		return null;
	}

	// class EntreParentesis { Expresion contenido; }
	public Object visit(EntreParentesis node, Object param) {

		// super.visit(node, param);

		if (node.getContenido() != null)
			node.getContenido().accept(this, param);

		node.setTipo(node.getContenido().getTipo());
		node.setModificable(false);

		return null;
	}

	// class Array { Litent litent; Tipo tipo; }
	public Object visit(Array node, Object param) {

		// super.visit(node, param);

		if (node.getLitent() != null)
			node.getLitent().accept(this, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

	// class Tipoint { }
	public Object visit(Tipoint node, Object param) {
		return null;
	}

	// class Tiporeal { }
	public Object visit(Tiporeal node, Object param) {
		return null;
	}

	// class Tipochar { }
	public Object visit(Tipochar node, Object param) {
		return null;
	}

	// class Tipoident { String tipo; }
	public Object visit(Tipoident node, Object param) {
		return null;
	}

	/**
	 * Método auxiliar opcional para ayudar a implementar los predicados de la
	 * Gramática Atribuida.
	 * 
	 * Ejemplo de uso (suponiendo implementado el método "esPrimitivo"):
	 * predicado(esPrimitivo(expr.tipo),
	 * "La expresión debe ser de un tipo primitivo", expr.getStart());
	 * predicado(esPrimitivo(expr.tipo),
	 * "La expresión debe ser de un tipo primitivo", null);
	 * 
	 * NOTA: El método getStart() indica la linea/columna del fichero fuente de
	 * donde se leyó el nodo. Si se usa VGen dicho método será generado en todos
	 * los nodos AST. Si no se quiere usar getStart() se puede pasar null.
	 * 
	 * @param condicion
	 *            Debe cumplirse para que no se produzca un error
	 * @param mensajeError
	 *            Se imprime si no se cumple la condición
	 * @param posicionError
	 *            Fila y columna del fichero donde se ha producido el error. Es
	 *            opcional (acepta null)
	 */
	private void predicado(boolean condicion, String mensajeError, Position posicionError) {
		if (!condicion)
			gestorErrores.error("Comprobación de tipos", mensajeError, posicionError);
	}

	private boolean simple(Tipo tipo) {
		return tipo instanceof Tipochar || tipo instanceof Tipoint || tipo instanceof Tiporeal;
	}

	public boolean comprobarTipos(Tipo tipo1, Tipo tipo2) {
		return tipo1.getClass().isInstance(tipo2.getClass());

	}

	private GestorErrores gestorErrores;
}
