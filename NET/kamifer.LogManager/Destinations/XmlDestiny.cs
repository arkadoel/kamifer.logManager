using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace kamifer.LogManager.Destinations
{
    public class XmlDestiny : Destiny
    {
        private string filePath = "";
        public string FilePath
        {
            get
            {
                return filePath;
            }
            set
            {
                filePath = value;
                setFilePath(value);
            }
        }

        public override void sendLog(string texto, string Level)
        {
            if ((Logger.LoggerLevels.FATAL == Level || Logger.LoggerLevels.ERROR == Level) && this.exception !=null )
            {
                StringBuilder lineas = new StringBuilder();
                XElement MensajeXML = new XElement("ErrorMensaje");

                MensajeXML.SetValue(exception.Message);

                if (exception.InnerException != null)
                {
                    XElement innerException = new XElement("InnerException",
                        new XElement("Source", exception.InnerException.Source),
                        new XElement("InnerText", exception.InnerException.Message)
                        );
                    MensajeXML.Add(innerException);
                }

                MensajeXML.Add(new XElement("StackTrace", exception.StackTrace.ToString()));

                lineas.AppendLine(String.Format("<Menssage>{0}</Menssage>", texto));
                lineas.AppendLine(MensajeXML.ToString());

                String mensaje = lineas.ToString();
                String ver = getReformatedWithFormat(mensaje, Level, this.Format);
                writeInFile(ver);
            }
            else
            {
                String ver = getReformatedWithFormat(texto, Level, this.Format);
                writeInFile(ver);
            }
        }

        public override void trace(string texto)
        {
            this.sendLog(texto, Logger.LoggerLevels.TRACE);
        }

        public override void debug(string texto)
        {
            this.sendLog(texto, Logger.LoggerLevels.DEBUG);
        }

        public override void info(string texto)
        {
            this.sendLog(texto, Logger.LoggerLevels.INFO);
        }

        public override void warning(string texto)
        {
            this.sendLog(texto, Logger.LoggerLevels.WARN);
        }

        public override void error(string texto)
        {
            this.sendLog(texto, Logger.LoggerLevels.ERROR);
        }

        public override void fatal(string texto)
        {
            this.sendLog(texto, Logger.LoggerLevels.FATAL);        
        }

        private void setFilePath(String filePath)
        {
            filePath = this.getReformatedWithFormat("", "INFO", filePath);
            filePath = filePath.Replace(":", "");
            this.filePath = filePath;
            //check and create file and write headers
            FileInfo fichero = new FileInfo(filePath);
            if (fichero.Directory.Exists == false)
            {
                Console.WriteLine("Creando directorio logs: {0}" + fichero.Directory.FullName);
                Directory.CreateDirectory(fichero.Directory.FullName);
            }
            if (fichero.Exists == false)
            {
                string textoInicio = String.Format("<?xml version=\"1.0\" encoding=\"utf-8\"?> \r\n<{0}>\r\n</{0}>", LoggerName);
                WriterDaemon demon = new WriterDaemon(filePath, textoInicio);
                /*Thread thread1 = new Thread(demon.run);
                thread1.IsBackground = true;
                thread1.Start();
                 */
                demon.run();
            }
        }

        private void writeInFile(String text)
        {
            WriterDaemonXML demon = new WriterDaemonXML(filePath, this.LoggerName, text);
           /*
            Thread thread1 = new Thread(demon.run);
            thread1.IsBackground = true;
            thread1.Start();      
            * */
            demon.run();
        }




    }
}
