package ast;

import visitor.*;

//	tipochar:tipo ->  ;

public class Tipochar extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	
	public int getSize(){
		return 1;
	}

	@Override
	public char getSufijo() {
		return 'b';
	}

	@Override
	public String getNombreMAPL() {
		return "char";
	}

}

