package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;

import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos.TerminarSimulacion;
import icaro.infraestructura.clasesGeneradorasOrganizacion.ArranqueSistemaScript;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class FinalizarSimulacion extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		TerminarSimulacion obj = (TerminarSimulacion)params[0];
		
		ArranqueSistemaScript.guardaResultados(obj.getTiemposAsignacion(),obj.getTiemposResolucion());
	}

}
