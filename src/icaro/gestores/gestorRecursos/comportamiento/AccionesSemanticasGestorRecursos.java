package icaro.gestores.gestorRecursos.comportamiento;

import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.AdaptadorRegRMI;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstancia;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaGestor;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.HebraMonitorizacion;
import icaro.infraestructura.patronRecursoSimple.FactoriaRecursoSimple;
import icaro.infraestructura.patronRecursoSimple.ItfGestionRecursoSimple;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Implementacin por defecto de las acciones que se ejecutarn por parte del
 * gestor de recursos.
 * 
 * @author Felipe Polo
 * @created 3 de diciembre de 2007
 */

public class AccionesSemanticasGestorRecursos extends
		AccionesSemanticasAgenteReactivo {
//	public static final int intentosCreacion = 3;
	/**
	 * Almacn de los nombres de los agentes que este gestor debe gestionar
	 * @uml.property  name="nombresRecursosGestionados"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	private Vector<String> nombresRecursosGestionados = new Vector<String>();

	/**
	 * Hebra para la monitorizacin
	 * @uml.property  name="hebra"
	 * @uml.associationEnd  
	 */
	private HebraMonitorizacion hebra;

        private ItfUsoConfiguracion config;

	/**
	 * Tiempo de monitorizacion
	 * @uml.property  name="tiempoParaNuevaMonitorizacion"
	 */
	private int tiempoParaNuevaMonitorizacion;
        private int maxNumIntentosCreacionCompGestionados = 3;
        private String esteNodo;
        private DescInstanciaGestor descGestorRecursos;
        private Boolean misInterfacesEstanEnElRegistroRMILocal= false;

	/**
	 * Constructor por defecto
	 */
	public AccionesSemanticasGestorRecursos() {
		super();
		// obtenemos el repositorio de interfaces
		this.itfUsoRepositorio = ClaseGeneradoraRepositorioInterfaces.instance();
	}

	public void configurarGestor() {
		try {
			 config = (ItfUsoConfiguracion) itfUsoRepositorio.obtenerInterfaz(
							NombresPredefinidos.ITF_USO
									+ NombresPredefinidos.CONFIGURACION);

                        tiempoParaNuevaMonitorizacion = Integer.parseInt(config.getValorPropiedadGlobal(NombresPredefinidos.INTERVALO_MONITORIZACION_ATR_PROPERTY));
			 descGestorRecursos = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_RECURSOS);
			 esteNodo = descGestorRecursos.getNodo().getNombreUso();

                        String maxIntentos =config.getValorPropiedadGlobal("maxIntentosCompGestionados");
                        if (maxIntentos != null)
                                                 maxNumIntentosCreacionCompGestionados = Integer.parseInt(maxIntentos);
			this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
					"gestor_configurado",
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
		} catch (Exception e) {
			e.printStackTrace();
			 logger.error("GestorRecursos: Hubo problemas al configurar el gestor de recursos.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Hubo problemas al configurar el gestor de recursos.",
					InfoTraza.NivelTraza.error));
		}

	}

	/**
	 * Crea la lista de recursos que posteriormente se va a utilizar para crear
	 * cada uno de los recursos
	 */
	public void listarRecursos() {
		try {
			logger.debug("GestorRecursos: Listando los recursos definidos en la configuracion.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Listando los recursos definidos en la configuracion.",
					InfoTraza.NivelTraza.debug));
			
			List<DescInstancia> lista = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_RECURSOS).getComponentesGestionados();

			Object[] parametros = new Object[] { lista, new Integer(0) };
			this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("recursos_listados",
					parametros, NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("GestorRecursos: Hubo problemas al listar los recursos desde la configuracion.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Hubo problemas al listar los recursos desde la configuracion.",
					InfoTraza.NivelTraza.error));
		}
	}

	/**
	 * crea el recurso que ocupa el lugar del insdice en la lista de recursos
	 * 
	 * @param lista
	 * @param indice
	 */
	public void crearRecurso(ArrayList<DescInstanciaRecursoAplicacion> lista, Integer indice) {
		Iterator<DescInstanciaRecursoAplicacion> iterador = lista.iterator();
		logger.debug("GestorRecursos: Creando recurso.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"Creando recurso.",
				InfoTraza.NivelTraza.debug));
		int j = 0;
		String nombre;
		boolean error = false;
		boolean encontrado = false;
		DescInstanciaRecursoAplicacion recurso = null;
               
		while (iterador.hasNext() && (encontrado == false)) {
			recurso = iterador.next();
			if (j == indice.intValue()) {// es el que debemos arrancar
				encontrado = true;
				nombre = recurso.getId();
				try {
					logger.debug("GestorRecursos: Creando recurso " + nombre+ ".");
					trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
							"Creando recurso " + nombre+ ".",
							InfoTraza.NivelTraza.debug));
					crearUnRecurso(recurso);
					// si todo ha ido bien, debemos añadirlo a la lista de
					// objetos gestionados por el gestor
					logger.debug("GestorRecursos: Añadiendo recurso " + nombre+ " a la lista de objetos gestionados.");
					trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
							"Añadiendo recurso " + nombre+ " a la lista de objetos gestionados.",
							InfoTraza.NivelTraza.debug));
					this.nombresRecursosGestionados.add(nombre);
				} catch (Exception ex) {
					logger.error("GestorRecursos: Hubo problemas al crear el recurso "+ nombre+ " desde la configuracion. Recuperando errores de creacin.");
					trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
							"Hubo problemas al crear el recurso "+ nombre+ " desde la configuracion. Recuperando errores de creacin.",
							InfoTraza.NivelTraza.error));
					ex.printStackTrace();
					error = true;
				}

			} else
				j++;
		}
		try {
			Object[] parametros;
			if (error == false && encontrado == true) {
				parametros = new Object[] { lista,
						new Integer(indice.intValue() + 1) };
				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
						"recurso_creado", parametros,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			} else if (error == false && encontrado == false)
				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
						"recursos_creados", new Integer(0),
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			else if (error == true) {
				parametros = new Object[] { lista, recurso, indice };
				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
						"error_en_creacion_recurso", parametros,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Crea un recurso y lo registra en el repositorio
	 * 
	 * @param recurso
	 */
	private void crearUnRecurso(DescInstanciaRecursoAplicacion recurso) throws Exception {
		try {

//			ItfUsoConfiguracion config = (ItfUsoConfiguracion) ClaseGeneradoraRepositorioInterfaces
//					.instance().obtenerInterfaz(
//							NombresPredefinidos.ITF_USO
//									+ NombresPredefinidos.CONFIGURACION);

			logger.debug("GestorRecursos: Construyendo recurso " + recurso.getId()+ ".");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Construyendo recurso " + recurso+ ".",
					InfoTraza.NivelTraza.debug));
			// Recurso de aplicacion: local o remoto?
			
			
//			DescInstanciaGestor descGestorRecursos = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_RECURSOS);
//			String esteNodo = descGestorRecursos.getNodo().getNombreUso();

			String nodoDestino = recurso.getNodo().getNombreUso();
			int intentos = 0;
			boolean ok = false;

			if (nodoDestino.equals(esteNodo)) {
             // Se crea el recurso en el mismo nodo
				FactoriaRecursoSimple.instance().crearRecursoSimple(recurso);
            // Si la organizacion esta desplegada en varios nodos entonces hay que registrarlo en el RMI local
            
                                if (! this.config.despliegueOrgEnUnSoloNodo())
                                        addEntityIntfs2Local_RMIregistry(recurso.getId());


			} else {
            // Crear el recuros en n nodo remoto
                             crearUnRecursoEnNodoRemotoConRMI(recurso);
//				while (!ok) {
//					++intentos;
//					try {
////						((FactoriaRecursoSimple) ClaseGeneradoraRepositorioInterfaces
////								.instance()
////								.obtenerInterfaz(
////										NombresPredefinidos.FACTORIA_RECURSO_SIMPLE
////												+ nodoDestino))
////								.crearRecursoSimple(recurso);
//                                            crearUnRecursoEnNodoRemotoConRMI(recurso);
//						ok = true;
//					} catch (Exception e) {
//						e.printStackTrace();
//						trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
//								"Error al crear el recurso "
//										+ recurso.getId()
//										+ " en un nodo remoto. Se volvera a intentar en "
//										+ intentos
//										+ " segundos...\n nodo origen: "
//										+ esteNodo + "\t nodo destino: "
//										+ nodoDestino,
//								InfoTraza.NivelTraza.error));
//						logger
//								.error("Error al crear el recurso "
//										+ recurso.getId()
//										+ " en un nodo remoto. Se volver a intentar en "
//										+ intentos
//										+ " segundos...\n nodo origen: "
//										+ esteNodo + "\t nodo destino: "
//										+ nodoDestino);
//						Thread.sleep(1000 * intentos);
//						ok = false;
//					}
//				}
			}
			logger.debug("GestorRecursos: Recurso " + recurso.getId() + " creado.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Recurso " + recurso.getId() + " creado.",
					InfoTraza.NivelTraza.debug));

		} catch (Exception ex) {
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Error al crear el recurso " + recurso.getId()+ ".",
					InfoTraza.NivelTraza.error));
			logger.error("GestorRecursos: Error al crear el recurso " + recurso+ ".", ex);
			throw ex;
		}
	}

 private void crearUnRecursoEnNodoRemotoConRMI(DescInstanciaRecursoAplicacion recurso) throws Exception {
	// Obtener el RMI regitry del nodo y la interfaz del Gestor de nodo

                    String identNodoRecurso = recurso.getNodo().getNombreUso();
                    String identRecurso = recurso.getId();
                    if (!misInterfacesEstanEnElRegistroRMILocal){ //Las añado
                        if (!AdaptadorRegRMI.registroRMILocalCreado) AdaptadorRegRMI.inicializar();
                            if (!  AdaptadorRegRMI.addElement2LocalRegRMI(NombresPredefinidos.ITF_USO+nombreAgente, this.itfUsoPropiadeEsteAgente))
                            { // No se ha podido añadir la interfaz de uso al registro local
                                trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Error al añadir mi interfaz al registro RMI local. " + nombreAgente+ "No se ha podido añadir  la itf de uso del Gestor de nodo en el registro RMI local " + identNodoRecurso,
					InfoTraza.NivelTraza.error));
                        }
                    }
          try {
            int intentos = 0;
            boolean ok = false;
//            DescInstanciaGestor gestorAgentes = config.getDescInstanciaGestor(identAgente);
//            String nodoDestino = gestorAgentes.getNodo().getNombreUso();
            while (!ok & (intentos <= maxNumIntentosCreacionCompGestionados)) {
                ++intentos;
                try {
                    // Se obtiene la interfaz remota del gestor de Nodo
                    // Se ordena al Gestor de Nodo que cree al Gestor. Se debe esperar la confirmacion
//                  String[] lista = nodeRegistry.list();
//                  Object obj = nodeRegistry.lookup(NombresPredefinidos.NOMBRE_GESTOR_NODO);
//                    ItfUsoAgenteReactivo itfUsoGestorNodo = (ItfUsoAgenteReactivo)nodeRegistry.lookup(NombresPredefinidos.NOMBRE_GESTOR_NODO);
//
//                    if ( !AdaptadorRegRMI.registroRMILocalCreado)
//                                AdaptadorRegRMI.getRegistroRMInodoLocal();
//                     if (AdaptadorRegRMI.registroRMILocal != null)
//                      if ( ! AdaptadorRegRMI.addElement2LocalRegRMI(NombresPredefinidos.ITF_USO+nombreAgente, this.itfUsoPropiadeEsteAgente))

                        
// Buscamos el gestor de nodo del recurso que vamos a crear
                      ItfUsoAgenteReactivo itfUsoGestorNodo = (ItfUsoAgenteReactivo) AdaptadorRegRMI.getItfAgteReactRemoto(identNodoRecurso,NombresPredefinidos.NOMBRE_GESTOR_NODO);
//                    ItfUsoAgenteReactivo itfUsoGestorNodo = (ItfUsoAgenteReactivo) AdaptadorRegRMI.getItfAgenteRemoto(NombresPredefinidos.NOMBRE_GESTOR_NODO, NombresPredefinidos.ITF_USO);
                      if (itfUsoGestorNodo != null) {
    // Creamos el mensaje para ordenarle al GN que cree el recurso en su nodo.
    //  Deberíamos esperar una confirmación de la creacion.
                // Registro la interfaz de uso  del gestor en el registro RMI de la organizacion para que pueda ser localizado y recibir informacion
                      
                        itfUsoGestorNodo.aceptaMensaje(new MensajeSimple(new InfoContEvtMsgAgteReactivo("peticion_crearRecurso",(Object)recurso.getId() )
                                ,this.nombreAgente,NombresPredefinidos.NOMBRE_GESTOR_NODO));
                         ok = true;
                        }
                    else
                        { // La interfaz del GN es null
                        trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Error al crear el recurso. " + identRecurso+ "No se ha podido obtener la itf de uso del Gestor de nodo en el nodo remoto " + identNodoRecurso,
					InfoTraza.NivelTraza.error));
                        }           
                } catch (Exception e) {
                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Error al crear el recurso:  " + identRecurso + " en un nodo remoto. Se volveraa intentar en " + intentos + " segundos...\n No se pudo acceder al Gestor de Nodo en el nodo: "
                            + identNodoRecurso , InfoTraza.NivelTraza.error));
                    logger.error("Error al crear el recurso " + identRecurso + " en un nodo remoto. Se volvera a intentar en " + intentos + " segundos...\n No se pudo acceder al Gestor de Nodo en el nodo: " + identNodoRecurso );


                }
                
                    Thread.sleep(500 * intentos);
//                    ok = false;
                }
          
            if (!ok) {
                trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "No se pudo crear el recurso " + identRecurso + " en un nodo remoto. Se supero el numero de intentos definido " + intentos, InfoTraza.NivelTraza.error));
                logger.error("Error al crear el recurso " + NombresPredefinidos.NOMBRE_GESTOR_RECURSOS + " en un nodo remoto. Se supero el numero de intentos definido " + intentos );
                this.generarInformeErrorIrrecuperable();

            }
            logger.debug("GestorRecursos : Recurso :" + identRecurso + "creado.");
            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Recurso :" + identRecurso + "creado.", InfoTraza.NivelTraza.debug));
        } catch (Exception e) {
			logger.error("GestorRecursos: Error al crear el recurso " + recurso.getId()+ "en el nodo remoto " + identNodoRecurso,e);
			trazas.aceptaNuevaTraza(new InfoTraza(identRecurso,
					"Error al crear el recurso " + identRecurso+ "en el nodo remoto " + identNodoRecurso,
					InfoTraza.NivelTraza.error));
			e.printStackTrace();
			try {
				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
						"error_en_creacion_recurso",
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        }


        
    }

	/**
	 * Funcion que genera el evento para comenzar los reintentos de creacion de
	 * un recurso
	 * 
	 * @param recurso
	 *            el recurso que ha dado fallo en la creacion
	 * @param indice
	 *            para poder luego continuar con la creacion de los recursos
	 */
	public void recuperarErrorCreacionRecurso(List<DescInstanciaRecursoAplicacion> lista,
			DescInstanciaRecursoAplicacion recurso, Integer indice) {
		String idRecurso = recurso.getId();
		Object[] parametros = new Object[] { lista, recurso, maxNumIntentosCreacionCompGestionados,
				indice };
		// por defecto no se implementan politicas de recuperacion
		logger.debug("GestorRecursos: Comenzando los reintentos de creacion para el recurso "+ idRecurso + ".");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"Comenzando los reintentos de creacion para el recurso "+ idRecurso + ".",
				InfoTraza.NivelTraza.debug));
		try {
			this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("reintenta",
					parametros, NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Se intenta crear un recurso el numero de veces que indiquen los
	 * reintentos hasta que se cree correctamente.
	 */
	public void reintentarCreacionRecurso(List<DescInstanciaRecursoAplicacion> lista, DescInstanciaRecursoAplicacion recurso,
			Integer reintento, Integer indice) {
		boolean fin_reintentos = false;
		boolean error = false;
		String idRecurso = recurso.getId();
		
		if (reintento.equals(0)) {
			fin_reintentos = true;
		}
		if (!fin_reintentos) { // se reintenta la creacion
			logger.debug("GestorRecursos: Reintento de creacion " + reintento+ " del recurso " + idRecurso);
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Reintento de creacion " + reintento
					+ " del recurso " + idRecurso,
					InfoTraza.NivelTraza.debug));
			try {

				logger.debug("GestorRecursos: Creando recurso " + idRecurso+ ".");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Creando recurso " + idRecurso+ ".",
						InfoTraza.NivelTraza.debug));
				
				// crearlos uno a uno dependiendo de su tipo
				crearUnRecurso(recurso);

				// si todo ha ido bien, debemos añadirlo a la lista de objetos
				// gestionados por el gestor
				logger.debug("GestorRecursos: Añadiendo recurso " + idRecurso	+ " a la lista de objetos gestionados.");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Añadiendo recurso " + idRecurso	+ " a la lista de objetos gestionados.",
						InfoTraza.NivelTraza.debug));
				this.nombresRecursosGestionados.add(idRecurso);
			} catch (Exception ex) {
				logger.error("GestorRecursos: Hubo problemas al crear el recurso "+ idRecurso+ " desde la configuracin. Recuperando errores de creacin.");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Hubo problemas al crear el recurso "+ idRecurso+ " desde la configuracion. Recuperando errores de creacin.",
						InfoTraza.NivelTraza.error));
				ex.printStackTrace();
				error = true;
			}
		}
		try {
			Object[] parametros;
			if (fin_reintentos) { // ya hay que decidir que se va a hacer una
									// vez que se sabe que no se puede crear el
									// recurso
				logger.debug("GestorRecursos: Agotados los intentos de crear el recurso "+ idRecurso + ". Continuando con la creacin.");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Agotados los intentos de crear el recurso "+ idRecurso + ". Continuando con la creacin.",
						InfoTraza.NivelTraza.debug));
				parametros = new Object[] { lista, recurso,
						new Integer((reintento.intValue() - 1)), indice };
				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
						"continua_creacion", parametros,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			} else if (error) {
				parametros = new Object[] { lista, recurso,
						new Integer((reintento.intValue() - 1)), indice };
				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
						"reintento_error", parametros,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			} else if (!error) {
				parametros = new Object[] { lista,
						new Integer((indice.intValue() + 1)) };
				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("reintento_ok",
						parametros, NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * arranca el siguiente recurso que especifique la configuracion.
	 */
	public void arrancarRecurso(Integer indice) {
		logger.debug("GestorRecursos: Arrancando recurso.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"Arrancando recurso.",
				InfoTraza.NivelTraza.debug));
		boolean errorEnArranque = false;

		// seleccionamos el recurso que corresponde
		String nombreRec = (String) this.nombresRecursosGestionados
				.elementAt(indice.intValue());
		try {
			// recuperamos la interfaz de gestion del recurso
			logger.debug("GestorRecursos: Es necesario arrancar el recurso "+ nombreRec + ", recuperando interfaz de gestion.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Es necesario arrancar el recurso "+ nombreRec + ", recuperando interfaz de gestion.",
					InfoTraza.NivelTraza.debug));
			InterfazGestion itfGesAg = (InterfazGestion) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION + nombreRec);
			// arrancamos el recurso
			logger.debug("GestorRecursos: Arrancando el recurso " + nombreRec+ ".");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Arrancando el recurso " + nombreRec+ ".",
					InfoTraza.NivelTraza.debug));
			if (itfGesAg == null){// No esta registrada en el repositirio de interfaces del nodo local

//                           String identHostRecurso= config.getDescInstanciaRecursoAplicacion(nombreRec).getNodo().getNombreUso();
//                            itfGesAg =  AdaptadorRegRMI.getItfGestionEntidadRemota(identHostRecurso, nombreRec);
                               itfGesAg = (InterfazGestion) AdaptadorRegRMI.getItfRecursoRemoto(nombreRec, NombresPredefinidos.ITF_GESTION );
                            if (itfGesAg == null)// la intf de gestion es null El recruso no ha sido registrado
                            {
                             logger.debug("GestorRecursos: No se puede dar la orden de arranque al recurso "+ nombreRec + ". Porque su interfaz es null");
                             trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					" No se puede dar la orden de arranque al recurso "+ nombreRec + ". Porque su interfaz es null",
					InfoTraza.NivelTraza.debug));
                                        errorEnArranque = true;
                            }
                            else {
                             // Registro la interfaz en el repositorio local y doy la orden de arrancar
                                this.itfUsoRepositorio.registrarInterfaz(NombresPredefinidos.ITF_GESTION + nombreRec, itfGesAg);
                               Remote itfUsoRec = AdaptadorRegRMI.getItfRecursoRemoto(nombreRec,  NombresPredefinidos.ITF_USO);
                              this.itfUsoRepositorio.registrarInterfaz(NombresPredefinidos.ITF_USO + nombreRec, itfUsoRec);

                                logger.debug("GestorRecursos: Registro la interfaz remota del recurso: "+ nombreRec + ". En el repositorio de interfaces");
                                trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					" Registro la interfaz remota del recurso: "+ nombreRec + ".  En el repositorio de interfaces",
					InfoTraza.NivelTraza.debug));
                                        errorEnArranque = false;

                                    }
                            }
                        if (itfGesAg!= null){
                             itfGesAg.arranca();
                             logger.debug("GestorRecursos: Orden de arranque ha sido dada al recurso "+ nombreRec + ".");
                             trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Orden de arranque ha sido dada al recurso "+ nombreRec + ".",
					InfoTraza.NivelTraza.debug));
                        }
//                        else {
//                     // La interfaz del recurso no esta en el repositorio local. Una posibilidad es buscar su itenrfaz remota
//                     // registrarla en el repositorio de interfaces local al GR  y darle la orden de arrancar
////                       String nodoRecurso =     this.config.getDescInstanciaRecursoAplicacion(nombre).getNodo().getNombreUso();
//                            itfGesAg =  AdaptadorRegRMI.getItfGestionAgteReactRemoto(nombreAgente);
//                            if (itfGesAg != null ){
//                                // Registro la interfaz en el repositorio local y doy la orden de arrancar
//                                this.itfUsoRepositorio.registrarInterfaz(NombresPredefinidos.ITF_GESTION + nombre, itfGesAg);
//                                this.itfUsoRepositorio.registrarInterfaz(NombresPredefinidos.ITF_USO + nombre, AdaptadorRegRMI.getItfUsoAgteReactRemoto(nombreAgente));
//                                
//                               
//                            }
//
//                            logger.debug("GestorRecursos: No se puede dar la orden de arranque al recurso "+ nombre + ". Porque su interfaz es null");
//                             trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
//					" No se puede dar la orden de arranque al recurso "+ nombre + ". Porque su interfaz es null",
//					InfoTraza.NivelTraza.debug));
//                                        errorEnArranque = true;
//                        }
		} catch (Exception ex) {
			logger.error("GestorRecursos: Hubo un problema al acceder al interfaz remoto mientras se arrancaba el recurso "	+ nombreRec + " en el gestor de recursos.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Hubo un problema al acceder al interfaz remoto mientras se arrancaba el recurso "	+ nombreRec + " en el gestor de recursos.",
					InfoTraza.NivelTraza.error));
			ex.printStackTrace();
			errorEnArranque = true;
		}
		if (errorEnArranque) { // ha ocurrido algn problema en el arranque del
								// recurso
			try {
				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
						"error_en_arranque_recurso",
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.error("GestorRecursos: Se produjo un error en el arranque del recurso "+ nombreRec + ".");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Se produjo un error en el arranque del recurso "+ nombreRec + ".",
					InfoTraza.NivelTraza.error));
			
		} else {// el recurso ha sido arrancado
			if (indice.intValue() == (this.nombresRecursosGestionados.size() - 1)) { // ya
																						// no
																						// hay
																						// ms
																						// recursos
																						// que
																						// arrancar
				logger.debug("GestorRecursos: Terminado proceso de arranque automatico de recursos.");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Terminado proceso de arranque automtico de recursos.",
						InfoTraza.NivelTraza.debug));
				try {
					this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
							"recursos_arrancados_ok",
							NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
							NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
					this.itfUsoGestorAReportar.aceptaEvento(new EventoRecAgte(
							"gestor_recursos_arrancado_ok",
							NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
							NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.debug("GestorRecursos: Gestor de recursos esperando peticiones.");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Gestor de recursos esperando peticiones.",
						InfoTraza.NivelTraza.debug));

				// creo hebra de monitorizacin
				hebra = new HebraMonitorizacion(tiempoParaNuevaMonitorizacion,
						this.itfUsoPropiadeEsteAgente, "monitorizar");
				this.hebra.start();
			} else { // hay mas recursos que arrancar
				logger.debug("GestorRecursos: Terminado arranque recurso "+ nombreRec + ". Arrancando el siguiente recurso.");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Terminado arranque recurso "+ nombreRec + ". Arrancando el siguiente recurso.",
						InfoTraza.NivelTraza.debug));
				try {
					this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
							"recurso_arrancado", new Integer(
									indice.intValue() + 1),
							NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
							NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Devuelve cierto si es necesario arrancar el recurso
	 * 
	 * @param nombreRecurso
	 * @return
	 */
	/*
	 * private boolean esNecesarioArrancar(String nombreRecurso) { Enumeration
	 * enume = configEspecifica.getListaRecursos().enumerateRecurso(); while
	 * (enume.hasMoreElements()) { Recurso item = (Recurso)enume.nextElement();
	 * if (nombreRecurso.equals(item.getNombre())) return
	 * item.getHayQueArrancarlo(); } logger.error("GestorRecursos: No se
	 * encontr ningn recurso con nombre "+nombreRecurso+" dentro de la lista
	 * de objetos gestionados."); throw new NullPointerException(); }
	 */
	/**
	 * Decide qu hacer en caso de fallos en los recursos.
	 */
	public void decidirTratamientoErrorIrrecuperable() {
		// el tratamiento ser siempre cerrar todo el chiringuito
		logger.debug("GestorRecursos: Se decide cerrar el sistema ante un error critico irrecuperable.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"Se decide cerrar el sistema ante un error crtico irrecuperable.",
				InfoTraza.NivelTraza.debug));
		try {
			this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
					"tratamiento_terminar_recursos_y_gestor_recursos",
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * intenta arrancar los recursos que especifique la config y hayan dado
	 * problemas.
	 */
	public void recuperarErrorArranqueRecurso() {
		// por defecto no se implementan polticas de recuperacin
		logger.debug("GestorRecursos: Fue imposible recuperar el error en el arranque de los recursos.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"Fue imposible recuperar el error en el arranque de los recursos.",
				InfoTraza.NivelTraza.debug));
		try {
			this.itfUsoGestorAReportar.aceptaEvento(new EventoRecAgte(
					"error_en_arranque_gestores",
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
			this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
					"imposible_recuperar_arranque",
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Esta accin no hace nada.
	 */
    @Override
	public void vacio() {
	}

	/**
	 * Elabora un informe del estado en el que se encuentran los recursos y lo
	 * enva al sistema de trazas.
	 */
	public void generarInformeErrorIrrecuperable() {
		// Producimos traza de un error
		logger.error("GestorRecursos: Finalizando gestor de recursos debido a un error irrecuperable.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"Finalizando gestor de recursos debido a un error irrecuperable.",
				InfoTraza.NivelTraza.error));
		try {
			this.itfUsoGestorAReportar.aceptaEvento(new EventoRecAgte(
					"peticion_terminar_todo",
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					this.itfUsoGestorAReportar.getIdentAgente()));
//                        this.itfUsoGestorAReportar.getIdentAgente();
//			this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("informe_generado",
//					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
//					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
                        this.informaraMiAutomata("informe_generado", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * da orden de terminar a un recurso
	 */
	public void finalizarRecurso() {
		// esto hay que recuperarlo de los parmetros
		logger.debug("GestorRecursos: finalizarRecurso():Este metodo no esta implementado");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"finalizarRecurso():Este metodo no esta implementado",
				InfoTraza.NivelTraza.debug));
		throw new UnsupportedOperationException();
	}

	/**
	 * Crea y arranca un recurso. Es necesario pasar las caractersticas del
	 * recurso a crear por parmetro.
	 */
	public void crearRecurso() {
		// esto hay que recuperarlo de los parmetros
		logger.debug("GestorRecursos: crearRecurso():Este metodo no esta implementado");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"crearRecurso():Este mtodo no est implementado",
				InfoTraza.NivelTraza.debug));
		throw new UnsupportedOperationException();
	}

	/**
	 * Monitoriza secuencialmente todos los recursos activos que estn definidos
	 * como necesarios en la monitorizacin.
	 */
	public void monitorizarRecursos() {
		System.out.println("GestorRecursos:Comienza ciclo monitorizacin.");

		boolean errorEncontrado = false;
		// recuperar todos los interfaces de gestin del repositorio que estamos
		// gestionando
		Enumeration enume = nombresRecursosGestionados.elements();
		while (enume.hasMoreElements() && !errorEncontrado) {
			String nombre = (String) enume.nextElement();

			try {

				ItfGestionRecursoSimple itfGes2 = (ItfGestionRecursoSimple) this.itfUsoRepositorio
						.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
								+ nombre);

				int monitoriz = itfGes2.obtenerEstado();

				// TODO poner condiciones de monitorizacion
				if (monitoriz == 0) {
					errorEncontrado = true;
					logger.debug("GestorRecursos:Recurso " + nombre	+ " est en estado errneo o terminado.");
					trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
							"Recurso " + nombre	+ " est en estado errneo o terminado.",
							InfoTraza.NivelTraza.debug));
				} else
					System.out.println("GestorRecursos:Recurso " + nombre
							+ " est ok.");

			} catch (Exception ex) {
				errorEncontrado = true;
				logger.error("GestorRecursos: Hubo errores al monitorizar.");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Hubo errores al monitorizar.",
						InfoTraza.NivelTraza.error));
				ex.printStackTrace();
			}
		}

		if (errorEncontrado)
			try {
				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
						"error_al_monitorizar",
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			} catch (Exception e) {
				e.printStackTrace();
			}
		else
			try {
				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("recursos_ok",
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	/**
	 * Da orden de terminacin a todos los recursos que se encuentran
	 * activos/arrancando
	 */
	public void terminarRecursosActivos() {
		logger.debug("GestorRecursos: Terminando los recursos que estn activos.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"Terminando los recursos que estn activos.",
				InfoTraza.NivelTraza.debug));
		// recorremos todos los recursos gestionados
		Enumeration enumRecursos = this.nombresRecursosGestionados.elements();
		String nombre = "";
		while (enumRecursos.hasMoreElements()) {
			try {
				nombre = (String) enumRecursos.nextElement();
				// slo se terminan los recursos que se arranquen desde aqu.

				// para cada recurso, recuperamos su itf de gestion
				logger.debug("GestorRecursos: Recuperando Itf Gestin del recurso "+ nombre + ".");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Recuperando Itf Gestin del recurso "+ nombre + ".",
						InfoTraza.NivelTraza.debug));
				InterfazGestion itfGesRec = (InterfazGestion) this.itfUsoRepositorio
						.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
								+ nombre);
				// finalizamos el recurso
				logger.debug("GestorRecursos: Terminando el recurso " + nombre	+ ".");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Terminando el recurso " + nombre	+ ".",
						InfoTraza.NivelTraza.debug));
				if (itfGesRec != null ){
                                itfGesRec.termina();
				logger.debug("GestorRecursos: Orden de terminacion ha sido dada al recurso "+ nombre + ".");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Orden de terminacion ha sido dada al recurso "+ nombre + ".",
						InfoTraza.NivelTraza.debug));
                                }
			} catch (Exception ex) {
				logger.error("GestorRecursos: Hubo un problema al acceder a un interfaz remoto mientras se daba orden de terminacin al recurso "+ nombre + ".");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Hubo un problema al acceder a un interfaz remoto mientras se daba orden de terminacin al recurso "+ nombre + ".",
						InfoTraza.NivelTraza.error));
				ex.printStackTrace();
			}
		}
		logger.debug("GestorRecursos: Finalizado proceso de terminacin de todos los recursos.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"Finalizado proceso de terminacin de todos los recursos.",
				InfoTraza.NivelTraza.debug));
		try {
			this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
					"recursos_terminados",
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Intenta recuperar los errores detectados en la monitorizacin siguiendo
	 * la poltica definida para cada recurso.
	 */
	public void recuperarErrorAlMonitorizarRecursos() {
		// por defecto no se implementan polticas de recuperacin
		logger.debug("GestorRecursos: No se pudo recuperar el error de monitorizacin.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"No se pudo recuperar el error de monitorizacin.",
				InfoTraza.NivelTraza.debug));
		try {
			this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
					"imposible_recuperar_error_monitorizacion",
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * destruye los recursos que se crearon a lo largo del ciclo de vida de este
	 * recurso.
	 */
	public void terminarGestorRecursos() {
		// termina el gestor.
		// puede esperarse a que terminen los recursos
		logger.debug("GestorRecursos: Terminando gestor de recursos.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"Terminando gestor de recursos.",
				InfoTraza.NivelTraza.debug));
		try {
                    if (this.hebra !=null)
			this.hebra.finalizar(); // CUIDADO, SI FALLASE LA CREACION DE LOS
									// RECURSOS ESTA HEBRA
		} // NO ESTA INICIALIZADA
		catch (Exception e) {
			e.printStackTrace();
			logger.error("GestorOrganizacion: La hebra no ha podido ser finalizada porque no habia sido creada.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"La hebra no ha podido ser finalizada porque no habia sido creada.",
					InfoTraza.NivelTraza.error));
		}
		try {
			((InterfazGestion) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS))
					.termina();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			this.itfUsoGestorAReportar.aceptaEvento(new EventoRecAgte(
					"gestor_recursos_terminado",
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void clasificaError() {
	}
        public Boolean addEntityIntfs2Local_RMIregistry (String identEntity){
        Boolean interfacesRegistradas = false;
         try {
            Remote itfEntity = (Remote)itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO+identEntity);
                if (itfEntity != null)
                        if (  AdaptadorRegRMI.addElement2LocalRegRMI(NombresPredefinidos.ITF_USO+identEntity, itfEntity)){
                        trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Se añaden las Itfs de uso y de gestion de la entidad :" +identEntity + " -- al RMI registry local",
					InfoTraza.NivelTraza.debug));
                            } else {
                                         trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"No he podido añadir la entidad :" +identEntity + " -- al RMI registry local.",
					InfoTraza.NivelTraza.error));
                                         informaraMiAutomata("error_en_registroRemoto_recurso", null);
                                         return interfacesRegistradas;
                                } else {
                                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"No he podido añadir la entidad :" +identEntity + " -- al RMI registry. La entidad no ha sido registrada en el registro local",
					InfoTraza.NivelTraza.error));
                                        informaraMiAutomata("error_en_registroRemoto_recurso", null);
                                        return interfacesRegistradas;
                                }
         itfEntity = (Remote)itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_GESTION+identEntity);
             if (itfEntity != null)
                        if (  AdaptadorRegRMI.addElement2LocalRegRMI(NombresPredefinidos.ITF_GESTION+identEntity, itfEntity)){
                        trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Se añaden las Itfs de uso y de gestion de la entidad :" +identEntity + " -- al RMI registry local",
					InfoTraza.NivelTraza.debug));
                                        interfacesRegistradas = true;
                            } else {
                                         trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"No he podido añadir la entidad :" +identEntity + " -- al RMI registry local.",
					InfoTraza.NivelTraza.error));
                                         informaraMiAutomata("error_en_registroRemoto_recurso", null);
                                         return false;
                                } else {
                                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"No he podido añadir la entidad :" +identEntity + " -- al RMI registry. La entidad no ha sido registrada en el registro local",
					InfoTraza.NivelTraza.error));
                                        informaraMiAutomata("error_en_registroRemoto_recurso", null);
                                        return false;
                                }

		} catch (Exception ex) {
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Error al registrar  la entidad " + identEntity+ ".",
					InfoTraza.NivelTraza.error));
			logger.error("GestorRecursos: Error al crear el recurso " + identEntity+ ". En el registro RMI local", ex);
                        informaraMiAutomata("error_en_registroRemoto_recurso", null);
//			throw ex;
		}
            return interfacesRegistradas;
}
}
