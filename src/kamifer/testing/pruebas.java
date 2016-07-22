package kamifer.testing;



public class pruebas {

	

	public static MiLog milog = new MiLog();
	
	public static void main(String[] args) {
		milog.LoadConfigXML("./cfg/LogManager.xml");
		milog.info("Se esta cargando");
		System.out.println("cargado log");
	}

}
