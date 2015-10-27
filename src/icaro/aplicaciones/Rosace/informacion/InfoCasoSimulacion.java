/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.informacion;

import java.util.*;

/**
 *
 * @author FGarijo
 */
public class InfoCasoSimulacion {
    private String identCaso; // Se podria poner una variable alfanumerica seguida del tiempo inicial del caso
    private String identEquipo;
    private long tiempoInicialDeLaSimulacion=0;      //Variable para obtener el tiempo al iniciar la simulacion
    private int numeroVictimasEntorno=0;            //Numero de victimas actuales que se han introducido en el entorno    
    private int numeroVictimasAsignadas=0;          //Numero de victimas asignadas a robots
    private int numeroVictimasDiferentesSimulacion; //Numero de victimas diferentes que van a intervenir en el proceso de simulacion
    private int numeroRobotsSimulacion = 0;         //Numero de robots diferentes que van a intervenir en el proceso de simulacion	
    private int intervaloSecuencia; //Intervalo uniforme de envio de la secuencia de victimas
    private InfoSerieResultadosSimulacion ctxAsignacionVictimas;
    private InfoAsignacionVictima infoAsignacionVictima;
    private Map<String, Victim> victims2Rescue = new HashMap <String, Victim>(); 
    private Map<String, InfoAsignacionVictima> infoVictims2Rescue; //Victimas que hay en el entorno    
    //private Map<String, String> victimasAsignadas = new HashMap <String, String>();
    private ArrayList<InfoAsignacionVictima> valoresSerieInfoAsignacionVictimas;
    private ArrayList<PuntoEstadistica> valoresSeriePuntosAsignacion;
    private ArrayList<PuntoEstadistica> valoresSeriePuntosLlegada ;
    private ArrayList<PuntoEstadistica> valoresSeriePuntosElapsed ;
    private InfoSerieResultadosSimulacion seriePuntosAsignacion,seriePuntosLlegada,seriePuntosElapsed,serieInfoAsignacionVictimas;
    private InfoEntornoCasoSimulacion infoEntorno;
    private Set <String> victimasAsignadas ;
    private boolean victimaNueva = false;
    

            //El tiempo del elemento i-esimo de elapsed es igual el tiempo del elemento asignacion(i) - tiempo del elemento llegada(i)
    
   public  InfoCasoSimulacion (String casoId){ 
       identCaso= casoId;   
       infoVictims2Rescue = new HashMap <String, InfoAsignacionVictima>();
      
       
   //    InfoContextoInicial 
   //    numeroVictimasEntorno = numVictimasEntorno;
   //    numeroRobotsSimulacion = numRobotsSimulacion;
   //    intervaloSecuencia = intervSecuencia;
   }
   public void setInfoCasoSimulacion(String equipoId, int numeroRobots, int numeroVictimas, int intervSecuencia){
       infoEntorno = new InfoEntornoCasoSimulacion(equipoId,numeroRobots,numeroVictimas,intervSecuencia);
       crearSeriesAconstruir ();
       identEquipo = equipoId;
       numeroRobotsSimulacion = numeroRobots;
       numeroVictimasDiferentesSimulacion = numeroVictimas;
       intervaloSecuencia = intervSecuencia;
   }
   public void setInfoCasoSimulacion(InfoEntornoCasoSimulacion infEntorno){
       infoEntorno = infEntorno;
       crearSeriesAconstruir ();
       identEquipo = infoEntorno.getEquipoId();
       numeroRobotsSimulacion = infoEntorno.getnumeroRobotsSimulacion();
       numeroVictimasDiferentesSimulacion = infoEntorno.getNrovictimasenentorno();
       intervaloSecuencia = infoEntorno.getintervSecuencia();
   }
    private  void crearSeriesAconstruir (){
       valoresSerieInfoAsignacionVictimas = new ArrayList();
       valoresSeriePuntosAsignacion = new ArrayList();
       valoresSeriePuntosLlegada = new ArrayList();
       valoresSeriePuntosElapsed = new ArrayList();
       victimasAsignadas =  new HashSet<String>();
       seriePuntosAsignacion = new InfoSerieResultadosSimulacion (VocabularioRosace.NombreFicheroSerieLlegadaYasignacion,infoEntorno);
       seriePuntosLlegada = new InfoSerieResultadosSimulacion (VocabularioRosace.NombreFicheroSeriePeticionVictimas,infoEntorno);
       seriePuntosElapsed = new InfoSerieResultadosSimulacion (VocabularioRosace.NombreFicheroSerieAsignacion,infoEntorno);
       serieInfoAsignacionVictimas= new InfoSerieResultadosSimulacion (VocabularioRosace.NombreFicheroSerieInfoAsignacionVictimas,infoEntorno);
    }

   public void setTiempoInicioEnvioPeticiones(long tiempoInicio){
       tiempoInicialDeLaSimulacion=tiempoInicio;
   }
   public void addEnvioVictima(InfoAsignacionVictima infoAsign){
      if( infoVictims2Rescue.put(infoAsign.getVictima().getName(), infoAsign)!= null) numeroVictimasEntorno ++ ;
   
   }
    public InfoAsignacionVictima getInfoAsignacionVictima(String idVictim){
      return  infoVictims2Rescue.get(idVictim);
   }
   public void addAsignacionVictima(InfoAsignacionVictima infoAsign){
       valoresSerieInfoAsignacionVictimas.add(infoAsign);
       infoAsignacionVictima = infoAsign;
       victimaNueva = victimasAsignadas.add(infoAsign.getVictima().getName());
       if (victimaNueva)numeroVictimasAsignadas++;
       addInfoSerieEnvioPeticiones();
       addInfoSerieAsignacionVictimas();
       addInfoSerieIntervaloAsignacionVictimas();       
   }
   private  void addInfoSerieEnvioPeticiones(){
       PuntoEstadistica puntoEnvioPeticion = new PuntoEstadistica();
               puntoEnvioPeticion.setX(numeroVictimasAsignadas);
               puntoEnvioPeticion.setY((infoAsignacionVictima.getTiempoPeticion()));             
       valoresSeriePuntosLlegada.add(puntoEnvioPeticion);
   }
   
   public void addInfoSerieAsignacionVictimas(){
               PuntoEstadistica puntoAsignacion = new PuntoEstadistica();
               puntoAsignacion.setX(numeroVictimasAsignadas);
               puntoAsignacion.setY(infoAsignacionVictima.getTiempoAsignacion());             
               valoresSeriePuntosAsignacion.add(puntoAsignacion);
   }
   public void addInfoSerieIntervaloAsignacionVictimas(){ // elapsed
       if ( victimaNueva){
       PuntoEstadistica puntoAsignacion = new PuntoEstadistica();
          double intervaloAsignacion = infoAsignacionVictima.getTiempoPeticion();
               puntoAsignacion.setX(numeroVictimasAsignadas);
               puntoAsignacion.setY((infoAsignacionVictima.getTiempoAsignacion()-infoAsignacionVictima.getTiempoPeticion()));             
       valoresSeriePuntosElapsed.add(puntoAsignacion);
       }
   }
   public int getnumeroVictimasEntorno(){
       return numeroVictimasEntorno;
   }
   public InfoEntornoCasoSimulacion getInfoEntornoCasoSimulacion(){
       return infoEntorno;
   }
   public int getnumeroVictimasAsignadas(){
       return numeroVictimasAsignadas;
   }
   public InfoSerieResultadosSimulacion getSerieAsignacion(){
       seriePuntosAsignacion.setserieResultadosSimulacion(valoresSeriePuntosAsignacion);
       seriePuntosAsignacion.setnrovictimastotalasignadas(numeroVictimasAsignadas);
       return seriePuntosAsignacion;
   }
   public InfoSerieResultadosSimulacion getSerieLlegadaPeticiones(){
       seriePuntosLlegada.setserieResultadosSimulacion(valoresSeriePuntosLlegada);
       seriePuntosLlegada.setnrovictimastotalasignadas(numeroVictimasAsignadas);
       return seriePuntosLlegada;
   }
   public InfoSerieResultadosSimulacion getserieInfoAsignacionVictimas (){
        serieInfoAsignacionVictimas.setserieResultadosSimulacion(valoresSerieInfoAsignacionVictimas);
        serieInfoAsignacionVictimas.setnrovictimastotalasignadas(numeroVictimasAsignadas);
       return serieInfoAsignacionVictimas;
   }
   public InfoSerieResultadosSimulacion getSerieElapsed (){
        seriePuntosElapsed.setserieResultadosSimulacion(valoresSeriePuntosElapsed);
        seriePuntosElapsed.setnrovictimastotalasignadas(numeroVictimasAsignadas);
       return seriePuntosElapsed;
   }
   
   public boolean todasLasVictimasAsgnadas(){ 
    return numeroVictimasAsignadas == numeroVictimasDiferentesSimulacion ;
   }
   
public boolean construirSeriesFromResultadosSimulacion(ArrayList<InfoAsignacionVictima> serieResultadosCasoSimulacion){
    return false;
}
}