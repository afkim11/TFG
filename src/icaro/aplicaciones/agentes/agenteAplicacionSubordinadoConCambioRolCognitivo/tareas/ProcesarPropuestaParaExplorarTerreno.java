/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.*;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.Informacion;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Informe;
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

public class ProcesarPropuestaParaExplorarTerreno extends TareaSincrona {

	private String nombreAgenteEmisor;
	private ItfUsoRecursoTrazas trazas;
	private RobotStatus miStatus;
	private String identDeEstaTarea ;
	//    private String mensajePropuesta ;   ////????????????????????esto no se inicializa?????????????

	@Override
	public void ejecutar(Object... params) {
		try {
			trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
			MisObjetivos misObjtvs = (MisObjetivos) params[0];
			miStatus = (RobotStatus)params[1];
			PropuestaAgente propuestaRecibida =  (PropuestaAgente)params[2];
			nombreAgenteEmisor = this.getAgente().getIdentAgente();
			identDeEstaTarea = this.getIdentTarea();
			AceptacionPropuesta aceptacionAsignacion = new AceptacionPropuesta ( nombreAgenteEmisor,VocabularioRosace.MsgAceptacionPropuestaExplora, propuestaRecibida );
			aceptacionAsignacion.setidentObjectRefAcetPropuesta(VocabularioRosace.MsgAceptacionPropuestaExplora);

			if(this.identAgente.equalsIgnoreCase("jerarquicoagenteasignador")){
				this.getComunicator().enviarInfoAotroAgente(new Informacion(VocabularioRosace.JerarquicoAsignadorRescatando), propuestaRecibida.getIdentAgente());
			}
			else {
				this.getComunicator().enviarInfoAotroAgente(aceptacionAsignacion, propuestaRecibida.getIdentAgente());
			}



			this.getEnvioHechos().eliminarHechoWithoutFireRules(propuestaRecibida);
			this.getEnvioHechos().insertarHecho(aceptacionAsignacion);	
			trazas.aceptaNuevaTrazaEjecReglas(nombreAgenteEmisor, "Se acepta la propuesta:  "+ propuestaRecibida );
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
