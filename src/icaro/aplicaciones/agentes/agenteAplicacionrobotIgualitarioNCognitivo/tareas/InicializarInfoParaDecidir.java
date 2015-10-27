/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;
import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

/**
 *
 * @author Francisco J Garijo
 */
public class InicializarInfoParaDecidir extends TareaSincrona{
    
   @Override
   public void ejecutar(Object... params) {
	   try {
             InfoEquipo miEquipo = (InfoEquipo)params[0];
             String idVictim = (String)params[1];
             InfoParaDecidirQuienVa infoDecisionAgente;
             if (miEquipo==null)infoDecisionAgente = new InfoParaDecidirQuienVa(this.identAgente);
             else infoDecisionAgente = new InfoParaDecidirQuienVa(identAgente,miEquipo);
             infoDecisionAgente.setidElementoDecision(idVictim);
             this.getEnvioHechos().insertarHecho(infoDecisionAgente);
             // Activo un timeout para la decision. Cuando venza se decidira que hacer en funcion de la situacion del agente
             // Porque se supone que estoy esperando informaciones que no llegan. 
             
       } catch (Exception e) {
			 e.printStackTrace();
       }
   }

}
