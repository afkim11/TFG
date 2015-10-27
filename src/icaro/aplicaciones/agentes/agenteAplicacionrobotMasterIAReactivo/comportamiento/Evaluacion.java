/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotMasterIAReactivo.comportamiento;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Administrador
 */
public class Evaluacion {
   /* public static int DISTANCIA_AGENTE_1 = 107;
    public static int DISTANCIA_AGENTE_2 = 101;
    public static int DISTANCIA_AGENTE_3 = 101;
    public static int DISTANCIA_AGENTE_4 = 107;*/
    

//    private String pathFichero = "./src/icaro/aplicaciones/agentes/agenteAplicacionrobotMasterIAReactivo/comportamiento/EvaluacionAgentes.xml";
    private String pathFichero = icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas.rutaFicheroEvaluacionAgentesXML;
    private static Evaluacion evaluacionAgentes = null;//patron singleton
    private ArrayList distancias = null; //arrayList con las distancias. El id de los agentes es su posicion

    private Evaluacion(){
        leerFicheroXML();
    }
    public Integer dameDistanciaAgentes(int i){
        return (Integer)distancias.get(i);
    }

    public static Evaluacion dameEvaluacionAgentes(){
        if (evaluacionAgentes == null){
            evaluacionAgentes = new Evaluacion();
        }
        return evaluacionAgentes;
    }

    //lectura del fichero XML con las distancias.
    private void leerFicheroXML() {
         distancias = new ArrayList();
         Integer valor;
         try {
            File file = new File(pathFichero);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();            
            NodeList nodeLst = doc.getElementsByTagName("agente");

            for (int s = 0; s < nodeLst.getLength(); s++) {

                Node fstNode = nodeLst.item(s);

                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

                  Element info = (Element) fstNode;
                  NodeList fstNmElmntLst = info.getElementsByTagName("id");
                  Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
                  NodeList fstNm = fstNmElmnt.getChildNodes();
                  //System.out.println("First Name : "  + ((Node) fstNm.item(0)).getNodeValue());
                  NodeList lstNmElmntLst = info.getElementsByTagName("valor");
                  Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
                  NodeList lstNm = lstNmElmnt.getChildNodes();
                  valor = new Integer(((Node) lstNm.item(0)).getNodeValue());
                  //System.out.println("Last Name : " + ((Node) lstNm.item(0)).getNodeValue());
                  distancias.add(Integer.parseInt(((Node) fstNm.item(0)).getNodeValue()), valor);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         
    }
}
