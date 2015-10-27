/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.Rosace.tareasComunes;
import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.Rosace.informacion.RobotStatus;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.Rosace.utils.AccesoPropiedadesGlobalesRosace;
import icaro.aplicaciones.Rosace.informacion.InfoRolAgente;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.ArrayList;

/**
 *
 * @author Francisco J Garijo
 */
   public class ContactarMiembrosEquipo  extends TareaSincrona {
   private InterfazUsoAgente agenteReceptor;
   private ArrayList <String> agentesEquipo;//resto de agentes que forman mi equipo                                
   private int mi_eval, mi_eval_nueva;
   private String nombreAgenteEmisor;
 
   

   // private TimeOutRespuestas tiempoSinRecibirRespuesta;  //no usado

	@Override
	public void ejecutar(Object... params) {
		try {     
              RobotStatus miStatus = (RobotStatus)params[0];    
             InfoEquipo equipoInfo = (InfoEquipo)params[1];  
              nombreAgenteEmisor = this.getAgente().getIdentAgente();
              agentesEquipo = equipoInfo.getTeamMemberIDs();
              trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Ejecuta la Tarea :"+ identTarea , InfoTraza.NivelTraza.debug));
                        //            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Enviamos la evaluacion " + miEvaluacion, InfoTraza.NivelTraza.masterIA));          
              InfoRolAgente mirol = new InfoRolAgente(nombreAgenteEmisor,equipoInfo.getTeamId(),miStatus.getIdRobotRol(),VocabularioRosace.IdentMisionEquipo);
              trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Enviamos informacion de Rol " + mirol.toString(), InfoTraza.NivelTraza.info));  
              this.getComunicator().informaraGrupoAgentes(mirol, agentesEquipo);
              equipoInfo.setinicioContactoConEquipo();
              equipoInfo.setidentMiRolEnEsteEquipo(miStatus.getIdRobotRol());
              this.getEnvioHechos().actualizarHechoWithoutFireRules(equipoInfo);
              trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Numero de agentes de los que espero respuesta:" + agentesEquipo.size(), InfoTraza.NivelTraza.info));     
              this.generarInformeTemporizadoFromConfigProperty(VocabularioRosace.IdentTareaTimeOutContactarMiembrosEquipo,null,nombreAgenteEmisor, null);
              // en le caso de que ya la haya enviado la evaluacion no hago nada
		} catch (Exception e) {
			e.printStackTrace();
        }
    }
   
}