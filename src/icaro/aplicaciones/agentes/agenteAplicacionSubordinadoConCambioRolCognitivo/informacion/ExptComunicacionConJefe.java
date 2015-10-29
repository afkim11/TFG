/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.informacion;

import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.Rosace.informacion.InfoEstatusComunicacionConOtroAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Expectativa;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.ArrayList;

/**
 *
 * @author FGarijo
 */
public class ExptComunicacionConJefe extends Expectativa{
    private InfoEquipo miEquipo;
    private ArrayList<String> agtesEquipoIds;
    private ArrayList<Boolean> respuestasAgtsEquipo;
    private int respuestasEsperadas ;
    private int respuestasRecibidas = 0;
    private int numRespuestasSinConexion = 0;
    private String identAgteRolJefe ;
    private boolean miComunicacionConJefe = true;
    private boolean restEquipoTieneComunicacionConJefe = true;
    public ExptComunicacionConJefe (String agentPropietarioId, InfoEquipo equipo, String idJefe ){
   //     super.setestatusSatisfaccion (false);
        super.setidAgtePropietario (agentPropietarioId);
        miEquipo = equipo;
        this.setDescripcion(VocabularioRosace.DescrExpectComunicacionConJefe);
        String miRol= miEquipo.getTeamMemberRol(agentPropietarioId);
        identAgteRolJefe = idJefe ;
        agtesEquipoIds = miEquipo.getTeamMemberIDsWithThisRol(miRol); // los agtes que tiene el mismo rol 
        respuestasAgtsEquipo = new ArrayList();
        for (int i = 0; i < agtesEquipoIds.size(); i++) {
                respuestasAgtsEquipo.add(i, Boolean.FALSE);
                }
            respuestasEsperadas = agtesEquipoIds.size() ;// quitamos al propio agente ;
    }
   
    /* Funcion que comprueba el estado de la conexion del robot IDENTAGTEINFORMANTE con el IDENTOTROAGENTE (que tiene que ser el jefe)
      y si se ha recibido se actualiza las respuestas y si no se aumenta las respuestas sin conexion */
    public void procesarInfoEstatusComunicacion(InfoEstatusComunicacionConOtroAgente comunicEstatus){
        // Se guarda la informacion recogida del estatus y se verifica si  todas las respuestas coinciden en la falta de comunicacion
        String identAgteInformante = comunicEstatus.getidentAgteInformante();
        String identOtroAgte = comunicEstatus.getidentidentOtroAgente();
        if (comunicEstatus.getidentidentOtroAgente().equals(identAgteRolJefe) ){
            for (int i = 0; i < agtesEquipoIds.size(); i++) {
                if (agtesEquipoIds.get(i).equals(identAgteInformante))  
                        if (!respuestasAgtsEquipo.get(i)){
                            respuestasRecibidas ++;
                            respuestasAgtsEquipo.set(i,true);
                        if (!comunicEstatus.gethayComunicAgteInformanteConOtroAgte()) numRespuestasSinConexion ++;
                  }
             } 
         }
    }
   
    @Override
    /*Funcion que sirve para comprobar si el equipo tiene conexion con el jefe, para esto llama a procesarInfoEstatusComunicacion y comprueba
      si las respuestas sin conexion son iguales a las respuestas esperadas, es decir, ningun subordinado se comunico con el jefe  */
    public void validarExpectativa(Object... params){
        // la expectativa NO se satisface si todos los agentes del equipo confirman la falta de conexion con el jefe
        try {
            InfoEstatusComunicacionConOtroAgente comEstatus = (InfoEstatusComunicacionConOtroAgente)params[0];
            procesarInfoEstatusComunicacion(comEstatus);
            if (numRespuestasSinConexion == respuestasEsperadas){
            setestatusSatisfaccion(false);
            restEquipoTieneComunicacionConJefe = false;
            
            }
        } catch (Exception e) {
             e.printStackTrace();
             ItfUsoRecursoTrazas trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
             trazas.trazar(this.getidAgtePropietario(), "No se pudo validar la expectativa : "+ this.getClass().getSimpleName() 
                     + "Verificar el valor de los parametros  utilizados para la verificacion",InfoTraza.NivelTraza.error);
           }       
        // todos confirma que no hay conexion
       
    }
    //Actualiza la variable booleana que indica si este subordinado se puede comunicar con el jefe
    public void setmiComunicacionConJefe(boolean hayComunicacion){
        miComunicacionConJefe = hayComunicacion;
    }
    
    //Devuelve el estado de la posible comunicacion con el jefe
    public boolean getmiComunicacionConJefe(){
        return miComunicacionConJefe ;
    }
    
    //Actualiza la variable booleana que indica si el resto de mi equipo tiene comunicacion con el jefe
    public void setrestEquipoTieneComunicacionConJefe(boolean hayComunicacion){
        restEquipoTieneComunicacionConJefe = hayComunicacion;
    }
    
    //Devuelve el estado de la posible comunicacion del resto del equipo con el jefe
    public boolean getrestEquipoTieneComunicacionConJefe(){
        return restEquipoTieneComunicacionConJefe ;
    }
    
    //Actualiza el String de identificador del jefe
    public void setidentAgteRolJefe(String  idJefe){
        identAgteRolJefe = idJefe;
    }
    
    //Devuelve el identificador del jefe
    public String getidentAgteRolJefe(){
        return identAgteRolJefe ;
    }
}
