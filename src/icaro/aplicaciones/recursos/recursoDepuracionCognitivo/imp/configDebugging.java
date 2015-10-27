package icaro.aplicaciones.recursos.recursoDepuracionCognitivo.imp;

public class configDebugging {
	
	//Yes -> Se depura por la consola
	public static Boolean DepuracionConsola = false;
	
	//Yes -> Se depura por el JFrame RD_NROAGENTE que muestra la traza de las reglas disparadas
	public static Boolean DepuracionRulesDebuger = true;
	//Formato de salida si las tres variables siguientes (ACTIVATIONRULES_DEBUGGING, 
	//                                                    FactHandlesMonitoring_afterActivationFired_DEBUGGING
	//                                                    FactHandlesMonitoring_beforeActivationFired_DEBUGGING) 
	//                                                    estan a true:
	//              Regla activada
	//              Hechos que activan la regla
	//              Hechos antes de activar la regla
	//              Hechos despues de activar la regla	
	//true -> Mostrar por la ventana de trazas AR_NOMBREAGENTE como se disparan las reglas (DefaultAgendaEventListener)
	//false -> No mostrar como se disparan las reglas
//	public static int ACTIVATIONRULES_DEBUGGING = true;
	public static boolean DepuracionActivationRulesDebugging = true;
	//true -> intento mostrar los fact handles antes/despues de dipararse la regla. (DefaultAgendaEventListener)
	//     Se muestran en la traza AR_NombreAgente.	
	public static boolean FactHandlesMonitoring_afterActivationFired_DEBUGGING = true;
	public static boolean FactHandlesMonitoring_beforeActivationFired_DEBUGGING = true;   

	
	//true -> Mostrar por la ventana de trazas WM_NOMBREAGENTE como se insertan, borran, actualizan, desde las TAREAS, 
	//     hechos en la memoria de trabajo de cada agente. 
	//     Concretamente es cuando se llama en las tareas a :
	//              this.getEnvioHechos().insertarHecho(...)   ---> este llama al assertFact de MotorDeReglasDroolsImp2     
	//              this.getEnvioHechos().eliminarHecho(...)   ---> este llama al retracttFact de MotorDeReglasDroolsImp2
	//              this.getEnvioHechos().actualizarHecho(...) ---> este llama al updateFact de MotorDeReglasDroolsImp2  
	public static int InsertDesdeTareas_DEBUGGING = 0;   //ANTES ESTABA A 1 
	public static int RetractDesdeTareas_DEBUGGING = 0;  //ANTES ESTABA A 1  
	public static int UpdateDesdeTareas_DEBUGGING = 0;   //ANTES ESTABA A 1
	
	//1 -> Mostrar por la ventana de trazas WM_NOMBREAGENTE como se insertan, borran, actualizan hechos en  
	//     la memoria de trabajo de cada agente	
//	public static int WORKINGMEMORY_DEBUGGING = 1;  //(DefaultWorkingMemoryEventListener)    
	public static boolean DepuracionWorkingMemoryDebugging = false;  //ANTES ESTABA A 1

	
	//1 -> Mostrar por la ventana de trazas WM_NOMBREAGENTE el contenido actual de los FactHandles que hay en la 
	//     memoria de trabajo. Para la depuracion asociada a los insert, retract y update asociados a los listener
	//     DefaultWorkingMemoryEventListener que aparecen en el metodo compileRules de la clase MotorDeReglasDroolsImp2
	//NOTA :  Con la combinacion 1, X, Y, Z intento mostrar los fact handles que hay en la memoria de trabajo cuando
	//        se hace un insert (X=1), retract (Y=1), update (Z=1) . 
	//        Se muestran en la traza WM_NOMBREAGENTE	
	public static boolean FactHandlesMonitoring_DEBUGGING = false;  //ANTES ESTABA A true      
	public static boolean FactHandlesMonitoringINSERT_DEBUGGING = false;  // Valor true: Si FactHandlesMonitoring_DEBUGGING==1 Entonces depurar los insert   
	public static boolean FactHandlesMonitoringRETRACT_DEBUGGING = false; // Valor true: Si FactHandlesMonitoring_DEBUGGING==1 Entonces depurar los retract     
	public static boolean FactHandlesMonitoringUPDATE_DEBUGGING = false;  // Valor true: Si FactHandlesMonitoring_DEBUGGING==1 Entonces depurar los update   
		
}
