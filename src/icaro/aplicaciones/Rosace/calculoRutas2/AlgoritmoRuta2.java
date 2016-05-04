package icaro.aplicaciones.Rosace.calculoRutas2;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.simpleframework.xml.core.Persister;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.EscenarioSimulacionRobtsVictms;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.LineaObstaculo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;

public class AlgoritmoRuta2 {


	private static int escenarioWidth = 1100;
	private static int escenarioHeight = 700;
	//private static int escenarioWidth = 50;
	//private static int escenarioHeight = 50;
	private int grid[][];
	private Coordinate coordIniciales,coordDestino;
	private static ArrayList<LineaObstaculo> obstaculos;

	public AlgoritmoRuta2(Coordinate target,Coordinate source){
		
			this.coordIniciales = source;
			this.coordDestino = target;
			this.grid = new int[escenarioWidth][escenarioHeight];
			inicializaObstaculos();
			inicializaGrid();
	}
	public ArrayList<Coordinate> iniciarCalculoRuta(){
		ArrayList<Coordinate> ruta = null;
		LinkedList<Coordinate> cola = new LinkedList<Coordinate>();
		Coordinate auxCoor;
		cola.add(coordIniciales);
		while(!cola.isEmpty()){
			auxCoor = cola.removeFirst();
			if(auxCoor.equals(this.coordDestino)){
				ruta = generaRuta();
				break;
			}
			generaNuevosNodos(cola,auxCoor);
		}

		return ruta;
	}





	private ArrayList<Coordinate> generaRuta() {
		ArrayList<Coordinate> ruta = new ArrayList<Coordinate>();
		int x = (int)this.coordDestino.getX(),y = (int)this.coordDestino.getY();

		Coordinate aux = this.coordDestino;

		while(x != (int)this.coordIniciales.getX() || y != (int)this.coordIniciales.getY()){
			x = (int)aux.getX();
			y = (int)aux.getY();
			ruta.add(0, new Coordinate(x,y,0.5));
			aux = getMinimoAlrededor(aux);
		}


		return ruta;
	}
	private Coordinate getMinimoAlrededor(Coordinate auxCoor) {
		int minx = (int)auxCoor.getX(),miny = (int)auxCoor.getY();
		int x = (int) auxCoor.getX(),y = (int) auxCoor.getY();

		for(int i = -1;i<2;i=i+2){
			if(x+i >=0 && y>=0 && x+i<escenarioWidth && y<escenarioHeight){
				if(this.grid[x+i][y] >-1 && this.grid[x+i][y] < this.grid[minx][miny] ){
					minx = x+i;
					miny = y;
				}
			}
		}
		for(int i = -1;i<2;i=i+2){
			if(x >=0 && y+i>=0 && x<escenarioWidth && y+i<escenarioHeight){
				if(this.grid[x][y+i] >-1 && this.grid[x][y+i] < this.grid[minx][miny]){
					minx = x;
					miny = y+i;
				}
			}
		}




		return new Coordinate(minx,miny,0.5);
	}
	private void generaNuevosNodos(LinkedList<Coordinate> cola, Coordinate auxCoor) {
		int x = (int) auxCoor.getX(),y = (int) auxCoor.getY();

		for(int i = -1;i<2;i=i+2){
			if(x+i >=0 && y>=0 && x+i<escenarioWidth && y<escenarioHeight){
				if(this.grid[x+i][y] ==-1 || this.grid[x+i][y] ==-3){
					this.grid[x+i][y] = this.grid[x][y] + 1;
					cola.add(new Coordinate(x+i,y,0.5));
				}
			}
		}
		for(int i = -1;i<2;i=i+2){
			if(x >=0 && y+i>=0 && x<escenarioWidth && y+i<escenarioHeight){
				if(this.grid[x][y+i] ==-1 || this.grid[x][y+i] ==-3){
					this.grid[x][y+i] = this.grid[x][y] + 1;
					cola.add(new Coordinate(x,y+i,0.5));
				}
			}
		}
	}




	/**
	 *  1-> blanco
	 * -2-> obstaculo
	 *  0-> inicio
	 * -3-> Destino
	 */
	private void inicializaGrid(){
		for(int i=0;i<escenarioWidth;i++)
			for(int j=0;j<escenarioHeight;j++)
				this.grid[i][j] = -1;
		this.grid[(int)coordIniciales.x][(int)coordIniciales.y] = 0;
		this.grid[(int)coordDestino.x][(int)coordDestino.y] = -3;

		for(LineaObstaculo obs:obstaculos){
			int iniX = (int)obs.getCoordenadaIni().x,iniY = (int)obs.getCoordenadaIni().y;
			int finX = (int)obs.getCoordenadaFin().x,finY = (int)obs.getCoordenadaFin().y;

			if(iniX == finX){
				for(int i =iniY;i<=finY;i++){
					this.grid[iniX][i] = -2;
				}
			}
			else{
				for(int i =iniX;i<=finX;i++){
					this.grid[i][iniY] = -2;
				}
			}
		}

	}
	private void inicializaObstaculos(){
		try {

			String rutaFicheroEscenario = NombresPredefinidos.RUTA_PERSISTENCIA_ESCENARIOS + VocabularioRosace.rutaEscenario;
			EscenarioSimulacionRobtsVictms escenario = new Persister().read(EscenarioSimulacionRobtsVictms.class,new File(rutaFicheroEscenario),false);
			obstaculos = escenario.getListObstacles();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*private void mostrarGrid(){
		for(int i=0;i<escenarioWidth;i++){
			for(int j=0;j<escenarioHeight;j++)
				System.out.print(this.grid[i][j] + " ");
			System.out.println("");
		}
		System.out.println("");
		System.out.println("");
	}
	public static void main(String[] args){

		AlgoritmoRuta2 alg = new AlgoritmoRuta2(new Coordinate(1.0,1.0,0.5),new Coordinate(350.0,1000.0,0.5));		
		alg.iniciarCalculoRuta();

	}*/


}
