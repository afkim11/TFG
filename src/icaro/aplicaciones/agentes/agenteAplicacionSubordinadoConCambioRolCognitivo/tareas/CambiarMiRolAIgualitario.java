/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.InfoEstatusComunicacionConOtroAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.informacion.ExptComunicacionConJefe;
import icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.informacion.InfoCambioRolAgente;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.Collection;

/**
 *
 * @author FGarijo
 */
public class CambiarMiRolAIgualitario extends TareaSincrona{
    private String nombreAgenteEmisor;

@Override
public void ejecutar(Object... params) {
     try {
          nombreAgenteEmisor = this.getIdentAgente();
          String rutaReglaIgualitario = "/icaro/aplicaciones/agentes/agenteAplicacionSubordinadoConCambioRolCognitivo/procesoResolucionObjetivos/reglasIgualitarioConCambioRol.drl";
             InfoCambioRolAgente mInfoCambioRol = (InfoCambioRolAgente) params[0];
             
          // Copio los objetos de la memoria antes de cambiar el comportamiento
          Collection<Object> copiaDeMiMemoria = this.getEnvioHechos().copiarObjetosDeMiMemoria();
            if ( this.getEnvioHechos().cambiarComportamiento(rutaReglaIgualitario) ){
            // Comprobar que el comportamiento ha cambiado y asertarlo en el motor
                this.getItfConfigMotorDeReglas().setDepuracionActivationRulesDebugging(true);
                this.getItfConfigMotorDeReglas().setfactHandlesMonitoring_afterActivationFired_DEBUGGING(true);
                  trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se cambia el rol del agente jerarquico  por gualitario :  "+ rutaReglaIgualitario , InfoTraza.NivelTraza.debug));
            // copio los objetos de la memoria antigua a la nueva 
                  this.getEnvioHechos().insertarObjetosEnMiMemoria(copiaDeMiMemoria);
            // actualizo  mi robot Status e InfoEquipo con el  nuevo rol y lo meto en la memoria 
                  mInfoCambioRol.setidentRolActualAgte(VocabularioRosace.IdentRolAgtesIgualitarios);
                  this.getEnvioHechos().actualizarHecho(mInfoCambioRol);
            //
            }   
            }         
            catch(Exception e) {
                  e.printStackTrace();
            }
    }
}
