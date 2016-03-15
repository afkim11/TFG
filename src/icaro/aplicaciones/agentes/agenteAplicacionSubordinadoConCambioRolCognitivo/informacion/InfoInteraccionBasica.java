/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.informacion;

/**
 *
 * @author FGarijo
 * Esta clase debe servir para envio de items de informacion - representados mediante identificadores - de un tema
 * o asunto en que un agente quiera informar a otro agente.
 * Tambien puede utilizarse  como  base para crear otros items mas complicados como propuestas o peticiones, mediante herencia
 */
public class InfoInteraccionBasica {
    private String agteIniciadorId ;
    private String refIteracion ;
    private String tema ;
    public InfoInteraccionBasica ( ){       
    }
    public InfoInteraccionBasica (String agentId, String temaId, String iteracionId ){
        agteIniciadorId =agentId;
        refIteracion = iteracionId;
        tema =temaId;
    }
    
    //Devuelve el valor del identificador del agente iniciador
     public String getAgteIniciadorId(){
         return agteIniciadorId ;
    }
     
    //Actualiza el valor del identificador del agente iniciador
    public void setAgteIniciadorId(String agentId){
        agteIniciadorId =agentId;
    }
    
    //Devuelve el tema?
    public String getTema(){
         return tema ;
    }
    
    //Actualiza el tema?
    public void setTema(String identTema){
        tema =identTema;
    }
    
    //Devuelve el referente de iteracion
    public String getrefIteracion(){
         return refIteracion ;
    }
    
    //Actualiza el referente de iteracion
    public void setrefIteracion(String iterId){
        refIteracion =iterId;
    }
}
