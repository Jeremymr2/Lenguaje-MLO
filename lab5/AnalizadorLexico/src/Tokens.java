import java.util.HashMap;

public class Tokens {
    HashMap<Integer, String> token = new HashMap<Integer, String>();

    public Tokens() {
        String reservada = "PALABRA RESERVADA";
        String operador = "OPERADOR";
        for(int i = 10; i <= 56; i++){
            if(i < 26) token.put(i,reservada);
            else token.put(i,operador);
        }
        token.put(57,"IDENTIFICADOR");
        token.put(58,"ENTERO");
        token.put(59,"FLOTANTE");
        token.put(60,"CONSTRUCTOR");
    }

    public String get(int indice) {
        return token.get(indice);
    }
}
