package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.PuntoEstadistica;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.ItfUsoRecursoPersistenciaEntornosSimulacion;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.EscenarioSimulacionRobtsVictms;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import java.util.ArrayList;

//Other imports used by this Resource
//#start_nodeuseItfSpecialImports:useItfSpecialImports <--useItfSpecialImports-- DO NOT REMOVE THIS
 
//#end_nodeuseItfSpecialImports:useItfSpecialImports <--useItfSpecialImports-- DO NOT REMOVE THIS


public interface ItfUsoRecursoVisualizadorEntornosSimulacion extends ItfUsoRecursoSimple {	
public void crearEInicializarVisorGraficaEstadisticas(String tituloVenanaVisor,
			                              String tituloLocalGrafico,
			                              String tituloEjeX,
			                              String tituloEjeY) throws Exception;	
// public void mostrarVisorGraficaEstadisticas(VisualizacionJfreechart visualizadorJFchart, int coordX, int coordY) throws Exception;
 public void mostrarVisorGraficaEstadisticas( )throws Exception;
 	
public void aniadirSerieAVisorGraficaEstadisticas( String tituloSerie, int indexSerie, ArrayList<PuntoEstadistica> puntosEstadistica) throws Exception;
	
 public void mostrarEscenarioSimulador(String rutaEscenario)throws Exception;
 public void mostrarVentanaControlSimulador()throws Exception;
 public void mostrarEscenario()throws Exception;
 public void crearVisorGraficasLlegadaYasignacionVictimas (int numeroRobotsSimulacion,int numeroVictimasDiferentesSimulacion,int intervaloSecuencia,String identificadorEquipo)throws Exception;
 public void visualizarLlegadaYasignacionVictimas(ArrayList<PuntoEstadistica> llegada,ArrayList<PuntoEstadistica> asignacion)throws Exception;
 public void crearVisorGraficasTiempoAsignacionVictimas (int numeroRobotsSimulacion,int numeroVictimasDiferentesSimulacion,int intervaloSecuencia,String identificadorEquipo)throws Exception;
 public void visualizarTiempoAsignacionVictimas (ArrayList<PuntoEstadistica> elapsed)throws Exception;
 public void mostrarResultadosFinSimulacion()throws Exception;
 public void mostrarPosicionRobot(String identRobot, Coordinate coordRobot)throws Exception;
 public void mostrarPosicionVictima(String identVict, Coordinate coordVict)throws Exception;
 public void mostrarVictimaRescatada(String identVictima)throws Exception;
 public  void inicializarDestinoRobot(String idRobot,Coordinate coordInicial,Coordinate coordDestino, double velocidadInicial)throws Exception;;
 public void mostrarMovimientoAdestino(String idRobot,String identDest,Coordinate coordDestino, double velocidadCrucero) throws Exception;
 public void mostrarIdentsEquipoRobots(ArrayList identList)throws Exception;
 public void mostrarIdentsVictims(ArrayList identList)throws Exception;
 public void quitarVictimaRescatada(String refVictima)throws Exception;
 public void mostrarVictimaMuerta(String refVictima)throws Exception;
 public void setItfUsoPersistenciaSimulador(ItfUsoRecursoPersistenciaEntornosSimulacion itfUsoRecursoPersistenciaEntornosSimulacion) throws Exception;
 public void updateEscenario(EscenarioSimulacionRobtsVictms escenarioNuevo) throws Exception;
 public void comprobarVictimasArea(Coordinate coor, int perimetroDeVision) throws Exception;
}