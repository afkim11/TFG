
package icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp;


import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.Coste;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.ItfUsoRecursoVisualizadorEntornosSimulacion;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.VisorEscenariosRosace;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.patronAgenteCognitivo.percepcion.imp.PercepcionAgenteCognitivoImp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.TreeMap;

import org.apache.log4j.Logger;


/**
 * Clase interna que se encarga de generar eventos de monitorizacin cada cierto tiempo
 *
 * @author Carlos Delgado
 */
public class HebraMonitorizacionLlegada extends Thread {

	/**
	 * Milisegundos que esperar antes de lanzar otra monitorizacin
	 * @uml.property  name="milis"
	 */
	protected long milis;

	/**
	 * Indica cundo debe dejar de monitorizar
	 * @uml.property  name="finalizar"
	 */
	protected volatile boolean finalizar = false;
	protected boolean bloqueado = false;

	/**
	 * Agente reactivo al que se pasan los eventos de monitorizacin
	 * @uml.property  name="agente"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	public MaquinaEstadoMovimientoCtrl controladorMovimiento;
	private Logger log = Logger.getLogger(this.getClass().getSimpleName());
	/**
	 * Evento a producir
	 * @uml.property  name="evento"
	 */
	private String identDestino, identRobot;
	private   volatile Coordinate coordActuales;
	private volatile Coordinate coordDestino;
	private volatile Coordinate coordIncremento;
	private double velocidadRobot; // en metros por segundo 
	//     protected int intervaloEnvioInformacion = 1000; // por defecto en ms . Deberia ser configurable
	private volatile double pendienteRecta;
	private volatile boolean estamosEnDestino;
	private double espacioRecorrido ;
	//     protected double distanciaDestino ;
	private boolean pendienteInfinita = false ;
	private volatile boolean parar = false ;
	private volatile boolean enDestino = false ;
	private float distanciaArecorrer ;
	private float b ; // punto corte recta con eje Y
	private int dirX =0, dirY=0,incrementoDistancia=0;
	private int intervaloEnvioInformesMs ;
	private int distanciaRecorridaEnIntervaloInformes ;
	private long tiempoParaAlcanzarDestino = 2000;
	public ItfUsoRecursoVisualizadorEntornosSimulacion itfusoRecVisSimulador;

	//    private int numeroPuntos = 20;
	/**
	 * Constructor
	 *
	 * @param milis Milisegundos a esperar entre cada monitorizacion
	 */
	public HebraMonitorizacionLlegada(String idRobot,MaquinaEstadoMovimientoCtrl contrMovimiento,  ItfUsoRecursoVisualizadorEntornosSimulacion itfRecVisSimulador) {
		super("HebraMonitorizacion "+idRobot);  
		controladorMovimiento =contrMovimiento;
		this.itfusoRecVisSimulador = itfRecVisSimulador;
		identRobot = idRobot;
	}
	public synchronized void inicializarDestino (String idDestino,Coordinate coordRobot,Coordinate coordDest, double velocidad ){    
		//      this.finalizar= false;
		coordActuales =coordRobot ;
		coordDestino = coordDest;
		velocidadRobot = velocidad; // 
		//      intervaloEnvioInformacion= intervEnvioInformacion; 
		espacioRecorrido = 0;
		identDestino = idDestino;
		//      this. pendienteRecta = (float) ((coordDestino.y-coordActuales.y)/(coordDestino.x-coordActuales.x));
		log.debug ("Coord Robot " + identRobot + " iniciales -> ("+this.coordActuales.getX() + " , " + this.coordActuales.getY() + ")");
		log.debug ("Coord Robot " + identRobot + " destino -> ("+this.coordDestino.getX() + " , " + this.coordDestino.getY() + ")");
		this.setDaemon(true);
		//      coordIncremento = this.calcularIncrementosCoordenadasAvelocidadConstante(intervaloEnvioInformacion);
		//     this.evento = notificacionAProducir;
		this.finalizar= false;
		//       distanciaDestino = this.distanciaEuclidC1toC2(coordActuales, coordDestino);
		double incrX=(coordDestino.getX()-coordActuales.getX());
		double incrY=(coordDestino.getY()-coordActuales.getY());
		if (incrX>0)dirX=1 ;
		else dirX=-1;
		if (incrY>0)dirY=1 ;
		else dirY=-1;
		if (incrX==0 &&incrY!=0){pendienteInfinita= true;
		distanciaArecorrer = (float)incrY;
		}else if (incrX==0 &&incrY==0) finalizar=true;
		else { pendienteRecta = (float) Math.abs(incrY/incrX);
		this.distanciaArecorrer =(float) Math.sqrt(incrX*incrX+incrY*incrY);
		this.b = (float) (coordActuales.y -pendienteRecta * coordActuales.x ) ;

		}
		//        this.incrementoDistancia= (int)distanciaArecorrer/numeroPuntos;
		//        tiempoParaAlcanzarDestino = (long)(distanciaArecorrer/velocidadRobot); // en milisegundos
		//       int intervaloEnvioInformesMs = (int)tiempoParaAlcanzarDestino/numeroPuntos;
		//       intervaloEnvioInformesMs = 40;
		//        distanciaRecorridaEnIntervaloInformes = (long)(1+velocidadRobot*intervaloEnvioInformesMs/50);
		//       coordIncremento = this.calcularIncrementosCoordenadasAvelocidadConstante(intervaloEnvioInformacion);
		intervaloEnvioInformesMs = (int)velocidadRobot* 12;
		distanciaRecorridaEnIntervaloInformes = 1;
	}


	/**
	 * Termina la monitorizacin
	 */
	public synchronized void finalizar() {
		this.finalizar= true;
		this.notifyAll();

	}
	public synchronized void setCoordRobot(Coordinate robotCoord) {
		this.coordActuales= robotCoord;
	}
	public synchronized Coordinate getCoordRobot() {
		return coordActuales;
	}
	public synchronized void setCoordDestino(Coordinate destCoord) {
		this.coordDestino= destCoord;
	}
	public synchronized Coordinate getCoordDestino() {
		return coordDestino;
	}
	public synchronized void setVelocidadRobot(double velRobot) {
		this.velocidadRobot= velRobot;
	}

	@Override
	public synchronized void run() {

		//       double espacioRecorridoEnIntervalo = velocidadRobot*intervaloEnvioInformacion;
		log.debug ("Coord Robot " + identRobot + " iniciales -> ("+this.coordActuales.getX() + " , " + this.coordActuales.getY() + ")");
		log.debug ("Coord Robot " + identRobot + " destino -> ("+this.coordDestino.getX() + " , " + this.coordDestino.getY() + ")");
		//       System.out.println("Coord Robot " + identRobot + " iniciales -> ("+this.coordActuales.x + " , " + this.coordActuales.y + ")");
		//      this.itfusoRecVisSimulador.mostrarMovimientoAdestino(identRobot,identDestino, coordActuales,velocidadRobot);
		while (!this.finalizar && (!enDestino)) {
			try {
				if(!this.bloqueado){
					Thread.sleep(intervaloEnvioInformesMs);

					calcularNuevasCoordenadas (distanciaRecorridaEnIntervaloInformes);                      
					log.debug("Coord Robot " + identRobot + " calculadas -> ("+this.coordActuales.getX() + " , " + this.coordActuales.getY() + ")");  
					enDestino = ((coordActuales.getX()-coordDestino.getX())*dirX>=0 &&(coordActuales.getY()-coordDestino.getY())*dirY>=0);
					finalizar = (coordActuales.x<0.5 || coordActuales.y<0.5 );
					if (itfusoRecVisSimulador != null)
						this.itfusoRecVisSimulador.mostrarPosicionRobot(identRobot, coordActuales);
					this.controladorMovimiento.setCoordenadasActuales(coordActuales);
				}
				else{
					boolean[][] visitados= new boolean[VisorEscenariosRosace.ancho][VisorEscenariosRosace.alto];
					for(int i =0;i<VisorEscenariosRosace.ancho;i++)
						for(int j=0;j<VisorEscenariosRosace.alto;j++)
							visitados[i][j]=false;
					visitados[(int)this.coordActuales.getX()][(int)this.coordActuales.getY()]=true;
					ArrayList<Coordinate> ruta=calculaRuta(visitados,this.coordActuales,0);
					if(ruta!=null){
						while(!enDestino){
							for(int i=0;i<ruta.size();i++){

								Coordinate punto=ruta.get(i);
								this.coordActuales.setY(punto.getY());
								this.coordActuales.setX(punto.getX());
								enDestino = ((coordActuales.getX()-coordDestino.getX())*dirX>=0 &&(coordActuales.getY()-coordDestino.getY())*dirY>=0);
								if (itfusoRecVisSimulador != null)
									this.itfusoRecVisSimulador.mostrarPosicionRobot(identRobot, coordActuales);
								this.controladorMovimiento.setCoordenadasActuales(coordActuales);

							}
						}
					}
					else {
						this.wait();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (enDestino ){
			finalizar = true;
			try {
				Thread.sleep(tiempoParaAlcanzarDestino);
				this.controladorMovimiento.estamosEnDestino(identDestino,coordDestino );
				log.debug("Coord Robot En thread  " + identRobot + " en destino -> ("+this.coordActuales.getX() + " , " + this.coordActuales.getY() + ")");
				//          System.out.println("Coord Robot En thread  " + identRobot + " en destino -> ("+this.coordActuales.x + " , " + this.coordActuales.y + ")");       
				//                this.controladorMovimiento.setCoordenadasActuales(coordDestino);
			} catch (InterruptedException ex) {
				log.error( ex);
			}
		}
	}

	/**
	 * 
	 * 123
	 * 4X5
	 * 678 
	 * 
	 */
	private ArrayList<Coordinate> calculaRuta(boolean[][] visitados, Coordinate coordinadasActuales,int anterior) {
		PriorityQueue<Coordinate> colaNodos=estimaCoste(visitados,coordinadasActuales,anterior); 

		return null;
	}
	private PriorityQueue<Coordinate> estimaCoste(boolean[][] visitados, Coordinate coordinadasActuales, int anterior) {
		PriorityQueue<Coordinate> cola=new PriorityQueue<Coordinate>(new Comparator<Coordinate>(){
			@Override
			public int compare(Coordinate o1, Coordinate o2){
				double coste1=Coste.distanciaC1toC2(o1,coordDestino),coste2=Coste.distanciaC1toC2(o2,coordDestino);
				if( coste1<coste2 )return -1;
				else if(coste1 > coste2)return 1;
				return 0;


			}});
		for(int i=1;i<=8;i++){
			if(i!=anterior){
				if(i==1){
					int x=(int)this.coordActuales.getX()-1;
					int y=(int)this.coordActuales.getY()-1;
					Coordinate coor=new Coordinate(x,y,0.5);
					if(!visitados[x][y] && !this.controladorMovimiento.checkObstaculo(coor))
						cola.add(coor);

				}
				else if(i==2){
					int x=(int)this.coordActuales.getX();
					int y=(int)this.coordActuales.getY()-1;
					Coordinate coor=new Coordinate(x,y,0.5);
					if(!visitados[x][y] && !this.controladorMovimiento.checkObstaculo(coor))
						cola.add(coor);

				}
				else if(i==3){
					int x=(int)this.coordActuales.getX()+1;
					int y=(int)this.coordActuales.getY()-1;
					Coordinate coor=new Coordinate(x,y,0.5);
					if(!visitados[x][y] && !this.controladorMovimiento.checkObstaculo(coor))
						cola.add(coor);

				}
				else if(i==4){
					int x=(int)this.coordActuales.getX()-1;
					int y=(int)this.coordActuales.getY();
					Coordinate coor=new Coordinate(x,y,0.5);
					if(!visitados[x][y] && !this.controladorMovimiento.checkObstaculo(coor))
						cola.add(coor);

				}
				else if(i==5){
					int x=(int)this.coordActuales.getX()+1;
					int y=(int)this.coordActuales.getY();
					Coordinate coor=new Coordinate(x,y,0.5);
					if(!visitados[x][y] && !this.controladorMovimiento.checkObstaculo(coor))
						cola.add(coor);

				}
				else if(i==6){
					int x=(int)this.coordActuales.getX()-1;
					int y=(int)this.coordActuales.getY()+1;
					Coordinate coor=new Coordinate(x,y,0.5);
					if(!visitados[x][y] && !this.controladorMovimiento.checkObstaculo(coor))
						cola.add(coor);

				}
				else if(i==7){
					int x=(int)this.coordActuales.getX();
					int y=(int)this.coordActuales.getY()+1;
					Coordinate coor=new Coordinate(x,y,0.5);
					if(!visitados[x][y] && !this.controladorMovimiento.checkObstaculo(coor))
						cola.add(coor);

				}
				else if(i==8){
					int x=(int)this.coordActuales.getX()+1;
					int y=(int)this.coordActuales.getY()+1;
					Coordinate coor=new Coordinate(x,y,0.5);
					if(!visitados[x][y] && !this.controladorMovimiento.checkObstaculo(coor))
						cola.add(coor);

				}
			}
		}		
		return cola;
	}

	private void calcularNuevasCoordenadas (long incrementoDistancia){
		// suponemos avance en linea recta 
		// formula aplicada x1 = xo + sqrt( espacioRecorrido**2 / (1 + pendienteRecta**2))
		//  y1 = y0 +(x1-x0)*pendienteRecta

		if (pendienteInfinita){
			//            constIncrX = 0;
			//            constIncrY= incrementoDistancia;
			this.coordActuales.setY(coordActuales.getY() + incrementoDistancia*dirY);
		}
		else {
			// incremmento de x respecto a distancia recorrida
			double nuevaVariableY = coordActuales.getY() + pendienteRecta*incrementoDistancia*dirY;
			double nuevaVariableX = coordActuales.getX() + incrementoDistancia*dirX;
			if(!this.controladorMovimiento.checkObstaculo(new Coordinate(nuevaVariableX, nuevaVariableY, coordActuales.getZ()))){
				this.coordActuales.setY(nuevaVariableY);
				this.coordActuales.setX(nuevaVariableX);
			}
			else {
				this.bloqueado = true;
				this.controladorMovimiento.bloqueadoPorObstaculo(new Coordinate(nuevaVariableX, nuevaVariableY, coordActuales.getZ()));
				//ArrayList<Object> array = new ArrayList<Object>();
				//array.add(VocabularioRosace.MsgRobotBloqueadoObstaculo);
				//array.add(this.controladorMovimiento.getObstaculo(new Coordinate(nuevaVariableX, nuevaVariableY, coordActuales.getZ()))); //Identidad obtaculo
				//this.controladorMovimiento.itfProcObjetivos.insertarHecho(new MensajeBloqueoObstaculo(array,this.identRobot,"Jefe"));      		
			}
		}
	}

}
