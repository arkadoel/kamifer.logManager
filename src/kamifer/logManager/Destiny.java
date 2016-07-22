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
    }

	public String Name = "";
    public String Format = "";

    public String CallerClass = "";
    public String CallerMethod = "";
    public String CallerFile = "";
    public String CallerLineNumber = "";
	
	abstract void sendLog(String texto, String Level);
	
	public abstract void trace(String texto);
    public abstract void debug(String texto);
    public abstract void info(String texto);
    public abstract void warning(String texto);
    public abstract void error(String texto);
    public abstract void fatal(String texto);

    public String getDateTimeForFormat(){
        String formatDate = this.DateParameterConfiguration();
        SimpleDateFormat sdfDate = new SimpleDateFormat(formatDate);
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public String DateParameterConfiguration(){
        if(this.Format.contains("%date")){
            int firstPosition = this.Format.indexOf("%date");

            String quitadoPrincipio =this.Format.substring(firstPosition, this.Format.length());
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

}
