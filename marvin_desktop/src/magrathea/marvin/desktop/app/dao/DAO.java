package magrathea.marvin.desktop.app.dao;

/**
 * @author Iván Cañizares Gómez
 * First class of the DAO system, 
 * Methods that every DAO provider need to implement
 */
public interface DAO {
    /**
     * Prepare all the stuff for do the job (include populate db...)
     * @throws Exception 
     */
    public void setup() throws Exception;
    
    // TODO: Make the connexion in a different thread of main (Android style)
    // Wait for a Reader (future) to populate views. 
    // !Derby don't need this. QueryRunner of Apache dbutils do these jobs.
    /** 
     * Do all stuff for connect to the provider. Different providers can need 
     * aux methods for do the Job, for example HTTPRequest don't persist the 
     * connection and implement an auxiliary method with parameters, but this is 
     * the starting point.
     * @throws Exception 
     */
    public void connect() throws Exception;
    
    /**
     * Close the connection
     * @throws Exception 
     */
    public void close() throws Exception;    
}
