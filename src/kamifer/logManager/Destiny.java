package kamifer.logManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * Clase que encapsula los distintos destinos que puede tener
 *
 */
public abstract class Destiny {

    /**
     * TYPES OF DESTINATIONS FOR LOGS
     */
    public static class DestinationsTypes{

        public static String CONSOLE = "CONSOLE";
        public static String CSV = "CSV";
    }

	public String Name = "";
    public String Format = "";

    public String CallerClass = "";
    public String CallerMethod = "";
    public String CallerFile = "";
    public String CallerLineNumber = "";
	
	protected abstract void sendLog(String texto, String Level);
	
	public abstract void trace(String texto);
    public abstract void debug(String texto);
    public abstract void info(String texto);
    public abstract void warning(String texto);
    public abstract void error(String texto);
    public abstract void fatal(String texto);

    public String getDateTimeForFormat(){
        return getDateTimeForFormat(this.Format);
    }

    public String getDateTimeForFormat(String format){
        String formatDate = this.DateParameterConfiguration(format);
        SimpleDateFormat sdfDate = new SimpleDateFormat(formatDate);
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public String DateParameterConfiguration(){
        return DateParameterConfiguration(this.Format);
    }

    private String DateParameterConfiguration(String format){
        if(format.contains("%date")){
            int firstPosition = format.indexOf("%date");

            String quitadoPrincipio =format.substring(firstPosition, format.length());
            quitadoPrincipio = quitadoPrincipio.replace("%date(", "");
            int finalformto = quitadoPrincipio.indexOf(")");
            quitadoPrincipio = quitadoPrincipio.substring(0, finalformto);
            return  quitadoPrincipio;
        }
        return null;
    }

    public String getDateXMLText(){
        return "%date(" + DateParameterConfiguration() + ")";
    }

    public String getDateXMLText(String format){
        return "%date(" + DateParameterConfiguration(format) + ")";
    }

    public String getReformatedText(String texto, String Level) {
        return getReformatedWithFormat(texto, Level, this.Format);
    }

    public String getReformatedWithFormat(String mensaje, String Level, String plantilla){
        String ver = plantilla;

        String dateXMLText = getDateXMLText(plantilla);
        String dateTimeForFormat = getDateTimeForFormat(plantilla);
        ver = ver.replace(dateXMLText,dateTimeForFormat );
        ver = ver.replace("%file", this.CallerFile);
        ver = ver.replace("%class", this.CallerClass);
        ver = ver.replace("%method", this.CallerMethod);
        ver = ver.replace("%line", this.CallerLineNumber);
        ver = ver.replace("%level", Level);
        ver = ver.replace("%message", mensaje);
        return ver;
    }

}
