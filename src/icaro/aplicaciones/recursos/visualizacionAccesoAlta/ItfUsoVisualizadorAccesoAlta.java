package icaro.aplicaciones.recursos.visualizacionAccesoAlta;

import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 *@author     
 *@created    
 */

public interface ItfUsoVisualizadorAccesoAlta extends ItfUsoVisualizadorAcceso{

        public void mostrarVisualizadorAcceso(String nombreAgente,String tipo) throws Exception;
	public void cerrarVisualizadorAcceso() throws Exception;
        public void mostrarVisualizadorAccesoAlta(String nombreUsuario) throws Exception;
	public void cerrarVisualizadorAccesoAlta() throws Exception;
  	public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
}