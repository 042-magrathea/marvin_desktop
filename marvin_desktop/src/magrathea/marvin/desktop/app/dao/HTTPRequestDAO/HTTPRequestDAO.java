package magrathea.marvin.desktop.app.dao.HTTPRequestDAO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
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
    
    public Reader connect(URL url, Proxy proxy, byte[] postDataBytes) throws Exception {
        if ( proxy == null ){proxy = Proxy.NO_PROXY;}           // direct con
        con = (HttpURLConnection) url.openConnection( proxy );  // default: No proxy
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Length",String.valueOf(postDataBytes.length));
        con.setDoOutput(true);
        con.getOutputStream().write(postDataBytes);
        Reader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        return in;
    }

    @Override
    public void close() throws Exception {
        if ( con != null ){
            con.disconnect();
        }
    }
}
