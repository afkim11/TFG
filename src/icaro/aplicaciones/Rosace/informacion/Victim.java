package icaro.aplicaciones.Rosace.informacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Victim implements Serializable{

       private String name;
       private volatile Coordinate coordinateVictim;
	   //Requirements. Robot abilities should cover these requirements to heal the victim.
       public List<Integer> RequiredCompetencies = new ArrayList<Integer>();  
       private int priority; //victim severity
       private int estimatedCost;
       private boolean isCostEstimated = false;

       public Victim(){
       } 
	
       //In our first scenario (1 injured group, 1 new injured) this constructor was used.
       public Victim(Coordinate coorVictim){
    	   this.coordinateVictim = coorVictim;
       } 

       public Victim(String name, Coordinate coorVictim, int priority, List<Integer> requirements){
           this.name = name;
    	   this.coordinateVictim = coorVictim;
    	   this.priority = priority;
   		   for(int i=0;i<requirements.size();i++){
   		      this.RequiredCompetencies.add(requirements.get(i));
   		   }
       }
       
       public synchronized String getName(){
    	   return this.name;
       }
       
       public synchronized void setName(String victimName){
    	   this.name = victimName;
       }
       
       public Coordinate getCoordinateVictim(){
    	   return this.coordinateVictim;
       }
       
       public void setCoordinateVictim(Coordinate coorVictim){
    	   this.coordinateVictim = coorVictim;
       }
       
   	   public synchronized int getPriority(){
		   return this.priority;
	   }

   	   public synchronized void setPriority(int priority){
		   this.priority = priority;
	   }

   	   public List<Integer> getRequirements(){
		   return this.RequiredCompetencies ;
	   }
   	   
   	   public void setRequirements(List<Integer> requirements){
   		   for(int i=0;i<requirements.size();i++){
    		      this.RequiredCompetencies.add(requirements.get(i));
    	   }   		   		 
	   }
           public synchronized int getEstimatedCost(){
		   return this.estimatedCost;
	   }

   	   public synchronized void setEstimatedCost(int costEstimated){
		   this.estimatedCost = costEstimated;
                   isCostEstimated = true;
	   }
   	   
           public synchronized boolean isCostEstimated(){
		  return isCostEstimated ;
	   }
   	   //Method for debugging
    @Override
   	   public String toString(){
   		   return "Victim: " + " name->" + this.getName() + " ; coordinate->" + this.getCoordinateVictim() + 
   		          " ; priority->" + this.getPriority() + " ; requirements->" + this.getRequirements();
   	   }
   	      	   
}
