package icaro.aplicaciones.Rosace.calculoRutas;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import org.simpleframework.xml.core.Persister;

import fr.laas.openrobots.jmorse.components.rosace_sensor.RosaceSensor;
import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.Coste;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.ItfUsoRecursoPersistenciaEntornosSimulacion;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.EscenarioSimulacionRobtsVictms;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.LineaObstaculo;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.VisorEscenariosRosace;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.RepositorioInterfacesImpGen;
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
	private static final int EscenarioWidth = 1100;
	private static final int EscenarioHeight = 700;
	private Anterior direccionVictima;
	private final Comparator<CoordenadaExtension> comparador = new Comparator<CoordenadaExtension>(){
		public int compare(CoordenadaExtension o1, CoordenadaExtension o2){
			double coste1=Coste.distanciaC1toC2(o1.getCoor(),coordenadasDestino),coste2=Coste.distanciaC1toC2(o2.getCoor(),coordenadasDestino);
			if(coste1 < coste2){
				if(direccionBuena(direccionVictima, o2.getDir()) && !direccionBuena(direccionVictima, o1.getDir()))
					return 1;
				else return -1;
			}
			else if(coste1 > coste2){
				if(!direccionBuena(direccionVictima, o2.getDir()) && direccionBuena(direccionVictima, o1.getDir()))
					return -1;
				else return 1;
			}
			return 0;

		}};

		private Coordinate coordenadasDestino;
		private Coordinate coordenadasIniciales;

		private int contador; //Este contador se encarga de que si el algoritmo no ha encontrado una solucion en cierto número de pasos, finalice la ejecución. Se supone en tal caso que no hay solución válida.
		private static final int limiteRecursividad=3000;
		private static ArrayList<LineaObstaculo> obstaculos;


		public AlgoritmoRuta(Coordinate destino,Coordinate iniciales){
			this.coordenadasDestino=destino;
			this.coordenadasIniciales=iniciales;
			this.contador=0;
			if(iniciales.getX() < destino.getX()) this.direccionVictima = Anterior.ESTE;
			else if(iniciales.getX() > destino.getX()) this.direccionVictima = Anterior.OESTE;
			this.inicializarObstaculos();
		}
		private void inicializarObstaculos() {
			try {

				String rutaFicheroEscenario = NombresPredefinidos.RUTA_PERSISTENCIA_ESCENARIOS + VocabularioRosace.rutaEscenario;
				EscenarioSimulacionRobtsVictms escenario = new Persister().read(EscenarioSimulacionRobtsVictms.class,new File(rutaFicheroEscenario),false);
				obstaculos = escenario.getListObstacles();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void iniciarCalculoruta(Coordinate coorActuales,ArrayList<Coordinate> ruta){
			boolean[][] visitados = generaMapaBooleanos();
			visitados[(int)coorActuales.getX()][(int)coorActuales.getY()]=true;
			ruta.add(coorActuales);
			ruta = calculaRuta(visitados, coorActuales, Anterior.MOV_NULO, ruta);
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
			try{
				this.contador++;

				if(this.contador>=limiteRecursividad)return null;
				if(rutaHastaAhora.get(rutaHastaAhora.size()-1).equals(this.coordenadasDestino)){
					return rutaHastaAhora;
				}
				else{
					PriorityQueue<CoordenadaExtension> colaNodos=estimaCoste(visitados,coordenadasActuales, anterior); 
					while(!colaNodos.isEmpty() && this.contador<limiteRecursividad){
						Coordinate coor=colaNodos.poll().getCoor();
						int x=(int)coor.getX(),y=(int)coor.getY();
						visitados[x][y]=true;
						rutaHastaAhora.add(coor);
						ArrayList<Coordinate> posible_sol=calculaRuta(visitados,coor,calculaAnterior(coordenadasActuales,coor),rutaHastaAhora);
						if(posible_sol!=null)return posible_sol;			
						rutaHastaAhora.remove(rutaHastaAhora.size()-1);
						visitados[x][y]=false;
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
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
		 * 
		 * @param anterior
		 * @return Anterior
		 * 
		 * Esta funcion se utiliza para calcular en que direccion se esta moviendo el robot para la coordenada que esta calculando ahora, por ejemplo si viene de NORTE,
		 * significa que el robot esta yendo hacia el SUR. Basicamente es el inverso del anterior.
		 */
		private Anterior calculaDirActual(Anterior anterior){
			switch(anterior){
			case NORTE:
				return Anterior.SUR;
			case SUR:
				return Anterior.NORTE;
			case ESTE:
				return Anterior.OESTE;
			case OESTE:
				return Anterior.ESTE;
			case NORESTE:
				return Anterior.SUROESTE;
			case NOROESTE:
				return Anterior.SURESTE;
			case SUROESTE:
				return Anterior.NORESTE;
			case SURESTE:
				return Anterior.SUROESTE;
			default:
				return Anterior.MOV_NULO;
			}
		}
		/**
		 * Este método se encarga de calcular los posibles nodos siguientes desde una posición sin tener en cuenta las coordenadas ya visitadas y la posición de la que se viene.
		 * @param visitados
		 * @param coordinadasActuales
		 * @param anterior
		 * @return
		 */
		private PriorityQueue<CoordenadaExtension> estimaCoste(boolean[][] visitados, Coordinate coordinadasActuales, Anterior anterior) {
			PriorityQueue<CoordenadaExtension> cola=new PriorityQueue<CoordenadaExtension>(8,comparador);
			for(int i=1;i<=8;i++){
				if(i!=anterior.ordinal()){
					if(i==Anterior.NOROESTE.ordinal()){
						int x=(int)coordinadasActuales.getX()-1;
						int y=(int)coordinadasActuales.getY()-1;
						generaNodo(x,y,visitados,coordinadasActuales,cola);
					}
					else if(i==Anterior.NORTE.ordinal()){
						int x=(int)coordinadasActuales.getX();
						int y=(int)coordinadasActuales.getY()-1;
						generaNodo(x,y,visitados,coordinadasActuales,cola);
					}
					else if(i==Anterior.NORESTE.ordinal()){
						int x=(int)coordinadasActuales.getX()+1;
						int y=(int)coordinadasActuales.getY()-1;
						generaNodo(x,y,visitados,coordinadasActuales,cola);
					}
					else if(i==Anterior.OESTE.ordinal()){
						int x=(int)coordinadasActuales.getX()-1;
						int y=(int)coordinadasActuales.getY();
						generaNodo(x,y,visitados,coordinadasActuales,cola);
					}
					else if(i==Anterior.ESTE.ordinal()){
						int x=(int)coordinadasActuales.getX()+1;
						int y=(int)coordinadasActuales.getY();
						generaNodo(x,y,visitados,coordinadasActuales,cola);
					}
					else if(i==Anterior.SUROESTE.ordinal()){
						int x=(int)coordinadasActuales.getX()-1;
						int y=(int)coordinadasActuales.getY()+1;
						generaNodo(x,y,visitados,coordinadasActuales,cola);
					}
					else if(i==Anterior.SUR.ordinal()){
						int x=(int)coordinadasActuales.getX();
						int y=(int)coordinadasActuales.getY()+1;
						generaNodo(x,y,visitados,coordinadasActuales,cola);
					}
					else if(i==Anterior.SURESTE.ordinal()){
						int x=(int)coordinadasActuales.getX()+1;
						int y=(int)coordinadasActuales.getY()+1;
						generaNodo(x,y,visitados,coordinadasActuales,cola);
					}
				}
			}
			return cola;
		}
		private void generaNodo(int x,int y,boolean[][] visitados,Coordinate coordinadasActuales,PriorityQueue<CoordenadaExtension> cola){
			if(x>=0 && y>=0 && x< EscenarioWidth && y < EscenarioHeight){
				Coordinate coor=new Coordinate(x,y,0.5);
				Anterior ant = calculaAnterior(coordinadasActuales, coor);
				if(!visitados[x][y] && !checkLimites(coor, ant))
					cola.add(new CoordenadaExtension(calculaDirActual(ant), coor));
			}
		}
		private boolean[][] generaMapaBooleanos(){
			boolean[][] visitados= new boolean[VisorEscenariosRosace.ancho][VisorEscenariosRosace.alto];
			for(int i =0;i<VisorEscenariosRosace.ancho;i++)
				for(int j=0;j<VisorEscenariosRosace.alto;j++)
					visitados[i][j]=false;
			return visitados;
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
		/**
		 * 
		 * @param dirVictima
		 * @param dirCoord
		 * @return
		 * 
		 * Este metodo se utiliza para calcular si la direccion a la que se mueve si se toma la coordenada es una direccion buena para llegar a la victima, la victima puede estar
		 * a la derecha(ESTE) o a la izquierda(OESTE). Si esta en la izquierda las direcciones que 
		 */
		private boolean direccionBuena(Anterior dirVictima, Anterior dirCoord){
			if(dirVictima == Anterior.ESTE){
				switch(dirCoord){
				case OESTE:
				case NOROESTE:
				case SUROESTE:
					return false;
				default:
					return true;				
				}
			}
			else if(dirVictima == Anterior.OESTE){
				switch(dirCoord){
				case ESTE:
				case NORESTE:
				case SURESTE:
					return false;
				default:
					return true;
				}
			}
			else return false;
		}
}