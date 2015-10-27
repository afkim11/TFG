/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;
import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.Rosace.informacion.PeticionAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.informacion.ExptComunicacionConJefe;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.ArrayList;

/**
 *
 * @author Francisco J Garijo
 */
   public class PeticionEstatusComunicacionConJefe  extends TareaSincrona {
   private String nombreAgenteEmisor;
   private InfoEquipo equipoInfo;
	@Override
	public void ejecutar(Object... params) {
	try {
        //      Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];    
              equipoInfo = (InfoEquipo)params[0]; 
              ExptComunicacionConJefe miExpComJefe = (ExptComunicacionConJefe)params[1]; 
              nombreAgenteEmisor = this.getAgente().getIdentAgente();
              String identTarea = this.getIdentTarea();
              PeticionAgente peticionEstatus = new PeticionAgente(nombreAgenteEmisor);
              peticionEstatus.setMensajePeticion(VocabularioRosace.MsgPeticionEnvioEstatusComunicacionConJefe);
              peticionEstatus.setJustificacion(VocabularioRosace.MsgJustificPetionEstatusComunConJefe);
            ArrayList<String>  agentesEquipo = equipoInfo.getTeamMemberIDsWithThisRol(equipoInfo.getTeamMemberRol(nombreAgenteEmisor));
              trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Ejecuta la Tarea :"+ identTarea , InfoTraza.NivelTraza.debug));
                        //            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Enviamos la evaluacion " + miEvaluacion, InfoTraza.NivelTraza.masterIA));
              trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Enviamos la peticion " + peticionEstatus, InfoTraza.NivelTraza.info));            
           //   infoDecision.setMi_eval(miEvaluacion.getEvaluacion()); Ya tengo mi evaluacion por tanto no la redefino
                        //         this.mandaMensajeATodos(miEvaluacion);
                        //            ComunicacionAgentes comunicacion = new ComunicacionAgentes(nombreAgenteEmisor);
                        //            comunicacion.informaraGrupoAgentes(miEvaluacion, agentesEquipo);
              miExpComJefe.setmiComunicacionConJefe(false);
              this.getItfMotorDeReglas().updateFact(miExpComJefe);
              this.getComunicator().informaraGrupoAgentes(peticionEstatus, agentesEquipo);
       //       this.generarInformeOK(identTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, VocabularioRosace.ResEjTaskMiEvalucionEnviadaAlEquipo);
              trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Numero de agentes de los que espero respuesta:" + agentesEquipo.size(), InfoTraza.NivelTraza.info));
              this.generarInformeTemporizadoFromConfigProperty(VocabularioRosace.IdentTareaTimeOutRecibirEstatusComunicacionConJefe,  null, 
                      nombreAgenteEmisor, null);
		} catch (Exception e) {
			e.printStackTrace();
        }
    }
   
}