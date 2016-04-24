package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Informe;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class VictimaNoRescatadaATiempo extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		// TODO Auto-generated method stub
		Informe informe = (Informe) params[0];
		Victim victima = (Victim) params[1];


		String nombreVictima = informe.identEntidadEmisora;
		Object[] valoresParametrosAccion = new Object[1];
		valoresParametrosAccion[0]=nombreVictima;
		String robotIdEncargado = victima.getIdRobotEncargadoDeMi();
		if(robotIdEncargado != null)
			this.comunicator.enviarInfoAotroAgente(new Informe(this.identAgente, nombreVictima,VocabularioRosace.MsgVictimaMuertaASubordinado),robotIdEncargado);

		InfoContEvtMsgAgteReactivo msg = new InfoContEvtMsgAgteReactivo("victimaMuerta", valoresParametrosAccion);
		this.comunicator.enviarInfoAotroAgente(msg, VocabularioRosace.IdentAgteControladorSimulador);
		this.itfProcObjetivos.eliminarHechoWithoutFireRules(informe);
		this.itfProcObjetivos.eliminarHecho(victima);
	}

}
