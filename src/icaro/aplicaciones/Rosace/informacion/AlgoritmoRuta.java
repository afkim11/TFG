package icaro.aplicaciones.Rosace.informacion;

 import java.util.ArrayList;
 import java.util.Comparator;
 import java.util.PriorityQueue;

import com.sun.swing.internal.plaf.synth.resources.synth;

import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.LineaObstaculo;
 import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.VisorEscenariosRosace;
 /**
  * Esta clase implementa un algoritmo de vuelta atras con heuristica para resolver la ruta minima 
  * entre 2 coordenadas teniendo en cuenta los posibles obstaculos esquivandolos. Para ello se tiene
  * en cuenta las coordenadas ya visitadas y la posicion de la que viene para no repetir caminos.
  *  
  * @author Luis Garcia Terriza y Sergio Moreno de Pradas
  *
  */
 public class AlgoritmoRuta {

	private static final int width = 27;
 	private static final int height = 40;
 	
 	private Coordinate coordenadasDestino;
 	private Coordinate coordenadasIniciales;
 	
 	private int contador; //Este contador se encarga de que si el algoritmo no ha encontrado una solucion en cierto numero de pasos, finalice la ejecucion. Se supone en tal caso que no hay solucion valida.
 	private static final int limiteRecursividad=4500;
 	private static ArrayList<LineaObstaculo> obstaculos;
 	
 	
 	
 	
 	public AlgoritmoRuta(Coordinate destino,Coordinate iniciales){
 		this.coordenadasDestino=destino;
 		this.coordenadasIniciales=iniciales;
 		this.contador=0;
 		actualizarObstaculos();
 	}
 	/**
 	 * Algoritmo de vuelta atras con heuristica que se encarga de comprobar si hay una solucion(un camino entre dos puntos).
 	 * Para limitar su calculabilidad, se hace uso de un contador de recursiones y de un limite de recursividad. 
 	 * @param visitados
 	 * @param coordenadasActuales
 	 * @param anterior
 	 * @param rutaHastaAhora
 	 * @return
 	 */
 	public ArrayList<Coordinate> calculaRuta(boolean[][] visitados, Coordinate coordenadasActuales,int anterior,ArrayList<Coordinate> rutaHastaAhora) {
 		this.contador++;
 		if(this.contador>=limiteRecursividad)return null;
 		if(rutaHastaAhora.get(rutaHastaAhora.size()-1).equals(this.coordenadasDestino)){
 			return rutaHastaAhora;
 		}
 		else{
 			PriorityQueue<Coordinate> colaNodos=estimaCoste(visitados,coordenadasActuales,anterior); 
 			while(!colaNodos.isEmpty() && this.contador<limiteRecursividad){
 				Coordinate coor=colaNodos.poll();
 				int x=(int)coor.getX(),y=(int)coor.getY();
 				visitados[x][y]=true;
 				rutaHastaAhora.add(coor);
 				ArrayList<Coordinate> posible_sol=calculaRuta(visitados,coor,calculaAnterior(coordenadasActuales,coor),rutaHastaAhora);
 				if(posible_sol!=null)return posible_sol;			
 				rutaHastaAhora.remove(rutaHastaAhora.size()-1);
 				visitados[x][y]=false;
 			}
 		}
 		return null;
 	}
 	/**
 	 * En funcion de la coordenada anterior a la que estoy, se codifica mediante un numero la posicion anterior.
 	 * Este esquema muestra como se han representado los numeros: por ejemplo, en la posicion X, si el numero que devuelve es "2", entonces viene de arriba.
 	 * 
 	 * 123
 	 * 4X5
 	 * 678 
 	 * 
 	 */
 	private int calculaAnterior(Coordinate anterior,Coordinate siguiente){
 		int x1=(int)anterior.getX(),y1=(int)anterior.getY(),x2=(int)siguiente.getX(),y2=(int)siguiente.getY();
 		int restaX = x2 - x1, restaY = y2 - y1;
 		if (restaX == -1 && restaY == -1) return 8;
 		else if (restaX == 0 && restaY == -1) return 7;
 		else if (restaX == 1 && restaY == -1) return 6;
 		else if (restaX == -1 && restaY == 0) return 5;
 		else if (restaX == 1 && restaY == 0) return 4;
 		else if (restaX == -1 && restaY == 1) return 3;
 		else if (restaX == 0 && restaY == 1) return 2;
 		else if (restaX == 1 && restaY == 1) return 1;
 		else return -1;
 		
 		
 		
 	}
 	/**
 	 * Este metodo se encarga de calcular los posibles nodos siguientes desde una posicion sin tener en cuenta las coordenadas ya visitadas y la posicion de la que se viene.
 	 * @param visitados
 	 * @param coordinadasActuales
 	 * @param anterior
 	 * @return
 	 */
 	private PriorityQueue<Coordinate> estimaCoste(boolean[][] visitados, Coordinate coordinadasActuales, int anterior) {
 		PriorityQueue<Coordinate> cola=new PriorityQueue<Coordinate>(new Comparator<Coordinate>(){
 			@Override
 			public int compare(Coordinate o1, Coordinate o2){
 				double coste1=Coste.distanciaC1toC2(o1,coordenadasDestino),coste2=Coste.distanciaC1toC2(o2,coordenadasDestino);
 				if( coste1<coste2 )return -1;
 				else if(coste1 > coste2)return 1;
 				return 0;
 
 
 			}});
 		for(int i=1;i<=8;i++){
 			if(i!=anterior){
 				if(i==1){
 					int x=(int)coordinadasActuales.getX()-1;
 					int y=(int)coordinadasActuales.getY()-1;
 					Coordinate coor=new Coordinate(x,y,0.5);
 					if(!visitados[x][y] && !checkLimites(coor))
 						cola.add(coor);
 
 				}
 				else if(i==2){
 					int x=(int)coordinadasActuales.getX();
 					int y=(int)coordinadasActuales.getY()-1;
 					Coordinate coor=new Coordinate(x,y,0.5);
 					if(visitados[x][y]==false && !checkLimites(coor))
 						cola.add(coor);
 
 				}
 				else if(i==3){
 					int x=(int)coordinadasActuales.getX()+1;
 					int y=(int)coordinadasActuales.getY()-1;
 					Coordinate coor=new Coordinate(x,y,0.5);
 					if(visitados[x][y]==false && !checkLimites(coor))
 						cola.add(coor);
 
 				}
 				else if(i==4){
 					int x=(int)coordinadasActuales.getX()-1;
 					int y=(int)coordinadasActuales.getY();
 					Coordinate coor=new Coordinate(x,y,0.5);
 					if(visitados[x][y]==false && !checkLimites(coor))
 						cola.add(coor);
 
 				}
 				else if(i==5){
 					int x=(int)coordinadasActuales.getX()+1;
 					int y=(int)coordinadasActuales.getY();
 					Coordinate coor=new Coordinate(x,y,0.5);
 					if(visitados[x][y]==false && !checkLimites(coor))
 						cola.add(coor);
 
 				}
 				else if(i==6){
 					int x=(int)coordinadasActuales.getX()-1;
 					int y=(int)coordinadasActuales.getY()+1;
 					Coordinate coor=new Coordinate(x,y,0.5);
 					if(visitados[x][y]==false && !checkLimites(coor))
 						cola.add(coor);
 
 				}
 			else if(i==7){
 					int x=(int)coordinadasActuales.getX();
 					int y=(int)coordinadasActuales.getY()+1;
 					Coordinate coor=new Coordinate(x,y,0.5);
 					if(visitados[x][y]==false && !checkLimites(coor))
 						cola.add(coor);
 
 				}
 				else if(i==8){
 					int x=(int)coordinadasActuales.getX()+1;
 					int y=(int)coordinadasActuales.getY()+1;
 					Coordinate coor=new Coordinate(x,y,0.5);
 					if(visitados[x][y]==false && !checkLimites(coor))
 						cola.add(coor);
 
 				}
 			}
 		}		
 		return cola;
 	}
 	
 	public static boolean checkLimites(Coordinate coor){
 		boolean obs = false;
 		for(int i = 0;(i < height && !obs); i++){
	 			Coordinate c1 = new Coordinate(coor.getX()+i, coor.getY(), coor.getZ());
	 			Coordinate c2 = new Coordinate(coor.getX()+i, coor.getY()+width, coor.getZ());
	 			obs = checkObstaculo(c1) || checkObstaculo(c2);
	 		}
 		
 		for(int i = 0;(i < width && !obs); i++){
	 			Coordinate c1 = new Coordinate(coor.getX(), coor.getY()+i, coor.getZ());
	 			Coordinate c2 = new Coordinate(coor.getX()+height, coor.getY()+i, coor.getZ());
	 			obs = checkObstaculo(c1) || checkObstaculo(c2);
	 		}
 		return obs;
 	}
 	/**
 	 * Comprueba si la coordenada esta en un obstaculo llamando al metodo de la clase LineaObstaculo
 	 * @param coor
 	 * @return
 	 */
 	public static synchronized boolean checkObstaculo(Coordinate coor){
 		for(LineaObstaculo obs:obstaculos){
 			if(obs.compruebaCoordenada(coor))return true;
 		}
 		return false;
 	}
 	public LineaObstaculo getObstaculo(Coordinate coordinate) {
 		for(LineaObstaculo obs:obstaculos){
 			if(obs.compruebaCoordenada(coordinate))return obs;
 		}
 		return null;
 	}
 
 	private void actualizarObstaculos() {
 		obstaculos = VisorEscenariosRosace.getObstaculos();
 	}
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 }