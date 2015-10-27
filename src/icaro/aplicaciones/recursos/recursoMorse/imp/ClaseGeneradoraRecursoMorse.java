package icaro.aplicaciones.recursos.recursoMorse.imp;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Thread;

import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.recursos.recursoMorse.ItfUsoRecursoMorse;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas;
import icaro.aplicaciones.Rosace.utils.ReadXMLTestRobots;
import icaro.aplicaciones.Rosace.utils.WaitSecond;

//This resource class needs the next imports in order to send events towards some agent
//import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;


//Imports in order to be able know relations betwen ICAROT and AMAS agents
import icaro.aplicaciones.recursos.recursoMorse.imp.ConfigurationMapICAROTAndAMASAgents;
//Import in order to read information about robots

public class ClaseGeneradoraRecursoMorse extends ImplRecursoSimple implements ItfUsoRecursoMorse {

    private ItfUsoRecursoTrazas trazas; //Se inicializa en el constructor con la referencia al recurso de trazas. Asi ya estara disponible en cualquier metodo.
    private String idRecurso;  //Se inicializara en el constructor con el identificador, dado a la instancia del recurso, en la descripcion de la organizacion

    //Other global variables used in this Resource
    private ConfigurationMapICAROTAndAMASAgents configMapICAROTAndAMASAgents;
    
    public ClaseGeneradoraRecursoMorse(String idRecurso)  throws Exception {

        super(idRecurso);
        this.idRecurso = idRecurso;
        try {
               trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance()
                                   .obtenerInterfaz(NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
               
//               configMapICAROTAndAMASAgents = new ConfigurationMapICAROTAndAMASAgents(ConfigurationMapICAROTAndAMASAgents.NumberOfRobots);

               configMapICAROTAndAMASAgents = new ConfigurationMapICAROTAndAMASAgents();

              // ItfUsoConfiguracion itfconfig = (ItfUsoConfiguracion)itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
              // String identificadorEquipo = itfconfig.getValorPropiedadGlobal(NombresPredefinidos.IDENTICADOR_EQUIPO_AGENTES);
               
               
        } catch (Exception e) {
  //      	this.itfAutomata.transita("error");
           // this.estadoAutomata.transita("error");
            System.out.println("No se pudo usar el recurso de trazas");
            e.printStackTrace();
        }
		
        //A continuacion se sigue completando el constructor si es necesario .... 
        //      .................................
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
               
        //Ultima llamada del constructor seria la siguiente
        trazas.aceptaNuevaTraza(new InfoTraza(this.idRecurso, "El constructor de la clase generadora del recurso " + this.id + " ha completado su ejecucion ....",	InfoTraza.NivelTraza.debug));	                    
    }	

    
    //Methods that implement Morse resource use interface
    //If throws Exception is not specified here then it will work too 
	public void methodTestRecursoMorse1() throws Exception{
        trazas.aceptaNuevaTraza(new InfoTraza(this.idRecurso, "The methodTestRecursoMorse1 method defined in ItfUsoRecursoMorse interface is executed ...",	InfoTraza.NivelTraza.debug));		
	}

	
	public void methodTestRecursoMorse2() throws Exception{
		
	}
		    			
	
	//Method to retrieve the initial coordinate of a robot. This allows us to know where a robot should be go again after stopping.
	public ConfigurationMapICAROTAndAMASAgents getConfigurationMapICAROTAndAMASAgents() throws Exception{
	   System.out.println("valor de  ............... " + configMapICAROTAndAMASAgents);	
	   return this.configMapICAROTAndAMASAgents;
	};
	
	//Methods in order to get information provided by MORSE robot sensors and send commands to MORSE robot actuators

	public Coordinate getGPSInfo(String iCAROTAgentName) throws Exception{
				
	   //*********************************************************************
		      // SUSTITUIR POR LECTURA DE COORDENADAS DESDE UN FICHERO XML
	   //*********************************************************************

    	String rutaFicheroRobotTest = getRutaFicheroRobotTest();
        ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots(rutaFicheroRobotTest);

//		ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots(Constantes.rutasFicheroRobots);
        // Esto es provisional y hay que cambiarlo *****************
                
		Coordinate coordinateRobot = rXMLTRobots.getRobotCoordinate(iCAROTAgentName);

		return coordinateRobot;				
	};
	
		
	public void goToLocation(String iCAROTAgentName, Coordinate targetCoordinate) throws Exception{
		
		//   BehaviorEmptyTest bet = getAMASAgentBehavior(iCAROTAgentName);
      	//   bet.goToLocation(targetCoordinate);
		
		   //*********************************************************************
	       //             SUSTITUIR POR ??????????????????
           //*********************************************************************
		
	};
	
	public void stopRobot(String iCAROTAgentName, int delay) throws Exception{
		
		   //BehaviorEmptyTest bet = getAMASAgentBehavior(iCAROTAgentName);
		   //bet.stopRobot(delay);

		   //*********************************************************************
	       //             SUSTITUIR POR ??????????????????
           //*********************************************************************

//		   String aMASAgentName = iCAROTAgentName;
		   //Get the ICARO_T agent name related to AMAS agent name 
           //Get the use interface belonging to RecursoMorse1
		
//		   ItfUsoRepositorioInterfaces ItfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
//		   ItfUsoRecursoMorse morseResourceRef;
		   InterfazUsoAgente itfUsoAgenteReceptor;

           try{
//		        morseResourceRef = (ItfUsoRecursoMorse) ItfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + 
//		                                                "RecursoMorse1");
		        
//			    ConfigurationMapICAROTAndAMASAgents confMapICAROTAndAMASAgents = morseResourceRef.getConfigurationMapICAROTAndAMASAgents();			 
//			    String identReceiverICAROTAgent = confMapICAROTAndAMASAgents.getICAROTAgentName(aMASAgentName);

			    String identReceiverICAROTAgent = iCAROTAgentName;

			    
		        itfUsoAgenteReceptor =  (InterfazUsoAgente) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(
		    		                                        NombresPredefinidos.ITF_USO + identReceiverICAROTAgent);

		        //-------------------------------------------------
		        //    LEER DEL XML LA POSICION SIMULADA DEL ROBOT
		        //-------------------------------------------------	

		    	String rutaFicheroRobotTest = getRutaFicheroRobotTest();
		        ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots(rutaFicheroRobotTest);

//				ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots(Constantes.rutasFicheroRobots);
				Coordinate coordinateRobot = rXMLTRobots.getRobotCoordinate(identReceiverICAROTAgent);
		        
		        
		        EventoSimple evs = new EventoSimple("RecursoMorse1",coordinateRobot);
		        itfUsoAgenteReceptor.aceptaEvento(evs);
		        ItfUsoRecursoTrazas trazas;
                trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
            		        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
             
                trazas.aceptaNuevaTraza(new InfoTraza(identReceiverICAROTAgent,".....^^^^^^^^ FollowStatusHandler:actionPerformed: ***** Robot " + identReceiverICAROTAgent + " stoped at location " +
                		                 coordinateRobot ,InfoTraza.NivelTraza.debug));	                    				     
           }
           catch (Exception ex){
               ex.printStackTrace();	
           }				
	};
	
	
//	public FollowStatus getRobotStatus(String iCAROTAgentName) throws Exception{
//		   BehaviorEmptyTest bet = getAMASAgentBehavior(iCAROTAgentName);
//		   return bet.getRobotStatus();				
//	}
		
//	public List<Integer> getCapabilitiesRobot(String iCAROTAgentName) throws Exception{
//		   BehaviorEmptyTest bet = getAMASAgentBehavior(iCAROTAgentName);
//	       return bet.getCapabilitiesRobot();			
//	};
	
	
//	public VictimInfos getVisibleVictims(BehaviorEmptyTest bet) throws Exception{
//		   return bet.getVisibleVictims();
//	} 
	
	
//	public VictimInfos getVisibleVictims(String iCAROTAgentName)  throws Exception{
//		   BehaviorEmptyTest bet = getAMASAgentBehavior(iCAROTAgentName);
//		   return bet.getVisibleVictims();		
//	};
	
	
//	public List<AgentRef> getNeighboursRobots(String iCAROTAgentName) throws Exception{
//		   BehaviorEmptyTest bet = getAMASAgentBehavior(iCAROTAgentName);
//		   return bet.getNeighboursRobots();		
//	};
	
	
	public void healVictim(String iCAROTAgentName, int delay) throws Exception{
		  // BehaviorEmptyTest bet = getAMASAgentBehavior(iCAROTAgentName);
		  // bet.healVictim(delay);
		
		   //*********************************************************************
	       //             SUSTITUIR POR ??????????????????
           //*********************************************************************
		System.out.println("ROBOT " + iCAROTAgentName + " CURA A LA VICTIMA......");
	}
	
	
	//Methods in order to get relationship between ICARO-T and AMAS agents
//	public BehaviorEmptyTest getAMASAgentBehavior(String iCAROTAgentName) throws Exception{
//	       String aMASAgentName = configMapICAROTAndAMASAgents.getAMASAgentName(iCAROTAgentName);
//	       BehaviorEmptyTest bet = configMapICAROTAndAMASAgents.getAMASAgentBehavior(aMASAgentName);
//           return bet;
//	};
	
	//It is used for testing the methods. It allows us knowing the AMAS agent name 
	//private AgentRef getAMASAgentRef(String AMASAgentName) throws Exception{
	//	   return configMapICAROTAndAMASAgents.getAMASAgentRef(AMASAgentName);	
    //}
	
    //End methods that implement Morse resource use interface


    @Override
    public void termina() {
        trazas.aceptaNuevaTraza(new InfoTraza(this.idRecurso, "Terminando recurso" + this.id + " ....",	InfoTraza.NivelTraza.debug));
 	    
        //Si es un recurso de visualizacion es necesaria una llamar a dispose de la ventana de visualizacion. Algo parecido a lo siguiente	
        //this.jvariableLocalReferenciaVisualizador.dispose(); //Destruye los componentes utilizados por este JFrame y devuelve la memoria utilizada al Sistema Operativo 	 
		
        super.termina();
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