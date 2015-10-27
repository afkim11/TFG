/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;

import icaro.aplicaciones.agentes.agenteAplicacionSubordinadoCognitivo.tareas.*;
import icaro.aplicaciones.Rosace.informacion.*;
import icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.informacion.ExptComunicacionConJefe;
import icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.informacion.InfoCambioRolAgente;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author Francisco J Garijo
 */


//NOTA JM: esta tarea se llama en la regla "Procesar propuestas de otro agente para ir yo" 

public class ProcesarInformesInfoRolAgente extends TareaSincrona {

    private String nombreAgenteEmisor;
    private String identDeEstaTarea ;

	@Override
	public void ejecutar(Object... params) {
            try {
                  InfoCambioRolAgente mInfocambioRol = (InfoCambioRolAgente)params[0];
                  InfoRolAgente estusRolrecibido = (InfoRolAgente)params[1];
                  nombreAgenteEmisor = this.getAgente().getIdentAgente();
                  identDeEstaTarea = this.getIdentTarea();
                  mInfocambioRol.procesarInfoCambioRolAgte(estusRolrecibido);
                  mInfocambioRol.verificarCondicionesCambioRolEquipo();
                  this.getEnvioHechos().eliminarHechoWithoutFireRules(estusRolrecibido);
                  this.getEnvioHechos().actualizarHecho(mInfocambioRol);
                  trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se procesa el informe de Rol:  "+ estusRolrecibido, InfoTraza.NivelTraza.debug));
               }         
            catch(Exception e) {
                  e.printStackTrace();
            }
    }
	
}
