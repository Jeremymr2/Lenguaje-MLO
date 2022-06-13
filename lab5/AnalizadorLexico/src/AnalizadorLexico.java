import java.util.HashMap;

/**
 *
 * @author jeremymr2
 */
public class AnalizadorLexico {
    String lexema, cadena;  //Lexema: buffer que irá almacenando caracteres, cadena: input del usuario
    int token, indice = 0, indiceLexema = 0;    //Token: código de cada lexema, indice: iterador de la cadena, indiceLexema: iterador del lexema
    String [] reservada = {"yes","main","loop","whyle","yesnot","write","read"  //Arreglo de palabras reservadas
    ,"class","ent","real","bul","chrct","bond","return","new","void"};
    String [] simbolos = {"+","-","*","/","\\",";",">","<","<=",">=","==","!"   //Arreglo de símbolos
    ,"!=","++","--","{","}","[","]","@","_","\"",".","(",")","$","|","&",",",";","'"};
    HashMap<String,Integer> tokenLexema = new HashMap<>();  //HashMap para guardar tokens por lexema

    //Constructor
    public AnalizadorLexico(String cadena){
        this.cadena = cadena;
        String[] concatenado = new String[reservada.length + simbolos.length];  //Crear arreglo de concatenación de reservada y simbolos
        System.arraycopy(reservada, 0, concatenado, 0, reservada.length);
        System.arraycopy(simbolos, 0, concatenado, reservada.length, simbolos.length);
        for(int i = 0; i < concatenado.length; i++){
            tokenLexema.put(concatenado[i], i+10);  //Guardando tokens por palabra reservada y símbolo
        }
    }
    //Método que devuelve tokens de la cadena
    public int verificar() {
        int tipo = 0;   // 0: inicial | 1: letra | 2: digito
        char car;       // Irá almacenando caracteres de la cadena
        boolean entero = true; //Verifica si es entero o flotante
        lexema = "";    // Inicializando en 0 el buffer
        indiceLexema = 0;//Iterador del lexema
        for (;;) {
            car = cadena.charAt(indice);
            if (car == '#' && indiceLexema == 0){   //Verifica si hay un # al inicio de la cadena
                return 0;
            } else if (car == '#') {    //Verifica el # al final de la cadena
                if (tipo == 1){ //IDENTIFICADOR
                    if(isReservada(lexema)) return tokenLexema.get(lexema); //Si está en el arreglo es palabra reservada
                    else return 57; //Si no es identificador
                } else if (tipo == 2) { //ENTERO
                    if (entero) return 58;
                    else return 59;
                } else {
                    return 911;
                }
            } else {
                lexema = lexema.trim();
                switch (tipo) {     //VERIFICA EL TIPO
                    case 0 -> {     //INICIAL
                        addLexema();
                        if (Character.isLetter(car)) {
                            tipo = 1;
                        } else if (Character.isDigit(car)) {
                            tipo = 2;
                        } else if(isSimbolo(car)){
                            indice++;
                            return tokenLexema.get(String.valueOf(car));
                        } else if (Character.isSpaceChar(car)){                            
                        }
                    }
                    case 1 -> {     //LETRA
                        if (Character.isLetterOrDigit(car)) {
                            tipo = 1;
                            addLexema();
                        } else if (car == ' ') {
                            if(isReservada(lexema)) return tokenLexema.get(lexema);
                            else if (lexema.charAt(lexema.length()-1) == '_') return 60;
                            else return 57;
                        } else {
                            addLexema();
                        }
                    }
                    case 2 -> {     //DIGITO
                        if (Character.isDigit(car)) {
                            tipo = 2;
                            addLexema();
                        } else if (car == ' ') {
                            if (entero) return 58;
                            else return 59;
                        } else if (car == '.'){
                            tipo = 2;
                            entero = false;
                            addLexema();
                        }
                    }
                    default -> {
                    }
                }   //Fin del Switch
                indice++;
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
            //System.out.println("PRUEBA: " + token);
            if(token == 0){
                System.out.println("Finalizó con éxito");
                band = false;
            } else if (token >= 10 && token <= 60){
                imprimir();
            } else {
                System.out.println("Error");
                band = false;
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
    }

    //Método que verifica si la palabra pertenece al arreglo de palabras reservadas
    public boolean isReservada(String palabra){
        boolean band=false;
        for(String verificador:reservada){
            if(palabra.equals(verificador)){
                band=true;
            }
        }
        return band;
    }

    //Método que verifica si la palabra pertenece al arreglo de símbolo
    public boolean isSimbolo(char palabra){
        String pal = String.valueOf(palabra);
        for(String verificador:simbolos){
            if(pal.equals(verificador)){
                return true;
            }
        }
        return false;
    }
}
