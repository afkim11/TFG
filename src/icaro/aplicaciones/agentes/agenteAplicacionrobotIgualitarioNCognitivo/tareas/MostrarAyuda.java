/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;

import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Tarea;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.comunicacion.SimpleMessage;


/**
 * 
 * @author carf
 */
public class MostrarAyuda extends Tarea {

	@Override
	public void ejecutar(Object... arg0) {
		try {
			ItfUsoVisualizadorAcceso va = (ItfUsoVisualizadorAcceso) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ
					.obtenerInterfaz(
							NombresPredefinidos.ITF_USO + "VisualizacionAcceso");
			// va.mostrarAyuda();
			va.mostrarMensajeInformacion("Ayuda", "Ayuda....");
			MensajeSimple resultado = new MensajeSimple();
			resultado.setEmisor("Task:MostrarAyuda");
			resultado.setReceptor(NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"AccesoCognitivo");			resultado.setContenido(new String("AyudaMostrada"));
			this.getAgente().aceptaMensaje(resultado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
