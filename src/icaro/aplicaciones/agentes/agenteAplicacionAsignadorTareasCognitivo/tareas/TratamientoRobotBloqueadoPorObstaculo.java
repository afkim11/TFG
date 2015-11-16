package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeBloqueoObstaculo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class TratamientoRobotBloqueadoPorObstaculo extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		MensajeBloqueoObstaculo mensaje=(MensajeBloqueoObstaculo) params[0];
		this.agente.añadirObstaculo(mensaje.getObstaculo());
		//Damos la oportunidad al robot de esquivar al obstaculo. Para ello, generamos un timeout para obtener una solucion al obstaculo
		this.generarInformeTemporizadoFromConfigProperty(VocabularioRosace.IdentTareaTimeOutEsquivarObstaculo, null, this.identAgente, NombresPredefinidos.PREFIJO_MSG_TIMEOUT_ESQUIVAR_OBSTACULO);
	
	}

}
