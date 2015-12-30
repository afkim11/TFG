package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;

import java.rmi.RemoteException;
import java.util.Iterator;

import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VictimsToRescue;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.InfoCompMovimiento;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class TareaRomperRobot extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		MisObjetivos misObjs = (MisObjetivos) params[0];
		VictimsToRescue victims2Resc = (VictimsToRescue)params[1];
		InfoCompMovimiento infoComMov  = (InfoCompMovimiento)params[2];
		ItfUsoMovimientoCtrl itfcompMov = (ItfUsoMovimientoCtrl) infoComMov.getitfAccesoComponente();
		itfcompMov.parar();
		
		Iterator<Objetivo> it = misObjs.getMisObjetivosPriorizados().iterator();
		while(it.hasNext()){
			Objetivo obj=it.next();
			if(obj.getState()==Objetivo.SOLVING){
				String nombreVictima=obj.getobjectReferenceId();
				Victim v=victims2Resc.getVictimToRescue(nombreVictima);
				Object[] valoresParametrosAccion = new Object[1];
				valoresParametrosAccion[0]=nombreVictima;
				InfoContEvtMsgAgteReactivo msg = new InfoContEvtMsgAgteReactivo("desasignarVictima", valoresParametrosAccion);
				this.getComunicator().enviarInfoAotroAgente(msg, VocabularioRosace.IdentAgteControladorSimulador);
				victims2Resc.addVictimNoAsignadas(v);
				misObjs.eliminarObjetivoDeMisObjetivosPriorizados(obj);
			}
		}
		
	}

}
