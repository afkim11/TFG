package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;


import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.informacion.InfoParaDecidirAQuienAsignarObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos.DecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class ReasignarVictima extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		String emisor = (String) params[0];
		InfoEquipo info = (InfoEquipo)params[1];
		InfoParaDecidirAQuienAsignarObjetivo infoDecision = (InfoParaDecidirAQuienAsignarObjetivo) params[2];
		MisObjetivos objs = (MisObjetivos) params[3];
		Focus foco = (Focus) params[4];
		
		info.setBloqueado(emisor);
		Victim v = objs.getVictimaAsignada(emisor);
		DecidirQuienVa o = new DecidirQuienVa(v.getName());
		this.itfProcObjetivos.eliminarHechoWithoutFireRules(infoDecision);
		this.itfProcObjetivos.eliminarHechoWithoutFireRules(foco);
		foco.setFoco(o);
		infoDecision = new InfoParaDecidirAQuienAsignarObjetivo(VocabularioRosace.IdentAgteDistribuidorTareas, info);
		this.itfProcObjetivos.insertarHechoWithoutFireRules(infoDecision);
		this.itfProcObjetivos.insertarHechoWithoutFireRules(o);
		this.itfProcObjetivos.insertarHechoWithoutFireRules(foco);
		this.itfProcObjetivos.insertarHecho(v);
	}

}
