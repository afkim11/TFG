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

import java.awt.Point;
import org.netbeans.api.visual.model.ObjectSceneEvent;
import org.netbeans.api.visual.model.ObjectSceneListener;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.model.ObjectSceneEventType;


import java.util.Set;

/**
 * @author David Kaspar
 */
public class RobotSceneListenerTest extends RobotSceneTest implements ObjectSceneListener {

    public static void main (String[] args) {
        RobotSceneListenerTest scene = new RobotSceneListenerTest ();
        scene.addNode ("Robot1");
        scene.addNode ("Robot2");
        scene.addNode ("Robot3");
        scene.addNode ("Robot4");
        scene.moveTo ("Robot1",null);       
        SceneSupport.show (scene);
        
        scene. moveTo("Robot2",new Point(800,200));
        scene. moveTo("Robot3",new Point(4000,200));
        scene.moveTo("Robot1",new Point(300,500));
        scene.getSceneAnimator().animatePreferredLocation(scene.findWidget("Robot1"), new Point(100,100));
        scene.getSceneAnimator().animatePreferredLocation(scene.findWidget("Robot2"), new Point(200,500));
        scene.getSceneAnimator().animatePreferredLocation(scene.findWidget("Robot3"), new Point(600,800));
    }

    public RobotSceneListenerTest () {
//        addObjectSceneListener (this, ObjectSceneEventType.values ());
    }

    public void objectAdded (ObjectSceneEvent event, Object addedObject) {
        System.out.println ("addedObject = " + addedObject);
    }

    public void objectRemoved (ObjectSceneEvent event, Object removedObject) {
        System.out.println ("removedObject = " + removedObject);
    }

    public void objectStateChanged (ObjectSceneEvent event, Object changedObject, ObjectState previousState, ObjectState newState) {
        System.out.println ("changedObject = " + changedObject + " | previousState = " + previousState + " | newState = " + newState);
//        Point punto this.findWidget(changedObject).getLocation()
        this.getSceneAnimator().animatePreferredLocation(this.findWidget(changedObject), new Point (100,100));
    }

    public void selectionChanged (ObjectSceneEvent event, Set<Object> previousSelection, Set<Object> newSelection) {
        System.out.println ("previousSelection = " + previousSelection + " | newSelection = " + newSelection);
    }

    public void highlightingChanged (ObjectSceneEvent event, Set<Object> previousHighlighting, Set<Object> newHighlighting) {
        System.out.println ("previousHighlighting = " + previousHighlighting + " | newHighlighting = " + newHighlighting);
    }

    public void hoverChanged (ObjectSceneEvent event, Object previousHoveredObject, Object newHoveredObject) {
        System.out.println ("previousHoveredObject = " + previousHoveredObject + " | newHoveredObject = " + newHoveredObject);
    }

    public void focusChanged (ObjectSceneEvent event, Object previousFocusedObject, Object newFocusedObject) {
        System.out.println ("previousFocusedObject = " + previousFocusedObject + " | newFocusedObject = " + newFocusedObject);
    }

}
