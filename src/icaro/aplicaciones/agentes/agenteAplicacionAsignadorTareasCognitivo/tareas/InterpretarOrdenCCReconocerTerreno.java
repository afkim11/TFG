package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class InterpretarOrdenCCReconocerTerreno extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		//Elegir al robot
		String robotReconocedor = "robotSubordinado1";
		
		
		//Enviarle un mensaje para que ejecute una regla
		PropuestaAgente propuesta = new PropuestaAgente(this.identAgente);
		propuesta.setMensajePropuesta(VocabularioRosace.MsgExploraTerreno);
		
		this.comunicator.enviarInfoAotroAgente(propuesta, robotReconocedor);
		
	}

}
