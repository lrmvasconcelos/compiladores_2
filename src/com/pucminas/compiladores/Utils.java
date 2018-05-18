// Frederico Oliveira Costa Santos, Lucas Rafael Madeira Vasconcelos, Rafael Libânio Solli

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
    
    public static void erroCaractereInvalido(int cont){
        System.out.println(cont + ": Caractere invalido.");
        System.exit(0);
    }
    
    public static void erroLexemaNaoIdentificado(int cont, String lexema){
        System.out.println(cont + ": Lexema nao identificado [" + lexema + " ].");
        System.exit(0);
    }
    
    public static void erroFimDeArquivoInesperado(int cont){
        System.out.println(cont + ": Fim de arquivo nao esperado.");
        System.exit(0);
    }
    
    public static void erroTokenInesperado(int cont, String token){
        System.out.println(cont + ": Token nao esperado [" + token + "].");
        System.exit(0);
    }
    
    public static void erroIdentificadorNaoDeclarado(int cont, String identificador){
        System.out.println(cont + ": Identificador nao declarado [" + identificador + "].");
        System.exit(0);
    }
    
    public static void erroIdentificadorJaDeclarado(int cont, String identificador){
        System.out.println(cont + ": Identificador ja declarado [" + identificador + "].");
        System.exit(0);
    }
    
    public static void erroClasseIdentificadorIncompativel(int cont, String identificador){
        System.out.println(cont + ": Classe de identificador incompatível [" + identificador + "].");
        System.exit(0);
    }
    
    public static void erroTiposIncompativeis(int cont){
        System.out.println(cont + ": Tipos incompativeis.");
        System.exit(0);
    }
    
    public static void erroTamanhoVetorExcedeMaximoPermitido(int cont){
        System.out.println(cont + ": Tamanho do vetor excede o maximo permitido.");
        System.exit(0);
    }
}
