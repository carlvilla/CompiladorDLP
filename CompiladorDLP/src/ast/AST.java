package ast;

import visitor.*;

public interface AST extends Traceable {
	public Object accept(Visitor visitor, Object param);
}

