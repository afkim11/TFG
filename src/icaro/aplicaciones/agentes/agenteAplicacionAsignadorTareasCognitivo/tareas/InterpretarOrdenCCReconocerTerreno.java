package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.OrdenCentroControl;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos.DecidirQuienVa;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos.ReconocerTerreno;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class InterpretarOrdenCCReconocerTerreno extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		MisObjetivos objs = (MisObjetivos) params[0];
		OrdenCentroControl orden = (OrdenCentroControl)params[1];
		if(VocabularioRosace.reconocedoresActualesReglas < VocabularioRosace.numeroReconocedores){
			ReconocerTerreno reconocer = new ReconocerTerreno(VocabularioRosace.reconocedoresActuales);
			VocabularioRosace.reconocedoresActuales++;
			reconocer.setPriority(9);
			objs.setobjetivoMasPrioritario(reconocer);

			DecidirQuienVa newDecision = new DecidirQuienVa(VocabularioRosace.MsgExploraTerreno);
			newDecision.setSolving();
			this.getEnvioHechos().insertarHechoWithoutFireRules(reconocer);
			this.getEnvioHechos().actualizarHechoWithoutFireRules(objs);
			this.getEnvioHechos().insertarHechoWithoutFireRules(newDecision);
			VocabularioRosace.reconocedoresActualesReglas++;
		}
		this.getEnvioHechos().eliminarHecho(orden);
	}

}
