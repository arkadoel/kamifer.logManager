package kamifer.logManager;

public class ConsoleDestiny extends Destiny {



	public ConsoleDestiny() {
		
	}

	@Override
	protected void sendLog(String texto, String Level) {
		String ver = getReformatedText(texto, Level);
		System.out.println(ver);
	}



	@Override
	public void trace(String texto) {
		this.sendLog(texto, Logger.LoggerLevels.TRACE);
	}

	@Override
	public void debug(String texto) {
		this.sendLog(texto, Logger.LoggerLevels.DEBUG);
	}

	@Override
	public void info(String texto) {
		this.sendLog(texto, Logger.LoggerLevels.INFO);
	}

	@Override
	public void warning(String texto) {
		this.sendLog(texto, Logger.LoggerLevels.WARNING);
	}

	@Override
	public void error(String texto) {
		this.sendLog(texto, Logger.LoggerLevels.ERROR);
	}

	@Override
	public void fatal(String texto) {
		this.sendLog(texto, Logger.LoggerLevels.FATAL);
	}


}
