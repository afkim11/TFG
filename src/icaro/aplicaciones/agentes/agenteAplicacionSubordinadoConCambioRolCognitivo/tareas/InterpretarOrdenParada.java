/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.*;
import icaro.aplicaciones.Rosace.objetivosComunes.PararRobot;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.InfoCompMovimiento;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

/**
 *
 * @author FGarijo
 */
public class InterpretarOrdenParada extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {
        try {
            OrdenParada ordenParar = (OrdenParada) params[0];
            InfoCompMovimiento infoCpteMov = (InfoCompMovimiento) params[1];
            MisObjetivos misObjs = (MisObjetivos) params[2];
            Focus foco = (Focus) params[3];
//             String identTarea = this.getIdentTarea();
            String nombreAgenteEmisor = this.getIdentAgente();
// se deberia verificar que el ordenante tiene autoridad para dar la orden
// suponemos que la tiene
//            this.getEnvioHechos().eliminarHechoWithoutFireRules(ccOrdenAyudarVictima);
            // Dos casos de interpretacion : cuando no existe expectativa y cuando existe
            if (verificarAutoridad(ordenParar.getIdentEmisor()) ) { // se procede a ejecutar la orden de parada
            // generar objetivo PararRobot y focalizarlo
                PararRobot pararObj = new PararRobot();
                pararObj.setSolving();
                foco.setFoco(pararObj);
                
                ((ItfUsoMovimientoCtrl)infoCpteMov.getitfAccesoComponente()).parar();
                this.itfProcObjetivos.eliminarHecho(ordenParar);
                this.itfProcObjetivos.insertarHecho(pararObj);
                this.itfProcObjetivos.actualizarHecho(foco);
                trazas.aceptaNuevaTrazaEjecReglas(nombreAgenteEmisor, "Se ejecuta la tarea " + identTarea
                            + " Se crea el objetivo  :  " + pararObj +"  y se focaliza " + foco);
            }     
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean verificarAutoridad ( String identEntidad){
        return true;
    }
}
