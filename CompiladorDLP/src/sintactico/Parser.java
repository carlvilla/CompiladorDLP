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
public final static short RETURN=269;
public final static short LITERALREAL=270;
public final static short LITERALCHAR=271;
public final static short CAST=272;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    2,    3,    3,    6,    6,
    9,    9,    7,    7,    7,    7,    7,    4,    5,   10,
   10,   11,    8,    8,   12,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   15,   15,   16,   16,   14,
   17,   17,   18,   18,
};
final static short yylen[] = {                            2,
    1,    1,    2,    1,    1,    1,    9,    7,    1,    0,
    3,    3,    1,    1,    1,    1,    4,    6,    2,    1,
    2,    4,    2,    0,    2,    3,    3,    7,    7,   11,
    4,    3,    2,    2,    1,    1,    1,    1,    3,    3,
    3,    3,    3,    3,    4,    4,    4,    4,    4,    4,
    2,    7,    3,    2,    1,    1,    2,    2,    3,    4,
    1,    0,    1,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    2,    4,    5,    6,    0,
    0,    0,   19,    3,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   20,   13,   14,   15,   16,    0,    0,
   11,    0,   24,   12,    0,   21,    0,   22,    0,    0,
   18,    0,   24,    0,    0,    0,    8,   35,    0,    0,
    0,    0,    0,    0,   36,   37,    0,   23,    0,    0,
   17,    0,   51,   55,    0,    0,    0,    0,   56,    0,
   25,    0,    0,    0,    0,   33,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   34,    7,
    0,    0,    0,    0,    0,   58,   57,   53,   26,   27,
    0,    0,   32,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   60,    0,   59,
    0,    0,    0,    0,    0,    0,    0,    0,   31,    0,
   64,   24,   24,    0,    0,    0,    0,   28,    0,   52,
    0,   24,    0,   30,
};
final static short yydgoto[] = {                          4,
    5,    6,    7,    8,    9,   16,   30,   40,   17,   23,
   13,   58,   59,   64,   68,   69,   93,   94,
};
final static short yysindex[] = {                      -244,
  -37, -246, -237,    0, -244,    0,    0,    0,    0, -236,
  -97,  -30,    0,    0,  -28,   -6,   -7, -237,  -90,  -90,
  -54, -236, -123,    0,    0,    0,    0,    0, -221,  -16,
    0,  -90,    0,    0,  -12,    0,  -44,    0,  -69,  -33,
    0,  -90,    0,   88,  -34,   88,    0,    0, -237,   88,
   88,   18,   32,   63,    0,    0,   13,    0,  460,   16,
    0,  -17,    0,    0,   88,   88, -183,  -32,    0,  151,
    0,   -9,   23,   88,   88,    0,  353,  -90,   88,   88,
   88,   88,   66,   69,   38,  -47,   85,   17,    0,    0,
   20,  490,   45,   35,  364,    0,    0,    0,    0,    0,
  389,  400,    0,   26,  521,  521,   29,   29,   88,   29,
   88,   29,   88,   88,   88,  421,   88,    0,   88,    0,
  -23,  -22,   53,   29,   29,   29,   29,   29,    0,   29,
    0,    0,    0,   88,   -1,   15,  454,    0, -173,    0,
  -13,    0,   31,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  111,    0,    0,    0,    0,   72,
    0,    0,    0,    0,    0,    0,   73,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  117,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  496,
    0,    0,    0,    0,   75,    0,    0,  328,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -19,    0,   76,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    1,   92,  158,  335,    0,  568,
    0,  581,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  605,  624,  634,  643,  662,    0,  669,
    0,    0,    0,    0,    0,    0,    0,    0,   47,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  114,    0,    0,    0,    0,  -15,  -35,   98,    0,
   -8,    0,  520,  -31,    0,   55,    0,    7,
};
final static int YYTABLESIZE=762;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         44,
   29,   35,   10,   32,   31,   65,   46,   62,   60,   24,
   11,   67,    1,   67,   36,   44,   39,    2,    3,   12,
   15,   63,   46,   88,   63,   18,   61,   19,   85,   20,
   60,   44,   81,   79,   21,   80,   22,   82,   46,   37,
   71,   39,   38,   39,   39,   39,   41,   44,   42,   99,
   83,   91,   84,   43,   46,   88,   66,   74,   66,   39,
   85,   88,  104,   44,   81,   79,   85,   80,   33,   82,
   46,   75,   78,   96,   89,  113,  114,  117,  119,   29,
  115,  100,   83,   91,   84,  118,   29,  123,   83,   91,
   84,   47,  134,   39,  141,   44,  135,  136,   44,  132,
  133,   44,   46,   60,   60,   46,  143,   90,   46,  142,
    1,   60,   10,    9,   86,   62,   61,   44,   14,   34,
   44,   76,   97,  138,   46,  131,  109,   46,    0,  111,
    0,    0,   40,   12,   40,   40,   40,    0,    0,  139,
    0,    0,    0,    0,    0,  115,   86,    0,    0,   38,
   40,    0,   86,    0,   38,  144,    0,   38,   38,   38,
   38,   38,    0,   38,    0,    0,   25,   26,   27,   28,
    0,   29,    0,    0,    0,   38,   38,   38,   38,    0,
    0,    0,    0,   88,   40,    0,    0,    0,   85,    0,
    0,   98,   81,   79,    0,   80,    0,   82,   42,   42,
   42,   42,   42,    0,   42,    0,    0,    0,    0,   38,
   83,   91,   84,    0,    0,    0,   42,    0,    0,    0,
    0,    0,    0,   45,    0,    0,    0,   48,    0,   49,
   50,   51,   52,   53,    0,   54,   55,   56,   57,   45,
   38,    0,    0,   48,    0,   49,   50,   51,   52,   53,
   42,   54,   55,   56,   57,   45,    0,    0,    0,   48,
    0,   49,   50,   51,   52,   53,    0,   54,   55,   56,
   57,   45,    0,    0,   86,   48,    0,   49,   50,   51,
   52,   53,    0,   54,   55,   56,   57,   45,    0,    0,
    0,   48,    0,   49,   50,   51,   52,   53,    0,   54,
   55,   56,   57,   29,    0,    0,    0,   29,    0,   29,
   29,   29,   29,   29,    0,   29,   29,   29,   29,   45,
    0,    0,   45,   48,    0,   45,   48,    0,    0,   48,
    0,    0,   55,   56,   57,   55,   56,   57,   55,   56,
   57,   45,    0,    0,   45,   48,    0,    0,   48,    0,
    0,    0,    0,    0,   55,   56,   57,   55,   56,   57,
   54,    0,    0,    0,    0,   54,    0,    0,   54,   54,
   54,   54,   54,    0,   54,   41,   41,   41,   41,   41,
    0,   41,    0,    0,    0,   88,   54,   54,   54,   54,
   85,    0,    0,   41,   81,   79,   88,   80,    0,   82,
    0,   85,    0,    0,    0,   81,   79,    0,   80,    0,
   82,  103,   83,   91,   84,    0,    0,    0,    0,    0,
   54,   88,    0,   83,   91,   84,   85,   41,    0,  121,
   81,   79,   88,   80,    0,   82,    0,   85,    0,    0,
  122,   81,   79,    0,   80,    0,   82,    0,   83,   91,
   84,   54,    0,   88,    0,    0,  120,    0,   85,   83,
   91,   84,   81,   79,    0,   80,    0,   82,    0,    0,
    0,    0,    0,    0,    0,    0,   86,    0,    0,  129,
   83,   91,   84,    0,    0,    0,   88,   86,    0,    0,
    0,   85,   88,    0,  140,   81,   79,   85,   80,    0,
   82,   81,   79,    0,   80,    0,   82,    0,    0,    0,
    0,    0,   86,   83,   91,   84,    0,    0,    0,   83,
   87,   84,   88,   86,    0,    0,    0,   85,   55,    0,
    0,   81,   79,   55,   80,    0,   82,   55,   55,    0,
   55,    0,   55,    0,   86,    0,    0,    0,    0,   83,
   91,   84,    0,   88,    0,   55,   55,   55,   85,    0,
    0,    0,   81,   63,    0,   70,    0,   82,    0,   72,
   73,    0,    0,   77,    0,    0,    0,   86,    0,    0,
   83,   91,   84,   86,   92,   95,    0,    0,    0,    0,
    0,    0,    0,  101,  102,    0,    0,    0,  105,  106,
  107,  108,  110,  112,    0,    0,  116,    0,   43,   43,
   43,   43,   43,   86,   43,    0,    0,    0,    0,   55,
    0,   44,   44,   44,   44,   44,   43,   44,  124,    0,
  125,    0,  126,  127,  128,    0,  130,    0,   92,   44,
    0,    0,    0,    0,   86,   45,   45,   45,   45,   45,
    0,   45,    0,  137,    0,    0,    0,    0,    0,    0,
   43,    0,    0,   45,   46,   46,   46,   46,   46,    0,
   46,    0,    0,   44,   49,   49,   49,   49,   49,    0,
   49,    0,   46,   50,   50,   50,   50,   50,    0,   50,
    0,    0,   49,    0,    0,    0,    0,   45,    0,    0,
    0,   50,   47,   47,   47,   47,   47,    0,   47,   48,
   48,   48,   48,   48,    0,   48,   46,    0,    0,    0,
   47,    0,    0,    0,    0,    0,   49,   48,    0,    0,
    0,    0,    0,    0,    0,   50,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   47,    0,    0,    0,    0,    0,
    0,   48,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   91,  125,   40,   58,   20,   40,   40,   43,   40,   18,
  257,   46,  257,   46,   23,   33,   32,  262,  263,  257,
  257,   41,   40,   33,   44,  123,   42,   58,   38,   58,
   62,   33,   42,   43,   41,   45,   44,   47,   40,  261,
   49,   41,   59,   43,   44,   45,   59,   33,   93,   59,
   60,   61,   62,  123,   40,   33,   91,   40,   91,   59,
   38,   33,   78,   33,   42,   43,   38,   45,  123,   47,
   40,   40,   60,  257,   59,   38,  124,   61,   44,   33,
   61,   59,   60,   61,   62,   41,   40,   62,   60,   61,
   62,  125,   40,   93,  268,   33,  132,  133,   33,  123,
  123,   33,   40,  135,  136,   40,  142,  125,   40,  123,
    0,  143,   41,   41,  124,   41,   41,   33,    5,   22,
   33,   59,   68,  125,   40,  119,   61,   40,   -1,   61,
   -1,   -1,   41,  257,   43,   44,   45,   -1,   -1,  125,
   -1,   -1,   -1,   -1,   -1,   61,  124,   -1,   -1,   33,
   59,   -1,  124,   -1,   38,  125,   -1,   41,   42,   43,
   44,   45,   -1,   47,   -1,   -1,  257,  258,  259,  260,
   -1,  125,   -1,   -1,   -1,   59,   60,   61,   62,   -1,
   -1,   -1,   -1,   33,   93,   -1,   -1,   -1,   38,   -1,
   -1,   41,   42,   43,   -1,   45,   -1,   47,   41,   42,
   43,   44,   45,   -1,   47,   -1,   -1,   -1,   -1,   93,
   60,   61,   62,   -1,   -1,   -1,   59,   -1,   -1,   -1,
   -1,   -1,   -1,  257,   -1,   -1,   -1,  261,   -1,  263,
  264,  265,  266,  267,   -1,  269,  270,  271,  272,  257,
  124,   -1,   -1,  261,   -1,  263,  264,  265,  266,  267,
   93,  269,  270,  271,  272,  257,   -1,   -1,   -1,  261,
   -1,  263,  264,  265,  266,  267,   -1,  269,  270,  271,
  272,  257,   -1,   -1,  124,  261,   -1,  263,  264,  265,
  266,  267,   -1,  269,  270,  271,  272,  257,   -1,   -1,
   -1,  261,   -1,  263,  264,  265,  266,  267,   -1,  269,
  270,  271,  272,  257,   -1,   -1,   -1,  261,   -1,  263,
  264,  265,  266,  267,   -1,  269,  270,  271,  272,  257,
   -1,   -1,  257,  261,   -1,  257,  261,   -1,   -1,  261,
   -1,   -1,  270,  271,  272,  270,  271,  272,  270,  271,
  272,  257,   -1,   -1,  257,  261,   -1,   -1,  261,   -1,
   -1,   -1,   -1,   -1,  270,  271,  272,  270,  271,  272,
   33,   -1,   -1,   -1,   -1,   38,   -1,   -1,   41,   42,
   43,   44,   45,   -1,   47,   41,   42,   43,   44,   45,
   -1,   47,   -1,   -1,   -1,   33,   59,   60,   61,   62,
   38,   -1,   -1,   59,   42,   43,   33,   45,   -1,   47,
   -1,   38,   -1,   -1,   -1,   42,   43,   -1,   45,   -1,
   47,   59,   60,   61,   62,   -1,   -1,   -1,   -1,   -1,
   93,   33,   -1,   60,   61,   62,   38,   93,   -1,   41,
   42,   43,   33,   45,   -1,   47,   -1,   38,   -1,   -1,
   41,   42,   43,   -1,   45,   -1,   47,   -1,   60,   61,
   62,  124,   -1,   33,   -1,   -1,   93,   -1,   38,   60,
   61,   62,   42,   43,   -1,   45,   -1,   47,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  124,   -1,   -1,   59,
   60,   61,   62,   -1,   -1,   -1,   33,  124,   -1,   -1,
   -1,   38,   33,   -1,   41,   42,   43,   38,   45,   -1,
   47,   42,   43,   -1,   45,   -1,   47,   -1,   -1,   -1,
   -1,   -1,  124,   60,   61,   62,   -1,   -1,   -1,   60,
   61,   62,   33,  124,   -1,   -1,   -1,   38,   33,   -1,
   -1,   42,   43,   38,   45,   -1,   47,   42,   43,   -1,
   45,   -1,   47,   -1,  124,   -1,   -1,   -1,   -1,   60,
   61,   62,   -1,   33,   -1,   60,   61,   62,   38,   -1,
   -1,   -1,   42,   44,   -1,   46,   -1,   47,   -1,   50,
   51,   -1,   -1,   54,   -1,   -1,   -1,  124,   -1,   -1,
   60,   61,   62,  124,   65,   66,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   74,   75,   -1,   -1,   -1,   79,   80,
   81,   82,   83,   84,   -1,   -1,   87,   -1,   41,   42,
   43,   44,   45,  124,   47,   -1,   -1,   -1,   -1,  124,
   -1,   41,   42,   43,   44,   45,   59,   47,  109,   -1,
  111,   -1,  113,  114,  115,   -1,  117,   -1,  119,   59,
   -1,   -1,   -1,   -1,  124,   41,   42,   43,   44,   45,
   -1,   47,   -1,  134,   -1,   -1,   -1,   -1,   -1,   -1,
   93,   -1,   -1,   59,   41,   42,   43,   44,   45,   -1,
   47,   -1,   -1,   93,   41,   42,   43,   44,   45,   -1,
   47,   -1,   59,   41,   42,   43,   44,   45,   -1,   47,
   -1,   -1,   59,   -1,   -1,   -1,   -1,   93,   -1,   -1,
   -1,   59,   41,   42,   43,   44,   45,   -1,   47,   41,
   42,   43,   44,   45,   -1,   47,   93,   -1,   -1,   -1,
   59,   -1,   -1,   -1,   -1,   -1,   93,   59,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,   -1,
   -1,   93,
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
"\"WHILE\"","\"IF\"","\"ELSE\"","\"RETURN\"","\"LITERALREAL\"",
"\"LITERALCHAR\"","\"CAST\"",
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
"parametro : \"IDENT\" ':' tipo",
"parametro : parametro ',' parametro",
"tipo : \"IDENT\"",
"tipo : \"INT\"",
"tipo : \"REAL\"",
"tipo : \"CHAR\"",
"tipo : '[' \"LITERALINT\" ']' tipo",
"struct : \"STRUCT\" \"IDENT\" '{' definiciones '}' ';'",
"atributo : \"VAR\" definicion",
"definiciones : definicion",
"definiciones : definiciones definicion",
"definicion : \"IDENT\" ':' tipo ';'",
"sentencias : sentencias sentencia",
"sentencias :",
"sentencia : \"VAR\" definicion",
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
"expresion : expresion '<' '=' expresion",
"expresion : expresion '>' '=' expresion",
"expresion : expresion '=' '=' expresion",
"expresion : expresion '!' '=' expresion",
"expresion : expresion '&' '&' expresion",
"expresion : expresion '|' '|' expresion",
"expresion : '!' expresion",
"expresion : \"CAST\" '<' tipo '>' '(' expresion ')'",
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
"valor : valor ',' valor",
};

//#line 125 "sintac.y"
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
//#line 478 "Parser.java"
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
