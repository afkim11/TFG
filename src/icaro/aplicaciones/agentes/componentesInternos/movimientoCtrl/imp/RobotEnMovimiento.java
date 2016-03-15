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
// public class RobotEnMovimiento extends MovimientoCtrlImp implements ItfUsoMovimientoCtrl{
  public class RobotEnMovimiento extends EstadoAbstractoMovRobot implements ItfUsoMovimientoCtrl{
	private RobotStatus robotStatus;
    public  RobotEnMovimiento (MaquinaEstadoMovimientoCtrl maquinaEstados){
 //       this.inicializarMovimientoCtrl(robotId);
     super (maquinaEstados,MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotEnMovimiento);
     
    }
    @Override
    public synchronized boolean estamosEnDestino(String destinoId){
        return false;
    } 
    @Override
        public synchronized void moverAdestino(String identdest,Coordinate coordDestino, float velocidadCrucero) {   
           if ( identdest.equals(identDestino)){
              this.trazas.trazar (this.identComponente, " Se esta avanzando hacia el destino ", InfoTraza.NivelTraza.debug);
              if (velocidadCrucero<= 0)trazas.trazar(identComponente, "La velocidad debe ser mayor que cero. Se ignora la operacion", InfoTraza.NivelTraza.error);
              else this.velocidadCrucero = velocidadCrucero;
            }else { // cambair destino
               if (monitorizacionLlegadaDestino != null)this.monitorizacionLlegadaDestino.finalizar();
               this.velocidadCrucero = velocidadCrucero;
               this.destinoCoord = coordDestino;
               this.identDestino = identdest;
               this.robotposicionActual = this.monitorizacionLlegadaDestino.getCoordRobot();
				this.monitorizacionLlegadaDestino = new HebraMonitorizacionLlegada (this.identAgente,maquinaEstados,this.itfusoRecVisSimulador, this.robotStatus);        
               monitorizacionLlegadaDestino.inicializarDestino(identdest,this.robotposicionActual,coordDestino,velocidadCrucero);
               monitorizacionLlegadaDestino.start();
                  }
         }
    @Override
        public void cambiaVelocidad( float nuevaVelocidadCrucero) {
            this.velocidadCrucero = nuevaVelocidadCrucero;
        }
    @Override
        public synchronized void cambiaDestino(String identdest,Coordinate coordDestino) {
        // Habria que obtener la posicion actual y recalcular la distancia y el tiempo
        // lo dejamos con mover a destino desde la posicion inicial 
            this.destinoCoord = coordDestino;
            this.identDestino = identdest;
            this.monitorizacionLlegadaDestino.finalizar();
            moverAdestino(identDestino,destinoCoord,this.velocidadCrucero);
        }
    
    @Override
        public void parar(){
            if (monitorizacionLlegadaDestino!=null ) this.monitorizacionLlegadaDestino.finalizar();
            this.maquinaEstados.cambiarEstado (MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotParado).parar();
//            this.trazas.trazar (this.identAgente +"."+this.getClass().getSimpleName(), " transito al estado parado ", InfoTraza.NivelTraza.debug);
//            this.maquinaEstados.getEstadoActual().parar();
        }
    @Override
        public void continuar(){
            this.trazas.trazar (this.identAgente +"."+this.getClass().getSimpleName(), " ignoro la operacion porque estoy parado ", InfoTraza.NivelTraza.debug); 
        }
    @Override
        public void bloquear(){
            if (monitorizacionLlegadaDestino!=null ) this.monitorizacionLlegadaDestino.finalizar();
            this.maquinaEstados.cambiarEstado (MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotBloqueado).parar();
    }
        @Override
    public  Coordinate getCoordenadasActuales(){
        return this.monitorizacionLlegadaDestino.getCoordRobot();
    }
		@Override
		public HebraMonitorizacionLlegada getHebraMonitorizacionLlegadaDestino() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void setRobotStatus(RobotStatus robotStatus) {
			this.robotStatus = robotStatus;
		}
		public Coordinate getCoordenasDestino() {
			return this.destinoCoord;
		} 
}
