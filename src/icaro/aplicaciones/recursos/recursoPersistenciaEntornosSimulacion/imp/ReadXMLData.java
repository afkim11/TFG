package icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.imp;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class ReadXMLData {
	private String sequenceTestPath;

	public ReadXMLData(String testFilePath){
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
	public synchronized NodeList getXMLStructure(Document doc, String tag){
		NodeList nodeLst = doc.getElementsByTagName(tag);
		return nodeLst;	
	}
	
	
}
