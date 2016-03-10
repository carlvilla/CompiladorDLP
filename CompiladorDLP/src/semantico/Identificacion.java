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
			 
		Funcion definicion = funciones.get(funcion.getString());
		predicado(definicion == null, "Funcion ya definida: " + funcion.getString(), funcion.getStart());
		funciones.put(funcion.getString(),funcion);
		
		super.visit(funcion, param);
		 
		 return null;
	 }
	 
	 public Object visit(Invocar invocar, Object param) {
				
		Funcion definicion = funciones.get(invocar.getString());
		predicado(definicion != null, "Funcion no definida: " + invocar.getString(), invocar.getStart());
		invocar.setDefinicion(definicion); // Enlazar referencia con definición
		
		super.visit(invocar, param);
			 
		return null;
	}
	 
	 //Structs
	 
	 public Object visit(Struct struct, Object param) {
			
		Struct definicion = structs.get(struct.getString());
		predicado(definicion == null, "Struct ya definido: " + struct.getString(), struct.getStart());
		structs.put(struct.getString(),struct);
		
		super.visit(struct, param);
		 
		 return null;
	 }
	 
	 public Object visit(Tipoident identStruct, Object param) {
				
		Struct definicion = structs.get(identStruct.getTipo());
		predicado(definicion != null, "Struct no definido: " + identStruct.getTipo(),identStruct.getStart());
		identStruct.setDefinicion(definicion); // Enlazar referencia con definición
		
		return null;
	}
	 
	 
	 //Variables

	
	
	
	
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

}
