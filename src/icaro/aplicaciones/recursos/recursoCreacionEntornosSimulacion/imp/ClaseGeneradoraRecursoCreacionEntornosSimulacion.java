package icaro.aplicaciones.recursos.recursoCreacionEntornosSimulacion.imp;

import javax.swing.JFrame;

import icaro.aplicaciones.recursos.recursoCreacionEntornosSimulacion.ItfUsoRecursoCreacionEntornosSimulacion;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.VisorEscenariosRosace;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

//This resource class needs the next imports in order to send events towards some agent
//import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;

//Other imports used by this Resource
//#start_nodespecialImports:specialImports <--specialImports-- DO NOT REMOVE THIS
 
//#end_nodespecialImports:specialImports <--specialImports-- DO NOT REMOVE THIS

public class ClaseGeneradoraRecursoCreacionEntornosSimulacion extends ImplRecursoSimple implements ItfUsoRecursoCreacionEntornosSimulacion {

    private ItfUsoRecursoTrazas trazas; //Se inicializa en el constructor con la referencia al recurso de trazas. Asi ya estara disponible en cualquier metodo.
    private String idRecurso;  //Se inicializara en el constructor con el identificador, dado a la instancia del recurso, en la descripcion de la organizacion

    //Other global variables used in this Resource
    //#start_nodeglobalVariables:globalVariables <--globalVariables-- DO NOT REMOVE THIS
 
    //#end_nodeglobalVariables:globalVariables <--globalVariables-- DO NOT REMOVE THIS

    public ClaseGeneradoraRecursoCreacionEntornosSimulacion(String idRecurso) throws Exception{
    //#start_nodeconstructorMethod:constructorMethod <--constructorMethod-- DO NOT REMOVE THIS

        super(idRecurso);
        this.idRecurso = idRecurso;
        try {
               trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance()
                                   .obtenerInterfaz(NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
        } catch (Exception e) {
            this.itfAutomata.transita("error");
            System.out.println("No se pudo usar el recurso de trazas");
        }
		
        //A continuacion se sigue completando el constructor si es necesario .... 
        //      .................................
		
        //Ultima llamada del constructor seria la siguiente
        trazas.aceptaNuevaTraza(new InfoTraza(this.idRecurso, "El constructor de la clase generadora del recurso " + this.id + " ha completado su ejecucion ....",	InfoTraza.NivelTraza.debug));	
                    
    //#end_nodeconstructorMethod:constructorMethod <--constructorMethod-- DO NOT REMOVE THIS    
    }	

    
    private VisorEscenariosRosace visorSc = null;
    
    //Methods that implement CreacionEntornosSimulacion resource use interface
	public void MostrarEscenarioActualSimulado(String identEscenario) throws Exception{
		
		if (visorSc == null)				
			visorSc = new VisorEscenariosRosace(identEscenario);
		
		visorSc.setVisible(true);
		visorSc.setExtendedState(JFrame.NORMAL);		
	}

    //End methods that implement CreacionEntornosSimulacion resource use interface


//#start_nodeterminaMethod:terminaMethod <--terminaMethod-- DO NOT REMOVE THIS        

    @Override
    public void termina() {
        trazas.aceptaNuevaTraza(new InfoTraza(this.idRecurso, "Terminando recurso" + this.id + " ....",	InfoTraza.NivelTraza.debug));
 	    
        //Si es un recurso de visualizacion es necesaria una llamar a dispose de la ventana de visualizacion. Algo parecido a lo siguiente	
        //this.jvariableLocalReferenciaVisualizador.dispose(); //Destruye los componentes utilizados por este JFrame y devuelve la memoria utilizada al Sistema Operativo 	 
		
        super.termina();
    }	
                        
//#end_nodeterminaMethod:terminaMethod <--terminaMethod-- DO NOT REMOVE THIS

    //Include in this section private methods used in this resource
//#start_nodelocalMethods:localMethods <--localMethods-- DO NOT REMOVE THIS    
 
//#end_nodelocalMethods:localMethods <--localMethods-- DO NOT REMOVE THIS
    
    	
    //Fragmento de codigo para mostrar por la ventana de trazas de este recurso un mensaje	
    //trazas.aceptaNuevaTraza(new InfoTraza(this.idRecurso,"Mensaje mostrado en la ventana de trazas del recurso ....",InfoTraza.NivelTraza.debug));
    
}
