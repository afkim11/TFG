/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.recursos.visualizacionAgenteMasterIA.imp.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Arturo Mazón
 */
public class ventanaMasterIA extends JFrame {
    JLabel robot1;
    JLabel robot2;
    JLabel robot3;
    JLabel robot4;
    JLabel resultado;
    JLabel accion;
    public ventanaMasterIA(){
        super();
        crearElementos();
        organizarElementos();
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(new Dimension(450, 400));
        this.setTitle("Ventana Master IA");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void crearElementos() {
        robot1 = new JLabel("Robot 1: ");robot1.setForeground(Color.RED);
        robot2 = new JLabel("Robot 2: ");robot2.setForeground(Color.BLUE);
        robot3 = new JLabel("Robot 3: ");robot3.setForeground(Color.ORANGE);
        robot4 = new JLabel("Robot 4: ");robot4.setForeground(Color.GREEN);
        resultado = new JLabel("Resultado: ");resultado.setForeground(Color.BLACK);
        accion = new JLabel("Accion: ");accion.setForeground(Color.YELLOW);
        this.setLayout(new GridLayout(4,1));
    }

    private void organizarElementos() {
        this.add(robot1);
        this.add(robot2);
        this.add(robot3);
        this.add(robot4);
        this.add(resultado);
        this.add(accion);
    }

    public void evaluar(int r, String p){
        switch(r){
            case 1 : robot1.setText("robot1 : " + p);break;
            case 2 : robot2.setText("robot2 : " + p);break;
            case 3 : robot3.setText("robot3 : " + p);break;
            case 4 : robot4.setText("robot4 : " + p);break;
        }
    }

    public void setResultado(String r){
        resultado.setText("Resultado : " + r);
    }

    public void setAccion(String a){
        accion.setText("Resultado : " + a);
    }
}
