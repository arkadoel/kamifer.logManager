package kamifer.logManager.csv;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fer on 23/07/16.
 */
public class WriterDaemon extends Thread {
    private String texto = "";
    private String filePath = "";

    WriterDaemon(String path, String text){
        texto = text;
        filePath = path;
        setDaemon(true);
    }

    @Override
    public void run() {
        boolean escrito = false;

        while (escrito == false) {
            try {
                Path file = Paths.get(filePath);
                List<String> lines = Arrays.asList(texto);

                Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                escrito = true;
                //System.out.print("*");
            } catch (IOException e) {
                e.printStackTrace();
                escrito = false;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    //
                }
            }
        }

    }

}
