package icaro.aplicaciones.Rosace.calculoRutas;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class CoordenadaExtension{
	private Anterior direccion;
	private Coordinate coord;
	
	public CoordenadaExtension(Anterior a, Coordinate c){
		this.direccion = a;
		this.coord = c;
	}
	
	public Anterior getDir(){
		return this.direccion;
	}
	
	public Coordinate getCoor(){
		return this.coord;
	}
}
