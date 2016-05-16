package icaro.infraestructura.clasesGeneradorasOrganizacion;


import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.ClaseGeneradoraRecursoVisualizadorEntornosSimulacion;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.ControladorVisualizacionSimulRosace;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.ClaseGeneradoraRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class ArranqueSistemaScript {
	private static int intervaloSecuencia = 500;
	private static final long serialVersionUID = 1L;
	private static boolean comenzar=false;
	/**
	 * Mtodo de arranque principal de la organizacin
	 * 
	 * @param args
	 *            Entrada: ruta completa hasta el fichero de configuracin
	 */
	private static String nombreXML=null;
	

	public static void setNombreXML(String nombre){
		nombreXML=nombre;
	}
	public static void comenzar(){
		comenzar=true;
	}
	/**
	 * Ejemplo entrada: 
	 * java -jar demo.jar descripcion_4Robots_6VictimasIP_001_Frecuencia10seg_Jerarquico 1
	 * 
	 * @param args
	 */
	public static void main(String args[]) {

		boolean herramientaArrancada = false;

		// creamos los recursos de la organizacin

		ItfUsoConfiguracion configuracionExterna = null;
		ItfUsoRecursoTrazas recursoTrazas = null;
		String msgUsuario;


		if(args.length<2){
			System.err.println("Numero de argumnentos invalido\nEjemplo: nombredescripcion eval");
			System.exit(1);
		}

		nombreXML=args[0];
		Integer eval = Integer.parseInt(args[1]);


		ItfUsoRepositorioInterfaces repositorioInterfaces = null;
		try {
			// Se crea el repositorio de interfaces y el recurso de trazas

			repositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
			recursoTrazas = ClaseGeneradoraRecursoTrazas.instance();
			recursoTrazas.visualizacionDeTrazas(false);
			NombresPredefinidos.ACTIVACION_PANEL_TRAZAS_DEBUG = "false";
			repositorioInterfaces.registrarInterfaz(
					NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS,
					recursoTrazas);
			repositorioInterfaces.registrarInterfaz(
					NombresPredefinidos.ITF_GESTION + NombresPredefinidos.RECURSO_TRAZAS,
					recursoTrazas);
			// Guardamos el recurso de trazas y el repositorio de Itfs en la clase de nombres predefinidos
			NombresPredefinidos.RECURSO_TRAZAS_OBJ = recursoTrazas;
			NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ = repositorioInterfaces;
			//       NombresPredefinidos.DESCRIPCION_XML_POR_DEFECTO = NombresPredefinidos.RUTA_DESCRIPCIONES+args[0];
			NombresPredefinidos.DESCRIPCION_XML_POR_DEFECTO = nombreXML;
		} catch (Exception e) {
			System.err.println("Error. No se pudo crear o registrar el recurso de trazas");
			e.printStackTrace();
			//no es error critico
		}
		// Se crea el iniciador que se encargara de crear el resto de componentes

		ItfGestionAgenteReactivo ItfGestIniciador = null;
		ItfUsoAgenteReactivo ItfUsoIniciador = null;
		try {
			//                DescInstanciaAgente descGestor = configuracionExterna.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
			// creo el agente gestor de organizacion

			FactoriaAgenteReactivo.instancia().crearAgenteReactivo( NombresPredefinidos.NOMBRE_INICIADOR, NombresPredefinidos.COMPORTAMIENTO_PORDEFECTO_INICIADOR);

			ItfGestIniciador = (ItfGestionAgenteReactivo) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_GESTION + NombresPredefinidos.NOMBRE_INICIADOR);
			ItfUsoIniciador = (ItfUsoAgenteReactivo) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO + NombresPredefinidos.NOMBRE_INICIADOR);
			// arranco la organizacion
			if ((ItfGestIniciador != null)&& (ItfUsoIniciador!= null)) {
				ItfGestIniciador.arranca();

				//     DescDefOrganizacion descOrganizacionaCrear = new DescDefOrganizacion();
				//     descOrganizacionaCrear.setIdentFicheroDefOrganizacion(args[0]);
				//         ItfUsoIniciador.aceptaEvento( new EventoRecAgte("crearOrganizacion",descOrganizacionaCrear, "main", "iniciador" ));
				// args[0] contiene el identificador del fichero que contiene la definicion de la organizacion a crear
				//        ItfUsoIniciador.aceptaEvento( new EventoRecAgte("crearOrganizacion",args[0], "main", "iniciador" ));
			}
		} catch (ExcepcionEnComponente e) {
			msgUsuario = "Error. No se ha podido crear el gestor de organizacion con nombre " + NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION;
			recursoTrazas.trazar(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION, msgUsuario, NivelTraza.error);
			System.err.println(msgUsuario);
			System.exit(1);
		}
		catch (Exception e) {
			msgUsuario = "Error. No se ha podido crear el gestor de organizacion con nombre " + NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION;
			recursoTrazas.trazar(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION, msgUsuario, NivelTraza.error);
			System.err.println(msgUsuario);
			System.exit(1);
		}


		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		VocabularioRosace.executionMode = 1;
		
		
		ClaseGeneradoraRecursoVisualizadorEntornosSimulacion claseVisualizacion=null;
		ControladorVisualizacionSimulRosace controlador=null;
		try {
			claseVisualizacion =  (ClaseGeneradoraRecursoVisualizadorEntornosSimulacion) repositorioInterfaces.obtenerInterfaz("Itf_Uso_RecursoVisualizadorEntornosSimulacion1");
			controlador = claseVisualizacion.getControlador();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		controlador.setVisibleControlGUI(false);

		if(eval==1){
			ControladorVisualizacionSimulRosace.modoEnvioVictimas = ControladorVisualizacionSimulRosace.PRIORIZACIONPORORDENDEIDENTIFICACION;
			ControladorVisualizacionSimulRosace.exploracionPrevia = true;
			VocabularioRosace.numeroReconocedores = 1;
		}
		else if(eval==2){
			ControladorVisualizacionSimulRosace.modoEnvioVictimas = ControladorVisualizacionSimulRosace.PRIORIZACIONPORORDENDEIDENTIFICACION;
			VocabularioRosace.numeroReconocedores = 2;
			ControladorVisualizacionSimulRosace.exploracionPrevia = true;
		}
		else if(eval==3){
			ControladorVisualizacionSimulRosace.modoEnvioVictimas = ControladorVisualizacionSimulRosace.PRIORIZADOTIEMPODEVIDA;
			ControladorVisualizacionSimulRosace.exploracionPrevia = false;
		} 
		else System.exit(1);
		controlador.peticionComenzarSimulacion("", intervaloSecuencia);
		VocabularioRosace.nombreFicheroResultadoSimulacion = "resultados" + args[0] + args[1];
		/*while(tiemposAsignacion==null || tiemposResolucion ==null){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		File f = new File("resultados" + args[0] + args[1]);
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		escribeResultados(f);
		System.exit(0);*/

	}
	
}
