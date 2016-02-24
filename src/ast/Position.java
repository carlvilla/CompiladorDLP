package ast;

public class Position {

	public Position(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	
	public String toString() {
		return line + ":" + column;
	}

	private int line, column;
}


