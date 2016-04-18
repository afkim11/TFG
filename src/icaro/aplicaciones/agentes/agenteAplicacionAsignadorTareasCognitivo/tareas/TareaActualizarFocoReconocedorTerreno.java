package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.AceptacionPropuesta;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos.DecidirQuienVa;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos.ReconocerTerreno;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class TareaActualizarFocoReconocedorTerreno extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		DecidirQuienVa dqv = (DecidirQuienVa) params[0];
		ReconocerTerreno rc = (ReconocerTerreno) params[1];
		Focus foco = (Focus) params[2];
		AceptacionPropuesta aceptacionPropuesta = (AceptacionPropuesta) params[3];
		dqv.setSolved();
		foco.setFoco(null);
		this.itfProcObjetivos.eliminarHechoWithoutFireRules(rc);
		this.itfProcObjetivos.eliminarHechoWithoutFireRules(aceptacionPropuesta);
		this.itfProcObjetivos.actualizarHechoWithoutFireRules(dqv);
		this.itfProcObjetivos.actualizarHecho(foco);
		
		
	}

}
