package magrathea.marvin.desktop.user.model;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author boscalent
 */
public class User_UserRoleTest {
    private User user;
    private String role;

    @Before
    public void setUp() {
        user = new User();
        role = user.getUserRole();
    }
    
    @Test(expected=NullPointerException.class)
    public void emptyCanNotHaveAdministratorRol() {

        boolean result = role.equalsIgnoreCase("ADMINISTRATOR");

        assertThat(result, equalTo(false));
    }

    @Test(expected=NullPointerException.class)
    public void emptyCanNotHaveEditorRol() {

        boolean result = role.equalsIgnoreCase("EDITOR");

        assertThat(result, equalTo(false));
    }
    
    @Test(expected=NullPointerException.class)
    public void emptyCanNotHaveEmptyRol() {

        boolean result = role.equalsIgnoreCase("");

        assertThat(result, equalTo(false));
    }
    
}