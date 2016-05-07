/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;


import java.util.ArrayList;

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
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Informe;
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
	public final static int perimetroDeVision = 50;
	private static int tipoActuacion=1;
	private ArrayList<Coordinate> puntosAExplorar;
	protected ReconocerTerreno rec;
	@Override
	public void ejecutar(Object... params) {
		velocidadCruceroPordefecto = (float)0.5;  	
		try {
			MisObjetivos misObjs = (MisObjetivos) params[0];
			Focus focoActual = (Focus)params[1];
			AceptacionPropuesta propuestaAceptada = (AceptacionPropuesta) params[2];
			InfoCompMovimiento infoComMov  = (InfoCompMovimiento)params[3];
			RobotStatus robotStatus = (RobotStatus) params[4];
			Informe inf = (Informe) params[5];
			
			
			String identTarea = this.getIdentTarea();
			String nombreAgenteEmisor = this.getIdentAgente();    
			String value = (String)inf.getContenidoInforme();
			String intValue = value.replaceAll("[^0-9]", ""); // returns 123
			int idNum = Integer.parseInt(intValue);
			
			
		
			long tiempoActual = System.currentTimeMillis(); 
			rec = new ReconocerTerreno(idNum);
			rec.setSolving();
			misObjs.addObjetivo(rec);
			focoActual.setFocusToObjetivoMasPrioritario(misObjs);
			itfcompMov = (ItfUsoMovimientoCtrl) infoComMov.getitfAccesoComponente();
			itfcompMov.setRobotStatus(robotStatus);
			
			inicializarPuntosExploracion();
			
			Thread t = new Thread(){
				public void run(){
					for(int i=0;i<puntosAExplorar.size();i++){
						
						Coordinate coor = puntosAExplorar.get(i);
						itfcompMov.moverAdestino(VocabularioRosace.MsgExploraTerreno, coor, velocidadCruceroPordefecto,tipoActuacion);
						
					}
				}
				/*public void run(){
					boolean last = false;
					int alto = generarYFin(),ancho = VisorEscenariosRosace.ancho;
					boolean finalizado = false;
					double x = perimetroDeVision,y=generarYIni();
					Coordinate coor = new Coordinate(x,y,0.5);
					itfcompMov.moverAdestino(VocabularioRosace.MsgExploraTerreno, coor, velocidadCruceroPordefecto,tipoActuacion);
					while(!finalizado){
						x = ancho - perimetroDeVision;
						coor = new Coordinate(x,y,0.5);
						itfcompMov.moverAdestino(VocabularioRosace.MsgExploraTerreno, coor, velocidadCruceroPordefecto,tipoActuacion);
						y = y + 2*perimetroDeVision;
						if(y>alto){
							if(alto >= 700 || last == true){
								finalizado=true;
								break;
							}
							else{
								last = true;
								y = alto-perimetroDeVision;
							}
							
						}
						coor = new Coordinate(x,y,0.5);
						itfcompMov.moverAdestino(VocabularioRosace.MsgExploraTerreno, coor, velocidadCruceroPordefecto,tipoActuacion);
						x = perimetroDeVision;
						coor = new Coordinate(x,y,0.5);
						itfcompMov.moverAdestino(VocabularioRosace.MsgExploraTerreno, coor, velocidadCruceroPordefecto,tipoActuacion);
						y = y +2*perimetroDeVision;
						if(y>alto){
							if(alto >= 700 || last == true){
								finalizado=true;
								break;
							}
							else{
								last = true;
								y = alto-perimetroDeVision;
							}
						}
						coor = new Coordinate(x,y,0.5);
						itfcompMov.moverAdestino(VocabularioRosace.MsgExploraTerreno, coor, velocidadCruceroPordefecto,tipoActuacion);
					}
				}*/
			};
			t.start();
			
			
			
			
			trazas.aceptaNuevaTrazaEjecReglas(identAgente, "Se ejecuta la tarea : " + identTarea + " Se genera el  objetivo:  "+ rec+
					" Se actualiza el  foco al objetivo:  " + focoActual + "\n");
			trazas.aceptaNuevaTrazaEjecReglas(identAgente, "Se da orden al comp Movimiento  para reconocer el terreno\n");
			System.out.println("\n" + identAgente + "Se ejecuta la tarea " + identTarea + " Se actualiza el  objetivo:  " + rec + "\n\n");
			this.getEnvioHechos().insertarHecho(rec);              
			this.getEnvioHechos().actualizarHechoWithoutFireRules(misObjs);
			this.getEnvioHechos().eliminarHecho(propuestaAceptada);
			this.getEnvioHechos().actualizarHecho(focoActual);
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se ejecuta la tarea " + this.getIdentTarea()+
					" Se actualiza el  objetivo:  "+ rec, InfoTraza.NivelTraza.debug));
			System.out.println("\n"+nombreAgenteEmisor +"Se ejecuta la tarea " + this.getIdentTarea()+ " Se actualiza el  objetivo:  "+ rec+"\n\n" );


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void inicializarPuntosExploracion() {
		int x = perimetroDeVision,y = generarYIni(),ancho = VisorEscenariosRosace.ancho,alto = generarYFin(),miperimetro = perimetroDeVision;
		puntosAExplorar = new ArrayList<Coordinate>();
		
		
		
		while(y <= alto){
			puntosAExplorar.add(new Coordinate(x,y,0.0));
			if(miperimetro>0){
				if(x<ancho){
					x += 2*miperimetro;
				}
				if(x>ancho){
					x = ancho - perimetroDeVision;
					//puntosAExplorar.add(new Coordinate(x,y,0.0));
					miperimetro = -miperimetro;
					y += 2*perimetroDeVision;
				}
			}
			else{
				if(x>=0){
					x +=2*miperimetro;
				}
				if(x<0){
					x = perimetroDeVision;
					//puntosAExplorar.add(new Coordinate(x,y,0.0));
					miperimetro = -miperimetro;
					y += 2*perimetroDeVision;
				}
			}
		}
		
		System.out.println("Hola");
		
	}

	private int generarYIni(){
		int iniY = (700 * rec.getRobotId()) / VocabularioRosace.numeroReconocedores;
		return iniY + perimetroDeVision;
	}
	private int generarYFin(){
		int finY = (700 * (rec.getRobotId()+1)) / VocabularioRosace.numeroReconocedores;
		if(finY == 700)
			return finY - perimetroDeVision;
		else return finY;
	}

}
