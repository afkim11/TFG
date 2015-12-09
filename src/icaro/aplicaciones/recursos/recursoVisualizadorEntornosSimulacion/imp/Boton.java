package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import icaro.infraestructura.entidadesBasicas.comunicacion.Informacion;

public class Boton extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2379502707297575125L;
	
	
	
	private NotificadorInfoUsuarioSimulador notifEvts;
	public Boton(String nombreBoton,final String robot,final String mensageAEnviar,NotificadorInfoUsuarioSimulador notifEvts2){
		super(nombreBoton);
		this.notifEvts=notifEvts2;
		
		this.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				notifEvts.enviarInfoAotroAgente(new Informacion(mensageAEnviar), robot);
				
			}
			
		});
	}
}
