/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.PeticionAgente;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Francisco J Garijo
 */
public class PedirEvaluacionesQueFaltan extends TareaSincrona{

	   private InterfazUsoAgente agenteReceptor;
                  //        private ArrayList agentesEquipo, respuestasAgentes,confirmacionesAgentes,nuevasEvaluacionesAgentes,empates;//resto de agentes que forman mi equipo
        private int mi_eval, mi_eval_nueva;
        private String nombreAgenteEmisor;
//        private ItfUsoRecursoTrazas trazas;
        private InfoParaDecidirQuienVa infoDecision;
        //private TimeOutRespuestas tiempoSinRecibirRespuesta;  //no usado
        
  @Override
  public void ejecutar(Object... params) {
		try {

                      //         InterfazUsoAgente    itfUsoPropiadeEsteAgente = (InterfazUsoAgente) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(nombreAgenteEmisor);
                      //           tiempoSinRecibirRespuesta = new TimeOutRespuestas(5000,itfUsoPropiadeEsteAgente, notifTimeoutRespuesta);
                      // para generar el informe con referencia al objetivo en que se ejecuta la tarea, se le pasa el ident del objetivo en el primer parametro
   //           trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
              Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];
              String identDeEstaTarea = this.getIdentTarea();
              nombreAgenteEmisor = this.getAgente().getIdentAgente();
              InfoParaDecidirQuienVa infoDecisionAgente = (InfoParaDecidirQuienVa) params[1];
              Victim victima = (Victim) params[2];
              try {
                   trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Ejecuta la Tarea :"+ identDeEstaTarea , InfoTraza.NivelTraza.debug));
                  if (!infoDecisionAgente.hanLlegadoTodasLasEvaluaciones){ // si no han llegado todas las evaluaciones
                   
                   ComunicacionAgentes comunicacion = new ComunicacionAgentes(nombreAgenteEmisor );            
                   for(int i = 0; i< infoDecisionAgente.getAgentesEquipo().size(); i++){
                       Integer evaluacionAgente = (Integer)infoDecisionAgente.getEvaluacionesRecibidas().get(i);
                       if(evaluacionAgente == 0){//si aun no tenemos la evaluacion , queremos que nos la vuelva a mandar
                          String agenteReceptor = (String)infoDecisionAgente.getAgentesEquipo().get(i);
                          
                          trazas.aceptaNuevaTrazaEjecReglas(nombreAgenteEmisor, "!!!!!!!!Pidiendo evaluacion al agente "+ agenteReceptor);
                          
                                  //                        mandaMensajeAAgenteId("PeticionReenvioEvaluaciones",agenteReceptor );
                                  PeticionAgente peticionEval = new PeticionAgente(this.getIdentAgente());
                                  peticionEval.setidentObjectRefPeticion(objetivoEjecutantedeTarea.getobjectReferenceId());
                                  peticionEval.setMensajePeticion(VocabularioRosace.MsgPeticionEnvioEvaluaciones);
                                  peticionEval.setJustificacion(victima); // para que se sepa qué evaluacion le pedimos
                                  comunicacion.enviarInfoAotroAgente(peticionEval, agenteReceptor);
                            //      comunicacion.enviarInfoConMomentoCreacionAotroAgente(peticionEval, agenteReceptor);
                       }
                   }
                   }
                       //            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Pedimos que nos reenvien las evaluaciones al agente: " +agenteReceptor, InfoTraza.NivelTraza.debug));
              } catch (Exception ex) {
                 trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Error al enviar peticionRecibirRespuestasRestantes "+ ex, InfoTraza.NivelTraza.error));
              }
        
              //NO SE LA NECESIDAD DE LAS DOS SENTENCIAS SIGUIENTES (el informe no se usa y el infoDecisionAgente solo se ha consultado y no se ha modificado)
              this.generarInformeOK(identDeEstaTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, "PeticionDeEvaluacionesQueFaltanRealizada");
                    
            //  this.getEnvioHechos().insertarHecho(infoDecisionAgente);
        }
        catch (Exception e) {
			e.printStackTrace();
        }
  }

  
  //El siguiente metodo no se usa actualmente aqui
  public void mandaMensajeAAgenteId(Object contenido,String identAgenteReceptor){

            // Este método crea un evento con la información de entrada y se le envía al agente REACTIVO que se indique por medio de
            // la  interfaz de uso
            MensajeSimple mensajeaEnviar = null;
         InterfazUsoAgente itfUsoAgenteReceptor = null;

//           Se verifica que la interfaz del aegente no es vacia

        try {


                itfUsoAgenteReceptor = (InterfazUsoAgente) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz
                    (NombresPredefinidos.ITF_USO+identAgenteReceptor);
               }
               catch (Exception e) {
                   Logger.getLogger(MandarEvalATodos.class.getName()).log(Level.SEVERE, null, e);
//                   logger.error("Ha habido un problema enviar un  evento al agente "+IdentAgenteReceptor);
    			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor,
				"Ha habido un problema al enviar el mensaje con informacion "+ contenido.toString()+"al   agente "+identAgenteReceptor,
					  InfoTraza.NivelTraza.error));
               }


         //   En primer lugar se crea el mensaje con  la informacion de entrada
            if (mensajeaEnviar == null){
                mensajeaEnviar = new MensajeSimple(contenido, nombreAgenteEmisor, identAgenteReceptor);
             }
        //    else{eventoaEnviar = new EventoRecAgte(input,infoComplementaria, nombreAgenteEmisor, IdentAgenteReceptor);}
             // Obtener la interfaz de uso del agente reactivo con el que se quiere comunicar
             try {
			itfUsoAgenteReceptor.aceptaMensaje(mensajeaEnviar);
                        trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se manda 1 mensaje de  " + contenido.toString() + " al agente  "+ identAgenteReceptor, InfoTraza.NivelTraza.debug));
            }catch (Exception e) {
	//	logger.error("Ha habido un problema enviar un  evento al agente "+IdentAgenteReceptor, e);
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor,
			  "Ha habido un problema enviar el mensaje con informacion "+ contenido.toString()+"al   agente "+identAgenteReceptor,
                        	  InfoTraza.NivelTraza.error));
            }

    }

}
