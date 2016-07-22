package kamifer.logManager;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;

public abstract class Logger {

	private String LogName;
	private String Level;
	private ArrayList<Destiny> Destinos = new ArrayList<Destiny>();

	/**
	 * Constructor por defecto
	 */
	public Logger() {
		
	}
	
	/**
	 * Cargamos el archivo de configuracion 
	 * @param path
	 */
	public void LoadConfigXML(String filePath){
		try{
			File fXmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);					
			
			doc.getDocumentElement().normalize();
			
			NodeList loggersList = doc.getElementsByTagName("LogManager"); //solo uno en la primera version
			
			for(int z = 0; z < loggersList.getLength(); z++){
				Node nodoLogConfig = loggersList.item(z);
				
				if (nodoLogConfig.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nodoLogConfig;
					
					this.Level = ((Element)eElement.getElementsByTagName("Level").item(0)).getAttribute("value");
					NodeList destinos = eElement.getElementsByTagName("Destinations");
					
					
				}
				
				
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void info(String mensaje){
		for(Destiny destino : Destinos){
			destino.info(mensaje);
		}
	}
	
	

	//getters and setters
	public String getLevel() {
		return Level;
	}

	public void setLevel(String level) {
		Level = level;
	}
	public String getLogName() {
		return LogName;
	}

	public void setLogName(String logName) {
		LogName = logName;
	}

	public ArrayList<Destiny> getDestinos() {
		return Destinos;
	}

	public void setDestinos(ArrayList<Destiny> destinos) {
		Destinos = destinos;
	}

}
