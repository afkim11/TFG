/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.informacion;

import java.util.ArrayList;

/**
 *
 * @author FGarijo
 */
public class EstatusRespuestasEquipo {
    private ArrayList<String> agtesEquipoIds;
    private ArrayList<Boolean> respuestasAgtsEquipo;
    private String idAgtePropietario ;
    private String identIteracion ;
    private int respuestasEsperadas ;
    private int respuestasRecibidas = 0;
    private boolean hanLlegadoTodasLasRespuestasEsperadas = false;
    public EstatusRespuestasEquipo (String agentId, ArrayList<String> equipoIds,String iteracionId ){
   //     super.setestatusSatisfaccion (false);
        identIteracion = iteracionId;
        idAgtePropietario =agentId;
        agtesEquipoIds = equipoIds;
        respuestasAgtsEquipo = new ArrayList();
        for (int i = 0; i < agtesEquipoIds.size(); i++) {
                respuestasAgtsEquipo.add(i, Boolean.FALSE);
                }
            respuestasEsperadas = agtesEquipoIds.size() ;// 
    }
    public boolean addRespuestaAgente (String identOtroAgte, String iteracionId){
        if ((respuestasEsperadas > respuestasRecibidas) && (identIteracion.equals(iteracionId)) ){
            for (int i = 0; i < agtesEquipoIds.size(); i++) { 
                if ( agtesEquipoIds.get(i).equals(identOtroAgte))
                    if (!respuestasAgtsEquipo.get(i)){
                      respuestasRecibidas ++;
                      respuestasAgtsEquipo.set(i,true);
                      return true;
                  }
            } 
        } return false;
    }
    public ArrayList<String> getAgtesFaltanRespuestas (String iteracionId){ 
        if (agtesEquipoIds.isEmpty() || !(identIteracion.equals(iteracionId)) ) return null;
        ArrayList<String> agtesEquipoSinRespuesta = new ArrayList();
        String idAgteSinRespuesta;
        for (int i = 0; i < agtesEquipoIds.size(); i++) { 
                    if (!respuestasAgtsEquipo.get(i)){
                        idAgteSinRespuesta = agtesEquipoIds.get(i);
                        if (!idAgteSinRespuesta.equals(idAgtePropietario)) agtesEquipoSinRespuesta.add(idAgteSinRespuesta);
                    }   
                  }
        return agtesEquipoSinRespuesta;
    }
    public ArrayList<String> getAgtesHanRespondido (String iteracionId){ 
        if (agtesEquipoIds.isEmpty() || !(identIteracion.equals(iteracionId)) ) return null;
        ArrayList<String> agtesEquipoHanRespondido = new ArrayList();
        String idAgteSinRespuesta;
        for (int i = 0; i < agtesEquipoIds.size(); i++) { 
                    if (!respuestasAgtsEquipo.get(i)){
                        idAgteSinRespuesta = agtesEquipoIds.get(i);
                        if (!idAgteSinRespuesta.equals(idAgtePropietario)) agtesEquipoHanRespondido.add(idAgteSinRespuesta);
                    }   
                  }
        return agtesEquipoHanRespondido;
    }
    public boolean hanLlegadoTodasLasRespuestasEsperadas (){
        return (respuestasRecibidas==respuestasEsperadas);
    }
    public void  setRespuestasEsperadas (int numRespEsperadas){
        respuestasEsperadas = numRespEsperadas;
    }
    public int  getRespuestasRecibidas (int numRespEsperadas){
        return respuestasRecibidas;
    }
}
