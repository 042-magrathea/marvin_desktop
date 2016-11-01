package magrathea.marvin.desktop.app.dao.HTTPRequestDAO;

import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import magrathea.marvin.desktop.app.dao.DAO;

/**
 *
 * @author boscalent
 */
public class HTTPRequestDAO implements DAO {

    protected HttpURLConnection con;
    
    @Override
    public void setup() throws Exception {
        // No populated DB from here
    }

    @Override
    public void connect() throws Exception {
       // WebService with differents url 
    }
    
    public void connect(URL url, Proxy proxy ) throws Exception {
        if ( proxy == null ){proxy = Proxy.NO_PROXY;}           // direct con
        con = (HttpURLConnection) url.openConnection( proxy );  // default: No proxy        
    }

    @Override
    public void close() throws Exception {
        if ( con != null ){
            con.disconnect();
        }
    }
}
