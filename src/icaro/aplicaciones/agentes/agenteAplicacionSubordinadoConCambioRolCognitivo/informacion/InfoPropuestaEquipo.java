/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.informacion;

import icaro.aplicaciones.Rosace.informacion.AceptacionPropuesta;
import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.RechazarPropuesta;
import java.util.ArrayList;

/**
 *
 * @author FGarijo
 */
public class InfoPropuestaEquipo extends InfoInteraccionBasica{
    private boolean propuestaAceptada = false;
    private boolean propuestaAceptadaPorUnanimidad= false;
    private boolean alguienDiscrepa = false;
    private boolean hayConsenso = false;
    private Boolean [] agtsEquipoAceptanPropuesta;
    private Boolean [] agtsEquipoRechazanPropuesta;
    private ArrayList<String> equipoIds;
    private int numAgtesAceptanPropuesta = 0;
    private int numAgtesRechazanPropuesta = 0;
    private int numCompasEnEquipo ;
    private int numMiembrosEquipoParaConsenso= 1;
    private String myId ;
    private String refIteracion ;
    private EstatusRespuestasEquipo respuestasEquipo;
    public InfoPropuestaEquipo (String identAgtePropuesta, ArrayList<String> idsEquipo, String refIteracionPropuesta ){
        myId = identAgtePropuesta;
        this.equipoIds = idsEquipo ;
        numCompasEnEquipo = equipoIds.size();
        refIteracion = refIteracionPropuesta;
        respuestasEquipo = new EstatusRespuestasEquipo(myId,equipoIds,refIteracion);
        agtsEquipoAceptanPropuesta = new Boolean [numCompasEnEquipo];
        agtsEquipoRechazanPropuesta= new Boolean [numCompasEnEquipo];
        numMiembrosEquipoParaConsenso = (idsEquipo.size()*2/3); // tomamos dos tercios por defecto
    }
    public void addAgteAceptaPropuesta ( PropuestaAgente aceptPr){
       if ( respuestasEquipo.addRespuestaAgente(aceptPr.identAgente, aceptPr.getMensajePropuesta())){
            agtsEquipoAceptanPropuesta[equipoIds.indexOf(aceptPr.identAgente)]= true;
            numAgtesAceptanPropuesta++;
        // Se actualiza el estado de la propuesta    
          actualizarInfoPropuesta () ;
       }       
    }
    public void actualizarInfoPropuesta (){
        if(this.hanLlegadoTodasLasResPuestasDeMiembrosDelEquipo())
            if (equipoIds.size()== numAgtesAceptanPropuesta){
                propuestaAceptadaPorUnanimidad = true;
                propuestaAceptada = true;
            }else if (!propuestaAceptada&&getHayConsenso(numMiembrosEquipoParaConsenso))propuestaAceptada = true;
    }
    
    public void procesarRechazarPropuesta ( PropuestaAgente rechazoPr){
        if ( respuestasEquipo.addRespuestaAgente(myId, rechazoPr.getMensajePropuesta())){
            agtsEquipoRechazanPropuesta[equipoIds.indexOf(rechazoPr.identAgente)]= true;
             numAgtesRechazanPropuesta++;
             alguienDiscrepa=true;
             actualizarInfoPropuesta ();
       }     
    }
    public boolean getpropuestaAceptadaPorUnanimidad(){
        return propuestaAceptadaPorUnanimidad;
    }
   
    public void setPropuestaAceptada(){
        propuestaAceptada = true;
    }
    public boolean getPropuestaAceptada(){
         return propuestaAceptada ;
    }  
    public boolean hanLlegadoTodasLasResPuestasDeMiembrosDelEquipo(){
         return respuestasEquipo.hanLlegadoTodasLasRespuestasEsperadas() ;
    }
    public ArrayList<String> getIdentsAgtesFaltanResponder(){
        return respuestasEquipo.getAgtesFaltanRespuestas(refIteracion);
    }
    public boolean getHayConsenso (int numMiembrosParaAcuerdo){
        // Puede usarse cuando no han llegado todas las respuestas pero una mayoria ha aceptado una propuesta y no hay rechazos
        return hayConsenso=(!alguienDiscrepa && (numAgtesAceptanPropuesta>= numMiembrosParaAcuerdo) );
    }
}
