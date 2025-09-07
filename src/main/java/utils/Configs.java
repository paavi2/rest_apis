package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configs {

    static Properties properties;

    public static Properties loadPropertiesFile(){
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            if(properties == null){
                properties = new Properties();
            }
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    public static String getProperty(String key){
        return loadPropertiesFile().getProperty(key);
    }
}
