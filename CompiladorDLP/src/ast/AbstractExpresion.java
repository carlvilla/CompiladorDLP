package ast;

public abstract class AbstractExpresion extends AbstractTraceable implements Expresion {

	private Tipo tipo;
	private Boolean modificable;
	
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public Boolean getModificable() {
		return modificable;
	}
	public void setModificable(Boolean modificable) {
		this.modificable = modificable;
	}
	
	
}

