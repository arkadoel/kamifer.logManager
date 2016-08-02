using kamifer.LogManager;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace testing
{
    public class miLog : Logger
    {
        public new void fatal(String mensaje)
        {
            base.log(mensaje, LoggerLevels.FATAL, 3);
            base.log("Error fatal, fin de la aplicacion", LoggerLevels.INFO, 3);
            Environment.Exit(-1);
        }
        public new void fatal(String mensaje, Exception ex)
        {
            base.log(mensaje, LoggerLevels.FATAL, 3, ex);
            base.log("Error fatal, fin de la aplicacion", LoggerLevels.INFO, 3);
            Environment.Exit(-1);
        }

    }
}
