/*
 * Se notifican los eventos al Agte Distribuidor de Tareas
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

import icaro.aplicaciones.Rosace.informacion.*;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.ItfUsoRecursoVisualizadorEntornosSimulacion;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Informe;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author FGarijo
 */
public class NotificadorInfoUsuarioSimulador extends ComunicacionAgentes{

	protected ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
	protected ClaseGeneradoraRecursoVisualizadorEntornosSimulacion generadoraVisualizador;
	//  protected InterfazUsoAgente itfUsoAgenteaReportar;
	protected String identificadorAgenteaReportar;
	protected String identRecurso;
	//   protected ComunicacionAgentes comunicacion;
	protected String identificadorEquipo ;
	protected InfoEquipo equipo;
	protected ArrayList identsAgtesEquipo;
	protected Integer victimOrder = 0;
	protected boolean stop = false;
	private InfoContEvtMsgAgteReactivo peticionEnvioVictimaSimulada,peticionEnvioSecuenciaVictimas,peticionIniciarSimulacion,
	peticionParar,peticionMostrarEscenario, peticionPararAgente, peticionActualizarEscenario ;


	private InterfazUsoAgente itfUsoAgenteControlador;

	//    private ItfUsoRecursoEstadistica itfUsoRecursoEstadistica;    //Para recoger estadisticas del instante de envio de victimas desde el centro de control


	public NotificadorInfoUsuarioSimulador(String  idRecurso, String identAgteControlador) {
		// la clase generadora pasa la informacion sobre el agente controlador a quien se le pasan los eventos
		// Obtenemos las informaciones que necesitamos de la clase generadora del recurso
		super (idRecurso)  ;      
		identRecurso = idRecurso;
		identificadorAgenteaReportar = identAgteControlador;
		// inicializo tipos de peticiones que se pueden enviar al aagente controlador
		peticionEnvioVictimaSimulada = new InfoContEvtMsgAgteReactivo (VocabularioRosace.peticionSimulacionVictima);
		peticionEnvioSecuenciaVictimas = new InfoContEvtMsgAgteReactivo (VocabularioRosace.peticionSimulacionSecuenciaVictimas);
		peticionIniciarSimulacion =  new InfoContEvtMsgAgteReactivo(VocabularioRosace.peticionIniciarSimulacion);
		peticionParar = new InfoContEvtMsgAgteReactivo (VocabularioRosace.peticionPararSimulacion);
		peticionMostrarEscenario= new InfoContEvtMsgAgteReactivo (VocabularioRosace.peticionMostrarEscenarioActualSimulado);
		this.itfUsoRepositorioInterfaces=NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;


	}
	public void setIdentAgenteAReportar(String identAgenteAReportar) {
		identificadorAgenteaReportar =identAgenteAReportar ;
	}
	public void sendPeticionSimulacionVictimToRobotTeam (String identVictimaSeleccionada){

		// se manda la peticion al agente para que decida lo que hay que hacer

		this.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo (VocabularioRosace.peticionSimulacionVictima,identVictimaSeleccionada), identificadorAgenteaReportar);
		//      comunicacion.enviarInfoAotroAgente(initialOrder, VocabularioRosace.IdentAgteDistribuidorTareas);
	}
	public void sendPeticionSimulacionSecuenciaVictimasToRobotTeam (int intervaloSecuencia){

		// se manda la peticion al agente para que decida lo que hay que hacer
		Object[] infoDefinidaPorUsuario = new Object[]{intervaloSecuencia};
		peticionEnvioSecuenciaVictimas.setvaloresParametrosAccion(infoDefinidaPorUsuario);
		this.informaraOtroAgenteReactivo(peticionEnvioSecuenciaVictimas, identificadorAgenteaReportar);
		//      comunicacion.enviarInfoAotroAgente(initialOrder, VocabularioRosace.IdentAgteDistribuidorTareas);
	}
	public void sendPeticionPararAgente (String idAgente){

		// se manda la peticion al agente para que decida lo que hay que hacer
		Object[] infoDefinidaPorUsuario = new Object[]{idAgente};
		peticionPararAgente = new InfoContEvtMsgAgteReactivo (VocabularioRosace.PeticionAgteControlSimul.pararRobot.name());
		peticionPararAgente.setvaloresParametrosAccion(infoDefinidaPorUsuario);

		this.informaraOtroAgenteReactivo(peticionPararAgente, identificadorAgenteaReportar);
	}
	public void sendPeticionPararSimulacion (){

		// se manda la peticion al agente para que decida lo que hay que hacer

		this.informaraOtroAgenteReactivo(peticionParar, identificadorAgenteaReportar);
		//      comunicacion.enviarInfoAotroAgente(initialOrder, VocabularioRosace.IdentAgteDistribuidorTareas);
	}
	public void sendPeticionMostrarEscenarioSimulacion() {
		try {
			this.informaraOtroAgenteReactivo(peticionMostrarEscenario, identificadorAgenteaReportar);
		} catch (Exception ex) {
			Logger.getLogger(NotificadorInfoUsuarioSimulador.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	public void sendPeticionAgteControlador(VocabularioRosace.PeticionAgteControlSimul identPeticion) {
		try {  
			this.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo (identPeticion.name()) , identificadorAgenteaReportar);
		} catch (Exception ex) {
			Logger.getLogger(NotificadorInfoUsuarioSimulador.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void sendNotificacionLlegadaDestino (String robotId,String identDestino){
		try {
			Informe informeLlegada = new Informe (robotId,identDestino, VocabularioRosace.MsgeLlegadaDestino);
			this.enviarInfoAotroAgente(informeLlegada, robotId);
		} catch (Exception ex) {
			Logger.getLogger(NotificadorInfoUsuarioSimulador.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	public void sendPeticionCambioEscenario(EscenarioSimulacionRobtsVictms escenarioNuevo) {
		if(generadoraVisualizador==null){
			try {
				generadoraVisualizador = (ClaseGeneradoraRecursoVisualizadorEntornosSimulacion) this.itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + "RecursoVisualizadorEntornosSimulacion1");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
				Object[] infoDefinidaPorUsuario = new Object[]{escenarioNuevo};
				peticionActualizarEscenario = new InfoContEvtMsgAgteReactivo (VocabularioRosace.peticionActualizarEscenario);
				peticionActualizarEscenario.setvaloresParametrosAccion(infoDefinidaPorUsuario);
				this.generadoraVisualizador.updateEscenario(escenarioNuevo);
				this.informaraOtroAgenteReactivo(peticionActualizarEscenario, identificadorAgenteaReportar);
		} 
		catch (Exception ex) {
			Logger.getLogger(NotificadorInfoUsuarioSimulador.class.getName()).log(Level.SEVERE, null, ex);}

	}
	public void sendIniciarSimulacion() {
		Object[] infoDefinidaPorUsuario = new Object[]{};
		peticionIniciarSimulacion.setvaloresParametrosAccion(infoDefinidaPorUsuario);
		this.informaraOtroAgenteReactivo(peticionIniciarSimulacion, identificadorAgenteaReportar);
		
	}
}