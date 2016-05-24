package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos.TerminarSimulacion;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class FinalizarSimulacion extends TareaSincrona{

	
	private InfoEquipo infoEquipo;
	private static TreeMap<Victim, Long> tiemposAsignacion=null;
	private static TreeMap<Victim, Long> tiemposResolucion=null;
	@Override
	public void ejecutar(Object... params) {
		TerminarSimulacion obj = (TerminarSimulacion)params[0];
		infoEquipo = (InfoEquipo) params[1];
		System.out.println("Numero de victimas: " + VocabularioRosace.victimasTotalesASalvar + "Numero de resueltas: " + obj.victimasResueltas);
		guardaResultados(obj.getTiemposAsignacion(),obj.getTiemposResolucion());
		if(VocabularioRosace.nombreFicheroResultadoSimulacion!=null)escribeResultados(new File(VocabularioRosace.nombreFicheroResultadoSimulacion + ".txt"));

		this.getEnvioHechos().eliminarHechoWithoutFireRules(obj);
		//Si estamos ejecutando en modo script cerramos la aplicacion al terminar simulación,sino continuamos con todo abierto. 
		if(VocabularioRosace.executionMode == 1)System.exit(0);
	}

	public void escribeResultados(File f){
		try {
			FileWriter fw = new FileWriter(f);

			
			
			Set<Entry<Victim, Long>> set = tiemposAsignacion.entrySet();
			Iterator<Entry<Victim,Long>> it = set.iterator();
			fw.write(this.infoEquipo.getNumberOfTeamMembers() + " " + set.size() + " " + VocabularioRosace.tipoSalvamento + " ");
			if(VocabularioRosace.rutaEscenario.contains("CASA")){
				fw.write("1\n");
			}
			else if(VocabularioRosace.rutaEscenario.contains("LLANURA")){
				fw.write("2\n");
			}
			else if(VocabularioRosace.rutaEscenario.contains("ESTADIO")){
				fw.write("3\n");
			}
			else 
				fw.write("0\n");
			while(it.hasNext()){
				Entry<Victim,Long> pareja = it.next();
				Victim v = pareja.getKey();
				Long tiempoAsignacion = pareja.getValue();
				Long tiempoResolucion = tiemposResolucion.get(v);
				String victimId="",robotId="";
				for(int i=7;i<v.getName().length();i++)
					victimId +=v.getName().charAt(i);
				if(v.getIdRobotEncargadoDeMi() == null){
					robotId = "-1";
				}
				else{
					for(int i=26;i<v.getIdRobotEncargadoDeMi().length();i++)
						robotId +=v.getIdRobotEncargadoDeMi().charAt(i);
				}
				String s;
				s = victimId + " " + tiempoAsignacion + " " + tiempoResolucion + " ";
				if(v.isAlive())s = s + "1";
				else s = s + "0";
				s = s + " " + robotId;
				s = s + "\n";
				fw.write(s);
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void guardaResultados(TreeMap<Victim, Long> tiemposA, TreeMap<Victim, Long> tiemposR) {
		tiemposAsignacion = tiemposA;
		tiemposResolucion = tiemposR;
	}
}
