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
public final static short IDENT=259;
public final static short INT=260;
public final static short REAL=261;
public final static short CHAR=262;
public final static short STRUCT=263;
public final static short VAR=264;
public final static short READ=265;
public final static short PRINT=266;
public final static short WHILE=267;
public final static short IF=268;
public final static short ELSE=269;
public final static short LITERALCHAR=270;
public final static short CAST=271;
public final static short RETURN=272;
public final static short LITERALINT=273;
public final static short LITERALREAL=274;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    2,    3,    3,    6,    6,
    9,    9,    7,    7,   10,   10,   10,   11,   12,   12,
   13,    4,    5,   15,   15,   16,   16,    8,    8,   17,
   17,   17,   17,   17,   17,   17,   17,   17,   17,   17,
   17,   17,   17,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   18,   18,   20,   20,   19,   21,   21,
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
    0,    0,    0,   44,   45,    0,   63,   26,   27,   18,
   20,    0,    0,   22,   60,    0,    0,    0,    0,   64,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   21,   29,    0,    8,    0,    0,    0,    0,    0,
    0,    0,   28,    0,    0,    0,    0,    0,    0,   66,
   65,   61,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   30,    0,
    0,    0,    0,    0,   42,    0,   43,    0,   68,    0,
   67,    0,    0,    0,    0,    0,    0,    7,    0,    0,
    0,   31,    0,   33,    0,    0,    0,   41,    0,   73,
   39,   37,    0,   32,    0,    0,    0,    0,   38,   29,
   29,    0,    0,    0,    0,    0,    0,   34,    0,   40,
   72,    0,   29,    0,   36,
};
final static short yydgoto[] = {                          4,
    5,    6,    7,    8,    9,   16,   30,   53,   17,   31,
   32,   33,   34,   86,   23,   13,   83,   59,   47,   60,
   87,   88,
};
final static short yysindex[] = {                      -123,
  -28, -228, -222,    0, -123,    0,    0,    0,    0, -201,
  -35,   49,    0,    0,   50,   53,   47, -222,  -90, -241,
  -50, -201, -120,    0,    0,    0,    0,    0,   -7,   54,
    0,   56,  -90,    0,    0,  -96,    0,    0,   61,    0,
   -7,    4,   -7,    0,    0,  286,    0,    0,    0,    0,
    0,   -2, -122,    0,    0,  -33,   -7, -137,   14,    0,
   10,   -7,   -7,   -7,   -7,  -31,  -29,  -27,   87,    5,
   67,    0,    0,   36,    0, -222, -127,   -7,   98,  107,
   99,  -23,    0,  108,  114,  459,  117,  124,  292,    0,
    0,    0,  470,  470,  -15,  -15,   -7,  -15,   -7,  -15,
   -7,  -15,   -7,   -7,   -7, -111,  -11,   32,    0,   33,
  322,   -7,   -7, -241,    0,  328,    0, -241,    0,  -33,
    0,  -15,  -15,  -15,  -15,  -15,  -15,    0,  118,  353,
   -7,    0,  116,    0,  361,  387,  122,    0,  125,    0,
    0,    0,  398,    0,   71,   74,  136,  164,    0,    0,
    0,   -7,   -7,  -86,  -76,  428,  453,    0,  -55,    0,
    0,   90,    0,  -66,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  217,    0,    0,    0,    0,  177,
    0,    0,    0,    0,    0,    0,  178,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  227,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  179,    0,    0,  252,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -17,    0,  188,    0,    0,
    0,    0,  190,  257,   42,  180,    0,  263,    0,  482,
    0,  492,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  502,  512,  521,  545,  556,  565,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -56,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  232,    0,    0,    0,    0,    2,  -32,  231,   -4,
    0,    0,  221,   -1,    0,   57,    0,  -20,  -38,  -34,
    0,  135,
};
final static int YYTABLESIZE=658;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         41,
   29,   41,   75,   41,   39,   41,   43,   36,   43,   41,
   43,   10,   43,  128,   84,   35,   43,   71,   26,   27,
   28,   41,   69,   71,   91,   41,   71,   46,   43,   97,
   11,   99,   43,  101,   50,  115,   12,   52,  158,   55,
  106,   61,   71,   56,   66,   68,   67,   69,  159,   58,
   92,   64,   62,  108,   63,   89,   65,   15,  165,   58,
   93,   94,   95,   96,   98,  100,  102,   84,   35,   66,
   68,   67,   37,   91,   24,   56,  111,   58,   58,   40,
  116,   58,   50,   50,   50,   50,   50,   18,   50,  133,
   22,  132,  131,   21,   57,  122,  107,  123,   91,  124,
   50,  125,  126,  127,   57,  130,   19,   20,   70,  137,
  135,  136,   48,  139,   49,   84,   84,  154,  155,   54,
   73,   90,   57,   57,  103,   84,   57,  105,  104,  143,
  164,  110,  109,   70,   50,    1,   74,  112,   12,    2,
    3,   76,   77,   78,   79,   80,  113,   74,   81,   82,
  156,  157,   76,   77,   78,   79,   80,  119,  114,   81,
   82,   58,   25,   26,   27,   28,  117,  120,   25,   26,
   27,   28,   74,  118,  144,  152,  141,   76,   77,   78,
   79,   80,   74,  147,   81,   82,  148,   76,   77,   78,
   79,   80,   74,  150,   81,   82,  151,   76,   77,   78,
   79,   80,   35,  153,   81,   82,   57,   35,   35,   35,
   35,   35,  163,  162,   35,   35,    1,   10,    9,   70,
   49,   49,   49,   49,   49,   42,   49,   42,   69,   42,
   47,   42,   47,   47,   47,   42,   14,   85,   49,   44,
   45,   44,   45,   44,   45,   44,   45,   42,   47,   44,
   45,   42,   38,   51,  140,    0,    0,    0,  129,   46,
    0,   44,   45,    0,   46,   44,   45,   46,   46,   46,
   46,   46,   49,   46,    0,    0,    0,    0,    0,    0,
    0,    0,   47,    0,   62,   46,   46,   46,   46,   62,
    0,    0,   62,   62,   62,   62,   62,   48,   62,   48,
   48,   48,    0,   51,   51,   51,   51,   51,    0,   51,
   62,   62,   62,   62,    0,   48,    0,    0,   71,   46,
    0,   51,    0,   69,   71,    0,    0,   64,   62,   69,
   63,    0,   65,   64,   62,    0,   63,    0,   65,    0,
    0,    0,    0,    0,   62,   66,   68,   67,    0,   48,
   46,   66,   68,   67,   71,   51,    0,    0,    0,   69,
   71,    0,    0,   64,   62,   69,   63,    0,   65,   64,
   62,    0,   63,    0,   65,   62,    0,    0,   72,    0,
  134,   66,   68,   67,  121,   71,  138,   66,   68,   67,
   69,    0,    0,   71,   64,   62,    0,   63,   69,   65,
    0,  145,   64,   62,    0,   63,    0,   65,    0,   70,
    0,  142,   66,   68,   67,   70,    0,    0,    0,   71,
   66,   68,   67,    0,   69,    0,    0,  146,   64,   62,
   71,   63,    0,   65,    0,   69,    0,    0,    0,   64,
   62,    0,   63,    0,   65,   70,   66,   68,   67,    0,
    0,   70,    0,    0,    0,    0,  149,   66,   68,   67,
   71,    0,    0,    0,    0,   69,    0,    0,  160,   64,
   62,    0,   63,    0,   65,    0,   70,    0,    0,    0,
    0,    0,    0,    0,   70,   71,    0,   66,   68,   67,
   69,   71,    0,  161,   64,   62,   69,   63,    0,   65,
   64,   62,   71,   63,    0,   65,    0,   69,    0,    0,
   70,   64,   66,   68,   67,    0,   65,    0,   66,   68,
   67,   70,   52,   52,   52,   52,   52,    0,   52,   66,
   68,   67,   56,   56,   56,   56,   56,    0,   56,    0,
   52,    0,   53,   53,   53,   53,   53,    0,   53,    0,
   56,   70,   54,   54,   54,   54,   54,    0,   54,    0,
   53,   55,   55,   55,   55,   55,    0,   55,    0,    0,
   54,    0,    0,    0,   52,    0,   70,    0,    0,   55,
    0,    0,   70,    0,   56,   58,   58,   58,   58,   58,
    0,   58,    0,   70,   53,    0,   59,   59,   59,   59,
   59,    0,   59,   58,   54,   57,   57,   57,   57,   57,
    0,   57,    0,   55,   59,    0,    0,    0,    0,    0,
    0,    0,    0,   57,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   58,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   59,    0,
    0,    0,    0,    0,    0,    0,    0,   57,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   91,   33,  125,   33,  125,   33,   40,   58,   40,   33,
   40,   40,   40,  125,   53,   20,   40,   33,  260,  261,
  262,   33,   38,   41,   59,   33,   44,   29,   40,   61,
  259,   61,   40,   61,   33,   59,  259,   36,  125,   41,
   73,   43,   33,   40,   60,   61,   62,   38,  125,   46,
   41,   42,   43,   74,   45,   57,   47,  259,  125,   46,
   62,   63,   64,   65,   66,   67,   68,  106,  125,   60,
   61,   62,  123,  108,   18,   40,   78,   46,   46,   23,
   82,   46,   41,   42,   43,   44,   45,  123,   47,  110,
   44,   59,   61,   41,   91,   97,   61,   99,  133,  101,
   59,  103,  104,  105,   91,  107,   58,   58,  124,  114,
  112,  113,   59,  118,   59,  154,  155,  150,  151,   59,
  123,  259,   91,   91,   38,  164,   91,   61,  124,  131,
  163,  259,   76,  124,   93,  259,  259,   40,  259,  263,
  264,  264,  265,  266,  267,  268,   40,  259,  271,  272,
  152,  153,  264,  265,  266,  267,  268,   41,   60,  271,
  272,   46,  259,  260,  261,  262,   59,   44,  259,  260,
  261,  262,  259,   60,   59,   40,   59,  264,  265,  266,
  267,  268,  259,   62,  271,  272,   62,  264,  265,  266,
  267,  268,  259,  123,  271,  272,  123,  264,  265,  266,
  267,  268,  259,   40,  271,  272,   91,  264,  265,  266,
  267,  268,  123,  269,  271,  272,    0,   41,   41,   41,
   41,   42,   43,   44,   45,  259,   47,  259,   41,  259,
   41,  259,   43,   44,   45,  259,    5,  271,   59,  273,
  274,  273,  274,  273,  274,  273,  274,  259,   59,  273,
  274,  259,   22,   33,  120,   -1,   -1,   -1,  270,   33,
   -1,  273,  274,   -1,   38,  273,  274,   41,   42,   43,
   44,   45,   93,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   93,   -1,   33,   59,   60,   61,   62,   38,
   -1,   -1,   41,   42,   43,   44,   45,   41,   47,   43,
   44,   45,   -1,   41,   42,   43,   44,   45,   -1,   47,
   59,   60,   61,   62,   -1,   59,   -1,   -1,   33,   93,
   -1,   59,   -1,   38,   33,   -1,   -1,   42,   43,   38,
   45,   -1,   47,   42,   43,   -1,   45,   -1,   47,   -1,
   -1,   -1,   -1,   -1,   93,   60,   61,   62,   -1,   93,
  124,   60,   61,   62,   33,   93,   -1,   -1,   -1,   38,
   33,   -1,   -1,   42,   43,   38,   45,   -1,   47,   42,
   43,   -1,   45,   -1,   47,  124,   -1,   -1,   93,   -1,
   59,   60,   61,   62,   93,   33,   59,   60,   61,   62,
   38,   -1,   -1,   33,   42,   43,   -1,   45,   38,   47,
   -1,   41,   42,   43,   -1,   45,   -1,   47,   -1,  124,
   -1,   59,   60,   61,   62,  124,   -1,   -1,   -1,   33,
   60,   61,   62,   -1,   38,   -1,   -1,   41,   42,   43,
   33,   45,   -1,   47,   -1,   38,   -1,   -1,   -1,   42,
   43,   -1,   45,   -1,   47,  124,   60,   61,   62,   -1,
   -1,  124,   -1,   -1,   -1,   -1,   59,   60,   61,   62,
   33,   -1,   -1,   -1,   -1,   38,   -1,   -1,   41,   42,
   43,   -1,   45,   -1,   47,   -1,  124,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  124,   33,   -1,   60,   61,   62,
   38,   33,   -1,   41,   42,   43,   38,   45,   -1,   47,
   42,   43,   33,   45,   -1,   47,   -1,   38,   -1,   -1,
  124,   42,   60,   61,   62,   -1,   47,   -1,   60,   61,
   62,  124,   41,   42,   43,   44,   45,   -1,   47,   60,
   61,   62,   41,   42,   43,   44,   45,   -1,   47,   -1,
   59,   -1,   41,   42,   43,   44,   45,   -1,   47,   -1,
   59,  124,   41,   42,   43,   44,   45,   -1,   47,   -1,
   59,   41,   42,   43,   44,   45,   -1,   47,   -1,   -1,
   59,   -1,   -1,   -1,   93,   -1,  124,   -1,   -1,   59,
   -1,   -1,  124,   -1,   93,   41,   42,   43,   44,   45,
   -1,   47,   -1,  124,   93,   -1,   41,   42,   43,   44,
   45,   -1,   47,   59,   93,   41,   42,   43,   44,   45,
   -1,   47,   -1,   93,   59,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   59,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   93,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   93,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=274;
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
null,null,null,null,null,null,null,null,null,"\">=\"","\"<=\"","\"IDENT\"",
"\"INT\"","\"REAL\"","\"CHAR\"","\"STRUCT\"","\"VAR\"","\"READ\"","\"PRINT\"",
"\"WHILE\"","\"IF\"","\"ELSE\"","\"LITERALCHAR\"","\"CAST\"","\"RETURN\"",
"\"LITERALINT\"","\"LITERALREAL\"",
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
"dimension : '[' expresion ']'",
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
//#line 475 "Parser.java"
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
