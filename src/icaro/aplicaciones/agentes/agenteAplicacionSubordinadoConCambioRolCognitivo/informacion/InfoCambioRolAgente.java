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
    public String getidentRolActualAgte(){
         return identRolActualAgte ;
    }
     public InfoEquipo getmiEquipo(){
         return miEquipo ;
    }
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
     public String getidentRolAnteriorAgte(){
         return identRolAnteriorAgte ;
    }
    public void setidentRolAnteriorAgte(String agentRolId){
        identRolAnteriorAgte =agentRolId;
    }
    public void setPropuestaCREnviada(PropuestaAgente propuesta){
        miPropuestaCREnviada = true;
    }
    public synchronized boolean getequipoAceptaPropuestaCR(){
         return equipoAceptaPropuestaCR ;
    }
    
    public synchronized void setequipoAceptaPropuestaCR(boolean estatusAceptacion){
        equipoAceptaPropuestaCR =estatusAceptacion;
    }
     public synchronized boolean getmiNuevoRolEnviadoAlEquipo(){
         return miNuevoRolEnviadoAlEquipo ;
    }
     public synchronized void setmiNuevoRolEnviadoAlEquipo(boolean bvalor){
        miNuevoRolEnviadoAlEquipo =bvalor;
    }
     public synchronized boolean getincicioProcesamientoInformesCR(){
         return inicicioProcesamientoInformesCR ;
    }
     
     public void setseVerificanLasCondicionesParaCambiarMirolAigualitario(){
        seVerificanLasCondicionesParaCambiarMirolAigualitario =true;
    }
     public boolean getseVerificanLasCondicionesParaCambiarMirolAigualitario(){
         return seVerificanLasCondicionesParaCambiarMirolAigualitario ;
    } 
    public void setincicioProcesamientoInformesCR(boolean inicioProcInformesCR){
        if(!inicicioProcesamientoInformesCR && inicioProcInformesCR){
            refIteracion = VocabularioRosace.IdentIteracionProcesoInformesCR ;//"procesamientoInformesCR";     
            respuestasCambioRol = new EstatusRespuestasEquipo (agtePropietarioId,miEquipo.getTeamMemberIDsWithMyRol(),refIteracion);
        }
         inicicioProcesamientoInformesCR =inicioProcInformesCR;
    }
    public void procesarPropuestaAceptada ( PropuestaAgente aceptPr){
        this.addAgteAceptaPropuesta(aceptPr); // Se actualiza el valor booleano
        equipoAceptaPropuestaCR = this.getPropuestaAceptada();
       
    }
    public void procesarRechazoPropuesta ( PropuestaAgente rechazoPr){
        this.procesarRechazarPropuesta(rechazoPr);
        equipoAceptaPropuestaCR = this.getPropuestaAceptada();
    }
    public synchronized boolean getmiRolhaCambiado(){
   //     if(!identRolActualAgte.isEmpty()&&identRolActualAgte.equals(identRolNuevoAgte)) 
        return miRolhaCambiado ;
    }
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
    
    public void verificarCondicionesCambioRolEquipo(){
  // han llegado todas las respuestas esperadas y todos los que tenian el rol antiguo han cambiado al nuevo 
        if(respuestasCambioRol.hanLlegadoTodasLasRespuestasEsperadas()&&
                ! identRolAnteriorAgte.equals(identRolActualAgte) &&
                miEquipo.getTeamMemberIDsWithMyRol().size()== numeroMiembrosEquipoConMiRolInicial){
                seVerificanCondicionesCambioRolEquipo = true ;
                equipoAceptaPropuestaCR = true ;
        }
    }
    public void verificarCondicionesCambiarMiRolenEquipo(){
  // han llegado todas las respuestas esperadas y todos los que tenian el rol antiguo han cambiado al nuevo 
        if( this.getequipoAceptaPropuestaCR()){
                seVerificanCondicionesCambioRolEquipo = true ;
               
        }
    }
}
