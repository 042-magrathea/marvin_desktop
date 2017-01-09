/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.host.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author boscalent
 */
public class HostTest {
    
    public HostTest() {
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
     * Test of toString method, of class Host.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Host instance = new Host();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class Host.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Host instance = new Host();
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Host.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        Host instance = new Host();
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class Host.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        long id = 0L;
        Host instance = new Host();
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Host.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        Host instance = new Host();
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLatitude method, of class Host.
     */
    @Test
    public void testSetLatitude() {
        System.out.println("setLatitude");
        float latitude = 0.0F;
        Host instance = new Host();
        instance.setLatitude(latitude);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLongitude method, of class Host.
     */
    @Test
    public void testSetLongitude() {
        System.out.println("setLongitude");
        float longitude = 0.0F;
        Host instance = new Host();
        instance.setLongitude(longitude);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPhone method, of class Host.
     */
    @Test
    public void testSetPhone() {
        System.out.println("setPhone");
        String phone = "";
        Host instance = new Host();
        instance.setPhone(phone);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAddress method, of class Host.
     */
    @Test
    public void testSetAddress() {
        System.out.println("setAddress");
        String address = "";
        Host instance = new Host();
        instance.setAddress(address);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEmail method, of class Host.
     */
    @Test
    public void testSeteMail() {
        System.out.println("seteMail");
        String eMail = "";
        Host instance = new Host();
        instance.setEmail(eMail);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class Host.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Host instance = new Host();
        long expResult = 0L;
        long result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class Host.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Host instance = new Host();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLatitude method, of class Host.
     */
    @Test
    public void testGetLatitude() {
        System.out.println("getLatitude");
        Host instance = new Host();
        float expResult = 0.0F;
        float result = instance.getLatitude();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLongitude method, of class Host.
     */
    @Test
    public void testGetLongitude() {
        System.out.println("getLongitude");
        Host instance = new Host();
        float expResult = 0.0F;
        float result = instance.getLongitude();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPhone method, of class Host.
     */
    @Test
    public void testGetPhone() {
        System.out.println("getPhone");
        Host instance = new Host();
        String expResult = "";
        String result = instance.getPhone();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAddress method, of class Host.
     */
    @Test
    public void testGetAddress() {
        System.out.println("getAddress");
        Host instance = new Host();
        String expResult = "";
        String result = instance.getAddress();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEmail method, of class Host.
     */
    @Test
    public void testGeteMail() {
        System.out.println("geteMail");
        Host instance = new Host();
        String expResult = "";
        String result = instance.getEmail();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
