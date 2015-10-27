/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;
import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.*;
import icaro.aplicaciones.recursos.recursoMorse.ItfUsoRecursoMorse;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import java.util.ArrayList;

/**
 * @author Francisco J Garijo
 * Se comprueba si la victima existe . Se busca al agente que tiene el rol de jefe
 * se realiza la evaluacion  y se le manda
 * se pone un temporizador para recibir la respuesta
 */
public class MandarPropuestaAlJefeParaRealizarObjetivo  extends TareaSincrona {
    private String nombreAgenteEmisor;
    private InfoEquipo miEquipo;
    private MisObjetivos misObjtvs;
    private Integer miEvalDeRespuesta;
    private Integer valorDisuasorioParaElquePideAcepteQueSoyYoElResponsable = 5000000;
    private  Integer valorParaExcluirmeDelObjetivo = -5000 ;
    private VictimsToRescue victimasRecibidas ;
    private Victim victimaCC,victimaEncontrada ;
    private RobotStatus robot;
    private Coordinate robotLocation;
	@Override
	public void ejecutar(Object... params) {
     //     Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];
          miEquipo = (InfoEquipo) params[0];
          victimaCC = (Victim) params[1]; 
          misObjtvs = (MisObjetivos) params[2];
       //   robot = (RobotStatus)params[4];
          victimasRecibidas = (VictimsToRescue) params[3];
          nombreAgenteEmisor = this.getIdentAgente();
          String identTarea = this.getIdentTarea();
          String nombreAgenteReceptor = getIdentAgteAsignadorTareas();
          if (nombreAgenteReceptor==null)
              trazas.trazar(nombreAgenteEmisor, "No se encuentra el agente asignador en  la Tarea :"+ identTarea , NivelTraza.error);
          else try {
              trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Ejecuta la Tarea :"+ identTarea , InfoTraza.NivelTraza.debug));
      // si el identificador esta entre mis objetivos es que esta resuelto , le mando un valor para que se desanime
     // en  otro caso puede ser que sea otro el que tenga el objetivo, en este caso él le mandará un valor grande
     // si no tengo noticias del objetivo le mando un valor pequeno para que vaya él
     // si coincide con el que estoy trabajando le mando el valor 
          //     if (identObjEvaluacion.equals(infoDecision.idElementoDecision)) miEvalDeRespuesta = infoDecision.getMi_eval();
          String identObjEvaluacion= victimaCC.getName(); 
                    if( misObjtvs.existeObjetivoConEsteIdentRef(identObjEvaluacion))miEvalDeRespuesta = valorDisuasorioParaElquePideAcepteQueSoyYoElResponsable;
                      else { // Miro en la tabla de vicitimas si tengo la victima, 
                        victimaEncontrada = victimasRecibidas.getVictimToRescue(identObjEvaluacion);
                        if(victimaEncontrada != null){ // tengo la victima miro si tengo el valor estimado
                            if (victimaEncontrada.isCostEstimated()) miEvalDeRespuesta = victimaEncontrada.getEstimatedCost();
                            else{ // calculo el coste y lo guardo en la victima
                               miEvalDeRespuesta= calcularCosteEstimadoVictima(victimaEncontrada);
                               victimaEncontrada.setEstimatedCost(miEvalDeRespuesta);
                               this.getEnvioHechos().actualizarHechoWithoutFireRules(victimaEncontrada);
                            }
                                }
                        else {// la victima no existe -> no se ha recibido el mensaje del CC. Calculo el coste y  meto los objetivos y la victima   
                            miEvalDeRespuesta= calcularCosteEstimadoVictima(victimaCC);
                            victimaCC.setEstimatedCost(miEvalDeRespuesta);
                            victimasRecibidas.addVictimToRescue(victimaCC); 
                            this.getEnvioHechos().actualizarHechoWithoutFireRules(victimasRecibidas);
                            this.getEnvioHechos().insertarHechoWithoutFireRules(victimaCC);
                        }
                }      
              EvaluacionAgente miEvaluacion = new EvaluacionAgente (nombreAgenteEmisor, miEvalDeRespuesta );
                               miEvaluacion.setObjectEvaluationId(identObjEvaluacion);
              PropuestaAgente miPropuesta = new PropuestaAgente (nombreAgenteEmisor);
                   miPropuesta.setMensajePropuesta(VocabularioRosace.MsgPropuesta_Para_Aceptar_Objetivo);
                   miPropuesta.setIdentObjectRefPropuesta(identObjEvaluacion);
                   miPropuesta.setJustificacion(miEvaluacion);
                   this.getComunicator().enviarInfoAotroAgente(miPropuesta,nombreAgenteReceptor );
                   this.generarInformeTemporizadoFromConfigProperty(VocabularioRosace.IdentTareaTimeOutRespuestaJefe, null, nombreAgenteEmisor, null);                 
                   trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Enviamos la propuesta " + miPropuesta, InfoTraza.NivelTraza.info));               
		  } catch (Exception e) {
			  e.printStackTrace();
          }
   }
         private String getIdentAgteAsignadorTareas(){
         ArrayList<String> identsConMismoRol = miEquipo.getTeamMemberIDsWithThisRol(VocabularioRosace.IdentRolAgteDistribuidorTareas);  
               if (identsConMismoRol.isEmpty())trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "No se ha encontrado un agente con el rol :  "+ VocabularioRosace.IdentAgteDistribuidorTareas, InfoTraza.NivelTraza.error)); 
                else if (identsConMismoRol.size()>1) trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Hay mas de un agente con el rol : "+ VocabularioRosace.IdentAgteDistribuidorTareas, InfoTraza.NivelTraza.error)); 
                    else return identsConMismoRol.get(0);
               return null;
         }
        private int calcularCosteEstimadoVictima(Victim victima){     
          try{    		   
                ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
                ItfUsoRecursoMorse morseResourceRef;
       		    morseResourceRef = (ItfUsoRecursoMorse) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + 
       				                      "RecursoMorse1");
       		    robotLocation = morseResourceRef.getGPSInfo(nombreAgenteEmisor);
       		           
       	          }
   	              catch (Exception ex){
       		              ex.printStackTrace();
       	          }  
          Coste coste = new Coste();
          robot = miEquipo.getTeamMemberStatus(nombreAgenteEmisor);
          return coste.CalculoCosteAyudarVictima(nombreAgenteEmisor, robotLocation, robot, victima, victimasRecibidas, misObjtvs, "FuncionEvaluacion3");                      
        }	
}
