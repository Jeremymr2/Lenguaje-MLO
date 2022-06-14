package clases;

import java.util.HashMap;

public class Tokens {
    HashMap<Integer, String> token = new HashMap<Integer, String>();
    String [] reservadas = {"yes","main","loop","whyle","yesnot","write","read"  //Arreglo de palabras reservadas
    ,"class","ent","real","bul","chrct","bond","return","new","void"};
    String [] simbolos = {"+","-","*","/","\\",";","=",">","<","<=",">=","==","!"   //Arreglo de símbolos
    ,"!=","++","--","{","}","[","]","@","_","\"",".","(",")","$","|","&",",",";","'"};
    HashMap<String,Integer> tokenLexema = new HashMap<>();  //HashMap para guardar tokens por lexema

    public Tokens() {
        String reservada = "PALABRA RESERVADA";
        String operador = "OPERADOR";

        //Rellenando por grupo
        for(int i = 0; i < reservadas.length; i++){token.put(i+100,reservada);}
        for(int i = 0; i < simbolos.length; i++){token.put(i+200,operador);}
        token.put(1000,"CADENA");
        token.put(1001,"CARACTER");
        token.put(1002,"CONSTRUCTOR");
        token.put(1003,"ENTERO");
        token.put(1004,"REAL");
        
        //Rellenando para tokens de cada palabra reservada o símbolo
        //Guardando tokens por palabra reservada
        for(int i = 0; i < reservadas.length; i++){tokenLexema.put(reservadas[i], i+100);}
        //Guardando tokens por símbolo
        for(int i = 0; i < simbolos.length; i++){tokenLexema.put(simbolos[i], i+200);}
    }

    public String get(int indice) {
        return token.get(indice);
    }

    public String[] getReservadas(){
        return reservadas;
    }

    public String[] getSimbolos(){
        return simbolos;
    }
    public HashMap<String,Integer> getToken(){
        return tokenLexema;
    } 
}
