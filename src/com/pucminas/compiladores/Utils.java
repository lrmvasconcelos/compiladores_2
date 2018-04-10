package com.pucminas.compiladores;

public class Utils{
    public static boolean letra(char c){
        boolean isLetra = false;
        
        if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')){
            isLetra = true;
        }
        
        return isLetra;
    }

    public static boolean simbolo(char c){
        boolean isSimbol = false;
        
        if(c == '=' || c == '<' || c == '-' ||
           c == '>' || c == '(' || c == ')' ||
           c == ',' || c == ' ' || c == '+' ||
           c == '*' || c == '[' || c == ']' ||
           c == '"' || (int)c == 39 || c == '/' ||
           c == '%' || c == ';'){
            isSimbol = true;
        }

        return isSimbol;
    }

    public static boolean caractereValido(char c){
        return (letra(c) || digito(c) || simbolo(c) ||
                (int)c == 39 || (int)c == 65535 || (int)c == 9 ||
                (int)c == 10 || (int)c == 13);
    }

    public static boolean digito(char c){
        boolean isDigito = false;
        
        if(c >= '0' && c <= '9'){
            isDigito = true;
        }
        
        return isDigito;
    }

    public static boolean letraHexadecimal(char c){
        return (c >= 'A' && c <= 'F');
    }
    
    public static void erroCaractereInvalido(int cont, char c){
        System.out.println("Erro [linha " + cont + "]: Caractere invalido [" + c + " ].");
        System.exit(0);
    }
    
    public static void erroLexemaNaoIdentificado(int cont, String lexema){
        System.out.println("Erro [linha " + cont + "]: Lexema nao identificado [" + lexema + " ].");
        System.exit(0);
    }
    
    public static void erroFimDeArquivoInesperado(int cont){
        System.out.println("Erro [linha " + cont + "]: Fim de arquivo inesperado.");
        System.exit(0);
    }
    
    public static void erroTokenInesperado(int cont, String token){
        System.out.println("Erro [linha " + cont + "]: Token inesperado [" + token + "].");
        System.exit(0);
    }
    
    public static void erroIdentificadorJaDeclarado(int cont, String identificador){
        System.out.println("Erro [linha " + cont + "]: Identificador ja declarado [" + identificador + "].");
        System.exit(0);
    }
    
    public static void erroTiposIncompativeis(int cont, String tipo1, String tipo2){
        System.out.println("Erro [linha " + cont + "]: Tipos incompativeis [" + tipo1 + " e " + tipo2 + "].");
        System.exit(0);
    }
}
