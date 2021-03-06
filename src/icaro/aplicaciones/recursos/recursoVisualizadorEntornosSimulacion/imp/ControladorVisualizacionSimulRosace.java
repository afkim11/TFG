/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.RobotCapability;
import icaro.aplicaciones.Rosace.informacion.RobotStatus;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.ItfUsoRecursoPersistenciaEntornosSimulacion;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.animator.SceneAnimator;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.widget.LayerWidget;
import org.openide.util.Exceptions;

/**
 *
 * @author FGarijo
 */
public class ControladorVisualizacionSimulRosace {
	public static final int PRIORIZACIONPORORDENDEIDENTIFICACION= 1;
	public static final int PRIORIZADOTIEMPODEVIDA = 2;
	public static boolean asignadorSeMueve = false;
	public static boolean exploracionPrevia = true;
	private NotificadorInfoUsuarioSimulador notifEvts;
	private int intervaloSecuencia = 10000; // valor por defecto. Eso deberia ponerse en otro sitio
	private int numMensajesEnviar = 3;
	private boolean primeraVictima = true;
	private ControlCenterGUI4 visorControlSim;
	private ArrayList identsRobotsEquipo ;
	private javax.swing.JLabel jLabelAux;
	private String directorioTrabajo;
	private String tituloVentanaVisor = "ROSACE Scenario Visor";
	private String rutassrc = "src/";   //poner "src/main/java" si el proyecto de icaro se monta en un proyecto maven
	private String rutapaqueteConstructorEscenariosROSACE = "utilsDiseniaEscenariosRosace/";
	private static  Image IMAGErobot,IMAGEmujer,IMAGEmujerRes ;
	public static int modoEnvioVictimas = 1;
	private String rutaIconos = "\\src\\utilsDiseniaEscenariosRosace\\";
	//    private String rutaPersistenciaEscenario = "\\src\\persistenciaEscenarios\\";
	private String directorioPersistencia = VocabularioRosace.IdentDirectorioPersistenciaEscenarios+File.separator;
	private String imageniconoHombre = "Hombre.png";
	private String imageniconoMujer = "Mujer.png";
	private String imageniconoMujerRescatada = "MujerRescatada.png";
	private String imageniconoHombreRescatado = "HombreRescatado.png";
	private String imageniconoRobot = "Robot.png";
	private String modeloOrganizativoInicial = "Igualitario";
	private String tituloAvisoEscenarioNoDefinido= "Escenario indefinido";
	private String mensajeEscenarioNoDefinido= "El esceneraio de simulacion no esta definido ";
	private String recomendacionDefinirEscenario= " Abrir un escenario con el menu de edicion o crear un escenario nuevo";
	private String mensajeEscenarioNoSeleccionado= "No se ha seleccionado el esceneraio de simulacion ";
	private Map<String, JLabel> tablaEntidadesEnEscenario;
	private ArrayList <JLabel> listaEntidadesEnEscenario;
	private ItfUsoRecursoPersistenciaEntornosSimulacion itfPersistenciaSimul;
	private JPanel panelVisor;
	JLabel entidadSeleccionada=null;
	private WidgetAction moveAction = ActionFactory.createMoveAction ();
	private Point ultimoPuntoClic ;
	private boolean intencionUsuarioCrearRobot;
	private boolean intencionUsuarioCrearVictima;
	private boolean entidadSeleccionadaParaMover;
	private int numeroRobots, mumeroVictimas;
	private volatile GestionEscenariosSimulacion gestionEscComp;
	private volatile EscenarioSimulacionRobtsVictms escenarioEdicionComp;
	private volatile PersistenciaVisualizadorEscenarios persistencia;
	private volatile VisorEscenariosRosace visorEscenarioRosace;
	private VisorCreacionEscenarios visorEditorEscen;
	private String modeloOrganizativo;
	private String identEquipoActual;

	public  ControladorVisualizacionSimulRosace (NotificadorInfoUsuarioSimulador NotificadorInfoUsuarioSimulador,VisorEscenariosRosace visorEscenariosRosace){
		notifEvts=NotificadorInfoUsuarioSimulador;
		this.visorEscenarioRosace=visorEscenariosRosace;
		initModelosYvistas();
	}


	public void setIftRecPersistencia(ItfUsoRecursoPersistenciaEntornosSimulacion itfPersisSimul){
		itfPersistenciaSimul = itfPersisSimul;
	}

	public void initModelosYvistas(){
		String  directorioPersistencia = VocabularioRosace.IdentDirectorioPersistenciaEscenarios+File.separator;
		//            VisorControlSimuladorRosace visorSc;

		persistencia= new PersistenciaVisualizadorEscenarios();
		gestionEscComp= new GestionEscenariosSimulacion();
		gestionEscComp.setIdentsEscenariosSimulacion(persistencia.obtenerIdentsEscenarioSimulacion(directorioPersistencia));
		try {
			gestionEscComp = new GestionEscenariosSimulacion();
			gestionEscComp.setIdentsEscenariosSimulacion(persistencia.obtenerIdentsEscenarioSimulacion(directorioPersistencia));
			visorEditorEscen= new VisorCreacionEscenarios(this);
			visorControlSim = new ControlCenterGUI4(this);


			//                    persistencia= new PersistenciaVisualizadorEscenarios();
			//                    visor.setPersistencia(persistencia);
			//                    visor.setGestorEscenarionComp(gestionEscComp);
			//                    visor.setEscenarioActualComp(gestionEscComp.crearEscenarioSimulacion());
			//                    visor.setIdentEquipoActual()
			//                    visor.actualizarInfoEquipoEnEscenario();
			// visorControlSim.setVisible(true);
		} catch (Exception ex) {
			Exceptions.printStackTrace(ex);
		}

	}

	public  void peticionComenzarSimulacion(String identEquipoActual, int intervaloSecuencia) {
		//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		// if ( escenarioActualComp ==null)visorControlSim.visualizarConsejo(tituloAvisoEscenarioNoDefinido,mensajeEscenarioNoDefinido,recomendacionDefinirEscenario);
		// else if (identEquipoActual== null)visorControlSim.setIdentEquipo(escenarioActualComp.getIdentEscenario());
		// else if (intervaloSecuencia <=0)visorControlSim.solicitarDefinicionItervaloSecuencia();
		//else 
		if(exploracionPrevia)
			notifEvts.sendIniciarSimulacion();
		else 
			notifEvts.sendPeticionSimulacionSecuenciaVictimasToRobotTeam(intervaloSecuencia);
	}

	public  void peticionCrearEscenario() {
		// throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		escenarioEdicionComp = gestionEscComp.crearEscenarioSimulacion();
		escenarioEdicionComp.initEscenario(); 

		visorEditorEscen.visualizarEscenario(escenarioEdicionComp);
		visorEditorEscen.setVisible(true);
	}

	public  void peticionAbrirEscenario() {
		File ficheroSeleccionado=   visorControlSim.solicitarSeleccionFichero(directorioPersistencia);
		if (ficheroSeleccionado==null)visorControlSim.visualizarConsejo(tituloAvisoEscenarioNoDefinido, mensajeEscenarioNoSeleccionado,recomendacionDefinirEscenario);
		else{
			EscenarioSimulacionRobtsVictms escenario = persistencia.obtenerInfoEscenarioSimulacion(ficheroSeleccionado.getAbsolutePath());
			if(escenario.getListIdentsRobots().size() == this.visorEscenarioRosace.getEscenario().getNumRobots()){
				this.escenarioEdicionComp = escenario;
				this.escenarioEdicionComp.setGestorEscenarios(gestionEscComp);
				identEquipoActual= this.escenarioEdicionComp.getIdentEscenario();
				visorControlSim.setIdentEquipo(identEquipoActual);
				VocabularioRosace.rutaEscenario = ficheroSeleccionado.getName();
				identsRobotsEquipo= this.escenarioEdicionComp.getListIdentsRobots();
				if( identsRobotsEquipo!=null) visorControlSim.visualizarIdentsEquipoRobot(identsRobotsEquipo);
				if(this.escenarioEdicionComp.getListIdentsVictims()!=null)visorControlSim.visualizarIdentsEquipoVictims(this.escenarioEdicionComp.getListIdentsVictims());
				this.notifEvts.sendPeticionCambioEscenario(this.escenarioEdicionComp);
			}
			else{
				JOptionPane.showMessageDialog(null, "El numero de robots del escenario no coincide con el numero de robots registrados", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public  void peticionEliminarEscenario() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public   void peticionPararRobot(String identRobotSeleccionado) {
		this.notifEvts.sendPeticionPararAgente(identRobotSeleccionado);
	}

	void peticionPararSimulacion() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	public void peticionMostrarEscenario() {
		notifEvts.sendPeticionMostrarEscenarioSimulacion();

	}
	public void setVisibleControlGUI(boolean b) {
		this.visorControlSim.setVisible(b);

	}
	public void visualizarIdentsEquipoRobot(ArrayList<String> identList) {
		this.visorControlSim.visualizarIdentsEquipoRobot(identList);

	}
	public void visualizarIdentsVictims(ArrayList<String> identList) {
		this.visorControlSim.visualizarIdentsEquipoVictims(identList);

	}
	public void victimaSeleccionadaParaSimulacion(String identVictimaSeleccionada) {
		if(identVictimaSeleccionada==null)JOptionPane.showMessageDialog(null, "No hay una victima seleccionada\nPara seleccionar una victima debes hacer doble click en el panel de victimas del CC");
		else {
			notifEvts.sendPeticionSimulacionVictimToRobotTeam(identVictimaSeleccionada);

		}

	}
	public void peticionGuardarEscenario(EscenarioSimulacionRobtsVictms escenarioComp) {
		escenarioEdicionComp = escenarioComp;
		if (escenarioEdicionComp.getNumRobots()<=0)visorEditorEscen.visualizarConsejo("Escenarios sin Robots definidos", "No hay robots en el escenario", " Definir los robots y victimas con el boton derecho para poder guardar");
		else{
			int respuesta =visorEditorEscen.confirmarPeticionGuardarEscenario("Se va a guardar el escenario : ");
			if (respuesta==JOptionPane.OK_OPTION){

				ArrayList<String> robotNombres = escenarioComp.getListIdentsRobots();
				for (String ideRobot:robotNombres){
					//                 String ideRobot = (String)robtIter.next();
					RobotStatus infoRobot = (RobotStatus) escenarioComp.getRobotInfo(ideRobot);
					List<RobotCapability> capacidades=infoRobot.getRobotCapabilities();
					System.out.println("Desde peticion Guardar Lista de capacidades a guardar del robot  : " + ideRobot+"Capacidades : "+ capacidades.toString() );
				}
				System.out.println("Desde peticion Guardar Numero de Robots  : " + escenarioEdicionComp.getNumRobots()+" Numero de victimas : "+ escenarioEdicionComp.getNumVictimas());
				if (itfPersistenciaSimul==null)   
					persistencia.guardarInfoEscenarioSimulacion(directorioPersistencia, escenarioEdicionComp);
				else 
					try {
						itfPersistenciaSimul.guardarInfoEscenarioSimulacion(escenarioEdicionComp);
					} catch (Exception ex) {
						Exceptions.printStackTrace(ex);
					}
				/* if ( peticionObtenerEscenarioValido&&!escenarioValidoObtenido){
                    // se envia el escenario al agente controlador que puede estar esperandolo
                    notifEvts.sendInfoEscenarioSeleccionado(escenarioEdicionComp);
                }*/
			}
			//            else if (respuesta==JOptionPane.)
			visorControlSim.visualizarIdentsEquipoRobot(escenarioComp.getListIdentsRobots());
			visorControlSim.setIdentEscenarioActual(escenarioComp.getIdentEscenario());
		} 

	}
	public Map<String,Victim> getVictimasEscenario(){
		return this.visorEscenarioRosace.getEscenario().getVictims();
	}

	public EscenarioSimulacionRobtsVictms getEscenario(){
		return this.escenarioEdicionComp;
	}
	public void setNuevoVisorEscenario(VisorEscenariosRosace visorEscenarios) {
		this.visorEscenarioRosace = visorEscenarios;	
	}
	public void addRastroExploracion(Coordinate coor){
		this.visorEscenarioRosace.actualizarRastro(coor);
	}


	public void configurarParametrosSimulacion() {
		// TODO Auto-generated method stub
		if(VocabularioRosace.funcionEvaluacionSeleccionada.equals("FuncionEvaluacion1")){
			modoEnvioVictimas = PRIORIZACIONPORORDENDEIDENTIFICACION;
			exploracionPrevia = true;
			VocabularioRosace.numeroReconocedores = 1;
		}
		else if(VocabularioRosace.funcionEvaluacionSeleccionada.equals("FuncionEvaluacion2")){
			modoEnvioVictimas = PRIORIZACIONPORORDENDEIDENTIFICACION;
			VocabularioRosace.numeroReconocedores = 2;
			exploracionPrevia = true;
		}
		else if(VocabularioRosace.funcionEvaluacionSeleccionada.equals("FuncionEvaluacion3")){
			modoEnvioVictimas = PRIORIZADOTIEMPODEVIDA;
			exploracionPrevia = false;
		} 
	}
}
