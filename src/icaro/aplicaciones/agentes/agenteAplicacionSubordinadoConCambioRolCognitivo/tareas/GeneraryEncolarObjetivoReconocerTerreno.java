/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.AceptacionPropuesta;
import icaro.aplicaciones.Rosace.informacion.RobotStatus;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VictimsToRescue;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.Rosace.objetivosComunes.AyudarVictima;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos.ReconocerTerreno;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.InfoCompMovimiento;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.aplicaciones.recursos.recursoEstadistica.ItfUsoRecursoEstadistica;
import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.ItfUsoRecursoPersistenciaEntornosSimulacion;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author FGarijo
 */
public class GeneraryEncolarObjetivoReconocerTerreno extends TareaSincrona{
	
	
	private ItfUsoMovimientoCtrl itfcompMov;
	
	private int velocidadCruceroPordefecto;
	
	
	@Override
	public void ejecutar(Object... params) {
		velocidadCruceroPordefecto = 1;
		//       ItfUsoRecursoEstadistica itfUsoRecursoEstadistica=null;    //Para recoger estadisticas del instante de envio de victimas desde el centro de contro    	
		try {
			MisObjetivos misObjs = (MisObjetivos) params[0];
			//         Objetivo obj1 = (Objetivo)params[1];
			//           InfoParaDecidirQuienVa infoDecision = (InfoParaDecidirQuienVa)params[2];
			Focus focoActual = (Focus)params[1];
			AceptacionPropuesta propuestaAceptada = (AceptacionPropuesta) params[2];
			InfoCompMovimiento infoComMov  = (InfoCompMovimiento)params[3];
			RobotStatus robotStatus = (RobotStatus) params[4];
			String identTarea = this.getIdentTarea();
			String nombreAgenteEmisor = this.getIdentAgente();    


			long tiempoActual = System.currentTimeMillis(); 
			ReconocerTerreno nuevoObj = new ReconocerTerreno();
			nuevoObj.setSolving();
			misObjs.addObjetivo(nuevoObj);
			focoActual.setFocusToObjetivoMasPrioritario(misObjs);
			itfcompMov = (ItfUsoMovimientoCtrl) infoComMov.getitfAccesoComponente();
			itfcompMov.setRobotStatus(robotStatus);
					
			Thread t = new Thread(){
				
				public void run(){
					/*boolean finalizado = false;
					while(!finalizado){
						itfcompMov.moverAdestino(victima.getName(), victima.getCoordinateVictim(), velocidadCruceroPordefecto);
					}*/
				}
			};
			t.start();
			
			
			
			
			trazas.aceptaNuevaTrazaEjecReglas(identAgente, "Se ejecuta la tarea : " + identTarea + " Se genera el  objetivo:  "+ nuevoObj+
					" Se actualiza el  foco al objetivo:  " + focoActual + "\n");
			trazas.aceptaNuevaTrazaEjecReglas(identAgente, "Se da orden al comp Movimiento  para reconocer el terreno\n");
			System.out.println("\n" + identAgente + "Se ejecuta la tarea " + identTarea + " Se actualiza el  objetivo:  " + nuevoObj + "\n\n");
			this.getEnvioHechos().insertarHecho(nuevoObj);              
			this.getEnvioHechos().actualizarHechoWithoutFireRules(misObjs);
			this.getEnvioHechos().eliminarHecho(propuestaAceptada);
			this.getEnvioHechos().actualizarHecho(focoActual);
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se ejecuta la tarea " + this.getIdentTarea()+
					" Se actualiza el  objetivo:  "+ nuevoObj, InfoTraza.NivelTraza.debug));
			System.out.println("\n"+nombreAgenteEmisor +"Se ejecuta la tarea " + this.getIdentTarea()+ " Se actualiza el  objetivo:  "+ nuevoObj+"\n\n" );


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
