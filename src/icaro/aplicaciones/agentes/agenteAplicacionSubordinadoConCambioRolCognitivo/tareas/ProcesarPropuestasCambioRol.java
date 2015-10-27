/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;

import icaro.aplicaciones.agentes.agenteAplicacionSubordinadoCognitivo.tareas.*;
import icaro.aplicaciones.Rosace.informacion.*;
import icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.informacion.ExptComunicacionConJefe;
import icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.informacion.InfoCambioRolAgente;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author Francisco J Garijo
 */

public class ProcesarPropuestasCambioRol extends TareaSincrona {

    private String nombreAgenteEmisor;
    private InfoEstatusComunicacionConOtroAgente comunicStatusRecibido;
    private String identDeEstaTarea ;
	@Override
	public void ejecutar(Object... params) {
            try {
        //          InfoEquipo miEquipo = (InfoEquipo)params[0];
                  InfoCambioRolAgente mInfoCambioRol = (InfoCambioRolAgente) params[0];
                  PropuestaAgente propuestaRecibida = (PropuestaAgente)params[1];
                  nombreAgenteEmisor = this.getAgente().getIdentAgente();
                  identDeEstaTarea = this.getIdentTarea();
                  if(propuestaRecibida.getMensajePropuesta().equals(VocabularioRosace.MsgPropuesta_CambioRolaIgualitario) &&
                     propuestaRecibida.justificacion.equals(VocabularioRosace.MsgJustificPetionEstatusComunConJefe) ){
                 // si nos propone cambiar de rol y el agte le ha enviado una propuesta similar se considerea la propuesta
                 // como una aceptacion a la propuesta enviada
                 // String identAgtePeticionario = propuestaRecibida.identAgente;
                     mInfoCambioRol.procesarPropuestaAceptada(propuestaRecibida);
                  // se modifica el estado de las variables booleanas que se utilizan en las reglas   
                 /*   
                     if (mInfoCambioRol.tengoTodasLasRespuestasApropuestaCRreEnviada())
                         if(mInfoCambioRol.getequipoAceptaPropuestaCR())
                             mInfoCambioRol.setseVerificanLasCondicionesParaCambiarMirolAigualitario();
                         else //
                         * 
                  */
                  }
                  this.getEnvioHechos().eliminarHechoWithoutFireRules(propuestaRecibida);
                  this.getEnvioHechos().actualizarHecho(mInfoCambioRol);
                 
                  trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se actualiza InfoCambioRol:  "+ mInfoCambioRol +
                          "Por procesamiento de una  propuesta recibida : " +propuestaRecibida , InfoTraza.NivelTraza.debug));
               }         
            catch(Exception e) {
                  e.printStackTrace();
            }
    }	
}
