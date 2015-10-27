/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.AceptacionPropuesta;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VictimsToRescue;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.Rosace.objetivosComunes.AyudarVictima;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.InfoCompMovimiento;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.aplicaciones.recursos.recursoEstadistica.ItfUsoRecursoEstadistica;
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
public class FocalizarObjetivoMasPrioritario extends TareaSincrona{
		
    @Override
   public void ejecutar(Object... params) {
    int	velocidadCruceroPordefecto = 1;
//       ItfUsoRecursoEstadistica itfUsoRecursoEstadistica=null;    //Para recoger estadisticas del instante de envio de victimas desde el centro de contro    	
	   try {
             MisObjetivos misObjs = (MisObjetivos) params[0];
    //         Objetivo obj1 = (Objetivo)params[1];
  //           InfoParaDecidirQuienVa infoDecision = (InfoParaDecidirQuienVa)params[2];
             Focus focoActual = (Focus)params[1];
             Victim victima = (Victim) params[2];
//             AceptacionPropuesta propuestaAceptada = (AceptacionPropuesta) params[3];
//             InfoCompMovimiento infoComMov  = (InfoCompMovimiento)params[4];
             VictimsToRescue victimas = (VictimsToRescue)params[3];
             String identTarea = this.getIdentTarea();
             String nombreAgenteEmisor = this.getIdentAgente();    
             //Para anotar en el fichero cuál es mi coste
            // int coste = 0;   //El coste se define como el MAYOR ENTERO - VALOR DE LA FUNCION DE EVALUACION
                              //El que menor coste tiene es el que se hace cargo de la victima o dicho de otra manera
                              //El que mayor función de evaluación tiene es el que se hace cargo de la victima
//             String identVictim = propuestaAceptada.getidentObjectRefAcetPropuesta();
      //       int miEvaluacion = infoDecision.getMi_eval(); 
             int miEvaluacion = victima.getEstimatedCost();
             if (miEvaluacion!=-1) miEvaluacion = Integer.MAX_VALUE - miEvaluacion;
             else miEvaluacion = miEvaluacion;    //SI EL COSTE EL -1 INDICARIA QUE SE HA HECHO CARGO PERO QUE NO PUEDE IR (NO TIENE RECURSOS)
                                       
             //ACTUALIZAR ESTADISTICAS
        	    //Inicializar y recuperar la referencia al recurso de estadisticas        	
  //          itfUsoRecursoEstadistica = (ItfUsoRecursoEstadistica)ClaseGeneradoraRepositorioInterfaces.instance().
 //			 		                                                    obtenerInterfaz(NombresPredefinidos.ITF_USO + "RecursoEstadistica1");
//              itfUsoRecursoEstadistica = (ItfUsoRecursoEstadistica)NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(NombresPredefinidos.ITF_USO + "RecursoEstadistica1");
              long tiempoActual = System.currentTimeMillis(); 
//            String refVictima = obj1.getobjectReferenceId();
            //      	 itfUsoRecursoEstadistica.escribeEstadisticaFicheroTextoPlanoTRealAsignacionVictimasRobots(tiempoActual, refVictima, nombreAgenteEmisor, coste);
            ////////////////////////////////////////////////////////
            //ENVIAR INFORMACION AL AGENTE CONTROLADOR DEL SIMULADOR 
            String refVictima = victima.getName();
            Object[] valoresParametrosAccion = new Object[4];
            valoresParametrosAccion[0] = tiempoActual;
            valoresParametrosAccion[1] = refVictima;
            valoresParametrosAccion[2] = nombreAgenteEmisor;
            valoresParametrosAccion[3] = miEvaluacion;
         InfoContEvtMsgAgteReactivo msg = new InfoContEvtMsgAgteReactivo("victimaAsignadaARobot", valoresParametrosAccion);
            this.getComunicator().enviarInfoAotroAgente(msg, VocabularioRosace.IdentAgteControladorSimulador);  
                AyudarVictima nuevoObj = new AyudarVictima(refVictima);
                nuevoObj.setSolving() ;
                victimas.addVictimToRescue(victima);
                misObjs.addObjetivo(nuevoObj);
                focoActual.setFocusToObjetivoMasPrioritario(misObjs);
                Objetivo objActual = focoActual.getFoco();
                victima = victimas.getVictimToRescue(objActual.getobjectReferenceId());
//                 ItfUsoMovimientoCtrl itfcompMov = (ItfUsoMovimientoCtrl) infoComMov.getitfAccesoComponente();
//            itfcompMov.moverAdestino(objActual.getobjectReferenceId(), victima.getCoordinateVictim(), velocidadCruceroPordefecto); // se pondra la verlocidad por defecto 
            trazas.aceptaNuevaTrazaEjecReglas(identAgente, "Se ejecuta la tarea : " + identTarea + " Se genera el  objetivo:  "+ nuevoObj+
                    " Se actualiza el  foco al objetivo:  " + focoActual + "\n");
            trazas.aceptaNuevaTrazaEjecReglas(identAgente, "Se da orden al comp Movimiento  para salvar a la victima :  " + victima + "\n");
            System.out.println("\n" + identAgente + "Se ejecuta la tarea " + identTarea + " Se actualiza el  objetivo:  " + nuevoObj + "\n\n");
                this.getEnvioHechos().insertarHecho(nuevoObj);              
                this.getEnvioHechos().actualizarHechoWithoutFireRules(misObjs);
                 this.getEnvioHechos().actualizarHechoWithoutFireRules(victimas);
//                this.getEnvioHechos().eliminarHecho(propuestaAceptada);
                this.getEnvioHechos().actualizarHecho(focoActual);
                trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se ejecuta la tarea " + this.getIdentTarea()+
                                    " Se actualiza el  objetivo:  "+ nuevoObj, InfoTraza.NivelTraza.debug));
            System.out.println("\n"+nombreAgenteEmisor +"Se ejecuta la tarea " + this.getIdentTarea()+ " Se actualiza el  objetivo:  "+ nuevoObj+"\n\n" );
                          
             
       } catch (Exception e) {
			 e.printStackTrace();
       }
   }

}
