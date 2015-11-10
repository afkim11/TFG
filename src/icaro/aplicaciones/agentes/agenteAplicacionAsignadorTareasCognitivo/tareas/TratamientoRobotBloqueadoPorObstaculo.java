package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;

import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeBloqueoObstaculo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class TratamientoRobotBloqueadoPorObstaculo extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		MensajeBloqueoObstaculo mensaje=(MensajeBloqueoObstaculo) params[0];
		this.agente.añadirObstaculo(mensaje.getObstaculo());
		
	
		
		
		
	}

}
