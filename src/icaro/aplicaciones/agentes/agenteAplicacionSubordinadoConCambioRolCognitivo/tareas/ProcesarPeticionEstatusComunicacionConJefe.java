/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;

import icaro.aplicaciones.agentes.agenteAplicacionSubordinadoCognitivo.tareas.*;
import icaro.aplicaciones.Rosace.informacion.*;
import icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.informacion.ExptComunicacionConJefe;
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


//NOTA JM: esta tarea se llama en la regla "Procesar propuestas de otro agente para ir yo" 

public class ProcesarPeticionEstatusComunicacionConJefe extends TareaSincrona {

    private String nombreAgenteEmisor;
    private InfoEstatusComunicacionConOtroAgente comunicStatusRecibido;
    private String identDeEstaTarea ;

	@Override
	public void ejecutar(Object... params) {
            try {
        //          InfoEquipo miEquipo = (InfoEquipo)params[0];
                  ExptComunicacionConJefe expectativaParaValidar = (ExptComunicacionConJefe) params[0];
                  PeticionAgente peticionRecibida = (PeticionAgente)params[1];
                  nombreAgenteEmisor = this.getAgente().getIdentAgente();
                  identDeEstaTarea = this.getIdentTarea();
                  if(peticionRecibida.mensajePeticion.equals(VocabularioRosace.MsgPeticionEnvioEstatusComunicacionConJefe) &&
                     peticionRecibida.justificacion.equals(VocabularioRosace.MsgJustificPetionEstatusComunConJefe) ){
                 // si nos pide el estatus con esa justificacion asumimos que no tiene comunicacion con el jefe
                 // se obtiene el nombre del  agente que tiene el rol de jefe
                     String identAgtePeticionario = peticionRecibida.identAgente;
                     comunicStatusRecibido = new InfoEstatusComunicacionConOtroAgente(identAgtePeticionario,expectativaParaValidar.getidentAgteRolJefe());
                     comunicStatusRecibido.sethayComunicAgteInformanteConOtroAgte(Boolean.FALSE);
                  }
                  expectativaParaValidar.validarExpectativa(comunicStatusRecibido);
                  this.getEnvioHechos().actualizarHecho(expectativaParaValidar);
                  trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se actualiza la expectativa:  "+ expectativaParaValidar +
                          "Por procesamiento del InfoEstatusComunicacionConOtroAgente : " +comunicStatusRecibido , InfoTraza.NivelTraza.debug));
               }         
            catch(Exception e) {
                  e.printStackTrace();
            }
    }	
}
