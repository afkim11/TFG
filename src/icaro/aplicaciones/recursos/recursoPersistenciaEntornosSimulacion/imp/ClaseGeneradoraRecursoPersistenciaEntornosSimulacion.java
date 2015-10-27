package icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.imp;

import icaro.aplicaciones.Rosace.informacion.*;
import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.ItfUsoRecursoPersistenciaEntornosSimulacion;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

//Other imports used by this Resource
//#start_nodespecialImports:specialImports <--specialImports-- DO NOT REMOVE THIS
 
//#end_nodespecialImports:specialImports <--specialImports-- DO NOT REMOVE THIS


    //Methods that implement PersistenciaEntornosSimulacion resource use interface
    
    
    
public  class ClaseGeneradoraRecursoPersistenciaEntornosSimulacion extends ImplRecursoSimple implements ItfUsoRecursoPersistenciaEntornosSimulacion {

//    private ItfUsoRecursoTrazas trazas; //Se inicializa en el constructor con la referencia al recurso de trazas. Asi ya estara disponible en cualquier metodo.
   private String idRecurso;  //Se inicializara en el constructor con el identificador, dado a la instancia del recurso, en la descripcion de la organizacion
   private ReadXMLTestSequence rXMLTSeq; 
   private NodeList nodeLst;      // estructura en memoria con todos los nodos de								   // las victimas que hay en el fichero xml
   private String rutaFicheroVictimasTest;	
   private String rutaFicheroRobotsTest; 
   private Document doc;
   private RecursoPersistenciaEntornosSimulacionImp impPersistenciaXML ;
   private Map<String, String> victimsDiferentesXML = new HashMap <String, String>();  //Victimas diferentes que hay en el fichero de secuencia tomado como entrada
   private int numeroVictimasDiferentesSimulacion; //Numero de victimas diferentes que van a intervenir en el proceso de simulacion
   private ArrayList<Victim> victimasDefinidas; 
   private RobotStatus statusRobot;
   public ClaseGeneradoraRecursoPersistenciaEntornosSimulacion(String idrecurso) throws Exception {
            super(idrecurso);           
             try {
           impPersistenciaXML = new RecursoPersistenciaEntornosSimulacionImp(idrecurso);
           impPersistenciaXML.inicializarRecursoPersistenciaEntornosSimulacion();
           impPersistenciaXML.setNumeroVictimasDiferentesSimulacion();
           
        } catch (Exception ex) {
            Logger.getLogger(RecursoPersistenciaEntornosSimulacionImp.class.getName()).log(Level.SEVERE, null, ex);
            
        }
     }
  
        
//    @Override
    
     public int getNumeroTotalVictimasEnLaSecuencia(){
         return this.impPersistenciaXML.getNumeroTotalVictimasEnLaSecuencia();
     }
    
 
    @Override
    public ArrayList<Victim> getVictimasArescatar ()throws Exception{
        if (victimasDefinidas == null)
        victimasDefinidas = this.impPersistenciaXML.getVictimsArescatar ();
        return victimasDefinidas;
        
    }
    @Override
    public void termina() {
        trazas.aceptaNuevaTraza(new InfoTraza(this.idRecurso, "Terminando recurso" + this.id + " ....",	InfoTraza.NivelTraza.debug));
 	    
        //Si es un recurso de visualizacion es necesaria una llamar a dispose de la ventana de visualizacion. Algo parecido a lo siguiente	
        //this.jvariableLocalReferenciaVisualizador.dispose(); //Destruye los componentes utilizados por este JFrame y devuelve la memoria utilizada al Sistema Operativo 	 
		
        super.termina();
    }

    @Override
    public void guardarInfoCasoSimulacion(InfoCasoSimulacion infoCaso) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public InfoCasoSimulacion obtenerInfoCasoSimulacion(String idCaso) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void guardarInfoAsignacionVictima(InfoAsignacionVictima infoAsignVictima) throws Exception {
        this.impPersistenciaXML.guardarInfoAsignacionVictima(infoAsignVictima);
    }

    @Override
    public ArrayList<InfoAsignacionVictima> obtenerInfoAsignacionVictimas() throws Exception {
       return  this.impPersistenciaXML.obtenerInfoAsignacionVictimas();
    }

    @Override
    public void finSimulacion() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public boolean guardarSerieResultadosSimulacion(InfoSerieResultadosSimulacion infoSerieResultados)throws Exception{
       return  this.impPersistenciaXML.guardarSerieResultadosXML(infoSerieResultados);
    }
    @Override
    public String getIdentEscenarioSimulacion()throws Exception{
    return  impPersistenciaXML.getRutaEscenarioSimulacion();
}
    @Override
    public RobotStatus getRobotStatus ( String robotId)throws Exception{
        return impPersistenciaXML.getRobotStatus (  robotId);
        
    }
}
            

