package ast;

public interface Expresion extends AST {
	
	public Tipo getTipo();
	public void setTipo(Tipo tipo);
	public void setModificable(Boolean modificable);
	public Boolean getModificable();

}

