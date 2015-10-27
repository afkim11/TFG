package icaro.aplicaciones.recursos.recursoDepuracionCognitivo;

import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

//Other imports used by this Resource
//#start_nodeuseItfSpecialImports:useItfSpecialImports <--useItfSpecialImports-- DO NOT REMOVE THIS
 
//#end_nodeuseItfSpecialImports:useItfSpecialImports <--useItfSpecialImports-- DO NOT REMOVE THIS


public interface ItfUsoRecursoDepuracionCognitivo extends ItfUsoRecursoSimple {
       public void mostrarInfoRD(String info) throws Exception;
       public void mostrarInfoAR(String info) throws Exception;
       public void mostrarInfoWM(String info) throws Exception;       
}
