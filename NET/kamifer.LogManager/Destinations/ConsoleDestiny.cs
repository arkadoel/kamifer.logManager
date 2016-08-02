using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace kamifer.LogManager.Destinations
{
    public class ConsoleDestiny : Destiny
    {

        public override void sendLog(string texto, string Level)
        {
            String ver = getReformatedText(texto, Level);
		    Console.WriteLine(ver);
        }

         

        public override void trace(String texto)
        {
            this.sendLog(texto, Logger.LoggerLevels.TRACE);
        }


        public override void debug(String texto)
        {
            this.sendLog(texto, Logger.LoggerLevels.DEBUG);
        }


        public override void info(String texto)
        {
            this.sendLog(texto, Logger.LoggerLevels.INFO);
        }


        public override void warning(String texto)
        {
            this.sendLog(texto, Logger.LoggerLevels.WARN);
        }


        public override void error(String texto)
        {
            this.sendLog(texto, Logger.LoggerLevels.ERROR);
        }


        public override void fatal(String texto)
        {
            this.sendLog(texto, Logger.LoggerLevels.FATAL);
        }
                
    }
}
