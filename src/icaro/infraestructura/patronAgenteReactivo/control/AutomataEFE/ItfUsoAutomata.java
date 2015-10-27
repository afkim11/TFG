package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE;

import org.apache.log4j.Logger;

/**
 * Interfaz de uso de un autmata
 * @author      Felipe Polo
 * @created     3 de Diciembre de 2007
 */

public interface ItfUsoAutomata {
	/**
	 *  Dice si el automata se encuentra en un estado final o no
	 *
	 *@return    est en estado final o no
	 */
	public boolean esEstadoFinal();

        public String getEstadoControlAgenteReactivo();


	/**
	 *  Admite un input y lo procesa segul ta tabla de estados, ejecutando la
	 *  transicin correspondiente
	 *
	 *@param  input  Input a procesar
	 */
	public  void procesaInput(String input, Object[] parametros);


	/**
	 *  Imprime la tabla de estados y el estado actual del autmata
	 *
	 *@return    Cadena con la informacin
	 */
	public String toString();


	/**
	 *  Devuelve el autmata a su estado inicial
	 */
	public void volverAEstadoInicial();
	
	public void cambiaEstado(String estado);

        public void transita(String input);
	// El automata transita de acuerdo con el estado actual y con el input enviado
        // No se ejecutan acciones aunque estuvieran definidas en el automata
	/**
	 * @param logger
	 * @uml.property  name="logger"
	 */
	public void setLogger(Logger logger);
		
	/**
	 * @uml.property  name="logger"
	 * @uml.associationEnd  
	 */
	public Logger getLogger();
	
}
