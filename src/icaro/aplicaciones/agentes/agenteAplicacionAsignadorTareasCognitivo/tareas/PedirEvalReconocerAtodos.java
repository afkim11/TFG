/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;
import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.PeticionAgente;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.Rosace.utils.AccesoPropiedadesGlobalesRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.informacion.InfoParaDecidirAQuienAsignarObjetivo;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.ControladorVisualizacionSimulRosace;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Informe;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.ArrayList;

/**
 *
 * @author Francisco J Garijo
 */
public class PedirEvalReconocerAtodos extends TareaSincrona {
	private InterfazUsoAgente agenteReceptor;
	private ArrayList <String> agentesEquipo;//resto de agentes que forman mi equipo                                
	private int mi_eval, mi_eval_nueva;
	private String nombreAgenteEmisor;
	private ItfUsoRecursoTrazas trazas;
	private InfoParaDecidirAQuienAsignarObjetivo infoDecision;

	// private TimeOutRespuestas tiempoSinRecibirRespuesta;  //no usado

	@Override
	public void ejecutar(Object... params) {
		try {
			trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
			Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];    
			infoDecision = (InfoParaDecidirAQuienAsignarObjetivo)params[1];
			
			nombreAgenteEmisor = this.getAgente().getIdentAgente();
			String identTarea = this.getIdentTarea();
			if(!infoDecision.peticionEvaluacionEnviadaAtodos){
				agentesEquipo = infoDecision.getIdentsAgentesEquipo();
				int numAgtesEnEquipo = agentesEquipo.size();
				if(numAgtesEnEquipo ==0) trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor,
						"  En la tarea : " +identTarea + " No se puede enviar la peticion porque el grupo de agentes a los que hay que enviar la informacion esta vacio ", InfoTraza.NivelTraza.error));
				else{
					PeticionAgente peticionEval = new PeticionAgente(nombreAgenteEmisor);
					peticionEval.setidentObjectRefPeticion(objetivoEjecutantedeTarea.getobjectReferenceId());
					peticionEval.setMensajePeticion(VocabularioRosace.MsgPeticionEnvioEvaluaciones);
					Victim victim = new Victim();
					victim.setCoordinateVictim(new Coordinate(1,1,0.5));
					peticionEval.setJustificacion(victim); // para que se sepa qué evaluacion le pedimos
					this.getComunicator().informaraGrupoAgentes(peticionEval, agentesEquipo);
					infoDecision.setRespuestasEsperadas(agentesEquipo.size());
					infoDecision.setPeticionEvaluacionEnviadaAtodos(Boolean.TRUE);
					this.getEnvioHechos().actualizarHecho(infoDecision);
					trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Numero de agentes de los que espero respuesta:" + agentesEquipo.size(), InfoTraza.NivelTraza.debug));

					this.generarInformeTemporizadoFromConfigProperty(VocabularioRosace.IdentTareaTimeOutRecibirEvaluaciones1,objetivoEjecutantedeTarea, 
							nombreAgenteEmisor,  infoDecision.getidElementoDecision());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
