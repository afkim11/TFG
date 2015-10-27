package icaro.aplicaciones.recursos.recursoMorse.imp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


import icaro.aplicaciones.Rosace.informacion.Coordinate;

import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas;
import icaro.aplicaciones.Rosace.utils.ReadXMLTestRobots;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigurationMapICAROTAndAMASAgents {

	//NumberOfRobots variable is initialised with the passed value in ConfigurationMapICAROTAndAMASAgents constructor
	private int NumberOfRobots;     //Number of robots for the simulation
		
	//ICAROTAgents2InitialTargetCoordinates contains initial coordinates that should be go ICARO-T agents when start the simulation
	private Map<String, Coordinate> ICAROTAgents2InitialTargetCoordinates; 

	
	//The constructor is "called" in class ClaseGeneradoraRecursoMorse	
	public ConfigurationMapICAROTAndAMASAgents(){

    	String rutaFicheroRobotTest = getRutaFicheroRobotTest();
		ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots(rutaFicheroRobotTest);
		
//		ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots( Constantes.rutasFicheroRobots);
		
		Document doc = rXMLTRobots.getDocument(rXMLTRobots.gettestFilePaht());
		NodeList nodeLst = rXMLTRobots.getRobotsXMLStructure(doc, "robot");
        int numeroRobots = rXMLTRobots.getNumberOfRobots(nodeLst);
		
		this.NumberOfRobots = numeroRobots; 
		
		System.out.println("\nNumber de robots included in the simulation --> " + this.NumberOfRobots);
				
		ICAROTAgents2InitialTargetCoordinates = new HashMap<String, Coordinate>();
		InitializeICAROTAgents2CoordinateInitialICAROTAgents();
	}
								
	
    public void InitializeICAROTAgents2CoordinateInitialICAROTAgents(){

    	String rutaFicheroRobotTest = getRutaFicheroRobotTest();

    	try {
//                ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots(Constantes.rutasFicheroRobots);    		
                ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots(rutaFicheroRobotTest);
                
                ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
                ItfUsoConfiguracion itfconfig = (ItfUsoConfiguracion)itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
                String    identificadorEquipo = itfconfig.getValorPropiedadGlobal(NombresPredefinidos.NOMBRE_PROPIEDAD_GLOBAL_EQUIPO_AGENTES);
                InfoEquipo infoTeam = new InfoEquipo("",identificadorEquipo);
                int numberOfRobots = infoTeam.getNumberOfTeamMembers();
                
                String teamId = infoTeam.getTeamId();
                
                for (int i=1;i<=numberOfRobots;i++){                	
                     String robotName= teamId +i;                                          
                     Coordinate coordinateRobot = rXMLTRobots.getRobotCoordinate(robotName);                                          
                     ICAROTAgents2InitialTargetCoordinates.put(robotName, coordinateRobot);
                }   		
        } catch (Exception ex) {
            Logger.getLogger(ConfigurationMapICAROTAndAMASAgents.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

    
    //Se utiliza en NotificacionEventosRecursoGUI1, NotificacionEventosRecursoGUI2, NotificacionEventosRecursoGUI3  
	public Coordinate getCoordinateInitialICAROTAgents(String ICAROTAgentName){
		System.out.println("coordenada del agente " + ICAROTAgentName);
		return ICAROTAgents2InitialTargetCoordinates.get(ICAROTAgentName);		
	}
	
	
	public int getNumberOfRobots(){
		return this.NumberOfRobots;
	}

	
	private String getRutaFicheroRobotTest(){
		
		String rutaFicheroRobotTest = "";
		ClaseGeneradoraRepositorioInterfaces itfUsoRepositorioInterfaces;
		ItfUsoConfiguracion itfconfig;
		
		//Recuperar la ruta del fichero de robots del escenario
    	try{    	
    		itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
    		itfconfig = (ItfUsoConfiguracion)itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
    		rutaFicheroRobotTest = itfconfig.getValorPropiedadGlobal(VocabularioRosace.rutaFicheroRobotsTest); 
    	}
    	catch (Exception e){
    		e.printStackTrace();
    	}
				
		return rutaFicheroRobotTest;
	}
	
	
}
