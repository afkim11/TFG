
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.InfoRolAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.informacion.InfoCambioRolAgente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author Francisco J Garijo
 */
public class MandarMiNuevoRolAlEquipo  extends TareaSincrona {
    private String miAgentId;
    private String identDeEstaTarea ;   
	@Override
	public void ejecutar(Object... params) {
           InfoCambioRolAgente miInfoCambioRol = (InfoCambioRolAgente)params[0];
 //          PropuestaAgente propuestaRecibida = (PropuestaAgente)params[1];
           miAgentId = this.getIdentAgente();
           identDeEstaTarea = this.getIdentTarea();
           try {
                 trazas.aceptaNuevaTraza(new InfoTraza(miAgentId, "Se Ejecuta la Tarea :"+ identDeEstaTarea , InfoTraza.NivelTraza.debug));    
                  InfoRolAgente miNuevoRol = new InfoRolAgente(miAgentId,miInfoCambioRol.getmiEquipo().getTeamId(),
                          miInfoCambioRol.getidentRolActualAgte(),VocabularioRosace.IdentIteracionProcesoInformesCR);
                    miInfoCambioRol.setmiNuevoRolEnviadoAlEquipo(true);
                   this.getItfConfigMotorDeReglas().setDepuracionActivationRulesDebugging(true);
                   this.getItfConfigMotorDeReglas().setfactHandlesMonitoring_afterActivationFired_DEBUGGING(true);
                   this.generarInformeTemporizadoFromConfigProperty(VocabularioRosace.IdentTareaTimeOutRecibirRespuestasEquipo, null, miAgentId, null);
                   this.getEnvioHechos().actualizarHecho(miInfoCambioRol);
                   this.getComunicator().informaraGrupoAgentes(miNuevoRol,miInfoCambioRol.getmiEquipo().getTeamMemberIDsWithMyRol() );
		   } catch (Exception e) {
			     e.printStackTrace();
           }            
    }

}
