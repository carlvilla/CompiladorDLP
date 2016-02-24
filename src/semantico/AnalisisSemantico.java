package semantico;

import main.*;
import ast.*;

/**
 * Esta clase coordina las dos fases del Análisis Semántico:
 * 1- Fase de Identificación
 * 2- Fase de Inferencia
 * 
 * No es necesario modificar esta clase. En su lugar hay que modificar las clases
 * que son llamadas desde aquí: "Identificacion.java" y "ComprobacionDeTipos.java"
 * 
 * @author Raúl Izquierdo
 *
 */
public class AnalisisSemantico {
	
	public AnalisisSemantico(GestorErrores gestor) {
		this.gestorErrores = gestor;
	}
	
	public void analiza(AST raiz) {
		Identificacion identificacion = new Identificacion(gestorErrores);
		raiz.accept(identificacion, null);

		if (gestorErrores.hayErrores())
			return;

		ComprobacionDeTipos comprobación = new ComprobacionDeTipos(gestorErrores);
		raiz.accept(comprobación, null);
	}

	private GestorErrores gestorErrores;
}
