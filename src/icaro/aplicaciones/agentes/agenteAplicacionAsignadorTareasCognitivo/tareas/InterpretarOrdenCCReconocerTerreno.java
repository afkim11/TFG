package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.OrdenCentroControl;
import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos.AyudarVictima;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos.DecidirQuienVa;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos.ReconocerTerreno;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

public class InterpretarOrdenCCReconocerTerreno extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		MisObjetivos objs = (MisObjetivos) params[0];
		OrdenCentroControl orden = (OrdenCentroControl)params[1];
		
		ReconocerTerreno reconocer = new ReconocerTerreno(VocabularioRosace.reconocedoresActuales);
		VocabularioRosace.reconocedoresActuales++;
		reconocer.setPriority(9);
		objs.setobjetivoMasPrioritario(reconocer);
		
		DecidirQuienVa newDecision = new DecidirQuienVa(VocabularioRosace.MsgExploraTerreno);
		newDecision.setSolving();
		this.getEnvioHechos().insertarHechoWithoutFireRules(reconocer);
		this.getEnvioHechos().actualizarHechoWithoutFireRules(objs);
		this.getEnvioHechos().eliminarHechoWithoutFireRules(orden);
		this.getEnvioHechos().insertarHecho(newDecision);
		
	}

}
