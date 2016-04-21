//### This file created by BYACC 1.8(/Java extension  1.14)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 3 "sintac.y"
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
//#line 24 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:Object
String   yytext;//user variable to return contextual strings
Object yyval; //used to return semantic vals from action routines
Object yylval;//the 'lval' (result) I got from yylex()
Object valstk[] = new Object[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new Object();
  yylval=new Object();
  valptr=-1;
}
final void val_push(Object val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    Object[] newstack = new Object[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final Object val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final Object val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short AND=257;
public final static short OR=258;
public final static short MENORIGUAL=259;
public final static short MAYORIGUAL=260;
public final static short DISTINTO=261;
public final static short IGUAL=262;
public final static short IDENT=263;
public final static short INT=264;
public final static short REAL=265;
public final static short CHAR=266;
public final static short VOID=267;
public final static short LITERALINT=268;
public final static short STRUCT=269;
public final static short VAR=270;
public final static short READ=271;
public final static short PRINT=272;
public final static short WHILE=273;
public final static short IF=274;
public final static short ELSE=275;
public final static short RETURN=276;
public final static short LITERALREAL=277;
public final static short LITERALCHAR=278;
public final static short CAST=279;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    2,    3,    3,    8,    8,
    6,    6,   10,   10,    7,    7,    7,    7,    7,    7,
    4,    5,   11,   11,   12,    9,    9,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   15,   15,
   16,   16,
};
final static short yylen[] = {                            2,
    1,    0,    2,    1,    1,    1,   10,    8,    0,    2,
    1,    0,    3,    5,    1,    1,    1,    1,    1,    4,
    6,    2,    0,    2,    4,    2,    0,    3,    3,    7,
    7,   11,    4,    3,    2,    5,    1,    1,    1,    1,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    2,    7,    3,    4,    3,    4,    1,    0,
    1,    3,
};
final static short yydefred[] = {                         2,
    0,    0,    0,    0,    0,    3,    4,    5,    6,    0,
    0,    0,   22,    0,    0,    0,   23,    0,    0,    0,
    0,    0,    0,   15,   16,   17,   18,   19,    0,   13,
    0,    9,    0,    0,   24,    0,   25,    0,    0,    0,
   21,    0,    9,   10,    0,   14,   20,    0,    0,    0,
    0,    8,   37,    0,    0,    0,    0,    0,   38,   39,
    0,   26,    0,    0,    0,   53,    0,    0,    0,    0,
    0,    0,   35,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    7,    0,    0,    0,    0,   55,   28,   29,    0,    0,
   34,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   57,    0,    0,    0,    0,
    0,    0,    0,   56,   33,   58,   36,    0,   27,   27,
    0,    0,    0,    0,   30,    0,   54,    0,   27,    0,
   32,
};
final static short yydgoto[] = {                          1,
    2,    6,    7,    8,   44,   15,   29,   39,   45,   16,
   22,   13,   62,   63,   94,   95,
};
final static short yysindex[] = {                         0,
    0, -237,  -25, -247, -238,    0,    0,    0,    0, -236,
  -89,  -23,    0,  -19,    4,    7,    0,  -73,  -73,  -49,
 -219, -124, -216,    0,    0,    0,    0,    0,   -6,    0,
  -73,    0,   -1,    2,    0,  -30,    0,  -65, -211,  -73,
    0,  -73,    0,    0,  -33,    0,    0, -211,   57,   24,
   57,    0,    0,   57,   57,   31,   35,   51,    0,    0,
   16,    0,  410,  -21,   37,    0,   57,  120,  416,  438,
   57,   57,    0,  444,  -73,   57,   57,   57,   57,   57,
   57,   57,   57,   57,   57,   57,   57,   57, -185,   57,
    0,   57,  527,   40,   36,    0,    0,    0,  142,  296,
    0,   20,  539,  539,   23,   23,   23,   23,  319,  319,
   -5,   -5,  -35,  -35,  468,    0,  474,   42,   26,   57,
  -36,  -29,   48,    0,    0,    0,    0,  527,    0,    0,
   57,   -9,    3,  347,    0, -186,    0,  -24,    0,   15,
    0,
};
final static short yyrindex[] = {                         0,
    0,   93,    0,    0,    0,    0,    0,    0,    0,   59,
    0,    0,    0,    0,    0,   60,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   27,    0,
    0,    0,    0,    0,    0,    0,    0,   27,    0,  480,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   88,    0,   61,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   61,    5,    0,   62,    0,    0,    0,    0,    0,
    0,    0,  -31,   65,  551,  573,  620,  650,  367,  389,
  155,  380,  -39,  112,    0,    0,    0,    0,  502,    0,
    0,    0,    0,    0,    0,    0,    0,    6,    0,    0,
    0,    0,    0,    0,    0,   39,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,   96,    0,   -2,   64,  -34,    0,
    0,   86,    0,  767,   19,    0,
};
final static int YYTABLESIZE=910;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         49,
   34,   44,   44,   44,   44,   44,   51,   44,   31,   51,
   89,   49,   51,   64,   10,   11,   30,   23,   51,   44,
   44,   44,   44,   49,   12,    3,   14,   51,   38,   51,
   51,    4,    5,   17,   18,   49,   86,   46,   19,   47,
   89,   87,   51,   33,   20,   61,   62,   49,   61,   62,
   21,   36,   37,   44,   51,   88,   40,   43,    5,   27,
   41,   51,   42,   67,   86,   84,   27,   85,   89,   87,
   71,   31,  102,   32,   72,   75,   92,  116,   31,  120,
  119,  123,  126,   49,  127,   88,  129,  131,  138,   49,
   51,   52,    1,  130,  132,  133,   51,    9,  139,   12,
   11,   60,   59,   91,  140,   52,   48,   35,   52,   73,
  118,    0,    0,   88,    0,  135,    0,    0,    0,    0,
    0,    0,    0,   52,    0,   52,    0,  136,   40,   40,
   40,   40,   40,   40,   40,    0,    0,    0,   12,  141,
    0,    0,    0,    0,    0,    0,   40,   40,   40,   40,
    0,   27,   43,   43,   43,   43,   43,   52,   43,    0,
   96,   86,   84,   31,   85,   89,   87,    0,    0,    0,
   43,   43,   43,   43,    0,    0,    0,    0,   40,   78,
   40,   79,  121,   86,   84,    0,   85,   89,   87,   24,
   25,   26,   27,   28,    0,   41,    0,   41,   41,   41,
    0,   78,    0,   79,   43,    0,    0,    0,    0,    0,
   88,    0,    0,   41,   41,   41,   41,   44,   44,   44,
   44,   44,   44,    0,    0,   51,   51,    0,    0,   50,
    0,    0,   88,    0,   53,    0,    0,   54,   55,   56,
   57,   50,   58,   59,   60,   61,   53,   41,    0,   54,
   55,   56,   57,   50,   58,   59,   60,   61,   53,    0,
    0,   54,   55,   56,   57,   50,   58,   59,   60,   61,
   53,    0,    0,   54,   55,   56,   57,   50,   58,   59,
   60,   61,   53,   82,   83,   54,   55,   56,   57,   27,
   58,   59,   60,   61,   27,    0,    0,   27,   27,   27,
   27,   31,   27,   27,   27,   27,   31,    0,    0,   31,
   31,   31,   31,   65,   31,   31,   31,   31,   53,   65,
    0,   52,   52,    0,   53,    0,    0,   59,   60,   61,
    0,    0,    0,   59,   60,   61,  122,   86,   84,    0,
   85,   89,   87,    0,   40,   40,   40,   40,   40,   40,
    0,    0,    0,    0,    0,   78,    0,   79,    0,    0,
   86,   84,    0,   85,   89,   87,    0,    0,   43,   43,
   43,   43,   43,   43,    0,    0,   76,   77,   80,   81,
   82,   83,    0,    0,    0,    0,   88,  137,   86,   84,
    0,   85,   89,   87,    0,    0,    0,    0,   76,   77,
   80,   81,   82,   83,    0,    0,   78,   50,   79,   88,
   50,   41,   41,   41,   41,   41,   41,    0,    0,    0,
   42,    0,   42,   42,   42,   50,   50,   50,   50,   49,
    0,    0,   49,    0,    0,    0,    0,   88,   42,   42,
   42,   42,    0,    0,    0,    0,    0,   49,   49,   49,
   49,   86,   84,    0,   85,   89,   87,   86,   84,   50,
   85,   89,   87,    0,    0,    0,    0,    0,    0,   78,
   90,   79,   42,    0,   97,   78,    0,   79,    0,   86,
   84,   49,   85,   89,   87,   86,   84,    0,   85,   89,
   87,    0,    0,    0,    0,    0,   98,   78,    0,   79,
   88,    0,  101,   78,    0,   79,   88,    0,    0,   86,
   84,    0,   85,   89,   87,   86,   84,    0,   85,   89,
   87,   40,   40,    0,   40,   40,   40,   78,   88,   79,
    0,    0,  125,   78,   88,   79,    0,    0,    0,   40,
   40,   40,    0,   58,   58,    0,   58,   58,   58,    0,
    0,    0,   76,   77,   80,   81,   82,   83,   88,    0,
  124,   58,   58,   58,   88,    0,    0,    0,   86,   84,
   40,   85,   89,   87,    0,    0,    0,    0,    0,    0,
   86,   84,    0,   85,   89,   87,   78,    0,   79,    0,
    0,   45,   58,    0,   45,    0,    0,    0,   78,    0,
   79,    0,    0,   76,   77,   80,   81,   82,   83,   45,
   45,   45,   45,   46,    0,    0,   46,   88,    0,    0,
    0,    0,    0,   50,   50,   50,   50,   50,   50,   88,
    0,   46,   46,   46,   46,    0,   42,   42,   42,   42,
   42,   42,    0,   45,    0,   49,   49,   49,   49,   49,
   49,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   47,    0,    0,   47,    0,   46,   76,   77,   80,   81,
   82,   83,   76,   77,   80,   81,   82,   83,   47,   47,
   47,   47,    0,    0,    0,    0,    0,    0,    0,    0,
   48,    0,    0,   48,   76,   77,   80,   81,   82,   83,
   76,   77,   80,   81,   82,   83,    0,    0,   48,   48,
   48,   48,   47,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   76,   77,   80,   81,   82,   83,
   76,   77,   80,   81,   82,   83,   40,   40,   40,   40,
   40,   40,   48,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   58,   58,
   58,   58,   58,   58,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   76,   77,   80,   81,   82,   83,    0,
    0,    0,    0,    0,    0,    0,    0,   80,   81,   82,
   83,    0,    0,    0,    0,    0,    0,   45,   45,   45,
   45,    0,    0,    0,    0,   66,    0,   68,    0,    0,
   69,   70,    0,    0,   74,    0,    0,    0,    0,   46,
   46,   46,   46,   93,    0,    0,    0,   99,  100,    0,
    0,    0,  103,  104,  105,  106,  107,  108,  109,  110,
  111,  112,  113,  114,  115,    0,  117,    0,   93,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   47,   47,   47,   47,
    0,    0,    0,    0,    0,    0,  128,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  134,    0,    0,
    0,    0,    0,    0,    0,    0,   48,   48,   48,   48,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
  125,   41,   42,   43,   44,   45,   40,   47,   58,   41,
   46,   33,   44,   48,   40,  263,   19,   91,   40,   59,
   60,   61,   62,   33,  263,  263,  263,   59,   31,   61,
   40,  269,  270,  123,   58,   33,   42,   40,   58,   42,
   46,   47,   40,  263,   41,   41,   41,   33,   44,   44,
   44,  268,   59,   93,   40,   91,   58,  123,  270,   33,
   59,   93,   93,   40,   42,   43,   40,   45,   46,   47,
   40,   33,   75,  123,   40,   60,   40,  263,   40,   44,
   41,   62,   41,   33,   59,   91,  123,   40,  275,   33,
   40,  125,    0,  123,  129,  130,   40,    2,  123,   41,
   41,   41,   41,  125,  139,   41,   43,   22,   44,   59,
   92,   -1,   -1,   91,   -1,  125,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   59,   -1,   61,   -1,  125,   41,   42,
   43,   44,   45,   46,   47,   -1,   -1,   -1,  263,  125,
   -1,   -1,   -1,   -1,   -1,   -1,   59,   60,   61,   62,
   -1,  125,   41,   42,   43,   44,   45,   93,   47,   -1,
   41,   42,   43,  125,   45,   46,   47,   -1,   -1,   -1,
   59,   60,   61,   62,   -1,   -1,   -1,   -1,   91,   60,
   93,   62,   41,   42,   43,   -1,   45,   46,   47,  263,
  264,  265,  266,  267,   -1,   41,   -1,   43,   44,   45,
   -1,   60,   -1,   62,   93,   -1,   -1,   -1,   -1,   -1,
   91,   -1,   -1,   59,   60,   61,   62,  257,  258,  259,
  260,  261,  262,   -1,   -1,  257,  258,   -1,   -1,  263,
   -1,   -1,   91,   -1,  268,   -1,   -1,  271,  272,  273,
  274,  263,  276,  277,  278,  279,  268,   93,   -1,  271,
  272,  273,  274,  263,  276,  277,  278,  279,  268,   -1,
   -1,  271,  272,  273,  274,  263,  276,  277,  278,  279,
  268,   -1,   -1,  271,  272,  273,  274,  263,  276,  277,
  278,  279,  268,  261,  262,  271,  272,  273,  274,  263,
  276,  277,  278,  279,  268,   -1,   -1,  271,  272,  273,
  274,  263,  276,  277,  278,  279,  268,   -1,   -1,  271,
  272,  273,  274,  263,  276,  277,  278,  279,  268,  263,
   -1,  257,  258,   -1,  268,   -1,   -1,  277,  278,  279,
   -1,   -1,   -1,  277,  278,  279,   41,   42,   43,   -1,
   45,   46,   47,   -1,  257,  258,  259,  260,  261,  262,
   -1,   -1,   -1,   -1,   -1,   60,   -1,   62,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   -1,   -1,  257,  258,
  259,  260,  261,  262,   -1,   -1,  257,  258,  259,  260,
  261,  262,   -1,   -1,   -1,   -1,   91,   41,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,   -1,   -1,   60,   41,   62,   91,
   44,  257,  258,  259,  260,  261,  262,   -1,   -1,   -1,
   41,   -1,   43,   44,   45,   59,   60,   61,   62,   41,
   -1,   -1,   44,   -1,   -1,   -1,   -1,   91,   59,   60,
   61,   62,   -1,   -1,   -1,   -1,   -1,   59,   60,   61,
   62,   42,   43,   -1,   45,   46,   47,   42,   43,   93,
   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,   60,
   61,   62,   93,   -1,   59,   60,   -1,   62,   -1,   42,
   43,   93,   45,   46,   47,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   -1,   -1,   59,   60,   -1,   62,
   91,   -1,   59,   60,   -1,   62,   91,   -1,   -1,   42,
   43,   -1,   45,   46,   47,   42,   43,   -1,   45,   46,
   47,   42,   43,   -1,   45,   46,   47,   60,   91,   62,
   -1,   -1,   59,   60,   91,   62,   -1,   -1,   -1,   60,
   61,   62,   -1,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,  257,  258,  259,  260,  261,  262,   91,   -1,
   93,   60,   61,   62,   91,   -1,   -1,   -1,   42,   43,
   91,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   60,   -1,   62,   -1,
   -1,   41,   91,   -1,   44,   -1,   -1,   -1,   60,   -1,
   62,   -1,   -1,  257,  258,  259,  260,  261,  262,   59,
   60,   61,   62,   41,   -1,   -1,   44,   91,   -1,   -1,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,   91,
   -1,   59,   60,   61,   62,   -1,  257,  258,  259,  260,
  261,  262,   -1,   93,   -1,  257,  258,  259,  260,  261,
  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   41,   -1,   -1,   44,   -1,   93,  257,  258,  259,  260,
  261,  262,  257,  258,  259,  260,  261,  262,   59,   60,
   61,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   41,   -1,   -1,   44,  257,  258,  259,  260,  261,  262,
  257,  258,  259,  260,  261,  262,   -1,   -1,   59,   60,
   61,   62,   93,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,
  257,  258,  259,  260,  261,  262,  257,  258,  259,  260,
  261,  262,   93,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  259,  260,  261,
  262,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,   -1,   -1,   -1,   -1,   49,   -1,   51,   -1,   -1,
   54,   55,   -1,   -1,   58,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,   67,   -1,   -1,   -1,   71,   72,   -1,
   -1,   -1,   76,   77,   78,   79,   80,   81,   82,   83,
   84,   85,   86,   87,   88,   -1,   90,   -1,   92,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,
   -1,   -1,   -1,   -1,   -1,   -1,  120,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  131,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=279;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,null,null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"\"AND\"","\"OR\"",
"\"MENORIGUAL\"","\"MAYORIGUAL\"","\"DISTINTO\"","\"IGUAL\"","\"IDENT\"",
"\"INT\"","\"REAL\"","\"CHAR\"","\"VOID\"","\"LITERALINT\"","\"STRUCT\"",
"\"VAR\"","\"READ\"","\"PRINT\"","\"WHILE\"","\"IF\"","\"ELSE\"","\"RETURN\"",
"\"LITERALREAL\"","\"LITERALCHAR\"","\"CAST\"",
};
final static String yyrule[] = {
"$accept : programa",
"programa : elementos",
"elementos :",
"elementos : elementos elemento",
"elemento : funcion",
"elemento : struct",
"elemento : atributo",
"funcion : \"IDENT\" '(' parametrosOpt ')' ':' tipo '{' atributos sentencias '}'",
"funcion : \"IDENT\" '(' parametrosOpt ')' '{' atributos sentencias '}'",
"atributos :",
"atributos : atributos atributo",
"parametrosOpt : parametros",
"parametrosOpt :",
"parametros : \"IDENT\" ':' tipo",
"parametros : parametros ',' \"IDENT\" ':' tipo",
"tipo : \"IDENT\"",
"tipo : \"INT\"",
"tipo : \"REAL\"",
"tipo : \"CHAR\"",
"tipo : \"VOID\"",
"tipo : '[' \"LITERALINT\" ']' tipo",
"struct : \"STRUCT\" \"IDENT\" '{' definiciones '}' ';'",
"atributo : \"VAR\" definicion",
"definiciones :",
"definiciones : definiciones definicion",
"definicion : \"IDENT\" ':' tipo ';'",
"sentencias : sentencias sentencia",
"sentencias :",
"sentencia : \"READ\" expresion ';'",
"sentencia : \"PRINT\" expresion ';'",
"sentencia : \"WHILE\" '(' expresion ')' '{' sentencias '}'",
"sentencia : \"IF\" '(' expresion ')' '{' sentencias '}'",
"sentencia : \"IF\" '(' expresion ')' '{' sentencias '}' \"ELSE\" '{' sentencias '}'",
"sentencia : expresion '=' expresion ';'",
"sentencia : \"RETURN\" expresion ';'",
"sentencia : \"RETURN\" ';'",
"sentencia : \"IDENT\" '(' valoresOpt ')' ';'",
"expresion : \"LITERALINT\"",
"expresion : \"LITERALREAL\"",
"expresion : \"LITERALCHAR\"",
"expresion : \"IDENT\"",
"expresion : expresion '+' expresion",
"expresion : expresion '-' expresion",
"expresion : expresion '/' expresion",
"expresion : expresion '*' expresion",
"expresion : expresion '<' expresion",
"expresion : expresion '>' expresion",
"expresion : expresion \"MENORIGUAL\" expresion",
"expresion : expresion \"MAYORIGUAL\" expresion",
"expresion : expresion \"IGUAL\" expresion",
"expresion : expresion \"DISTINTO\" expresion",
"expresion : expresion \"AND\" expresion",
"expresion : expresion \"OR\" expresion",
"expresion : '!' expresion",
"expresion : \"CAST\" '<' tipo '>' '(' expresion ')'",
"expresion : '(' expresion ')'",
"expresion : expresion '[' expresion ']'",
"expresion : expresion '.' \"IDENT\"",
"expresion : \"IDENT\" '(' valoresOpt ')'",
"valoresOpt : valores",
"valoresOpt :",
"valores : expresion",
"valores : valores ',' expresion",
};

//#line 120 "sintac.y"
/* No es necesario modificar esta sección ------------------ */

public Parser(Yylex lex, GestorErrores gestor, boolean debug) {
	this(debug);
	this.lex = lex;
	this.gestor = gestor;
}

// Métodos de acceso para el main -------------
public int parse() { return yyparse(); }
public AST getAST() { return raiz; }

// Funciones requeridas por Yacc --------------
void yyerror(String msg) {
	Token lastToken = (Token) yylval;
	gestor.error("Sintáctico", "Token = " + lastToken.getToken() + ", lexema = \"" + lastToken.getLexeme() + "\". " + msg, lastToken.getStart());
}

int yylex() {
	try {
		int token = lex.yylex();
		yylval = new Token(token, lex.lexeme(), lex.line(), lex.column());
		return token;
	} catch (IOException e) {
		return -1;
	}
}

private Yylex lex;
private GestorErrores gestor;
private AST raiz;
//#line 512 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 23 "sintac.y"
{raiz = new Programa(val_peek(0));}
break;
case 2:
//#line 26 "sintac.y"
{yyval = new ArrayList<Elemento>();}
break;
case 3:
//#line 27 "sintac.y"
{yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0));}
break;
case 4:
//#line 30 "sintac.y"
{yyval = val_peek(0);}
break;
case 5:
//#line 31 "sintac.y"
{yyval = val_peek(0);}
break;
case 6:
//#line 32 "sintac.y"
{yyval = val_peek(0);}
break;
case 7:
//#line 35 "sintac.y"
{yyval = new Funcion(val_peek(9),val_peek(7),val_peek(4),val_peek(2),val_peek(1));}
break;
case 8:
//#line 36 "sintac.y"
{yyval = new Funcion(val_peek(7),val_peek(5),new Tipovoid(),val_peek(2),val_peek(1));}
break;
case 9:
//#line 39 "sintac.y"
{yyval = new ArrayList<Atributo>();}
break;
case 10:
//#line 40 "sintac.y"
{yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0));}
break;
case 11:
//#line 43 "sintac.y"
{yyval = val_peek(0);}
break;
case 12:
//#line 44 "sintac.y"
{yyval=new ArrayList<Parametro>();}
break;
case 13:
//#line 47 "sintac.y"
{List<Parametro> lista = new ArrayList<Parametro>();lista.add(new Parametro(val_peek(2),val_peek(0)));yyval = lista;}
break;
case 14:
//#line 48 "sintac.y"
{yyval = val_peek(4); ((List<Parametro>)val_peek(4)).add(new Parametro(val_peek(2),val_peek(0)));}
break;
case 15:
//#line 50 "sintac.y"
{yyval = new Tipoident(val_peek(0));}
break;
case 16:
//#line 51 "sintac.y"
{yyval = new Tipoint();}
break;
case 17:
//#line 52 "sintac.y"
{yyval = new Tiporeal();}
break;
case 18:
//#line 53 "sintac.y"
{yyval = new Tipochar();}
break;
case 19:
//#line 54 "sintac.y"
{yyval = new Tipovoid();}
break;
case 20:
//#line 55 "sintac.y"
{yyval = new Array(new Litent((Integer.valueOf(((Token)val_peek(2)).getLexeme()))),val_peek(0));}
break;
case 21:
//#line 58 "sintac.y"
{yyval=new Struct(val_peek(4),val_peek(2));}
break;
case 22:
//#line 61 "sintac.y"
{yyval=new Atributo(val_peek(0));}
break;
case 23:
//#line 64 "sintac.y"
{yyval = new ArrayList();}
break;
case 24:
//#line 65 "sintac.y"
{yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0));}
break;
case 25:
//#line 68 "sintac.y"
{yyval = new Definicion(val_peek(3),val_peek(1));}
break;
case 26:
//#line 71 "sintac.y"
{yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0));}
break;
case 27:
//#line 72 "sintac.y"
{yyval = new ArrayList<Sentencia>();}
break;
case 28:
//#line 75 "sintac.y"
{yyval = new Read(val_peek(1));}
break;
case 29:
//#line 76 "sintac.y"
{yyval = new Print(val_peek(1));}
break;
case 30:
//#line 77 "sintac.y"
{yyval = new While(val_peek(4),val_peek(1));}
break;
case 31:
//#line 78 "sintac.y"
{yyval = new If(val_peek(4),val_peek(1),null);}
break;
case 32:
//#line 79 "sintac.y"
{yyval = new If(val_peek(8),val_peek(5),val_peek(1));}
break;
case 33:
//#line 80 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(3),"=",val_peek(1));}
break;
case 34:
//#line 81 "sintac.y"
{yyval = new Return(val_peek(1));}
break;
case 35:
//#line 82 "sintac.y"
{yyval = new Return(val_peek(0));}
break;
case 36:
//#line 83 "sintac.y"
{yyval = new InvocarSentencia(val_peek(4),val_peek(2));}
break;
case 37:
//#line 86 "sintac.y"
{yyval = new Litent(val_peek(0));}
break;
case 38:
//#line 87 "sintac.y"
{yyval = new Litreal(val_peek(0));}
break;
case 39:
//#line 88 "sintac.y"
{yyval = new Litchar(val_peek(0));}
break;
case 40:
//#line 89 "sintac.y"
{yyval = new Var(val_peek(0));}
break;
case 41:
//#line 90 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"+",val_peek(0));}
break;
case 42:
//#line 91 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"-",val_peek(0));}
break;
case 43:
//#line 92 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"/",val_peek(0));}
break;
case 44:
//#line 93 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"*",val_peek(0));}
break;
case 45:
//#line 94 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"<",val_peek(0));}
break;
case 46:
//#line 95 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),">",val_peek(0));}
break;
case 47:
//#line 96 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"<=",val_peek(0));}
break;
case 48:
//#line 97 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),">=",val_peek(0));}
break;
case 49:
//#line 98 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"==",val_peek(0));}
break;
case 50:
//#line 99 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"!=",val_peek(0));}
break;
case 51:
//#line 100 "sintac.y"
{yyval = new ExpresionLogica(val_peek(2),"&&",val_peek(0));}
break;
case 52:
//#line 101 "sintac.y"
{yyval = new ExpresionLogica(val_peek(2),"||",val_peek(0));}
break;
case 53:
//#line 102 "sintac.y"
{yyval = new ExpresionUnaria(val_peek(0));}
break;
case 54:
//#line 103 "sintac.y"
{yyval=new Cast(val_peek(4),val_peek(1));}
break;
case 55:
//#line 104 "sintac.y"
{yyval = new EntreParentesis(val_peek(1));}
break;
case 56:
//#line 105 "sintac.y"
{yyval = new AccesoArray(val_peek(3),val_peek(1));}
break;
case 57:
//#line 106 "sintac.y"
{yyval = new AccesoStruct(val_peek(2),val_peek(0));}
break;
case 58:
//#line 107 "sintac.y"
{yyval = new InvocarFuncion(val_peek(3),val_peek(1));}
break;
case 59:
//#line 110 "sintac.y"
{yyval = val_peek(0);}
break;
case 60:
//#line 111 "sintac.y"
{yyval = new ArrayList();}
break;
case 61:
//#line 114 "sintac.y"
{List<Expresion> lista = new ArrayList<Expresion>();lista.add((Expresion)val_peek(0));yyval = lista;}
break;
case 62:
//#line 115 "sintac.y"
{yyval = val_peek(2);((List<Expresion>)val_peek(2)).add((Expresion)val_peek(0));}
break;
//#line 908 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
