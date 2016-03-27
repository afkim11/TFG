/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.ItfUsoRecursoVisualizadorEntornosSimulacion;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Informe;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author FGarijo
 */
public abstract class EstadoAbstractoMovRobot  {

    public static  enum EstadoMovimientoRobot {Indefinido,RobotParado, RobotEnMovimiento, RobotBloqueado,RobotBloqueadoPorObstaculo,RobotavanceImposible,enDestino,  error}
    //Nombres de las clases que implementan estados del recurso interno
    public static  enum EvalEnergiaRobot {sinEnergia,energiaSuficiente,EnergiaJusta, EnergiaInsuficiente }
    public EstadoAbstractoMovRobot estadoActual;
    public MaquinaEstadoMovimientoCtrl maquinaEstados;
    public String identAgente;
    public volatile Coordinate robotposicionActual;
    public volatile Coordinate destinoCoord;
    public double distanciaDestino ;
    protected int tipoActuacion;
    protected float velocidadCrucero;
    public ItfProcesadorObjetivos itfProcObjetivos;
    protected HebraMonitorizacionLlegada monitorizacionLlegadaDestino;
    public ItfUsoRecursoTrazas trazas ;
    public String identComponente ;
    public String identEstadoActual ;
    public String identDestino ;
    public ItfUsoRecursoVisualizadorEntornosSimulacion itfusoRecVisSimulador;
    
   public  EstadoAbstractoMovRobot (MaquinaEstadoMovimientoCtrl maquinaEstds,MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot identEstadoAcrear) {
    if (maquinaEstds == null){
       estadoActual = null;
       trazas.trazar(this.getClass().getSimpleName(), " Error al crear el estado  "+ identEstadoAcrear+
                " La maquinaEstds es null   ", InfoTraza.NivelTraza.error);
    }else{
          maquinaEstados = maquinaEstds ;
//          estadoActual = this; 
          identEstadoActual= identEstadoAcrear.name();
    }
}
    public void Inicializar (ItfProcesadorObjetivos itfProcObj){
        identAgente = itfProcObj.getAgentId();
        itfProcObjetivos = itfProcObj;
        identComponente = identAgente+"."+this.getClass().getSimpleName();
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
 
    }
    public void Inicializar (ItfProcesadorObjetivos itfProcObj,ItfUsoRecursoVisualizadorEntornosSimulacion itfVisSimul){
        identAgente = itfProcObj.getAgentId();
        itfProcObjetivos = itfProcObj;
        identComponente = identAgente+"."+this.getClass().getSimpleName();
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        itfusoRecVisSimulador = itfVisSimul;
 
    }
    private EstadoAbstractoMovRobot setEstadoActual(EstadoAbstractoMovRobot estadoMovCtrl) {
        return estadoActual = maquinaEstados.estadoActual;
    }

    public  void inicializarInfoMovimiento(Coordinate coordInicial, float velocidadInicial){
        robotposicionActual =coordInicial;
        velocidadCrucero = velocidadInicial;
    } 
        public abstract void moverAdestino(String identDest,Coordinate coordDestino, float velocidadCrucero,int tipoActuacion);
        public void cambiaVelocidad( float nuevaVelocidadCrucero) {
            estadoActual.cambiaVelocidad(nuevaVelocidadCrucero);
        }

        public synchronized void cambiaDestino(String identDest,Coordinate coordDestino) {
            estadoActual.cambiaDestino(identDest,coordDestino);
            this.identDestino = identDest;
         //   identDestino = identDest;
        }   
        public synchronized void parar(){
          estadoActual.parar(); 
        }

        public void continuar(){
            estadoActual.continuar();
        }
    public abstract  boolean estamosEnDestino(String identDest);

    public synchronized void imposibleAvanzarADestino(){
        maquinaEstados.cambiarEstado(MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotBloqueado);
    }
    

    public abstract Coordinate getCoordenadasActuales(); 
    

    public synchronized void setCoordenadasActuales(Coordinate nuevasCoordenadas) {
        if (nuevasCoordenadas != null){
         robotposicionActual.x = nuevasCoordenadas.x;
         robotposicionActual.y = nuevasCoordenadas.y;
         robotposicionActual.z = nuevasCoordenadas.z;
        }
    }
    public EstadoAbstractoMovRobot getEstadoActual (){
        return this.estadoActual;
    }
    
    /**
	 * 0-->Rescatando
	 * 1-->Explorando
	 * 	 * @return
	 */
	public int getActuacion(){
		return this.tipoActuacion;
	}

}
