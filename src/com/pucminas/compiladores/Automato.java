package com.pucminas.compiladores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.pucminas.compiladores.Utils.*;

public class Automato {
    public static String linha = "";
    public static RegistroLexico registroLexico;
    public static BufferedReader leitor;
    public static TabelaSimbolos tabela;
    private static boolean fim = false;
    static int cont = 1;
    String fonte;

    public Automato() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Digite o nome: ");
        fonte = br.readLine();
        leitor = new BufferedReader(new FileReader(fonte += ".l"));
        tabela = new TabelaSimbolos();
    }

    public static RegistroLexico automato(boolean marcado, char c) throws IOException {
        int estadoAtual = 0;
        int estadoFinal = 2;

        while (estadoAtual != estadoFinal) {

            switch (estadoAtual) {

                case 0:
                    if (marcado) {
                        c = (char) leitor.read();

                        if (!caractereValido(c)) {

                            System.out.println(cont + ":caractere invalido ( " + c + " ).");

                            System.exit(0);
                        }
                    }
                    marcado = true;

                    if (c == '/') {
                        estadoAtual = 5;
                        linha += c;

                    } else if (c == '"') { // Inicio String
                        estadoAtual = 1;
                        linha += c;
                    } else if ((int) c == 39) { // Inicio char
                        estadoAtual = 3;
                        linha += c;
                    } else if (c == '-' || c == '+' || c == '*' || c == '(' || c == ')' || c == ',' || c == '['
                            || c == ']' || c == '%' || c == ';' || c == '=') {

                        estadoAtual = estadoFinal;

                        linha += c;
                    } else if (c == '<') {
                        estadoAtual = 8;
                        linha += c;
                    }else if (c == '>') {
                            estadoAtual = 9;
                            linha += c;
                    } else if (c == ' ') { // Leitura de espaco no inicio
                        estadoAtual = 0;
                    } else if ((int) c == 9) { // TAB
                        estadoAtual = 0;
                    } else if ((int) c == 10) { // \n
                        cont++;
                        estadoAtual = 0;
                    } else if ((int) c == 13) { // \r
                        estadoAtual = 0;
                    } else if (!caractereValido(c)) {
                        System.out.println(cont + ":lexema nao identificado [" + linha + "].");
                        System.exit(0);
                    } else {
                        estadoAtual = estadoFinal;
                        fim = true;
                        leitor.close();
                    }

                    break;

                case 1:
                    c = (char) leitor.read();
                    if (c == '"') // Fim string
                    {
                        estadoAtual = estadoFinal;
                        linha += c;
                    } else if ((int) c == 9) {
                        estadoAtual = estadoFinal;
                    } else if ((int) c == 10) // \n
                    {
                        cont++;
                        estadoAtual = estadoFinal;
                    } else if ((int) c == 13) // \r
                    {
                        estadoAtual = estadoFinal;
                    } else if ((int) c == 65535) // Fim de arquivo
                    {
                        System.out.println(cont + ":fim de arquivo nao esperado.");
                        System.exit(0);
                    }
                    // Corpo String
                    else {
                        linha += c;
                    }
                    break;

                case 3:
                    c = (char) leitor.read();
                    if (letra(c) || digito(c) || isSimbol(c)) {
                        estadoAtual = 4;
                        linha += c;
                    } else if ((int) c == 65535) // Fim de arquivo
                    {
                        System.out.println(cont + ":fim de arquivo não esperado.");
                        System.exit(0);
                    } else {
                        System.out.println(cont + ":lexema nao identificado [" + linha + "].");
                    }
                    break;

                case 4:
                    c = (char) leitor.read();
                    if ((int) c == 39) {
                        estadoAtual = estadoFinal;
                        linha += c;
                    } else if ((int) c == 65535) // Fim de arquivo
                    {
                        System.out.println(cont + ":fim de arquivo não esperado.");
                        System.exit(0);
                    } else {
                        System.out.println(cont + ":lexema nao identificado [" + linha + "].");
                    }
                    break;

                case 5:
                    c = (char) leitor.read();

                    if (c == '*') {
                        estadoAtual = 6;
                        linha = "";
                    } else if (c != '*') {
                        estadoAtual = estadoFinal;
                        marcado = false;
                    }
                case 6:
                    c = (char) leitor.read();
                    if (c == '*') {
                        estadoAtual = 7;
                    } else if ((int) c == 9) {
                        estadoAtual = 6;
                    } else if ((int) c == 10) {  // \n
                        cont++;
                        estadoAtual = 6;
                    } else if ((int) c == 13) { // \r
                        estadoAtual = 6;
                    } else if ((int) c == 65535) {
                        System.out.println(cont + ":fim de arquivo nao esperado.");
                        System.exit(0);
                    } else {
                        estadoAtual = 6;
                    }
                    break;
                case 7:
                    c = (char) leitor.read();
                    if (c == '*') {
                        estadoAtual = 7;
                    } else if (c == '/') // Fim comentario
                    {
                        estadoAtual = 0;
                    } else if ((int) c == 9) {
                        estadoAtual = 6;
                    } else if ((int) c == 10) // \n
                    {
                        cont++;
                        estadoAtual = 6;
                    } else if ((int) c == 13) // \r
                    {
                        estadoAtual = 6;
                    } else if ((int) c == 65535) // Fim de arquivo
                    {
                        System.out.println(cont + ":fim de arquivo n�o esperado.");
                        System.exit(0);
                    } else if (c != '*' && c != '/') // Volta a ler caracteres
                    {
                        estadoAtual = 6;
                    }
                    break;
                case 8:
                    c = (char)leitor.read();
                    if(c == '>' || c == '=' || c == '-'){
                        estadoAtual = estadoFinal;
                        linha += c;
                    }

                    else if (c != '>' || c != '=' || c != '-'){
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

                    else if (c != '='){
                        estadoAtual = estadoFinal;
                        marcado = false;
                    }
                    break;
                default:
                    System.out.println(cont + ":caractere invalido " + c);
                    break;
            }
        }

        if (fim) { // Fim de arquivo

            System.out.println("Fim");

            registroLexico = new RegistroLexico((byte) 65535, linha, marcado, c, cont);

        } else if (!digito(linha.charAt(0)) && linha.charAt(0) != '"') { // Não é numero nem string

            boolean existe = false;

            existe = tabela.existe(linha.toLowerCase());

            if (!existe) { // Identificador

                Simbolo endereco = tabela.inserir(linha.toLowerCase(), "", "");

                registroLexico = new RegistroLexico(endereco.getToken(), linha.toLowerCase(), endereco, marcado, c, cont); //ID

            } else {

                byte token_2 = tabela.pesquisa(linha.toLowerCase());

                registroLexico = new RegistroLexico(token_2, linha.toLowerCase(), marcado, c, cont); //palavras reservadas
            }
        } else {
            boolean constante = true, zero = false;

            if (digito(linha.charAt(0))) {

                if (linha.charAt(0) != '0') {

                    for (int i = 0; i < linha.length() && constante; i++) {

                        constante = digito(linha.charAt(i)); // Verfica se é numero
                    }
                } else {

                    if (!(linha.length() == 4 && linha.charAt(0) == '0' && linha.charAt(1) == 'x' && (letraHexa(linha.charAt(2)) || digito(linha.charAt(2)))

                            && (letraHexa(linha.charAt(3)) || digito(linha.charAt(3))))) { // Hexa

                        constante = false;
                    }

                    if (linha.length() == 1) { // Zero

                        zero = true;

                    } else if (linha.length() > 1) { // Zero com mais caracteres

                        zero = true;

                        for (int i = 1; i < linha.length() && zero; i++) {

                            zero = linha.charAt(i) == '0';

                        }
                    }
                }
            } else if (linha.charAt(0) != '"' || linha.charAt(linha.length() - 1) != '"') { // Verifica se não é String

                constante = false;
            }

            if (constante || zero) {

                registroLexico = new RegistroLexico((byte) 36, linha, marcado, c, cont); //CONSTANTE
            }
        }

        linha = "";
        estadoAtual = 0;

        return registroLexico;
    }

}
