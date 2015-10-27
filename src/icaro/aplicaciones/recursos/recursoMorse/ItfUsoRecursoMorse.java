package icaro.aplicaciones.recursos.recursoMorse;

import java.util.List;

import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import icaro.aplicaciones.recursos.recursoMorse.imp.ConfigurationMapICAROTAndAMASAgents;

import icaro.aplicaciones.Rosace.informacion.Coordinate;


public interface ItfUsoRecursoMorse extends ItfUsoRecursoSimple {
	
	//ICARO-T framework needs that the declared methods in the resource interface contains the throws Exception  
	public void methodTestRecursoMorse1() throws Exception;
	public void methodTestRecursoMorse2() throws Exception;
		
	//----------------------------------------------------------------------------------------------
	//Methods to access MORSE sensor/actuators via the ICARO-T agent name
	public Coordinate getGPSInfo(String iCAROTAgentName) throws Exception;
	public void goToLocation(String iCAROTAgentName, Coordinate targetCoordinate) throws Exception;
	public void stopRobot(String iCAROTAgentName, int delay) throws Exception;	
	//public FollowStatus getRobotStatus(String iCAROTAgentName) throws Exception;
	
	//public List<Integer> getCapabilitiesRobot(String iCAROTAgentName) throws Exception;

	//public VictimInfos getVisibleVictims(String iCAROTAgentName)  throws Exception;
	
	//public List<AgentRef> getNeighboursRobots(String iCAROTAgentName) throws Exception;

	public void healVictim(String iCAROTAgentName, int delay) throws Exception;
	
	public ConfigurationMapICAROTAndAMASAgents getConfigurationMapICAROTAndAMASAgents() throws Exception;

	
}