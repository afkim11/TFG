package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;

import java.util.ArrayList;

import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeBloqueoObstaculo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

public class EnviarMensajeAlJefePorBloqueoPorObstaculo extends TareaSincrona{
	private String asignadorId;
	private InfoEquipo miEquipo;
	private String nombreAgenteEmisor;
	private MensajeBloqueoObstaculo mensaje;
	private ComunicacionAgentes comunicador;
	@Override
	public void ejecutar(Object... params) {
		this.nombreAgenteEmisor=(String)params[0];
		miEquipo = (InfoEquipo)params[1];
		this.mensaje=(MensajeBloqueoObstaculo)params[2];
		this.comunicador=getComunicator();
		this.comunicador.enviarMsgaOtroAgente(mensaje);
		
		
	}
	
	private String getIdentAgteAsignadorTareas(){
		ArrayList<String> identsConMismoRol = miEquipo.getTeamMemberIDsWithThisRol(VocabularioRosace.IdentRolAgteDistribuidorTareas);  
		if (identsConMismoRol.isEmpty())trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "No se ha encontrado un agente con el rol :  "+ VocabularioRosace.IdentAgteDistribuidorTareas, InfoTraza.NivelTraza.error)); 
		else if (identsConMismoRol.size()>1) trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Hay mas de un agente con el rol : "+ VocabularioRosace.IdentAgteDistribuidorTareas, InfoTraza.NivelTraza.error)); 
		else return identsConMismoRol.get(0);
		return null;
	}

}
