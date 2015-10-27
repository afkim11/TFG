/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.informacion.dominioClases.asistente;

/**
 *
 * @author Alvaro
 */
public class Comportamiento {

    private String nombreCarpeta;
    private boolean ficheroAccionesSemanticas;
    private boolean ficheroAutomata;

    public Comportamiento(){
        ficheroAccionesSemanticas = false;
        ficheroAutomata = false;
    }

    public boolean isFicheroAccionesSemanticas() {
        return ficheroAccionesSemanticas;
    }

    public void setFicheroAccionesSemanticas(boolean ficheroAccionesSemanticas) {
        this.ficheroAccionesSemanticas = ficheroAccionesSemanticas;
    }

    public boolean isFicheroAutomata() {
        return ficheroAutomata;
    }

    public void setFicheroAutomata(boolean ficheroAutomata) {
        this.ficheroAutomata = ficheroAutomata;
    }

    public String getNombreCarpeta() {
        return nombreCarpeta;
    }

    public void setNombreCarpeta(String nombreCarpeta) {
        this.nombreCarpeta = nombreCarpeta;
    }

    
}
