package utils;

import java.io.*;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: cristobal
 * Date: 06/24/14
 * Time: 05:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurationManager {

    private String configFilePath;
    private Properties properties = new Properties();
    private boolean isXML;

    public ConfigurationManager(String configFilePath, boolean isXML) throws IOException {
        this.configFilePath = configFilePath;
        this.isXML = isXML;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(configFilePath);
            if (isXML) {
                properties.loadFromXML(fis);
            } else {
                properties.load(fis);
            }
        } catch (FileNotFoundException ex) {
            // creates the configuration file and set default properties
            setDefaults();
            save();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    private void setDefaults() {
        properties.put("host", "localhost");
        properties.put("port", "3306");
        properties.put("user", "root");
        properties.put("pass", "none123");
        properties.put("pallet", "lila");
        properties.put("log", "tiendadb.log");
    }

    public void save() throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(configFilePath);
            if (isXML) {
                properties.storeToXML(fos, "My Application Settings");
            } else {
                properties.store(fos, "My Application Settings");
            }
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}
