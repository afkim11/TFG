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
		Focus foco = (Focus) params[3];
		
		info.setBloqueado(emisor);
		Victim v = objs.getVictimaAsignada(emisor);
		DecidirQuienVa o = new DecidirQuienVa(v.getName());
		o.setState(Objetivo.SOLVING);
		//this.itfProcObjetivos.eliminarHechoWithoutFireRules(infoDecision);
		this.itfProcObjetivos.eliminarHechoWithoutFireRules(foco);
		foco.setFoco(o);
		InfoParaDecidirAQuienAsignarObjetivo infoDecision = new InfoParaDecidirAQuienAsignarObjetivo(VocabularioRosace.IdentAgteDistribuidorTareas, info);
        this.getEnvioHechos().eliminarHechoWithoutFireRules(infoDecision);
        AyudarVictima ayudarVictima = new AyudarVictima(v.getName());
        ayudarVictima.setState(Objetivo.PENDING);
   
        this.getEnvioHechos().insertarHechoWithoutFireRules(ayudarVictima);
		infoDecision.inicializarInfoParaDecidir(v.getName());
		this.itfProcObjetivos.actualizarHechoWithoutFireRules(infoDecision);
		this.itfProcObjetivos.actualizarHechoWithoutFireRules(o);
		this.itfProcObjetivos.actualizarHechoWithoutFireRules(foco);
		this.itfProcObjetivos.actualizarHecho(v);
	}

}
