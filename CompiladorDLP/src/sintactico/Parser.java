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
    4,    5,   12,   12,   11,    9,    9,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   15,   15,
   16,   16,
};
final static short yylen[] = {                            2,
    1,    0,    2,    1,    1,    1,   10,    8,    0,    2,
    1,    0,    1,    3,    1,    1,    1,    1,    1,    4,
    6,    3,    0,    3,    3,    2,    0,    3,    3,    7,
    7,   11,    4,    3,    2,    5,    1,    1,    1,    1,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    2,    7,    3,    4,    3,    4,    1,    0,
    1,    3,
};
final static short yydefred[] = {                         2,
    0,    0,    0,    0,    0,    3,    4,    5,    6,    0,
    0,    0,    0,    0,    0,   13,   23,    0,   22,    0,
    0,    0,    0,   15,   16,   17,   18,   19,   25,    0,
    9,   14,    0,    0,    0,    0,    0,   21,   24,    0,
    9,   10,    0,   20,    0,    0,    0,    0,    8,   37,
    0,    0,    0,    0,    0,   38,   39,    0,   26,    0,
    0,    0,   53,    0,    0,    0,    0,    0,    0,   35,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    7,    0,    0,
    0,    0,   55,   28,   29,    0,    0,   34,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   57,    0,    0,    0,    0,    0,    0,    0,
   56,   33,   58,   36,    0,   27,   27,    0,    0,    0,
    0,   30,    0,   54,    0,   27,    0,   32,
};
final static short yydgoto[] = {                          1,
    2,    6,    7,    8,   42,   14,   29,   37,   43,   15,
   13,   22,   59,   60,   91,   92,
};
final static short yysindex[] = {                         0,
    0, -237,  -26, -247, -236,    0,    0,    0,    0, -236,
  -94,  -16,   -1,   10,   17,    0,    0,  -73,    0,  -49,
 -236, -124, -209,    0,    0,    0,    0,    0,    0,  -73,
    0,    0,    4,   12,  -29,  -50, -195,    0,    0,  -73,
    0,    0,  -33,    0, -195,   57,   36,   57,    0,    0,
   57,   57,   37,   38,   51,    0,    0,   20,    0,  410,
  -21,   41,    0,   57,  120,  416,  438,   57,   57,    0,
  444,  -73,   57,   57,   57,   57,   57,   57,   57,   57,
   57,   57,   57,   57,   57, -181,   57,    0,   57,  527,
   44,   42,    0,    0,    0,  142,  296,    0,   25,  539,
  539,   23,   23,   23,   23,   -8,   -8,   -2,   -2,  -35,
  -35,  468,    0,  474,   47,   34,   57,  -27,  -23,   54,
    0,    0,    0,    0,  527,    0,    0,   57,   -9,    3,
  347,    0, -180,    0,  -22,    0,   15,    0,
};
final static short yyrindex[] = {                         0,
    0,  102,    0,    0,    0,    0,    0,    0,    0,   62,
    0,    0,    0,    0,   64,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   27,    0,    0,    0,
    0,    0,    0,    0,   27,    0,  480,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   88,    0,   66,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   66,    5,
    0,   70,    0,    0,    0,    0,    0,    0,    0,  -31,
   65,  551,  573,  620,  650,  367,  389,  155,  380,  -39,
  112,    0,    0,    0,    0,  502,    0,    0,    0,    0,
    0,    0,    0,    0,    6,    0,    0,    0,    0,    0,
    0,    0,   39,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,  110,    0,  -15,   72,  -28,    0,
   31,    0,    0,  770,   26,    0,
};
final static int YYTABLESIZE=910;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         46,
   33,   44,   44,   44,   44,   44,   48,   44,   30,   51,
   86,   46,   51,   10,   36,   11,   61,   23,   48,   44,
   44,   44,   44,   46,   44,    3,   12,   51,   17,   51,
   48,    4,    5,   83,   81,   46,   82,   86,   84,   83,
   16,   18,   48,   86,   84,   61,   62,   46,   61,   62,
   20,   32,   34,   44,   48,   85,   99,   19,   35,   27,
   21,   51,   38,   40,   83,   81,   27,   82,   86,   84,
   39,   31,   41,   31,    5,   64,   68,   69,   31,   72,
   89,  113,   85,   46,  116,  117,  120,  123,   85,   46,
   48,   49,  124,  128,  135,  126,   48,  129,  130,  127,
  136,    1,   12,   88,   11,   52,   60,  137,   52,   70,
   59,    9,   45,   85,  115,  132,    0,    0,    0,    0,
    0,    0,    0,   52,    0,   52,    0,  133,   40,   40,
   40,   40,   40,   40,   40,    0,    0,    0,   12,  138,
    0,    0,    0,    0,    0,    0,   40,   40,   40,   40,
    0,   27,   43,   43,   43,   43,   43,   52,   43,    0,
   93,   83,   81,   31,   82,   86,   84,    0,    0,    0,
   43,   43,   43,   43,    0,    0,    0,    0,   40,   75,
   40,   76,  118,   83,   81,    0,   82,   86,   84,   24,
   25,   26,   27,   28,    0,   41,    0,   41,   41,   41,
    0,   75,    0,   76,   43,    0,    0,    0,    0,    0,
   85,    0,    0,   41,   41,   41,   41,   44,   44,   44,
   44,   44,   44,    0,    0,   51,   51,    0,    0,   47,
    0,    0,   85,    0,   50,    0,    0,   51,   52,   53,
   54,   47,   55,   56,   57,   58,   50,   41,    0,   51,
   52,   53,   54,   47,   55,   56,   57,   58,   50,    0,
    0,   51,   52,   53,   54,   47,   55,   56,   57,   58,
   50,    0,    0,   51,   52,   53,   54,   47,   55,   56,
   57,   58,   50,   79,   80,   51,   52,   53,   54,   27,
   55,   56,   57,   58,   27,    0,    0,   27,   27,   27,
   27,   31,   27,   27,   27,   27,   31,    0,    0,   31,
   31,   31,   31,   62,   31,   31,   31,   31,   50,   62,
    0,   52,   52,    0,   50,    0,    0,   56,   57,   58,
    0,    0,    0,   56,   57,   58,  119,   83,   81,    0,
   82,   86,   84,    0,   40,   40,   40,   40,   40,   40,
    0,    0,    0,    0,    0,   75,    0,   76,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   43,   43,
   43,   43,   43,   43,    0,    0,   73,   74,   77,   78,
   79,   80,    0,    0,    0,    0,   85,  134,   83,   81,
    0,   82,   86,   84,    0,    0,    0,    0,   73,   74,
   77,   78,   79,   80,    0,    0,   75,   50,   76,    0,
   50,   41,   41,   41,   41,   41,   41,    0,    0,    0,
   42,    0,   42,   42,   42,   50,   50,   50,   50,   49,
    0,    0,   49,    0,    0,    0,    0,   85,   42,   42,
   42,   42,    0,    0,    0,    0,    0,   49,   49,   49,
   49,   83,   81,    0,   82,   86,   84,   83,   81,   50,
   82,   86,   84,    0,    0,    0,    0,    0,    0,   75,
   87,   76,   42,    0,   94,   75,    0,   76,    0,   83,
   81,   49,   82,   86,   84,   83,   81,    0,   82,   86,
   84,    0,    0,    0,    0,    0,   95,   75,    0,   76,
   85,    0,   98,   75,    0,   76,   85,    0,    0,   83,
   81,    0,   82,   86,   84,   83,   81,    0,   82,   86,
   84,   40,   40,    0,   40,   40,   40,   75,   85,   76,
    0,    0,  122,   75,   85,   76,    0,    0,    0,   40,
   40,   40,    0,   58,   58,    0,   58,   58,   58,    0,
    0,    0,   73,   74,   77,   78,   79,   80,   85,    0,
  121,   58,   58,   58,   85,    0,    0,    0,   83,   81,
   40,   82,   86,   84,    0,    0,    0,    0,    0,    0,
   83,   81,    0,   82,   86,   84,   75,    0,   76,    0,
    0,   45,   58,    0,   45,    0,    0,    0,   75,    0,
   76,    0,    0,   73,   74,   77,   78,   79,   80,   45,
   45,   45,   45,   46,    0,    0,   46,   85,    0,    0,
    0,    0,    0,   50,   50,   50,   50,   50,   50,   85,
    0,   46,   46,   46,   46,    0,   42,   42,   42,   42,
   42,   42,    0,   45,    0,   49,   49,   49,   49,   49,
   49,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   47,    0,    0,   47,    0,   46,   73,   74,   77,   78,
   79,   80,   73,   74,   77,   78,   79,   80,   47,   47,
   47,   47,    0,    0,    0,    0,    0,    0,    0,    0,
   48,    0,    0,   48,   73,   74,   77,   78,   79,   80,
   73,   74,   77,   78,   79,   80,    0,    0,   48,   48,
   48,   48,   47,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   73,   74,   77,   78,   79,   80,
   73,   74,   77,   78,   79,   80,   40,   40,   40,   40,
   40,   40,   48,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   58,   58,
   58,   58,   58,   58,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   73,   74,   77,   78,   79,   80,    0,
    0,    0,    0,    0,    0,    0,    0,   77,   78,   79,
   80,    0,    0,    0,    0,    0,    0,   45,   45,   45,
   45,    0,    0,    0,    0,   63,    0,   65,    0,    0,
   66,   67,    0,    0,   71,    0,    0,    0,    0,   46,
   46,   46,   46,   90,    0,    0,    0,   96,   97,    0,
    0,    0,  100,  101,  102,  103,  104,  105,  106,  107,
  108,  109,  110,  111,  112,    0,  114,    0,   90,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   47,   47,   47,   47,
    0,    0,    0,    0,    0,    0,  125,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  131,    0,    0,
    0,    0,    0,    0,    0,    0,   48,   48,   48,   48,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
  125,   41,   42,   43,   44,   45,   40,   47,   58,   41,
   46,   33,   44,   40,   30,  263,   45,   91,   40,   59,
   60,   61,   62,   33,   40,  263,  263,   59,  123,   61,
   40,  269,  270,   42,   43,   33,   45,   46,   47,   42,
   10,   58,   40,   46,   47,   41,   41,   33,   44,   44,
   41,   21,   22,   93,   40,   91,   72,   59,  268,   33,
   44,   93,   59,   93,   42,   43,   40,   45,   46,   47,
   59,   33,  123,  123,  270,   40,   40,   40,   40,   60,
   40,  263,   91,   33,   41,   44,   62,   41,   91,   33,
   40,  125,   59,   40,  275,  123,   40,  126,  127,  123,
  123,    0,   41,  125,   41,   41,   41,  136,   44,   59,
   41,    2,   41,   91,   89,  125,   -1,   -1,   -1,   -1,
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
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,   -1,   -1,  257,  258,  259,  260,
  261,  262,   -1,   -1,   -1,   -1,   91,   41,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,   -1,   -1,   60,   41,   62,   -1,
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
  260,   -1,   -1,   -1,   -1,   46,   -1,   48,   -1,   -1,
   51,   52,   -1,   -1,   55,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,   64,   -1,   -1,   -1,   68,   69,   -1,
   -1,   -1,   73,   74,   75,   76,   77,   78,   79,   80,
   81,   82,   83,   84,   85,   -1,   87,   -1,   89,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,
   -1,   -1,   -1,   -1,   -1,   -1,  117,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  128,   -1,   -1,
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
"parametros : definicion",
"parametros : parametros ',' definicion",
"tipo : \"IDENT\"",
"tipo : \"INT\"",
"tipo : \"REAL\"",
"tipo : \"CHAR\"",
"tipo : \"VOID\"",
"tipo : '[' \"LITERALINT\" ']' tipo",
"struct : \"STRUCT\" \"IDENT\" '{' definiciones '}' ';'",
"atributo : \"VAR\" definicion ';'",
"definiciones :",
"definiciones : definiciones definicion ';'",
"definicion : \"IDENT\" ':' tipo",
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
//#line 509 "Parser.java"
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
{yyval=new ArrayList<Definicion>();}
break;
case 13:
//#line 47 "sintac.y"
{List<Definicion> lista = new ArrayList<Definicion>();((Definicion)val_peek(0)).setEsParametro(true);lista.add((Definicion)val_peek(0));yyval = lista;}
break;
case 14:
//#line 48 "sintac.y"
{yyval = val_peek(2);((Definicion)val_peek(0)).setEsParametro(true);((List<Definicion>)val_peek(2)).add((Definicion)val_peek(0));}
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
{yyval=new Atributo(val_peek(1));}
break;
case 23:
//#line 64 "sintac.y"
{yyval = new ArrayList<Definicion>();}
break;
case 24:
//#line 65 "sintac.y"
{yyval = val_peek(2); ((List<Definicion>)val_peek(2)).add((Definicion)val_peek(1));}
break;
case 25:
//#line 68 "sintac.y"
{yyval = new Definicion(val_peek(2),val_peek(0));}
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
//#line 905 "Parser.java"
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
