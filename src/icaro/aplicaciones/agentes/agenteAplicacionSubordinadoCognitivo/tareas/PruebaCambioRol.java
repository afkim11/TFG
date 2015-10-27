/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoCognitivo.tareas;

import icaro.aplicaciones.agentes.agenteAplicacionSubordinadoCognitivo.tareas.*;
import icaro.aplicaciones.Rosace.informacion.*;
import icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.informacion.ExptComunicacionConJefe;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.*;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.io.InputStream;

/**
 *
 * @author Francisco J Garijo
 */

public class PruebaCambioRol extends Tarea {

    private String nombreAgenteEmisor;
    private ItfUsoRecursoTrazas trazas;
    private InfoEstatusComunicacionConOtroAgente comunicStatusRecibido;
    private ExptComunicacionConJefe expectativaParaValidar;
    private String identDeEstaTarea ;

	@Override
	public void ejecutar(Object... params) {
            try {
                  trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
             
                  nombreAgenteEmisor = this.getAgente().getIdentAgente();
                  identDeEstaTarea = this.getIdentTarea();
                  String rutaNuevasReglas = "/icaro/aplicaciones/agentes/agenteAplicacionrobotMasterIACognitivo/procesoResolucionObjetivos/reglas.drl";
            /*
            InputStream reglas = this.getClass().getResourceAsStream(rutaNuevasReglas);
            this.getItfMotorDeReglas().crearKbSesionConNuevasReglas(reglas, rutaNuevasReglas);
            this.getItfMotorDeReglas().addGlobalVariable(NombresPredefinidos.TASK_MANAGER_GLOBAL, this.getEnvioHechos().GetItfGestorTareas());
            this.getItfMotorDeReglas().addGlobalVariable(NombresPredefinidos.ITFUSO_RECURSOTRAZAS_GLOBAL, trazas);
            this.getItfMotorDeReglas().addGlobalVariable(NombresPredefinidos.AGENT_ID_GLOBAL, nombreAgenteEmisor);
             */
            if ( this.getEnvioHechos().cambiarComportamiento(rutaNuevasReglas) )
                  trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se cambia el rol del agente jerarquico  por gualitario :  "+ rutaNuevasReglas , InfoTraza.NivelTraza.debug));
               }         
            catch(Exception e) {
                  e.printStackTrace();
            }
    }
	
}
