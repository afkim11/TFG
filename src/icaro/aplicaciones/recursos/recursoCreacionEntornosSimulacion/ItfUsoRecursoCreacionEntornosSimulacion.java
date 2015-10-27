package icaro.aplicaciones.recursos.recursoCreacionEntornosSimulacion;

import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

//Other imports used by this Resource
//#start_nodeuseItfSpecialImports:useItfSpecialImports <--useItfSpecialImports-- DO NOT REMOVE THIS
 
//#end_nodeuseItfSpecialImports:useItfSpecialImports <--useItfSpecialImports-- DO NOT REMOVE THIS


public interface ItfUsoRecursoCreacionEntornosSimulacion extends ItfUsoRecursoSimple {

	public void MostrarEscenarioActualSimulado(String identEscenario) throws Exception;
       
}
