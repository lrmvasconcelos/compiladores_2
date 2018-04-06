package com.pucminas.compiladores;

import java.io.IOException;

public class Gramatica {

    RegistroLexico registro;
    Automato anLex;
    boolean declaracao;

    public Gramatica() throws IOException {
        anLex = new Automato();
        registro = anLex.automato(true, ' ');
    }
}
