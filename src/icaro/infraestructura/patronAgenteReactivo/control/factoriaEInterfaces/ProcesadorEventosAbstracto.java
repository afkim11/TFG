package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces;

import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;

/**
 * 
 *@author    F Garijo
 */

 public abstract class ProcesadorEventosAbstracto extends Thread implements ItfControlAgteReactivo {
// public abstract class ProcesadorEventosAbstracto extends java.lang.Thread {

	public ProcesadorEventosAbstracto(String string) {
		super(string);
	}

}