/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 */
package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

//import test.animator.*;
import org.netbeans.api.visual.action.*;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.openide.util.Utilities;
//import test.SceneSupport;

import java.awt.*;
import java.awt.event.MouseEvent;
import org.netbeans.api.visual.animator.SceneAnimator;
import org.openide.util.Exceptions;

/**
 * @author 
 */
public class RobotSceneTest extends GraphScene.StringGraph {

//    private static final Image IMAGE = Utilities.loadImage ("test/resources/displayable_64.png"); // NOI18N
//private static final Image IMAGEmujer = Utilities.loadImage ("imp/MujerRescatada.png");

private String directorioTrabajo;
//String rutaIconoRobot = directorioTrabajo + "/" + rutassrc + rutapaqueteConstructorEscenariosROSACE + imageniconoRobot;
    private String rutassrc = "src/";   //poner "src/main/java" si el proyecto de icaro se monta en un proyecto maven
    private String rutapaqueteConstructorEscenariosROSACE = "utilsDiseniaEscenariosRosace/";
private String imageniconoHombre = "Hombre.png";
    private String imageniconoMujer = "Mujer.png";
    private String imageniconoMujerRescatada = "MujerRescatada.png";
    private String imageniconoHombreRescatado = "HombreRescatado.png";
    private String imageniconoRobot = "Robot.png"; 
    private static  Image IMAGErobot,IMAGEmujer,IMAGEmujerRes ;
    private LayerWidget layer;
    private  SceneAnimator animator;
    private int numRobot;
    private int numRobots = 3;

    private WidgetAction moveAction = ActionFactory.createMoveAction ();

    
    private LayerWidget backgroundLayer;
    private LayerWidget mainLayer;

//    private WidgetAction moveAction = ActionFactory.createMoveAction ();
    private MyAction action = new MyAction ();

    public RobotSceneTest () {
        String rutaIconoRobot =   rutapaqueteConstructorEscenariosROSACE + imageniconoRobot;
        IMAGErobot = Utilities.loadImage (rutaIconoRobot);
        IMAGEmujerRes = Utilities.loadImage ( rutapaqueteConstructorEscenariosROSACE +imageniconoMujerRescatada); 
        IMAGEmujer = Utilities.loadImage ( rutapaqueteConstructorEscenariosROSACE +imageniconoMujer);
        addChild (backgroundLayer = new LayerWidget (this));
        addChild (mainLayer = new LayerWidget (this));

        getActions ().addAction (ActionFactory.createZoomAction ());
        getActions ().addAction (ActionFactory.createPanAction ());
        getActions ().addAction (action);
        getActions ().addAction (ActionFactory.createRectangularSelectAction (this, backgroundLayer));
        
    }

    protected Widget attachNodeWidget (String idNode) {
        IconNodeWidget widget = new IconNodeWidget (this);
        if (idNode.contains("Robot"))widget.setImage (IMAGErobot);
        else widget.setImage (IMAGEmujer);     
        widget.setLabel (idNode);
        mainLayer.addChild (widget);
        widget.getActions ().addAction (createSelectAction ());
        widget.getActions ().addAction (createObjectHoverAction ());
        widget.getActions ().addAction (moveAction);
        return widget;
    }

    protected Widget attachEdgeWidget (String edge) {
        return null;
    }

    protected void attachEdgeSourceAnchor (String edge, String oldSourceNode, String sourceNode) {
    }

    protected void attachEdgeTargetAnchor (String edge, String oldTargetNode, String targetNode) {
    }

    public class MyAction extends WidgetAction.Adapter {

        public WidgetAction.State mouseClicked (Widget widget, WidgetAction.WidgetMouseEvent event) {
            if (event.getButton () == MouseEvent.BUTTON3) {
//                moveTo (widget.event.getPoint ());
                return WidgetAction.State.CONSUMED;
            } else if (event.getButton () == MouseEvent.BUTTON2) {
//                moveTo (null);
                return WidgetAction.State.CONSUMED;
            }
            return WidgetAction.State.REJECTED;
        }

    }

    public  void moveTo (String robotId,Point point) {
        int index = 0;
//        Widget wgRobot = findWidget (robotId);
//        for (String node : getNodes ())
//            wgRobot = findWidget (node);
             if (point == null) point = new Point (++ index * 100, index * 100);
             this.findWidget (robotId).setPreferredLocation(point);
//             wgRobot.setPreferredLocation(point);
             this.repaint();
             
//             getSceneAnimator ().animateZoomFactor(0.8);
//             animator.animateZoomFactor(0.5);
//             getSceneAnimator ().animatePreferredLocation (wgRobot,point);
//             animator.animatePreferredLocation (wgRobot,point);
   
    }

    public static void main (String[] args) {
        RobotSceneTest scene = new RobotSceneTest ();
        scene.addNode ("Robot1");
        scene.addNode ("Robot2");
        scene.addNode ("Robot3");
        scene.addNode ("RobotJefe");
        scene.moveTo ("Robot1",null);
        SceneSupport.show (scene);
    }

}
