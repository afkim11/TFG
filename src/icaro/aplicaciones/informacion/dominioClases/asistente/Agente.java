/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.informacion.dominioClases.asistente;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alvaro
 */
public class Agente {

    private String nombreCarpeta;
    private String nombreAgente;
    private ArrayList<Comportamiento> comportamientos;

    public List<Comportamiento> getComportamientos() {
        return comportamientos;
    }

    public void setComportamientos(ArrayList<Comportamiento> comportamientos) {
        this.comportamientos = comportamientos;
    }

    public String getNombreAgente() {
        return nombreAgente;
    }

    public void setNombreAgente(String nombreAgente) {
        this.nombreAgente = nombreAgente;
    }

    public String getNombreCarpeta() {
        return nombreCarpeta;
    }

    public void setNombreCarpeta(String nombreCarpeta) {
        this.nombreCarpeta = nombreCarpeta;
    }

    
}
