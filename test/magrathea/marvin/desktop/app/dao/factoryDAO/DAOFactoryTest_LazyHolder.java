package magrathea.marvin.desktop.app.dao.factoryDAO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author boscalent
 */
public class DAOFactoryTest_LazyHolder {
    
    public DAOFactoryTest_LazyHolder() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of get Instance Method
     * Switch case of DAO implementation from properties.
     * CASES: HTTPRequest & Derby. Cover from null and wrong value (?Load good defaults).
     * Mockito of Properties. Return values ("HTTPRequest", "Derby", "", null).
     */
    
    
}
