/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.JLabel;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

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
    private Map<String, Point> robotsLoc;     
    @ElementMap(entry="victimas", key="key", attribute=true, inline=true)
    private Map<String, Point> victimasLoc;
    private String robotInicialId = "initRobot";
    private String victimInicialId = "initVictim";
    private volatile GestionEscenariosSimulacion migestor;
     

public  EscenarioSimulacionRobtsVictms (){
    migestor=null;
    modeloOrganizativo = "SinDefinir";
        numRobots=0;
        numVictimas= 0;
        robotsLoc = new HashMap<String, Point>();
        robotsLoc.put(robotInicialId, new Point(0,0));
        victimasLoc = new HashMap<String, Point>();
        victimasLoc.put(victimInicialId, new Point(0,0));
}
public synchronized void setGestorEscenarios( GestionEscenariosSimulacion gestorEsc){
    migestor=gestorEsc;     
}
public void setNumVictimas( int numVict){
    numVictimas=numVict;
}
@XmlElement (name = "numVictimas")
public int  getNumVictimas( ){
    return numVictimas;
}
public void setNumRobots( int numRobot){
    numRobots=numRobot;
}
@XmlElement (name = "numRobots")
public int  getNumRobots( ){
    return numRobots;
}
public void setmodeloOrganizativo( String modeloOrg){
    modeloOrganizativo=modeloOrg;
}
@XmlElement (name = "modeloOrganizativo")
public String  getmodeloOrganizativo( ){
    return modeloOrganizativo;
}
public void addVictimLoc (String idVictima,Point puntoEnEscenario ){
    if(numVictimas==0)victimasLoc.remove(victimInicialId);
    this.victimasLoc.put(idVictima, puntoEnEscenario);
    numVictimas++;
}
public void eliminaVictim (String idVictima){
    if(victimasLoc.containsKey(idVictima)){
        victimasLoc.remove(idVictima);
        numVictimas--;
    }
}
public void addRoboLoc (String idRobot,Point puntoEnEscenario ){
    if(numRobots==0)robotsLoc.remove(robotInicialId);
    this.robotsLoc.put(idRobot, puntoEnEscenario);
    numRobots++;
}
public void eliminaRobot (String idRobot){
    if(robotsLoc.containsKey(idRobot)){
        robotsLoc.remove(idRobot);
        numRobots--;
    }   
}
public void eliminarEntidad (String idEntidad){
    if(idEntidad.contains("Robot")){
           if( robotsLoc.containsKey(idEntidad)){
            robotsLoc.remove(idEntidad);
            numRobots--;
           }else if(idEntidad.startsWith("init"))robotsLoc.remove(idEntidad);
            if(numRobots==0)robotsLoc.put(robotInicialId, new Point(0,0));
           }
    else if(idEntidad.contains("Victim")){
           if( victimasLoc.containsKey(idEntidad)){
            victimasLoc.remove(idEntidad);
            numVictimas--;
           }else if(idEntidad.startsWith("init"))victimasLoc.remove(idEntidad);
            if(numVictimas==0)victimasLoc.put(victimInicialId, new Point(0,0));
        }
}
public Set getVictims (){
    if(numVictimas>0)victimasLoc.remove(victimInicialId);
    return this.victimasLoc.entrySet();
}
public Set getRobots (){
    if(numRobots>0)robotsLoc.remove(robotInicialId);
    return this.robotsLoc.entrySet();
}

public Point getVictimLoc (String idVictima){
    return this.victimasLoc.get(idVictima);
}
public Point getRobotLoc (String idRobot){
    return this.robotsLoc.get(idRobot);
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
   public ArrayList getListIdentsRobots(){
       if (numRobots<=0)return null;
       else{
          robotsLoc.remove(robotInicialId);
          ArrayList listaIdents = new ArrayList(); 
          Object[] identRobots= robotsLoc.keySet().toArray();
          for(int i=0; i<identRobots.length; i++){
           listaIdents.add(identRobots[i]);
          }
          return listaIdents;
       }
   }
}