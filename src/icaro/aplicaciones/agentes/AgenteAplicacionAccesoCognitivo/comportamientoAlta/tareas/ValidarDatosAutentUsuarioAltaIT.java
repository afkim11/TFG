/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.AgenteAplicacionAccesoCognitivo.comportamientoAlta.tareas;

/**
 *
 * @author FGarijo
 */
import icaro.aplicaciones.agentes.AgenteAplicacionAccesoCognitivo.tareas.*;
import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.InfoAccesoSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.VocabularioSistemaAcceso;
import icaro.aplicaciones.recursos.persistenciaAccesoSimple.ItfUsoPersistenciaAccesoSimple;
import icaro.infraestructura.entidadesBasicas.PerformativaUsuario;
import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.aplicaciones.recursos.visualizacionAccesoAlta.ItfUsoVisualizadorAccesoAlta;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Tarea;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.AdaptadorRegRMI;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Creencia;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;
/**
 *
 * @author Francisco J Garijo
 */
public class ValidarDatosAutentUsuarioAltaIT extends Tarea {
//    private String identDeEstaTarea= "ValidarDatosAutenticacionUsuario";
    private String identAgenteOrdenante;
    private Objetivo contextoEjecucionTarea = null;
//    private boolean resultadoValidacion;
/** Crea una nueva instancia de ValidarDatos */
	
@Override
public void ejecutar(Object... params) {
//         String   IdentRecursoVisualizacionAcceso = "VisualizacionAcceso1";
//         String   IdentRecursoPersistencia = "Persistencia1";
//         String identTareaLong = getClass().getName();
         String identTarea = getClass().getSimpleName();
//	Se extraen  los datos de los parametros
	PerformativaUsuario infoUsuario = (PerformativaUsuario) params[0];
        Object [] parametrosPerfortiva = (Object [])infoUsuario.getParametros ();
        InfoAccesoSinValidar infoAcceso = (InfoAccesoSinValidar) parametrosPerfortiva[0] ;
        try {
// Se obtienen las interfaces de los recursos. Si no se pueden obtener las interfaces se debe genera un informe de tarea
// para que el motor se entere de que la tarea ha fallado
                identAgenteOrdenante = this.getAgente().getIdentAgente();
		ItfUsoVisualizadorAccesoAlta visualizadorAcceso = (ItfUsoVisualizadorAccesoAlta) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ
					.obtenerInterfaz(NombresPredefinidos.ITF_USO + VocabularioSistemaAcceso.IdentRecursoVisualizacionAccesoAlta);
                if (visualizadorAcceso==null)
                    visualizadorAcceso = (ItfUsoVisualizadorAccesoAlta)AdaptadorRegRMI.getItfRecursoRemoto(VocabularioSistemaAcceso.IdentRecursoVisualizacionAccesoAlta, NombresPredefinidos.ITF_USO);
		if (visualizadorAcceso == null)
                   this.generarInformeConCausaTerminacion(identTarea, contextoEjecucionTarea, identAgenteOrdenante, "Error-AlObtener:"+VocabularioSistemaAcceso.IdentRecursoVisualizacionAccesoAlta, CausaTerminacionTarea.ERROR);
                else
                     visualizadorAcceso.mostrarVisualizadorAcceso(this.getAgente().getIdentAgente(), NombresPredefinidos.TIPO_COGNITIVO);                    
                ItfUsoPersistenciaAccesoSimple itfUsoPersistencia = (ItfUsoPersistenciaAccesoSimple) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(
							NombresPredefinidos.ITF_USO + VocabularioSistemaAcceso.IdentRecursoPersistenciaAcceso);
                if (itfUsoPersistencia == null)
                    itfUsoPersistencia = (ItfUsoPersistenciaAccesoSimple)AdaptadorRegRMI.getItfRecursoRemoto(VocabularioSistemaAcceso.IdentRecursoPersistenciaAcceso, NombresPredefinidos.ITF_USO);
		if (itfUsoPersistencia == null)
                     this.generarInformeConCausaTerminacion(identTarea, contextoEjecucionTarea, identAgenteOrdenante, "Error-AlObtener:Interfaz_"+VocabularioSistemaAcceso.IdentRecursoPersistenciaAcceso, CausaTerminacionTarea.ERROR);
                else {
                boolean resultadoValidacion = itfUsoPersistencia.compruebaUsuario(infoAcceso.tomaUsuario()
					, infoAcceso.tomaPassword());               
                String contenidoInformeTarea;
                if (resultadoValidacion){
                    contenidoInformeTarea = VocabularioSistemaAcceso.ResultadoAutenticacion_DatosValidos;
                 }else{
 //                       visualizadorAcceso.mostrarMensajeAviso("Acceso incorrecto", "Procedemos a darle de alta");
                        contenidoInformeTarea = VocabularioSistemaAcceso.ResultadoAutenticacion_DatosNoValidos;
                      }
                this.generarInformeOK(identTarea, contextoEjecucionTarea, identAgenteOrdenante, contenidoInformeTarea);
//                identAgenteOrdenante = this.getAgente().getIdentAgente();
// Se recupera el resultado del recurso de persistencia y se transforma en creencia para enviarlo al Procesador De Objetivos
//                Creencia nuevaCreencia  = new Creencia();
//                nuevaCreencia.setEmisor(identDeEstaTarea);
//                if (resultadoValidacion){
//                    nuevaCreencia.setContenido("usuarioValido");
//                   }else{
//                         nuevaCreencia.setContenido("usuarioNoValido");
//                        }
//                ItfProcesadorObjetivos itfenvioH = this.getEnvioHechos();
//                itfenvioH.procesarCreencia(nuevaCreencia);
            }
//              this.getEnvioHechos().procesarCreencia(nuevaCreencia);
//                itfenvioH.assertFact(nuevaCreencia);
		} catch (Exception e) {
                    e.printStackTrace();
                    this.generarInformeConCausaTerminacion(identTarea, contextoEjecucionTarea, identAgenteOrdenante, "Error-AlObtener:Interfaz_Recurso_Persistencia", CausaTerminacionTarea.ERROR);
		}
    }
}
