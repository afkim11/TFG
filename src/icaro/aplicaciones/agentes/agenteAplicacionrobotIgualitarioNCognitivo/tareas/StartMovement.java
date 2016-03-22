/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Tarea;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import fr.laas.openrobots.jmorse.components.destination.Coordinate;

/**
 *
 * @author Francisco J Garijo
 */
public class StartMovement extends Tarea{

   @Override
   public void ejecutar(Object... params) {
	  try {
           Coordinate initialTargetCoordinate = (Coordinate)params[0];
           
           trazas.aceptaNuevaTraza(new InfoTraza(this.getIdentAgente(),"--------------------> Start Movement task: VOY A ...." + initialTargetCoordinate.toString(),InfoTraza.NivelTraza.info));
           
    	   ItfUsoRepositorioInterfaces ItfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();/*
    	   try{    		   
    		   ItfUsoRecursoMorse morseResourceRef;
    		   morseResourceRef = (ItfUsoRecursoMorse) ItfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + 
    				   "RecursoMorse1");
//    		   morseResourceRef.goToLocation(this.getIdentAgente(), initialTargetCoordinate);

    	   }
	         catch (Exception ex){
    		   ex.printStackTrace();
    	   }*/
	  }
        catch (Exception e) {
			e.printStackTrace();
      }
   }
}


/*             
Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];
     String identTarea = this.getIdentTarea();
     String nombreAgenteEmisor = this.getIdentAgente();
//     InfoParaDecidirQuienVa infoDecisionAgente = (InfoParaDecidirQuienVa) params[1];
//            EvaluacionAgente evaluacionRecibida = (EvaluacionAgente) params[2];
      InfoParaDecidirQuienVa infoDecisionAgente = new InfoParaDecidirQuienVa(nombreAgenteEmisor);

// se envía al  procesador de objetivos para que lo trate
     this.getEnvioHechos().insertarHecho(infoDecisionAgente);
     this.generarInformeOK(identTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, infoDecisionAgente);
*/             
