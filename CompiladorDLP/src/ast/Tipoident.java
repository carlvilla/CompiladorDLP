package ast;

import visitor.*;

//	tipoident:tipo ->  ;

public class Tipoident extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

}

