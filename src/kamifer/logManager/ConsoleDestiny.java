package kamifer.logManager;

public class ConsoleDestiny extends Destiny {



	public ConsoleDestiny() {
		
	}

	@Override
	void sendLog(String texto, String Level) {
		String ver = Format;

		ver = ver.replace(getDateXMLText(), getDateTimeForFormat());
		ver = ver.replace("%file", this.CallerFile);
		ver = ver.replace("%class", this.CallerClass);
		ver = ver.replace("%method", this.CallerMethod);
		ver = ver.replace("%line", this.CallerLineNumber);
		ver = ver.replace("%level", Level);
		ver = ver.replace("%message", texto);
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
