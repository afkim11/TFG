/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;


import icaro.aplicaciones.Rosace.informacion.AceptacionPropuesta;
import icaro.aplicaciones.Rosace.informacion.Coordinate;
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
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.VisorEscenariosRosace;
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
	
	private float velocidadCruceroPordefecto;
	public static int perimetroDeVision = 50;
	private static int tipoActuacion=1;
	@Override
	public void ejecutar(Object... params) {
		velocidadCruceroPordefecto = 1;
		//       ItfUsoRecursoEstadistica itfUsoRecursoEstadistica=null;    //Para recoger estadisticas del instante de envio de victimas desde el centro de contro    	
		try {
			MisObjetivos misObjs = (MisObjetivos) params[0];
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
					velocidadCruceroPordefecto = (float)0.75;
					int alto = VisorEscenariosRosace.alto,ancho = VisorEscenariosRosace.ancho;
					boolean finalizado = false;
					double x = perimetroDeVision,y=perimetroDeVision;
					Coordinate coor = new Coordinate(x,y,0.5);
					itfcompMov.moverAdestino(VocabularioRosace.MsgExploraTerreno, coor, velocidadCruceroPordefecto,tipoActuacion);
					while(!finalizado){
						x = ancho - perimetroDeVision;
						coor = new Coordinate(x,y,0.5);
						itfcompMov.moverAdestino(VocabularioRosace.MsgExploraTerreno, coor, velocidadCruceroPordefecto,tipoActuacion);
						y = y + 2*perimetroDeVision;
						coor = new Coordinate(x,y,0.5);
						if(y>alto){
							finalizado=true;
							break;
						}
						itfcompMov.moverAdestino(VocabularioRosace.MsgExploraTerreno, coor, velocidadCruceroPordefecto,tipoActuacion);
						x = perimetroDeVision;
						coor = new Coordinate(x,y,0.5);
						itfcompMov.moverAdestino(VocabularioRosace.MsgExploraTerreno, coor, velocidadCruceroPordefecto,tipoActuacion);
						y = y +2*perimetroDeVision;
						if(y>alto){
							finalizado=true;
							break;
						}
						coor = new Coordinate(x,y,0.5);
						itfcompMov.moverAdestino(VocabularioRosace.MsgExploraTerreno, coor, velocidadCruceroPordefecto,tipoActuacion);
					}
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
