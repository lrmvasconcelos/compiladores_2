// Frederico Oliveira Costa Santos, Lucas Rafael Madeira Vasconcelos, Rafael Libânio Solli

public class RegistroLexico{
    int numToken;
    String lexema;
    Simbolo endereco; //IDs
    String tipo; //constantes
    boolean marcado;
    int cont;
    char c;

    //palavras reservadas
    public RegistroLexico(byte token, String lexema, boolean marcado, char c, int cont){
        this.numToken = token;
        this.lexema = lexema;
        this.marcado = marcado;
        this.c = c;
        this.cont = cont;
    }

    public RegistroLexico(byte token, String lexema, Simbolo endereco, boolean marcado, char c, int cont){
        this.numToken = token;
        this.lexema = lexema;
        this.endereco = endereco;
        this.marcado = marcado;
        this.c = c;
        this.cont = cont;
    }

    public int getNumToken(){
        return numToken;
    }

    public void setNumToken(int numToken){
        this.numToken = numToken;
    }

    public String getLexema(){
        return lexema;
    }

    public void setLexema(String lexema){
        this.lexema = lexema;
    }

    public Simbolo getEndereco(){
        return endereco;
    }

    public void setEndereco(Simbolo endereco){
        this.endereco = endereco;
    }

    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public int getCont(){
        return cont;
    }

    public void setCont(int cont){
        this.cont = cont;
    }

    public boolean getMarcado(){
        return this.marcado;
    }

    public char getC(){
        return this.c;
    }
}
