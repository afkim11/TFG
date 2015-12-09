package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;

import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.comunicacion.Informacion;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class MandarEstatusEsquivarObstaculo extends TareaSincrona{
	private ComunicacionAgentes comunicador;
	@Override
	public void ejecutar(Object... params) {
		MensajeSimple mensaje=(MensajeSimple)params[0];
		this.comunicador=getComunicator();
		String receptor = (String)mensaje.getReceptor();
		this.comunicador.enviarInfoAotroAgente(this.agente.getVictima(), receptor);
		this.comunicador.enviarMsgaOtroAgente(mensaje);
	}

}
