package magrathea.marvin.desktop.app.dao.HTTPRequestDAO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import magrathea.marvin.desktop.app.dao.DAO;

/**
 * Concrete implementation of DAO for a PHP server WebService 
 * @author Iván Cañizares Gómez
 */
public class HTTPRequestDAO implements DAO {

    protected HttpURLConnection con;
    
    @Override
    public void setup() throws Exception { /* No populated DB from here */ }

    /**
     * WebService with differents URL
     * @see Connect to a server Address + phpfile
     * @throws Exception 
     */
    @Override
    public void connect() throws Exception {}
    
    // TODO: Refactor for single responsibility principle: One method for 
    // construct the request and other for do the connection, make a connection 
    // in a diferent thread and wait for return a Reader (future).
    /**
     * Aux method specific for POST request nature.
     * Make the header of the message, adds the parameters of the query and do 
     * the connection, wait for a response in a Reader object that return.
     * @param url
     * @param proxy
     * @param postDataBytes
     * @return
     * @throws Exception 
     */
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