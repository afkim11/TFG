package icaro.aplicaciones.recursos.recursoDepuracionCognitivo.imp;

import javax.swing.JFrame;

import icaro.aplicaciones.recursos.recursoDepuracionCognitivo.ItfUsoRecursoDepuracionCognitivo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

//This resource class needs the next imports in order to send events towards some agent
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;

//Other imports used by this Resource
//#start_nodespecialImports:specialImports <--specialImports-- DO NOT REMOVE THIS
import java.awt.Color;
import java.awt.Font; 
//#end_nodespecialImports:specialImports <--specialImports-- DO NOT REMOVE THIS

public class ClaseGeneradoraRecursoDepuracionCognitivo extends ImplRecursoSimple implements ItfUsoRecursoDepuracionCognitivo {

    private ItfUsoRecursoTrazas trazas; //Se inicializa en el constructor con la referencia al recurso de trazas. Asi ya estara disponible en cualquier metodo.
    private String idRecurso;  //Se inicializara en el constructor con el identificador, dado a la instancia del recurso, en la descripcion de la organizacion

    //Other global variables used in this Resource
    //#start_nodeglobalVariables:globalVariables <--globalVariables-- DO NOT REMOVE THIS
    private GUIDepuracionCognitivo ventanaRulesDebugerGUI;
    private GUIDepuracionCognitivo ventanaActivationRulesGUI;
    private GUIDepuracionCognitivo ventanaWorkingMemoryGUI;
    
    //#end_nodeglobalVariables:globalVariables <--globalVariables-- DO NOT REMOVE THIS

    
    //El string finaliza en un n�mero.
    //Este m�todo devuelve la posici�n en el que empieza el numero.
    private int getNumberStartIndex(String s){
    	
    	int index=0;
    	
    	for (int x=s.length()-1;x>=0;x--){
    		char ch = s.charAt(x);
    		String sch = "" + ch;
    		int chint = (int)ch;    		
    		int numberchint = chint - 48; //48 es el valor ascii del 0

    		if ((numberchint<0) || (numberchint >= 10)) //no es un numero
    		   {   
    			  return x+1;
    		}                   		
    	}
    	return index;
    }
    
    //El string finaliza en un n�mero.
    //Este m�todo devuelve el substring que contiene el numero.    
    private String getNumber(String s, int index){
    	String stringNumber;
    	stringNumber = s.substring(index);    	    	
    	return stringNumber;
    }
    
    
    public ClaseGeneradoraRecursoDepuracionCognitivo(String idRecurso)  throws Exception {
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

        
        int index = getNumberStartIndex(idRecurso);
        String number = getNumber(idRecurso,index);

	  	//Crear las trazas AR_NOMBREAGENTE y WM_NOMBREAGENTE en la ventana de trazas
        /*
	  	if (configDebugging.DepuracionJFrameActivationRulesDebugging){
	        ventanaActivationRulesGUI = new GUIDepuracionCognitivo();
	        this.ventanaActivationRulesGUI.setState(JFrame.ICONIFIED);	        
	        this.ventanaActivationRulesGUI.setVisible(true);
	        //configurar el aspecto del text area (color de fondo, color de la letra y tipo de letra)
	        this.ventanaActivationRulesGUI.setBackgroundTextArea(Color.white);
	        this.ventanaActivationRulesGUI.setForegroundTextArea(Color.blue);
//	        Font fuente = new Font("Arial", Font.PLAIN, 20);  //cambiar la fuente
//	        Font fuente = new Font("Arial", Font.PLAIN, 12);  //cambiar la fuente
	        Font fuente = new Font("Trebuchet", Font.PLAIN, 12);  //cambiar la fuente
	        this.ventanaActivationRulesGUI.setFontTextArea(fuente);
	        //No poermitir cerrar la ventana cuando se hace clic en el bot�n X situado en la parte superior derecha 
	        this.ventanaActivationRulesGUI.setDefaultCloseOperation(0);
	        this.ventanaActivationRulesGUI.setTitle("AR_" + number);
	  	}
	  
          * 
          */
	  	
	  	if (configDebugging.DepuracionWorkingMemoryDebugging){
	        ventanaWorkingMemoryGUI = new GUIDepuracionCognitivo();
	        this.ventanaWorkingMemoryGUI.setState(JFrame.ICONIFIED);	        	        
	        this.ventanaWorkingMemoryGUI.setBackgroundTextArea(Color.white);
	        this.ventanaWorkingMemoryGUI.setForegroundTextArea(Color.blue);
	        Font fuente = new Font("Trebuchet", Font.PLAIN, 12);  //cambiar la fuente
	        this.ventanaWorkingMemoryGUI.setFontTextArea(fuente);	        
	        this.ventanaWorkingMemoryGUI.setVisible(true);
	        this.ventanaWorkingMemoryGUI.setDefaultCloseOperation(0);
	        this.ventanaWorkingMemoryGUI.setTitle("WM_"+number);	  		
	  	}
	  	
	  /*	
	  	if (configDebugging.DepuracionJFrameRulesDebuger.equals("Yes")){
	        ventanaRulesDebugerGUI = new GUIDepuracionCognitivo();
	        this.ventanaRulesDebugerGUI.setState(JFrame.ICONIFIED);
	        this.ventanaRulesDebugerGUI.setBackgroundTextArea(Color.white);
	        this.ventanaRulesDebugerGUI.setForegroundTextArea(Color.blue);
	        Font fuente = new Font("Trebuchet", Font.PLAIN, 12);  //cambiar la fuente
	        this.ventanaRulesDebugerGUI.setFontTextArea(fuente);	        	        
	        this.ventanaRulesDebugerGUI.setVisible(true);
	        this.ventanaRulesDebugerGUI.setDefaultCloseOperation(0);
	        this.ventanaRulesDebugerGUI.setTitle("RD_" + number);	  		
	  	}
  	              
	  
          * 
          */
        //Ultima llamada del constructor seria la siguiente
        trazas.aceptaNuevaTraza(new InfoTraza(this.idRecurso, "El constructor de la clase generadora del recurso " + this.id + " ha completado su ejecucion ....",	InfoTraza.NivelTraza.debug));	
                
    //#end_nodeconstructorMethod:constructorMethod <--constructorMethod-- DO NOT REMOVE THIS    
    }	

    //Methods that implement DepuracionCognitivo resource use interface

    
    public synchronized void mostrarInfoRD(String info) throws Exception{
        this.ventanaRulesDebugerGUI.mostrarInfoTextArea(info);
    }
    
    
    public synchronized void mostrarInfoAR(String info) throws Exception{
        this.ventanaActivationRulesGUI.mostrarInfoTextArea(info);
    }
    
    
    public synchronized void mostrarInfoWM(String info) throws Exception{
        this.ventanaWorkingMemoryGUI.mostrarInfoTextArea(info);
    }     
    
    
    //End methods that implement DepuracionCognitivo resource use interface


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
