// Frederico Oliveira Costa Santos, Lucas Rafael Madeira Vasconcelos, Rafael Libânio Solli

import java.io.IOException;

public class Compiladores{
    public static void main(String[] args){
        System.out.println("Compiladores");

        try{
            Gramatica gramatica = new Gramatica();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
