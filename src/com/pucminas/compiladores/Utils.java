package com.pucminas.compiladores;

public class Utils {
    /**
     *
     * @param c
     * @return
     */
    public static boolean letra(char c) {
        boolean isLetra = false;
        if ((c >= 'a' && c <= 'z'))
            isLetra = true;
        return isLetra;
    }

    /**
     *
     * @param c
     * @return
     */
    public static boolean isSimbol(char c) {
        boolean isSimbol = false;
        if (c == '=' || c == '<' || c == '-' || c == '>' || c == '(' || c == ')' || c == ',' || c == ' '
                || c == '+' || c == '*' || c == '[' || c == ']' || c == '"'
                || (int) c == 39 || c == '/' || c == '%' || c == ';')
            isSimbol = true;

        return isSimbol;
    }

    /**
     *
     * @param c
     * @return
     */
    public static boolean caractereValido(char c) {
        return (letra(c) || digito(c) || isSimbol(c) || (int) c == 39 || (int) c == 65535 || (int) c == 9 || (int) c == 10 || (int) c == 13);
    }

    /**
     *
     * @param c
     * @return
     */
    public static boolean digito(char c) {
        boolean isDigito = false;
        if (c >= '0' && c <= '9')
            isDigito = true;
        return isDigito;
    }

    /**
     *
     * @param c
     * @return
     */
    public static boolean letraHexa(char c) {
        return (c >= 'A' && c <= 'F');
    }
}
