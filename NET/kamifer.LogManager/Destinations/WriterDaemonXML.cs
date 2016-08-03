using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace kamifer.LogManager.Destinations
{
    public class WriterDaemonXML 
    {
        private String texto = "";
        private String filePath = "";
        private String project = "";

        public WriterDaemonXML(String path, String loggerName, String text)
        {
            texto = text;
            filePath = path;
            project = loggerName;
        }

        public void run()
        {
            Boolean escrito = false;

            while (escrito == false)
            {
                XDocument doc = null;
                try
                {
                    doc=  XDocument.Load(filePath);
                    XElement elemento = XElement.Parse(texto);
                    doc.Element(project).Add(elemento);
                    doc.Save(filePath, SaveOptions.None);
                    escrito = true;
                    //Console.WriteLine("+");
                }
                catch (IOException e)
                {
                    Console.WriteLine(e.Message);
                    escrito = false;
                    Thread.Sleep(2000);
                }
                finally
                {
                    if (doc != null)
                    {
                        doc = null;
                        GC.Collect();
                    }
                }
            }

        }
    }
}
