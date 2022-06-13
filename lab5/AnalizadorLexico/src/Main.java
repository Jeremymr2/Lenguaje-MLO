/**
 *
 * @author Jeremymr2
 */
public class Main {
    public static void main(String[] args) {
        String p = "hola 123.4 12 hola1 whyle main yesnot write read ; / \\ \" {} yesnotread 13.4 3 & hola_ ho_la ji 13.1";
        AnalizadorLexico lex = new AnalizadorLexico(p);
        lex.analizar();
    }
}
