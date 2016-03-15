package icaro.aplicaciones.Rosace.informacion;

 import java.util.ArrayList;
 import java.util.Comparator;
 import java.util.PriorityQueue;

import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.LineaObstaculo;
 import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.VisorEscenariosRosace;
 /**
  * Esta clase implementa un algoritmo de vuelta atrás con heurística para resolver la ruta mínima 
  * entre 2 coordenadas teniendo en cuenta los posibles obstaculos esquivándolos. Para ello se tiene
  * en cuenta las coordenadas ya visitadas y la posición de la que viene para no repetir caminos.
  *  
  * @author Luis García Terriza y Sergio Moreno de Pradas
  *
  */
 public class AlgoritmoRuta {
	private static final int width = 40;
 	private static final int height = 27;
 	private final Comparator<Coordinate> comparador = new Comparator<Coordinate>(){
 			public int compare(Coordinate o1, Coordinate o2){
 				double coste1=Coste.distanciaC1toC2(o1,coordenadasDestino),coste2=Coste.distanciaC1toC2(o2,coordenadasDestino);
 				if( coste1<coste2 )return -1;
 				else if(coste1 > coste2)return 1;
 				return 0;

 			}};
 	
 	private Coordinate coordenadasDestino;
 	private Coordinate coordenadasIniciales;
 	
 	private int contador; //Este contador se encarga de que si el algoritmo no ha encontrado una solucion en cierto número de pasos, finalice la ejecución. Se supone en tal caso que no hay solución válida.
 	private static final int limiteRecursividad=3000;
 	private static ArrayList<LineaObstaculo> obstaculos;
 	
 	
 	
 	public enum Anterior{MOV_NULO, NOROESTE, NORTE, NORESTE, OESTE, ESTE, SUROESTE, SUR, SURESTE};
 	
 	
 	public AlgoritmoRuta(Coordinate destino,Coordinate iniciales){
 		this.coordenadasDestino=destino;
 		this.coordenadasIniciales=iniciales;
 		this.contador=0;
 		actualizarObstaculos();
 	}
 	/**
 	 * Algoritmo de vuelta atrás con heurística que se encarga de comprobar si hay una solución(un camino entre dos puntos).
 	 * Para limitar su calculabilidad, se hace uso de un contador de recursiones y de un límite de recursividad. 
 	 * @param visitados
 	 * @param coordenadasActuales
 	 * @param anterior
 	 * @param rutaHastaAhora
 	 * @return
 	 */
 	public ArrayList<Coordinate> calculaRuta(boolean[][] visitados, Coordinate coordenadasActuales,Anterior anterior,ArrayList<Coordinate> rutaHastaAhora) {
 		this.contador++;
 		if(this.contador>=limiteRecursividad)return null;
 		if(rutaHastaAhora.get(rutaHastaAhora.size()-1).equals(this.coordenadasDestino)){
 			return rutaHastaAhora;
 		}
 		else{
 			PriorityQueue<Coordinate> colaNodos=estimaCoste(visitados,coordenadasActuales, anterior); 
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
 	 * En función de la coordenada anterior a la que estoy, se codifica mediante un número la posición anterior.
 	 * Este esquema muestra como se han representado los números: por ejemplo, en la posición X, si el número que devuelve es "2", entonces viene de arriba.
 	 * 
 	 * 123
 	 * 4X5
 	 * 678 
 	 * 
 	 */
 	private Anterior calculaAnterior(Coordinate anterior,Coordinate siguiente){
 		int x1=(int)anterior.getX(),y1=(int)anterior.getY(),x2=(int)siguiente.getX(),y2=(int)siguiente.getY();
 		int restaX = x2 - x1, restaY = y2 - y1;
 		if (restaX == -1 && restaY == -1) return Anterior.SURESTE;
 		else if (restaX == 0 && restaY == -1) return Anterior.SUR;
 		else if (restaX == 1 && restaY == -1) return Anterior.SUROESTE;
 		else if (restaX == -1 && restaY == 0) return Anterior.ESTE;
 		else if (restaX == 1 && restaY == 0) return Anterior.OESTE;
 		else if (restaX == -1 && restaY == 1) return Anterior.NORESTE;
 		else if (restaX == 0 && restaY == 1) return Anterior.NORTE;
 		else if (restaX == 1 && restaY == 1) return Anterior.NOROESTE;
 		else return Anterior.MOV_NULO;
 		
 		
 		
 	}
 	/**
 	 * Este método se encarga de calcular los posibles nodos siguientes desde una posición sin tener en cuenta las coordenadas ya visitadas y la posición de la que se viene.
 	 * @param visitados
 	 * @param coordinadasActuales
 	 * @param anterior
 	 * @return
 	 */
 	private PriorityQueue<Coordinate> estimaCoste(boolean[][] visitados, Coordinate coordinadasActuales, Anterior anterior) {
 		PriorityQueue<Coordinate> cola=new PriorityQueue<Coordinate>(comparador);
 		for(int i=1;i<=8;i++){
 			if(i!=anterior.ordinal()){
 				if(i==Anterior.NOROESTE.ordinal()){
 					int x=(int)coordinadasActuales.getX()-1;
 					int y=(int)coordinadasActuales.getY()-1;
 					Coordinate coor=new Coordinate(x,y,0.5);
 					if(!visitados[x][y] && !checkLimites(coor, calculaAnterior(coordinadasActuales, coor)))
 						cola.add(coor);
 
 				}
 				else if(i==Anterior.NORTE.ordinal()){
 					int x=(int)coordinadasActuales.getX();
 					int y=(int)coordinadasActuales.getY()-1;
 					Coordinate coor=new Coordinate(x,y,0.5);
 					if(visitados[x][y]==false && !checkLimites(coor, calculaAnterior(coordinadasActuales, coor)))
 						cola.add(coor);
 
 				}
 				else if(i==Anterior.NORESTE.ordinal()){
 					int x=(int)coordinadasActuales.getX()+1;
 					int y=(int)coordinadasActuales.getY()-1;
 					Coordinate coor=new Coordinate(x,y,0.5);
 					if(visitados[x][y]==false && !checkLimites(coor, calculaAnterior(coordinadasActuales, coor)))
 						cola.add(coor);
 
 				}
 				else if(i==Anterior.OESTE.ordinal()){
 					int x=(int)coordinadasActuales.getX()-1;
 					int y=(int)coordinadasActuales.getY();
 					Coordinate coor=new Coordinate(x,y,0.5);
 					if(visitados[x][y]==false && !checkLimites(coor, calculaAnterior(coordinadasActuales, coor)))
 						cola.add(coor);
 
 				}
 				else if(i==Anterior.ESTE.ordinal()){
 					int x=(int)coordinadasActuales.getX()+1;
 					int y=(int)coordinadasActuales.getY();
 					Coordinate coor=new Coordinate(x,y,0.5);
 					if(visitados[x][y]==false && !checkLimites(coor, calculaAnterior(coordinadasActuales, coor)))
 						cola.add(coor);
 
 				}
 				else if(i==Anterior.SUROESTE.ordinal()){
 					int x=(int)coordinadasActuales.getX()-1;
 					int y=(int)coordinadasActuales.getY()+1;
 					Coordinate coor=new Coordinate(x,y,0.5);
 					if(visitados[x][y]==false && !checkLimites(coor, calculaAnterior(coordinadasActuales, coor)))
 						cola.add(coor);
 
 				}
 			else if(i==Anterior.SUR.ordinal()){
 					int x=(int)coordinadasActuales.getX();
 					int y=(int)coordinadasActuales.getY()+1;
 					Coordinate coor=new Coordinate(x,y,0.5);
 					if(visitados[x][y]==false && !checkLimites(coor, calculaAnterior(coordinadasActuales, coor)))
 						cola.add(coor);
 
 				}
 				else if(i==Anterior.SURESTE.ordinal()){
 					int x=(int)coordinadasActuales.getX()+1;
 					int y=(int)coordinadasActuales.getY()+1;
 					Coordinate coor=new Coordinate(x,y,0.5);
 					if(visitados[x][y]==false && !checkLimites(coor, calculaAnterior(coordinadasActuales, coor)))
 						cola.add(coor);
 
 				}
 			}
 		}		
 		return cola;
 	}
 	
 	
 	
 	public static boolean checkLimites(Coordinate coor, Anterior anterior){
 		if(obstaculos.isEmpty())return false;
 		
 		boolean obs = false;
 		boolean izquierda = false, derecha = false, arriba = false, abajo = false;
 		double X = coor.getX();
 		double Y = coor.getY();
 		double Z = coor.getZ();
 		if(anterior == Anterior.NOROESTE){abajo = true;derecha = true;}
 		else if(anterior == Anterior.NORTE){abajo = true;}
 		else if(anterior == Anterior.NORESTE){abajo = true;izquierda = true;}
 		else if(anterior == Anterior.OESTE){derecha = true;}
 		else if(anterior == Anterior.ESTE){izquierda = true;}
 		else if(anterior == Anterior.SUROESTE){arriba = true; derecha = true;}
 		else if(anterior == Anterior.SUR){arriba = true;}
 		else if(anterior == Anterior.SURESTE){arriba = true; izquierda = true;}
 		
 		for(int i = 0; izquierda && !obs &&i < height ; i++){
 			Coordinate c1 = new Coordinate(X, Y+i, Z);
 			obs = checkObstaculo(c1);
 		}
 		
 		for(int i = 0;derecha && !obs && i < height; i++){
 			Coordinate c1 = new Coordinate(X+width, Y+i,Z);
 			obs = checkObstaculo(c1);
 		}
 		
 		for(int i = 0; arriba && !obs && i < width; i++){
 			Coordinate c1 = new Coordinate(X+i, Y, Z);
 			obs = checkObstaculo(c1);
 		}
 		
 		for(int i = 0;abajo && !obs && i < width; i++){
 			Coordinate c1 = new Coordinate(X+i, Y+height, Z);
 			obs = checkObstaculo(c1);
 		}
 		return obs;
 	}
 	/**
 	 * Comprueba si la coordenada está en un obstáculo llamando al método de la clase LineaObstaculo
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