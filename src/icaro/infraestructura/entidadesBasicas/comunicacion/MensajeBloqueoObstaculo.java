/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.entidadesBasicas.comunicacion;

import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.LineaObstaculo;
/**
 *
 * @author Francisco J Garijo
 */
import icaro.infraestructura.entidadesBasicas.*;
import javafx.scene.shape.Line;

import java.io.Serializable;
import java.util.ArrayList;
public class MensajeBloqueoObstaculo extends MensajeSimple implements Serializable {


	private static final long serialVersionUID = 1624889937599726865L;
   /* private Object contenido;
	private Object emisor;
	private Object receptor;
	*/
	private LineaObstaculo obstaculo;
    private boolean iscontentColection= false;
    private ArrayList<LineaObstaculo> listaObstaculos= null;

    public MensajeBloqueoObstaculo() {

    }

	public MensajeBloqueoObstaculo(Object contenido, Object emisor, Object receptor, LineaObstaculo obstaculo, ArrayList<LineaObstaculo> lObs) {
            this.contenido = contenido;
		this.emisor=emisor;
		this.receptor = receptor;
		this.obstaculo = obstaculo;
		this.listaObstaculos = lObs;
	}

	public Object getEmisor() {
		return emisor;
	}

        
	public void setEmisor(Object emisor) {
            this.emisor = emisor;
	}


	public Object getReceptor () {
		return receptor;
	}

	public void setReceptor(Object receptor) {
            this.receptor = receptor;
	}

	public void setContenido(Object contenido) {
		this.contenido = contenido;
	}
        public boolean isContenidoColection(){
            return iscontentColection;
        }

    public String toString(){
    	return "Emisor: "+emisor+", Receptor: "+receptor;
    }
    
    public LineaObstaculo getObstaculo(){
    	return this.obstaculo;
    }
    
    public void setObstaculo(LineaObstaculo obs){
    	this.obstaculo = obs;
    }
    
    public ArrayList<LineaObstaculo> getObstaculosDescubiertos(){
    	return this.listaObstaculos;
    }
    
    public void setObstaculoDescubierto(LineaObstaculo obs){
    	this.listaObstaculos.add(obs);
    }

}