/*
 *  
 *
 *
 *  All rights reserved
 */
package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces;

import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;

 import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;

/**
 * 
 *@author     F Garijo
 *@created    10 de enero  de 2009
 */

public interface ItfControlAgteReactivo extends InterfazGestion {

public void setGestorAReportar(ItfUsoAgenteReactivo itfUsoGestorAReportar);
public void procesarInfoControlAgteReactivo (InfoContEvtMsgAgteReactivo infoParaProcesar  );
public String getEstadoControlAgenteReactivo ( );
}
