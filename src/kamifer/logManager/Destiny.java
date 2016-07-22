package kamifer.logManager;

/***
 * Clase que encapsula los distintos destinos que puede tener
 *
 */
public abstract class Destiny {

	public String Name = "";
	
	void sendLog(String texto){
		System.out.println(texto);
		
	}
	
	public abstract void info(String texto);

}
