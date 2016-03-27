/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.RobotStatus;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author FGarijo
 */
public class RobotBloqueado extends EstadoAbstractoMovRobot implements ItfUsoMovimientoCtrl{

	 private RobotStatus robotStatus;
//   public MaquinaEstadoMovimientoCtrl maquinaEstados;
    public  RobotBloqueado (MaquinaEstadoMovimientoCtrl maquinaEstados){
       // en este estado se puede simular que el robot no avanza, pero que esta intentando salir del atasco
        // esto puede durar un tiempo. al final o ha salido del atasco para continuar hacia el destino o se queda
        // esperando ordenes
         super (maquinaEstados,MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotParado);
  
    }
   @Override
    public  void inicializarInfoMovimiento(Coordinate coordInicial, float velocidadInicial){
       
   } 
    @Override
        public void moverAdestino(String identdest,Coordinate coordDestino, float velocidadCrucero,int tipoActuacion) {
//            this.distanciaDestino = this.distanciaEuclidC1toC2(this.robotposicionActual, coordDestino);
//            double tiempoParaAlcanzarDestino = distanciaDestino/velocidadCrucero;
            this.identDestino = identdest;
        }
    @Override
        public void cambiaVelocidad( float nuevaVelocidadCrucero) {
            this.velocidadCrucero = nuevaVelocidadCrucero;
        }
    @Override
        public void cambiaDestino(String identdest,Coordinate coordDestino) {
            this.destinoCoord = coordDestino;
            this.identDestino = identdest;
        }
    @Override
        public void parar(){
         if (monitorizacionLlegadaDestino!=null ) this.monitorizacionLlegadaDestino.finalizar();
            this.trazas.trazar (this.identAgente +"."+this.getClass().getSimpleName(), " ignoro la operacion porque estoy parado ", InfoTraza.NivelTraza.debug);
        }
    @Override
        public void bloquear(){
           if (monitorizacionLlegadaDestino!=null ) this.monitorizacionLlegadaDestino.finalizar();
           this.trazas.trazar (this.identAgente +"."+this.getClass().getSimpleName(), " ignoro la operacion porque estoy bloqueado ", InfoTraza.NivelTraza.debug);
    }
    @Override
        public void continuar(){
        // deberia intentar salir del bloqueo durante un tiempo, si no lo consigue notifica al agente
        // lo podria hacer generando una notificacion que va al controlador del agente
            this.trazas.trazar (this.identAgente +"."+this.getClass().getSimpleName(), " ignoro la operacion porque estoy parado ", InfoTraza.NivelTraza.debug); 
        }
    @Override
    public boolean estamosEnDestino(String identDest){
        return (identDestino.equals(identDest));
        // 
        // se podria estar atascado y en destino
//         this.trazas.trazar (this.identAgente +"."+this.getClass().getSimpleName(), " ignoro la operacion porque estoy atascado ", InfoTraza.NivelTraza.debug);
    }
    @Override
    public  Coordinate getCoordenadasActuales(){
        return this.monitorizacionLlegadaDestino.getCoordRobot();
    }
	@Override
	public HebraMonitorizacionLlegada getHebraMonitorizacionLlegadaDestino() {
		return null;
	}
	@Override
	public void setRobotStatus(RobotStatus robotStatus) {
		this.robotStatus = robotStatus;
		
	}
	@Override
	public Coordinate getCoordenasDestino() {
		return this.destinoCoord;
	} 
}
