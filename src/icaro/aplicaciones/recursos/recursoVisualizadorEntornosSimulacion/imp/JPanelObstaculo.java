package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JPanel;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class JPanelObstaculo extends JPanel{
	
	private ArrayList<LineaObstaculo> obstaculos;
	private ArrayList<Coordinate> rastroExploracion;
	public JPanelObstaculo(ArrayList<LineaObstaculo> obs){
		this.obstaculos = obs;
		rastroExploracion = new ArrayList<Coordinate>();
	}
	public synchronized void paint(Graphics g){
		super.paint(g);
		for(LineaObstaculo obs : obstaculos){
			g.drawLine((int)obs.getCoordenadaIni().getX(), (int)obs.getCoordenadaIni().getY(), (int)obs.getCoordenadaFin().getX(), (int)obs.getCoordenadaFin().getY());
		}
		for(Coordinate coor : rastroExploracion){
			g.drawRect((int)coor.getX(), (int)coor.getY(), 1, 1);
		}
		
	}
	
	public synchronized void addCoordRastro(Coordinate coord){
		this.rastroExploracion.add(coord);
	}
	
}
