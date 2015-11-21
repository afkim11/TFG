package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;

import java.rmi.RemoteException;

import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.informacion.InfoParaDecidirAQuienAsignarObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos.DecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;

public class ReasignarVictima extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		String idE;
		try {
			Victim v = (Victim) params[0];
			DecidirQuienVa obj = new DecidirQuienVa(v.getName());
			idE = this.itfConfig.getValorPropiedadGlobal(NombresPredefinidos.NOMBRE_PROPIEDAD_GLOBAL_EQUIPO_AGENTES);
			InfoEquipo ie = new InfoEquipo("", idE);
			InfoParaDecidirAQuienAsignarObjetivo aux = new InfoParaDecidirAQuienAsignarObjetivo(this.identAgente, ie);
			//En aux poner que la del robot bloqueado no se espera y ponerle valor que se sepa que no se lee (Modificar clase que lee) y introducir todo en hechos
			this.itfProcObjetivos.insertarHecho(v);
			this.itfProcObjetivos.insertarHecho(obj);
			this.itfProcObjetivos.insertarHecho(aux);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExcepcionEnComponente e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
