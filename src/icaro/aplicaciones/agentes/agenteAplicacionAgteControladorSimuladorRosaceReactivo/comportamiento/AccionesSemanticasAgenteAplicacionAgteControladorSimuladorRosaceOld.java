package icaro.aplicaciones.agentes.agenteAplicacionAgteControladorSimuladorRosaceReactivo.comportamiento;
/*
//import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.aplicaciones.Rosace.informacion.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas;
import icaro.aplicaciones.Rosace.utils.PuntoEstadistica;
import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.imp.ReadXMLTestSequence;
import icaro.aplicaciones.Rosace.utils.VictimaAsignadaEstadistica;
import icaro.aplicaciones.Rosace.utils.VictimaLlegadaEstadistica;
import icaro.aplicaciones.Rosace.informacion.InfoSerieResultadosSimulacion;
import icaro.aplicaciones.recursos.recursoCreacionEntornosSimulacion.ItfUsoRecursoCreacionEntornosSimulacion;
import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.ItfUsoRecursoPersistenciaEntornosSimulacion;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.ItfUsoRecursoVisualizadorEntornosSimulacion;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
//This agent class need the next imports in order to use resources

//Other imports used by this Agent
//#start_nodespecialImports:specialImports <--specialImports-- DO NOT REMOVE THIS
//#end_nodespecialImports:specialImports <--specialImports-- DO NOT REMOVE THIS
public class AccionesSemanticasAgenteAplicacionAgteControladorSimuladorRosaceOld extends AccionesSemanticasAgenteReactivo {

    // Include in this section global variables used in this Agent
    // #start_nodeglobalVariables:globalVariables <--globalVariables-- DO NOT
    // REMOVE THIS
    private ReadXMLTestSequence rXMLTSeq; // para leer del fichero de xml de victimas
    private NodeList nodeLst;      // estructura en memoria con todos los nodos de
    // las victimas que hay en el fichero xml
    private int numMensajesEnviar; // numero total de nodos que hay en nodeLst
    private ItfUsoRecursoVisualizadorEntornosSimulacion itfUsoRecursoVisualizadorEntornosSimulacion;   //Para visualizar graficas de estadisticas
    private InfoEquipo equipo;
    private String identificadorEquipo;
    private ArrayList identsAgtesEquipo;
//	private ComunicacionAgentes comunicacion;
    private boolean stop = false; // Parar la simulacion
//	private ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
    private ItfUsoConfiguracion itfconfig;
    private String rutaFicheroVictimasTest;
    private String rutaFicheroRobotsTest;
    private long tiempoInicialDeLaSimulacion = 0;      //Variable para obtener el tiempo al iniciar la simulacion
    private int numeroVictimasEntorno = 0;            //Numero de victimas actuales que se han introducido en el entorno    
    private int numeroVictimasAsignadas = 0;          //Numero de victimas asignadas a robots
    private int numeroVictimasDiferentesSimulacion; //Numero de victimas diferentes que van a intervenir en el proceso de simulacion
    private int numeroRobotsSimulacion = 0;         //Numero de robots diferentes que van a intervenir en el proceso de simulacion
    private int intervaloSecuencia;                 //Intervalo uniforme de envio de la secuencia de victimas
    private ArrayList<Victim> victimasDefinidas;     //Victimas definidas en la simulacion 
    private Map<String, Victim> victims2Rescue = new HashMap<String, Victim>();      //Victimas que han sido enviadas al equipo   
    private Map<String, String> victimasAsignadas = new HashMap<String, String>();   //Victimas que han sido asignadas a algun robot, es decir, algun robot se ha hecho responsable para salvarla
    private Map<String, InfoAsignacionVictima> infoVictimasAsignadas ;
    private ArrayList<VictimaLlegadaEstadistica> victimasLlegadaEstadistica = new ArrayList<VictimaLlegadaEstadistica>();         //Buffer para estadisticas de llegada de victimas    
    private ArrayList<VictimaAsignadaEstadistica> victimasAsignadasEstadistica = new ArrayList<VictimaAsignadaEstadistica>();     //Buffer para estadisticas de asignacion de victimas
    private ItfUsoRecursoCreacionEntornosSimulacion itfUsoRecursoCreacionEntornosSimulacion = null;
    private ItfUsoRecursoPersistenciaEntornosSimulacion itfUsoRecursoPersistenciaEntornosSimulacion;
    private InfoSerieResultadosSimulacion infoContxVict;
//    private InfoAsignacionVictima infoAsigVictima;
    private InfoCasoSimulacion infoCasoSimul;
    private InfoEntornoCasoSimulacion  infoEntornoCaso;
    private int indexvictimasAsignadasEstadistica = 0;
    final int nMM = this.numMensajesEnviar; // numeroMaximoDeMensajes a  enviar										
    final int interv = intervaloSecuencia;
    // #end_nodeglobalVariables:globalVariables <--globalVariables-- DO NOT
    // REMOVE THIS
    private boolean peticionTerminacionSimulacionUsuario = false;

    // AccionA is the action initial executed when agent manager sends the comenzar event
    public void AccionComenzar() {
        //Inicializar las interfaces a los recursos que se van a utilizar
        //----------------------------------------------------------------------------------------------------------------
        // INICIALIZACION DE VARIABLES RELACIONADAS CON LAS VICTIMAS
        //----------------------------------------------------------------------------------------------------------------			
        // Lectura del fichero de victimas
        // Recuperar la ruta del fichero de victimas del escenario
        //itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();


        //----------------------------------------------------------------------------------------------------------------
        // Inicializar variables para la comunicacion, el identificadorEquipo,
        // los identificadores de los agentes del equipo
        //----------------------------------------------------------------------------------------------------------------	
        try {
            itfconfig = (ItfUsoConfiguracion) this.itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
            rutaFicheroVictimasTest = itfconfig.getValorPropiedadGlobal(VocabularioRosace.rutaFicheroVictimasTest);
            itfUsoRecursoPersistenciaEntornosSimulacion = (ItfUsoRecursoPersistenciaEntornosSimulacion) this.itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO + "RecursoPersistenciaEntornosSimulacion1");
//	rXMLTSeq   =   itfUsoRecursoPersistenciaEntornosSimulacion.getSecuenciaVictimasClase(rutaFicheroVictimasTest);						
//	Document doc = itfUsoRecursoPersistenciaEntornosSimulacion.getDocumentVictimas(rXMLTSeq);
//	nodeLst    =   itfUsoRecursoPersistenciaEntornosSimulacion.getListaNodosVictima(rXMLTSeq, doc);   // Obtain all the victims

            // El numero de mensajes a enviar es la cantidad de victimas que hay en el xml (ojo, algunas podrian estar repetidas)
            // Este numero de mensajes podria no coincidir con el numero total de victimas diferentes que hay en la secuencia
            //	this.numMensajesEnviar = itfUsoRecursoPersistenciaEntornosSimulacion.getNumeroTotalVictimasEnLaSecuencia(rXMLTSeq, nodeLst);
            //			this.numMensajesEnviar = itfUsoRecursoPersistenciaEntornosSimulacion.getNumeroTotalVictimasEnLaSecuencia();					
            identificadorEquipo = itfconfig.getValorPropiedadGlobal(NombresPredefinidos.NOMBRE_PROPIEDAD_GLOBAL_EQUIPO_AGENTES);
            equipo = new InfoEquipo(this.nombreAgente, identificadorEquipo);  //el primer parametro es una cadena con un caracter en blanco, asi obtengo el equipo correctamente
            identsAgtesEquipo = equipo.getTeamMemberIDs();
            this.numeroRobotsSimulacion = identsAgtesEquipo.size();
            itfUsoRecursoVisualizadorEntornosSimulacion = (ItfUsoRecursoVisualizadorEntornosSimulacion) this.itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO + "RecursoVisualizadorEntornosSimulacion1");
            itfUsoRecursoVisualizadorEntornosSimulacion.setIdentAgenteAReportar(this.nombreAgente);
            itfUsoRecursoVisualizadorEntornosSimulacion.mostrarVisualizadorSimulador();
            comunicator = this.getComunicator();
        } catch (Exception e) {
            e.printStackTrace();
        }


        trazas.trazar(this.nombreAgente, "Accion AccionComenzar completada ....", NivelTraza.debug);
    }

    //Esta accion semantica se ejecuta cuando se envia el input "sendSequenceOfSimulatedVictimsToRobotTeam" en el 
    //metodo sendSequenceOfSimulatedVictimsToRobotTeam de la clase NotificacionEventosRecursoGUI3	
    public void SendSequenceOfSimulatedVictimsToRobotTeam(Integer intervaloSecuencia) {
        this.intervaloSecuencia = intervaloSecuencia;
        final int interv = intervaloSecuencia;
        trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, "Accion SendSequenceOfSimulatedVictimsToRobotTeam  ...."
                + intervaloSecuencia, InfoTraza.NivelTraza.debug));
        //--------------------------------------------------------------------------------------------------------------------
        // Inicializar y recuperar la referencia al recurso de estadisticas y el visualizador de estadisticas
        // Inicializar el numero de victimas diferentes que hay en la simulacion en el recurso PersistenciaEntornosSimulacion
        //--------------------------------------------------------------------------------------------------------------------
        long tiempoActual = 0;
        
        try {
            //    itfUsoRecursoPersistenciaEntornosSimulacion.setNumeroVictimasDiferentesSimulacion(rXMLTSeq);
            //	this.numeroVictimasDiferentesSimulacion = itfUsoRecursoPersistenciaEntornosSimulacion.getNumeroVictimasDiferentesSimulacion();
            victimasDefinidas = this.itfUsoRecursoPersistenciaEntornosSimulacion.getVictimasArescatar();
            numeroVictimasDiferentesSimulacion = victimasDefinidas.size();
            infoVictimasAsignadas = new HashMap<String, InfoAsignacionVictima>(); 
            trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                    "Accion SendSequenceOfSimulatedVictimsToRobotTeam  .... "
                    + "Simulacion de tipo " + identificadorEquipo + " ; "
                    + numeroRobotsSimulacion + " robots en la simulacion ; "
                    + numeroVictimasDiferentesSimulacion + " Victimas Diferentes Simulacion "
                    + " (numero Mensajes a Enviar " + this.numMensajesEnviar + ") "
                    + " ; frecuencia de envio " + intervaloSecuencia + " milisegundos", InfoTraza.NivelTraza.debug));

        } catch (Exception e) {
            e.printStackTrace();
        }

        //----------------------------------------------------------------------------------------------------------------	
        // Inicializar fichero Estadistica de llegada de victimas y asignacion
        // de victimas
        //----------------------------------------------------------------------------------------------------------------	
        try {
            // definir Info contexto de la simulacion : se anota el num de victimas de robots, tiempo de incio y otros
            tiempoActual = System.currentTimeMillis();
            tiempoInicialDeLaSimulacion = tiempoActual;
            String identCaso = identificadorEquipo + tiempoActual;
            
       //     infoContxVict = new InfoContextoAsignacionVictima(identificadorEquipo, numeroVictimasDiferentesSimulacion, numeroRobotsSimulacion,  this.intervaloSecuencia); 
            infoEntornoCaso = new InfoEntornoCasoSimulacion(identificadorEquipo, numeroRobotsSimulacion, numeroVictimasDiferentesSimulacion, intervaloSecuencia);
            infoEntornoCaso.setTiempoInicioSimulacion(tiempoInicialDeLaSimulacion);
            infoCasoSimul = new InfoCasoSimulacion (identCaso);
            infoCasoSimul.setInfoCasoSimulacion(infoEntornoCaso);
            infoCasoSimul.setTiempoInicioEnvioPeticiones(tiempoInicialDeLaSimulacion);
            itfUsoRecursoPersistenciaEntornosSimulacion.setTiempoinicial(tiempoActual);
            itfUsoRecursoPersistenciaEntornosSimulacion.crearFicheroXMLTRealLlegadaVictimas(icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasLlegadaVictimas,
                    identificadorEquipo, numeroRobotsSimulacion,
                    numeroVictimasDiferentesSimulacion, this.intervaloSecuencia);
            //Crear el fichero EstadisticasAsignacionVictimas.xml
            itfUsoRecursoPersistenciaEntornosSimulacion.crearFicheroXMLTRealAsignacionVictimasRobots(icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasAsignacionVictimas,
                    identificadorEquipo, numeroRobotsSimulacion,
                    numeroVictimasDiferentesSimulacion, this.intervaloSecuencia);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //----------------------------------------------------------------------------------------------------------------	
        // Crear hilo responsable de realizar el envio de la secuencia de victimas a intervalos regulares de tiempo
        //----------------------------------------------------------------------------------------------------------------	

        //final ReadXMLTestSequence rNXM = rXMLTSeq;
        //final NodeList nl = nodeLst;
        Thread t = new Thread() {

            @Override
            public void run() {
                int i = 0;
                Victim victima;
                while ((i < numeroVictimasDiferentesSimulacion) && (stop == false)) {
                    //      victima = createNewVictim(rXMLTSeq, nodeLst, i);
                    victima = victimasDefinidas.get(i);
                    OrdenCentroControl ccOrder = new OrdenCentroControl("ControlCenter", VocabularioRosace.MsgOrdenCCAyudarVictima, victima);
                    // Escribir nueva linea de estadistica en el fichero de llegada de victimas					
                    try {
                        long tActual = System.currentTimeMillis();
                       
                        //Lo siguiente se hacia en escribeEstadisticaFicheroXMLTRealLlegadaVictimas //EN EL FUTURO HABRIA QUE INTENTAR QUITARLO DE ESE METODO
                        Victim valor = victims2Rescue.put(victima.getName(), victima);
                        if (valor == null) //no estaba insertado
                        {
                            incrementarNumeroVictimasActuales();
                        }
                        //Actualiza el fichero EstadisticasLlegadaVictimas.xml
                        itfUsoRecursoPersistenciaEntornosSimulacion.escribeEstadisticaFicheroXMLTRealLlegadaVictimas(tActual, victima);
                        //Anotar informacion en el buffer de estadisticas de victimas que llegan al entorno
                        InfoAsignacionVictima infoAsigVictima = new InfoAsignacionVictima ();
                        infoAsigVictima.setNrovictimasenentorno(numeroVictimasEntorno);
                        infoAsigVictima.setTiempoPeticion(tActual-tiempoInicialDeLaSimulacion);
                        infoAsigVictima.setVictima(victima);
                //        infoAsigVictima.setInfoCtxAsignacion(infoContxVict);
                        infoCasoSimul.addEnvioVictima(infoAsigVictima);
                        ///////
                        VictimaLlegadaEstadistica victLlegEst = new VictimaLlegadaEstadistica();
                        victLlegEst.setVictima(victima);
                        victLlegEst.setTiempoActual(tActual);
                        victLlegEst.setNrovictimas(numeroVictimasEntorno);
                        //	victimasLlegadaEstadistica.add(i-1, victLlegEst);  //el buffer empieza en la posicion 0
                        victimasLlegadaEstadistica.add(i, victLlegEst);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (identificadorEquipo.equals("robotSubordinado")) //VocabularioRosace.IdentEquipoJerarquico
                    {
                        comunicator.enviarInfoAotroAgente(ccOrder, VocabularioRosace.IdentAgteDistribuidorTareas);
                    } else {
                        comunicator.informaraGrupoAgentes(ccOrder, identsAgtesEquipo);
                    }

                    i++;
                    try {
                        this.sleep(interv);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }// fin del while

                // Se han enviado todas las victimas
                // Cerrar el fichero de estadistica en el fichero de llegada de victimas
                try {
                    //Cerrar el fichero EstadisticasLlegadaVictimas.xml					
                    itfUsoRecursoPersistenciaEntornosSimulacion.cerrarFicheroXMLTRealLlegadaVictimas();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }
    

    //Esta accion semantica se ejecuta cuando se envia el input "victimaAsignadaARobot" en la  
    //tarea sincrona GeneraryEncolarObjetivoActualizarFoco del agente Subordinado
    //Esta accion semantica se ejecuta cuando se envia el input "victimaAsignadaARobot" en la  
    //tarea sincrona EncolarObjetivoActualizarFoco del agente Igualitario (robotMasterIA)
    //	public void VictimaAsignadaARobot(Object[] params){  //ASI NO FUNCIONABA		
    public void VictimaAsignadaARobot(Long tiempoReportado, String refVictima, String nombreAgenteEmisor, Integer miEvaluacion) {

        trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                "Accion VictimaAsignadaARobot  ... " + "tiempoActual->" + tiempoReportado + " ; refVictima->"
                + refVictima + " ; nombreAgenteEmisor->" + nombreAgenteEmisor + " ; miEvaluacion->" + miEvaluacion, InfoTraza.NivelTraza.debug));

        InfoAsignacionVictima infoAsigVictima = infoCasoSimul.getInfoAsignacionVictima(refVictima);
     infoAsigVictima.setEvaluacion(miEvaluacion);
     infoAsigVictima.setRobotId(nombreAgenteEmisor);
     infoAsigVictima.setTiempoAsignacion(tiempoReportado- tiempoInicialDeLaSimulacion); // tiempo reportado
     infoAsigVictima.setNrovictimastotalasignadas(infoCasoSimul.getnumeroVictimasAsignadas());
     infoAsigVictima.setNrovictimasenentorno(infoCasoSimul.getnumeroVictimasEntorno());
     infoCasoSimul.addAsignacionVictima(infoAsigVictima);
     
     
   //  if (infoCasoSimul.todasLasVictimasAsgnadas())this.informaraMiAutomata(VocabularioRosace.informacionFinSimulacion, null);
        
        String valor = victimasAsignadas.put(refVictima, refVictima);
        int numeroVictimasEnEntorno = this.numeroVictimasEntorno;

        if (valor == null) //no estaba insertado, es una nueva victima asignada
        {
            incrementarNumeroVictimasAsignadas();
        }
        //      int nroVictimasAsignadas = this.numeroVictimasAsignadas;

        //ANOTAR EN EL BUFFER DE ESTADISTICAS DE ASIGNACION
        VictimaAsignadaEstadistica victAsigEst = new VictimaAsignadaEstadistica();
        victAsigEst.setVictima(refVictima);
        victAsigEst.setRobot(nombreAgenteEmisor);
        victAsigEst.setEvaluacion(miEvaluacion);
        victAsigEst.setTiempoAsignacion(tiempoReportado);
        victAsigEst.setNrovictimastotalasignadas(numeroVictimasAsignadas);
        victAsigEst.setNrovictimasenentorno(numeroVictimasEnEntorno);

        victimasAsignadasEstadistica.add(indexvictimasAsignadasEstadistica, victAsigEst);
        indexvictimasAsignadasEstadistica++;

        //escribir la asignacion en el fichero de estadisticas EstadisticasAsignacionVictimas.xml       
        try {
            itfUsoRecursoPersistenciaEntornosSimulacion.escribeEstadisticaFicheroXMLTRealAsignacionVictimasRobots(tiempoReportado, refVictima, nombreAgenteEmisor, miEvaluacion,
                    numeroVictimasAsignadas, numeroVictimasEnEntorno);
            itfUsoRecursoPersistenciaEntornosSimulacion.guardarInfoAsignacionVictima(infoAsigVictima);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //SI se han asignado todas las victimas diferentes que hay en la simulacion ENTONCES cerramos los ficheros correspondientes
        if (numeroVictimasAsignadas == this.numeroVictimasDiferentesSimulacion) {

            try {
                //Cerrar los ficheros EstadisticasLlegadaVictimas.xml y EstadisticasAsignacionVictimas.xml
                itfUsoRecursoPersistenciaEntornosSimulacion.cerrarFicheroXMLTRealLlegadaVictimas();
                itfUsoRecursoPersistenciaEntornosSimulacion.cerrarFicheroXMLTRealAsignacionVictimasRobots();
            } catch (Exception e) {
                e.printStackTrace();
            }

            trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                    "Accion VictimaAsignadaARobotSubordinado  ... " + " FIN DE SIMULACION ....", InfoTraza.NivelTraza.debug));
            //DEPURACION IMPRIME EL BUFFER victimasLlegadaEstadistica POR LA TRAZA DEL AGENTE    		    		
            int tamanioBufferVLE = victimasLlegadaEstadistica.size();

            for (int index = 0; index < tamanioBufferVLE; index++) {

                VictimaLlegadaEstadistica VLE = new VictimaLlegadaEstadistica();
                VLE = victimasLlegadaEstadistica.get(index);

                trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                        "  victima -> " + VLE.getVictima()
                        + "; tiempollegada -> " + VLE.getTiempoActual()
                        + "; nrovictimas -> " + VLE.getNrovictimas(), InfoTraza.NivelTraza.info));
            }


            //DEPURACION IMPRIME EL BUFFER victimasAsignadasEstadistica POR LA TRAZA DEL AGENTE
            int tamanioBufferVAE = victimasAsignadasEstadistica.size();

            for (int index = 0; index < tamanioBufferVAE; index++) {

                VictimaAsignadaEstadistica VAE = new VictimaAsignadaEstadistica();
                VAE = victimasAsignadasEstadistica.get(index);

                trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                        "  victima -> " + VAE.getVictima()
                        + "; robot -> " + VAE.getRobot()
                        + "; evaluacion -> " + VAE.getEvaluacion()
                        + "; tiempoasignacion -> " + VAE.getTiempoAsignacion()
                        + "; nrovictimastotalasignadas -> " + VAE.getNrovictimastotalasignadas()
                        + "; nrovictimasenentorno -> " + VAE.getNrovictimasenentorno(),
                        InfoTraza.NivelTraza.info));
            }


            //Comunicar a todos los agentes el final de la simulacion
            //Envio de un mensaje a los agentes del equipo, con contenido FinSimulacion, para que se cree y genere el fichero 
            //EstadisticaFinalSimulacionAsignacionMisionV2.xml. Ver tarea FinalizarSimulacion.
            FinSimulacion finalSimulacion = new FinSimulacion();
            comunicator.informaraGrupoAgentes(finalSimulacion, identsAgtesEquipo);

            trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                    "identsAgtesEquipo->" + identsAgtesEquipo,
                    InfoTraza.NivelTraza.info));

            ///////////////////////////////////*****************************************************/////////////////////////////////////
            ///////////////////////////////////*** VISUALIZACION DE LAS GRAFICAS DE ESTADISTICAS ***/////////////////////////////////////
            ///////////////////////////////////*****************************************************/////////////////////////////////////

            //GRAFICA "Victim's Notification and Assignment to Team members"
            //------------------------------------------------------------------

        //    ArrayList<PuntoEstadistica> llegada = new ArrayList();
        //    ArrayList<PuntoEstadistica> asignacion = new ArrayList();
/*
            //El tiempo del elemento i-esimo de elapsed es igual el tiempo del elemento asignacion(i) - tiempo del elemento llegada(i)
            ArrayList<PuntoEstadistica> elapsed = new ArrayList();

            try {
                //1) Obtener del fichero de estadisticas EstadisticasLlegadaVictimas.xml el array de puntos
                //   a rerpresentar en la grafica "Victim's Notification and Assignment to Team members"
                llegada = itfUsoRecursoPersistenciaEntornosSimulacion.leerFicheroXMLTRealLlegadaVictimasPuntos(icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasLlegadaVictimas);


                //Estadisticas depuracion
                System.out.println("GRAFICA DE LLEGADA ................");
                System.out.println("-----------------------------------");

                for (int i = 0; i < llegada.size(); i++) {
                    double x = llegada.get(i).getX();
                    double y = llegada.get(i).getY();
                    System.out.println("Punto = " + i + " ; x->" + x + " ; y->" + y);

                    trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, "Accion VictimaAsignadaARobot  .... "
                            + "Punto = " + i + " ; x->" + x + " ; y->" + y, InfoTraza.NivelTraza.debug));

                }

                //2) Obtener del fichero de estadisticas EstadisticasAsignacionVictimas.xml el array de puntos
                //   a rerpresentar en la grafica "Victim's Notification and Assignment to Team members"
                //   OJO la implementacion actual de leerFicheroXMLTRealAsignacionVictimasRobotsPuntos va a devolver un valor para cada victima
                //       es decir, si la victima1 se asigno en el tiempo 10 y en el tiempo 12, entonces solo devuelve el 10
                asignacion = itfUsoRecursoPersistenciaEntornosSimulacion.leerFicheroXMLTRealAsignacionVictimasRobotsPuntos(icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasAsignacionVictimas);

                //Estadisticas depuracion
                System.out.println("GRAFICA DE ASIGNACION ................");
                System.out.println("--------------------------------------");
                for (int i = 0; i < asignacion.size(); i++) {
                    double x = asignacion.get(i).getX();
                    double y = asignacion.get(i).getY();
                    System.out.println("Punto = " + i + " ; x->" + x + " ; y->" + y);

                    trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, "Accion VictimaAsignadaARobot  .... "
                            + "Punto = " + i + " ; x->" + x + " ; y->" + y, InfoTraza.NivelTraza.debug));

                }

*/
                //3) Representar las dos series de puntos obtenidos previamente (llegada y asignacion) en una grafica 
                //   "Victim's Notification and Assignment to Team members"
       //     try {
                 
                /*
                 * itfUsoRecursoVisualizadorEntornosSimulacion.crearEInicializarVisorGraficaEstadisticas(
                 * "Simulacion: " + this.numeroRobotsSimulacion + " R; " +
                 * this.numeroVictimasDiferentesSimulacion + " Vic; " +
                 * this.intervaloSecuencia + " mseg ; " + " tipo simulacion->" +
                 * this.identificadorEquipo, "Victim's Notification and
                 * Assignment to Team members ", "Number of Victim's
                 * Notifications", "Time in seconds");                  *
                 * itfUsoRecursoVisualizadorEntornosSimulacion.mostrarVisorGraficaEstadisticas(
                 * );                  *
                 * itfUsoRecursoVisualizadorEntornosSimulacion.aniadirSerieAVisorGraficaEstadisticas(
                 * "Notification Time", 1, llegada);
                 *
                 * itfUsoRecursoVisualizadorEntornosSimulacion.aniadirSerieAVisorGraficaEstadisticas(
                 * "Assignment Time", 2, asignacion);
                       *
                 */

                //GRAFICA "Elapsed Time to Assign a New Victim"
                //------------------------------------------------------------------
/*
                for (int i = 0; i < llegada.size(); i++) {
                    try {

                        double elapsedtimeiEsimo = asignacion.get(i).getY() - llegada.get(i).getY();

                        PuntoEstadistica puntoEstadisticaElapsed = new PuntoEstadistica(i + 1, elapsedtimeiEsimo);

                        elapsed.add(puntoEstadisticaElapsed);

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
           
                try {
            // prueba obtencion de los puntos desde el fichero de la persistencia
                
                 ArrayList<PuntoEstadistica> llegada = new ArrayList();
                 ArrayList<PuntoEstadistica> asignacion = new ArrayList();
                 ArrayList<PuntoEstadistica> elapsed = new ArrayList();
                    itfUsoRecursoVisualizadorEntornosSimulacion.crearVisorGraficasLlegadaYasignacionVictimas(this.numeroRobotsSimulacion, this.numeroVictimasDiferentesSimulacion, this.intervaloSecuencia, this.identificadorEquipo); // parametros definicion titulos		                                                                        
                    llegada = infoCasoSimul.getSerieLlegadaPeticiones().getserieResultadosSimulacion();
                    asignacion = infoCasoSimul.getSerieAsignacion().getserieResultadosSimulacion();
                    itfUsoRecursoVisualizadorEntornosSimulacion.visualizarLlegadaYasignacionVictimas(llegada, asignacion);
                    itfUsoRecursoVisualizadorEntornosSimulacion.crearVisorGraficasTiempoAsignacionVictimas(this.numeroRobotsSimulacion, this.numeroVictimasDiferentesSimulacion, this.intervaloSecuencia, this.identificadorEquipo); // parametros definicion titulos		                                                                        
                    elapsed = infoCasoSimul.getSerieElapsed().getserieResultadosSimulacion();
                    itfUsoRecursoVisualizadorEntornosSimulacion.visualizarTiempoAsignacionVictimas(elapsed);

                    /*
                     * itfUsoRecursoVisualizadorEntornosSimulacion.crearEInicializarVisorGraficaEstadisticas(
                     * "Simulacion: " + this.numeroRobotsSimulacion + " R; " +
                     * this.numeroVictimasDiferentesSimulacion + " Vic; " +
                     * this.intervaloSecuencia + " mseg ; " + " tipo
                     * simulacion->" + this.identificadorEquipo, "Elapsed Time
                     * to Assign a New Victim", "Number of Victim's
                     * Notifications", "Time in seconds");
                     *
                     * itfUsoRecursoVisualizadorEntornosSimulacion.mostrarVisorGraficaEstadisticas(
                     * );
                     * itfUsoRecursoVisualizadorEntornosSimulacion.aniadirSerieAVisorGraficaEstadisticas(
                     * "Elapsed Time", 1, elapsed);
                     */
                    //Aqui se podria crear el fichero EstIntLlegadaYAsignacionVictims2013_04_19_20_33_09 .... (usando el Elapsed)            		
   /* 
        } catch (Exception e1) {
                    e1.printStackTrace();
                }

        //    } catch (Exception e1) {
        //        e1.printStackTrace();
        //    }


            //Llamadas para que se creen automaticamente otros ficheros de estadisticas (llegadaYAsignacion e intervalo)        	        	
            try {

                //Generar el fichero EstadisticasLlegadaYAsignacionVictimas.xml.
                //Este fichero se obtiene a partir de la informacion almacenada en los ficheros EstadisticasLlegadaVictimas.xml y EstadisticasAsignacionVictimas.xml
              // InfoSerieResultadosSimulacion infoSerieResultados = new InfoSerieResultadosSimulacion("Serie AsignacionVictimas",identificadorEquipo,
              //                    numeroRobotsSimulacion,
              //                    numeroVictimasDiferentesSimulacion,
              //                    intervaloSecuencia                   );
              //  infoSerieResultados.setserieResultadosSimulacion(infoCasoSimul.getSerieLlegada());
                InfoSerieResultadosSimulacion infoSerieLlegadaPeticiones = infoCasoSimul.getSerieLlegadaPeticiones();
                itfUsoRecursoPersistenciaEntornosSimulacion.guardarSerieResultadosSimulacion(infoSerieLlegadaPeticiones);
                itfUsoRecursoPersistenciaEntornosSimulacion.generaFicheroXMLEstadisticasLlegadaYAsignacionVictimas(identificadorEquipo,
                        numeroRobotsSimulacion,
                        numeroVictimasDiferentesSimulacion,
                        intervaloSecuencia);

                //Generar el fichero EstIntLlegadaYAsignacionVictimsFECHA.xml
                //Este fichero se obtiene a partir de la informacion almacenada en el fichero EstadisticasLlegadaYAsignacionVictimas.xml
                itfUsoRecursoPersistenciaEntornosSimulacion.generaFicheroXMLEstadisticasIntLlegadaYAsignacionVictims(identificadorEquipo,
                        numeroRobotsSimulacion,
                        numeroVictimasDiferentesSimulacion,
                        intervaloSecuencia);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    int contadorRobotsQueContestanFinsimulacion = 0;
    
 public void procesarInfoAsignacionVictima(Long tiempoAsignacion, String refVictima, String nombreAgenteEmisor, Integer miEvaluacion){
     // el robot que se ha quedado con la victima informa sobre los detalles de la asingnacion
     // este agente incorpora el contexto de asigancion de la victima
     trazas.trazar (this.nombreAgente,
                "Info Asigancion Victima  ... " + "tiempoActual->" + tiempoAsignacion + " ; refVictima->"
                + refVictima + " ; nombreAgenteEmisor->" + nombreAgenteEmisor + " ; miEvaluacion->" + miEvaluacion, InfoTraza.NivelTraza.debug);
     InfoAsignacionVictima infoAsigVictima = infoCasoSimul.getInfoAsignacionVictima(refVictima);
     infoAsigVictima.setEvaluacion(miEvaluacion);
     infoAsigVictima.setRobotId(nombreAgenteEmisor);
     infoAsigVictima.setTiempoAsignacion(tiempoAsignacion);
     infoAsigVictima.setNrovictimastotalasignadas(infoCasoSimul.getnumeroVictimasAsignadas());
     infoAsigVictima.setNrovictimasenentorno(infoCasoSimul.getnumeroVictimasEntorno());
     infoCasoSimul.addAsignacionVictima(infoAsigVictima);
     if (infoCasoSimul.todasLasVictimasAsgnadas())this.informaraMiAutomata(VocabularioRosace.informacionFinSimulacion, null);
//        String valor = victimasAsignadas.put(refVictima, refVictima);
//        int numeroVictimasEnEntorno = this.numeroVictimasEntorno;

//        if (valor == null) //no estaba insertado, es una nueva victima asignada
//        {
//            incrementarNumeroVictimasAsignaaciondas();
//        }
        //      int nroVictimasAsignadas = this.numeroVictimasAsignadas;
  
     // decirle a la persistencia que guarde la informacion 
     // itfUsoRecursoPersistenciaEntornosSimulacion. guardarInfoAsignacionVictima (infoAsignacion) ;
//        VictimaAsignadaEstadistica victAsigEst = new VictimaAsignadaEstadistica();
//        victAsigEst.setVictima(refVictima);
//        victAsigEst.setRobot(nombreAgenteEmisor);
//        victAsigEst.setEvaluacion(miEvaluacion);
//        victAsigEst.setTiempoAsignacion(tiempoActual);
//        victAsigEst.setNrovictimastotalasignadas(numeroVictimasAsignadas);
//        victAsigEst.setNrovictimasenentorno(numeroVictimasEnEntorno);
 }
    //Esta accion semantica se ejecuta cuando se envia el input "finSimulacion" en la  
    //tarea sincrona FinalizarSimulacion del agente Subordinado y el igualitario
    //Nos permite generar un fichero EstadisticaFinalSimulacionAsignacionMisionV2.xml que resume que victimas han sido asignadas a cada robot.
    public void FinSimulacion(String robot, ArrayList idsVictimasFinalesAsignadas, Double tiempoTotalCompletarMisionAtenderVictimasFinalesAsignadas) {

        trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, "Accion FinSimulacion  .... "
                + "robot->" + robot + " ; idsVictimasFinalesAsignadas->" + idsVictimasFinalesAsignadas
                + " ; tiempoTotalCompletarMisionAtenderVictimasFinalesAsignadas->" + tiempoTotalCompletarMisionAtenderVictimasFinalesAsignadas, InfoTraza.NivelTraza.debug));

        //Crear los ficheros EstadisticaFinalSimulacionAsignacionMision (.xml y .txt)            
        //String filenameXMLV1 = ConstantesRutasEstadisticas.rutaficheroXMLRepartoTareasRobotsYTiempoCompletarlasV1;
        String filenameXMLV2 = ConstantesRutasEstadisticas.rutaficheroXMLRepartoTareasRobotsYTiempoCompletarlasV2;
        //String filenameTXT = ConstantesRutasEstadisticas.rutaficheroTextoPlanoRepartoTareasRobotsYTiempoCompletarlas;

        try {
                ArrayList<InfoAsignacionVictima> infoAsignVictms = new ArrayList();
                 infoAsignVictms = itfUsoRecursoPersistenciaEntornosSimulacion.obtenerInfoAsignacionVictimas();
            //Crear el fichero EstadisticaFinalSimulacionAsignacionMisionV2.xml. 
            //El metodo crearFicherosRepartoTareasRobotsYTiempoCompletarlas controla que solo se cree la primera vez 
            itfUsoRecursoPersistenciaEntornosSimulacion.crearFicherosRepartoTareasRobotsYTiempoCompletarlas(filenameXMLV2,
                    identificadorEquipo,
                    numeroRobotsSimulacion,
                    numeroVictimasDiferentesSimulacion,
                    intervaloSecuencia);

            //Escribir en el fichero EstadisticaFinalSimulacionAsignacionMisionV2.xml
            //El metodo escribeEstadisticaFicherosRepartoTareasRobotsYTiempoCompletarlas tambien controla cuando debe cerrar el fichero 
            itfUsoRecursoPersistenciaEntornosSimulacion.escribeEstadisticaFicherosRepartoTareasRobotsYTiempoCompletarlas(robot,
                    idsVictimasFinalesAsignadas,
                    tiempoTotalCompletarMisionAtenderVictimasFinalesAsignadas);
            contadorRobotsQueContestanFinsimulacion++;
            if (identificadorEquipo.equals("robotSubordinado")) //VocabularioRosace.IdentEquipoJerarquico
            {//Simulacion de equipo de robots subordinados
                if (contadorRobotsQueContestanFinsimulacion == identsAgtesEquipo.size()) //Han contestado todos
                {
                    itfUsoRecursoPersistenciaEntornosSimulacion.cerrarFicherosRepartoTareasRobotsYTiempoCompletarlas();
                    mostrarVentanaAlertaFinSimulacion();
                }
            } else //Simulacion de equipo de robots igualitarios		    	
            {
                if (contadorRobotsQueContestanFinsimulacion == identsAgtesEquipo.size()) //Han contestado todos
                {
                    itfUsoRecursoPersistenciaEntornosSimulacion.cerrarFicherosRepartoTareasRobotsYTiempoCompletarlas();
                    mostrarVentanaAlertaFinSimulacion();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MostrarEscenarioActualSimulado() {

        trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, "Accion MostrarEscenarioActualSimulado  ....", InfoTraza.NivelTraza.debug));
        try {
            itfUsoRecursoCreacionEntornosSimulacion.MostrarEscenarioActualSimulado();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Include in this section other (private) methods used in this agent
    // #start_nodelocalMethods:localMethods <--localMethods-- DO NOT REMOVE THIS
    /**
     * El metodo clasificaError es necesario declararlo, aunque no exista una
     * accion semantica explicita para realizar el tratamiento de errores
     */
/*
    @Override
    public void clasificaError() {
        // TODO Auto-generated method stub
    }

    // ---------------------------------------------------
    // ----------- Metodos auxiliares -----------
    // ---------------------------------------------------
    private Victim createNewVictim(ReadXMLTestSequence rXMLTSeq,
            NodeList nodeLst, int numeroVictima) {
        int posicionVictimaNodoLst = numeroVictima - 1; // El nodo de la victima
        // j se encuentra en la
        // posicion j-1 en la
        // lista de nodoses 0
        Element info = rXMLTSeq.getVictimElement(nodeLst,
                posicionVictimaNodoLst);
        String valueid = rXMLTSeq.getVictimIDValue(info, "id");
        int valueseverity = rXMLTSeq.getVictimSeverity(info, "severity");
        List<Integer> victimRequirements = new ArrayList<Integer>();
        victimRequirements = rXMLTSeq.getVictimRequirements(info);
        Coordinate valueCoordinate = rXMLTSeq.getVictimCoordinate(info);
        return new Victim(valueid, valueCoordinate, valueseverity,
                victimRequirements);
    }

    private void incrementarNumeroVictimasActuales() {
        this.numeroVictimasEntorno++;
    }

    private void incrementarNumeroVictimasAsignadas() {
        this.numeroVictimasAsignadas++;
    }

    private void mostrarVentanaAlertaFinSimulacion() {

        String directorioTrabajo = System.getProperty("user.dir");  //Obtener directorio de trabajo      		

        String msg = "FIN DE LA SIMULACION !!!.\n";
        msg = msg + "Se ha completado la captura de todas las estadisticas para la simulacion actual.\n";
        msg = msg + "Los ficheros de estadisticas se encuentran en el directorio " + directorioTrabajo + "/" + ConstantesRutasEstadisticas.rutaDirectorioEstadisticas + "\n";
        msg = msg + "Los ficheros de estadisticas son los siguientes:\n";
        msg = msg + directorioTrabajo + "/" + ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasLlegadaVictimas + "\n";
        msg = msg + directorioTrabajo + "/" + ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasAsignacionVictimas + "\n";
        msg = msg + directorioTrabajo + "/" + ConstantesRutasEstadisticas.rutaficheroXMLRepartoTareasRobotsYTiempoCompletarlasV2 + "\n";
        msg = msg + directorioTrabajo + "/" + ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasLlegadaYAsignacionVictimas + "\n";
        msg = msg + directorioTrabajo + "/estadisticas/" + "EstIntLlegadaYAsignacionVictims" + "FECHA.xml" + "\n";

        JOptionPane.showMessageDialog(null, msg);
    }
    // #end_nodelocalMethods:localMethods <--localMethods-- DO NOT REMOVE THIS
/*
    private void comprobarCondicionesTerminacionSimulacion() {
    //    throw new UnsupportedOperationException("Not yet implemented");
        if (infoContxVict.getnrovictimastotalasignadas()== infoContxVict.getNrovictimasenentorno() ){
            
            this.informaraMiAutomata(VocabularioRosace.informacionFinSimulacion, null);
        }
        else if (peticionTerminacionSimulacionUsuario)this.informaraMiAutomata(VocabularioRosace.peticionTerminarSimulacion, null);
    }
*/

/*
   private void informarResultadosSimulacion() { 
        try {
            // visualizamos los resultados
         this.itfUsoRecursoVisualizadorEntornosSimulacion.visualizarLlegadaYasignacionVictimas(identsAgtesEquipo, identsAgtesEquipo);
         this.itfUsoRecursoVisualizadorEntornosSimulacion.visualizarTiempoAsignacionVictimas(identsAgtesEquipo);
            // guardamos los resultados para poder consultarlos
         
        } catch (Exception ex) {
            Logger.getLogger(AccionesSemanticasAgenteAplicacionAgteControladorSimuladorRosaceOld.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
     
    
}
* */ 
