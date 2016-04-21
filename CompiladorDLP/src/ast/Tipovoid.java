package ast;

import visitor.*;

//	tipovoid:tipo ->  ;

public class Tipovoid extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	
	public int getSize(){
		return 0;
	}

	@Override
	public char getSufijo() {
		return 0;
	}

	@Override
	public String getNombreMAPL() {
		// TODO Auto-generated method stub
		return null;
	}

}

