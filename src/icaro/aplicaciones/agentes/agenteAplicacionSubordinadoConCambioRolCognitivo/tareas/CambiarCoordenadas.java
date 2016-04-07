package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.RobotStatus;
import icaro.infraestructura.entidadesBasicas.comunicacion.Informacion;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class CambiarCoordenadas extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		
		RobotStatus robotStatus = (RobotStatus) params[0];	
		
		Informacion mensaje = (Informacion) params[1];
		RobotStatus newStatus = (RobotStatus) mensaje.getContenido2();
		
		robotStatus.setAvailableEnergy(newStatus.getAvailableEnergy());
		robotStatus.setRobotCoordinate(newStatus.getRobotCoordinate());
		robotStatus.setIdRobot(newStatus.getIdRobot());
		robotStatus.setIdRobotRol(newStatus.getIdRobotRol());
		robotStatus.setRobotCapabilities(newStatus.getRobotCapabilities());
		this.itfProcObjetivos.actualizarHechoWithoutFireRules(robotStatus);
		this.itfProcObjetivos.eliminarHechoWithoutFireRules(mensaje);
	}

}
