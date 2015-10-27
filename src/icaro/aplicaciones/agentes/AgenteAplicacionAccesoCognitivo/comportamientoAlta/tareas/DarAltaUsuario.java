/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.AgenteAplicacionAccesoCognitivo.comportamientoAlta.tareas;
import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.VocabularioSistemaAcceso;
import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.aplicaciones.recursos.visualizacionAccesoAlta.ItfUsoVisualizadorAccesoAlta;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Tarea;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author Francisco J Garijo
 */
public class DarAltaUsuario  extends Tarea {

	/** Crea una nueva instancia de SolicitarDatos */
	public DarAltaUsuario() {
	}

	@Override
	public void ejecutar(Object... params) {
		try {
		ItfUsoVisualizadorAccesoAlta visualizadorAlta = (ItfUsoVisualizadorAccesoAlta) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(
						NombresPredefinidos.ITF_USO + VocabularioSistemaAcceso.IdentRecursoVisualizacionAccesoAlta);
		
                visualizadorAlta.cerrarVisualizadorAcceso();
                visualizadorAlta.mostrarVisualizadorAccesoAlta("") ;
                visualizadorAlta.mostrarMensajeAviso("Acceso Incorrecto", "Procedemos a darle de Alta ");
		
		visualizadorAlta.mostrarVisualizadorAccesoAlta("");
//            trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, "mostrar visualizador de alta", InfoTraza.NivelTraza.info));
		
                } catch(Exception e) {
			e.printStackTrace();
		}
      }
    }

