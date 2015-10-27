package icaro.aplicaciones.Rosace.utils;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.Victim;

import java.util.ArrayList;

//Esta clase no se utiliza en las tareas de los robots
//RobotStatusUtils se utiliza en las clases siguientes
//     InfoEquipo localizada en el paquete icaro.aplicaciones.Rosace.informacion
//
public class RobotStatusUtils {

    private String id;
    private int engergylevel;
    private Coordinate coordinate;
    private float healrange;
    private ArrayList victims;
    
    public RobotStatusUtils(String id, int engergylevel, Coordinate coordinate, float healrange){
    	this.id = id;
    	this.engergylevel = engergylevel;
    	this.coordinate = coordinate;
    	this.healrange = healrange;
        this.victims = null;       
    }
    
    public RobotStatusUtils(String id){
    	this.id = id;
    	this.engergylevel = 0;
    	this.coordinate = null;
    	this.healrange = 0;
        this.victims = null;       
    }
    
    public String getRobotID(){
    	return this.id;
    }
    
    public void setRobotID(String id){
    	 this.id = id;
    }
    
    public int getRobotEnergyLevel(){
    	return this.engergylevel;
    }
    
    public void setRobotEnergyLevel(int engergylevel){
    	 this.engergylevel = engergylevel;
    }
	
    public Coordinate getRobotInitialCoordinate(){
    	return this.coordinate;
    }
    
    public void setRobotCoordinate(Coordinate coordinate){
    	 this.coordinate =  coordinate;
    }
    
    public float getRobotHealRange(){
    	return this.healrange;
    }
    
    public void setRobotHealRange(float healrange){
    	this.healrange = healrange ;
    }
    
    public void addVictim (Victim vict){
         victims.add(vict);
    }
    
    public ArrayList getVictims (){
        return victims;
    }
    
    public String toString(){
    	return "Robot: id->" + this.getRobotID() + 
    		   " ; engergylevel->" + this.getRobotEnergyLevel() +
    	       " ; coordinate->" +  this.getRobotInitialCoordinate() +
    	       " ; healrange->" + this.getRobotHealRange();
    }
}
