/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.*;
import icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.informacion.ExptComunicacionConJefe;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author FGarijo
 */
public class InterpretarPeticionDelCC extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {
        try {
            OrdenCentroControl ccOrdenAyudarVictima = (OrdenCentroControl) params[0];
            ExptComunicacionConJefe expComJefe = (ExptComunicacionConJefe) params[1];
            InfoEquipo miEquipo = (InfoEquipo) params[2];
            Victim victim = (Victim) ccOrdenAyudarVictima.getJustificacion();
//             String identTarea = this.getIdentTarea();
            String nombreAgenteEmisor = this.getIdentAgente();
            String idVictim = victim.getName();
            this.getEnvioHechos().eliminarHechoWithoutFireRules(ccOrdenAyudarVictima);
            // Dos casos de interpretacion : cuando no existe expectativa y cuando existe
            if (expComJefe == null) {
                String jefeEquipoId = miEquipo.getidentAgenteJefeEquipo();
                if (jefeEquipoId != null) {
                    ExptComunicacionConJefe exptComJefe = new ExptComunicacionConJefe(nombreAgenteEmisor, miEquipo, jefeEquipoId);
                    this.getEnvioHechos().insertarHechoWithoutFireRules(exptComJefe);
                    this.generarInformeTemporizadoFromConfigProperty(VocabularioRosace.IdentTareaTimeOutReaccionJefeAmsgeCC, null, nombreAgenteEmisor, null);
                    trazas.aceptaNuevaTrazaEjecReglas(nombreAgenteEmisor, "Se ejecuta la tarea " + identTarea
                            + " Se crea la   expectativa :  " + exptComJefe);
                    System.out.println("\n" + nombreAgenteEmisor + "Se ejecuta la tarea " + this.getIdentTarea() + " Se crea la   expectativa:  " + exptComJefe + "\n\n");
                } else {
                    trazas.aceptaNuevaTrazaEjecReglas("\n" + nombreAgenteEmisor, "Se ejecuta la tarea " + this.identTarea
                            + " El identificador del jefe debe estar definido:  ");
                }
            } else {
                trazas.aceptaNuevaTrazaEjecReglas("\n" + nombreAgenteEmisor, "Se ejecuta la tarea " + this.getIdentTarea()
                        + " Pero NO Se crea ninguna   expectativa:  ");
                System.out.println("\n" + nombreAgenteEmisor + "Se ejecuta la tarea " + this.getIdentTarea() + " Pero NO Se crea ninguns   expectativa:  " + "\n\n");
            }
            this.getEnvioHechos().insertarHecho(victim);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
