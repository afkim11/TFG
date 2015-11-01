package icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.imp;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.swing.internal.plaf.synth.resources.synth;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class ReadXMLTestObstacles {
	private String sequenceTestPath;

	public ReadXMLTestObstacles(String testFilePath){
		this.sequenceTestPath = testFilePath;
	}

	public String gettestFilePath(){
		return this.sequenceTestPath;
	}
	public synchronized Document getDocument(String testFilePath){
		Document doc=null;		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(new File(testFilePath));
			doc.getDocumentElement().normalize();
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc; //this return will not be  executed
	}
	//tag parameter should be equal to "obstacle"
	public synchronized NodeList getObstaculosXMLStructure(Document doc, String tag){
		NodeList nodeLst = doc.getElementsByTagName(tag);
		return nodeLst;	
	}
	
	public synchronized int getNumberObstaculos(NodeList listaObs){
		return listaObs.getLength();
	}

	public synchronized Element getObsElement(NodeList nodeLstObs, int posicion) {
		  Element info=null;
		  Node fstNode = nodeLstObs.item(posicion);
		  if (fstNode.getNodeType() == Node.ELEMENT_NODE){
			  info = (Element) fstNode;
		  }		      	
		  return info;
	}

	public synchronized Coordinate getCoordinate(Element info, String coordinate) {
		Coordinate robotCoordinate = new Coordinate(Double.parseDouble(this.getObsCoordinateX(info,coordinate,"x")),
		 Double.parseDouble(this.getObsCoordinateY(info,coordinate,"y")), 0.5);    	
		return robotCoordinate;
	}

	//Obtain information about x coordinate for the current robot  (contained in Element info)
    //tagcoordinate parameter should be equal to "initialcoordinate"
    //tagdimension parameter should be equal to "x"
    private synchronized String getObsCoordinateX(Element info, String tagcoordinate, String tagdimension){
          String valuex = "";          
		  NodeList coordinateNmElmntLst = info.getElementsByTagName(tagcoordinate);
		  Node coordinateNode = coordinateNmElmntLst.item(0);
		  if (coordinateNode.getNodeType() == Node.ELEMENT_NODE){
			  Element coorInfo = (Element) coordinateNode;			 
			  NodeList xNmElmntLst = coorInfo.getElementsByTagName(tagdimension);
			  Element xNmElmnt = (Element) xNmElmntLst.item(0);
			  NodeList xNm = xNmElmnt.getChildNodes();					  
			  valuex = ((Node)xNm.item(0)).getNodeValue();				  
		  }
		  return valuex;
    }

	//Obtain information about y coordinate for the current robot  (contained in Element info)
    //tagcoordinate parameter should be equal to "initialcoordinate"
    //tagdimension parameter should be equal to "y"    
    private synchronized String getObsCoordinateY(Element info, String tagcoordinate, String tagdimension){
          String valuey = "";        
		  NodeList coordinateNmElmntLst = info.getElementsByTagName(tagcoordinate);
		  Node coordinateNode = coordinateNmElmntLst.item(0);
		  if (coordinateNode.getNodeType() == Node.ELEMENT_NODE){
			  Element coorInfo = (Element) coordinateNode;			  			  
			  //Obtain information about y coordinate for the current robot 
			  NodeList yNmElmntLst = coorInfo.getElementsByTagName(tagdimension);
			  Element yNmElmnt = (Element) yNmElmntLst.item(0);
			  NodeList yNm = yNmElmnt.getChildNodes();					  
			  valuey = ((Node)yNm.item(0)).getNodeValue();					  
		  }
		  return valuey;
    }

	public String getObsIDValue(Element info, String tag) {
		  NodeList idNmElmntLst = info.getElementsByTagName(tag);
		  Element idNmElmnt = (Element) idNmElmntLst.item(0);
		  NodeList idNm = idNmElmnt.getChildNodes();					  
		  String valueid = ((Node)idNm.item(0)).getNodeValue();					  
    	  return valueid;
	}    


}
