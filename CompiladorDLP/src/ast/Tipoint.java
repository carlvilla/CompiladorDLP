package ast;

import visitor.*;

//	tipoint:tipo ->  ;

public class Tipoint extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

}
