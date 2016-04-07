package semantico;

import java.util.*;

import ast.*;
import main.*;
import visitor.*;


public class Identificacion extends DefaultVisitor {

	public Identificacion(GestorErrores gestor) {
		this.gestorErrores = gestor;
	}

	
	
	/*
	 * Poner aquí los visit necesarios.
	 * Si se ha usado VGen solo hay que copiarlos de la clase 'visitor/_PlantillaParaVisitors.txt'.
	 */

	//Funciones
	
	 public Object visit(Funcion funcion, Object param) {
			 
		variables.set();
		
		Funcion definicion = funciones.get(funcion.getString());
		predicado(definicion == null, "Funcion ya definida: " + funcion.getString(), funcion.getStart());
		funciones.put(funcion.getString(),funcion);
		
		super.visit(funcion, param);
		 
		variables.reset();
		
		return null;
	 }
	 
		 
	 
		//	class InvocarSentencia { String string;  List<Expresion> expresion; }
		public Object visit(InvocarSentencia node, Object param) {

			// super.visit(node, param);
			
			Funcion definicion = funciones.get(node.getString());

			predicado(definicion != null, "Funcion no definida: " + node.getString(), node.getStart());
			node.setDefinicion(definicion); // Enlazar referencia con definición
			
			
			if (node.getExpresion() != null)
				for (Expresion child : node.getExpresion())
					child.accept(this, param);

			return null;
		}
	 
	 
	 
	 
	 
//		class InvocarFuncion { String string;  List<Expresion> expresion; }
		public Object visit(InvocarFuncion node, Object param) {

			// super.visit(node, param);
			
			Funcion definicion = funciones.get(node.getString());
			
			predicado(definicion != null, "Funcion no definida: " + node.getString(), node.getStart());
			node.setDefinicion(definicion); // Enlazar referencia con definición

			if (node.getExpresion() != null)
				for (Expresion child : node.getExpresion())
					child.accept(this, param);

			return null;
		}

	 

	 //Structs
	 
	 public Object visit(Struct struct, Object param) {
		 
		variables.set();
			
		Struct definicion = structs.get(struct.getString());
		predicado(definicion == null, "Struct ya definido: " + struct.getString(), struct.getStart());
		structs.put(struct.getString(),struct);
		
		super.visit(struct, param);
		 
		variables.reset();
		
		 return null;
	 }
	 
	 public Object visit(Tipoident identStruct, Object param) {
				
		Struct definicion = structs.get(identStruct.getTipo());
		predicado(definicion != null, "Struct no definido: " + identStruct.getTipo(),identStruct.getStart());
		identStruct.setDefinicion(definicion); // Enlazar referencia con definición
		
		super.visit(identStruct, param);
		
		return null;
	}
	 
	 
	 //Variables
	 
		public Object visit(Definicion node, Object param) {
			Definicion definicion = (Definicion) variables.getFromTop(node.getNombre());
			predicado(definicion == null, "Variable ya definida: " + node.getNombre(), node.getStart());
			variables.put(node.getNombre(), node);
			
			super.visit(node, param);
			
			return null;
		}


		public Object visit(Var variable, Object param) {
			Definicion definicion = (Definicion) variables.getFromAny(variable.getNombre());
			predicado(definicion != null, "Variable no definida: " + variable.getNombre(), variable.getStart());
			variable.setDefinicion(definicion); // Enlazar referencia con definición
			return null;
		}
		
	//Parametros
		
		//	class Parametro { String string;  Tipo tipo; }
		public Object visit(Parametro node, Object param) {

			Definicion definicionPar = new Definicion(node.getString(),node.getTipo());
			Definicion definicion = (Definicion) variables.getFromTop(node.getString());
			
			predicado(definicion==null,"Parámetro ya definido: "+ node.getString(), node.getStart());
			variables.put(node.getString(), definicionPar);
			
			super.visit(node, param);
			
			return null;
		}

	
	
	
	/**
	 * Método auxiliar opcional para ayudar a implementar los predicados de la Gramática Atribuida.
	 * 
	 * Ejemplo de uso:
	 * 	predicado(variables.get(nombre), "La variable no ha sido definida", expr.getStart());
	 * 	predicado(variables.get(nombre), "La variable no ha sido definida", null);
	 * 
	 * NOTA: El método getStart() indica la linea/columna del fichero fuente de donde se leyó el nodo.
	 * Si se usa VGen dicho método será generado en todos los nodos AST. Si no se quiere usar getStart() se puede pasar null.
	 * 
	 * @param condicion Debe cumplirse para que no se produzca un error
	 * @param mensajeError Se imprime si no se cumple la condición
	 * @param posicionError Fila y columna del fichero donde se ha producido el error. Es opcional (acepta null)
	 */
	private void predicado(boolean condicion, String mensajeError, Position posicionError) {
		if (!condicion)
			gestorErrores.error("Identificación", mensajeError, posicionError);
	}


	private GestorErrores gestorErrores;
	
	private Map<String, Funcion> funciones = new HashMap<String, Funcion>();
	private Map<String, Struct> structs = new HashMap<String, Struct>();
	ContextMap variables = new ContextMap();

}
