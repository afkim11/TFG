package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

public class ReconocerTerreno extends Objetivo{
	private int idNum;
	
	public ReconocerTerreno(int idNum){
		super.setgoalId("Reconozco el terreno " + idNum);
		this.idNum = idNum;
	}

	
	public void setRobotId(int id){
		this.idNum = id;
	}
	public int getRobotId(){
		return this.idNum;
	}
}
