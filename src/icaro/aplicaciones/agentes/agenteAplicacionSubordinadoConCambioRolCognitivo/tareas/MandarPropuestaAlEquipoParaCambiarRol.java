
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.informacion.InfoCambioRolAgente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author Francisco J Garijo
 */
public class MandarPropuestaAlEquipoParaCambiarRol  extends TareaSincrona {
    private String nombreAgenteEmisor;
    private String identDeEstaTarea ;   
	@Override
	public void ejecutar(Object... params) {
           InfoEquipo miEquipo = (InfoEquipo)params[0];
 //          PropuestaAgente propuestaRecibida = (PropuestaAgente)params[1];
           nombreAgenteEmisor = this.getIdentAgente();
           identDeEstaTarea = this.getIdentTarea();
           try {
                 trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Ejecuta la Tarea :"+ identDeEstaTarea , InfoTraza.NivelTraza.debug));    
                  PropuestaAgente miPropuesta = new PropuestaAgente (nombreAgenteEmisor);
                   miPropuesta.setMensajePropuesta(VocabularioRosace.MsgPropuesta_CambioRolaIgualitario);
                   miPropuesta.setJustificacion(VocabularioRosace.MsgJustificPetionEstatusComunConJefe);
                   this.getComunicator().informaraGrupoAgentes(miPropuesta,miEquipo.getTeamMemberIDsWithMyRol() );
                   InfoCambioRolAgente infoCambioRol = new InfoCambioRolAgente(nombreAgenteEmisor,miEquipo,VocabularioRosace.MsgPropuesta_CambioRolaIgualitario);
                    infoCambioRol.setPropuestaCREnviada(miPropuesta);
                   this.generarInformeTemporizadoFromConfigProperty(VocabularioRosace.IdentTareaTimeOutRecibirRespuestasEquipo, null, nombreAgenteEmisor, null);
                   this.getEnvioHechos().insertarHecho(infoCambioRol);
		   } catch (Exception e) {
			     e.printStackTrace();
           }            
    }

}
