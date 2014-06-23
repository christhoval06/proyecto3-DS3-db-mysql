package utils;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Christhoval on 3/6/14.
 */
public class Logger {

    private FileWriter fw;
    private String logFile;
    private boolean debug = false;

    public Logger(String lf){
       logFile = lf;
        createFile();
    }

    public Logger(String lf, boolean d) {
        debug = d;
        logFile = lf;
        createFile();
    }

    private boolean createFile(){
        try{
            if(debug) fw = new FileWriter(logFile, true);
            return true;
        } catch (Exception e) {
            System.out.println("error");
        }
        return false;
    }

    public void log(String s){
        try{
            if(debug) {
                if (createFile()) {
                    fw.write((new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")).format(new Date()) + " : " + s + "\n");
                    fw.close();
                }
            }
        } catch (Exception e) {
            System.out.println("error");
        }
    }
}