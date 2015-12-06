package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.Informacion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.utils.AccesoPropiedadesGlobalesRosace;
import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.imp.ReadXMLTestObstacles;
import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.imp.ReadXMLTestRobots;
import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.imp.ReadXMLTestSequence;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class VisorEscenariosRosace extends JFrame {

	private String tituloVentanaVisor = "ROSACE Scenario Visor";
	private String rutassrc = "src/";   //poner "src/main/java" si el proyecto de icaro se monta en un proyecto maven
	private String rutapaqueteConstructorEscenariosROSACE = "utilsDiseniaEscenariosRosace/";
	private String directorioTrabajo;
	private int excesoY = 20;
	private int excesoX = 10;
	private int dimensionVerticalTextArea = 20;
	public static int alto= 700;
	public static int ancho= 1100;
	private int dimensionHorizontalJFrame = excesoX + ancho;                                //width  -- ancho  1110 es el valor del ancho del JPanel del editor de escenarios
	private int dimensionVerticalJFrame = excesoY + dimensionVerticalTextArea + alto;     //height -- alto   700  es el valor del alto  del JPanel del editor de escenarios
	private int posicionXInicialJFrame = 0;
	private int posicionYInicialJFrame = 0;
	private JPanel contentPaneRoot;
	private Map<String, JLabel> robotslabel;      //La clave es el indice del robot, es decir, 1, 2, 3, .... El contenido es el JLabel
	private Map<String, JLabel> victimaslabel;    //La clave es el indice de la victima, es decir, 1, 2, 3, .... El contenido es el JLabel 
	private static ArrayList <LineaObstaculo> obstaculos;
	private String imageniconoHombre = "Hombre.png";
	private String imageniconoMujer = "Mujer.png";
	private String imageniconoMujerRescatada = "MujerRescatada.png";
	private String imageniconoHombreRescatado = "HombreRescatado.png";
	private String imageniconoRobot = "Robot.png";
	private JTextArea textAreaMensaje;
	private JPanelObstaculo panelVisor;
	private Graphics graficosVisor;

	private NotificadorInfoUsuarioSimulador notifEvts;



	/**
	 * Create the frame.
	 */
	public VisorEscenariosRosace(String rutaFicheroRobotsTest) throws Exception {

	}
	public VisorEscenariosRosace( NotificadorInfoUsuarioSimulador notifEvt) throws Exception {
		this.notifEvts=notifEvt;
		robotslabel = new HashMap<String, JLabel>();
		victimaslabel = new HashMap<String, JLabel>();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 649, 409);
		contentPaneRoot = new JPanel();
		contentPaneRoot.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPaneRoot);

		//CONFIGURANDO EL VISOR
		//---------------------------------------------------------------------------
		this.setTitle(tituloVentanaVisor);

		//Desactivar el boton de maximizar
		this.setResizable(false);

		//Fijar las dimensiones del JFrame
		Dimension dimension = new Dimension();
		dimension.setSize(dimensionHorizontalJFrame, dimensionVerticalJFrame);
		this.setSize(dimension);

		//Posicionar el JFrame en la esquina superior izquierda de la pantalla
		this.setLocation(posicionXInicialJFrame, posicionYInicialJFrame);

		//this.setVisible(true);


		//this.setExtendedState(JFrame.ICONIFIED);  //Mostrar el JFrame minimizado


		//contentPaneRoot.setBackground(Color.WHITE);   //Poner en blanco el color del fondo del JPanel
		contentPaneRoot.setLayout(null);


		//Colocar un JSplitPane en el componente raiz
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);  //Los componentes se colocan verticalmente en el JSplitPane
		splitPane.setSize(dimensionHorizontalJFrame, dimensionVerticalJFrame - excesoY);
		contentPaneRoot.add(splitPane);

		//Poner en la parte superior del JSplitPane un JPanel para mostrar los elementos del escenarios
		//---------------------------------------------------------------------------------------------
		//JPanel panelVisor = new JPanel();




		//Poner en la parte inferior del JSplitPane un JTextArea para mostrar mensajes
		//---------------------------------------------------------------------------------------------
		//JTextArea textAreaMensaje = new JTextArea();
		//textAreaMensaje lo he declarado como variable global para que sea muy sencillo ofrecer un metodo que nos permita escribir texto en el JTextArea
		/*textAreaMensaje = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textAreaMensaje,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setSize(dimensionHorizontalJFrame, dimensionVerticalTextArea);
        scrollPane.setAutoscrolls(true);
        splitPane.setRightComponent(scrollPane);*/
		ItfUsoConfiguracion itfconfig = (ItfUsoConfiguracion) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
		String rutaFicheroRobotsTest = itfconfig.getValorPropiedadGlobal(VocabularioRosace.rutaFicheroRobotsTest);
		ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots(rutaFicheroRobotsTest);
		Document docRobots = rXMLTRobots.getDocument(rXMLTRobots.gettestFilePath());
		NodeList nodeLstRobots = rXMLTRobots.getRobotsXMLStructure(docRobots, "robot");   //Obtain all the robots		
		int nroRobots = rXMLTRobots.getNumberOfRobots(nodeLstRobots);

		JPanel panelAccionesRobots=new JPanel();
		final String[] nombres= new String [nroRobots];
		
		ArrayList<JButton> listaDeBotones=new ArrayList<JButton>();
		for(int i=1;i<nroRobots;i++){
			

			panelAccionesRobots.add(new Boton("Romper robot " + i,VocabularioRosace.IdentEquipoJerarquico + VocabularioRosace.IdentRolAgtesSubordinados + i
					,VocabularioRosace.MsgRomperRobot,notifEvts));
		}



		splitPane.setRightComponent(panelAccionesRobots);





		//Colocar la posicion del divisor
		splitPane.setDividerLocation(dimensionVerticalJFrame - dimensionVerticalTextArea - 3 * excesoY);

		splitPane.setOneTouchExpandable(false);
		splitPane.setEnabled(false);             
		//Leo la ruta de los ficheros
		directorioTrabajo = System.getProperty("user.dir");  //Obtener directorio de trabajo


		//--------------------------------------------
		// Leer xml simulacion localizacion de robots
		//--------------------------------------------
		//        String rutaFicheroRobotsTest = AccesoPropiedadesGlobalesRosace.getRutaFicheroRobotsTest();
		//        ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots(rutaFicheroRobotsTest);

		//        Document docRobots = rXMLTRobots.getDocument(rXMLTRobots.gettestFilePaht());
		//        NodeList nodeLstRobots = rXMLTRobots.getRobotsXMLStructure(docRobots, "robot");   //Obtain all the robots		
		//        int nroRobots = rXMLTRobots.getNumberOfRobots(nodeLstRobots);

		//----------------------------------------------
		// Leer xml simulacion localizacion de victimas
		//----------------------------------------------

		//Recuperar la ruta del fichero de victimas del escenario
		//        ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();

		String rutaFicheroVictimasTest = itfconfig.getValorPropiedadGlobal(VocabularioRosace.rutaFicheroVictimasTest);
		String rutaFicheroObstaculosTest = itfconfig.getValorPropiedadGlobal(VocabularioRosace.rutaFicheroObstaculosTest);
		ReadXMLTestSequence rXMLTSeq = new ReadXMLTestSequence(rutaFicheroVictimasTest);
		ReadXMLTestObstacles rXMLTObs = new ReadXMLTestObstacles(rutaFicheroObstaculosTest);



		Document docVictimas = rXMLTSeq.getDocument(rXMLTSeq.gettestFilePath());
		NodeList nodeLstVictimas = rXMLTSeq.getVictimsXMLStructure(docVictimas, "victim");   //Obtain all the victims
		int nroVictimas = rXMLTSeq.getNumberOfVictimsInSequence(nodeLstVictimas);
		Document docObstaculos = rXMLTObs.getDocument(rXMLTObs.gettestFilePath());
		NodeList nodeLstObs = rXMLTObs.getObstaculosXMLStructure(docObstaculos, "obstacle");
		int nroObstaculos = rXMLTObs.getNumberObstaculos(nodeLstObs);
		obstaculos = new ArrayList<LineaObstaculo>();

		for (int j = 0; j < nroObstaculos; j++) {
			Element info = rXMLTObs.getObsElement(nodeLstObs, j);
			String valueid = rXMLTObs.getObsIDValue(info, "id");
			Coordinate valueInitialCoordinate = rXMLTObs.getCoordinate(info, "initialcoordinate");
			Coordinate valueFinalCoordinate = rXMLTObs.getCoordinate(info, "finalcoordinate");
			int iniX = (int) valueInitialCoordinate.x;
			int iniY = (int) valueInitialCoordinate.y;
			int finX = (int) valueFinalCoordinate.x;
			int finY = (int) valueFinalCoordinate.y;

			obstaculos.add(new LineaObstaculo(valueInitialCoordinate, valueFinalCoordinate, valueid));

			System.out.println("Localizacion del obstaculo " + valueid + "-> (" + iniX + "(Inicio - X), " + iniY + "(Inicio - Y) ;;;; " + finX + "(Fin - X), " + finY + "(Fin - Y)");
		}

		panelVisor = new JPanelObstaculo(obstaculos);

		panelVisor.setBackground(Color.WHITE);
		panelVisor.setSize(dimensionHorizontalJFrame, dimensionVerticalJFrame);
		panelVisor.setLayout(null);

		System.out.println("Tamanio del visor -> " + panelVisor.size());
		splitPane.setLeftComponent(panelVisor);

		System.out.println("Escenario actual simulado con " + nroRobots + " robots y " + nroVictimas + " victimas, teniendo el mapa " + nroObstaculos + " obstaculos.");
		System.out.println("Los elementos estan localizados en el escenario como sigue ......\n");



		//*********************************************************************************************
		//Aniadir al panel panelVisor los componentes label que representan los robots leidos del xml
		//*********************************************************************************************
		for (int j = 0; j < nroRobots; j++) {
			Element info = rXMLTRobots.getRobotElement(nodeLstRobots, j);
			String valueid = rXMLTRobots.getRobotIDValue(info, "id");
			Coordinate valueInitialCoordinate = rXMLTRobots.getRobotCoordinate(info);
			int coordinateX = (int) valueInitialCoordinate.x;
			int coordinateY = (int) valueInitialCoordinate.y;
			//coordinateX = Math.abs(coordinateX);
			//coordinateY = Math.abs(coordinateY);

			//crear el label y posicionarlo en el JPanel
			JLabel label = new JLabel("");
			String rutaIconoRobot = directorioTrabajo + "/" + rutassrc + rutapaqueteConstructorEscenariosROSACE + imageniconoRobot;

			//System.out.println("Ruta->" + rutaIconoRobot);

			label.setIcon(new javax.swing.ImageIcon(rutaIconoRobot));

			//El texto que se pone en el label NO es el nombre completo del robot, solo ponemos el numero. 
			//Por ejemplo, de robotIgualitario2 nos quedaria 2, y 2 sería el texto que ponemos en el label
			int index = utilsCadenaComponente.getNumberStartIndex(valueid);
			String idNumero = utilsCadenaComponente.getNumber(valueid, index);
			label.setText(idNumero);
			label.setEnabled(true);
			label.setVisible(true);

			Dimension size = label.getPreferredSize();
			label.setBounds(coordinateX, coordinateY, size.width, size.height);
			panelVisor.add(label);

			robotslabel.put(idNumero, label);   //Lo anoto en el Map: la clave es el numero del robot y contenido es el label creado

			System.out.println("Localizacion del robot " + label.getText() + "-> (" + label.getLocation().x + "," + label.getLocation().y + ")");
		}

		System.out.println("");


		//*********************************************************************************************        
		//Aniadir al panel panelVisor los componentes label que representan las victimas leidas del xml
		//*********************************************************************************************                
		for (int j = 0; j < nroVictimas; j++) {
			//Obtain info about first victim located at the test sequence 
			Element info = rXMLTSeq.getVictimElement(nodeLstVictimas, j);
			String valueid = rXMLTSeq.getVictimIDValue(info, "id");
			Coordinate valueInitialCoordinate = rXMLTSeq.getVictimCoordinate(info);
			int coordinateX = (int) valueInitialCoordinate.x;
			int coordinateY = (int) valueInitialCoordinate.y;
			//coordinateX = Math.abs(coordinateX);
			//coordinateY = Math.abs(coordinateY);

			int index = utilsCadenaComponente.getNumberStartIndex(valueid);
			String idNumero = utilsCadenaComponente.getNumber(valueid, index);

			//System.out.println("idNumero->" + idNumero);

			int indexVictima;

			if (idNumero.equals("")) {
				indexVictima = 0;
				idNumero = "0";
				//System.out.println("El valor de idNumero es vacio");
			} else {
				int aux = utilsCadenaComponente.getNumberStartIndexPrimerDigitoDistintoCero(idNumero);
				idNumero = utilsCadenaComponente.getNumberSinCerosAlaIzquierda(idNumero, aux);
				//System.out.println("El valor de idNumero ahora es " + idNumero);           	
				indexVictima = Integer.parseInt(idNumero);
			}


			//Las victimas con identificador IMPAR se representaran con el icono de mujer. Por ejemplo, Victim.1, Victim.3, Victim.5, ....
			//Las victimas con identificador PAR se representaran con el icono de hombre.  Por ejemplo, Victim.2, Victim.4, Victim.6, ....
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			int numero = indexVictima % 2;
			String rutaIconoVictima = "";
			if (numero == 0) {
				rutaIconoVictima = directorioTrabajo + "/" + rutassrc + rutapaqueteConstructorEscenariosROSACE + imageniconoHombre;
			} else {
				rutaIconoVictima = directorioTrabajo + "/" + rutassrc + rutapaqueteConstructorEscenariosROSACE + imageniconoMujer;
			}

			//crear el label para la victima y posicionarlo
			JLabel label = new JLabel("");
			label.setIcon(new javax.swing.ImageIcon(rutaIconoVictima));
			//El texto que se pone en el label NO es el nombre completo de la victima, solo ponemos el numero. 
			//Por ejemplo, de Victim.3 nos quedaria 3, y 3 sería el texto que ponemos en el label
			label.setText(idNumero);
			Dimension size = label.getPreferredSize();
			label.setBounds(coordinateX, coordinateY, size.width, size.height);
			panelVisor.add(label);

			victimaslabel.put(idNumero, label);   //Lo anoto en el Map: la clave es el numero de la victima y contenido es el label creado

			System.out.println("Localizacion de la victima " + label.getText() + "-> (" + label.getLocation().x + "," + label.getLocation().y + ")");
		}


		System.out.println("");
	}

	public synchronized void cambiarPosicionRobot(String valor_idRobot, int nueva_coordx, int nueva_coordy) {

		String numeroRobot = getNumeroRobot(valor_idRobot);

		//JLabel jlabelRobot = new JLabel();

		JLabel jlabelRobot = robotslabel.get(numeroRobot);

		if (jlabelRobot != null) {
			//            JOptionPane.showMessageDialog(panelVisor, "Se llama idRobot:"+valor_idRobot+" X:"+nueva_coordx+ "Y:"+nueva_coordy);
			//            jlabelRobot.setBounds(jlabelRobot.getX()+10, jlabelRobot.getY()+10, jlabelRobot.getWidth(), jlabelRobot.getHeight());
			//            jlabelRobot.setBounds(nueva_coordx, nueva_coordy, jlabelRobot.getWidth(), jlabelRobot.getHeight());
			jlabelRobot.setLocation(nueva_coordx, nueva_coordy);
			this.notifyAll();

			//Eliminar de la visualizacion
			//            jlabelRobot.setVisible(false);
			//            panelVisor.remove(jlabelRobot);
			//Eliminar de la variable mapa que almacena identificadores y posiciones de los robots
			//            robotslabel.remove(numeroRobot);

			//Crear una nueva label en la nueva posicion
			//crear el label y posicionarlo en el JPanel
			//            JLabel label = new JLabel("");
			//            String rutaIconoRobot = directorioTrabajo + "/" + rutassrc + rutapaqueteConstructorEscenariosROSACE + imageniconoRobot;
			//
			//            label.setIcon(new javax.swing.ImageIcon(rutaIconoRobot));
			//
			//            label.setText(numeroRobot);
			//            label.setEnabled(true);
			//            label.setVisible(true);
			//
			//            Dimension size = label.getPreferredSize();
			//            label.setBounds(nueva_coordx, nueva_coordy, size.width, size.height);
			//
			//            panelVisor.add(label);
			//
			//            robotslabel.put(numeroRobot, label);   //Lo anoto en el Map: la clave es el numero del robot y contenido es el label creado

			//            System.out.println("NUEVA Localizacion del robot " + label.getText() + "-> (" + label.getLocation().x + "," + label.getLocation().y + ")");
		} else {
			System.out.println("jlabel nulo");
		}


		//        System.out.println("Localizacion del robot " + jlabelRobot.getText() + "-> " + jlabelRobot.getBounds());
		//System.out.println("Localizacion del robot " + jlabelRobot.getText() + "-> " + jlabelRobot.getLocationOnScreen());
	}

	public void cambiarIconoVictimaARescatada(String valor_idVictima) {

		String numeroVictima = getNumeroVictima(valor_idVictima);

		int numeroIdVictima = Integer.parseInt(numeroVictima);

		JLabel jlabelVictima = new JLabel();

		jlabelVictima = victimaslabel.get(numeroVictima);

		if (jlabelVictima != null) {

			//String rutaAbsolutaIconoVictima = jlabelVictima.getIcon().toString();			
			//System.out.println("victima " + numeroVictima + "  , " + jlabelVictima.getIcon().toString());

			if (numeroIdVictima % 2 == 0) {
				jlabelVictima.setIcon(new javax.swing.ImageIcon(directorioTrabajo + "/" + rutassrc + rutapaqueteConstructorEscenariosROSACE + "HombreRescatado.png"));
				//System.out.println("Es un hombre");
			} else {
				jlabelVictima.setIcon(new javax.swing.ImageIcon(directorioTrabajo + "/" + rutassrc + rutapaqueteConstructorEscenariosROSACE + "MujerRescatada.png"));
				//System.out.println("Es una mujer");
			}

		} else {
			System.out.println("jlabelVictima nulo");
		}
	}

	public void escribirEnAreaTexto(String texto) {

		textAreaMensaje.append(texto);
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	//  Metodos auxiliares para obtener los valores numericos de los robots y las victimas   //
	//  Por ejemplo getNumeroRobot("robotIgualitario12") devuelve 12                         //
	//              getNumeroVictima("Victim.14") devuelve 14                                //
	///////////////////////////////////////////////////////////////////////////////////////////
	private String getNumeroRobot(String valor_idRobot) {

		int index = utilsCadenaComponente.getNumberStartIndex(valor_idRobot);
		String idNumero = utilsCadenaComponente.getNumber(valor_idRobot, index);

		return idNumero;
	}

	private String getNumeroVictima(String valor_idVictima) {

		int index = utilsCadenaComponente.getNumberStartIndex(valor_idVictima);
		String idNumero = utilsCadenaComponente.getNumber(valor_idVictima, index);

		return idNumero;
	}
	public static  ArrayList<LineaObstaculo> getObstaculos(){
		return obstaculos;
	}
}
