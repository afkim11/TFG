package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VictimsToRescue;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.InfoCompMovimiento;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class VictimaHaMuerto extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		InfoCompMovimiento infoComMov = (InfoCompMovimiento)params[0];
		MisObjetivos misObj = (MisObjetivos) params[1];
		Victim victima = (Victim) params[2];
		VictimsToRescue victimas = (VictimsToRescue) params[3];
		
		ItfUsoMovimientoCtrl itfcompMov;
		itfcompMov = (ItfUsoMovimientoCtrl) infoComMov.getitfAccesoComponente();
		
		Iterator<Objetivo> it = misObj.getMisObjetivosPriorizados().iterator();
		boolean encontrado=false;
		while(!encontrado && it.hasNext()){
			Objetivo obj = it.next();
			if(obj.getobjectReferenceId().equalsIgnoreCase(victima.getName())){
				encontrado=true;
				misObj.eliminarObjetivoDeMisObjetivosPriorizados(obj);
				if(itfcompMov.getCoordenasDestino().equals(victima.getCoordinateVictim())){
					itfcompMov.parar();
				}
			} 
		}
	}

}
