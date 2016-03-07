package ast;

import visitor.*;

//	invocarSentencia:sentencia -> invocar:invocar ;

public class InvocarSentencia extends AbstractSentencia {

	public InvocarSentencia(Invocar invocar) {
		this.invocar = invocar;

		searchForPositions(invocar);	// Obtener linea/columna a partir de los hijos
	}

	public InvocarSentencia(Object invocar) {
		this.invocar = (Invocar) invocar;

		searchForPositions(invocar);	// Obtener linea/columna a partir de los hijos
	}

	public Invocar getInvocar() {
		return invocar;
	}
	public void setInvocar(Invocar invocar) {
		this.invocar = invocar;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Invocar invocar;
}

