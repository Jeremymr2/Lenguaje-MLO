package clases;

import java.io.*;

/**
 *
 * @author Jeremymr2
 */
public class Main {
    public static void main(String[] args) throws IOException {
        File archivo = new File("C:\\Users\\jmr2_\\OneDrive\\Escritorio\\test.txt");
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String p;
        String temp = "";
        //Llenando la cadena
        while((p = br.readLine()) != null){
            temp += p;
        }
        AnalizadorLexico lex = new AnalizadorLexico(temp);
        lex.analizar();
    }
}
