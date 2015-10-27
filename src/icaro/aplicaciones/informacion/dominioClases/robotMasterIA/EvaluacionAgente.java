/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.informacion.dominioClases.robotMasterIA;

import java.io.Serializable;

/**
 *
 * @author Francisco J Garijo
 */
public class EvaluacionAgente implements Serializable{
    public String identAgente;
    public Integer valorEvaluacion;

    public EvaluacionAgente(String nombreAgenteEmisor, Integer evaluacion) {
        identAgente= nombreAgenteEmisor;
    valorEvaluacion =evaluacion;

 }
 public Integer   getEvaluacion(){
      return valorEvaluacion;
 }

 public String   getIdentAgente(){
      return identAgente;
 }
 }