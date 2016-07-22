package kamifer.testing;

import kamifer.logManager.Logger;

public class MiLog extends Logger{

    /**
     * Personalizacion del metodo fatal
     * @param mensaje
     */
    @Override
    public void fatal(String mensaje) {
        super.log(mensaje, LoggerLevels.FATAL, 4);
        super.log("Error fatal, fin de la aplicacion", LoggerLevels.INFO, 4);
        System.exit(-1);
    }
}