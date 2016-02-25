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
public final static short IDENT=257;
public final static short INT=258;
public final static short REAL=259;
public final static short CHAR=260;
public final static short LITERALINT=261;
public final static short STRUCT=262;
public final static short VAR=263;
public final static short READ=264;
public final static short PRINT=265;
public final static short WHILE=266;
public final static short IF=267;
public final static short ELSE=268;
public final static short LITERALCHAR=269;
public final static short CAST=270;
public final static short RETURN=271;
public final static short LITERALREAL=272;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    2,    3,    3,    6,    6,
    9,    9,    7,    7,   10,   10,   10,   11,   12,   12,
   13,    4,    5,   14,   14,   15,   15,    8,    8,   16,
   16,   16,   16,   16,   16,   16,   16,   16,   16,   16,
   16,   16,   16,   18,   18,   18,   18,   18,   18,   18,
   18,   18,   18,   18,   18,   18,   18,   18,   18,   18,
   18,   18,   18,   17,   17,   20,   20,   19,   21,   21,
   22,   22,   22,
};
final static short yylen[] = {                            2,
    1,    1,    2,    1,    1,    1,    9,    7,    1,    0,
    3,    3,    1,    1,    1,    1,    1,    2,    1,    2,
    3,    6,    2,    1,    2,    4,    4,    2,    0,    2,
    3,    4,    3,    7,    7,   11,    4,    5,    4,    7,
    3,    2,    2,    1,    1,    1,    3,    3,    3,    3,
    3,    3,    4,    4,    4,    3,    4,    4,    4,    2,
    3,    2,    1,    1,    2,    2,    3,    4,    1,    0,
    1,    7,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    2,    4,    5,    6,    0,
    0,    0,   23,    3,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   24,   14,   15,   16,   17,    0,    0,
   13,    0,    0,   19,   11,    0,   29,   12,    0,   25,
    0,   26,   27,   18,   20,    0,    0,   22,   21,   29,
    0,    8,    0,    0,    0,    0,    0,    0,    0,   28,
    0,    0,    0,    0,    0,    0,    0,   64,   30,    0,
    0,    0,    0,   44,   45,    0,   63,    0,    0,    0,
   42,    0,   43,    7,    0,    0,    0,    0,    0,    0,
    0,   66,    0,   65,   31,    0,   60,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   33,
    0,    0,    0,   41,   39,   37,    0,   68,    0,   67,
    0,   32,   61,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   73,   38,    0,    0,    0,    0,    0,    0,   29,   29,
    0,    0,    0,    0,    0,    0,   34,    0,   40,    0,
    0,   72,   29,    0,   36,
};
final static short yydgoto[] = {                          4,
    5,    6,    7,    8,    9,   16,   30,   47,   17,   31,
   32,   33,   34,   23,   13,   60,   67,   88,   77,   68,
   89,   90,
};
final static short yysindex[] = {                      -122,
  -12, -236, -221,    0, -122,    0,    0,    0,    0, -218,
  -71,    6,    0,    0,    7,   33,   31, -221,  -89,   11,
  -42, -218, -120,    0,    0,    0,    0,    0, -163,   42,
    0,   49,  -89,    0,    0,   -5,    0,    0,   58,    0,
   25,    0,    0,    0,    0,    2, -121,    0,    0,    0,
   60,    0, -221, -135,  -10,   87,   98,   92,  -33,    0,
  103, -110,  -32,  -27,  -10,  -94,  -28,    0,    0,   73,
  -10,  -21,  -10,    0,    0,  250,    0,  -10,  -10,   11,
    0,  280,    0,    0,  108,  311,  118,  450,  140,  138,
  317,    0,  -10,    0,    0,   74,    0,   67,  347,  -10,
  -10,  -10,  -10,  -30,  -13,  -11,  151,   68,  139,    0,
  358,  383,  141,    0,    0,    0,   11,    0,  -27,    0,
  389,    0,    0,   24,   24,   69,   69,  -10,   69,  -10,
   69,  -10,   69,  -10,  -10,  -10,   88,   91,  164,  148,
    0,    0,   69,   69,   69,   69,   69,   69,    0,    0,
  -10,  180,  -91,  -80,  414,  -10,    0,  -46,    0,  425,
  100,    0,    0,  -69,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  226,    0,    0,    0,    0,  191,
    0,    0,    0,    0,    0,    0,  194,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  195,    0,    0,    0,    0,    0,    0,
    0,   -1,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   -6,    0,  197,
    0,    0,    0,    0,    0,    0,    0,   35,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   10,  241,   46,  174,    0,  457,    0,
  476,    0,  483,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  511,  518,  537,  544,  565,  572,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -58,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  236,    0,    0,    0,    0,  -19,  -39,  227,   -8,
    0,    0,  224,    0,   81,    0,  -23,  201,  -38,   16,
    0,  144,
};
final static int YYTABLESIZE=665;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         71,
   71,   29,   71,   52,   39,   71,   73,   73,   61,   73,
   62,   35,   73,   44,   84,   36,   46,   66,   64,   71,
   11,   71,   71,   61,   66,   81,   73,   10,   73,   73,
  128,   46,   93,  157,   71,   12,   46,   71,   15,   46,
   46,   46,   46,   46,  158,   46,   96,  130,   98,  132,
   47,   18,   47,   47,   47,  165,  109,   46,   46,   46,
   46,  107,   65,   19,   20,  102,   35,   62,   47,   65,
  103,  113,   62,   21,   22,   62,   62,   62,   62,   62,
   37,   62,   94,  104,  106,  105,   50,   50,   50,   50,
   50,   46,   50,   62,   62,   62,   62,   41,   24,   64,
   42,  109,   47,   40,   50,   66,  107,   43,  140,  153,
  154,   94,   66,   94,   61,   61,   48,   49,   66,   66,
   63,   70,   46,  164,   50,   61,   78,   62,  104,  106,
  105,   95,  122,   69,    1,   51,   12,   79,   50,    2,
    3,   53,   54,   55,   56,   57,   51,  108,   58,   59,
   65,   80,   53,   54,   55,   56,   57,   65,   62,   58,
   59,   83,   92,   65,   65,   51,  115,   25,   26,   27,
   28,   53,   54,   55,   56,   57,   51,  117,   58,   59,
  118,  119,   53,   54,   55,   56,   57,   51,  134,   58,
   59,  135,  108,   53,   54,   55,   56,   57,   35,  136,
   58,   59,  139,  151,   35,   35,   35,   35,   35,  152,
  149,   35,   35,  150,   49,   49,   49,   49,   49,  156,
   49,  161,  163,   72,   72,    1,   72,   74,   74,   72,
   74,   10,   49,   74,    9,   70,   85,   69,   75,   75,
   14,   75,   87,   72,   75,   72,   72,   74,   38,   74,
   74,   25,   26,   27,   28,   76,   45,    0,   75,   82,
   75,   75,  141,   86,    0,   91,   49,    0,   26,   27,
   28,   97,    0,   99,    0,    0,    0,    0,  111,  112,
    0,   48,  109,   48,   48,   48,    0,  107,    0,    0,
    0,  102,  100,  121,  101,    0,  103,    0,    0,   48,
  124,  125,  126,  127,  129,  131,  133,    0,  110,  104,
  106,  105,  109,    0,    0,    0,    0,  107,    0,    0,
    0,  102,  100,    0,  101,    0,  103,    0,  143,    0,
  144,    0,  145,   48,  146,  147,  148,    0,  114,  104,
  106,  105,    0,  109,    0,    0,    0,    0,  107,  109,
    0,  155,  102,  100,  107,  101,  160,  103,  102,  100,
    0,  101,    0,  103,    0,    0,    0,    0,    0,  116,
  104,  106,  105,  108,    0,    0,  104,  106,  105,  109,
    0,    0,    0,    0,  107,    0,    0,  123,  102,  100,
  109,  101,    0,  103,    0,  107,    0,    0,  137,  102,
  100,    0,  101,  108,  103,    0,  104,  106,  105,  120,
    0,    0,    0,    0,    0,  109,    0,  104,  106,  105,
  107,  109,    0,  138,  102,  100,  107,  101,    0,  103,
  102,  100,    0,  101,  108,  103,    0,    0,    0,    0,
  108,    0,  104,  106,  105,    0,  109,  142,  104,  106,
  105,  107,    0,    0,  159,  102,  100,  109,  101,    0,
  103,    0,  107,    0,    0,  162,  102,  100,    0,  101,
  108,  103,    0,  104,  106,  105,    0,    0,    0,    0,
    0,  108,  109,    0,  104,  106,  105,  107,    0,    0,
    0,  102,  100,    0,  101,    0,  103,   51,   51,   51,
   51,   51,    0,   51,    0,    0,  108,    0,    0,  104,
  106,  105,  108,    0,    0,   51,   52,   52,   52,   52,
   52,    0,   52,   56,   56,   56,   56,   56,    0,   56,
    0,    0,    0,    0,   52,    0,    0,  108,    0,    0,
    0,   56,    0,    0,    0,    0,    0,    0,  108,   51,
    0,   53,   53,   53,   53,   53,    0,   53,   54,   54,
   54,   54,   54,    0,   54,    0,    0,    0,   52,   53,
    0,    0,    0,  108,    0,   56,   54,   55,   55,   55,
   55,   55,    0,   55,   58,   58,   58,   58,   58,    0,
   58,    0,    0,    0,    0,   55,    0,    0,    0,    0,
    0,    0,   58,   53,    0,   59,   59,   59,   59,   59,
   54,   59,   57,   57,   57,   57,   57,    0,   57,    0,
    0,    0,    0,   59,    0,    0,    0,    0,    0,   55,
   57,    0,    0,    0,    0,    0,   58,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   59,    0,    0,
    0,    0,    0,    0,   57,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   33,   91,   33,  125,  125,   33,   40,   40,   47,   40,
   50,   20,   40,   33,  125,   58,   36,   46,   40,   33,
  257,   33,   33,   62,   46,   59,   40,   40,   40,   40,
   61,   33,   61,  125,   41,  257,   38,   44,  257,   41,
   42,   43,   44,   45,  125,   47,   70,   61,   72,   61,
   41,  123,   43,   44,   45,  125,   33,   59,   60,   61,
   62,   38,   91,   58,   58,   42,  125,   33,   59,   91,
   47,   80,   38,   41,   44,   41,   42,   43,   44,   45,
  123,   47,   67,   60,   61,   62,   41,   42,   43,   44,
   45,   93,   47,   59,   60,   61,   62,  261,   18,   40,
   59,   33,   93,   23,   59,   46,   38,   59,  117,  149,
  150,   96,   46,   98,  153,  154,   59,   93,   46,   46,
   61,  257,  124,  163,  123,  164,   40,   93,   60,   61,
   62,   59,   59,   53,  257,  257,  257,   40,   93,  262,
  263,  263,  264,  265,  266,  267,  257,  124,  270,  271,
   91,   60,  263,  264,  265,  266,  267,   91,  124,  270,
  271,   59,  257,   91,   91,  257,   59,  257,  258,  259,
  260,  263,  264,  265,  266,  267,  257,   60,  270,  271,
   41,   44,  263,  264,  265,  266,  267,  257,   38,  270,
  271,  124,  124,  263,  264,  265,  266,  267,  257,   61,
  270,  271,   62,   40,  263,  264,  265,  266,  267,   62,
  123,  270,  271,  123,   41,   42,   43,   44,   45,   40,
   47,  268,  123,  257,  257,    0,  257,  261,  261,  257,
  261,   41,   59,  261,   41,   41,  269,   41,  272,  272,
    5,  272,  270,  257,  272,  257,  257,  261,   22,  261,
  261,  257,  258,  259,  260,   55,   33,   -1,  272,   59,
  272,  272,  119,   63,   -1,   65,   93,   -1,  258,  259,
  260,   71,   -1,   73,   -1,   -1,   -1,   -1,   78,   79,
   -1,   41,   33,   43,   44,   45,   -1,   38,   -1,   -1,
   -1,   42,   43,   93,   45,   -1,   47,   -1,   -1,   59,
  100,  101,  102,  103,  104,  105,  106,   -1,   59,   60,
   61,   62,   33,   -1,   -1,   -1,   -1,   38,   -1,   -1,
   -1,   42,   43,   -1,   45,   -1,   47,   -1,  128,   -1,
  130,   -1,  132,   93,  134,  135,  136,   -1,   59,   60,
   61,   62,   -1,   33,   -1,   -1,   -1,   -1,   38,   33,
   -1,  151,   42,   43,   38,   45,  156,   47,   42,   43,
   -1,   45,   -1,   47,   -1,   -1,   -1,   -1,   -1,   59,
   60,   61,   62,  124,   -1,   -1,   60,   61,   62,   33,
   -1,   -1,   -1,   -1,   38,   -1,   -1,   41,   42,   43,
   33,   45,   -1,   47,   -1,   38,   -1,   -1,   41,   42,
   43,   -1,   45,  124,   47,   -1,   60,   61,   62,   93,
   -1,   -1,   -1,   -1,   -1,   33,   -1,   60,   61,   62,
   38,   33,   -1,   41,   42,   43,   38,   45,   -1,   47,
   42,   43,   -1,   45,  124,   47,   -1,   -1,   -1,   -1,
  124,   -1,   60,   61,   62,   -1,   33,   59,   60,   61,
   62,   38,   -1,   -1,   41,   42,   43,   33,   45,   -1,
   47,   -1,   38,   -1,   -1,   41,   42,   43,   -1,   45,
  124,   47,   -1,   60,   61,   62,   -1,   -1,   -1,   -1,
   -1,  124,   33,   -1,   60,   61,   62,   38,   -1,   -1,
   -1,   42,   43,   -1,   45,   -1,   47,   41,   42,   43,
   44,   45,   -1,   47,   -1,   -1,  124,   -1,   -1,   60,
   61,   62,  124,   -1,   -1,   59,   41,   42,   43,   44,
   45,   -1,   47,   41,   42,   43,   44,   45,   -1,   47,
   -1,   -1,   -1,   -1,   59,   -1,   -1,  124,   -1,   -1,
   -1,   59,   -1,   -1,   -1,   -1,   -1,   -1,  124,   93,
   -1,   41,   42,   43,   44,   45,   -1,   47,   41,   42,
   43,   44,   45,   -1,   47,   -1,   -1,   -1,   93,   59,
   -1,   -1,   -1,  124,   -1,   93,   59,   41,   42,   43,
   44,   45,   -1,   47,   41,   42,   43,   44,   45,   -1,
   47,   -1,   -1,   -1,   -1,   59,   -1,   -1,   -1,   -1,
   -1,   -1,   59,   93,   -1,   41,   42,   43,   44,   45,
   93,   47,   41,   42,   43,   44,   45,   -1,   47,   -1,
   -1,   -1,   -1,   59,   -1,   -1,   -1,   -1,   -1,   93,
   59,   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,
   -1,   -1,   -1,   -1,   93,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=272;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,null,"'&'",null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'","'|'","'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"\"IDENT\"","\"INT\"","\"REAL\"",
"\"CHAR\"","\"LITERALINT\"","\"STRUCT\"","\"VAR\"","\"READ\"","\"PRINT\"",
"\"WHILE\"","\"IF\"","\"ELSE\"","\"LITERALCHAR\"","\"CAST\"","\"RETURN\"",
"\"LITERALREAL\"",
};
final static String yyrule[] = {
"$accept : programa",
"programa : elementos",
"elementos : elemento",
"elementos : elementos elemento",
"elemento : funcion",
"elemento : struct",
"elemento : atributo",
"funcion : \"IDENT\" '(' parametros ')' ':' tipo '{' sentencias '}'",
"funcion : \"IDENT\" '(' parametros ')' '{' sentencias '}'",
"parametros : parametro",
"parametros :",
"parametro : \"IDENT\" ':' tipoPrimitivo",
"parametro : parametro ',' parametro",
"tipo : tipoPrimitivo",
"tipo : \"IDENT\"",
"tipoPrimitivo : \"INT\"",
"tipoPrimitivo : \"REAL\"",
"tipoPrimitivo : \"CHAR\"",
"array : dimensiones tipo",
"dimensiones : dimension",
"dimensiones : dimensiones dimension",
"dimension : '[' \"LITERALINT\" ']'",
"struct : \"STRUCT\" \"IDENT\" '{' definiciones '}' ';'",
"atributo : \"VAR\" definicion",
"definiciones : definicion",
"definiciones : definiciones definicion",
"definicion : \"IDENT\" ':' tipo ';'",
"definicion : \"IDENT\" ':' array ';'",
"sentencias : sentencias sentencia",
"sentencias :",
"sentencia : \"VAR\" definicion",
"sentencia : \"READ\" \"IDENT\" ';'",
"sentencia : \"READ\" \"IDENT\" accesos ';'",
"sentencia : \"PRINT\" expresion ';'",
"sentencia : \"WHILE\" '(' expresion ')' '{' sentencias '}'",
"sentencia : \"IF\" '(' expresion ')' '{' sentencias '}'",
"sentencia : \"IF\" '(' expresion ')' '{' sentencias '}' \"ELSE\" '{' sentencias '}'",
"sentencia : \"IDENT\" '=' expresion ';'",
"sentencia : \"IDENT\" accesos '=' expresion ';'",
"sentencia : \"IDENT\" '=' \"LITERALCHAR\" ';'",
"sentencia : \"CAST\" '<' tipoPrimitivo '>' '(' expresion ')'",
"sentencia : \"RETURN\" expresion ';'",
"sentencia : \"RETURN\" ';'",
"sentencia : invocacionMetodo ';'",
"expresion : \"LITERALINT\"",
"expresion : \"LITERALREAL\"",
"expresion : \"IDENT\"",
"expresion : expresion '+' expresion",
"expresion : expresion '-' expresion",
"expresion : expresion '/' expresion",
"expresion : expresion '*' expresion",
"expresion : expresion '<' expresion",
"expresion : expresion '>' expresion",
"expresion : expresion '<' '=' expresion",
"expresion : expresion '>' '=' expresion",
"expresion : expresion '=' '=' expresion",
"expresion : expresion '=' expresion",
"expresion : expresion '!' '=' expresion",
"expresion : expresion '&' '&' expresion",
"expresion : expresion '|' '|' expresion",
"expresion : '!' expresion",
"expresion : '(' expresion ')'",
"expresion : \"IDENT\" accesos",
"expresion : invocacionMetodo",
"accesos : acceso",
"accesos : accesos acceso",
"acceso : '.' \"IDENT\"",
"acceso : '[' expresion ']'",
"invocacionMetodo : \"IDENT\" '(' valores ')'",
"valores : valor",
"valores :",
"valor : expresion",
"valor : \"CAST\" '<' tipoPrimitivo '>' '(' expresion ')'",
"valor : valor ',' valor",
};

//#line 141 "sintac.y"
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
//#line 477 "Parser.java"
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
