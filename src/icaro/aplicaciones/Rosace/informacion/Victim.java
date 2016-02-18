package icaro.aplicaciones.Rosace.informacion;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class Victim implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Element
	private String name;
	@Element
	private volatile Coordinate coordinateVictim;
	//Requirements. Robot abilities should cover these requirements to heal the victim.
	@ElementList(entry="competency")
	public List<Integer> RequiredCompetencies = new ArrayList<Integer>(); 
	@Element
	private int priority; //victim severity
	private int estimatedCost;
	private boolean isRescued = false;
	private boolean isCostEstimated = false;

	public Victim(){
	} 

	//In our first scenario (1 injured group, 1 new injured) this constructor was used.
	public Victim(Coordinate coorVictim){
		this.coordinateVictim = coorVictim;
	}
	public Victim(String nombre){
		this.name=nombre;
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

	public synchronized boolean getRescued(){
		return this.isRescued;
	}

	public synchronized void setRescued(){
		this.isRescued = true;
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

	public void setLocPoint(Point punto) {
		// TODO Auto-generated method stub
		this.coordinateVictim=new Coordinate(punto.getX(),punto.getY(),0.5);
	}

	public Point getLocPoint() {
		// TODO Auto-generated method stub
		return new Point((int)this.coordinateVictim.getX(),(int)this.coordinateVictim.getY());
	}

}
