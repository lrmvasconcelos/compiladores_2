package com.pucminas.compiladores;

import java.io.IOException;

public class Gramatica
{
    private final byte FINAL = 0;
    private final byte INT = 1;
    private final byte CHAR = 2;
    private final byte FOR = 3;
    private final byte IF = 4;
    private final byte IGUAL = 5;
    private final byte ELSE = 6;
    private final byte AND = 7;
    private final byte OR = 8;
    private final byte NOT =9;
    private final byte ATRIBUICAO = 10;
    private final byte TO = 11;
    private final byte ABREPAREN = 12;
    private final byte FECHAPAREN = 13;
    private final byte MENOR = 14;
    private final byte MAIOR = 15;
    private final byte DIFERENTE = 16;
    private final byte MAIORIGUAL = 17;
    private final byte MENORIGUAL = 18;
    private final byte VIRGULA = 19;
    private final byte MAIS = 20;
    private final byte MENOS = 21;
    private final byte VEZES = 22;
    private final byte DIVIDIDO = 23;
    private final byte PONTOVIRGULA = 24;
    private final byte BEGIN = 25;
    private final byte END = 26;
    private final byte THEN = 27;
    private final byte READLN = 28;
    private final byte STEP = 29;
    private final byte WRITE = 30;
    private final byte WRITELN = 31;
    private final byte PORCENT = 32;
    private final byte ABRECOLCHE = 33;
    private final byte FECHACOLCHE = 34;
    private final byte DO = 35;
	private final byte CONSTANTE = 36;

    RegistroLexico registro;
    Automato anLex;
    boolean declaracao;

    public Gramatica() throws IOException
	{
        anLex = new Automato();
        registro = anLex.automato(true, ' ');
		declaracao = false;
		
		if(registro.getLexema() != "")
		{
			procS();
			if(registro.getLexema() != "")
			{
				System.out.println(registro.getCont()+":token nao esperado  ["+ registro.getLexema() + "].");
				System.exit(0);
			}
		}
		else
		{
			System.out.println(registro.getCont()+":fim de arquivo nao esperado.");
			System.exit(0);
		}
    }
	
	public void casaToken(byte tokenRecebido) throws IOException{
		if(tokenRecebido != (byte)registro.getNumToken())
		{
			if(registro.getNumToken()==(byte)65535)
			{
				System.out.println(registro.getCont()+":fim de arquivo nao esperado.");
				System.exit(0);
			}
			System.out.println(registro.getCont()+":token nao esperado ["+ registro.getLexema() + "].");
			System.exit(0);
			
		}
		else
		{
			registro = anLex.automato(registro.getMarcado(), registro.getC());
		}
	}
	
	public Simbolo casaTokenId(String classe, String tipo) throws IOException
	{
		Simbolo id = null;
		
		if((byte)registro.getNumToken() > 36)
		{
			if(registro.getNumToken() == (byte)65535)
			{
				System.out.println(registro.getCont()+":fim de arquivo nao esperado.");
				System.exit(0);
			}
			else
			{
				if(!anLex.tabela.existe(registro.getLexema()) || !anLex.tabela.getSimbolo(registro.getLexema()).getTipo().equals(""))
				{
					System.out.println(registro.getCont() + ":identificador ja declarado " + "[" + registro.getLexema() + "].");
					System.exit(0);
				}
				else
				{
					id = anLex.tabela.getSimbolo(registro.getLexema());
					id.setClasse(classe);
					id.setTipo(tipo);
					registro = anLex.automato(registro.getMarcado(), registro.getC());
				}
			}
		}
		else
		{
			System.out.println(registro.getCont()+":token nao esperado  ["+ registro.getLexema() + "].");
			System.exit(0);
		}
		
		return id;
	}
	
	public Simbolo casaTokenConstante() throws IOException
	{
		if(registro.getNumToken() == (byte)65535)
		{
			System.out.println(registro.getCont()+":fim de arquivo nao esperado.");
			System.exit(0);
		}
		
		else if(registro.getNumToken() == (byte)36)
		{
			String lexemaTemp = registro.getLexema();
			
			if(lexemaTemp.charAt(0) == '\'' && lexemaTemp.charAt(2) == '\'') // Caractere
			{
				registro = anLex.automato(registro.getMarcado(), registro.getC());
				return new Simbolo((byte)36, lexemaTemp, "constante", "caractere", lexemaTemp);
			}
			
			else if((anLex.digito(lexemaTemp.charAt(0))) || (lexemaTemp.charAt(0) == '-')) // Numero
			{
				boolean digito = true;
				
				for(int i = 1; i < lexemaTemp.length()-1 && digito; i++) // Verifica se eh numero
				{
					digito = anLex.digito(lexemaTemp.charAt(i));
				}
				
				if(digito)
				{
					registro = anLex.automato(registro.getMarcado(), registro.getC());
					return new Simbolo((byte)36, lexemaTemp, "constante", "inteiro", lexemaTemp);
				}
				
				else // Nao eh formado somente por digitos
				{
					if(lexemaTemp.length() == 4)
					{
						if(lexemaTemp.charAt(0) == '0' && (lexemaTemp.charAt(3) == 'h') &&
						   (anLex.digito(lexemaTemp.charAt(1)) || anLex.letraHexa(lexemaTemp.charAt(1))) &&
						   (anLex.digito(lexemaTemp.charAt(2)) || anLex.letraHexa(lexemaTemp.charAt(2)))) // Hexa no formato 0DDh
						{
							registro = anLex.automato(registro.getMarcado(), registro.getC());
							return new Simbolo((byte)36, lexemaTemp, "constante", "caractere", lexemaTemp);
						}
					}
				}
			}
		}
		
		return null;
	}
	
	public void procS() throws IOException
	{		
		while((registro.getNumToken() == FINAL) || (registro.getNumToken() == INT) || (registro.getNumToken() == CHAR))
		{
			declaracao = true;
			procD();
		}
		
		declaracao =  false;
		
		procC();
	}
	
	public Simbolo procV() throws IOException
	{
		if(registro.getNumToken() == CONSTANTE)
		{
			Simbolo cte = casaTokenConstante();
			return cte;
		}
	}
	
	public void procD() throws IOException
	{
		if(registro.getNumToken() == FINAL) || (registro.getNumToken() == INT) || (registro.getNumToken() == CHAR))
		{
			String tipo1 = "", tipo2 = "", nomeVariavel = "";
			Simbolo expV;
			
			if(registro.getNumToken() == FINAL)
			{
				casaToken(FINAL);
				nomeVariavel = registro.getLexema();
				casaTokenId("variavel", "final");
				tipo1 = "final";
			}
			
			else if(registro.getNumToken() == INT)
			{
				casaToken(INT);
				nomeVariavel = registro.getLexema();
				casaTokenId("variavel", "inteiro");
				tipo1 = "inteiro";
			}
			
			else if(registro.getNumToken() == CHAR)
			{
				casaToken(CHAR);
				nomeVariavel = registro.getLexema();
				casaTokenId("variavel", "caractere");
				tipo1 = "caractere";
			}
			
			if(registro.getNumToken() == ATRIBUICAO)
			{
				casaToken(ATRIBUICAO);
				expV = procV();
				tipo2 = expV.getTipo();
				
				anLex.tabela.getSimbolo(nomeVariavel).setValor(expV.getValor());
				
				if(!(tipo1.equals(tipo2)))
				{
					System.out.println((registro.getCont()) + ":tipos incompativeis.");
					System.exit(0);
				}
			}
			else if(registro.getNumToken() == IGUAL)
			{
				casaToken(IGUAL);
				expV = procV();
				tipo2 = expV.getTipo();
				
				anLex.tabela.getSimbolo(nomeVariavel).setValor(expV.getValor());
				
				tipo1 = tipo2;
			}
			else if(registro.getNumToken() == ABRECOLCHE){
				casaToken(ABRECOLCHE);
				expV = procV();
				tipo2 = expV.getTipo();
				
				if(!(tipo2.equals("inteiro")))
				{
					System.out.println((registro.getCont()) + ":tipos incompativeis.");
					System.exit(0);
				}
				
				anLex.tabela.getSimbolo(nomeVariavel).setValor(expV.getValor());
				
				casaToken(FECHACOLCHE);
			}
			else{
				while(registro.getNumToken() == VIRGULA)
				{
					casaToken(VIRGULA);
					nomeVariavel = registro.getLexema();
					casaTokenId("variavel", tipo1);
					
					if(registro.getNumToken() == ABRECOLCHE){
						casaToken(ABRECOLCHE);
						expV = procV();
						tipo2 = expV.getTipo();
				
						if(!(tipo2.equals("inteiro")))
						{
							System.out.println((registro.getCont()) + ":tipos incompativeis.");
							System.exit(0);
						}
						
						anLex.tabela.getSimbolo(nomeVariavel).setValor(expV.getValor());
						
						casaToken(FECHACOLCHE);
					}
				}
			}
			
			casaToken(PONTOVIRGULA);
		}
	}
	
}
