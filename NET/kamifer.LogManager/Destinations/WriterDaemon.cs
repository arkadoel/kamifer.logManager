using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace kamifer.LogManager.Destinations
{
    public class WriterDaemon
    {
        private String texto = "";
        private String filePath = "";
        private String project = "";

        public WriterDaemon(String path, String text)
        {
            texto = text;
            filePath = path;
        }

        public void run()
        {
            Boolean escrito = false;

            while (escrito == false)
            {
                StreamWriter fich = null;
                try
                {

                    fich = new StreamWriter(filePath, true);
                    fich.WriteLine(texto);
                    escrito = true;
                    //Console.WriteLine("*");
                }
                catch (IOException e)
                {
                    Console.WriteLine(e.Message);
                    escrito = false;
                    Thread.Sleep(2000);
                }
                finally
                {
                    if (fich != null)
                    {
                        fich.Close();
                    }
                }
            }

        }
    }
}
