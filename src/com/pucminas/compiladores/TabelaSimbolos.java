package com.pucminas.compiladores;

import java.util.HashMap;

public class TabelaSimbolos{
    public static HashMap<String, Simbolo> token = new HashMap<String, Simbolo>();

    private final byte FINAL = 0;
    private final byte INT = 1;
    private final byte CHAR = 2;
    private final byte FOR = 3;
    private final byte IF = 4;
    private final byte IGUAL = 5;
    private final byte ELSE = 6;
    private final byte AND = 7;
    private final byte OR = 8;
    private final byte NOT = 9;
    private final byte ATRIBUICAO = 10;
    private final byte TO = 11;
    private final byte ABREPAREN = 12;
    private final byte FECHAPAREN = 13;
    private final byte MENOR = 14;
    private final byte MAIOR = 15;
    private final byte DIFERENTE = 16;
    private final byte MAIORIGUAL = 17;
    private final byte MENORIGUAL = 18;
    private final byte VIRGULA = 19;
    private final byte MAIS = 20;
    private final byte MENOS = 21;
    private final byte VEZES = 22;
    private final byte DIVIDIDO = 23;
    private final byte PONTOVIRGULA = 24;
    private final byte BEGIN = 25;
    private final byte END = 26;
    private final byte THEN = 27;
    private final byte READLN = 28;
    private final byte STEP = 29;
    private final byte WRITE = 30;
    private final byte WRITELN = 31;
    private final byte PORCENT = 32;
    private final byte ABRECOLCHE = 33;
    private final byte FECHACOLCHE = 34;
    private final byte DO = 35;
    private final byte CONSTANTE = 36;
    private int n = 37;

    public TabelaSimbolos(){
        token.put("final", new Simbolo(FINAL, "final"));
        token.put("int", new Simbolo(INT, "int"));
        token.put("char", new Simbolo(CHAR, "char"));
        token.put("for", new Simbolo(FOR, "for"));
        token.put("if", new Simbolo(IF, "if"));
        token.put("=", new Simbolo(IGUAL, "="));
        token.put("else", new Simbolo(ELSE, "else"));
        token.put("and", new Simbolo(AND, "and"));
        token.put("or", new Simbolo(OR, "or"));
        token.put("not", new Simbolo(NOT, "not"));
        token.put("<-", new Simbolo(ATRIBUICAO, "<-"));
        token.put("to", new Simbolo(TO, "to"));
        token.put("(", new Simbolo(ABREPAREN, "("));
        token.put(")", new Simbolo(FECHAPAREN, ")"));
        token.put("<", new Simbolo(MENOR, "<"));
        token.put(">", new Simbolo(MAIOR, ">"));
        token.put("<>", new Simbolo(DIFERENTE, "<>"));
        token.put(">=", new Simbolo(MAIORIGUAL, ">="));
        token.put("<=", new Simbolo(MENORIGUAL, "<="));
        token.put(",", new Simbolo(VIRGULA, ","));
        token.put("+", new Simbolo(MAIS, "+"));
        token.put("-", new Simbolo(MENOS, "-"));
        token.put("*", new Simbolo(VEZES, "*"));
        token.put("/", new Simbolo(DIVIDIDO, "/"));
        token.put(";", new Simbolo(PONTOVIRGULA, ";"));
        token.put("begin", new Simbolo(BEGIN, "begin"));
        token.put("end", new Simbolo(END, "end"));
        token.put("then", new Simbolo(THEN, "then"));
        token.put("readln", new Simbolo(READLN, "readln"));
        token.put("step", new Simbolo(STEP, "step"));
        token.put("write", new Simbolo(WRITE, "write"));
        token.put("writeln", new Simbolo(WRITELN, "writeln"));
        token.put("%", new Simbolo(PORCENT, "%"));
        token.put("[", new Simbolo(ABRECOLCHE, "["));
        token.put("]", new Simbolo(FECHACOLCHE, "]"));
        token.put("do", new Simbolo(DO, "do"));
        token.put("const", new Simbolo(CONSTANTE, "const"));
    }

    //Pesquisar
    public byte pesquisa(String lexema){
        Simbolo a = token.get(lexema);
        return a.getToken();
    }

    //Inserir
    public Simbolo inserir(String lexema, String classe, String tipo){
        Simbolo simbolo = new Simbolo((byte) n++, lexema, classe, tipo);
        token.put(lexema, simbolo);
        return token.get(lexema);
    }

    //Verifica se Lexama Existe
    public boolean existe(String lexema){
        return token.containsKey(lexema);
    }

    public Simbolo getSimbolo(String lexema){
        Simbolo a = token.get(lexema);
        return a;
    }
}
