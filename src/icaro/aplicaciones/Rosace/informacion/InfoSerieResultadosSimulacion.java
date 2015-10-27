/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.informacion;

import java.util.ArrayList;

/**
 *
 * @author FGarijo
 */
public class InfoSerieResultadosSimulacion {
      private String tituloSerie;
 //     private long tiempoInicioSimulacion = 0;      //Variable para obtener el tiempo al iniciar la simulacion
 //   private int nroVictimasenEntorno = 0;            //Numero de victimas actuales que se han introducido en el entorno    
//    private int nrovictimastotalasignadas = 0;          //Numero de victimas asignadas a robots
 //   private int numeroVictimasDiferentesSimulacion; //Numero de victimas diferentes que van a intervenir en el proceso de simulacion
 //   private int numeroRobotsSimulacion = 0;         //Numero de robots diferentes que van a intervenir en el proceso de simulacion	
 //   private int intervaloSecuencia;                 //Intervalo uniforme de envio de la secuencia de victimas
//    private Map<String, Victim> victims2Rescue = new HashMap<String, Victim>();      //Victimas que hay en el entorno    
//    private Map<String, String> victimasAsignadas = new HashMap<String, String>();
 
//    private String equipoId;                       // victima a la que se refiere el contexto 
//    private long tiempoInicioSimulacion;
    private InfoEntornoCasoSimulacion infoEntornoSimulacion;
    private long tiempoInicioSimulacion = 0;
    private int nrovictimastotalasignadas = 0;
    private ArrayList serieResultados;

    public InfoSerieResultadosSimulacion(String titSerie,InfoEntornoCasoSimulacion infEntorno) {
       
        infoEntornoSimulacion = infEntorno;
        tituloSerie= titSerie+infoEntornoSimulacion.getTiempoInicioSimulacion();
    }

    public InfoSerieResultadosSimulacion(String titSerie,String idEquipo, int numRobotsSimulacion, int numVictimasEntorno, int intervSecuencia) {
        tituloSerie = titSerie;
        infoEntornoSimulacion = new InfoEntornoCasoSimulacion(idEquipo,numRobotsSimulacion,numVictimasEntorno,intervSecuencia);
     //   serieResultados = new ArrayList();
    }

    public String getTituloSerie() {
        return tituloSerie;
    }

    public void setTituloSerie(String titulo) {
        this.tituloSerie = titulo;
    }

    public long getTiempoInicioSimulacion (){
        return tiempoInicioSimulacion;
    }
     public void setTiempoInicioSimulacion (long tiempoInicio){
        tiempoInicioSimulacion = tiempoInicio;
    }

    public int getnrovictimastotalasignadas() {
        return nrovictimastotalasignadas;
    }

    public void setnrovictimastotalasignadas(int nrovictimasenentorno) {
        this.nrovictimastotalasignadas = nrovictimasenentorno;
    }
    public void addVictimaAsignada(){
        nrovictimastotalasignadas++;
    }
    
    public void setserieResultadosSimulacion(ArrayList serieResulSimul) {
        this.serieResultados = serieResulSimul;
    }

    public ArrayList getserieResultadosSimulacion(){
        return serieResultados;
    }

}
