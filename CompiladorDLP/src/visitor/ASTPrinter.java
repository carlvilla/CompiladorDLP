package visitor;

import java.io.*;

import ast.*;
import java.util.*;

/**
 * ASTPrinter. Utilidad que ayuda a validar un arbol AST:
 * 	-	Muestra la estructura del árbol en HTML.
 * 	-	Destaca los hijos/propiedades a null.
 * 	-	Muestra a qué texto apuntan las posiciones de cada nodo (linea/columna)
 * 		ayudando a decidir cual de ellas usar en los errores y generación de código.
 * 
 * Esta clase se genera con VGen. El uso de esta clase es opcional (puede eliminarse del proyecto). 
 * 
 */
public class ASTPrinter extends DefaultVisitor {

	/**
	 * toHtml. Muestra la estructura del AST indicando qué hay en las posiciones (linea y columna) de cada nodo.
	 * 
	 * @param sourceFile	El fichero del cual se ha obtenido el AST
	 * @param raiz				El AST creado a partir de sourceFile
	 * @param filename		Nombre del fichero HMTL a crear con la traza del AST
	 */

	public static void toHtml(String sourceFile, AST raiz, String filename) {
		toHtml(sourceFile, raiz, filename, 4);
	}
	
	public static void toHtml(AST raiz, String filename) {
		toHtml(null, raiz, filename);
	}

	// tabWidth deberían ser los espacios correspondientes a un tabulador en eclipse.
	// Normalmente no será necesario especificarlo. Usar mejor los dos métodos anteriores.
	public static void toHtml(String sourceFile, AST raiz, String filename, int tabWidth) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(filename.endsWith(".html") ? filename : filename + ".html"));
			generateHeader(writer);
			writer.println("[ASTPrinter] -------------------------------- line:col  line:col");
			if (raiz != null) {
				ASTPrinter tracer = new ASTPrinter(writer, loadLines(sourceFile, tabWidth));
				raiz.accept(tracer, new Integer(0));
			} else
				writer.println("raiz == null");
			writer.println(ls + ls + "[ASTPrinter] --------------------------------");
			generateFooter(writer);
			writer.close();
			System.out.println(ls + "ASTPrinter: Fichero '" + filename + ".html' generado con éxito. Abra el fichero para validar el árbol AST generado.");
		} catch (IOException e) {
			System.out.println(ls + "ASTPrinter: No se ha podido crear el fichero " + filename);
			e.printStackTrace();
		}
	}

	private static void generateHeader(PrintWriter writer) {
		writer.println("<html>\r\n" +
				"<head>\r\n" +
				"<style type=\"text/css\">\r\n" +
				".value { font-weight: bold; }\r\n" +
				".dots { color: #888888; }\r\n" +
				".type { color: #BBBBBB; }\r\n" +
				".pos { color: #CCCCCC; }\r\n" +
				".sourceText { color: #BBBBBB; }\r\n" +
				".posText {\r\n" +
				"	color: #BBBBBB;\r\n" +
				"	text-decoration: underline; font-weight: bold;\r\n" +
				"}\r\n" +
				".null {\r\n" +
				"	color: #FF0000;\r\n" +
				"	font-weight: bold;\r\n" +
				"	font-style: italic;\r\n" +
				"}\r\n" +
			//	 "pre { font-family: Arial, Helvetica, sans-serif; font-size: 11px; }\r\n" +
			//	"pre { font-size: 11px; }\r\n" +
				"</style>\r\n" +
				"</head>\r\n" +
				"\r\n" +
				"<body><pre>");
	}

	private static void generateFooter(PrintWriter writer) {
		writer.println("</pre>\r\n" +
				"</body>\r\n" +
				"</html>");
	}

	private ASTPrinter(PrintWriter writer, List<String> sourceLines) {
		this.writer = writer;
		this.sourceLines = sourceLines;
	}

	// ----------------------------------------------

	//	class Programa { List<Elemento> elemento; }
	public Object visit(Programa node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Programa", node, false);

		visit(indent + 1, "elemento", "List<Elemento>",node.getElemento());
		return null;
	}

	//	class Funcion { String string;  List<Parametro> parametro;  Tipo tipo;  List<Atributo> atributo;  List<Sentencia> sentencia; }
	public Object visit(Funcion node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Funcion", node, false);

		print(indent + 1, "string", "String", node.getString());
		visit(indent + 1, "parametro", "List<Parametro>",node.getParametro());
		visit(indent + 1, "tipo", "Tipo",node.getTipo());
		visit(indent + 1, "atributo", "List<Atributo>",node.getAtributo());
		visit(indent + 1, "sentencia", "List<Sentencia>",node.getSentencia());
		return null;
	}

	//	class Struct { String string;  List<Definicion> definicion; }
	public Object visit(Struct node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Struct", node, false);

		print(indent + 1, "string", "String", node.getString());
		visit(indent + 1, "definicion", "List<Definicion>",node.getDefinicion());
		return null;
	}

	//	class Atributo { Definicion definicion; }
	public Object visit(Atributo node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Atributo", node, false);

		visit(indent + 1, "definicion", "Definicion",node.getDefinicion());
		return null;
	}

	//	class Definicion { String nombre;  Tipo tipo; }
	public Object visit(Definicion node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Definicion", node, false);

		print(indent + 1, "nombre", "String", node.getNombre());
		visit(indent + 1, "tipo", "Tipo",node.getTipo());
		return null;
	}

	//	class Parametro { String string;  Tipo tipo; }
	public Object visit(Parametro node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Parametro", node, false);

		print(indent + 1, "string", "String", node.getString());
		visit(indent + 1, "tipo", "Tipo",node.getTipo());
		return null;
	}

	//	class If { Expresion condic;  List<Sentencia> verdadero;  List<Sentencia> falso; }
	public Object visit(If node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "If", node, false);

		visit(indent + 1, "condic", "Expresion",node.getCondic());
		visit(indent + 1, "verdadero", "List<Sentencia>",node.getVerdadero());
		visit(indent + 1, "falso", "List<Sentencia>",node.getFalso());
		return null;
	}

	//	class Read { Expresion expresion; }
	public Object visit(Read node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Read", node, false);

		visit(indent + 1, "expresion", "Expresion",node.getExpresion());
		return null;
	}

	//	class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Return", node, false);

		visit(indent + 1, "expresion", "Expresion",node.getExpresion());
		return null;
	}

	//	class Print { Expresion expresion; }
	public Object visit(Print node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Print", node, false);

		visit(indent + 1, "expresion", "Expresion",node.getExpresion());
		return null;
	}

	//	class While { Expresion expresion;  List<Sentencia> sentencia; }
	public Object visit(While node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "While", node, false);

		visit(indent + 1, "expresion", "Expresion",node.getExpresion());
		visit(indent + 1, "sentencia", "List<Sentencia>",node.getSentencia());
		return null;
	}

	//	class Invocar { String string;  List<Expresion> expresion; }
	public Object visit(Invocar node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Invocar", node, false);

		print(indent + 1, "string", "String", node.getString());
		visit(indent + 1, "expresion", "List<Expresion>",node.getExpresion());
		return null;
	}

	//	class ExpresionBinaria { Expresion left;  String string;  Expresion right; }
	public Object visit(ExpresionBinaria node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "ExpresionBinaria", node, false);

		visit(indent + 1, "left", "Expresion",node.getLeft());
		print(indent + 1, "string", "String", node.getString());
		visit(indent + 1, "right", "Expresion",node.getRight());
		return null;
	}

	//	class Litent { int valor; }
	public Object visit(Litent node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Litent", node, false);

		print(indent + 1, "valor", "int", node.getValor());
		return null;
	}

	//	class Litchar { String valor; }
	public Object visit(Litchar node, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "Litchar", node, "valor", node.getValor());
		return null;
	}

	//	class Litreal { String valor; }
	public Object visit(Litreal node, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "Litreal", node, "valor", node.getValor());
		return null;
	}

	//	class Var { String nombre; }
	public Object visit(Var node, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "Var", node, "nombre", node.getNombre());
		return null;
	}

	//	class Cast { Tipo tipo;  Expresion expresion; }
	public Object visit(Cast node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Cast", node, false);

		visit(indent + 1, "tipo", "Tipo",node.getTipo());
		visit(indent + 1, "expresion", "Expresion",node.getExpresion());
		return null;
	}

	//	class ExpresionUnaria { Expresion expresion; }
	public Object visit(ExpresionUnaria node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "ExpresionUnaria", node, false);

		visit(indent + 1, "expresion", "Expresion",node.getExpresion());
		return null;
	}

	//	class AccesoArray { Expresion contenedor;  Expresion posicion; }
	public Object visit(AccesoArray node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "AccesoArray", node, false);

		visit(indent + 1, "contenedor", "Expresion",node.getContenedor());
		visit(indent + 1, "posicion", "Expresion",node.getPosicion());
		return null;
	}

	//	class AccesoStruct { Expresion contenedor;  String atributo; }
	public Object visit(AccesoStruct node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "AccesoStruct", node, false);

		visit(indent + 1, "contenedor", "Expresion",node.getContenedor());
		print(indent + 1, "atributo", "String", node.getAtributo());
		return null;
	}

	//	class Array { Litent litent;  Tipo tipo; }
	public Object visit(Array node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Array", node, false);

		visit(indent + 1, "litent", "Litent",node.getLitent());
		visit(indent + 1, "tipo", "Tipo",node.getTipo());
		return null;
	}

	//	class Tipoint {  }
	public Object visit(Tipoint node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Tipoint", node, true);

		return null;
	}

	//	class Tiporeal {  }
	public Object visit(Tiporeal node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Tiporeal", node, true);

		return null;
	}

	//	class Tipochar {  }
	public Object visit(Tipochar node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Tipochar", node, true);

		return null;
	}

	//	class Tipoident { String tipo; }
	public Object visit(Tipoident node, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "Tipoident", node, "tipo", node.getTipo());
		return null;
	}

	// -----------------------------------------------------------------
	// Métodos invocados desde los métodos visit -----------------------

	private void printName(int indent, String name, AST node, boolean empty) {
		String text = ls + tabula(indent) + name + " &rarr;  ";
		text = String.format("%1$-" + 93 + "s", text);
		if (empty)
			text = text.replace(name, valueTag(name));
		writer.print(text + getPosition(node));
	}

	private void print(int indent, String name, String type, Object value) {
		write(indent, formatValue(value) + "  " + typeTag(type));
	}

	private void print(int indent, String attName, String type, List<? extends Object> children) {
		write(indent, attName + "  " + typeTag(type) + " = ");
		if (children != null)
			for (Object child : children)
				write(indent + 1, formatValue(child));
		else
			writer.print(" " + valueTag(null));
	}

	// Versión compacta de una linea para nodos que solo tienen un atributo String
	private void printCompact(int indent, String nodeName, AST node, String attName, Object value) {
		String fullName = nodeName + '.' + attName;
		String text = ls + tabula(indent) + '\"' + value + "\"  " + fullName;
		text = String.format("%1$-" + 88 + "s", text);
		// text = text.replace(value.toString(), valueTag(value));
		text = text.replace(fullName, typeTag(fullName));
		writer.print(text + getPosition(node));
	}

	private void visit(int indent, String attName, String type, List<? extends AST> children) {
		write(indent, attName + "  " + typeTag(type) + " = ");
		if (children != null)
			for (AST child : children)
				child.accept(this, indent + 1);
		else
			writer.print(" " + valueTag(null));
	}

	private void visit(int indent, String attName, String type, AST child) {
		if (child != null)
			child.accept(this, new Integer(indent));
		else
			write(indent, valueTag(null) + "  " + attName + ':' + typeTag(type));
	}

	// -----------------------------------------------------------------
	// Métodos auxiliares privados -------------------------------------

	private void write(int indent, String text) {
		writer.print(ls + tabula(indent) + text);
	}

	private static String tabula(int count) {
		StringBuffer cadena = new StringBuffer("<span class=\"dots\">");
		for (int i = 0; i < count; i++)
			cadena.append(i % 2 == 0 && i > 0 ? "|  " : "·  ");
		return cadena.toString() + "</span>";
	}

	private String typeTag(String type) {
		if (type.equals("String"))
			return "";
		return "<span class=\"type\">" + type.replace("<", "&lt;").replace(">", "&gt;") + "</span>";
	}

	private String valueTag(Object value) {
		if (value == null)
			return "<span class=\"null\">null</span>";
		return "<span class=\"value\">" + value + "</span>";
	}

	private String formatValue(Object value) {
		String text = valueTag(value);
		if (value instanceof String)
			text = "\"" + text + '"';
		return text;
	}


	// -----------------------------------------------------------------
	// Métodos para mostrar las Posiciones -----------------------------

	private String getPosition(Traceable node) {
		String text = node.getStart() + "  " + node.getEnd();
		text = "<span class=\"pos\">" + String.format("%1$-" + 13 + "s", text) + "</span>";
		text = text.replace("null", "<span class=\"null\">null</span>");
		String sourceText = findSourceText(node);
		if (sourceText != null)
			text += sourceText;
		return text;
	}

	private String findSourceText(Traceable node) {
		if (sourceLines == null)
			return null;

		Position start = node.getStart();
		Position end = node.getEnd();
		if (start == null || end == null)
			return null;

		String afterText, text, beforeText;
		if (start.getLine() == end.getLine()) {
			String line = sourceLines.get(start.getLine() - 1);
			afterText = line.substring(0, start.getColumn() - 1);
			text = line.substring(start.getColumn() - 1, end.getColumn());
			beforeText = line.substring(end.getColumn());
		} else {
			String firstLine = sourceLines.get(start.getLine() - 1);
			String lastLine = sourceLines.get(end.getLine() - 1);

			afterText = firstLine.substring(0, start.getColumn() - 1);

			text = firstLine.substring(start.getColumn() - 1);
			text += "</span><span class=\"sourceText\">" + " ... " + "</span><span class=\"posText\">";
			text += lastLine.substring(0, end.getColumn()).replaceAll("^\\s+", "");

			beforeText = lastLine.substring(end.getColumn());
		}
		return "<span class=\"sourceText\">" + afterText.replaceAll("^\\s+", "")
				+ "</span><span class=\"posText\">" + text
				+ "</span><span class=\"sourceText\">" + beforeText + "</span>";
	}

	private static List<String> loadLines(String sourceFile, int tabWidth) {
		if (sourceFile == null)
			return null;
		try {
			String spaces = new String(new char[tabWidth]).replace("\0", " ");
			
			List<String> lines = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(sourceFile));
			String line;
			while ((line = br.readLine()) != null)
				lines.add(line.replace("\t", spaces));
			br.close();
			return lines;
		} catch (FileNotFoundException e) {
			System.out.println("Warning. No se pudo encontrar el fichero fuente '" + sourceFile + "'. No se mostrará informaicón de posición.");
			return null;
		} catch (IOException e) {
			System.out.println("Warning. Error al leer del fichero fuente '" + sourceFile + "'. No se mostrará informaicón de posición.");
			return null;
		}
	}


	private List<String> sourceLines;
	private static String ls = System.getProperty("line.separator");
	private PrintWriter writer;
}

