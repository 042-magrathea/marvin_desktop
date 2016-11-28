/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.user.model;

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
public class UserTest {
    
    public UserTest() {
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
     * Test of hashCode method, of class User.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        User instance = new User();
        int expResult = 0;
        int result = instance.hashCode();
        assertNotEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        User instance = new User();
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void sameNickNameAndEmailIsEqual() {
        User user1 = new User();
        user1.setEmail("magrathea@marvin.es");
        user1.setNickname("Magrathea");
        user1.setId(0);
        
        User user2 = new User();
        user2.setEmail("magrathea@marvin.es");
        user2.setNickname("Magrathea");
        user2.setId(1);

        boolean expResult = true;
        boolean result = user1.equals(user2);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class User.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        User instance = new User();
        String expResult = null;
        String result = instance.toString();
        assertNotEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class User.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        User instance = new User();
        long expResult = 0L;
        long result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class User.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        long id = 0L;
        User instance = new User();
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getNickname method, of class User.
     */
    @Test
    public void testGetNickname() {
        System.out.println("getNickname");
        User instance = new User();
        String expResult = null;
        String result = instance.getNickname();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setNickname method, of class User.
     */
    @Test
    public void testSetNickname() {
        System.out.println("setNickname");
        String nickname = "";
        User instance = new User();
        instance.setNickname(nickname);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getPassword method, of class User.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        User instance = new User();
        String expResult = null;
        String result = instance.getPassword();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setPassword method, of class User.
     */
    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "";
        User instance = new User();
        instance.setPassword(password);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getEmail method, of class User.
     */
    @Test
    public void testGetEmail() {
        System.out.println("getEmail");
        User instance = new User();
        String expResult = null;
        String result = instance.getEmail();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setEmail method, of class User.
     */
    @Test
    public void testSetEmail() {
        System.out.println("setEmail");
        String email = "";
        User instance = new User();
        instance.setEmail(email);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of isAdministrator method, of class User.
     */
    @Test
    public void testIsAdministrator() {
        System.out.println("isAdministrator");
        User instance = new User();
        boolean expResult = false;
        boolean result = instance.isAdministrator();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setAdministrator method, of class User.
     */
    @Test
    public void testSetAdministrator() {
        System.out.println("setAdministrator");
        boolean administrator = false;
        User instance = new User();
        instance.setAdministrator(administrator);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class User.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        User instance = new User();
        String expResult = null;
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class User.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        User instance = new User();
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getPhone method, of class User.
     */
    @Test
    public void testGetPhone() {
        System.out.println("getPhone");
        User instance = new User();
        String expResult = null;
        String result = instance.getPhone();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setPhone method, of class User.
     */
    @Test
    public void testSetPhone() {
        System.out.println("setPhone");
        String phone = "";
        User instance = new User();
        instance.setPhone(phone);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getAds method, of class User.
     */
    @Test
    public void testGetAds() {
        System.out.println("getAds");
        User instance = new User();
        boolean expResult = false;
        boolean result = instance.getAds();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setAds method, of class User.
     */
    @Test
    public void testSetAds() {
        System.out.println("setAds");
        boolean ads = false;
        User instance = new User();
        instance.setAds(ads);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getPrivateDes method, of class User.
     */
    @Test
    public void testGetPrivateDes() {
        System.out.println("getPrivateDes");
        User instance = new User();
        String expResult = null;
        String result = instance.getPrivateDes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setPrivateDes method, of class User.
     */
    @Test
    public void testSetPrivateDes() {
        System.out.println("setPrivateDes");
        String privateDes = "";
        User instance = new User();
        instance.setPrivateDes(privateDes);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getPublicDes method, of class User.
     */
    @Test
    public void testGetPublicDes() {
        System.out.println("getPublicDes");
        User instance = new User();
        String expResult = null;
        String result = instance.getPublicDes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setPublicDes method, of class User.
     */
    @Test
    public void testSetPublicDes() {
        System.out.println("setPublicDes");
        String publicDes = "";
        User instance = new User();
        instance.setPublicDes(publicDes);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getUserRole method, of class User.
     */
    @Test
    public void testGetUserRole() {
        System.out.println("getUserRole");
        User instance = new User();
        String expResult = null;
        String result = instance.getUserRole();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setUserRole method, of class User.
     */
    @Test
    public void testSetUserRole() {
        System.out.println("setUserRole");
        String userRole = null;
        User instance = new User();
        instance.setUserRole(userRole);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getLanguage method, of class User.
     */
    @Test
    public void testGetLanguage() {
        System.out.println("getLanguage");
        User instance = new User();
        String expResult = null;
        String result = instance.getLanguage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setLanguage method, of class User.
     */
    @Test
    public void testSetLanguage() {
        System.out.println("setLanguage");
        String language = "";
        User instance = new User();
        instance.setLanguage(language);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getDatePassword method, of class User.
     */
    @Test
    public void testGetDatePassword() {
        System.out.println("getDatePassword");
        User instance = new User();
        String expResult = null;
        String result = instance.getDatePassword();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setDatePassword method, of class User.
     */
    @Test
    public void testSetDatePassword() {
        System.out.println("setDatePassword");
        String datePassword = "";
        User instance = new User();
        instance.setDatePassword(datePassword);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getMemberSince method, of class User.
     */
    @Test
    public void testGetMemberSince() {
        System.out.println("getMemberSince");
        User instance = new User();
        String expResult = null;
        String result = instance.getMemberSince();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setMemberSince method, of class User.
     */
    @Test
    public void testSetMemberSince() {
        System.out.println("setMemberSince");
        String memberSince = "";
        User instance = new User();
        instance.setMemberSince(memberSince);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
