using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace kamifer.LogManager
{
    public abstract class Destiny
    {
        public static class DestinationsTypes
        {
            public static String CONSOLE = "CONSOLE";
            public static String CSV = "CSV";
            public static String XML = "XML";
        }

        public String Name = "";
        public String LoggerName = "";
        public String Format = "";

        public String CallerClass = "";
        public String CallerMethod = "";
        public String CallerFile = "";
        public String CallerLineNumber = "";
        public Exception exception = null; 

        public Dictionary<String, String> MyValues = new Dictionary<String, String>();
        

        public Destiny(){
        }

        public abstract void sendLog(String texto, String Level);

        public abstract void trace(String texto);
        public abstract void debug(String texto);
        public abstract void info(String texto);
        public abstract void warning(String texto);
        public abstract void error(String texto);
        public abstract void fatal(String texto);

        public String getDateTimeForFormat()
        {
            return getDateTimeForFormat(this.Format);
        }

        public String getDateTimeForFormat(String format)
        {
            String formatDate = this.DateParameterConfiguration(format);
            DateTime fecha = DateTime.Now;
            String strDate = fecha.ToString(formatDate);
            return strDate;
        }

        public String DateParameterConfiguration()
        {
            return DateParameterConfiguration(this.Format);
        }

        private String DateParameterConfiguration(String format)
        {
            if (format.Contains("%date"))
            {
                int firstPosition = format.IndexOf("%date");

                String quitadoPrincipio = format.Substring(firstPosition, format.Length-firstPosition);
                quitadoPrincipio = quitadoPrincipio.Replace("%date(", "");
                int finalformto = quitadoPrincipio.IndexOf(")");
                quitadoPrincipio = quitadoPrincipio.Substring(0, finalformto);
                return quitadoPrincipio;
            }
            return null;
        }

        public String getDateXMLText()
        {
            return "%date(" + DateParameterConfiguration() + ")";
        }

        public String getDateXMLText(String format)
        {
            return "%date(" + DateParameterConfiguration(format) + ")";
        }

        public String getReformatedText(String texto, String Level)
        {
            return getReformatedWithFormat(texto, Level, this.Format);
        }

        public String getReformatedWithFormat(String mensaje, String Level, String plantilla)
        {
            String ver = plantilla;

            String dateXMLText = getDateXMLText(plantilla);
            String dateTimeForFormat = getDateTimeForFormat(plantilla);
            ver = ver.Replace(dateXMLText, dateTimeForFormat);
            ver = ver.Replace("%loggerName", LoggerName);
            ver = ver.Replace("%file", this.CallerFile);
            ver = ver.Replace("%class", this.CallerClass);
            ver = ver.Replace("%method", this.CallerMethod);
            ver = ver.Replace("%line", this.CallerLineNumber);
            ver = ver.Replace("%level", Level);
            ver = ver.Replace("%message", mensaje);

            foreach (String key in MyValues.Keys)
            {
                ver = ver.Replace(key, MyValues[key]);
            }
            return ver;
        }
    }
}
