/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.AgenteAplicacionAccesoADO.comportamientoAlta.tareas;
import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.VocabularioSistemaAcceso;
import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.aplicaciones.recursos.visualizacionAccesoAlta.ItfUsoVisualizadorAccesoAlta;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Tarea;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author Francisco J Garijo
 */
public class SolicitarDatosParaDardeAlta  extends Tarea {

	/** Crea una nueva instancia de SolicitarDatos */
	private String identAgenteOrdenante;
        private Objetivo contextoEjecucionTarea = null;

	@Override
	public void ejecutar(Object... params) {
		try {
                String identDeEstaTarea=this.getIdentTarea();
                String identRecursoVisualizacionAccesoAlta = (String)params[0];
		ItfUsoVisualizadorAccesoAlta visualizadorAlta = (ItfUsoVisualizadorAccesoAlta) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(
						NombresPredefinidos.ITF_USO + identRecursoVisualizacionAccesoAlta);
		if (visualizadorAlta!= null){
                visualizadorAlta.cerrarVisualizadorAcceso();
                visualizadorAlta.mostrarVisualizadorAccesoAlta("") ;
                visualizadorAlta.mostrarMensajeAviso("Acceso Incorrecto", "Procedemos a darle de Alta ");	
		visualizadorAlta.mostrarVisualizadorAccesoAlta("");
                } else {
                    identAgenteOrdenante = this.getAgente().getIdentAgente();
                     this.generarInformeConCausaTerminacion(this.getIdentTarea(), contextoEjecucionTarea, identAgenteOrdenante, "Error-AlObtener:Interfaz:"+VocabularioSistemaAcceso.IdentRecursoVisualizacionAccesoAlta, CausaTerminacionTarea.ERROR);
//            trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, "mostrar visualizador de alta", InfoTraza.NivelTraza.info));
                }
                } catch(Exception e) {
			e.printStackTrace();
		}
      }
    }

