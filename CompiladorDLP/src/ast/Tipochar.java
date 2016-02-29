package ast;

import visitor.*;

//	tipochar:tipo ->  ;

public class Tipochar extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

}

