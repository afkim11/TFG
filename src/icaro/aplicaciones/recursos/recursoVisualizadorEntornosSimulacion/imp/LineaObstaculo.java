package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class LineaObstaculo {
	private Coordinate ini;
	private Coordinate fin;
	private String valueid;
	
	public LineaObstaculo(Coordinate ini, Coordinate fin, String valueid)
	{
		this.ini = ini;
		this.fin = fin;
		this.valueid = valueid;
	}
	
	public Coordinate getCoordenadaIni(){
		return this.ini;
	}
	
	public Coordinate getCoordenadaFin(){
		return this.fin;
	}
}
