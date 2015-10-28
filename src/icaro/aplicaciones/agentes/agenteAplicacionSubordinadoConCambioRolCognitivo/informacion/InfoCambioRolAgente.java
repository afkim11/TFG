/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.informacion;

import icaro.aplicaciones.Rosace.informacion.*;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.ArrayList;

/**
 *
 * @author FGarijo
 */
public class InfoCambioRolAgente extends InfoPropuestaEquipo{
    private String agtePropietarioId ;
    private String refIteracion ;
    private InfoEquipo miEquipo ;
    private String identRolActualAgte ;
    private String identRolAnteriorAgte ;
 //   private InfoPropuestaEquipo infoMiPropuestaAlEquipo;
    private boolean equipoAceptaPropuestaCR = false;
    private boolean miRolhaCambiado = false;
    private boolean miPropuestaCREnviada = false;
    private boolean miPropuestaCRreEnviada = false;
    private boolean faltanRespuestasApropuestaCRreEnviada = false;
    private boolean seVerificanLasCondicionesParaCambiarMirolAigualitario = false;
    private boolean inicicioProcesamientoInformesCR = false;
    private boolean miNuevoRolEnviadoAlEquipo = false;
    private boolean seVerificanCondicionesCambioRolEquipo = false;
    private int numAgtesHanInformadoCambioRol=0;
    private int numeroMiembrosEquipoConMiRolInicial = 0;
    private EstatusRespuestasEquipo respuestasCambioRol;
    public InfoCambioRolAgente (String agentId, InfoEquipo equipo, String iteracionId ){
        super(agentId,equipo.getTeamMemberIDsWithMyRol(),iteracionId);
        refIteracion =iteracionId ;
        agtePropietarioId = agentId;
        miEquipo =equipo;
        identRolActualAgte = equipo.getidentMiRolEnEsteEquipo();
        numeroMiembrosEquipoConMiRolInicial = equipo.getTeamMemberIDsWithMyRol().size();
        respuestasCambioRol = new EstatusRespuestasEquipo(agtePropietarioId,equipo.getTeamMemberIDsWithMyRol(),refIteracion);
        
    }   
    //Devuelve el identificador de rol actual del agente
    public String getidentRolActualAgte(){
         return identRolActualAgte ;
    }
    
    //Devuelve la informacion del equipo actual
     public InfoEquipo getmiEquipo(){
         return miEquipo ;
    }
     
    //Actualiza la informacion del agente habiendo cambiado su identificador de rol
    public void setidentRolActualAgte(String agentRolId){
        if(!identRolActualAgte.equals(agentRolId)){
            identRolAnteriorAgte = identRolActualAgte;
            identRolActualAgte = agentRolId;
            miRolhaCambiado = true;
        // Actualizo mi rol 
        String miAgentId = this.miEquipo.getidentAgentePropietario();
        RobotStatus miStatus = this.miEquipo.getTeamMemberStatus(miAgentId);
        String idRolAnterior = miStatus.getIdRobotRol();
            if (identRolAnteriorAgte.equals(miStatus.getIdRobotRol())){
                miStatus.setIdRobotRol(identRolActualAgte);
            }else {
                NombresPredefinidos.RECURSO_TRAZAS_OBJ.trazar(this.agtePropietarioId,"Inconsistencia entre los roles definidos y los almacenados antes del Cambio de Rol", InfoTraza.NivelTraza.error);
            	}
        }
    }
    
    //Devuelve el valor de identificador de agente que tenia antes
     public String getidentRolAnteriorAgte(){
         return identRolAnteriorAgte ;
    }
     
    //Actualiza el valor del identificador de rol anterior
    public void setidentRolAnteriorAgte(String agentRolId){
        identRolAnteriorAgte =agentRolId;
    }
    
    //Actualiza el valor booleano de se ha enviado una propuesta de cambio de rol por el agente (No haria falta el argumento, ¿no?)
    public void setPropuestaCREnviada(PropuestaAgente propuesta){
        miPropuestaCREnviada = true;
    }
    
    //Deulve el valor booleano de si el equipo acepta mi propuesta de cambio de rol
    public synchronized boolean getequipoAceptaPropuestaCR(){
         return equipoAceptaPropuestaCR ;
    }
    
    //Actualiza el valor booleano de si el equipo acepta mi cambio de rol
    public synchronized void setequipoAceptaPropuestaCR(boolean estatusAceptacion){
        equipoAceptaPropuestaCR =estatusAceptacion;
    }
    
    //¿Devuelve el valor booleano de si he enviado mi nuevo rol al equipo?
     public synchronized boolean getmiNuevoRolEnviadoAlEquipo(){
         return miNuevoRolEnviadoAlEquipo ;
    }

     //¿Actualiza el valor booleano de si he enviado mi nuevo rol al equipo?
     public synchronized void setmiNuevoRolEnviadoAlEquipo(boolean bvalor){
        miNuevoRolEnviadoAlEquipo =bvalor;
    }
     
     //
     public synchronized boolean getincicioProcesamientoInformesCR(){
         return inicicioProcesamientoInformesCR ;
    }
     
     public void setseVerificanLasCondicionesParaCambiarMirolAigualitario(){
        seVerificanLasCondicionesParaCambiarMirolAigualitario =true;
    }
     
     public boolean getseVerificanLasCondicionesParaCambiarMirolAigualitario(){
         return seVerificanLasCondicionesParaCambiarMirolAigualitario ;
    } 
    //¿¿¿???
    public void setincicioProcesamientoInformesCR(boolean inicioProcInformesCR){
        if(!inicicioProcesamientoInformesCR && inicioProcInformesCR){
            refIteracion = VocabularioRosace.IdentIteracionProcesoInformesCR ;//"procesamientoInformesCR";     
            respuestasCambioRol = new EstatusRespuestasEquipo (agtePropietarioId,miEquipo.getTeamMemberIDsWithMyRol(),refIteracion);
        }
         inicicioProcesamientoInformesCR =inicioProcInformesCR;
    }
    
    //¿Se actualiza si el equipo acepta mi propuesta?
    public void procesarPropuestaAceptada ( PropuestaAgente aceptPr){
        this.addAgteAceptaPropuesta(aceptPr); // Se actualiza el valor booleano
        equipoAceptaPropuestaCR = this.getPropuestaAceptada();
    }
    
    //¿Se actualiza si el equipo rechazo mi propuesta?
    public void procesarRechazoPropuesta ( PropuestaAgente rechazoPr){
        this.procesarRechazarPropuesta(rechazoPr);
        equipoAceptaPropuestaCR = this.getPropuestaAceptada();
    }
    
    //Devuelve el valor booleano que indica que mi rol ha cambiado alguna vez
    public synchronized boolean getmiRolhaCambiado(){
   //     if(!identRolActualAgte.isEmpty()&&identRolActualAgte.equals(identRolNuevoAgte)) 
        return miRolhaCambiado ;
    }
    
    /* Funcion que obtiene el nombre del agente que ha creado esta tarea de cambio de rol, comprueba que se puede añadir a las respuestas a cambio de rol de este agente con
      mi referente de iteracion y se obtiene el estado del agente a cambiar rol; se obtiene el identificador de rol del agente y si es diferente al identificador de rol de
      el estado del robot que se consiguio antes se actualiza. (¿Imagino que la última comprobación es para no actualizar si es el mismo?) */
    public void procesarInfoCambioRolAgte(InfoRolAgente infoRol){
        String identAgteInformante = infoRol.getAgteIniciadorId();
        if (respuestasCambioRol.addRespuestaAgente(identAgteInformante, refIteracion)){
        RobotStatus miStatusRobotInformante = this.miEquipo.getTeamMemberStatus(identAgteInformante);
        String idRolAgteInformante = infoRol.getidentRolAgte();
        if (!idRolAgteInformante.equals(miStatusRobotInformante.getIdRobotRol())){
            miStatusRobotInformante.setIdRobotRol(idRolAgteInformante);
        }
        }
    }
    public synchronized void setmiPropuestaCREnviada(){
        miPropuestaCREnviada = true;
    }
    public synchronized boolean getmiPropuestaCREnviada(){
         return miPropuestaCREnviada ;
    }
    public synchronized void setmiPropuestaCRreEnviada(){
        miPropuestaCRreEnviada = true;
    }
    public synchronized boolean getmiPropuestaCRreEnviada(){
         return miPropuestaCRreEnviada ;
    }
    
    //¿Funcion que actualiza valores cuando el equipo va a cambiar de rol? 
    public void verificarCondicionesCambioRolEquipo(){
  // han llegado todas las respuestas esperadas y todos los que tenian el rol antiguo han cambiado al nuevo 
        if(respuestasCambioRol.hanLlegadoTodasLasRespuestasEsperadas()&&
                ! identRolAnteriorAgte.equals(identRolActualAgte) &&
                miEquipo.getTeamMemberIDsWithMyRol().size()== numeroMiembrosEquipoConMiRolInicial){
                seVerificanCondicionesCambioRolEquipo = true ;
                equipoAceptaPropuestaCR = true ;
        }
    }
    
    //Funcion que actualiza valores cuando el equipo acepta la prupuesta de que yo cambie de rol
    public void verificarCondicionesCambiarMiRolenEquipo(){
  // han llegado todas las respuestas esperadas y todos los que tenian el rol antiguo han cambiado al nuevo 
        if( this.getequipoAceptaPropuestaCR()){
                seVerificanCondicionesCambioRolEquipo = true ;
               
        }
    }
}
