package ast;

import visitor.*;

//	tiporeal:tipo ->  ;

public class Tiporeal extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

}

