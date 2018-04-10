// Frederico Oliveira Costa Santos, Lucas Rafael Madeira Vasconcelos, Rafael Libânio Solli

public class Simbolo{
    private String lexema;
    private byte token;
    private String classe;
    private String tipo;
    private String valor;

    public Simbolo(){ }

    public Simbolo(byte token, String lexema){
        this.lexema = lexema;
        this.token = token;
    }

    public Simbolo(byte token, String lexema, String classe, String tipo){
        this.lexema = lexema;
        this.token = token;
        this.classe = classe;
        this.tipo = tipo;
    }

    public Simbolo(byte token, String lexema, String classe){
        this.lexema = lexema;
        this.token = token;
        this.classe = classe;
    }

    public Simbolo(byte token, String lexema, String classe, String tipo, String valor){
        this.lexema = lexema;
        this.token = token;
        this.classe = classe;
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getLexema(){
        return lexema;
    }

    public byte getToken(){
        return token;
    }

    public void setLexema(String lexema){
        this.lexema = lexema;
    }

    public void setToken(byte token){
        this.token = token;
    }

    public String getClasse(){
        return this.classe;
    }

    public String getTipo(){
        return this.tipo;
    }

    public void setClasse(String classe){
        this.classe = classe;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public String getValor(){
        return valor;
    }

    public void setValor(String valor){
        this.valor = valor;
    }
}
