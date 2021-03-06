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

		int sumaTamaņoVariables = 0;
		
		//Variables globales
		for (Elemento child : node.getElemento()) {
			if(child instanceof Atributo){
			((Atributo)child).getDefinicion().setDireccion(sumaTamaņoVariables);
			sumaTamaņoVariables += ((Atributo)child).getDefinicion().getTipo().getSize();
			}
		
			child.accept(this, param);
			
		}
		
		return null;
	}
	
		
//		class Funcion { String string;  List<Parametro> parametro;  Tipo tipo;  List<Atributo> atributo;  List<Sentencia> sentencia; }
		public Object visit(Funcion node, Object param) {


			int sumaTamaņoVariablesLocales = 0;
			int sumaTamaņoVariablesParametros = 4;
			
			if (node.getParametros() != null){
				for(int i=node.getParametros().size()-1;i>-1;i--){
					node.getParametros().get(i).setDireccion(sumaTamaņoVariablesParametros);
					sumaTamaņoVariablesParametros+=node.getParametros().get(i).getTipo().getSize();					
				}						
			}

			if (node.getTipo() != null)
				node.getTipo().accept(this, param);

			if (node.getAtributo() != null){
				for (Atributo child : node.getAtributo()){
					sumaTamaņoVariablesLocales-=child.getDefinicion().getTipo().getSize();
					child.getDefinicion().setDireccion(sumaTamaņoVariablesLocales);
					
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
			
			int sumaTamaņoVariables = 0;

			if (node.getDefinicion() != null)
				for (Definicion child : node.getDefinicion()){
					child.setDireccion(sumaTamaņoVariables);
					sumaTamaņoVariables+=child.getTipo().getSize();
				}

			return null;
		}

		
		

}
