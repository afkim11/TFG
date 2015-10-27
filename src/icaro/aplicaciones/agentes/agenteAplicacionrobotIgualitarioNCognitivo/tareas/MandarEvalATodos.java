/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;
import icaro.aplicaciones.Rosace.informacion.EvaluacionAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.ArrayList;


/**
 *
 * @author Francisco J Garijo
 */
   public class MandarEvalATodos  extends TareaSincrona {
    //public class MandarEvalATodos  extends TareaComunicacion {
	/**  */
   private InterfazUsoAgente agenteReceptor;
   private ArrayList <String> agentesEquipo;//resto de agentes que forman mi equipo                                
   private int mi_eval, mi_eval_nueva;
   private String nombreAgenteEmisor;
//   private ItfUsoRecursoTrazas trazas;
   private InfoParaDecidirQuienVa infoDecision;

	@Override
	public void ejecutar(Object... params) {
		try {
                           
 //             trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;              
              //El primer parametro es el objetivo DecidirQuienVa que tenia el foco en el momento de ejecutar la regla
              //"Ya tengo la evaluacion para realizar el objetivo.Se lo mando al resto". En su parte derecha llama a esta tarea
              Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];    
              infoDecision = (InfoParaDecidirQuienVa)params[1];
              EvaluacionAgente miEvaluacion = (EvaluacionAgente) params[2]; // redefino miEvaluacion
              
              nombreAgenteEmisor = this.getAgente().getIdentAgente();
              String identTareaLong = getClass().getName();
         //     miEvaluacion.identAgente = nombreAgenteEmisor;
         //     miEvaluacion.objectEvaluationId = infoDecision.idElementoDecision;
        //      miEvaluacion.valorEvaluacion = infoDecision.mi_eval;
                        //             agentesEquipo = infoDecision.ObtenerNombreAgentesDelEquipo("robotMasterIA", nombreAgenteEmisor);
              if(!infoDecision.miEvaluacionEnviadaAtodos){
              agentesEquipo = infoDecision.getIdentsAgentesEquipo();
              trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Ejecuta la Tarea :"+ identTarea , InfoTraza.NivelTraza.debug));
                        //            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Enviamos la evaluacion " + miEvaluacion, InfoTraza.NivelTraza.masterIA));
              trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Enviamos la evaluacion " + infoDecision.getMi_eval(), InfoTraza.NivelTraza.info));            
           //   infoDecision.setMi_eval(miEvaluacion.getEvaluacion()); Ya tengo mi evaluacion por tanto no la redefino
                        //         this.mandaMensajeATodos(miEvaluacion);
                        //            ComunicacionAgentes comunicacion = new ComunicacionAgentes(nombreAgenteEmisor);
                        //            comunicacion.informaraGrupoAgentes(miEvaluacion, agentesEquipo);
             this.getComunicator().informaraGrupoAgentes(miEvaluacion, agentesEquipo);
         //   this.getComunicator().informarConMomentoCreacionaGrupoAgentes(miEvaluacion, agentesEquipo);
              infoDecision.setRespuestasEsperadas(agentesEquipo.size());
              infoDecision.setMiEvaluacionEnviadaAtodos(Boolean.TRUE);
              this.getEnvioHechos().eliminarHechoWithoutFireRules(miEvaluacion);
              this.getEnvioHechos().actualizarHecho(infoDecision);
       //       this.generarInformeOK(identTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, VocabularioRosace.ResEjTaskMiEvalucionEnviadaAlEquipo);
              trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Numero de agentes de los que espero respuesta:" + agentesEquipo.size(), InfoTraza.NivelTraza.info));
                        //          Generacion de untemporizador que emitira un informe ;
                        //            new InformeTimeout(5000, this.getEnvioHechos(), new InformeDeTarea(identTarea,  nombreAgenteEmisor, VocabularioRosace.MsgTimeoutRecibirEvaluaciones));
//              this.generarInformeTemporizado(configConstantesSimulacion.TimeTimeoutRecibirEvaluaciones ,            		  
//            		                         identTarea,          objetivoEjecutantedeTarea, 
//            		                         nombreAgenteEmisor,  VocabularioRosace.MsgTimeoutRecibirEvaluaciones+objetivoEjecutantedeTarea.getID());
// Interesa dar un nombre especifico a la tarea que genera la temporizacion. El informe debe ser tambien especifico se pone el identificador de la victima      
              //Leo el valor time de las propiedades globales de la descripcion de la organizacion
   //           long time = (long)AccesoPropiedadesGlobalesRosace.getTimeTimeoutMilisegundosRecibirEvaluaciones();

              this.generarInformeTemporizadoFromConfigProperty(VocabularioRosace.IdentTareaTimeOutRecibirEvaluaciones1,  objetivoEjecutantedeTarea, 
                      nombreAgenteEmisor,  infoDecision.getidElementoDecision());
              
//              this.generarInformeTemporizado(configConstantesSimulacion.TimeTimeoutRecibirEvaluaciones ,            		  
//                      VocabularioRosace.IdentTareaTimeOutRecibirEvaluaciones1,  objetivoEjecutantedeTarea, 
//                      nombreAgenteEmisor,  infoDecision.getidElementoDecision());
              
             } // en le caso de que ya la haya enviado la evaluacion no hago nada
		} catch (Exception e) {
			e.printStackTrace();
        }
    }
   
}