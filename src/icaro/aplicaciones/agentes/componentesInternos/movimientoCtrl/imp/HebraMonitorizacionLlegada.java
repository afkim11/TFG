
package icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp;


import java.util.ArrayList;

import org.apache.log4j.Logger;

import icaro.aplicaciones.Rosace.calculoRutas.AlgoritmoRuta;
import icaro.aplicaciones.Rosace.calculoRutas.Anterior;
import icaro.aplicaciones.Rosace.calculoRutas.AlgoritmoRutaLee;
import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.RobotStatus;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas.GeneraryEncolarObjetivoReconocerTerreno;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.ItfUsoRecursoVisualizadorEntornosSimulacion;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.VisorEscenariosRosace;
import icaro.infraestructura.entidadesBasicas.comunicacion.Informacion;


/**
 * Clase interna que se encarga de generar eventos de monitorizacin cada cierto tiempo
 *
 * @author Carlos Delgado
 */
public class HebraMonitorizacionLlegada extends Thread {

	protected long milis;
	protected volatile boolean finalizar = false;
	public MaquinaEstadoMovimientoCtrl controladorMovimiento;
	private Logger log = Logger.getLogger(this.getClass().getSimpleName());
	private String identDestino, identRobot;
	private   volatile Coordinate coordActuales;
	private volatile Coordinate coordDestino;
	private double velocidadRobot;
	private volatile double pendienteRecta;
	private volatile boolean parar = false ;
	private volatile boolean enDestino = false ;
	private boolean energia = true;
	private float distanciaArecorrer ;
	private float b ; // punto corte recta con eje Y
	private int dirX =0, dirY=0;
	private int intervaloEnvioInformesMs ;
	private RobotStatus robotStatus;
	public ItfUsoRecursoVisualizadorEntornosSimulacion itfusoRecVisSimulador;
	final int perimetroDeVision = GeneraryEncolarObjetivoReconocerTerreno.perimetroDeVision;

	

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
	public HebraMonitorizacionLlegada(String idRobot,MaquinaEstadoMovimientoCtrl contrMovimiento,  ItfUsoRecursoVisualizadorEntornosSimulacion itfRecVisSimulador, RobotStatus robotStatus) {
		super("HebraMonitorizacion "+idRobot);  
		controladorMovimiento =contrMovimiento;
		this.itfusoRecVisSimulador = itfRecVisSimulador;
		identRobot = idRobot;
		this.robotStatus = robotStatus;
	}
	public synchronized void inicializarDestino (String idDestino,Coordinate coordRobot,Coordinate coordDest, double velocidad ){    
		//      this.finalizar= false;
		coordActuales =coordRobot ;
		coordDestino = coordDest;
		velocidadRobot = velocidad; // 
		//      intervaloEnvioInformacion= intervEnvioInformacion; 
		identDestino = idDestino;
		//      this. pendienteRecta = (float) ((coordDestino.y-coordActuales.y)/(coordDestino.x-coordActuales.x));
		log.debug ("Coord Robot " + identRobot + " iniciales -> ("+this.coordActuales.getX() + " , " + this.coordActuales.getY() + ")");
		log.debug ("Coord Robot " + identRobot + " destino -> ("+this.coordDestino.getX() + " , " + this.coordDestino.getY() + ")");
		this.setDaemon(true);
		//      coordIncremento = this.calcularIncrementosCoordenadasAvelocidadConstante(intervaloEnvioInformacion);
		//     this.evento = notificacionAProducir;
		this.finalizar= false;
		//       distanciaDestino = this.distanciaEuclidC1toC2(coordActuales, coordDestino);

		intervaloEnvioInformesMs = (int)(velocidadRobot* 12);

	}

	public void pararRobot(){
		this.finalizar=true;		
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
	public Coordinate getCoordRobot() {
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
		int energiaActual=this.robotStatus.getAvailableEnergy();
		//       double espacioRecorridoEnIntervalo = velocidadRobot*intervaloEnvioInformacion;
		log.debug ("Coord Robot " + identRobot + " iniciales -> ("+this.coordActuales.getX() + " , " + this.coordActuales.getY() + ")");
		log.debug ("Coord Robot " + identRobot + " destino -> ("+this.coordDestino.getX() + " , " + this.coordDestino.getY() + ")");
		//       System.out.println("Coord Robot " + identRobot + " iniciales -> ("+this.coordActuales.x + " , " + this.coordActuales.y + ")");
		//      this.itfusoRecVisSimulador.mostrarMovimientoAdestino(identRobot,identDestino, coordActuales,velocidadRobot);
		while (!this.finalizar && (!enDestino)){
			try {
				AlgoritmoRutaLee alg=new AlgoritmoRutaLee(this.coordDestino, this.coordActuales);
				ArrayList<Coordinate> ruta=new ArrayList<Coordinate>();
				ruta = alg.iniciarCalculoRuta();
				int anchoVictima =15;
				int referenciaExploracion = (int)this.coordActuales.getX();
				if(ruta!=null){
					while(!enDestino && this.energia &&!this.finalizar){
						for(int i=0;i<ruta.size() && !this.finalizar && this.energia ;i++){

							Coordinate punto=ruta.get(i);
							this.coordActuales.setY(punto.getY());
							this.coordActuales.setX(punto.getX());
							enDestino = ((coordActuales.getX()-coordDestino.getX())==0 &&(coordActuales.getY()-coordDestino.getY())==0);
							if (itfusoRecVisSimulador != null)
								this.itfusoRecVisSimulador.mostrarPosicionRobot(identRobot, coordActuales);
							this.controladorMovimiento.setCoordenadasActuales(coordActuales);
							/*
							 * Si estas explorando entonces se comprueba que en el perímetro de vision del robot haya victimas. 
							 */
							//Movimiento a derechas
							if(this.controladorMovimiento.estadoActual.getActuacion() == 1 && (referenciaExploracion + anchoVictima) == (int)this.coordActuales.getX() ){
								referenciaExploracion = referenciaExploracion + anchoVictima;
								Thread t = new Thread(){
									public void run(){
										try {
											itfusoRecVisSimulador.comprobarVictimasArea(coordActuales, perimetroDeVision);
										} catch (Exception e){
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								};
								t.start();

							}
							//Movimiento a izquierdas
							else if(this.controladorMovimiento.estadoActual.getActuacion() == 1 && (referenciaExploracion - anchoVictima) == (int)this.coordActuales.getX()){
								referenciaExploracion = referenciaExploracion - anchoVictima;								
								Thread t = new Thread(){
									public void run(){
										try {
											itfusoRecVisSimulador.comprobarVictimasArea(coordActuales, perimetroDeVision);
										} catch (Exception e){
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								};
								t.start();
							}
							if(this.controladorMovimiento.estadoActual.getActuacion()==1 && (i%5 == 0))
								itfusoRecVisSimulador.addRastroExploracion(this.coordActuales);
							Thread.sleep(intervaloEnvioInformesMs);
							if(energiaActual > 0){
								energiaActual--;
								this.robotStatus.setAvailableEnergy(energiaActual);
							}
							else energia = false;
						}
					}

					//Salvamento de la victima.
					if(this.controladorMovimiento.estadoActual.getActuacion() == 0){
						Coordinate victimCoor = this.coordDestino;
						this.coordDestino = itfusoRecVisSimulador.getEscenario().getCoordenadaLugarSeguro();
						ArrayList<Coordinate> ruta1 = new ArrayList<Coordinate>();
						AlgoritmoRutaLee alg1=new AlgoritmoRutaLee(this.coordDestino, this.coordActuales);
						ruta1 = alg1.iniciarCalculoRuta();
						for(int i=0;i<ruta1.size() && !this.finalizar && this.energia ;i++){
							Coordinate punto=ruta1.get(i);
							this.coordActuales.setY(punto.getY());
							this.coordActuales.setX(punto.getX());
							enDestino = ((coordActuales.getX()-coordDestino.getX())==0 &&(coordActuales.getY()-coordDestino.getY())==0);
							if (itfusoRecVisSimulador != null){
								this.itfusoRecVisSimulador.mostrarPosicionRobot(identRobot, coordActuales);
								this.itfusoRecVisSimulador.mostrarPosicionVictima(this.identDestino,coordActuales);
								victimCoor = coordActuales;
							}										
							this.controladorMovimiento.setCoordenadasActuales(coordActuales);
							Thread.sleep(intervaloEnvioInformesMs);								
							if(energiaActual > 0){
								energiaActual--;
								this.robotStatus.setAvailableEnergy(energiaActual);
							}
							else energia = false;
						}
					}
					if(!energia){
						this.controladorMovimiento.itfProcObjetivos.insertarHecho(new Informacion(VocabularioRosace.MsgRomperRobot));
					}
				}
				else {
					finalizar=true;
					enDestino=false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(enDestino){
			finalizar = true;
			this.controladorMovimiento.estamosEnDestino(identDestino,this.coordActuales);
			log.debug("Coord Robot En thread  " + identRobot + " en destino -> ("+this.coordActuales.getX() + " , " + this.coordActuales.getY() + ")");
		}
	}


}
