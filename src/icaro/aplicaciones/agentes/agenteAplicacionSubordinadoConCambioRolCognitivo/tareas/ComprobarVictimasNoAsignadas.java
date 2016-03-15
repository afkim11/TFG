package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;

import java.util.ArrayList;

import icaro.aplicaciones.Rosace.informacion.Coste;
import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.RobotStatus;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VictimsToRescue;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Informe;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
/**
 * Tarea encargada de comprobar si se puede asignar una victima que se haya intentado
 * asignar y no se haya podido. Si el robot que ejecuta esta tarea es capaz de llegar
 * a la victima entonces se le manda propuesta para que vaya a salvar a la victima.
 * @author Luis Garcia y Sergio Moreno
 *
 */




public class ComprobarVictimasNoAsignadas extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		VictimsToRescue v2r = (VictimsToRescue) params[0];
		RobotStatus robotS = (RobotStatus) params[1];
		MisObjetivos misObjs = (MisObjetivos) params[2];
		Informe informe =(Informe) params[3];
		this.itfProcObjetivos.eliminarHechoWithoutFireRules(informe);
		ArrayList<Victim> victims = v2r.getVictimNoAsignadas();
		
		double temp=Integer.MAX_VALUE;
		int pos=-1;
		
		for(int i = 0; i < victims.size(); i++){
			Victim v = victims.get(i);
			if (v != null){
				Coste c = new Coste();
				double eval = c.CalculoCosteAyudarVictima(this.identAgente, robotS.getRobotCoordinate(), robotS, v, v2r, misObjs, null);
				if (eval != Integer.MAX_VALUE && eval<temp){
					pos=i;
					temp=eval;
				}
			}
		}
		if(pos!=-1){
			Victim v=victims.get(pos);
			PropuestaAgente miPropuesta = new PropuestaAgente (this.identAgente);
			miPropuesta.setMensajePropuesta(VocabularioRosace.MsgPropuesta_Para_Aceptar_Objetivo);
			miPropuesta.setIdentObjectRefPropuesta(v.getName());
			miPropuesta.setJustificacion(v);
			this.itfProcObjetivos.insertarHecho(miPropuesta);
			v2r.getVictimNoAsignadas().remove(pos);
		}
	}

}
