package com.pucminas.compiladores;

import java.io.IOException;

public class Gramatica{
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

    RegistroLexico registro;
    Automato anLex;
    boolean declaracao;

    public Gramatica() throws IOException{
        anLex = new Automato();
        registro = anLex.automato(true, ' ');
        declaracao = false;

        if(registro.getLexema() != ""){
            produzS();
            if(registro.getLexema() != ""){
                Utils.erroTokenInesperado(registro.getCont(), registro.getLexema());
            }
        }
        else{
            Utils.erroFimDeArquivoInesperado(registro.getCont());
        }
    }

    public void casaToken(byte tokenRecebido) throws IOException{
        if(tokenRecebido != (byte) registro.getNumToken()){
            if(registro.getNumToken() == (byte) 65535){
                Utils.erroFimDeArquivoInesperado(registro.getCont());
            }
            
            Utils.erroTokenInesperado(registro.getCont(), registro.getLexema());
        }
        else{
            registro = anLex.automato(registro.getMarcado(), registro.getC());
        }
    }

    public Simbolo casaTokenId(String classe, String tipo) throws IOException{
        Simbolo id = null;

        if((byte) registro.getNumToken() > 36){
            if(registro.getNumToken() == (byte) 65535){
                Utils.erroFimDeArquivoInesperado(registro.getCont());
            }
            else{
                if(!anLex.tabela.existe(registro.getLexema()) || !anLex.tabela.getSimbolo(registro.getLexema()).getTipo().equals("")){
                    Utils.erroIdentificadorJaDeclarado(registro.getCont(), registro.getLexema());
                }
                else{
                    id = anLex.tabela.getSimbolo(registro.getLexema());
                    id.setClasse(classe);
                    id.setTipo(tipo);
                    registro = anLex.automato(registro.getMarcado(), registro.getC());
                }
            }
        }
        else{
            Utils.erroTokenInesperado(registro.getCont(), registro.getLexema());
        }

        return id;
    }

    public Simbolo casaTokenConstante() throws IOException{
        if(registro.getNumToken() == (byte) 65535){
            Utils.erroFimDeArquivoInesperado(registro.getCont());
        }

        else if(registro.getNumToken() == (byte) 36){
            String lexemaTemp = registro.getLexema();

            if(lexemaTemp.charAt(0) == '\'' && lexemaTemp.charAt(2) == '\''){ // Caractere
                registro = anLex.automato(registro.getMarcado(), registro.getC());
                return new Simbolo((byte) 36, lexemaTemp, "constante", "caractere", lexemaTemp);
            }

            else if((Utils.digito(lexemaTemp.charAt(0))) || (lexemaTemp.charAt(0) == '-')){ // Numero
                boolean digito = true;

                for(int i = 1; i < lexemaTemp.length() - 1 && digito; i++){ // Verifica se eh numero
                    digito = Utils.digito(lexemaTemp.charAt(i));
                }

                if(digito){
                    registro = anLex.automato(registro.getMarcado(), registro.getC());
                    return new Simbolo((byte) 36, lexemaTemp, "constante", "inteiro", lexemaTemp);
                }

                else{ // Nao eh formado somente por digitos
                    if(!(lexemaTemp.length() == 4 && lexemaTemp.charAt(0) == '0' && lexemaTemp.charAt(3) == 'h' &&
                       (Utils.letraHexadecimal(lexemaTemp.charAt(1)) || Utils.digito(lexemaTemp.charAt(1))) &&
                       (Utils.letraHexadecimal(lexemaTemp.charAt(2)) || Utils.digito(lexemaTemp.charAt(2))))){ // Hexadecimal
                            registro = anLex.automato(registro.getMarcado(), registro.getC());
                            return new Simbolo((byte) 36, lexemaTemp, "constante", "caractere", lexemaTemp);
                    }
                }
            }
        }

        return null;
    }

    public void produzS() throws IOException{
        while((registro.getNumToken() == FINAL) || (registro.getNumToken() == INT) || (registro.getNumToken() == CHAR)){
            declaracao = true;
            produzD();
        }

        declaracao = false;

        //TODO - procC();
    }

    public Simbolo produzV() throws IOException{
        if(registro.getNumToken() == CONSTANTE){
            Simbolo cte = casaTokenConstante();
            return cte;
        }

        return null;
    }

    public void produzD() throws IOException{
        if((registro.getNumToken() == FINAL) || (registro.getNumToken() == INT) || (registro.getNumToken() == CHAR)){
            String tipo1 = "", tipo2 = "", nomeVariavel = "";
            Simbolo expV;

            if(registro.getNumToken() == FINAL){
                casaToken(FINAL);
                nomeVariavel = registro.getLexema();
                casaTokenId("variavel", "final");
                tipo1 = "final";
            }

            else if(registro.getNumToken() == INT){
                casaToken(INT);
                nomeVariavel = registro.getLexema();
                casaTokenId("variavel", "inteiro");
                tipo1 = "inteiro";
            }

            else if(registro.getNumToken() == CHAR){
                casaToken(CHAR);
                nomeVariavel = registro.getLexema();
                casaTokenId("variavel", "caractere");
                tipo1 = "caractere";
            }

            if(registro.getNumToken() == ATRIBUICAO){
                casaToken(ATRIBUICAO);
                expV = produzV();
                tipo2 = expV.getTipo();

                anLex.tabela.getSimbolo(nomeVariavel).setValor(expV.getValor());

                if(!(tipo1.equals(tipo2))){
                    Utils.erroTiposIncompativeis(registro.getCont(), tipo1, tipo2);
                }
            }
            else if(registro.getNumToken() == IGUAL){
                casaToken(IGUAL);
                expV = produzV();
                tipo2 = expV.getTipo();

                anLex.tabela.getSimbolo(nomeVariavel).setValor(expV.getValor());
                tipo1 = tipo2;
            }
            else if(registro.getNumToken() == ABRECOLCHE){
                casaToken(ABRECOLCHE);
                expV = produzV();
                tipo2 = expV.getTipo();

                if(!(tipo2.equals("inteiro"))){
                    Utils.erroTiposIncompativeis(registro.getCont(), "inteiro", tipo2);
                }

                anLex.tabela.getSimbolo(nomeVariavel).setValor(expV.getValor());
                casaToken(FECHACOLCHE);
            }
            else{
                while(registro.getNumToken() == VIRGULA){
                    casaToken(VIRGULA);
                    nomeVariavel = registro.getLexema();
                    casaTokenId("variavel", tipo1);

                    if(registro.getNumToken() == ABRECOLCHE){
                        casaToken(ABRECOLCHE);
                        expV = produzV();
                        tipo2 = expV.getTipo();

                        if(!(tipo2.equals("inteiro"))){
                            Utils.erroTiposIncompativeis(registro.getCont(), "inteiro", tipo2);
                        }

                        anLex.tabela.getSimbolo(nomeVariavel).setValor(expV.getValor());
                        casaToken(FECHACOLCHE);
                    }
                }
            }

            casaToken(PONTOVIRGULA);
        }
    }
}
