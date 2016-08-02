using kamifer.LogManager.Destinations;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace kamifer.LogManager
{
    public class Logger
    {
        private List<string> LevelsToPrint = new List<string>() { 
            LoggerLevels.TRACE,
            LoggerLevels.DEBUG,
            LoggerLevels.INFO,
            LoggerLevels.WARN,
            LoggerLevels.ERROR,
            LoggerLevels.FATAL
        };

        public static class LoggerLevels
        {
            public static String TRACE = "TRACE";
            public static String DEBUG = "DEBUG";
            public static String INFO = "INFO";
            public static String WARN = "WARN";
            public static String ERROR = "ERROR";
            public static String FATAL = "FATAL";
        }

        private string level;
        public string Level {
            get
            {
                return this.level;
            }
            set
            {
                this.level = value;
                setLevelsToPrint(level);
            } 
        }
        private string LoggerName { get; set; }
        public static string LOG_DIR { get; set; }
        public static string LOG_FILEPATH { get; set; }
        public static string NOMBRE_PROYECTO { get; set; }

        private List<Destiny> Destinos = new List<Destiny>();

        public Logger()
        {

        }

        /// <summary>
        /// Chequea que se deba de imprimir o no el mensaje
        /// </summary>
        /// <param name="level"></param>
        /// <returns>true || false</returns>
        private Boolean useForTheDefinedLevel(String level)
        {
            Boolean print = false;
            if (LevelsToPrint.Contains(level))
            {
                print = true;
            }

            return print;
        }

        /// <summary>
        /// Set the level and the levels to print
        /// </summary>
        /// <param name="level"></param>
        private void setLevelsToPrint(String level)
        {
            bool encontrado = false;
            for (int i = 0; i <=LevelsToPrint.Count && encontrado == false; i++)
            {
                if (LevelsToPrint[i] == level.ToUpper())
                {
                    encontrado = true;
                }
                else
                {
                    LevelsToPrint.RemoveAt(i);
                    i--;
                }
            }
        }


        public void LoadConfigXML(String filePath)
        {
            XDocument configxml = null;
            try
            {
                configxml = XDocument.Load(filePath);
                var loggersList = configxml.Elements("LogManager").Elements("Logger");

                foreach (var xmlLogger in loggersList)
                {
                    this.LoggerName = xmlLogger.Attribute("name").Value.ToString();
                    this.Level = xmlLogger.Element("Level").Attribute("value").Value.ToString();
                    foreach (var nodoDestino in xmlLogger.Elements("Destinations").Elements("Destination"))
                    {
                        string tipoDestino = nodoDestino.Attribute("value").Value.ToString().ToUpper();
                       
                        if (tipoDestino == Destiny.DestinationsTypes.CONSOLE)
                        {

                            ConsoleDestiny destino = new ConsoleDestiny();
                            destino.LoggerName = this.LoggerName;
                            destino.Format = nodoDestino.Attribute("format").Value.ToString();
                            Destinos.Add(destino);
                        }
                        else if (tipoDestino == Destiny.DestinationsTypes.XML)
                        {
                            XmlDestiny destino = new XmlDestiny();
                            destino = new Destinations.XmlDestiny();
                            destino.LoggerName = this.LoggerName;
                            destino.Format = nodoDestino.Attribute("format").Value.ToString();
                            destino.FilePath = nodoDestino.Attribute("filePath").Value.ToString();
                            Destinos.Add(destino);
                        }

                    }
                }

            }
            catch (Exception ex)
            {
                Console.WriteLine("Error log: " + ex.Message);
            }
            finally
            {
                configxml = null;
                GC.Collect();
            }

        }

        public void info(String mensaje)
        {
            if (useForTheDefinedLevel(LoggerLevels.INFO))
            {
                foreach (Destiny destino in Destinos)
                {
                    SetDestinyCallers(destino, 2);
                    destino.info(mensaje);
                }
            }
        }

        public void trace(String mensaje)
        {
            if (useForTheDefinedLevel(LoggerLevels.TRACE))
            {
                foreach (Destiny destino in Destinos)
                {
                    SetDestinyCallers(destino, 2);
                    destino.trace(mensaje);
                }
            }
        }

        public void debug(String mensaje)
        {
            if (useForTheDefinedLevel(LoggerLevels.DEBUG))
            {
                foreach (Destiny destino in Destinos)
                {
                    SetDestinyCallers(destino, 2);
                    destino.debug(mensaje);
                }
            }
        }

        public void warning(String mensaje)
        {
            if (useForTheDefinedLevel(LoggerLevels.WARN))
            {
                foreach (Destiny destino in Destinos)
                {
                    SetDestinyCallers(destino, 2);
                    destino.warning(mensaje);
                }
            }
        }

        public void error(String mensaje)
        {
            if (useForTheDefinedLevel(LoggerLevels.ERROR))
            {
                foreach (Destiny destino in Destinos)
                {
                    SetDestinyCallers(destino, 2);
                    destino.error(mensaje);
                }
            }
        }

        public void fatal(String mensaje)
        {
            if (useForTheDefinedLevel(LoggerLevels.FATAL))
            {
                foreach (Destiny destino in Destinos)
                {
                    SetDestinyCallers(destino, 2);
                    destino.fatal(mensaje);
                }
            }
        }

        /**
         *
         * @param text
         * @param loggerLevel
         * @param stackLevel: the normal level is 3 but if you extends other class with Logger
         *                  you must increase this number
         */
        public void log(String text, String loggerLevel, int stackLevel)
        {
            if (useForTheDefinedLevel(loggerLevel.ToUpper()))
            {
                foreach (Destiny destino in Destinos)
                {
                    SetDestinyCallers(destino, stackLevel);
                    destino.sendLog(text, loggerLevel);
                }
            }
        }

        public void log(String text, String loggerLevel, int stackLevel, Exception ex)
        {
            if (useForTheDefinedLevel(loggerLevel.ToUpper()))
            {
                foreach (Destiny destino in Destinos)
                {
                    SetDestinyCallers(destino, stackLevel);
                    destino.exception = ex;
                    destino.sendLog(text, loggerLevel);
                }
            }
        }
        
        private void SetDestinyCallers(Destiny destino, int nivelStack)
        {
            StackTrace stackTrace = new StackTrace();
            destino.CallerMethod = stackTrace.GetFrame(nivelStack).GetMethod().Name;
            destino.CallerFile = new FileInfo( new StackFrame(1, true).GetFileName()).Name;
            destino.CallerLineNumber = new StackFrame(nivelStack, true).GetFileLineNumber().ToString();
            destino.CallerClass = stackTrace.GetFrame(nivelStack).GetMethod().DeclaringType.Name;            
        }

    }
}
