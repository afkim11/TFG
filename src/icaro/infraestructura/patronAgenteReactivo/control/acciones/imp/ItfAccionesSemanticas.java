package icaro.infraestructura.patronAgenteReactivo.control.acciones.imp;

import icaro.infraestructura.patronAgenteReactivo.control.acciones.*;




/**
 * @author      Felipe Polo
 * @created     3 de Diciembre de 2007
 */

public interface ItfAccionesSemanticas {
	
	public void setAccion(String accion);
	public void setParametros(Object[] parametros);
	/**
	 * @uml.property  name="accionesSemanticas"
	 * @uml.associationEnd  
	 */
	public AccionesSemanticasAgenteReactivo getAccionesSemanticas();
	/**
	 * @param accionesSemanticas
	 * @uml.property  name="accionesSemanticas"
	 */
	public void setAccionesSemanticas(AccionesSemanticasAgenteReactivo accionesSemanticas);
	public void ejecutarAccion(String accion, Object[] parametros, boolean modoBloqueante)throws ExcepcionEjecucionAcciones;
	public void run();

}
