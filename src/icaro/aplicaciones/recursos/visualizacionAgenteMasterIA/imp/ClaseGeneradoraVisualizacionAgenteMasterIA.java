/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.recursos.visualizacionAgenteMasterIA.imp;

import icaro.aplicaciones.recursos.visualizacionAgenteMasterIA.ItfUsoVisualizacionAgenteMasterIA;
import icaro.aplicaciones.recursos.visualizacionAgenteMasterIA.imp.gui.ventanaMasterIA;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;

/**
 *
 * @author Arturo Mazón
 */
public class ClaseGeneradoraVisualizacionAgenteMasterIA extends ImplRecursoSimple implements ItfUsoVisualizacionAgenteMasterIA{
    ventanaMasterIA v;
     private String agenteControlador;
    public ClaseGeneradoraVisualizacionAgenteMasterIA (String id) throws Exception {
        super (id);
//        v = new ventanaMasterIA();
//        chat = new TChatter();
////        v = new ventanaChat(false);
////        vc = new ventanaAccesoChat();
//
//        agenteControlador = "Indefinido";
        this.setAgenteControlador("");
    }
    public ventanaMasterIA getVentana(){
        return v;
    }
    public void setAgenteControlador(String nombreAgente) {
        agenteControlador = nombreAgente;
    }
    public void mostrarVentanaMasterIA(String nombreAgente) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mostrarMensaje(String usuario, String mensaje) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
