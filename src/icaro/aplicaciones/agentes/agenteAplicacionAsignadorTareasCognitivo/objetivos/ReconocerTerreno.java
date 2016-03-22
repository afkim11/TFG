package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

public class ReconocerTerreno extends Objetivo{
	private String robotIdEncargado = null;
	
	public ReconocerTerreno(){
		super.setgoalId("Reconocer terreno");
	}
	public void setRobotEncargado(String robotId){
		this.robotIdEncargado = robotId;
	}
	public String getRobotEncargado(){
		return this.robotIdEncargado;
	}
}
