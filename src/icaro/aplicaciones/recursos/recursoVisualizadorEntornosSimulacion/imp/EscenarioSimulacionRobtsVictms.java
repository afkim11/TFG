/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

import icaro.aplicaciones.Rosace.informacion.RobotCapability;
import icaro.aplicaciones.Rosace.informacion.RobotStatus;
import icaro.aplicaciones.Rosace.informacion.Victim;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JLabel;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import fr.laas.openrobots.jmorse.components.destination.Coordinate;

/**
 *
 * @author FGarijo
 */
@Root(name = "escenarioSimulacion",strict=false)
public class EscenarioSimulacionRobtsVictms {
	@Element
	private String identEscenario;
	@Element
	private String modeloOrganizativo;
	@Element
	private int numRobots;
	@Element
	private int numVictimas;
	@ElementMap(entry="robots", key="key", attribute=true, inline=true)
	private Map<String, RobotStatus> infoRobots;     
	@ElementMap(entry="victimas", key="key", attribute=true, inline=true)
	//    private Map<String, Point> victmasLoc;
	private Map<String, Victim> infoVictimas;
	@ElementMap(entry="obstaculos", key="key", attribute=true, inline=true)
	private Map<String,LineaObstaculo> infoObstaculos;
	private String robotInicialId = "initRobot";
	private String victimInicialId = "initVictim";
	private String robotCapability = "camera";
	//    @Element
	//    private  List<String> robotCapabilities ;
	private volatile RobotStatus ultimoRobotDefinido ;
	private volatile Victim ultimaVictimaModificada;
	private volatile GestionEscenariosSimulacion migestor;
	private RobotCapability capabilityInicial;


	public  EscenarioSimulacionRobtsVictms (){
		migestor=null;
		
		infoRobots = new HashMap<String,RobotStatus >();
		//        infoRobots.put(robotInfo.getIdRobot(),robotInfo);
		//        infoRobots.put(robotInicialId, robotInfo);


		//        victimasLoc = new HashMap<String, Point>();
		infoVictimas = new HashMap<String, Victim>();
		infoObstaculos=new HashMap<String,LineaObstaculo>();


	}
	public void  initEscenario(){
		modeloOrganizativo = "SinDefinir";
		numRobots=0;
		numVictimas= 0;
		ultimoRobotDefinido = new RobotStatus();
		capabilityInicial = new RobotCapability();
		capabilityInicial.setNombre("radar");
		capabilityInicial.setDescripcion("radar del robot"); 
		ultimoRobotDefinido.setRobotCapability(capabilityInicial);
		ultimoRobotDefinido.setIdRobot(robotInicialId);
		ultimaVictimaModificada = new Victim (victimInicialId);
		//        ultimaVictimaModificada.setName(victimInicialId);
		//        this.addRoboInfo(robotInicialId, ultimoRobotDefinido);
		this.infoRobots.put(robotInicialId, ultimoRobotDefinido);

		//        victimasLoc.put(victimInicialId, new Point(0,0));
		this.infoVictimas.put(victimInicialId, ultimaVictimaModificada);

	}
	public synchronized void setGestorEscenarios( GestionEscenariosSimulacion gestorEsc){
		migestor=gestorEsc;     
	}
	public synchronized void setNumVictimas( int numVict){
		numVictimas=numVict;
	}
	@XmlElement (name = "numVictimas")
	public synchronized int  getNumVictimas( ){
		return numVictimas;
	}
	public synchronized void setNumRobots( int numRobot){
		numRobots=numRobot;
	}
	@XmlElement (name = "numRobots")
	public synchronized int  getNumRobots( ){
		return numRobots;
	}
	public void setmodeloOrganizativo( String modeloOrg){
		modeloOrganizativo=modeloOrg;
	}
	@XmlElement (name = "modeloOrganizativo")
	public String  getmodeloOrganizativo( ){
		return modeloOrganizativo;
	}
	public synchronized void addVictimLoc (String idVictima,Point puntoEnEscenario ){

		if(ultimaVictimaModificada!=null &&ultimaVictimaModificada.getName().equalsIgnoreCase(idVictima))ultimaVictimaModificada.setLocPoint(puntoEnEscenario);
		else {
			ultimaVictimaModificada = infoVictimas.get(idVictima);
			if(ultimaVictimaModificada == null){
				if(numVictimas==0)infoVictimas.remove(victimInicialId);
				ultimaVictimaModificada = new  Victim(idVictima);
				infoVictimas.put (idVictima,ultimaVictimaModificada );
				numVictimas++;
			}
			ultimaVictimaModificada.setLocPoint(puntoEnEscenario);
		}

	}
	public void eliminaVictim (String idVictima){
		if(infoVictimas.containsKey(idVictima)){
			infoVictimas.remove(idVictima);
			numVictimas--;
		}
	}
	public synchronized void addRoboInfo (String idRobot,RobotStatus infoRobot ){
		if(numRobots==0)
			infoRobots.remove(robotInicialId);
		//remove(robotInicialId);
		this.infoRobots.put(idRobot, infoRobot);
		numRobots++;
	}
	public void addRoboLoc (String idRobot,Point robotLoc ){

		if (ultimoRobotDefinido!=null &&ultimoRobotDefinido.getIdRobot().equals(idRobot))ultimoRobotDefinido.setLocPoint(robotLoc);
		else{
			ultimoRobotDefinido = infoRobots.get(idRobot);
			if (ultimoRobotDefinido==null){
				if(numRobots==0)infoRobots.remove(robotInicialId);
				ultimoRobotDefinido = new RobotStatus();
				ultimoRobotDefinido.setIdRobot(idRobot);
				ultimoRobotDefinido.setRobotCapability(capabilityInicial);
				infoRobots.put(idRobot, ultimoRobotDefinido);   
				numRobots++;
			}
			ultimoRobotDefinido.setLocPoint(robotLoc);
		}


	}

	public void eliminaRobot (String idRobot){
		if(infoRobots.containsKey(idRobot)){
			infoRobots.remove(idRobot);
			numRobots--;
		}   
	}
	public void eliminarEntidad (String idEntidad){
		if(idEntidad.contains("Robot")){
			if( infoRobots.containsKey(idEntidad)){
				infoRobots.remove(idEntidad);
				numRobots--;
			}else if(numRobots>0&&idEntidad.startsWith("init"))infoRobots.remove(idEntidad);

		}
		else if(idEntidad.contains("Victim")){
			if( infoVictimas.containsKey(idEntidad)){
				infoVictimas.remove(idEntidad);
				numVictimas--;
			}else if(idEntidad.startsWith("init"))infoVictimas.remove(idEntidad);
			if(numVictimas==0)infoVictimas.put(victimInicialId, new Victim(victimInicialId));
		}
	}
	public Set getSetVictims (){
		if(numVictimas>0)infoVictimas.remove(victimInicialId);
		return this.infoVictimas.entrySet();
	}
	public synchronized Map<String, Victim> getVictims (){
		if(numVictimas>0)infoVictimas.remove(victimInicialId);
		return this.infoVictimas;
	}

	public Set getRobots (){
		if(numRobots>0)infoRobots.remove(robotInicialId);
		return this.infoRobots.entrySet();
	}

	public Point getVictimLoc (String idVictima){
		return this.infoVictimas.get(idVictima).getLocPoint();
	}
	public RobotStatus getRobotInfo (String idRobot){
		return this.infoRobots.get(idRobot);
	}
	public void setIdentEscenario(String escenarioId) {
		identEscenario = escenarioId;
	}
	public synchronized String getIdentEscenario() {
		//        throw new UnsupportedOperationException("Not supported yet."); 
		if (identEscenario==null&& migestor!=null)return migestor.getIdentEscenario(modeloOrganizativo, numRobots, numVictimas);
		return this.identEscenario;
	}
	public synchronized void  setIdentificadorNormalizado(){
		// se lo pide al gestor para que verifique posibles conflictos
		this.identEscenario= migestor.getIdentEscenario(modeloOrganizativo, numRobots, numVictimas);

	} 
	public ArrayList<String> getListIdentsRobots(){
		if (numRobots<=0)return null;
		else{
			infoRobots.remove(robotInicialId);
			ArrayList<String> listaIdents = new ArrayList<String>(); 
			Object[] identRobots= infoRobots.keySet().toArray();
			for(int i=0;i<infoRobots.size();i++){
				listaIdents.add((String)identRobots[i]);
			}

			return listaIdents;
		}
	}
	public ArrayList<String> getListIdentsVictims(){
		if (numVictimas<=0)return null;
		else{
			infoVictimas.remove(victimInicialId);
			ArrayList<String> listaIdents = new ArrayList<String>(); 
			Object[] identVictimas= infoVictimas.keySet().toArray();
			for(int i=0;i<infoVictimas.size();i++){
				listaIdents.add((String)identVictimas[i]);
			}

			return listaIdents;
		}
	}
	public ArrayList<LineaObstaculo> getListObstacles(){
		if(infoObstaculos.size()<=0)return null;
		else {
			ArrayList<LineaObstaculo> listaObstaculos=new ArrayList<LineaObstaculo>();
			Object[] obstaculos= infoObstaculos.values().toArray();
			for(int i=0;i<infoObstaculos.size();i++){
				listaObstaculos.add((LineaObstaculo)obstaculos[i]);
			}
			return listaObstaculos;
		}    	
	}

	public void renombrarIdentRobts(ArrayList identList) {
		String identNuevo; int i= 0;
		RobotStatus robotInfo = null;
		Object[] identRobots= infoRobots.keySet().toArray();
		for( i=0; i<identList.size();i++){
			robotInfo = infoRobots.get ( identRobots[i]);
			infoRobots.remove ( identRobots[i]);  
			identNuevo = (String)identList.get(i);
			robotInfo.setIdRobot(identNuevo);  
			infoRobots.put(identNuevo,robotInfo );           
		}
	}
	public void addObstaculo(LineaObstaculo obstaculo){
		infoObstaculos.put(obstaculo.getId(), obstaculo);    	
	}
	

	public Map<String, RobotStatus> getRobotsWithIds() {
		return this.infoRobots;
	}
}