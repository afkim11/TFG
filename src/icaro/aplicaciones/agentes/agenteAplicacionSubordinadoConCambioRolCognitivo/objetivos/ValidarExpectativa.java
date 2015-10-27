/*
 * DarAcceso.java
 *
 * Creado 23 de abril de 2007, 12:49
 *
 * Telefonica I+D Copyright 2006-2007
 */

package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
/**
 *
 * @author Francisco J Garijo
 */
public class ValidarExpectativa extends Objetivo {
	
	public String objId;
	public String  expectId;  // identificador de la victima que se pretende salvar
/** Crea una nueva instancia de AyudarNuevoHerido */
     public ValidarExpectativa() {
        super.setgoalId("ValidarExpectativa");
    }
     
     public ValidarExpectativa(String expectativaId) {
         super.setgoalId("AyudarVictima");
         this.expectId = expectativaId;
         super.setobjectReferenceId(expectativaId);
     }
     public void setexpectId(String victmId){
         this.expectId= victmId;
         super.setobjectReferenceId(victmId);
     }
     public String getexpectId(){
         return expectId;
     }
     
//     public String getID(){
//    	 return super.getID();
//    	 return this.id;
//     }
     
//     public void setID(String id){
//    	 this.id = id;
//     }
     
     
}