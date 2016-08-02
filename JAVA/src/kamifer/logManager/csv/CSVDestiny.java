package kamifer.logManager.csv;

import kamifer.logManager.Destiny;
import kamifer.logManager.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/**
 * Created by fer on 22/07/16.
 */
public class CSVDestiny extends Destiny {

    private Character separatorCharacter = ';';
    private String headersLine = "";
    private String filePath = "";

    @Override
    protected void sendLog(String texto, String Level) {
        String ver = getReformatedWithFormat(texto, Level, this.Format);
        writeInFile(ver);
    }

    @Override
    public void trace(String texto) {
        this.sendLog(texto, Logger.LoggerLevels.TRACE);
    }

    @Override
    public void debug(String texto) {
        this.sendLog(texto, Logger.LoggerLevels.DEBUG);
    }

    @Override
    public void info(String texto) {
        this.sendLog(texto, Logger.LoggerLevels.INFO);
    }

    @Override
    public void warning(String texto) {
        this.sendLog(texto, Logger.LoggerLevels.WARNING);
    }

    @Override
    public void error(String texto) {
        this.sendLog(texto, Logger.LoggerLevels.ERROR);
    }

    @Override
    public void fatal(String texto) { this.sendLog(texto, Logger.LoggerLevels.FATAL); }


    ///////////////////////////// GUETTERS AND SETTERS /////////////////////////
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        filePath = this.getReformatedWithFormat("", "INFO", filePath);
        filePath = filePath.replaceAll(":", "");
        this.filePath = filePath;
        //check and create file and write headers
        File fichero = new File(filePath);
        if(fichero.exists() == false){

                //fichero.createNewFile();

                if(headersLine.trim().equalsIgnoreCase("") == false) {
                    writeInFile(headersLine);
                }

        }
    }

    private void writeInFile(String text){
        WriterDaemon demon = new WriterDaemon(filePath, text);
        demon.run();
    }

    public Character getSeparatorCharacter() {
        return separatorCharacter;
    }

    public void setSeparatorCharacter(Character separatorCharacter) {
        this.separatorCharacter = separatorCharacter;
    }

    public String getHeadersLine() {
        return headersLine;
    }

    public void setHeadersLine(String headersLine) {
        this.headersLine = headersLine;
    }
}
