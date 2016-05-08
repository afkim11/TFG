package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;

import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos.DecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class DoyObjetivoPorAsignado extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		DecidirQuienVa dec = (DecidirQuienVa) params[0];
		dec.setSolved();
		this.getEnvioHechos().actualizarHechoWithoutFireRules(dec);
		
	}

}
