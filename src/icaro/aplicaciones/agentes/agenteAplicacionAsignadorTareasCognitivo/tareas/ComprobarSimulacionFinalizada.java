package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;

import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos.TerminarSimulacion;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Informe;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class ComprobarSimulacionFinalizada extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		TerminarSimulacion obj = (TerminarSimulacion)params[0];
		Informe informe =(Informe) params[1];
		this.itfProcObjetivos.eliminarHechoWithoutFireRules(informe);
		
		
	}

}
