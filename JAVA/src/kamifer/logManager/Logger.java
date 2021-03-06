package kamifer.logManager;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import kamifer.logManager.csv.CSVDestiny;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Logger {

	private String LogName;
	private String Level;
	private ArrayList<Destiny> Destinos = new ArrayList<Destiny>();
    private  ArrayList<String> LevelsToPrint = new ArrayList<>();
    private Map<String, String> myValues = new HashMap<String, String>();

    public Map<String, String> getMyValues() {
        return myValues;
    }

    public void setMyValue(String key, String value) {
        myValues.put("%" + key, value);

        for(Destiny destino : Destinos){
            destino.MyValues = myValues;
        }
    }

    public static class LoggerLevels{
        public static String TRACE = "TRACE";
        public static String DEBUG = "DEBUG";
        public static String INFO = "INFO";
        public static String WARNING = "WARNING";
        public static String ERROR = "ERROR";
        public static String FATAL = "FATAL";
    }

	/**
	 * Constructor por defecto
	 */
	public Logger() {
		
	}
	
	/**
	 * Cargamos el archivo de configuracion 
	 * @param filePath
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
                    this.setLevelsToPrint(this.getLevel());

                    //CARGAR VARIABLES PERSONALIZADAS
                    NodeList xmlMyValues = eElement.getElementsByTagName("MyValues").item(0).getChildNodes();


                    for(int zval = 0; zval<xmlMyValues.getLength(); zval++){
                        Node nodoMyValue = xmlMyValues.item(zval);
                        if(nodoMyValue.getNodeType() == Node.ELEMENT_NODE){
                            Element eMyValue = (Element) nodoMyValue;

                            if(eMyValue.hasAttribute("default")) {
                                myValues.put("%" + eMyValue.getTagName(), eMyValue.getAttribute("default"));
                            }
                            else{
                                myValues.put("%" + eMyValue.getTagName(), "");
                            }
                        }
                    }

                    //CARGAR DESTINOS
                    NodeList destinos = ((Element)eElement.getElementsByTagName("Destinations").item(0)).getElementsByTagName("Destination");

                    Destinos = new ArrayList<Destiny>();

                    //cargamos los distintos destinos puestos en el archivo de configuracion
					for(int zd=0; zd<destinos.getLength(); zd++){
						Node nodoDestino = destinos.item(zd);

                        if(nodoDestino.getNodeType() == Node.ELEMENT_NODE){
                            Element eDestino = (Element) nodoDestino;

                            if(eDestino.getAttribute("value").toUpperCase().equals(Destiny.DestinationsTypes.CONSOLE)){
                                ConsoleDestiny consola = new ConsoleDestiny();
                                consola.Format = eDestino.getAttribute("format");
                                consola.MyValues = myValues;
                                Destinos.add(consola);
                                System.out.println("Cargado un destino de consola");
                            }
                            else if(eDestino.getAttribute("value").toUpperCase().equals(Destiny.DestinationsTypes.CSV)){
                                CSVDestiny csv = new CSVDestiny();
                                csv.Format = eDestino.getAttribute("format");
                                if(eDestino.getAttribute("separatorCharacter") != null && eDestino.getAttribute("separatorCharacter").length()==1) {
                                    csv.setSeparatorCharacter( Character.valueOf( eDestino.getAttribute("separatorCharacter").toCharArray()[0]));
                                }

                                if(eDestino.getAttribute("headersLine") != null){
                                    csv.setHeadersLine( eDestino.getAttribute("headersLine"));
                                }
                                csv.setFilePath( eDestino.getAttribute("filePath"));

                                csv.MyValues = myValues;
                                Destinos.add(csv);
                                System.out.println("Cargado un destino de csv");
                            }
                        }
					}
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * Chequea que se deba de imprimir o no el mensaje
     * @param Level
     * @return true || false
     */
    private boolean useForTheDefinedLevel(String level){
        boolean print = false;
        if(LevelsToPrint.contains(level)){
            print = true;
        }

        return print;
    }

    private void setLevelsToPrint(String level) {

        switch (level.toUpperCase()){
            case "TRACE": LevelsToPrint.add("TRACE");
            case "DEBUG": LevelsToPrint.add("DEBUG");
            case "INFO": LevelsToPrint.add("INFO");
            case "WARNING": LevelsToPrint.add("WARNING");
            case "ERROR": LevelsToPrint.add("ERROR");
            case "FATAL": LevelsToPrint.add("FATAL");
        }
    }

    public void info(String mensaje){
	    if(useForTheDefinedLevel(LoggerLevels.INFO)) {
            for (Destiny destino : Destinos) {
                SetDestinyCallers(destino,3);
                destino.info(mensaje);
            }
        }
	}

    public void trace(String mensaje){
        if(useForTheDefinedLevel(LoggerLevels.TRACE)) {
            for (Destiny destino : Destinos) {
                SetDestinyCallers(destino,3);
                destino.trace(mensaje);
            }
        }
    }

    public void debug(String mensaje){
        if(useForTheDefinedLevel(LoggerLevels.DEBUG)) {
            for (Destiny destino : Destinos) {
                SetDestinyCallers(destino,3);
                destino.debug(mensaje);
            }
        }
    }

    public void warning(String mensaje){
        if(useForTheDefinedLevel(LoggerLevels.WARNING)) {
            for (Destiny destino : Destinos) {
                SetDestinyCallers(destino,3);
                destino.warning(mensaje);
            }
        }
    }

    public void error(String mensaje){
        if(useForTheDefinedLevel(LoggerLevels.ERROR)) {
            for (Destiny destino : Destinos) {
                SetDestinyCallers(destino,3);
                destino.error(mensaje);
            }
        }
    }

    public void fatal(String mensaje){
        if(useForTheDefinedLevel(LoggerLevels.FATAL)) {
            for (Destiny destino : Destinos) {
                SetDestinyCallers(destino,4);
                destino.fatal(mensaje );
            }
        }
    }

    /**
     *
     * @param text
     * @param loggerLevel
     * @param stackLevel: the normal level is 3 but if you extends other class with Logger
     *                  you must increase this number
     */
    public void log(String text, String loggerLevel, int stackLevel ){
        if(useForTheDefinedLevel(loggerLevel.toUpperCase())) {
            for (Destiny destino : Destinos) {
                SetDestinyCallers(destino,stackLevel);
                destino.sendLog(text, loggerLevel);
            }
        }
    }

    private void SetDestinyCallers(Destiny destino, int nivelStack) {
        destino.CallerClass = Thread.currentThread().getStackTrace()[nivelStack].getClassName();
        destino.CallerFile = Thread.currentThread().getStackTrace()[nivelStack].getFileName();
        destino.CallerLineNumber = String.valueOf( Thread.currentThread().getStackTrace()[nivelStack].getLineNumber());
        destino.CallerMethod = Thread.currentThread().getStackTrace()[nivelStack].getMethodName();
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
