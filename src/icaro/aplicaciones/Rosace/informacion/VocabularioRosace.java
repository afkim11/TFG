/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.informacion;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;

/**
 *
 * @author FGarijo
 */
public class VocabularioRosace extends NombresPredefinidos{
	
	public static final String JerarquicoAsignadorRescatando = "AsignadorRescatandoAVictimas";
	
    public static final String NombrePropiedadGlobalIdentEquipo= "identificadorEquipo";
    public static final String IdentMisionEquipo = "mision1";
    public static final String IdentEquipoIgualitario= "robotMasterIA";
    public static final String IdentEquipoJerarquico= "Jerarquico";  ///ES LO QUE ESTABA ANTES
//    public static final String IdentEquipoJerarquico= "robotSubordinado";
    public static final String IdentEquipoJerarquicoConCambioRol= "jerarquicoConCR";
    public static final String IdentAgteDistribuidorTareas= IdentEquipoJerarquico+"agenteAsignador";
    public static final String IdentRolAgteDistribuidorTareas= "agenteAsignador";
    public static final String IdentRolAgtesSubordinados= "robotSubordinado";
    public static final String IdentRolAgtesSubordinadosConCR= "robotSubordinadoConCR";
    public static final String IdentRolAgtesIgualitarios = "robotIgualitario";
    public static final String DescrExpectComunicacionConJefe="miComunicacionConJefe";
    public static final String MsgPropuesta_Oferta_Para_Ir= "SoyElMejorSituadoParaRealizarElObjetivo";
    public static final String MsgPropuesta_Decision_Ir = "YoVoy";
    public static final String MsgPropuesta_Para_Q_vayaOtro = "CreoQueDebesIrTu";
    public static final String MsgPropuesta_Para_Q_vayaYo = "CreoQueDebesIrTu";
    public static final String MsgPropuesta_Para_Aceptar_Objetivo = "HazteCargoDeEstaTarea";
    public static final String MsgJustificacion_Propuesta_Para_Aceptar_Objetivo = "Eres el mejor situado";
    public static final String MsgJustificPetionEstatusComunConJefe= "noReciboPeticiones";
    public static final String MsgPropuesta_CambioRolaIgualitario = "cambioRolaIgualitario";
    public static final String MsgPropuesta_Para_Desempatar = "MiEvaluacionParaDesempatar";
    public static final String MsgContenidoPropuestaNoValida = "MensajePropuestaNoValido";
    public static final String MsgNoSePuedeAceptarPropuesta = "NoPuedoAceptarLaPropuesta";
    public static final String MsgAceptacionPropuesta = "PropuestaAceptadada";
    public static final String MsgAceptacionPropuestaNoValida ="LaPropuestaConfirmarNoEsValida";
    public static final String CausaPropuestaNoValida = "LaPropuestaNoEsValidaNoTengoLaMejorEvaluacion";
    static public  final String MsgDecision_De_Asumir_Objetivo_Ir_Yo = "YoVoy";
    static public  final String MsgDecision_De_Rechazar_Objetivo = "YoNoVoy";
    static public  final String MsgDecision_De_Continuar_Objetivo = "SigoConObjetivoOriginal";
    static public  final String ResEjTaskMiEvalucionEnviadaAlEquipo = "EvaluacionEnviadaAtodos";
    static public  final String ResEjTaskMiPropuestaParaIrYoEnviadaAlEquipo = "PropuestaParaIrYoEnviadaAtodos";
    static public  final String ResEjTaskMiPropuestaParaDesempatarEnviada = "PropuestaParaDesempatarEnviadaAtodos";
    static public  final String ResEjTaskMiEvaluacionEnviadaAlAgtesQLaPide = "EvaluacionEnviadaAlAgente : ";
    static public  final String MsgPeticionEnvioEvaluaciones = "EnviarEvaluaciones";
    static public  final String MsgPeticionEnvioEstatusComunicacionConJefe = "EnviarEstatusComunicacionConJefe";
    static public  final String ResEjTaskPeticionEvaluacionesQueFaltanRealizada = "PeticionDeEvaluacionesQueFaltanRealizada";
    static public  final String ResEjTaskProcesadoEvaluaciones_MefaltanEvaluaciones = "DebenllegarMasEvaluaciones";
    static public  final String ResEjTaskProcesadoEvaluaciones_NoSoyElMejor = "NoSoyElMejor";
    static public  final String ResEjTaskProcesadoEvaluaciones_SoyElMejor = "SoyElMejor";
    static public  final String ResEjTaskProcesadoEvaluaciones_HayEmpates = "HayEmpates";
    static public  final String ResEjTaskConsensoParaIrSinSerElMejor = "NoSoyElMejor Pero TengoAcuerdoDeTodosParaIrYo";
    static public  final String ResEjTaskConsensoParaIrYo = "TengoAcuerdoDeTodosParaIrYo";
    static public  final String ResEjTaskDebenLlegarMasConfirmacionesParaIrYo="DebenLlegarMasRespuestasDeAcuerdoParaIrYo";
    static public  final String MsgTimeoutRecibirPropuestaDesempate = "ExpiroElTimeoutPropuestaDesempate";
    static public  final String MsgTimeoutEsquivarObstaculo = "EsquivarObstaculo";
    static public  final String MsgTimeoutRecibirRespPropuestasIrYo = "ExpiroElTimeoutRespuestaPropuestasParaIrYo";
    static public  final String MsgTimeoutRecibirEvaluaciones = "ExpiradoTiempoEsperaEvaluaciones";
    static public  final String MsgTimeoutComunicacionConJefe = "ExpiroTimeoutReaccionJefe";
    static public  final String MsgTimeoutPropuestaConEvalAJefe = "ExpiroTimeoutReaccionJefeAmiPropuesta";
    static public  final String MsgTimeoutGeneralEsperaInformacion = "ExpiroTimeoutEsperaInformacion";
    static public  final String MsgPeticionTerminacionGestor ="peticionTerminacion";
    static public  final String MsgDecisionIrYoEnvidaTodos ="InformadoGrupo(YoVoy)";
    static public  final String MsgOrdenCCAyudarVictima ="AyudarVictima";
    static public  final String MsgeLlegadaDestino ="estoyEnDestino";
    static public  final String MsgeOrdenParar ="Para";
    static public  final String MsgeRobotParado ="RobotParado";
    static public  final String IdentIteracionProcesoInformesCR ="procesamientoInformesCR";
    static public  final String IdentTareaTimeOutRecibirEvaluaciones1 ="TimeOutRecibirEvaluaciones1";
    static public  final String IdentTareaTimeOutRecibirEvaluaciones2 ="TimeOutRecibirEvaluaciones2";
    static public  final String IdentTareaTimeOutRecibirConfirmacionesRealizacionObjetivo1 ="TimeOutRecibirConfirmacionesRealizacionObjetivo1";
    static public  final String IdentTareaTimeOutRecibirConfirmacionesRealizacionObjetivo2 ="TimeOutRecibirConfirmacionesRealizacionObjetivo2";
    static public  final String IdentTareaTimeOutRecibirPropuestasDesempate ="TimeOutRecibirPropuestasDesempate";
    static public  final String IdentTareaTimeOutParaTomarDecision ="TimeOutTomarDecision";
    static public  final String IdentTareaTimeOutRecibirEstatusComunicacionConJefe ="TimeOutRecibirEstatusComunicacionConJefe";
    static public  final String IdentTareaTimeOutRecibirRespuestasEquipo ="TimeOutRecibirRespuestasEquipo";
    static public  final String IdentTareaTimeOutContactarMiembrosEquipo ="TimeOutContactarMiembrosEquipo";
    static public  final String IdentTareaTimeOutEsquivarObstaculo ="TimeOutEsquivarObstaculo";    
    static public  final String IdentTareaTimeOutReaccionJefeAmsgeCC ="TimeOutReaccionJefeAmsgeCC";
    static public  final String IdentTareaTimeOutRespuestaJefe = "TimeOutRespuestaJefe";
    static public  final String PrefijoMsgTimeout = "Expiro timeout";
    static public  final String PrefijoPropiedadTareaTimeout ="timeMsTarea:";
    
    static public  final String tipoAplicacionIgualitario = "igualitario";
    static public  final String tipoAplicacionJerarquico  = "jerarquico";
    
    //identificadores de propiedades globales que hay en fichero de descripcion de la organizacion asociadas a aplicaciones rosace
    static public  final String identificadorEquipo = "identificadorEquipo"; 
    
    static public  final String rutaFicheroVictimasTest  = "rutaFicheroVictimasTest";
    static public  final String rutaFicheroRobotsTest    = "rutaFicheroRobotsTest";
	static public  final String rutaFicheroObstaculosTest    = "rutaFicheroObstaculosTest";
    static public  final String rutaPruebaFicheroVictimasTest  = "src/utils/Escenario_0IP_V001_6Victims.xml" ;
    static public  final String rutaPruebaFicheroRobotsTest    = "src/utils/Escenario_Jerarquico_001_4Robots.xml";
    static public  final String intervaloMilisegundosEnvioCCMensajes = "intervaloMilisegundosEnvioMensajesDesdeCC"; 
    static public  final String timeTimeoutPorDefecto = "timeTimeoutPorDefecto";     
    static public  final String timeTimeoutMilisegundosRecibirEvaluaciones = "timeTimeoutMilisegundosRecibirEvaluaciones";     
    static public  final String timeTimeoutMilisegundosRecibirPropuestaDesempate = "timeTimeoutMilisegundosRecibirPropuestaDesempate";
    static public  final String timeTimeoutMilisegundosRecibirRespPropuestasIrYo = "timeTimeoutMilisegundosRecibirRespPropuestasIrYo";
    // tiempo de espera antes de que inicie la verificacion de la comunicacion con el jefe en un equipo jerarquico con cambio de rol
    static public  final String timeTimeoutMilisEsperaComunicConJefe = "timeTimeoutMilisEsperaComunicConJefe"; 
    
    //Nuevas variables para reorganizar el simulador (JM)
    static public  final String IdentAgteControladorSimulador = "AgenteControladorSimuladorRosaceReactivo1"; 
    static public  final String IdentRecursoVisualizadorEstadisticas = "RecursoVisualizadorEstadisticas1";
    static public  final String IdentRecursoCreacionEntornosSimulacion = "RecursoCreacionEntornosSimulacion1";
    static public  final String IdentRecursoVisualizadorEntornosSimulacion = "RecursoVisualizadorEntornosSimulacion1";
    static public  final String IdentPersistenciaEntornosSimulacion = "RecursoPersistenciaEntornosSimulacion1";
    static public  final String IdentDirectorioPersistenciaSimulacion = "PersistenciaResultadosSimulacion";
    static public  final String IdentDirectorioPersistenciaEscenarios = "PersistenciaEscenariosSimulacion";
    // Valores de los inputs del agente controlador. Son necesarios para que el visualizador envie notificaciones y para definir
    // el automata
    static public  final String peticionSimulacionVictima = "peticionSimulacionVictima";
    static public  final String peticionPararSimulacion = "peticionPararSimulacion";
    static public  final String peticionSimulacionSecuenciaVictimas = "sendSequenceOfSimulatedVictimsToRobotTeam";
    static public  final String peticionMostrarEscenarioActualSimulado = "mostrarEscenarioActualSimulado";
    static public  final String peticionActualizarEscenario = "actualizarEscenarioActual";
    static public  final String peticionTerminarSimulacion = "TerminarSimulacion";
    static public  final String peticionTerminarSimulacionUsuario = "peticionTerminarSimulacion";
    static public  final String peticionPararSimulacionUsuario ="peticionPararSimulacionUsuario" ;
    static public  final String informacionVictimaAsignadaARobot = "victimaAsignadaARobot";
    static public  final String informacionFinSimulacion = "finSimulacion";
    static public  final String NombreFicheroSerieInfoAsignacionVictimas = "serieInfoAsignacionVictimas";
    static public  final String NombreFicheroSeriePeticionVictimas = "seriePeticionVictimas";
    static public  final String NombreFicheroSerieLlegadaYasignacion = "serieLlegadaYasignacionVictimas";
    static public  final String NombreFicheroSerieAsignacion = "serieAsignacionVictimas";
	public static  final String MsgRobotBloqueadoObstaculo = "robotBloqueadoPorObstaculo";
	static public final String MsgEsquivaObstaculo = "He esquivado el obstaculo";
	public static final String MsgRomperRobot = "elRobotSeVaARomper";
	static public  enum PeticionAgteControlSimul{TerminarSimulacion,mostrarEscenarioActualSimulado,pararRobot,sendSequenceOfSimulatedVictimsToRobotTeam,enviarIdentsEquipo}
	static public  final String rutaPersistenciaEscenarios    = "src/PersistenciaEscenariosSimulacion";
	public static final String CambiarUbicacion = "La ubicacion de los robots ha sido modificada";
}
