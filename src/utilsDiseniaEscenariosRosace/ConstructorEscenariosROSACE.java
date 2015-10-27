package utilsDiseniaEscenariosRosace;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.event.MouseInputAdapter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JSplitPane;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JTextField;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.utils.ReadXMLTestRobotsConstructorEscenarios;
import icaro.aplicaciones.Rosace.utils.ReadXMLTestSequence;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import javax.swing.JSeparator;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Choice;


public class ConstructorEscenariosROSACE extends JFrame {

	//--------------------------------------------------------------------------------
	//----------------------  VOCABULARIO --------------------------------------------
	
	private String identificadorEquipoAplicacionIgualitario = "robotMasterIA";
	private String identificadorEquipoAplicacionJerarquico  = "robotSubordinado";

	//Variables para indicar propiedades de los robots
	private int initialenergy = 3000;
	private double healrange  = 4.5;
	//Variables para indicar propiedades de las victimas
	private int time = 5;             //5 segundos
	private int severity = 8;         //prioridad de la victima 8

	//Imagenes para mostrar en el escenario
	private String imageniconoHombre =  "Hombre.png";
	private String imageniconoMujer  =  "Mujer.png";
	private String imageniconoRobot  =  "Robot.png";
	
	//Prefijo inicial para los ficheros robots y victimas a salvar
	private String prefijoFicheros   =  "Escenario";

	//Otros
	private String paqueteClaseConstructorEscenariosROSACE = "/utilsDiseniaEscenariosRosace/";
	private String rutaClaseConstructorEscenariosROSACE = "/src" + paqueteClaseConstructorEscenariosROSACE; 

	private String paqueteFicherosDeEscenarios = "/utils/";
	private String rutaFicherosDeEscenarios = "/src" + paqueteFicherosDeEscenarios; 
	
    private String tipoAplicacion = "";
    private String tipoAplicacionIgualitario = "Igualitario";
    private String tipoAplicacionJerarquico  = "Jerarquico";    
    
	//----------------------  FIN VOCABULARIO ----------------------------------------
	//--------------------------------------------------------------------------------
	
	
    private PrintWriter ficheroXMLRobots;          // Fichero para almacenar el xml de los robots
    private PrintWriter ficheroXMLVictimas;        // Fichero para almacenar el xml de la secuencia de victimas
    
	private int dimensionHorizontalJFrame = 1100;  //width  -- ancho 
	private int dimensionVerticalJFrame = 700;     //height -- alto					
	
	private int posicionXInicialJFrame = 0;
	private int posicionYInicialJFrame = 0;
	
	private JPanel contentPane;
	
	private Map<String,Dimension> robots;   //La clave es el indice del robot, es decir, 1, 2, 3, .... El contenido es la posicion del JLabel
	private Map<String,JLabel> robotslabel; //La clave es el indice del robot, es decir, 1, 2, 3, .... El contenido es el JLabel
	private int indexRobot = 1;             //El primer robot sera el 1
	private JButton botonModoRobot = new JButton();
			
	private Map<String,Dimension> victimas;      //La clave es el indice de la victima, es decir, 1, 2, 3, .... El contenido es la posicion del JLabel
	private Map<String,JLabel> victimaslabel;    //La clave es el indice de la victima, es decir, 1, 2, 3, .... El contenido es el JLabel 

	private int indexVictima = 1;  //La primera victima sera la 1		
	private JButton botonModoVictima = new JButton();
	
	private String directorioImagenesConstructorEscenariosROSACE;
	
    private String rutasFicheroRobots = "";  
	public  String rutaFicheroVictimasTest = "";
	
	private String modoInsercion = "Robot";  //Valores posibles: Robot significa que se insertaran robots en el escenario cuando se hace clic, 
	                                         //                  Victima significa que se insertaran victimas en el escenario cuando se hace clic
	
	private String identador1="    ";
	private String identador2="        ";
	private String identador3="            ";
		
	private JTextField txtPrefijoFicheros;
	private JTextField txtModoInsercion;
	private JTextField textInfoCoordenadaObjetoInsertado;
	
	private Choice textRobotName;
	private JLabel lblIdentificadorEquipo; 	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConstructorEscenariosROSACE frame = new ConstructorEscenariosROSACE();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConstructorEscenariosROSACE() {
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 866, 494);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSave = new JMenuItem("Save Scenario");
		mntmSave.addActionListener(new ActionListener() {
			//SALVAR EN UN FICHERO XML LA POSICION DE LAS VICTIMAS Y EN OTRO FICHERO XML LA POSICION DE LOS ROBOTS 
			public void actionPerformed(ActionEvent e) {
				
				int nroRobots = robots.size();
				int nroVictimas = victimas.size();

				if ((nroRobots>0) && (nroVictimas>0))
				   {		    		 
		    		 String AgentName = textRobotName.getSelectedItem();
		    		 
		    		 String AgentNameaux = AgentName.trim();      //Quito los espacios en blanco
		    		  
		    		 if (AgentNameaux.isEmpty()){
						   String mensaje = "ERROR: No se ha salvado el escenario. \n" + 
		    		                        "El nombre de los robots no puede estar vacio. \n" + 
								            "Para poder salvar el escenario debe dar un nombre a los robots. " +
								            "Por ejemplo robotMasterIA \n" +
								            "Otro ejemplo es robotSobordinado" ;
		                   JOptionPane.showMessageDialog(null, mensaje);				    			
		    		 }
		    		 
		    		 else {
		    			 
						 String mensaje = "El nombre de los robots que tiene seleccionado en la lista Identificador Equipo es " +
								   textRobotName.getSelectedItem() + " \n\n" +
								   identificadorEquipoAplicacionIgualitario  + " es el nombre de equipo elegido para una aplicacion del modelo de robots igualitarios \n" +
								   identificadorEquipoAplicacionJerarquico  + " es el nombre de equipo elegido para la aplicacion del modelo jerarquico \n\n" +
 								   "Si el elemento seleccionado es correcto pulse Aceptar y se salvara el escenario \n" +
						           "Si no es correcto pulse Cancelar y seleccione en la lista otro elemento diferente \n" ;
						 
						 int option = JOptionPane.showOptionDialog(null, mensaje, "",  JOptionPane.OK_CANCEL_OPTION, 
				                      JOptionPane.QUESTION_MESSAGE, null, null, null);
																		
						 if (option==JOptionPane.OK_OPTION){
							 
					        saveXMLFileRobots();					 
					        saveXMLFileVictims();
					 					 
				            //Mostrar un mensaje de advertencia indicando donde se han salvado los ficheros
				            int nrorobotsescenario   = robotslabel.size();
				            int nrovictimasescenario = victimaslabel.size();
				     
		                    String directorioTrabajo  = System.getProperty("user.dir");  //Obtener directorio de trabajo      		
		    	            String rutasFicheroRobots = directorioTrabajo + rutaClaseConstructorEscenariosROSACE + 
		    	            							generateRobotScenarioFileName();
		    	                                         
		    	            String rutasFicheroVictimas = directorioTrabajo + rutaClaseConstructorEscenariosROSACE + 
		    	            							  generateVictimScenarioFileName();
		    	            		
		    	            String msg = "Escenario con " + nrorobotsescenario + " robots y " + nrovictimasescenario + " victimas\n";
					               msg = msg + "El escenario se ha salvado en los siguientes ficheros xml\n";
					               msg = msg + "Robots: " + rutasFicheroRobots + "\n";
					               msg = msg + "Secuencia Victimas: " + rutasFicheroVictimas + "\n";
	                        JOptionPane.showMessageDialog(null, msg);	                     
						 }
		    		 }
				   }
				
				else{
				   String mensaje = "ERROR: No se ha salvado el escenario: Nro total robots en el escenario->" + nroRobots + 
                                    " ; Nro total victimas en el escenario->" + nroVictimas;				   
		           JOptionPane.showMessageDialog(null, mensaje);					
				   System.out.println(mensaje);				   
				}
			}
		});
		
		JMenuItem mntmNewScenario = new JMenuItem("New Scenario");
		mntmNewScenario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String msgnewScenario="Desea eliminar todos los elementos del escenario actual "+
				                      "y empezar a crear un nuevo escenario vacio";
				
				int option = JOptionPane.showOptionDialog(null, msgnewScenario, "",  JOptionPane.OK_CANCEL_OPTION, 
	                                                      JOptionPane.QUESTION_MESSAGE, null, null, null);
												
			    if (option==JOptionPane.OK_OPTION){			    	
			    	//Eliminar del escenario todos los robots
			    	EliminarRobotsEscenario();			    	
			    	//Eliminar del escenario todas las victimas			
			    	EliminarVictimasEscenario();
			    					
			    	String msg = "Se han eliminado todos los elementos del escenario actual. " +
			    				 "Ahora puede empezar a crear un nuevo escenario.";												
			    	System.out.println(msg);
			    }
			}
		});		
		mnFile.add(mntmNewScenario);
		mnFile.add(mntmSave);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msgnewScenario="Desea salir de la aplicacion";
	
				int option = JOptionPane.showOptionDialog(null, msgnewScenario, "",  JOptionPane.OK_CANCEL_OPTION, 
														  JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (option==JOptionPane.OK_OPTION){
						System.out.println("Opcion ok pulsada->" + option);
						System.exit(1);

				}								
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);
		
		JMenuItem mntmLoadRobot = new JMenuItem("Load Robots");
		mntmLoadRobot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int nroRobotsEscenarioActual = robotslabel.size();
				if (nroRobotsEscenarioActual>0){
					   String mensaje = "ERROR: Imposible cargar robots desde un fichero xml. El " +
					   		            "escenario ya incluye " + nroRobotsEscenarioActual + " robots";
	                   JOptionPane.showMessageDialog(null, mensaje);				
				}
				else{
					LoadRobotsEscenario();					
				}
			}
		});
		mnTools.add(mntmLoadRobot);
		
		JMenuItem mntmLoadVictims = new JMenuItem("Load Victims");
		mntmLoadVictims.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int nroVictimasEscenarioActual = victimaslabel.size();
				if (nroVictimasEscenarioActual>0){
					   String mensaje = "ERROR: Imposible cargar victimas desde un fichero xml. El " +
					   		            "escenario ya incluye " + nroVictimasEscenarioActual + " victimas";
	                   JOptionPane.showMessageDialog(null, mensaje);				
				}
				else{
					LoadVictimasEscenario();					
				}				
			}
		});
		mnTools.add(mntmLoadVictims);
		
		JSeparator separator = new JSeparator();
		mnTools.add(separator);
		
		JMenuItem mntmClearRobots = new JMenuItem("Clear Robots");
		mntmClearRobots.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msgnewScenario="Desea eliminar todos los robots del escenario actual " +
	                                  "y empezar a introducir de nuevo los robots";
	
				int option = JOptionPane.showOptionDialog(null, msgnewScenario, "",  JOptionPane.OK_CANCEL_OPTION, 
														  JOptionPane.QUESTION_MESSAGE, null, null, null);
				
			    if (option==JOptionPane.OK_OPTION){
			    	//Eliminar del escenario todos los robots
			    	EliminarRobotsEscenario();			    	
			    }				
			}
		});
		mnTools.add(mntmClearRobots);
		
		JMenuItem mntmClearVictims = new JMenuItem("Clear Victims");
		mntmClearVictims.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msgnewScenario="Desea eliminar todos las victimas del escenario actual " +
                        "y empezar a introducir de nuevo las victimas";

				int option = JOptionPane.showOptionDialog(null, msgnewScenario, "",  JOptionPane.OK_CANCEL_OPTION, 
											  JOptionPane.QUESTION_MESSAGE, null, null, null);
	
				if (option==JOptionPane.OK_OPTION){
					//Eliminar del escenario todas las victimas					
					EliminarVictimasEscenario();
				}
			}
		});
		mnTools.add(mntmClearVictims);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "ROSACE Scenario Designer\n";
				       msg = msg + "Developed by .....\n";
				       msg = msg + "Version 1.0\n";
				       msg = msg + "May 2012\n";
				//System.out.println(msg);				
                JOptionPane.showMessageDialog(null, msg);
			}
		});
		mnHelp.add(mntmAbout);

		contentPane = new JPanel();
		
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int X = e.getX();
				int Y = e.getY();
				
				if (modoInsercion.equals("Robot")){
					//Buscar indice libre para asignar al robot
					indexRobot = buscarIndiceLibre(robots);					                    					
					addComponenteRobot(indexRobot, X,Y,contentPane);
					System.out.println("Robot " + indexRobot + " insertado en la posicion X->" + X + " , Y->" + Y + " .....");				
				}

				if (modoInsercion.equals("Victima")){
					//Buscar indice libre para asignar a la victima
					indexVictima = buscarIndiceLibre(victimas);										    			        			    															
					addComponenteVictima(indexVictima, X,Y,contentPane);
					System.out.println("Victima " + indexVictima + " insertada en la posicion X->" + X + " , Y->" + Y + " .....");
				}
			}
		});
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

        //Poner el tipo de layout para el JPanel como Absolute Layout
        contentPane.setLayout(null);

		
		//CONFIGURANDO EL VISOR
        //---------------------------------------------------------------------------
		this.setTitle("ROSACE Scenario Designer");
		
		//Desactivar el boton de maximizar
		this.setResizable(false);

		//Fijar las dimensiones del JFrame
		Dimension dimension = new Dimension();
		dimension.setSize(dimensionHorizontalJFrame, dimensionVerticalJFrame);
		this.setSize(dimension);
				
		//Posicionar el JFrame en la esquina superior izquierda de la pantalla
		this.setLocation(posicionXInicialJFrame, posicionYInicialJFrame);		
		this.setVisible(true);

		//Mostrar el JFrame minimizado
        //this.setExtendedState(JFrame.ICONIFIED);
		
        //Poner en blanco el color del fondo del JPanel
        contentPane.setBackground(Color.WHITE);
        
        JButton btnRobot = new JButton("Robot");
        botonModoRobot = btnRobot;
        btnRobot.setEnabled(false);
        
        btnRobot.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {        		
        		modoInsercion = "Robot";
        		txtModoInsercion.setText("Robot");
        		botonModoRobot.setEnabled(false);
        		botonModoVictima.setEnabled(true);
        	}
        });
        
        btnRobot.setBounds(249, 0, 89, 23);
        contentPane.add(btnRobot);
        
        JButton btnVictima = new JButton("Victima");        
        botonModoVictima = btnVictima;
        
        btnVictima.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		modoInsercion = "Victima";
        		txtModoInsercion.setText("Victima");        		
        		botonModoVictima.setEnabled(false);
        		botonModoRobot.setEnabled(true);
        	}
        });
        btnVictima.setBounds(348, 0, 89, 23);
        contentPane.add(btnVictima);
        
        JLabel lblPrefijoFicheros = new JLabel("Prefijo Ficheros->");
        lblPrefijoFicheros.setBounds(458, 2, 111, 19);
        contentPane.add(lblPrefijoFicheros);
        
        txtPrefijoFicheros = new JTextField();
        txtPrefijoFicheros.setText(prefijoFicheros);
        txtPrefijoFicheros.setBounds(579, 1, 86, 20);
        contentPane.add(txtPrefijoFicheros);
        txtPrefijoFicheros.setColumns(10);
        
        JLabel lblNewLabel = new JLabel("Modo Insercion->");
        lblNewLabel.setBounds(10, 27, 117, 19);
        contentPane.add(lblNewLabel);
        
        txtModoInsercion = new JTextField();
        txtModoInsercion.setText("Robot");
        txtModoInsercion.setBounds(125, 26, 58, 20);
        contentPane.add(txtModoInsercion);
        txtModoInsercion.setColumns(10);
        txtModoInsercion.setEnabled(false);
        
        textInfoCoordenadaObjetoInsertado = new JTextField();
        textInfoCoordenadaObjetoInsertado.setBounds(675, 1, 185, 20);
        contentPane.add(textInfoCoordenadaObjetoInsertado);
        textInfoCoordenadaObjetoInsertado.setColumns(10);
        textInfoCoordenadaObjetoInsertado.setEnabled(false);        
        
        lblIdentificadorEquipo = new JLabel("Identificador Equipo->");
        lblIdentificadorEquipo.setFont(new Font("Tahoma", Font.PLAIN, 9));
        lblIdentificadorEquipo.setBounds(10, 4, 94, 19);
        contentPane.add(lblIdentificadorEquipo);
                
        textRobotName = new Choice();
        textRobotName.setBounds(110, 0, 133, 20);
        contentPane.add(textRobotName);
        AniadirItemstextARobotNameChoice();   //agregar items al objeto textRobotName                
        textRobotName.select(0);              ///opcion por default de choice

        String seleccionChoice = textRobotName.getSelectedItem(); 
        System.out.println("elemento seleccionado en choice " + seleccionChoice);
        
                
        String directorioTrabajo = System.getProperty("user.dir");        
        directorioImagenesConstructorEscenariosROSACE = directorioTrabajo + rutaClaseConstructorEscenariosROSACE;
        
        //System.out.println("Variable directorioImagenesConstructorEscenariosROSACE->" + directorioImagenesConstructorEscenariosROSACE);
                
        robots = new HashMap<String,Dimension>();
        robotslabel = new HashMap<String,JLabel>();
        victimas = new HashMap<String,Dimension>();
        victimaslabel = new HashMap<String,JLabel>();        
	}
	
	
	private void addComponenteRobot(int indexRobot, int x, int y, final JPanel contenedor){
		
		String idRobot = "" + indexRobot;

		final JLabel label = new JLabel("");

		String rutaIconoRobot = directorioImagenesConstructorEscenariosROSACE + imageniconoRobot;				
		label.setIcon(new javax.swing.ImageIcon(rutaIconoRobot));				
		label.setText(idRobot);
        label.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    	//Eliminar de la visualizacion
                        label.setVisible(false);
                        contenedor.remove(label);
                        //Eliminar de la variable mapa que almacena identificadores y posiciones de los robots
                        Dimension d = robots.remove(label.getText());                                                
                        //System.out.println("Tamanio robotlabel->" + robotslabel.size());
                        
                        robotslabel.remove(label.getText());                        
                        System.out.println("Eliminado el robot " + label.getText() + " localizado en (" + d.width + "," + d.height + ")");
                        
                        //System.out.println("Tamanio del mapa -> " + robots.size());                        
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet.");
                    	//System.out.println("raton pressed sobre el componente");
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet.");
                    	//System.out.println("raton released sobre el componente");
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet.");
                    	//System.out.println("raton entrando sobre el componente");
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet.");
                    	//System.out.println("raton saliendo sobre el componente");
            }
        }); 
				
		contenedor.add(label);
		
	    Insets insets = contenedor.getInsets();
	    Dimension size = label.getPreferredSize();

	    label.setBounds(x, y, size.width, size.height);
	    
//	    jl.setBounds(25 + insets.left, 5 + insets.top, size.width, size.height);

	    Dimension d = new Dimension();  //Usamos la clase Dimension para representar un punto x,y
	    d.width = x;
	    d.height = y;	    	    
	    robots.put(idRobot, d);
	    robotslabel.put(idRobot, label);
	    
	    textInfoCoordenadaObjetoInsertado.setText("Info: Robot insertado en (" + x + "," + y + ")");	    
		//System.out.println("rutaIconoRobot->" + rutaIconoRobot);
		//System.out.println("Robots->" + robots);
	}

	
	private void addComponenteVictima(int indexVictima, int x, int y, final JPanel contenedor){
		
		String idVictima = "" + indexVictima;
		String rutaIconoVictima = "";
		
		final JLabel label = new JLabel("");
		
		int numero = indexVictima % 2;				
    	if (numero == 0 )		
    		rutaIconoVictima = directorioImagenesConstructorEscenariosROSACE + imageniconoMujer;
    	else
    		rutaIconoVictima = directorioImagenesConstructorEscenariosROSACE + imageniconoHombre;
    	
		label.setIcon(new javax.swing.ImageIcon(rutaIconoVictima));				
		label.setText(idVictima);
		label.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    	//Eliminar de la visualizacion                    	
                    	label.setVisible(false);
                        contenedor.remove(label);
                        //Eliminar de la variable mapa que almacena identificadores y posiciones de las victimas
                        Dimension d = victimas.remove(label.getText());
                        //System.out.println("Tamanio victimaslabel->" + victimaslabel.size());
                        
                        victimaslabel.remove(label.getText());                        
                        System.out.println("Eliminado la victima " + label.getText() + " localizada en (" + d.width + "," + d.height + ")");
                        
                        ////System.out.println("Tamanio del mapa -> " + victimas.size());
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet.");
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet.");
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet.");
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet.");
            }
        }); 		
		contenedor.add(label);
		
	    Insets insets = contenedor.getInsets();
	    Dimension size = label.getPreferredSize();
	    label.setBounds(x, y, size.width, size.height);
	    
	    Dimension d = new Dimension();  //Usamos la clase Dimension para representar un punto x,y
	    d.width = x;
	    d.height = y;	    	   
	    
	    victimas.put(idVictima, d);
	    victimaslabel.put(idVictima, label);
	    
	    textInfoCoordenadaObjetoInsertado.setText("Info: Victima insertada en (" + x + "," + y + ")");	    
		//System.out.println("rutaIconoVictima->" + rutaIconoVictima);  
		//System.out.println("Victimas->" + victimas);                        
	}


	//Buscar indice libre para asignar al robot o victima
	private int buscarIndiceLibre(Map<String,Dimension> mapa){
		int index = 1;
		
        int nroRobots = mapa.size();
		Set keySet = mapa.keySet();
	    String keyId="";

	    int indiceaux=1;
	    Dimension dim;
	    for (int i=1; i<=(keySet.size()+1); i++){
	    	keyId = "" + i;
	    	dim = mapa.get(keyId);
	    	if (dim==null){
	    		index = i;   //He encontrado un indice libre
	    		break;
	    	}
	    }
	    return index;		
	} 
	
	
	//Numera las etiquetas de los robots con numereos consecutivos empezando con el 1
	private void updateIndicesJLabelRobots(Map<String,JLabel> robotslabel){
		//Eliminar todo el contenido del mapa robots		
		robots = new HashMap<String,Dimension>();

		//Regenerar el contenido del mapa robots y actualizar etiquetas de los robots
		Set keySet = robotslabel.keySet();
		Iterator its = keySet.iterator();
		String keyIdRobot="";
		JLabel labelRobot=null;		  
		int indice = 1;
						
		System.out.println("Actualizando indices de los robots en el escenario para numerarlos desde el 1 hasta el " + keySet.size() + " ......\n");
		
		while(its.hasNext()){
			  String s = (String)its.next();  			  
			  labelRobot = (JLabel)robotslabel.get(s);
			  String nuevaLabel = "" + indice;
			  labelRobot.setText(nuevaLabel);
			  Point pRobot = new Point();			  
			  pRobot = labelRobot.getLocation();
			  
			  Dimension locationRobot = new Dimension();
			  locationRobot.width =  pRobot.x;
			  locationRobot.height = pRobot.y;
			  
			  robots.put(nuevaLabel, locationRobot);
			  
			  System.out.println("Localizacion del robot " + nuevaLabel + 
					             "-> (" + locationRobot.width + "," + locationRobot.height + ")");			  
			  indice++;
		}
	}

	
	private void saveXMLFileRobots(){
    	try {     		    		    			      		  
           	  updateIndicesJLabelRobots(robotslabel);
    		
              String directorioTrabajo = System.getProperty("user.dir");  //Obtener directorio de trabajo        
    		              
              tipoAplicacion = InicializaVariableTipoAplicacion();                                          
                                          
    	      String rutasFicheroRobots = directorioTrabajo + rutaClaseConstructorEscenariosROSACE + generateRobotScenarioFileName();
    	    		                           		
    	      //El nombre del fichero generado se obtiene concatenando el nombre que hay en el cuadro de texto del prefijo de ficheros con _Robots.xml
			  PrintWriter xml = new PrintWriter(new FileOutputStream(rutasFicheroRobots));			  
			  this.ficheroXMLRobots = xml;			  
			  this.ficheroXMLRobots.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			  this.ficheroXMLRobots.println("<sequence>");

			  int nroRobots = robots.size();
			  
			  Set keySet = robots.keySet();
			  Iterator its = keySet.iterator();
			  String keyIdRobot="";
			  Dimension dimRobot;
			  
			  for (int i=1;i<=nroRobots;i++){
				  keyIdRobot=""+i; 
				  dimRobot = robots.get(keyIdRobot);
		          double X = dimRobot.getWidth();
				  double Y = dimRobot.getHeight();
		          double Z = 0.5;	
		          
		          String idAgenteRobot = textRobotName.getSelectedItem().trim() + keyIdRobot;  //trim quita los espacios en blanco
		          
		    	  String cadena =   identador1 + "<robot>" + "\n";
		    	  cadena = cadena + identador2 +      "<id>" + idAgenteRobot + "</id>" + "\n";
		    	  cadena = cadena + identador2 +      "<initialenergy>" + initialenergy + "</initialenergy>" + "\n";
		    	  cadena = cadena + identador2 +      "<initialcoordinate>" + "\n" ;		    	  
		    	  cadena = cadena + identador3 +      		"<x>" + X + "</x>" + "\n";
		    	  cadena = cadena + identador3 +      		"<y>" + Y + "</y>" + "\n";
		    	  cadena = cadena + identador3 +      		"<z>" + Z + "</z>" + "\n";		    	  		    	  
		    	  cadena = cadena + identador2 +      "</initialcoordinate>" + "\n" ;
		    	  cadena = cadena + identador2 +      "<healrange>" + healrange + "</healrange>" + "\n";
		    	  cadena = cadena + identador2 +      "<!--<rangeproximity>30.0</rangeproximity>-->" + "\n";		    	  		    	  			    	  		    	  		    	  		    	 
		    	  cadena = cadena + identador1 + "</robot>" + "\n";
		    	  this.ficheroXMLRobots.print(cadena);    			
			  }
			  		      			  
			  this.ficheroXMLRobots.print("</sequence>");
			  this.ficheroXMLRobots.close();
    		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}    					
	}
	

	//La secuencia generada es en orden del identificador, es decir, se envia la secuencia de victimas Victim.1, Victim.2, Victim.2, .....
	private void saveXMLFileVictims(){
    	try {
              String directorioTrabajo = System.getProperty("user.dir");  //Obtener directorio de trabajo
              
    	      String rutasFicheroVictimas = directorioTrabajo + rutaClaseConstructorEscenariosROSACE + generateVictimScenarioFileName();
    	    		                        
    	      
    	      //El nombre del fichero generado se obtiene concatenando el nombre que hay en el cuadro de texto del prefijo de ficheros con _Victims.xml
			  PrintWriter xml = new PrintWriter(new FileOutputStream(rutasFicheroVictimas));		
			  this.ficheroXMLVictimas = xml;			  
			  this.ficheroXMLVictimas.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			  this.ficheroXMLVictimas.println("<sequence>");

			  int nroVictimas = victimas.size();
			  			  
			  Set keySet = victimas.keySet();
			  Iterator its = keySet.iterator();
			  String keyIdVictima="";
			  
			  int mayor=1;
			  while(its.hasNext()){				  
				  String s = (String)its.next();
				  int numero = Integer.parseInt(s);
				  if (numero>mayor) mayor = numero;				  
			  }
              //mayor contiene el mayor indice de las victimas
			  			  
			  Dimension dimVictima;
			  			  
			  //En la secuencia, las victimas se envian de acuerdo al indice que tienen. Primero se empieza con el menor indice.
			  for (int i=1;i<=mayor;i++){
				  
				  keyIdVictima=""+i;				  
				  dimVictima = victimas.get(keyIdVictima);

				  if (dimVictima!=null) 
				  {				  
					  double X = dimVictima.getWidth();
					  double Y = dimVictima.getHeight();
					  double Z = 0.5;
		          
					  String idVictima = "Victim." + keyIdVictima;
		          
					  String cadena =   identador1 + "<victim>" + "\n";		    	  
					  cadena = cadena + identador2 +      "<time>" + time + "</time>" + "\n";		    	  
					  cadena = cadena + identador2 +      "<id>" + idVictima + "</id>" + "\n";
					  cadena = cadena + identador2 +      "<coordinate>" + "\n" ;		    	  
					  cadena = cadena + identador3 +      		"<x>" + X + "</x>" + "\n";
					  cadena = cadena + identador3 +      		"<y>" + Y + "</y>" + "\n";
					  cadena = cadena + identador3 +      		"<z>" + Z + "</z>" + "\n";	    	  		    	  
					  cadena = cadena + identador2 +      "</coordinate>" + "\n" ;		    	  		    	  
					  cadena = cadena + identador2 +      "<severity>" + severity + "</severity>" + "\n";
					  cadena = cadena + identador2 +      "<requirements>" + "\n" ;		    	  
					  cadena = cadena + identador3 +      		"<requirement>" + 1 + "</requirement>" + "\n";
					  cadena = cadena + identador3 +      		"<requirement>" + 2 + "</requirement>" + "\n";
					  cadena = cadena + identador2 +      "</requirements>" + "\n" ;	    	  		    	  		    	  		    	  
					  cadena = cadena + identador1 + "</victim>" + "\n";		    	  		    	  
					  this.ficheroXMLVictimas.print(cadena);
			      }
			  }
			  		      			  
			  this.ficheroXMLVictimas.print("</sequence>");
			  this.ficheroXMLVictimas.close();			  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}    					
	}
		
	private void LoadRobotsEscenario(){
		//Obtener directorio actual
        String directorioTrabajo  = System.getProperty("user.dir");        
        //Fijar el directorio en que se abrira el JFileChoser
        String DirectoryPathAApuntarJFileChoser = directorioTrabajo + rutaFicherosDeEscenarios;
				
		JFileChooser filechooser = new JFileChooser(DirectoryPathAApuntarJFileChoser);
		int returnVal = filechooser.showOpenDialog(ConstructorEscenariosROSACE.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = filechooser.getSelectedFile();
			String pathFileRobots = file.getAbsolutePath();
			System.out.println("Fichero elegido " + pathFileRobots);

	        //Leer fichero xml abierto que contiene localizacion de robots
			ReadXMLTestRobotsConstructorEscenarios rXMLTRobots = new ReadXMLTestRobotsConstructorEscenarios(pathFileRobots);
			Document docRobots = rXMLTRobots.getDocument(rXMLTRobots.gettestFilePaht());		
			NodeList nodeLstRobots = rXMLTRobots.getRobotsXMLStructure(docRobots, "robot"); //Obtain all the robots		
			int nroRobots =  rXMLTRobots.getNumberOfRobots(nodeLstRobots);
			
			if (nroRobots==0){
				   String mensaje = "ERROR: El numero de robots en el xml elegido es " + nroRobots + ". El fichero xml elegido no " +
				   		            " se corresponde con el " +
				   		            "formato de un fichero xml con informacion sobre localizacion de robots";
                   JOptionPane.showMessageDialog(null, mensaje);
			}
		    else {
			  Element infoaux = rXMLTRobots.getRobotElement(nodeLstRobots, 0);
			  String valueidaux = rXMLTRobots.getRobotIDValue(infoaux,"id");
              //Obtener el tipo			
			  int numero = getNumberStartIndexPrimerDigitoDistintoCero(valueidaux);
			  String nombreDelRobot = valueidaux.substring(0, numero);	

			  int indice = getIndexFParaChoice(nombreDelRobot);
			  textRobotName.select(indice);
			  			  
	          for(int j=0; j<nroRobots;j++){
	        	Element info = rXMLTRobots.getRobotElement(nodeLstRobots, j);    		
			    String valueid = rXMLTRobots.getRobotIDValue(info,"id");
			    Coordinate valueInitialCoordinate = rXMLTRobots.getRobotCoordinate(info);
	            int coordinateX = (int)valueInitialCoordinate.x;
	            int coordinateY = (int)valueInitialCoordinate.y;

				//Buscar indice libre para asignar al robot
				indexRobot = buscarIndiceLibre(robots);					                    					
				addComponenteRobot(indexRobot, coordinateX, coordinateY,contentPane);
				System.out.println("Robot " + indexRobot + " insertado en la posicion X->" + coordinateX + " , Y->" + coordinateY + " .....");				            				        					        	
	          }
		    }
		}		
	}

	
	private int getIndexFParaChoice(String nombreRobot){

		int nroItemsEnChoice = textRobotName.getItemCount();
		
		for (int i = 0; i < nroItemsEnChoice; i++){
            String temchoice = textRobotName.getItem(i);
            if (temchoice.equals(nombreRobot)){
            	return i;            	
            }			
		}					
		return 0;
	}
	
	
	private void LoadVictimasEscenario(){
		
		//Obtener directorio actual
        String directorioTrabajo  = System.getProperty("user.dir");        
        //Fijar el directorio en que se abrira el JFileChoser
        String DirectoryPathAApuntarJFileChoser = directorioTrabajo + rutaFicherosDeEscenarios;
                
		JFileChooser filechooser = new JFileChooser(DirectoryPathAApuntarJFileChoser);
				
		int returnVal = filechooser.showOpenDialog(ConstructorEscenariosROSACE.this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = filechooser.getSelectedFile();
			String pathFileVictimas = file.getAbsolutePath();
			System.out.println("Fichero elegido " + pathFileVictimas);
			
	        //Leer fichero xml abierto que contiene localizacion de las victimas
			ReadXMLTestSequence rXMLTSeq = new ReadXMLTestSequence(pathFileVictimas);
			Document docVictimas = rXMLTSeq.getDocument(rXMLTSeq.gettestFilePaht());		
			NodeList nodeLstVictimas = rXMLTSeq.getVictimsXMLStructure(docVictimas, "victim");   //Obtain all the victims
			int nroVictimas = rXMLTSeq.getNumberOfVictimsInSequence(nodeLstVictimas);

			if (nroVictimas==0){
				   String mensaje = "ERROR: El numero de victimas en el xml elegido es " + nroVictimas + ". El fichero xml elegido no " +
				   		            " se corresponde con el " +
				   		            "formato de un fichero xml con informacion sobre localizacion de victimas";
                JOptionPane.showMessageDialog(null, mensaje);
			}
			
	        for(int j=0; j<nroVictimas;j++){
				Element info = rXMLTSeq.getVictimElement(nodeLstVictimas, j);			
				String valueid = rXMLTSeq.getVictimIDValue(info,"id");
			    Coordinate valueInitialCoordinate = rXMLTSeq.getVictimCoordinate(info);
			    int coordinateX = (int)valueInitialCoordinate.x;
	            int coordinateY = (int)valueInitialCoordinate.y;

				//Buscar indice libre para asignar a la victima
				indexVictima = buscarIndiceLibre(victimas);										    			        			    															
				addComponenteVictima(indexVictima, coordinateX, coordinateY,contentPane);
				System.out.println("Victima " + indexVictima + " insertada en la posicion X->" + coordinateX + " , Y->" + coordinateY + " .....");	        	
	        }			
		}		
	}
	
	
	private void EliminarRobotsEscenario(){
    	//Eliminar del escenario todos los robots
    	Set keySetRobots = robotslabel.keySet();
    	Iterator itsRobots = keySetRobots.iterator();
    	JLabel labelRobot=null;	  												
    	while(itsRobots.hasNext()){
    		String s = (String)itsRobots.next();  			  
    		labelRobot = (JLabel)robotslabel.get(s);					  
    		labelRobot.setVisible(false);
    		contentPane.remove(labelRobot);					  					  
    	}				
    	//Eliminar todo el contenido de los mapas		
    	robots = new HashMap<String,Dimension>();
    	robotslabel =  new HashMap<String,JLabel>();

    	String msg = "Se han eliminado todos los robots del escenario actual. " +
				     "Ahora puede empezar a insertar de nuevo los robots.";											
	    System.out.println(msg);		
	}
	
	
	private void EliminarVictimasEscenario(){
		//Eliminar del escenario todas las victimas			
    	Set keySetVictimas = victimaslabel.keySet();
    	Iterator itsVictimas = keySetVictimas.iterator();
    	JLabel labelVictima=null;		  												
    	while(itsVictimas.hasNext()){
    		String s = (String)itsVictimas.next();  			  
    		labelVictima = (JLabel)victimaslabel.get(s);					  
    		labelVictima.setVisible(false);
    		contentPane.remove(labelVictima);					  					  
    	}
					
    	//Eliminar todo el contenido de los mapas		
    	victimas = new HashMap<String,Dimension>();
    	victimaslabel = new HashMap<String,JLabel>();	
    	String msg = "Se han eliminado todas las victimas del escenario actual. " +
			         "Ahora puede empezar a insertar de nuevo las victimas.";											
        System.out.println(msg);			    						
	}

	
	//El string s es un numero
	//El metodo devuelve la posicion en la que empieza el primer digito distinto de cero
    private int getNumberStartIndexPrimerDigitoDistintoCero(String s){    	
    	int index=0;    	
    	//System.out.println("Cadena->" + s + " , longitud->" + s.length());    	
       	for (int x=0;x<=s.length()-1;x++){       		
    		char ch = s.charAt(x);
    		String sch = "" + ch;
    		int chint = (int)ch;    		
    		int numberchint = chint - 48; //48 es el valor ascii del 0    		
        	//System.out.println("numberchint->" + numberchint);       	
    		if ((numberchint<0) || (numberchint >= 10)) //no es un numero
 		    {
    			;
 		    }
    		else //es un numero   		
    			if (numberchint!=0){    			
    				index = x;
    				break;
    			}    			
    	}       	       	
    	//System.out.println("Pos Cadena->" + index);       	
    	return index;
    }

    
    
    //items que apareceran en la lista desplegable
    private void AniadirItemstextARobotNameChoice(){
    	textRobotName.addItem(identificadorEquipoAplicacionIgualitario);     ///agregar item
    	textRobotName.addItem(identificadorEquipoAplicacionJerarquico);      ///agregar item        	
    } 

    
    //Inicializa la variable privada tipoAplicacion
    private String InicializaVariableTipoAplicacion(){
  	  String tipoAplicacion = "";
  	  
        if (textRobotName.getSelectedItem().equals(identificadorEquipoAplicacionIgualitario )){
      	  tipoAplicacion = tipoAplicacionIgualitario;
        }
        if (textRobotName.getSelectedItem().equals(identificadorEquipoAplicacionJerarquico)){
      	  tipoAplicacion = tipoAplicacionJerarquico;                                    
        }

        return tipoAplicacion;            	  
    }
    
    
    private String generateRobotScenarioFileName(){
          String name =	txtPrefijoFicheros.getText() +
                        "_" + tipoAplicacion + "_xxx_" +  		  
                        robots.size() + "Robots.xml";
          return name;
    }

    
    private String generateVictimScenarioFileName(){
    	String name = txtPrefijoFicheros.getText() +  
    			      "_0IP_Vxx_" +
                       victimas.size() + "Victims.xml";
   	    return name;
    }
    
}
