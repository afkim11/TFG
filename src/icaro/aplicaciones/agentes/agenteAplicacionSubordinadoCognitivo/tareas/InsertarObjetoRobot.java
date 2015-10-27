package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.RobotStatus;
import icaro.aplicaciones.Rosace.utils.AccesoPropiedadesGlobalesRosace;
import icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas;
import icaro.aplicaciones.Rosace.utils.ReadXMLTestRobots;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class InsertarObjetoRobot extends TareaSincrona{

	String valueid;
	
    @Override
   public void ejecutar(Object... params) {
    	
        String nombreAgenteEmisor = this.getIdentAgente();
        
        //Lectura del fichero de robots. Aprovechamos para tener en memoria la configuracion de robots.
        
        
        String rutaFicheroRobotsTest = AccesoPropiedadesGlobalesRosace.getRutaFicheroRobotsTest();
        
    	//ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots(Constantes.rutasFicheroRobotsJerarquico);

    	ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots(rutaFicheroRobotsTest);    	
		Document doc = rXMLTRobots.getDocument(rXMLTRobots.gettestFilePaht());
		//Obtain all the robots
		NodeList nodeLst = rXMLTRobots.getRobotsXMLStructure(doc, "robot");
		
		int numeroRobotsSimulation = rXMLTRobots.getNumberOfRobots(nodeLst);
		
		for(int j=0; j<numeroRobotsSimulation;j++){
  		    //Obtain info about robot located at the test        	
        	Element info = rXMLTRobots.getRobotElement(nodeLst, j);			        	
        	valueid = rXMLTRobots.getRobotIDValue(info, "id");
        	
        	if (nombreAgenteEmisor.equals(valueid)){        		
        	   int energy = rXMLTRobots.getRobotInitialEnergy(info, "initialenergy");
        	   Coordinate initialCoordinate = rXMLTRobots.getRobotCoordinate(info);
        	   float healRange = rXMLTRobots.getRobotHealRange(info, "healrange");
        		        	           	   
        	   RobotStatus robotStatus = new RobotStatus();        	           	   
        	   robotStatus.setIdRobot(valueid);
        	   robotStatus.setAvailableEnergy(energy);        	   
        	   robotStatus.setRobotCoordinate(initialCoordinate);        	   
        	   robotStatus.setHealRange(healRange); 
        	   
        	   this.getEnvioHechos().insertarHecho(robotStatus);
        	   
        	   j = numeroRobotsSimulation; //Salir del bucle for. Se ha encontrado la informacion xml asociada al robot/agente que ejecuta esta tarea
        	}        	        	
        }
   }
}
