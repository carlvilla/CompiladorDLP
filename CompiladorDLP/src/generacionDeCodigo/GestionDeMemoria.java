package generacionDeCodigo;

import ast.*;
import visitor.*;

/** 
 * Clase encargada de asignar direcciones a las variables 
 *
 * @author Raul Izquierdo
 */
public class GestionDeMemoria extends DefaultVisitor {

	// class Programa { List<DefVariable> definiciones; List<Sentencia> sentencias; }
	public Object visit(Programa node, Object param) {

		int sumaTama�oVariables = 0;
		
		//Variables globales
		for (Elemento child : node.getElemento()) {
			if(child instanceof Atributo){
			((Atributo)child).getDefinicion().setDireccion(sumaTama�oVariables);
			sumaTama�oVariables += ((Atributo)child).getDefinicion().getTipo().getSize();
			}
		
			child.accept(this, param);
			
		}
		
		return null;
	}
	
		
//		class Funcion { String string;  List<Parametro> parametro;  Tipo tipo;  List<Atributo> atributo;  List<Sentencia> sentencia; }
		public Object visit(Funcion node, Object param) {


			int sumaTama�oVariablesLocales = 0;
			int sumaTama�oVariablesParametros = 4;
			
			if (node.getParametro() != null){
				for(int i=node.getParametro().size()-1;i>-1;i--){
					Parametro parametro = node.getParametro().get(i);
					parametro.setDireccion(sumaTama�oVariablesParametros);
					sumaTama�oVariablesParametros+=parametro.getTipo().getSize();					
				}						
			}

			if (node.getTipo() != null)
				node.getTipo().accept(this, param);

			if (node.getAtributo() != null){
				for (Atributo child : node.getAtributo()){
					sumaTama�oVariablesLocales-=child.getDefinicion().getTipo().getSize();
					child.getDefinicion().setDireccion(sumaTama�oVariablesLocales);
					
				}
				
			}

			if (node.getSentencia() != null)
				for (Sentencia child : node.getSentencia())
					child.accept(this, param);

			return null;
		}
		
		//	class Struct { String string;  List<Definicion> definicion; }
		public Object visit(Struct node, Object param) {

			// super.visit(node, param);
			
			int sumaTama�oVariables = 0;

			if (node.getDefinicion() != null)
				for (Definicion child : node.getDefinicion()){
					child.setDireccion(sumaTama�oVariables);
					sumaTama�oVariables+=child.getTipo().getSize();
				}

			return null;
		}

		
		

}
