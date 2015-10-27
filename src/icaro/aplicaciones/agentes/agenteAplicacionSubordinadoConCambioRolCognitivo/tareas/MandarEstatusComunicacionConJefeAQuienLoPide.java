/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

/**
 *
 * @author Francisco J Garijo
 */
public class MandarEstatusComunicacionConJefeAQuienLoPide  extends TareaSincrona {
   

/*
    private InterfazUsoAgente agenteReceptor;
    private ArrayList agentesEquipo, respuestasAgentes,confirmacionesAgentes,nuevasEvaluacionesAgentes,empates;//resto de agentes que forman mi equipo
    private int mi_eval, mi_eval_nueva;
    private String nombreAgenteEmisor;
    private PeticionAgente  peticionRecibida;
    private ItfUsoRecursoTrazas trazas;
    private InfoEstatusComunicacionConOtroAgente infoEstatusComOtroAgente;
    private MisObjetivos misObjtvs;
    private String identObjEvaluacion;
    private String nombreAgenteQuePideLaEvaluacion ;
    private Integer miEvalDeRespuesta;
    private Integer valorDisuasorioParaElquePideAcepteQueSoyYoElResponsable = 5000000;
    private  Integer valorParaExcluirmeDelObjetivo = -5000 ;
    private VictimsToRescue victimasRecibidas ;
    private Victim victimEnPeticion ;
    private RobotStatus robot;
    private Coordinate robotLocation;
    //private TimeOutRespuestas tiempoSinRecibirRespuesta; //no usado
    
	@Override
	public void ejecutar(Object... params) {
		
          trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
  //        Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];
          infoEstatusComOtroAgente = (InfoEstatusComunicacionConOtroAgente)params[1];
          peticionRecibida = (PeticionAgente) params[2]; 
          InfoEquipo equipoInfo = (InfoEquipo) params[3];
          nombreAgenteEmisor = this.getIdentAgente();
                       //             String identTareaLong = getClass().getName();
          String identTarea = this.getIdentTarea();
                       //             agentesEquipo = infoDecision.ObtenerNombreAgentesDelEquipo("robotMasterIA", nombreAgenteEmisor);
          agentesEquipo = equipoInfo.getTeamMemberIDs();
          identObjEvaluacion = peticionRecibida.getidentObjectRefPeticion();
          nombreAgenteQuePideLaEvaluacion= peticionRecibida.getIdentAgente();
         
          try {
              trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Ejecuta la Tarea :"+ identTarea , InfoTraza.NivelTraza.debug));
      
                        }
                }
              this.getEnvioHechos().eliminarHecho(peticionRecibida);
              EvaluacionAgente miEvaluacion = new EvaluacionAgente (nombreAgenteEmisor, miEvalDeRespuesta );
                               miEvaluacion.setObjectEvaluationId(identObjEvaluacion);
              trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Enviamos la evaluacion " + miEvaluacion, InfoTraza.NivelTraza.info));
                              
              this.getComunicator().enviarInfoAotroAgente(miEvaluacion, nombreAgenteQuePideLaEvaluacion);
              this.generarInformeOK(identTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, VocabularioRosace.ResEjTaskMiEvaluacionEnviadaAlAgtesQLaPide+ nombreAgenteQuePideLaEvaluacion);
              
              trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "EvaluacionEnviadaAlAgente : "+ nombreAgenteQuePideLaEvaluacion, InfoTraza.NivelTraza.info));
                       //            tiempoSinRecibirRespuesta.start();

		  } catch (Exception e) {
			  e.printStackTrace();
          }
   }
        private int calcularCosteEstimadoVictima(){
          
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
          return coste.CalculoCosteAyudarVictima(nombreAgenteEmisor, robotLocation, robot, victimEnPeticion, victimasRecibidas, misObjtvs, "FuncionEvaluacion3");
                              
        }
 */
     @Override
	public void ejecutar(Object... params) {}
}
