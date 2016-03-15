/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.informacion.dominioClases.asistente;

/**
 *
 * @author alvaro
 */
public class Recurso {

    private String nombreCarpeta;
    private String nombreRecurso;
    private boolean interfazUso;
    private boolean claseGeneradora;

    public Recurso(){
        interfazUso = false;
        claseGeneradora = false;
    }

    public boolean isClaseGeneradora() {
        return claseGeneradora;
    }

    public void setClaseGeneradora(boolean claseGeneradora) {
        this.claseGeneradora = claseGeneradora;
    }

    public boolean isInterfazUso() {
        return interfazUso;
    }

    public void setInterfazUso(boolean interfazUso) {
        this.interfazUso = interfazUso;
    }

    public String getNombreCarpeta() {
        return nombreCarpeta;
    }

    public void setNombreCarpeta(String nombreCarpeta) {
        this.nombreCarpeta = nombreCarpeta;
    }

    public String getNombreRecurso() {
        return nombreRecurso;
    }

    public void setNombreRecurso(String nombreRecurso) {
        this.nombreRecurso = nombreRecurso;
    }
    
}
