package magrathea.marvin.desktop.app.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point2D;

/**
 *
 * @author boscalent
 */

// Store app configuration;
public class Config {
    Properties prop; 
    OutputStream output = null;
    InputStream input = null;
    
    private String server = "http://marvin-server.duckdns.org/";
    private String dao;
    private int hostID;
    private int langID;
    private Point2D interfaceSize = new Point2D(1024, 768);
    private String DAOprovider = "";    // HTTPRequest, JavaDB
    
    private boolean NewUsersAuto = false;
    private boolean DeleteUsersAuto = true;
    
    
    public Config(){
        try {
            prop = new Properties();
            output = new FileOutputStream("config.properties");
            
            // Setting the properties value
            prop.setProperty("dao", dao);
            prop.setProperty("dao", dao);
            
            // save properties to project root folder
            prop.store(output, null);
 
        } catch (FileNotFoundException ex) {
            // File
        } catch (IOException ex) {
            // Store
        } finally {
            if (output != null){
                try {
                    output.close();
                } catch (IOException e){}
            }
        }
    }
    
    public boolean loadProperties(){
        try {
            input = new FileInputStream("config.properties");
            
            // load a properties file
            prop.load(input);
            
            // get the property value
            prop.getProperty("dao", "");
            
            
        } catch (FileNotFoundException ex) {
            //
        } catch (IOException ex) {
            //
        }
        
        return false;
    }
}
