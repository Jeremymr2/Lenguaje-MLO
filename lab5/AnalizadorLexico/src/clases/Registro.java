
package clases;

/**
 *
 * @author Jeremymr2
 */
public class Registro {
    private Integer token;
    private String descripcion;
    private String lexema;

    public Registro(Integer token, String descripcion, String lexema) {
        this.token = token;
        this.descripcion = descripcion;
        this.lexema = lexema;
    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }
    
    
}
