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
public final static short MENORIGUAL=257;
public final static short MAYORIGUAL=258;
public final static short IGUAL=259;
public final static short DISTINTO=260;
public final static short AND=261;
public final static short OR=262;
public final static short IDENT=263;
public final static short INT=264;
public final static short REAL=265;
public final static short CHAR=266;
public final static short LITERALINT=267;
public final static short STRUCT=268;
public final static short VAR=269;
public final static short READ=270;
public final static short PRINT=271;
public final static short WHILE=272;
public final static short IF=273;
public final static short ELSE=274;
public final static short RETURN=275;
public final static short LITERALREAL=276;
public final static short LITERALCHAR=277;
public final static short CAST=278;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    2,    3,    3,    8,    8,
    6,    6,   10,   10,    7,    7,    7,    7,    7,    4,
    5,   11,   11,   12,    9,    9,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   15,   16,   16,
   17,   17,
};
final static short yylen[] = {                            2,
    1,    0,    2,    1,    1,    1,   10,    8,    0,    2,
    1,    0,    3,    5,    1,    1,    1,    1,    4,    6,
    2,    0,    2,    4,    2,    0,    3,    3,    7,    7,
   11,    4,    3,    2,    2,    1,    1,    1,    1,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    2,    7,    3,    4,    3,    1,    4,    1,    0,
    1,    3,
};
final static short yydefred[] = {                         2,
    0,    0,    0,    0,    0,    3,    4,    5,    6,    0,
    0,    0,   21,    0,    0,    0,   22,    0,    0,    0,
    0,    0,    0,   15,   16,   17,   18,    0,   13,    0,
    9,    0,    0,   23,    0,   24,    0,    0,    0,   20,
    0,    9,   10,    0,   14,   19,    0,    0,    0,    0,
    8,   36,    0,    0,    0,    0,    0,   37,   38,    0,
   25,    0,    0,    0,   52,   57,    0,    0,    0,    0,
    0,    0,   34,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   35,    7,    0,    0,    0,   54,   27,   28,    0,    0,
   33,    0,    0,    0,    0,    0,    0,    0,    0,   56,
   46,   47,   48,   49,   50,   51,    0,   58,    0,    0,
    0,    0,   55,   32,    0,   26,   26,    0,    0,    0,
    0,   29,    0,   53,    0,   26,    0,   31,
};
final static short yydgoto[] = {                          1,
    2,    6,    7,    8,   43,   15,   28,   38,   44,   16,
   22,   13,   61,   62,   66,   94,   95,
};
final static short yysindex[] = {                         0,
    0, -249,  -18, -238, -233,    0,    0,    0,    0, -230,
  -70,   -2,    0,    1,   16,   14,    0,  -60,  -60,  -55,
 -203, -124, -204,    0,    0,    0,    0,    7,    0,  -60,
    0,   11,   13,    0,  -20,    0,  -53, -195,  -60,    0,
  -60,    0,    0,  -33,    0,    0, -195,   82,   36,   82,
    0,    0,   82,   82,   43,   44,   79,    0,    0,   25,
    0,  325,   29,  -17,    0,    0,   82,  132,  394,  400,
   82,   82,    0,  421,  -60,   82,   82,   82,   82,   82,
   82,   82, -169,   82,   82,   82,   82,   82,   82,   82,
    0,    0,  483,   57,   56,    0,    0,    0,  140,  166,
    0,   39,  489,  489,  357,  357, -216, -216,  427,    0,
    0,    0,    0,    0,    0,    0,  451,    0,   82,  -21,
  -19,   65,    0,    0,  483,    0,    0,   82,   -1,   15,
  173,    0, -168,    0,  -16,    0,   31,    0,
};
final static short yyrindex[] = {                         0,
    0,  109,    0,    0,    0,    0,    0,    0,    0,   69,
    0,    0,    0,    0,    0,   70,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   47,    0,    0,
    0,    0,    0,    0,    0,    0,   47,    0,  104,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  461,    0,    0,    0,   72,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -36,    0,   73,    0,    0,    0,    0,    0,
    0,    0,    6,   34,  -32,   -7,  583,  711,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -23,    0,    0,    0,    0,    0,
    0,    0,   63,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,  114,    0,  -13,   75,  -45,    0,
    0,   96,    0,  522,  -40,    0,    0,
};
final static int YYTABLESIZE=804;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         48,
   33,   64,   30,   63,   61,   29,   50,   61,   43,   43,
   43,   43,   43,    3,   43,   48,   37,   62,    4,    5,
   62,   10,   50,   63,   11,   45,   43,   46,   43,   12,
   23,   48,   14,   42,   42,   42,   42,   42,   50,   42,
   84,   85,   86,   87,   88,   89,   40,   48,   40,   40,
   40,   42,   17,   42,   50,   18,   20,   21,   19,   32,
   43,  102,   35,   48,   40,   36,   40,   31,   39,   42,
   50,   40,   41,    5,   41,   67,   41,   41,   41,   26,
  129,  130,   71,   72,   75,   42,   26,   91,   63,   63,
  137,   51,   41,  110,   41,   30,   63,  118,   40,  119,
  122,  126,   30,  127,  128,  135,  136,   92,    1,   12,
   11,   48,   60,   59,   48,    9,   47,   34,   50,    0,
    0,   50,    0,  132,    0,    0,   41,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   73,   12,  133,
    0,    0,    0,    0,   39,   39,   39,   39,   39,   39,
   39,    0,    0,    0,    0,  138,    0,    0,    0,    0,
    0,    0,   39,   39,   39,   39,    0,    0,    0,    0,
    0,   26,   96,   78,   76,    0,   77,   83,   79,    0,
  120,   78,   76,    0,   77,   83,   79,   30,    0,    0,
    0,   80,    0,   81,   39,    0,   39,    0,    0,   80,
    0,   81,   24,   25,   26,   27,  121,   78,   76,    0,
   77,   83,   79,  134,   78,   76,    0,   77,   83,   79,
    0,    0,   82,    0,    0,   80,    0,   81,    0,   49,
   82,    0,   80,   52,   81,    0,   53,   54,   55,   56,
    0,   57,   58,   59,   60,   49,    0,    0,    0,   52,
    0,    0,   53,   54,   55,   56,   82,   57,   58,   59,
   60,   49,    0,   82,    0,   52,    0,    0,   53,   54,
   55,   56,    0,   57,   58,   59,   60,   49,    0,    0,
    0,   52,    0,    0,   53,   54,   55,   56,    0,   57,
   58,   59,   60,   49,    0,    0,    0,   52,    0,    0,
   53,   54,   55,   56,    0,   57,   58,   59,   60,   26,
    0,    0,    0,   26,    0,    0,   26,   26,   26,   26,
    0,   26,   26,   26,   26,   30,    0,    0,    0,   30,
    0,    0,   30,   30,   30,   30,    0,   30,   30,   30,
   30,   49,    0,    0,   49,   52,    0,    0,   52,    0,
    0,    0,    0,    0,   58,   59,   60,   58,   59,   60,
   39,   39,   39,   39,   39,   39,   78,   76,    0,   77,
   83,   79,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   80,   90,   81,    0,   84,   85,
   86,   87,   88,   89,    0,    0,   84,   85,   86,   87,
   88,   89,   83,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   82,   80,    0,   81,    0,
    0,    0,   84,   85,   86,   87,   88,   89,    0,   84,
   85,   86,   87,   88,   89,   78,   76,    0,   77,   83,
   79,   78,   76,    0,   77,   83,   79,   82,    0,    0,
    0,    0,   97,   80,    0,   81,    0,    0,   98,   80,
    0,   81,   78,   76,    0,   77,   83,   79,   78,   76,
    0,   77,   83,   79,    0,    0,    0,    0,    0,  101,
   80,    0,   81,    0,   82,    0,   80,    0,   81,    0,
   82,    0,   78,   76,    0,   77,   83,   79,    0,    0,
    0,    0,   57,   57,    0,   57,   57,   57,    0,  124,
   80,   82,   81,    0,    0,    0,    0,   82,    0,  123,
   57,   57,   57,    0,   78,   76,    0,   77,   83,   79,
   78,    0,    0,    0,   83,   79,    0,    0,    0,    0,
    0,   82,   80,    0,   81,    0,    0,    0,   80,    0,
   81,   57,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   65,
    0,   68,    0,   82,   69,   70,    0,    0,   74,   82,
    0,   84,   85,   86,   87,   88,   89,    0,   93,    0,
    0,    0,   99,  100,    0,    0,    0,  103,  104,  105,
  106,  107,  108,  109,    0,  111,  112,  113,  114,  115,
  116,  117,    0,   84,   85,   86,   87,   88,   89,    0,
    0,    0,    0,   44,   44,   44,   44,   44,   44,   44,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  125,   44,   44,   44,   44,    0,    0,    0,    0,  131,
   84,   85,   86,   87,   88,   89,   84,   85,   86,   87,
   88,   89,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   44,    0,   44,    0,   84,   85,   86,
   87,   88,   89,   84,   85,   86,   87,   88,   89,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   84,   85,   86,
   87,   88,   89,    0,    0,    0,    0,   57,   57,   57,
   57,   57,   57,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   84,
   85,   86,   87,   88,   89,   84,   85,   86,   87,   88,
   89,   45,   45,   45,   45,   45,   45,   45,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   45,
   45,   45,   45,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   45,    0,   45,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
  125,   47,   58,   44,   41,   19,   40,   44,   41,   42,
   43,   44,   45,  263,   47,   33,   30,   41,  268,  269,
   44,   40,   40,   64,  263,   39,   59,   41,   61,  263,
   91,   33,  263,   41,   42,   43,   44,   45,   40,   47,
  257,  258,  259,  260,  261,  262,   41,   33,   43,   44,
   45,   59,  123,   61,   40,   58,   41,   44,   58,  263,
   93,   75,  267,   33,   59,   59,   61,  123,   58,  123,
   40,   59,   93,  269,   41,   40,   43,   44,   45,   33,
  126,  127,   40,   40,   60,   93,   40,   59,  129,  130,
  136,  125,   59,  263,   61,   33,  137,   41,   93,   44,
   62,  123,   40,  123,   40,  274,  123,  125,    0,   41,
   41,   33,   41,   41,   33,    2,   42,   22,   40,   -1,
   -1,   40,   -1,  125,   -1,   -1,   93,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,  263,  125,
   -1,   -1,   -1,   -1,   41,   42,   43,   44,   45,   46,
   47,   -1,   -1,   -1,   -1,  125,   -1,   -1,   -1,   -1,
   -1,   -1,   59,   60,   61,   62,   -1,   -1,   -1,   -1,
   -1,  125,   41,   42,   43,   -1,   45,   46,   47,   -1,
   41,   42,   43,   -1,   45,   46,   47,  125,   -1,   -1,
   -1,   60,   -1,   62,   91,   -1,   93,   -1,   -1,   60,
   -1,   62,  263,  264,  265,  266,   41,   42,   43,   -1,
   45,   46,   47,   41,   42,   43,   -1,   45,   46,   47,
   -1,   -1,   91,   -1,   -1,   60,   -1,   62,   -1,  263,
   91,   -1,   60,  267,   62,   -1,  270,  271,  272,  273,
   -1,  275,  276,  277,  278,  263,   -1,   -1,   -1,  267,
   -1,   -1,  270,  271,  272,  273,   91,  275,  276,  277,
  278,  263,   -1,   91,   -1,  267,   -1,   -1,  270,  271,
  272,  273,   -1,  275,  276,  277,  278,  263,   -1,   -1,
   -1,  267,   -1,   -1,  270,  271,  272,  273,   -1,  275,
  276,  277,  278,  263,   -1,   -1,   -1,  267,   -1,   -1,
  270,  271,  272,  273,   -1,  275,  276,  277,  278,  263,
   -1,   -1,   -1,  267,   -1,   -1,  270,  271,  272,  273,
   -1,  275,  276,  277,  278,  263,   -1,   -1,   -1,  267,
   -1,   -1,  270,  271,  272,  273,   -1,  275,  276,  277,
  278,  263,   -1,   -1,  263,  267,   -1,   -1,  267,   -1,
   -1,   -1,   -1,   -1,  276,  277,  278,  276,  277,  278,
  257,  258,  259,  260,  261,  262,   42,   43,   -1,   45,
   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   60,   61,   62,   -1,  257,  258,
  259,  260,  261,  262,   -1,   -1,  257,  258,  259,  260,
  261,  262,   46,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   91,   60,   -1,   62,   -1,
   -1,   -1,  257,  258,  259,  260,  261,  262,   -1,  257,
  258,  259,  260,  261,  262,   42,   43,   -1,   45,   46,
   47,   42,   43,   -1,   45,   46,   47,   91,   -1,   -1,
   -1,   -1,   59,   60,   -1,   62,   -1,   -1,   59,   60,
   -1,   62,   42,   43,   -1,   45,   46,   47,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   59,
   60,   -1,   62,   -1,   91,   -1,   60,   -1,   62,   -1,
   91,   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,   59,
   60,   91,   62,   -1,   -1,   -1,   -1,   91,   -1,   93,
   60,   61,   62,   -1,   42,   43,   -1,   45,   46,   47,
   42,   -1,   -1,   -1,   46,   47,   -1,   -1,   -1,   -1,
   -1,   91,   60,   -1,   62,   -1,   -1,   -1,   60,   -1,
   62,   91,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   48,
   -1,   50,   -1,   91,   53,   54,   -1,   -1,   57,   91,
   -1,  257,  258,  259,  260,  261,  262,   -1,   67,   -1,
   -1,   -1,   71,   72,   -1,   -1,   -1,   76,   77,   78,
   79,   80,   81,   82,   -1,   84,   85,   86,   87,   88,
   89,   90,   -1,  257,  258,  259,  260,  261,  262,   -1,
   -1,   -1,   -1,   41,   42,   43,   44,   45,   46,   47,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  119,   59,   60,   61,   62,   -1,   -1,   -1,   -1,  128,
  257,  258,  259,  260,  261,  262,  257,  258,  259,  260,
  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   91,   -1,   93,   -1,  257,  258,  259,
  260,  261,  262,  257,  258,  259,  260,  261,  262,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,  262,  257,  258,  259,  260,  261,
  262,   41,   42,   43,   44,   45,   46,   47,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,
   60,   61,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   91,   -1,   93,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=278;
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
null,null,null,null,null,null,null,null,null,"\"MENORIGUAL\"","\"MAYORIGUAL\"",
"\"IGUAL\"","\"DISTINTO\"","\"AND\"","\"OR\"","\"IDENT\"","\"INT\"","\"REAL\"",
"\"CHAR\"","\"LITERALINT\"","\"STRUCT\"","\"VAR\"","\"READ\"","\"PRINT\"",
"\"WHILE\"","\"IF\"","\"ELSE\"","\"RETURN\"","\"LITERALREAL\"",
"\"LITERALCHAR\"","\"CAST\"",
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
"sentencia : invocacionMetodo ';'",
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
"expresion : invocacionMetodo",
"invocacionMetodo : \"IDENT\" '(' valoresOpt ')'",
"valoresOpt : valores",
"valoresOpt :",
"valores : expresion",
"valores : valores ',' expresion",
};

//#line 121 "sintac.y"
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
//#line 488 "Parser.java"
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
//#line 22 "sintac.y"
{raiz = new Programa(val_peek(0));}
break;
case 2:
//#line 25 "sintac.y"
{yyval = new ArrayList<Elemento>();}
break;
case 3:
//#line 26 "sintac.y"
{yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0));}
break;
case 4:
//#line 29 "sintac.y"
{yyval = val_peek(0);}
break;
case 5:
//#line 30 "sintac.y"
{yyval = val_peek(0);}
break;
case 6:
//#line 31 "sintac.y"
{yyval = val_peek(0);}
break;
case 7:
//#line 34 "sintac.y"
{yyval = new Funcion(val_peek(9),val_peek(7),val_peek(4),val_peek(2),val_peek(1));}
break;
case 8:
//#line 35 "sintac.y"
{yyval = new Funcion(val_peek(7),val_peek(5),null,val_peek(2),val_peek(1));}
break;
case 9:
//#line 38 "sintac.y"
{yyval = new ArrayList<Atributo>();}
break;
case 10:
//#line 39 "sintac.y"
{yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0));}
break;
case 11:
//#line 42 "sintac.y"
{yyval = val_peek(0);}
break;
case 12:
//#line 43 "sintac.y"
{yyval=null;}
break;
case 13:
//#line 46 "sintac.y"
{List<Parametro> lista = new ArrayList<Parametro>();lista.add(new Parametro(val_peek(2),val_peek(0)));yyval = lista;}
break;
case 14:
//#line 47 "sintac.y"
{yyval = val_peek(4); ((List<Parametro>)val_peek(4)).add(new Parametro(val_peek(2),val_peek(0)));}
break;
case 15:
//#line 49 "sintac.y"
{yyval = new Tipoident(val_peek(0));}
break;
case 16:
//#line 50 "sintac.y"
{yyval = new Tipoint();}
break;
case 17:
//#line 51 "sintac.y"
{yyval = new Tiporeal();}
break;
case 18:
//#line 52 "sintac.y"
{yyval = new Tipochar();}
break;
case 19:
//#line 53 "sintac.y"
{yyval = new Array(new Litent((Integer.valueOf(((Token)val_peek(2)).getLexeme()))),val_peek(0));}
break;
case 20:
//#line 56 "sintac.y"
{yyval=new Struct(val_peek(4),val_peek(2));}
break;
case 21:
//#line 59 "sintac.y"
{yyval=new Atributo(val_peek(0));}
break;
case 22:
//#line 62 "sintac.y"
{yyval = new ArrayList();}
break;
case 23:
//#line 63 "sintac.y"
{yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0));}
break;
case 24:
//#line 66 "sintac.y"
{yyval = new Definicion(val_peek(3),val_peek(1));}
break;
case 25:
//#line 69 "sintac.y"
{yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0));}
break;
case 26:
//#line 70 "sintac.y"
{yyval = new ArrayList<Sentencia>();}
break;
case 27:
//#line 73 "sintac.y"
{yyval = new Read(val_peek(1));}
break;
case 28:
//#line 74 "sintac.y"
{yyval = new Print(val_peek(1));}
break;
case 29:
//#line 75 "sintac.y"
{yyval = new While(val_peek(4),val_peek(1));}
break;
case 30:
//#line 76 "sintac.y"
{yyval = new If(val_peek(4),val_peek(1),null);}
break;
case 31:
//#line 77 "sintac.y"
{yyval = new If(val_peek(8),val_peek(5),val_peek(1));}
break;
case 32:
//#line 78 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(3),"=",val_peek(1));}
break;
case 33:
//#line 79 "sintac.y"
{yyval = new Return(val_peek(1));}
break;
case 34:
//#line 80 "sintac.y"
{yyval = new Return(null);}
break;
case 35:
//#line 81 "sintac.y"
{yyval = new InvocarSentencia(val_peek(1));}
break;
case 36:
//#line 84 "sintac.y"
{yyval = new Litent(val_peek(0));}
break;
case 37:
//#line 85 "sintac.y"
{yyval = new Litreal(val_peek(0));}
break;
case 38:
//#line 86 "sintac.y"
{yyval = new Litchar(val_peek(0));}
break;
case 39:
//#line 87 "sintac.y"
{yyval = new Var(val_peek(0));}
break;
case 40:
//#line 88 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"+",val_peek(0));}
break;
case 41:
//#line 89 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"-",val_peek(0));}
break;
case 42:
//#line 90 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"/",val_peek(0));}
break;
case 43:
//#line 91 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"*",val_peek(0));}
break;
case 44:
//#line 92 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"<",val_peek(0));}
break;
case 45:
//#line 93 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),">",val_peek(0));}
break;
case 46:
//#line 94 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"<=",val_peek(0));}
break;
case 47:
//#line 95 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),">=",val_peek(0));}
break;
case 48:
//#line 96 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"==",val_peek(0));}
break;
case 49:
//#line 97 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"!=",val_peek(0));}
break;
case 50:
//#line 98 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"&&",val_peek(0));}
break;
case 51:
//#line 99 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"||",val_peek(0));}
break;
case 52:
//#line 100 "sintac.y"
{yyval = new ExpresionUnaria(val_peek(0));}
break;
case 53:
//#line 101 "sintac.y"
{yyval=new Cast(val_peek(4),val_peek(1));}
break;
case 54:
//#line 102 "sintac.y"
{yyval = new EntreParentesis(val_peek(1));}
break;
case 55:
//#line 103 "sintac.y"
{yyval = new AccesoArray(val_peek(3),val_peek(1));}
break;
case 56:
//#line 104 "sintac.y"
{yyval = new AccesoStruct(val_peek(2),val_peek(0));}
break;
case 57:
//#line 105 "sintac.y"
{yyval = val_peek(0);}
break;
case 58:
//#line 108 "sintac.y"
{yyval = new Invocar(val_peek(3),val_peek(1));}
break;
case 59:
//#line 111 "sintac.y"
{yyval = val_peek(0);}
break;
case 60:
//#line 112 "sintac.y"
{yyval = new ArrayList();}
break;
case 61:
//#line 115 "sintac.y"
{List<Expresion> lista = new ArrayList<Expresion>();lista.add((Expresion)val_peek(0));yyval = lista;}
break;
case 62:
//#line 116 "sintac.y"
{yyval = val_peek(2);((List<Expresion>)val_peek(2)).add((Expresion)val_peek(0));}
break;
//#line 884 "Parser.java"
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
