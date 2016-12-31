package magrathea.marvin.desktop.app.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author boscalent
 */
// Store app configuration;
/**
 * @see:
 * http://howtodoinjava.com/core-java/io/java-loadreadwrite-properties-file-examples/
 * @see:http://java-properties-editor.com/index.html
 * @see: http://plugins.netbeans.org/plugin/63739/multiproperties Properties
 * editor, Netbeans plugin.
 * @author boscalent
 */
public class MarvinConfig {

    private final String PROPERTIES_PATH
            = "magrathea/marvin/desktop/app/model/marvin.properties";
    private final String PROPERTIES_PATH_INNER
            = "marvin.properties";
    private final String DEFAULTS_PROPERTIES_PATH
            = "magrathea/marvin/desktop/app/model/marvin_defaults.properties";
    private final String DEFAULTS_LANG_PATH
            = "magrathea/marvin/desktop/app/view/i18n/marvin_i18n.properties";
    private Properties properties = null;
    private FileWriter fw = null;

    private MarvinConfig() {
        try {
            // Read properties from file
            properties = getPropertiesFromFile(PROPERTIES_PATH);
        } catch (Exception ex) {
            try {
                properties = getPropertiesFromFile(DEFAULTS_PROPERTIES_PATH);
            } catch (IOException e) {
                // nothing to do
            }
        }
    }

    // Bill Pugh singleton pattern ////////////
    private static class LazyHolder {

        private static final MarvinConfig INSTANCE = new MarvinConfig();
    }

    public static MarvinConfig getInstance() {
        return LazyHolder.INSTANCE;
    }
    //////////////////////////////////////////////

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public Set<String> getAllProperyNames() {
        return properties.stringPropertyNames();
    }

    public boolean constainsKey(String key) {
        return properties.contains(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    /**
     * Reload properties with good defaults
     */
    /*private void setDefaultsProperties() {
        try {
            storePropertiesInToFile(
                    getPropertiesFromFile(DEFAULTS_PROPERTIES_PATH), PROPERTIES_PATH);
        } catch (IOException ex) {
            Logger.getLogger(MarvinConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

    /**
     * Aux method to load properties
     */
    
    public Properties getPropertiesFromFile(String path) throws IOException {
        // Read properties from file
        Properties props = new Properties();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(path)) {
            props.load(in);
        }
        return props;
    }
    
    private void storePropertiesInToFile() {
        // Store properties in file
        //File f = new File(path);
        //Date date = new Date();
        //try (OutputStream out = new FileOutputStream(f)) {
        //props.store(out, "MARVIN PROPERTIES");
    }
    
    // Interface for ConfigController
    public void storeProps(){}
}
