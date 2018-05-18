// Frederico Oliveira Costa Santos, Lucas Rafael Madeira Vasconcelos, Rafael Libânio Solli

import java.io.IOException;

public class Compiladores{
    public static void main(String[] args){
        try{
            new Gramatica(args[0]);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
