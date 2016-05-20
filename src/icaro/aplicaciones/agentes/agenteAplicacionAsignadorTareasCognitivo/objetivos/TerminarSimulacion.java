package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos;



import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;


public class TerminarSimulacion extends Objetivo{
	public int victimasResueltas;
	public int victimasAsignadas;
	private TreeMap<Victim, Long> tiempoInicio;
	private TreeMap<Victim, Long> tiempoResolucion;
	private static Comparator<Victim> comparator;

	public TerminarSimulacion(){
		comparator = new Comparator<Victim>() {
			private static final long serialVersionUID = -2375398473570667809L;
			@Override
			public int compare(Victim v1, Victim v2) {

				return v1.getName().compareToIgnoreCase(v2.getName());
			}
		};
		this.tiempoInicio = new TreeMap<Victim,Long>(comparator);
		this.tiempoResolucion = new TreeMap<Victim,Long>(comparator); 
		this.victimasResueltas = 0;
		this.victimasAsignadas = 0;
	}
	public void inicioVictimaSimulacion(Victim v){
		this.tiempoInicio.put(v, System.currentTimeMillis()-VocabularioRosace.tiempoInicioEjecucion);
		this.victimasAsignadas++;
	}
	public void sumarVictima(Victim v){
		if(!this.tiempoResolucion.containsKey(v)){
			this.victimasResueltas++;
			this.tiempoResolucion.put(v, System.currentTimeMillis()-VocabularioRosace.tiempoInicioEjecucion);
		}
	}
	public  TreeMap<Victim, Long> getTiemposAsignacion() {
		return this.tiempoInicio;
	}
	public  TreeMap<Victim, Long> getTiemposResolucion() {
		return this.tiempoResolucion;
	}
	public boolean isAsignada(Victim victim) {
		return this.tiempoInicio.containsKey(victim);
	}
}
