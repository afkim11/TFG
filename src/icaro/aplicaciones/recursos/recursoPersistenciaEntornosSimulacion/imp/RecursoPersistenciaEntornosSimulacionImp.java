/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.imp;

import com.thoughtworks.xstream.XStream;
import icaro.aplicaciones.Rosace.informacion.*;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.EscenarioSimulacionRobtsVictms;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author FGarijo
 */
public class RecursoPersistenciaEntornosSimulacionImp extends ImplRecursoSimple{
	EscenarioSimulacionRobtsVictms escenario;
	private NodeList nodeLst;      // estructura en memoria con todos los nodos de								   // las victimas que hay en el fichero xml
	private String rutaFicheroEscenario;
	private int numeroVictimasDiferentesSimulacion = 0; //Numero de victimas diferentes que van a intervenir en el proceso de simulacion
	private Map<String, String> victimsDiferentesXML = new HashMap <String, String>();  //Victimas diferentes que hay en el fichero de secuencia tomado como entrada
	private ArrayList<Victim> victimasDefinidas;    //Victimas que hay en el entorno 
	private ArrayList<RobotStatus> robotsDefinidos;    //Victimas que hay en el entorno 
	protected ComunicacionAgentes comunicacion;
	protected InfoEquipo equipo;
	protected ArrayList identsAgtesEquipo;
	protected String identificadorEquipo;
	private String directorioPersistencia;
	private String identFicheroInfoAsigVictimasObj;
	private String identFicheroInfoAsigVictimasXML;
	private static final int tiempoDeVidaVictimaPorDefecto = 10000;

	//    private ObjectOutputStream  oPersAsignVictima;
	//    private ObjectInputStream  inPersAsignVictima;
	private  ArrayList<InfoAsignacionVictima> infoVictimasAsigandas  ;
	public RecursoPersistenciaEntornosSimulacionImp (String recursoId) throws RemoteException{
		super (recursoId);
	}
	public  void  inicializarRecursoPersistenciaEntornosSimulacion (){
		try {
			ItfUsoConfiguracion   itfconfig = (ItfUsoConfiguracion) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
			rutaFicheroEscenario = itfconfig.getValorPropiedadGlobal(VocabularioRosace.rutaFicheroEscenarioSimulacion);
			this.escenario = new Persister().read(EscenarioSimulacionRobtsVictms.class,new File(rutaFicheroEscenario + ".xml"),false);
			victimasDefinidas = new ArrayList<Victim> ();
			if ((escenario!=null) && (escenario.getListIdentsVictims()!=null))
				numeroVictimasDiferentesSimulacion = escenario.getNumVictimas();
			// se obtienen tambien el conjunto de vicitmas definidas
			//    String nombreFicheroAsignVictim = "asigVictimasObjetos";
			directorioPersistencia = VocabularioRosace.IdentDirectorioPersistenciaSimulacion+File.separator;
			identFicheroInfoAsigVictimasObj = directorioPersistencia+VocabularioRosace.NombreFicheroSerieInfoAsignacionVictimas+".tmp";
			identFicheroInfoAsigVictimasXML = directorioPersistencia+VocabularioRosace.NombreFicheroSerieInfoAsignacionVictimas+".xml";
			File dirFicherosPersistencia = new File(directorioPersistencia);
			if(!dirFicherosPersistencia.exists())dirFicherosPersistencia.mkdir();
			infoVictimasAsigandas = new ArrayList<InfoAsignacionVictima>();
			robotsDefinidos = getTeamRobotStatus();
		} catch (Exception ex) {
			Logger.getLogger(RecursoPersistenciaEntornosSimulacionImp1.class.getName()).log(Level.SEVERE, null, ex);
			//           return null;
		}
	}
	public boolean guardarInfoEscenarioSimulacion(EscenarioSimulacionRobtsVictms escenario){
		Serializer serializer = new Persister();
		String identFichero = VocabularioRosace.rutaPersistenciaEscenarios+escenario.getIdentEscenario()+".xml";
		try {
			File dirFicherosPersistencia = new File(VocabularioRosace.rutaPersistenciaEscenarios);
			if(!dirFicherosPersistencia.exists()){
				dirFicherosPersistencia.mkdir();
				//                    rutaFicheroInfoPersistencia= dirFicherosPersistencia.getAbsolutePath()+"\\";
			}
			File result = new File(identFichero);
			if (result.exists()){
				result.delete();
				serializer.write(escenario, new File(identFichero));
			}else serializer.write(escenario, result);
			ArrayList<String> robotNombres = escenario.getListIdentsRobots();
			for (String ideRobot:robotNombres){
				//                 String ideRobot = (String)robtIter.next();
				RobotStatus infoRobot = (RobotStatus) escenario.getRobotInfo(ideRobot);

				List<RobotCapability> capacidades=infoRobot.getRobotCapabilities();
				System.out.println(" Desde persistencia Lista de capacidades a guardar del robot  : " + ideRobot+"Capacidades : "+ capacidades.toString() );
			}
			System.out.println("Desde peticion Guardar Numero de Robots  : " + escenario.getNumRobots()+" Numero de victimas : "+ escenario.getNumVictimas());

			//          serializer.write(escenario, new File(rutaFicheroInfoPersistencia+identFichero+".xml"));

			System.out.println("En el fichero   : "+ identFichero);
			System.out.println("Se va a guardar  : "+ escenario.toString() );
			return true;


		} catch (Exception e) { // catches ANY exception
			e.printStackTrace();
			return false;
		}    
	}

	public EscenarioSimulacionRobtsVictms obtenerInfoEscenarioSimulacion(String rutaFicheroInfoPersistencia){
		try {
			File ficheroEscenario = new File(rutaFicheroInfoPersistencia);
			if(!ficheroEscenario.exists()){
				//                    dirFicherosPersistencia.mkdir();
				//                    rutaFicheroInfoPersistencia= dirFicherosPersistencia.getAbsolutePath()+"\\";
				System.out.println("El fichero   : "+ rutaFicheroInfoPersistencia+ " No existe " );

			}else {
				Serializer serializer = new Persister();
				return   serializer.read(EscenarioSimulacionRobtsVictms.class,ficheroEscenario, false);

			}   
		}catch (Exception e) { // catches ANY exception
			e.printStackTrace();
		}
		return null;
	}
	public boolean  eliminarEscenarioSimulacion(String rutaFicheroInfoPersistencia){
		try {
			File dirFicherosPersistencia = new File(rutaFicheroInfoPersistencia);
			if(!dirFicherosPersistencia.exists()){
				System.out.println("El fichero   : "+ rutaFicheroInfoPersistencia+ " No existe " );
				//                    rutaFicheroInfoPersistencia= dirFicherosPersistencia.getAbsolutePath()+"\\";

			}else{
				//confirmar eliminacion y si lo confirma eliminar
				dirFicherosPersistencia.delete();
				return true;
			}
		}catch (Exception e) { // catches ANY exception
			e.printStackTrace();
		}  
		return false;
	}
	public HashSet obtenerIdentsEscenarioSimulacion(String rutaDirectorioInfoPersistencia){

		HashSet conjIdentsFicheros = new HashSet<String>();
		try {
			File directorioEscenario = new File(rutaDirectorioInfoPersistencia);
			if(directorioEscenario.exists()){
				File[] ficheros = directorioEscenario.listFiles();
				for (int x=0;x<ficheros.length;x++){
					System.out.println(ficheros[x].getName());
					conjIdentsFicheros.add(x);
				} return conjIdentsFicheros;
			}else return null;
		}catch (Exception e) { // catches ANY exception
			e.printStackTrace();
		}   return null;
	}

	/**
	 * Saves the current person data to the specified file.
	 * 
	 * @param file
	 */

	public ReadXMLTestSequence getSecuenciaVictimasClase(String rutaFicheroVictimasTest) throws Exception{    	
		ReadXMLTestSequence rXMLTSeq;    	    	
		rXMLTSeq = new ReadXMLTestSequence(rutaFicheroVictimasTest);    	
		return rXMLTSeq;
	}

	public Document getDocumentVictimas(ReadXMLTestSequence rXMLTSeq) throws Exception{        
		if (rXMLTSeq!=null)    	
			return rXMLTSeq.getDocument(rXMLTSeq.gettestFilePath());
		else    
			return null;
	}


	public NodeList getListaNodosVictima(ReadXMLTestSequence rXMLTSeq, Document doc) throws Exception{    	
		if ((rXMLTSeq!=null) && (doc!=null)) 
			return rXMLTSeq.getVictimsXMLStructure(doc, "victim"); // Obtain all the victims   	
		else return null;
	}


	public int getNumeroTotalVictimasEnLaSecuencia(){

		return numeroVictimasDiferentesSimulacion;
	}

	public void setNumeroVictimasDiferentesSimulacion() throws Exception{
		
		int numItemsVictim = escenario.getNumVictimas();
		Victim victima;
		ArrayList<String> victimasIds = escenario.getListIdentsVictims();
		for (int i=0; i<numItemsVictim; i++){
			String valueid = victimasIds.get(i);
			//Si esta repetida no se hara nada 
			if ( valueid != null && !victimsDiferentesXML.containsKey(valueid)){
				victimsDiferentesXML.put(valueid, valueid); 
				victima = this.escenario.getVictims().get(valueid);
				victimasDefinidas.add(victima);
			}
		}
		this.numeroVictimasDiferentesSimulacion = this.escenario.getNumVictimas();  	
	};

	public int getNumeroVictimasDiferentesSimulacion() throws Exception{
		return this.numeroVictimasDiferentesSimulacion;
	}

	public ArrayList<Victim>  getVictimsArescatar (){

		return victimasDefinidas;      
	}

	private Victim createNewVictim(ReadXMLTestSequence rXMLTSeq,NodeList nodeLst, int numeroVictima) {
		//	int posicionVictimaNodoLst = numeroVictima - 1; // El nodo de la victima

		Element info = rXMLTSeq.getVictimElement(nodeLst,numeroVictima);
		String valueid = rXMLTSeq.getVictimIDValue(info, "id");
		int valueseverity = rXMLTSeq.getVictimSeverity(info, "severity");
		List<Integer> victimRequirements = new ArrayList<Integer>();
		victimRequirements = rXMLTSeq.getVictimRequirements(info);                    
		Coordinate valueCoordinate = rXMLTSeq.getVictimCoordinate(info);
		return new Victim(valueid, valueCoordinate, valueseverity, victimRequirements,tiempoDeVidaVictimaPorDefecto);  
	}

	//obtener valor en segundos con nrodecimales decimales a partir de long que expresa el tiempo en milisegundos
	private double ObtenerTiempoEnSegundosUnDecimal(long tiempoEnMilisegundos, int nrodecimales){
		//		  int numtiempoEnMiliSegundos = Integer.parseInt(strMilisegundos);		  
		double numtiempoEnSegundos = ((double)tiempoEnMilisegundos / 1000);		  		  
		String strnumtiempoEnSegundos = "" + numtiempoEnSegundos;		  		  
		int posPunto = strnumtiempoEnSegundos.indexOf('.');
		String strnumtiempoEnSegundosUnDecimal = strnumtiempoEnSegundos.substring(0, posPunto+1+nrodecimales);
		return Double.parseDouble(strnumtiempoEnSegundosUnDecimal);
		//		  return strnumtiempoEnSegundosUnDecimal;
	}

	public void guardarInfoCasoSimulacion(InfoCasoSimulacion infoCaso) throws Exception {
		// se guarda la serie asignacion de victimas. Las series graficas se guardan por orden del agente 
	} 
	public void guardarInfoAsignacionVictima (InfoAsignacionVictima infoAsignVictima)throws Exception{

		infoVictimasAsigandas.add(infoAsignVictima);
		FileOutputStream fInfoAsignVictima = new FileOutputStream(identFicheroInfoAsigVictimasObj);
		ObjectOutputStream  oPersAsignVictima = new ObjectOutputStream (fInfoAsignVictima);
		oPersAsignVictima.writeObject(infoVictimasAsigandas);
		oPersAsignVictima.close();


	}

	public ArrayList<InfoAsignacionVictima> obtenerInfoAsignacionVictimas ()throws Exception{
		//   String nombreFicheroAsignVictim = "pruebaAsigVictimas";
		if (infoVictimasAsigandas.isEmpty())return null;
		String directorioPersistencia = VocabularioRosace.IdentDirectorioPersistenciaSimulacion+File.separator;
		//             String identFichero = directorioPersistencia+nombreFicheroAsignVictim;
		File ficheroPersistencia = new File(identFicheroInfoAsigVictimasObj);
		if(ficheroPersistencia.exists()){

			FileInputStream fInInfoAsignVictima = new FileInputStream(identFicheroInfoAsigVictimasObj);
			ObjectInputStream   inPersAsignVictima = new ObjectInputStream (fInInfoAsignVictima);
			//               ArrayList<InfoAsignacionVictima> infoAsignVictimas = new ArrayList<InfoAsignacionVictima>();
			//               infoAsignVictimas = 
			return (ArrayList<InfoAsignacionVictima>)inPersAsignVictima.readObject();
		}
		else return null;
	} 

	public boolean guardarSerieResultadosXML(InfoSerieResultadosSimulacion serieResultados){
		// Se guarda en un fichero xml

		try {
			String identFicheroSerie=directorioPersistencia+ serieResultados.getTituloSerie()+".xml";
			//     FileOutputStream fserieResultados = new FileOutputStream(identFicheroSerie);
			Path path = FileSystems.getDefault().getPath(".", identFicheroSerie);
			OutputStream in = Files.newOutputStream(path);
			BufferedWriter escritorXML = Files.newBufferedWriter(FileSystems.getDefault().getPath(".", identFicheroSerie),
					Charset.forName("US-ASCII"),
					StandardOpenOption.CREATE);
			//     PrintWriter xmlWriter = new PrintWriter(new FileOutputStream(identFicheroSerie));
			//      Writer buffer = new StringWriter();

			XStream xstream = new XStream(); 
			xstream.alias(serieResultados.getTituloSerie(), InfoSerieResultadosSimulacion.class);
			xstream.alias("puntoAsignacionVictima", PuntoEstadistica.class);
			escritorXML.write(xstream.toXML(serieResultados));
			//      xmlWriter.print(xstream.toXML(serieResultados));
			//      xmlWriter.close();
			System.out.println(xstream.toXML(serieResultados));
			escritorXML.close();
			return true ;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
	}
	public String getRutaEscenarioSimulacion()throws Exception{
		return this.rutaFicheroEscenario;
	}
	RobotStatus getRobotStatus ( String robotId)throws Exception{
		//    ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots(rutaFicheroRobotsTest);
		//    	Document doc = rXMLTRobots.getDocument(rXMLTRobots.gettestFilePaht());
		//		//Obtain all the robots
		//		NodeList nodeLst = rXMLTRobots.getRobotsXMLStructure(doc, "robot");
		//		
		int numeroRobotsSimulation = robotsDefinidos.size();
		int j=0; boolean encontrado = false;
		while( j<numeroRobotsSimulation&& !encontrado){
			//Obtain info about robot located at the test        	
			//        	Element info = rXMLTRobots.getRobotElement(nodeLst, j);			        	
			//                String	valueid = rXMLTRobots.getRobotIDValue(info, "id");
			RobotStatus robot = robotsDefinidos.get(j);
			if (robot.getIdRobot().equals(robotId)) return robot ;       		
		}
		return null;
	}
	public ArrayList<RobotStatus> getTeamRobotStatus ( )throws Exception{
		ArrayList<RobotStatus> robots = new ArrayList<RobotStatus>();
		Iterator<RobotStatus> it = this.escenario.getRobotsWithIds().values().iterator();
		while(it.hasNext())
				robots.add(it.next());	
		return robots;
	}

}
