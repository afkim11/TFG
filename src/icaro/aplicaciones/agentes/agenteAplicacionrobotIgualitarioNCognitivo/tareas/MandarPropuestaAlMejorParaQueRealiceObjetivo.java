/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;
import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.ArrayList;

/**
 *
 * @author Francisco J Garijo
 */
public class MandarPropuestaAlMejorParaQueRealiceObjetivo  extends TareaSincrona {

	/**  */
    private InterfazUsoAgente agenteReceptor;
    private ArrayList agentesEquipo, respuestasAgentes,confirmacionesAgentes,nuevasEvaluacionesAgentes,empates;//resto de agentes que forman mi equipo
    private String nombreAgenteEmisor;
//    private ItfUsoRecursoTrazas trazas;
    private InfoParaDecidirQuienVa infoDecision;
    private String identDeEstaTarea ;
    private String nombreAgenteReceptor ;

    //private TimeOutRespuestas tiempoSinRecibirRespuesta; //no usado

    
	@Override
	public void ejecutar(Object... params) {
		try {
  //            trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
              Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];
              infoDecision = (InfoParaDecidirQuienVa)params[1];
                      //             nombreAgenteReceptor = (String)params[2];

              nombreAgenteEmisor = this.getAgente().getIdentAgente();
              identDeEstaTarea = this.getIdentTarea();

              try {
                   trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Ejecuta la Tarea :"+ identDeEstaTarea , InfoTraza.NivelTraza.debug));
   //               if (! infoDecision.getheInformadoAlmejorParaQueAsumaElObjetivo() ){ // si ya se le ha informado no se hace nada
                   nombreAgenteReceptor = infoDecision.dameIdentMejor();
                   PropuestaAgente miPropuesta = new PropuestaAgente (nombreAgenteEmisor);
                   miPropuesta.setMensajePropuesta(VocabularioRosace.MsgPropuesta_Para_Q_vayaOtro);
                   miPropuesta.setIdentObjectRefPropuesta(infoDecision.getidElementoDecision());
                   miPropuesta.setJustificacion(infoDecision.getMi_eval());
                   
                   trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor,"IdentObjetoPropuesta: " +infoDecision.getidElementoDecision()+ "Enviamos la propuesta: " + VocabularioRosace.MsgPropuesta_Para_Q_vayaOtro + "  Al agente " +nombreAgenteReceptor  , InfoTraza.NivelTraza.debug));
                   
                   ComunicacionAgentes comunicacion = new ComunicacionAgentes(nombreAgenteEmisor);
                   comunicacion.enviarInfoAotroAgente(miPropuesta,nombreAgenteReceptor );
                   
                        infoDecision.setheInformadoAlmejorParaQueAsumaElObjetivo(Boolean.TRUE);
                        this.getEnvioHechos().actualizarHecho(infoDecision);

                   //creo que el informe siguiente actualmente no se utiliza en las reglas
                   //     this.generarInformeOK(identDeEstaTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, VocabularioRosace.MsgPropuesta_Para_Q_vayaOtro);
                             //         trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Enviada Propuesta al agente " + agentesEquipo.size(), InfoTraza.NivelTraza.info));
                             //            tiempoSinRecibirRespuesta.start();
       //            }
		      } catch (Exception e) {
			       e.printStackTrace();
              }
        }
        catch(Exception e) {
			e.printStackTrace();
        }
    }

}
