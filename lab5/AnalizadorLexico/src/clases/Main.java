package clases;

import java.io.*;

/**
 *
 * @author Jeremymr2
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String hola = "Hola\n\nsi\n\nno";
        
        hola = hola.replace("\n", " ");
        System.out.println(hola);
    }
}
