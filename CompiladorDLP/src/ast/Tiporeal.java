package ast;

import visitor.*;

//	tiporeal:tipo ->  ;

public class Tiporeal extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	
	public int getSize(){
		return 4;
	}

	@Override
	public char getSufijo() {
		return 'f';
	}

	@Override
	public String getNombreMAPL() {
		return "float";
	}

}

