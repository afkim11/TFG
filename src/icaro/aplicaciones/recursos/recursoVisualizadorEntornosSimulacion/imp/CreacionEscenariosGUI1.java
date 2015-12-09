/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ControlCenterGui2.java
 *
 * Created on 29-dic-2011, 20:42:57
 */
package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.imp.ReadXMLTestRobots;
import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.imp.ReadXMLTestSequence;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.animator.SceneAnimator;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.openide.util.Exceptions;
import org.openide.util.Utilities;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author FGarijo
 */
public class CreacionEscenariosGUI1 extends javax.swing.JFrame {

    /** Creates new form ControlCenterGui2 */
    private NotificadorInfoUsuarioSimulador notifEvts;
    private int intervaloSecuencia = 10000; // valor por defecto. Eso deberia ponerse en otro sitio
    private int numMensajesEnviar = 3;
    private boolean primeraVictima = true;
    private VisorEscenariosRosace visorSc;
    private ArrayList identsRobotsEquipo ;
    private javax.swing.JLabel jLabelAux;
    private String directorioTrabajo;
     private String tituloVentanaVisor = "ROSACE Scenario Visor";
    private String rutassrc = "src/";   //poner "src/main/java" si el proyecto de icaro se monta en un proyecto maven
    private String rutapaqueteConstructorEscenariosROSACE = "utilsDiseniaEscenariosRosace/";
    private static  Image IMAGErobot,IMAGEmujer,IMAGEmujerRes ;
     private String imageniconoHombre = "Hombre.png";
    private String imageniconoMujer = "Mujer.png";
    private String imageniconoMujerRescatada = "MujerRescatada.png";
    private String imageniconoHombreRescatado = "HombreRescatado.png";
    private String imageniconoRobot = "Robot.png";
    private JPanel panelVisor;
    private ObjectScene scene;
    private LayerWidget layer;
    private WidgetAction moveAction = ActionFactory.createMoveAction ();
    private Point punto = new Point(1,1);
    private SceneAnimator animator ;
//    public CreacionEscenariosGUI1() {
//        initComponents();
//        String rutaIconoRobot =   rutapaqueteConstructorEscenariosROSACE + imageniconoRobot;
//        IMAGErobot = Utilities.loadImage (rutaIconoRobot);
//        IMAGEmujerRes = Utilities.loadImage ( rutapaqueteConstructorEscenariosROSACE +imageniconoMujerRescatada); 
//        IMAGEmujer = Utilities.loadImage ( rutapaqueteConstructorEscenariosROSACE +imageniconoMujer);
// //       this.cgen = cgenRec;
// //       this.visorSc = visorScn;
////        initComponents();
//        JLabel label = new JLabel("");
//        label.setIcon(new javax.swing.ImageIcon(rutaIconoRobot));
//        label.setEnabled(true);
//            label.setVisible(true);
//
//            Dimension size = label.getPreferredSize();
//            label.setBounds(10, 10, size.width, size.height);
//            this.add(label);
// 
//       
//    }
     public  CreacionEscenariosGUI1() throws Exception {
//        super("visor Escenario ");
        initComponents();
        initEscenario();
//        initEscena();
        
//        scene = new RobotAnimatorTest();
//        jScrollPane1.setName("visor Escenario ");
//        jScrollPane1.setViewportView(scene.createView());
        leerInfoEscenario();
          
        //*********************************************************************************************
        //Aniadir al panel panelVisor los componentes label que representan los robots leidos del xml
        //*********************************************************************************************
       
//        SceneSupport.show(scene);
    }
    private void initEscena(){
        String rutaIconoRobot =   rutapaqueteConstructorEscenariosROSACE + imageniconoRobot;
        IMAGErobot = Utilities.loadImage (rutaIconoRobot);
        IMAGEmujerRes = Utilities.loadImage ( rutapaqueteConstructorEscenariosROSACE +imageniconoMujerRescatada); 
        IMAGEmujer = Utilities.loadImage ( rutapaqueteConstructorEscenariosROSACE +imageniconoMujer);
        scene = new ObjectScene();
        this.layer = new LayerWidget (scene);
        scene.addChild (layer);
        scene.getActions ().addAction (ActionFactory.createZoomAction ());
        scene.getActions ().addAction (ActionFactory.createPanAction ());
        scene.getActions ().addAction (ActionFactory.createMoveAction ());
//        scene.addObjectSceneListener( ObjectSceneEventType.OBJECT_STATE_CHANGED);
//        animator = new SceneAnimator(scene);
//        animator.animateZoomFactor(0.8);
//        scene.getActions ().addAction (new RobotAnimatorTest.MyAction ());
    }
    private void initEscenario(){
        String rutaIconoRobot =   rutapaqueteConstructorEscenariosROSACE + imageniconoRobot;
        IMAGErobot = Utilities.loadImage (rutaIconoRobot);
        IMAGEmujerRes = Utilities.loadImage ( rutapaqueteConstructorEscenariosROSACE +imageniconoMujerRescatada); 
        IMAGEmujer = Utilities.loadImage ( rutapaqueteConstructorEscenariosROSACE +imageniconoMujer);
        JLabel label = new javax.swing.JLabel("RobotPrueba");
//            String rutaIconoRobot = directorioTrabajo + "/" + rutassrc + rutapaqueteConstructorEscenariosROSACE + imageniconoRobot;

       label.setIcon(new javax.swing.ImageIcon(rutaIconoRobot));     //System.out.println("Ruta->" + rutaIconoRobot);
       label.createImage(10,10);
            this.getRootPane().add(label);
            this.repaint();
    }
private void leerInfoEscenario(){
        String rutaIconoRobot =   rutapaqueteConstructorEscenariosROSACE + imageniconoRobot;
         IMAGErobot = Utilities.loadImage (rutaIconoRobot);
        IMAGEmujer = Utilities.loadImage ( rutapaqueteConstructorEscenariosROSACE +imageniconoMujer); 
        IMAGEmujerRes = Utilities.loadImage ( rutapaqueteConstructorEscenariosROSACE +imageniconoMujerRescatada); 
        ReadXMLTestSequence rXMLTSeq = new ReadXMLTestSequence(VocabularioRosace.rutaPruebaFicheroVictimasTest);
        ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots(VocabularioRosace.rutaPruebaFicheroRobotsTest);
        Document docRobots = rXMLTRobots.getDocument(rXMLTRobots.gettestFilePath());
        NodeList nodeLstRobots = rXMLTRobots.getRobotsXMLStructure(docRobots, "robot");   //Obtain all the robots		
        int nroRobots = rXMLTRobots.getNumberOfRobots(nodeLstRobots);
        Document docVictimas = rXMLTSeq.getDocument(rXMLTSeq.gettestFilePath());
        NodeList nodeLstVictimas = rXMLTSeq.getVictimsXMLStructure(docVictimas, "victim");   //Obtain all the victims
        int nroVictimas = rXMLTSeq.getNumberOfVictimsInSequence(nodeLstVictimas);

        System.out.println("Escenario actual simulado con " + nroRobots + " robots y " + nroVictimas + " victimas");
        System.out.println("Los elementos estan localizados en el escenario como sigue ......\n");
        
         for (int j = 0; j < nroRobots; j++) {
            Element info = rXMLTRobots.getRobotElement(nodeLstRobots, j);
            String valueid = rXMLTRobots.getRobotIDValue(info, "id");
            Coordinate valueInitialCoordinate = rXMLTRobots.getRobotCoordinate(info);
            int coordinateX = (int) valueInitialCoordinate.x;
            int coordinateY = (int) valueInitialCoordinate.y;
            //coordinateX = Math.abs(coordinateX);
            //coordinateY = Math.abs(coordinateY);
             
            //crear el label y posicionarlo en el JPanel
//            JLabel label = new JLabel("");
//            String rutaIconoRobot = directorioTrabajo + "/" + rutassrc + rutapaqueteConstructorEscenariosROSACE + imageniconoRobot;

            //System.out.println("Ruta->" + rutaIconoRobot);

//            label.setIcon(new javax.swing.ImageIcon(rutaIconoRobot));
            
            //El texto que se pone en el label NO es el nombre completo del robot, solo ponemos el numero. 
            //Por ejemplo, de robotIgualitario2 nos quedaria 2, y 2 serÃ­a el texto que ponemos en el label
//            int index = utilsCadenaComponente.getNumberStartIndex(valueid);
//            String idNumero = utilsCadenaComponente.getNumber(valueid, index);
//            punto.move (coordinateX,coordinateY);
//            Widget wid = crearNodeWidget("Robot"+idNumero, new Point(coordinateX,coordinateY));
//            scene.addObject("Robot"+idNumero, wid);
//            scene.addNode("Robot"+idNumero).setPreferredLocation(new Point(coordinateX, coordinateY));
//            label.setText(idNumero);
//            label.setEnabled(true);
//            label.setVisible(true);

//            Dimension size = label.getPreferredSize();
//            label.setBounds(coordinateX, coordinateY, size.width, size.height);
//            panelVisor.add(label);

//            robotslabel.put(idNumero, label);   //Lo anoto en el Map: la clave es el numero del robot y contenido es el label creado

//            System.out.println("Localizacion del robot " + label.getText() + "-> (" + label.getLocation().x + "," + label.getLocation().y + ")");
        
        }
//        SceneSupport.show(scene);
//        System.out.println("");


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
//            JLabel label = new JLabel("");
//            label.setIcon(new javax.swing.ImageIcon(rutaIconoVictima));
            //El texto que se pone en el label NO es el nombre completo de la victima, solo ponemos el numero. 
            //Por ejemplo, de Victim.3 nos quedaria 3, y 3 serÃ­a el texto que ponemos en el label
//            label.setText(idNumero);
            
            
//            Dimension size = label.getPreferredSize();
//            label.setBounds(coordinateX, coordinateY, size.width, size.height);
//            Widget victimWid = new Widget(scene);
//            victimWid.setPreferredSize(size);
//            victimWid.setLabel();
//            victimWid.setPreferredLocation(new Point(coordinateX,coordinateY));
//             punto.move (coordinateX,coordinateY);
//             Widget victimWid = crearNodeWidget("victima"+idNumero, new Point(coordinateX,coordinateY));
//             scene.addObject("victima"+idNumero,victimWid);
//            scene.addChild(crearNodeWidget("victima"+idNumero, punto)) ;
//            panelVisor.add(label);

//            victimaslabel.put(idNumero, label);   //Lo anoto en el Map: la clave es el numero de la victima y contenido es el label creado

//            System.out.println("Localizacion de la victima " + label.getText() + "-> (" + label.getLocation().x + "," + label.getLocation().y + ")");
        }
    }
    
    
//    protected Widget crearNodeWidget (String idNode, Point punto) {
//        IconNodeWidget widget = new IconNodeWidget (scene);
//        if (idNode.contains("Robot"))widget.setImage (IMAGErobot);
//        else widget.setImage (IMAGEmujer);     
//        widget.setLabel (idNode);
//        widget.setPreferredLocation(punto);
//        layer.addChild (widget);
//        widget.getActions ().addAction (scene.createWidgetHoverAction());
//        widget.getActions ().addAction (moveAction);
//
//        return widget;
//    }
        public synchronized void cambiarIconoVictimaARescatada(String valor_idVictima) {
        System.out.println("se cambia el icono de la victima a rescatada: "+valor_idVictima );
//        ((IconNodeWidget)scene.findWidget(valor_idVictima)).setImage(IMAGEmujerRes);
 
//        int numeroIdVictima = Integer.parseInt(numeroVictima);
//
//        JLabel jlabelVictima = new JLabel();
//
//        jlabelVictima = victimaslabel.get(numeroVictima);
//
//        if (jlabelVictima != null) {
//
//            //String rutaAbsolutaIconoVictima = jlabelVictima.getIcon().toString();			
//            //System.out.println("victima " + numeroVictima + "  , " + jlabelVictima.getIcon().toString());
//
//            if (numeroIdVictima % 2 == 0) {
//                jlabelVictima.setIcon(new javax.swing.ImageIcon(directorioTrabajo + "/" + rutassrc + rutapaqueteConstructorEscenariosROSACE + "HombreRescatado.png"));
//                //System.out.println("Es un hombre");
//            } else {
//                jlabelVictima.setIcon(new javax.swing.ImageIcon(directorioTrabajo + "/" + rutassrc + rutapaqueteConstructorEscenariosROSACE + "MujerRescatada.png"));
//                //System.out.println("Es una mujer");
//            }
//
//        } else {
//            System.out.println("jlabelVictima nulo");
//        }
    System.out.println("se cambia el icono de la victima a rescatada");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        intervalNumRobots = new javax.swing.JTextField();
        intervalNumVictimas = new javax.swing.JTextField();
        identEquipo = new javax.swing.JTextField();
        jButtonAceptar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        VictimaIcon = new javax.swing.JLabel();
        RobotIcon = new javax.swing.JLabel();
        VictimaIcon1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuEdtEscenario = new javax.swing.JMenu();
        jMenuAddRobot = new javax.swing.JMenu();
        jMenuAddVictima = new javax.swing.JMenu();
        jMenuSalir = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Creación de Escenarios");
        setMaximumSize(new java.awt.Dimension(214748, 21474836));
        setMinimumSize(new java.awt.Dimension(30, 30));
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });

        jLabel1.setText("Numero de Robots");

        jLabel3.setText("Numero de Victimas");

        intervalNumRobots.setText("0");
        intervalNumRobots.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intervalNumRobotsActionPerformed(evt);
            }
        });

        intervalNumVictimas.setText("0");

        identEquipo.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        identEquipo.setText(" MiEquipo");
        identEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identEquipoActionPerformed(evt);
            }
        });

        jButtonAceptar.setText("Aceptar");
        jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAceptarActionPerformed(evt);
            }
        });

        jLabel4.setText("Identificador Equipo");

        VictimaIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilsDiseniaEscenariosRosace/Mujer.png"))); // NOI18N
        VictimaIcon.setText("Victima");

        RobotIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilsDiseniaEscenariosRosace/Robot.png"))); // NOI18N
        RobotIcon.setText("Robot");

        VictimaIcon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilsDiseniaEscenariosRosace/Mujer.png"))); // NOI18N
        VictimaIcon1.setText("Victima");

        jMenu1.setText("File");
        jMenu1.setIconTextGap(12);

        jMenu6.setText("Guardar");
        jMenu6.setAlignmentX(0.2F);
        jMenu6.setIconTextGap(0);
        jMenu1.add(jMenu6);
        jMenu1.add(jSeparator1);

        jMenuItem2.setText("Salir");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenuEdtEscenario.setText("Editar Escenario");
        jMenuBar1.add(jMenuEdtEscenario);

        jMenuAddRobot.setText("Añadir Robot");
        jMenuBar1.add(jMenuAddRobot);

        jMenuAddVictima.setText("Añadir Victima");
        jMenuAddVictima.setIconTextGap(10);
        jMenuBar1.add(jMenuAddVictima);

        jMenuSalir.setText("Salir");
        jMenuSalir.setIconTextGap(10);
        jMenuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuSalirActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenuSalir);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(VictimaIcon)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(identEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(180, 180, 180)
                                .addComponent(RobotIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(intervalNumRobots, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(intervalNumVictimas, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonAceptar)
                .addGap(121, 121, 121))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(554, Short.MAX_VALUE)
                    .addComponent(VictimaIcon1)
                    .addGap(200, 200, 200)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(intervalNumVictimas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(intervalNumRobots, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonAceptar)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(RobotIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(identEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(74, 74, 74)
                .addComponent(VictimaIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(223, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(47, 47, 47)
                    .addComponent(VictimaIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(319, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void identEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identEquipoActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_identEquipoActionPerformed

    private void intervalNumRobotsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intervalNumRobotsActionPerformed
        // TODO add your handling code here:
        int num1 = Integer.parseInt(intervalNumRobots.getText());
        System.out.println("Numero de robots");
//        if (num1>20 )jOptionPaneAvisoError.setToolTipText("El numero de robots tiene que ser menor que 20");
        
    }//GEN-LAST:event_intervalNumRobotsActionPerformed

    private void jButtonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAceptarActionPerformed
        // TODO add your handling code here:
        System.out.println("Ha pulsado el botÃ³n Aceptar valores Robots y victimas");
         String valor ; 
         setLocationRelativeTo(this);
         
//         String smsg = "Puede cambiar el valor en milisegundos en que deben enviarse las coordenadas";

         String smsg = "Este es el valor en milisegundos leido de la propiedad global \nintervaloMilisegundosEnvioMensajesDesdeCC establecido \n en la descripcion de la organizacion lanzada";         		 
         valor = JOptionPane.showInputDialog(rootPane, smsg, intervaloSecuencia);
//         jOptionPaneAvisoError.setToolTipText(smsg);
//         jOptionPaneAvisoError.setLocation(20,20);
//         jOptionPaneAvisoError.setVisible(true);
//         this.add(jOptionPaneAvisoError);
//         this.validate();
        

    }//GEN-LAST:event_jButtonAceptarActionPerformed

    private void jMenuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuSalirActionPerformed
        // TODO add your handling code here:
        //this.setVisible(false);
        System.out.println("Click");
        this.dispose();
    }//GEN-LAST:event_jMenuSalirActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseReleased

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseDragged
    private void setIntervaloEnvioMensajesDesdeCC(int intervalo){
		intervaloSecuencia = intervalo ;
		int intervaloEnvioMensajesDesdeCC = 1000;
		String strintervaloEnvioMensajesDesdeCC = "";
    }
    public void visualizarIdentsEquipoRobot ( ArrayList<String> equipoIds){
//        eqipoIds = eqipoIds.toArray();
        identsRobotsEquipo = equipoIds;
//        this.listaComponentes.setListData(identsRobotsEquipo.toArray());
//        listaComponentes.setVisible(true);
}
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CreacionEscenariosGUI1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreacionEscenariosGUI1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreacionEscenariosGUI1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreacionEscenariosGUI1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
            CreacionEscenariosGUI1 visor;
                try {
                    visor = new CreacionEscenariosGUI1();
                    visor.setVisible(true);
                } catch (Exception ex) {
                    Exceptions.printStackTrace(ex);
                }
                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel RobotIcon;
    private javax.swing.JLabel VictimaIcon;
    private javax.swing.JLabel VictimaIcon1;
    private javax.swing.JTextField identEquipo;
    private javax.swing.JTextField intervalNumRobots;
    private javax.swing.JTextField intervalNumVictimas;
    private javax.swing.JButton jButtonAceptar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenuAddRobot;
    private javax.swing.JMenu jMenuAddVictima;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuEdtEscenario;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenu jMenuSalir;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
