package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos;



import java.util.HashMap;
import java.util.Map;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

public class TerminarSimulacion extends Objetivo{
	public int victimasResueltas;
	public int victimasAsignadas;
	private Map<Victim, Long> tiempoInicio;
	private Map<Victim, Long> tiempoResolucion;
	
	
	public TerminarSimulacion(){
		this.tiempoInicio = new HashMap<Victim,Long>();
		this.tiempoResolucion = new HashMap<Victim,Long>(); 
		this.victimasResueltas = 0;
		this.victimasAsignadas = 0;
	}
	public void inicioVictimaSimulacion(Victim v){
		this.tiempoInicio.put(v, System.currentTimeMillis()-VocabularioRosace.tiempoInicioEjecucion);
		this.victimasAsignadas++;
	}
	public void sumarVictima(Victim v){
		this.victimasResueltas++;
		this.tiempoResolucion.put(v, System.currentTimeMillis()-VocabularioRosace.tiempoInicioEjecucion);
	}
	public  Map<Victim, Long> getTiemposAsignacion() {
		return this.tiempoInicio;
	}
	public  Map<Victim, Long> getTiemposResolucion() {
		return this.tiempoResolucion;
	}
}
