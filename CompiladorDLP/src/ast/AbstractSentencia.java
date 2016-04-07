package ast;

public abstract class AbstractSentencia extends AbstractTraceable implements Sentencia {

	private Funcion miFuncion;

	public Funcion getMiFuncion() {
		return miFuncion;
	}

	public void setMiFuncion(Funcion miFuncion) {
		this.miFuncion = miFuncion;
	}
	
	
}

