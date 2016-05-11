package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos.TerminarSimulacion;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class FinalizarSimulacion extends TareaSincrona{
	
	private static Map<Victim, Long> tiemposAsignacion=null;
	private static Map<Victim, Long> tiemposResolucion=null;
	@Override
	public void ejecutar(Object... params) {
		TerminarSimulacion obj = (TerminarSimulacion)params[0];
		
		guardaResultados(obj.getTiemposAsignacion(),obj.getTiemposResolucion());
		if(VocabularioRosace.nombreFicheroResultadoSimulacion!=null)escribeResultados(new File(VocabularioRosace.nombreFicheroResultadoSimulacion));
		
		
		
		System.exit(0);
	}

	public static void escribeResultados(File f){
		try {
			FileWriter fw = new FileWriter(f);
			Set<Entry<Victim, Long>> set = tiemposAsignacion.entrySet();
			Iterator<Entry<Victim,Long>> it = set.iterator();
			while(it.hasNext()){
				Entry<Victim,Long> pareja = it.next();
				Victim v = pareja.getKey();
				Long tiempoAsignacion = pareja.getValue();
				Long tiempoResolucion = tiemposResolucion.get(v);
				String s;
				s = v.getName() + " " + tiempoAsignacion + " " + tiempoResolucion + " ";
				if(v.isAlive())s = s + "si";
				else s = s + "no";
				s = s + "\n";
				fw.write(s);
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void guardaResultados(Map<Victim, Long> tiemposA, Map<Victim, Long> tiemposR) {
		tiemposAsignacion = tiemposA;
		tiemposResolucion = tiemposR;
	}
}
