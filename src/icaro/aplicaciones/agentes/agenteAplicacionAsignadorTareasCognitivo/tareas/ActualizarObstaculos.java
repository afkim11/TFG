package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;

import java.util.ArrayList;

import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.LineaObstaculo;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeBloqueoObstaculo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class ActualizarObstaculos extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		//Añadir el obstaculo a la lista 
		LineaObstaculo obs = ((MensajeBloqueoObstaculo)this.params[0]).getObstaculo();
		((MensajeBloqueoObstaculo)this.params[0]).setObstaculoDescubierto(obs);
		//Mostrar un mensaje de que se añadió el obstáculo
		
	}
	
}
