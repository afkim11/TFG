/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;
import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

/**
 *
 * @author Francisco J Garijo
 */
public class InicializarInfoParaDecidirCR extends TareaSincrona{
    
   @Override
   public void ejecutar(Object... params) {
	   try {
             InfoEquipo miEquipo = (InfoEquipo)params[0];
             String nombreAgenteEmisor = this.getIdentAgente();
             String idVictim = (String)params[1];
             InfoParaDecidirQuienVa infoDecisionAgente;
             if (miEquipo==null)infoDecisionAgente = new InfoParaDecidirQuienVa(nombreAgenteEmisor);
             else infoDecisionAgente = new InfoParaDecidirQuienVa(nombreAgenteEmisor,miEquipo);
             infoDecisionAgente.setidElementoDecision(idVictim);
             this.getEnvioHechos().insertarHecho(infoDecisionAgente);
             // Activo un timeout para la decision. Cuando venza se decidira que hacer en funcion de la situacion del agente
             // Porque se supone que estoy esperando informaciones que no llegan. 
             
       } catch (Exception e) {
			 e.printStackTrace();
       }
   }

}
