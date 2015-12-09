package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JPanel;

public class JPanelObstaculo extends JPanel{
	
	private ArrayList<LineaObstaculo> obstaculos;
	 
	public JPanelObstaculo(ArrayList<LineaObstaculo> obs){
		this.obstaculos = obs;
	}
	public void paint(Graphics g){
		super.paint(g);
		for(LineaObstaculo obs : obstaculos){
			g.drawLine((int)obs.getCoordenadaIni().getX(), (int)obs.getCoordenadaIni().getY(), (int)obs.getCoordenadaFin().getX(), (int)obs.getCoordenadaFin().getY());
		}
		
		
	}
	
}
