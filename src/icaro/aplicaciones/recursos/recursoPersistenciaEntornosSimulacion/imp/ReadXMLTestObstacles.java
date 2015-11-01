package icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.imp;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

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
	public synchronized NodeList getRobotsXMLStructure(Document doc, String tag){
		NodeList nodeLst = doc.getElementsByTagName(tag);
		return nodeLst;	
	}

}
