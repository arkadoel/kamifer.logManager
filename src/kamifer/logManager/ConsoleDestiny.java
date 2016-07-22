package kamifer.logManager;

public class ConsoleDestiny extends Destiny {

	public ConsoleDestiny() {
		
	}

	@Override
	public void info(String texto) {
		this.sendLog(texto);
		
	}

	


}
