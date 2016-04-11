package ast;

import visitor.*;

//	tipovoid:tipo ->  ;

public class Tipovoid extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

}

