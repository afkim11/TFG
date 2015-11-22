package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;


import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.Rosace.objetivosComunes.AyudarVictima;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.informacion.InfoParaDecidirAQuienAsignarObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos.DecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class ReasignarVictima extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		String emisor = (String) params[0];
		InfoEquipo info = (InfoEquipo)params[1];
		MisObjetivos objs = (MisObjetivos) params[2];
		
		info.setBloqueado(emisor);
		Victim v = objs.getVictimaAsignada(emisor);
		//this.itfProcObjetivos.eliminarHechoWithoutFireRules(infoDecision);
		InfoParaDecidirAQuienAsignarObjetivo infoDecision = new InfoParaDecidirAQuienAsignarObjetivo(VocabularioRosace.IdentAgteDistribuidorTareas, info);
        AyudarVictima ayudarVictima = new AyudarVictima(v.getName());
        ayudarVictima.setState(Objetivo.PENDING);
        ayudarVictima.setPriority(Integer.MAX_VALUE);
        objs.addObjetivo(ayudarVictima);
		this.itfProcObjetivos.actualizarHechoWithoutFireRules(info);
        this.itfProcObjetivos.insertarHechoWithoutFireRules(ayudarVictima);
		infoDecision.inicializarInfoParaDecidir(v.getName());
		this.itfProcObjetivos.insertarHechoWithoutFireRules(infoDecision);
		this.itfProcObjetivos.actualizarHechoWithoutFireRules(objs);
	}

}
