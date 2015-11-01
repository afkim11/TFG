package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class LineaObstaculo {
	private Coordinate ini;
	private Coordinate fin;
	
	public LineaObstaculo(Coordinate ini, Coordinate fin)
	{
		this.ini = ini;
		this.fin = fin;
	}
	
	public Coordinate getCoordenadaIni(){
		return this.ini;
	}
	
	public Coordinate getCoordenadaFin(){
		return this.fin;
	}
}
