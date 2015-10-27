/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.recursos.visualizacionAgenteMasterIA;

import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 *
 * @author Arturo Mazón
 */
public interface ItfUsoVisualizacionAgenteMasterIA extends ItfUsoRecursoSimple{
    public void setAgenteControlador (String nombreAgente);
    public void mostrarMensaje (String usuario, String mensaje);
    public void mostrarVentanaMasterIA (String nombreAgente);
}
