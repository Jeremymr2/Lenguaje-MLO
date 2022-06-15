package clases;

import vistas.Vista;

/**
 *
 * @author jeremymr2
 */
public class AnalizadorLexico {
    String lexema, cadena;  //Lexema: buffer que irá almacenando caracteres, cadena: input del usuario
    int iterador = 0;
    int token, indice = 0, indiceLexema = 0;    //Token: código de cada lexema, indice: iterador de la cadena, indiceLexema: iterador del lexema
    Tokens tokenList = new Tokens();
    Vista vista = new Vista();  //Para usar las tablas
    int filaAll = 0, filaSimb = 0;   //Fila de la tabla
    
    //Constructor
    public AnalizadorLexico(){
    }
    public AnalizadorLexico(String cadena){
        this.cadena = cadena;
    } 

    //Método que devuelve tokens de la cadena
    public int verificar() {
        int tipo = 0;   // 0: inicial | 1: letra | 2: digito
        char car,nextCar = '#';       // Irá almacenando caracteres de la cadena
        boolean entero = true; //Verifica si es entero o flotante
        lexema = "";    // Inicializando en 0 el buffer
        indiceLexema = 0;//Iterador del lexema
        for (;;) {
            car = cadena.charAt(indice);
            if(car!='#') nextCar = cadena.charAt(indice+1);

            if (car == '#' && indiceLexema == 0){   //Verifica si hay un # al inicio de la cadena
                return 0;
            } else if (car == '#') {    //Verifica el # al final de la cadena
                switch (tipo) {
                    case 1 -> {
                        //IDENTIFICADOR
                        if(isReservada(lexema)) return tokenList.getToken().get(lexema); //Si está en el arreglo es palabra reservada
                        else if (lexema.length() == 1) return 1001;
                        else if (lexema.charAt(lexema.length()-1) == '_') return 1002;    //ES CONSTRUCTOR
                        else return 1000; //ES CADENA
                    }
                    case 2 -> {
                        //ENTERO
                        if (entero) return 1003;
                        else return 1004;
                    }
                    default -> {
                            return 911;
                    }
                }
            } else {
                lexema = lexema.trim();
                switch (tipo) {     //VERIFICA EL TIPO
                    case 0 -> {     //INICIAL
                        addLexema();
                        if (Character.isLetter(car)) {
                            tipo = 1;
                            indice++;
                        } else if (Character.isDigit(car)) {
                            tipo = 2;
                            indice++;
                        } else if(isSimbolo(car)){
                            if (car == '\\'){
                                if (nextCar=='n'){
                                    indice++; 
                                    indice++;
                                    lexema = "";
                                    break;
                                }
                                else if (nextCar=='t'){
                                    indice++; 
                                    indice++; 
                                    lexema = "";
                                    break;
                                } else {
                                    indice++;
                                    return tokenList.getToken().get(String.valueOf(car));
                                }
                            } else if(car == '+'){
                                if (nextCar=='+'){
                                    indice++;
                                    addLexema();
                                    indice++;
                                    return 214;
                                } else {
                                    indice++;
                                    return tokenList.getToken().get(String.valueOf(car));
                                }
                            } else if(car == '-'){
                                if (nextCar=='-'){
                                    indice++;
                                    addLexema();
                                    indice++;
                                    return 215;
                                } else {
                                    indice++;
                                    return tokenList.getToken().get(String.valueOf(car));
                                }
                            } else if(car == '<' || car == '>' || car == '=' || car == '!'){
                                if (nextCar=='='){
                                    indice++;
                                    addLexema();
                                    indice++;
                                    if(car == '<') return 209;
                                    else if (car == '>') return 210;
                                    else if (car == '=') return 211; 
                                    else return 213;
                                } else {
                                    indice++;
                                    return tokenList.getToken().get(String.valueOf(car));
                                }
                            } else{
                                indice++;
                                return tokenList.getToken().get(String.valueOf(car));
                            }
                        } else if (Character.isSpaceChar(car)){     
                            indice++;                       
                        } else {
                            return 911;
                        }
                    }
                    case 1 -> {     //LETRA
                        if (Character.isLetterOrDigit(car)) {
                            tipo = 1;
                            addLexema();
                            indice++;
                        } else if (car == ' ') {
                            indice++;
                            if(isReservada(lexema)) return tokenList.getToken().get(lexema);
                            else if (lexema.length() == 1) return 1001;
                            else if (lexema.charAt(lexema.length()-1) == '_') return 1002;
                            else return 1000;
                        } else if (isSimbolo(car)){
                            return 1000;
                        }else {
                            addLexema();
                            indice++;
                        }
                    }
                    case 2 -> {     //DIGITO
                        if (Character.isDigit(car)) {
                            tipo = 2;
                            addLexema();
                            indice++;
                        } else if (car == ' ') {
                            indice++;
                            if (entero) return 1003;
                            else return 1004;
                        } else if (car == '.'){
                            tipo = 2;
                            entero = false;
                            addLexema();
                            indice++;
                        } else if (isSimbolo(car)){
                            if (entero) return 1003;
                            else return 1004;
                        } else{
                            return 911;
                        }
                    }
                    default -> {
                    }
                }   //Fin del Switch
            }   //Fin del if-else
        }   //Fin del for
    }   

    //Método que analiza la efectividad del programa
    public void analizar() {
        boolean band = true;
        cadena = cadena.trim(); //Eliminando espacios vacíos de inicio y fin de la cadena 
        cadena += '#';          //Concatenando caracter centinela al final de la cadena
        do {
            token = verificar();//Llamando al método verificar para que devuelva el valor de token

            if(token == 0){
                System.out.println("Finalizó con éxito");
                band = false;
            } else if (token == 911) {
                System.out.println("Error");
                band = false;
            }else if (token >= 100 && token <= 1004){
                imprimir();
            } 
        } while (band);
    }

    //Método que agrega un caracter al lexema
    public void addLexema() {
        lexema += cadena.charAt(indice);
        indiceLexema++;
    }

    //Método que imprime TOKEN - DESCRIPCION - LEXEMA
    public void imprimir() {
        Tokens descripcion = new Tokens();
        System.out.println(token + " " + descripcion.get(token) + " " + lexema);
        Registro r = new Registro(token, descripcion.get(token), lexema);
        arrayRegistros.registros.add(r);
        if (isSimbolo(lexema.charAt(0))) arrayRegistros.simbolos.add(r);
    }

    //Método que verifica si la palabra pertenece al arreglo de palabras reservadas
    public boolean isReservada(String palabra){
        boolean band=false;
        for(String verificador:tokenList.getReservadas()){
            if(palabra.equals(verificador)){
                band=true;
            }
        }
        return band;
    }

    //Método que verifica si la palabra pertenece al arreglo de símbolo
    public boolean isSimbolo(char palabra){
        String pal = String.valueOf(palabra);
        for(String verificador:tokenList.getSimbolos()){
            if(pal.equals(verificador)){
                return true;
            }
        }
        return false;
    }
}
