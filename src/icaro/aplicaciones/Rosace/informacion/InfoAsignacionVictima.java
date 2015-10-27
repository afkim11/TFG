/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.informacion;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author FGarijo
 */
public class InfoAsignacionVictima implements Serializable{

    private long tiempoInicialDeLaSimulacion = 0;      //Variable para obtener el tiempo al iniciar la simulacion
    private int numeroVictimasEntorno = 0;            //Numero de victimas actuales que se han introducido en el entorno    
    private int numeroVictimasAsignadas = 0;          //Numero de victimas asignadas a robots
    private int numeroVictimasDiferentesSimulacion; //Numero de victimas diferentes que van a intervenir en el proceso de simulacion
    private int numeroRobotsSimulacion = 0;         //Numero de robots diferentes que van a intervenir en el proceso de simulacion	
    private int intervaloSecuencia;                 //Intervalo uniforme de envio de la secuencia de victimas
    private Map<String, Victim> victims2Rescue = new HashMap<String, Victim>();      //Victimas que hay en el entorno    
    private Map<String, String> victimasAsignadas = new HashMap<String, String>();
    private Victim victima;
    private String robotId;
    private int evaluacion;
    private long tiempoAsignacion;
    private long tiempoPeticion;
    private int nrovictimastotalasignadas;
    private int nrovictimasenentorno;
//    private InfoContextoAsignacionVictima infoCtxtAsingacionVictima;

    public InfoAsignacionVictima() {
    }

    public Victim getVictima() {
        return victima;
    }

    public void setVictima(Victim vict) {
        this.victima = vict;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robot) {
        this.robotId = robot;
    }

    public int getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(int evaluacion) {
        this.evaluacion = evaluacion;
    }

    public long getTiempoAsignacion() {
        return tiempoAsignacion;
    }

    public void setTiempoAsignacion(long tiempoAsignacion) {
        this.tiempoAsignacion = tiempoAsignacion;
    }
    public long getTiempoPeticion() {
        return tiempoPeticion;
    }

    public void setTiempoPeticion(long tiempoAsignacion) {
        this.tiempoPeticion = tiempoAsignacion;
    }

    public int getNrovictimastotalasignadas() {
        return nrovictimastotalasignadas;
    }

    public void setNrovictimastotalasignadas(int nrovictimastotalasignadas) {
        this.nrovictimastotalasignadas = nrovictimastotalasignadas;
    }

    public int getNrovictimasenentorno() {
        return nrovictimasenentorno;
    }

    public void setNrovictimasenentorno(int nrovictimasenentorno) {
        this.nrovictimasenentorno = nrovictimasenentorno;
    }

    public InfoAsignacionVictima(int numVictimasEntorno, int numRobotsSimulacion, int intervSecuencia) {
        numeroVictimasEntorno = numVictimasEntorno;
        numeroRobotsSimulacion = numRobotsSimulacion;
        intervaloSecuencia = intervSecuencia;
    }
/*
    public void setInfoCtxAsignacion(InfoContextoAsignacionVictima infoContxVict) {
        infoCtxtAsingacionVictima = infoContxVict;
    }
    public InfoContextoAsignacionVictima getInfoCtxAsignacion() {
       return infoCtxtAsingacionVictima ;
    }
    * 
    */
}
