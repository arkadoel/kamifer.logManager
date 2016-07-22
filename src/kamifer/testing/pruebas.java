package kamifer.testing;


import java.text.SimpleDateFormat;
import java.util.Date;

public class pruebas {

	

	public static MiLog milog = new MiLog();
	
	public static void main(String[] args) {
		milog.LoadConfigXML("./cfg/logManager.xml");
		milog.info("Se esta cargando");
        milog.error("Se esta cargando");
        milog.trace("Se esta cargando");
        milog.debug("Se esta cargando");
        milog.warning("Se esta cargando");
        new OtraClase().metodo();
		milog.error("error normal");
		System.out.println("cargado log");


	}

}
