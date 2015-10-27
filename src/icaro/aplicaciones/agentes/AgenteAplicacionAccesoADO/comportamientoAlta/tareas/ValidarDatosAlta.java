/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.AgenteAplicacionAccesoADO.comportamientoAlta.tareas;
import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.InfoAccesoSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.VocabularioSistemaAcceso;
import icaro.aplicaciones.recursos.persistenciaAccesoSimple.ItfUsoPersistenciaAccesoSimple;
import icaro.aplicaciones.recursos.visualizacionAccesoAlta.ItfUsoVisualizadorAccesoAlta;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.PerformativaUsuario;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Tarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
/**
 *
 * @author Francisco J Garijo
 */
public class ValidarDatosAlta extends Tarea {
	private MensajeSimple msgResultado;
    //    private boolean resultadoValidacion;
	/** Crea una nueva instancia de ValidarDatos */
public ValidarDatosAlta() {
	}
@Override
public void ejecutar(Object... params) {
	try {
		ItfUsoVisualizadorAccesoAlta va = (ItfUsoVisualizadorAccesoAlta) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ
				.obtenerInterfaz(NombresPredefinidos.ITF_USO + "VisualizacionAccesoAlta1");
                ItfUsoPersistenciaAccesoSimple itfUsoPersistencia = (ItfUsoPersistenciaAccesoSimple) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(
		 	 				NombresPredefinidos.ITF_USO + "Persistencia1");
                ItfUsoRecursoTrazas  itfUsoTrazas =  (ItfUsoRecursoTrazas) NombresPredefinidos.RECURSO_TRAZAS_OBJ;
			// va.mostrarVentana(false);
//			va.cerrarVisualizadorAcceso();
                        String identDeEstaTarea=getClass().getSimpleName();
                        String identAgente = this.getAgente().getIdentAgente();
			InfoContEvtMsgAgteReactivo infoUsuario = (InfoContEvtMsgAgteReactivo) params[0];
                        Object [] parametrosInfoCont = (Object [])infoUsuario.getvaloresParametrosAccion();
                        InfoAccesoSinValidar infoAcceso = (InfoAccesoSinValidar)parametrosInfoCont[0];
                        String userName = infoAcceso.tomaUsuario();
                        String passw = infoAcceso.tomaPassword();
                //        InfoAccesoSinValidar infoAcceso = new InfoAccesoSinValidar ( ,parametrosPerfortiva[1])  ;
		//	MensajeSimple resultado = new MensajeSimple();
		//	resultado.setEmisor("Task:ValidarDatos");
		//	resultado.setReceptor( "AgenteAplicacionAccesoAlta1");

			boolean resultadoValidacion = itfUsoPersistencia.compruebaUsuario(userName, passw);
		//	resultado.setContenido(new Boolean(usuarioValido));
			if (resultadoValidacion){
                              va.mostrarMensajeError("Error", "El nombre de usuario y la clave elegida ya esta ocupado.Introduzca otro nombre y otra clave");
//                              msgResultado = new MensajeSimple("infoUsuarioYaExistente","Task:GuardarInfoAcceso","AgenteAplicacionAcceso1" );
                              this.generarInformeOK(identDeEstaTarea, null, identAgente, "infoUsuarioYaExistente");
                        }
                        else{
                           // Guardamos la inforamcion del usuario
                            try{
                                    itfUsoPersistencia.insertaUsuario(userName, passw);
              //                      Object[] datosEnvio = new Object[]{datos.tomaUsuario(), datos.tomaPassword()};
              //                      informaraMiAutomata("correcto", datosEnvio);
                                }
				catch(Exception exc){
            //			informaraMiAutomata("error", null);
            //                            logger.error("Fallo al insertar un nuevo valor.");

					itfUsoTrazas. aceptaNuevaTraza(new InfoTraza("Persistencia1","Fallo al insertar un nuevo valor.", InfoTraza.NivelTraza.error));
/*
                    try{
						itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("error",nombreAgente,nombreAgente));
					}
					catch(Exception exce){
						logger.error("Fallo al enviar un evento error.");
						exce.printStackTrace();
					}
   */

                                }
                            va.mostrarMensajeInformacion("Datos de Acceso correctos", "Sus datos han sido almacenados correctamente") ;
                            va.cerrarVisualizadorAccesoAlta();
                            va.mostrarVisualizadorAcceso(identAgente, NombresPredefinidos.TIPO_COGNITIVO);
//                            msgResultado = new MensajeSimple("InfoAltaUsuarioGuardada","Task:GuardarInfoAcceso","AgenteAplicacionAcceso1" );
                            this.generarInformeOK(identDeEstaTarea, null, identAgente, "InfoAltaUsuarioGuardada");
                          }
//                        this.getAgente().aceptaMensaje(msgResultado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
