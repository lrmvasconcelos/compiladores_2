// Frederico Oliveira Costa Santos, Lucas Rafael Madeira Vasconcelos, Rafael Libânio Solli

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Automato{
    public static String linha = "";
    public static RegistroLexico registroLexico;
    public static BufferedReader leitor;
    public static TabelaSimbolos tabela;
    private static boolean fim = false;
    static int cont = 1;
    String fonte;
    
    public Automato(String fonte) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        fonte = fonte;
        
        leitor = new BufferedReader(new FileReader(fonte));
        tabela = new TabelaSimbolos();
    }

    public static RegistroLexico automato(boolean marcado, char c) throws IOException{
        int estadoAtual = 0;
        int estadoFinal = 2;

        while(estadoAtual != estadoFinal){
            switch(estadoAtual){
                case 0:
                    if(marcado){
                        c = (char)leitor.read();

                        if(!Utils.caractereValido(c)){
                            Utils.erroCaractereInvalido(cont);
                        }
                    }
                    marcado = true;

                    if(c == '/'){ // Pode ser comentario ou /
                        estadoAtual = 5;
                        linha += c;
                    }
                    else if(c == '"'){ // String
                        estadoAtual = 1;
                        linha += c;
                    }
                    else if((int)c == 39){ // Caractere
                        estadoAtual = 3;
                        linha += c;
                    }
                    else if(c == '-' || c == '+' || c == '*' ||
                            c == '(' || c == ')' || c == ',' ||
                            c == '[' || c == ']' || c == '%' ||
                            c == ';' || c == '='){ // Simbolos
                        estadoAtual = estadoFinal;
                        linha += c;
                    }
                    else if(c == '0'){ // Pode ser hexadecimal ou numero
                        estadoAtual = 12;
                        linha += c;
                    }
                    else if(c == '_'){ // Identificador
                        estadoAtual = 10;
                        linha += c;
                    }
                    else if(c == '<'){ // Pode ser <, <>, <= ou <-
                        estadoAtual = 8;
                        linha += c;
                    }
                    else if(c == '>'){ // Pode ser > ou >=
                        estadoAtual = 9;
                        linha += c;
                    }
                    else if(Utils.letra(c)){ // Identificador
                        estadoAtual = 11;
                        linha += c;
                    }
                    else if(Utils.digito(c) && c != '0'){ // Numero
                        estadoAtual = 17;
                        linha += c;
                    }
                    else if((int)c == 10){ // \n
                        cont++;
                        estadoAtual = 0;
                    }
                    else if((c == ' ') || ((int)c == 9) || ((int)c == 13)){ // Espaco, \t ou \r
                        estadoAtual = 0;
                    }
                    else if(!Utils.caractereValido(c)){ // Caractere invalido
                        Utils.erroLexemaNaoIdentificado(cont, linha);
                    }
                    else{
                        estadoAtual = estadoFinal;
                        fim = true;
                        leitor.close();
                    }

                    break;
                case 1:
                    c = (char)leitor.read();
                    
                    if(c == '"'){ // String
                        estadoAtual = estadoFinal;
                        linha += c;
                    }
                    else if((int)c == 9){ // \t
                        estadoAtual = estadoFinal;
                    }
                    else if((int)c == 10){ // \n
                        cont++;
                        estadoAtual = estadoFinal;
                    }
                    else if((int)c == 13){ // \r
                        estadoAtual = estadoFinal;
                    }
                    else if((int)c == 65535){ // Fim de arquivo
                        estadoAtual = estadoFinal;
                    }
                    else{ // String
                        linha += c;
                    }
                    
                    break;
                case 3:
                    c = (char)leitor.read();
                    
                    if(Utils.letra(c) || Utils.digito(c) || Utils.simbolo(c)){ // Letra, digito ou simbolo
                        estadoAtual = 4;
                        linha += c;
                    }
                    else if((int)c == 65535){ // Fim de arquivo
                        estadoAtual = estadoFinal;
                    }
                    else{
                        Utils.erroLexemaNaoIdentificado(cont, linha);
                    }
                    
                    break;
                case 4:
                    c = (char)leitor.read();
                    
                    if((int)c == 39){ // Caractere
                        estadoAtual = estadoFinal;
                        linha += c;
                    }
                    else if((int)c == 65535){ // Fim de arquivo
                        estadoAtual = estadoFinal;
                    }
                    else{
                        Utils.erroLexemaNaoIdentificado(cont, linha);
                    }
                    
                    break;
                case 5:
                    c = (char)leitor.read();

                    if(c == '*'){ // Comentario
                        estadoAtual = 6;
                        linha = "";
                    }
                    else if(c != '*'){ // /
                        estadoAtual = estadoFinal;
                        marcado = false;
                    }
                    
                    break;
                case 6:
                    c = (char)leitor.read();
                    
                    if(c == '*'){ // Comentario
                        estadoAtual = 7;
                    }
                    else if((int)c == 9){ // \t
                        estadoAtual = 6;
                    }
                    else if((int)c == 10){ // \n
                        cont++;
                        estadoAtual = 6;
                    }
                    else if((int)c == 13){ // \r
                        estadoAtual = 6;
                    }
                    else if((int)c == 65535){
                        estadoAtual = estadoFinal;
                    }
                    else{ // Comentario
                        estadoAtual = 6;
                    }
                    
                    break;
                case 7:
                    c = (char)leitor.read();
                    
                    if(c == '*'){ // Comentario
                        estadoAtual = 7;
                    }
                    else if(c == '/') // Comentario
                    {
                        estadoAtual = 0;
                    }
                    else if((int)c == 9){ // \t
                        estadoAtual = 6;
                    }
                    else if((int)c == 10){ // \n
                        cont++;
                        estadoAtual = 6;
                    }
                    else if((int)c == 13){ // \r
                        estadoAtual = 6;
                    }
                    else if((int)c == 65535){ // Fim de arquivo
                        estadoAtual = estadoFinal;
                    }
                    else if(c != '*' && c != '/') // Comentario
                    {
                        estadoAtual = 6;
                    }
                    
                    break;
                case 8:
                    c = (char)leitor.read();
                    
                    if(c == '>' || c == '=' || c == '-'){ // <>, <= ou <-
                        estadoAtual = estadoFinal;
                        linha += c;
                    }
                    else if(c != '>' || c != '=' || c != '-'){ // <
                        estadoAtual = estadoFinal;
                        marcado = false;
                    }
                    
                    break;
                case 9:
                    c = (char)leitor.read();
                    
                    if(c == '='){
                        estadoAtual = estadoFinal;
                        linha += c;
                    }
                    else if(c != '='){
                        estadoAtual = estadoFinal;
                        marcado = false;
                    }
                    
                    break;
                case 10:
                    c = (char)leitor.read();
                    
                    if(c == '_'){ // Identificador
                        estadoAtual = 10;
                        linha += c;
                    }
                    else if(Utils.letra(c) || Utils.digito(c)){ // Identificador
                        estadoAtual = 11;
                        linha += c;
                    }
                    else if((int)c == 9){ // \t
                        estadoAtual = estadoFinal;
                    }
                    else if((int)c == 10){ // \n
                        cont++;
                        estadoAtual = estadoFinal;
                    }
                    else if((int)c == 13){ // \r
                        estadoAtual = estadoFinal;
                    }
                    else if((int)c == 65535){ // Fim de arquivo
                        estadoAtual = estadoFinal;
                    }
                    else{ // Identificador
                        estadoAtual = estadoFinal;
                        marcado = false;
                    }

                    break;
                case 11:
                    c = (char)leitor.read();
                    
                    if(Utils.digito(c) || Utils.letra(c) || c == '_'){ // Identificador
                        estadoAtual = 11;
                        linha += c;
                    }
                    else if((int)c == 9){ // \t
                        estadoAtual = estadoFinal;
                    }
                    else if((int)c == 10){ // \n
                        cont++;
                        estadoAtual = estadoFinal;
                    }
                    else if((int)c == 13){ // \r
                        estadoAtual = estadoFinal;
                    }
                    else if((int)c == 65535){ // Fim de arquivo
                        estadoAtual = estadoFinal;
                    }
                    else{ // Identificador
                        estadoAtual = estadoFinal;
                        marcado = false;
                    }
                    
                    break;
                case 12:
                    c = (char)leitor.read();
                    
                    if(Utils.digito(c)){ // Pode ser hexadecimal ou numero
                        estadoAtual = 13;
                        linha += c;
                    }
                    else if(Utils.letraHexadecimal(c)){ // Hexadecimal
                        estadoAtual = 14;
                        linha += c;
                    }
                    else{ // Numero
                        estadoAtual = estadoFinal;
                        marcado = false;
                    }
                    
                    break;
                case 13:
                    c = (char)leitor.read();
                    
                    if(Utils.digito(c)){ // Pode ser hexadecimal ou numero
                        estadoAtual = 16;
                        linha += c;
                    }
                    else if(Utils.letraHexadecimal(c)){ // Hexadecimal
                        estadoAtual = 15;
                        linha += c;
                    }
                    else{ // Numero
                        estadoAtual = estadoFinal;
                        marcado = false;
                    }
                    
                    break;
                case 14:
                    c = (char)leitor.read();
                    
                    if(Utils.letraHexadecimal(c) || Utils.digito(c)){ // Hexadecimal
                        linha += c;
                        estadoAtual = 15;
                    }
                    
                    break;
                case 15:
                    c = (char)leitor.read();
                    
                    if(c == 'h'){ // Hexadecimal
                        estadoAtual = estadoFinal;
                        linha += c;
                    }
                    else{ // Hexadecimal
                        estadoAtual = estadoFinal;
                        marcado = false;
                    }
                    
                    break;
                case 16:
                    c = (char)leitor.read();
                    
                    if(c == 'h'){ // Hexadecimal
                        estadoAtual = estadoFinal;
                        linha += c;
                    }
                    else if(Utils.digito(c)){ // Numero
                        estadoAtual = 17;
                        linha += c;
                    }
                    else{ // Hexadecimal
                        estadoAtual = estadoFinal;
                        marcado = false;
                    }
                    
                    break;
                case 17:
                    c = (char)leitor.read();
                    
                    if(Utils.digito(c)){ // Numero
                        estadoAtual = 17;
                        linha += c;
                    }
                    else{ // Numero
                        estadoAtual = estadoFinal;
                        marcado = false;
                    }
                    
                    break;
                default:
                    Utils.erroCaractereInvalido(cont);
                    break;
            }
        }

        if(fim){ // Fim de arquivo
            registroLexico = new RegistroLexico((byte) 65535, linha, marcado, c, cont);
        }
        else if(!Utils.digito(linha.charAt(0)) && linha.charAt(0) != '"'){ // Não é numero nem string
            boolean existe = false;

            existe = tabela.existe(linha.toLowerCase());
            if(!existe){ // Identificador
                Simbolo endereco = tabela.inserir(linha.toLowerCase(), "", "");
                registroLexico = new RegistroLexico(endereco.getToken(), linha.toLowerCase(), endereco, marcado, c, cont); //ID
            }
            else{
                byte token_2 = tabela.pesquisa(linha.toLowerCase());
                registroLexico = new RegistroLexico(token_2, linha.toLowerCase(), marcado, c, cont); //palavras reservadas
            }
        }
        else{
            boolean constante = true, zero = false;

            if(Utils.digito(linha.charAt(0))){
                if(linha.charAt(0) != '0'){
                    for(int i = 0; i < linha.length() && constante; i++){
                        constante = Utils.digito(linha.charAt(i)); // Numero
                    }
                }
                else{
                    if(!(linha.length() == 4 && linha.charAt(0) == '0' && linha.charAt(3) == 'h' &&
                       (Utils.letraHexadecimal(linha.charAt(1)) || Utils.digito(linha.charAt(1))) &&
                       (Utils.letraHexadecimal(linha.charAt(2)) || Utils.digito(linha.charAt(2))))){ // Hexadecimal
                        constante = false;
                    }

                    if(linha.length() == 1){ // Zero
                        zero = true;
                    }
                    else if(linha.length() > 1){ // Zero com mais caracteres
                        zero = true;
                        for(int i = 1; i < linha.length() && zero; i++){
                            zero = linha.charAt(i) == '0';
                        }
                    }
                }
            }
            else if(linha.charAt(0) != '"' || linha.charAt(linha.length() - 1) != '"'){ // Verifica se não é String
                constante = false;
            }

            if(constante || zero){
                registroLexico = new RegistroLexico((byte) 36, linha, marcado, c, cont); //Constante
            }
        }

        linha = "";
        estadoAtual = 0;

        return registroLexico;
    }

}
