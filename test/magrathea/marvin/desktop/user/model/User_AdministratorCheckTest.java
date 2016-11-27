package magrathea.marvin.desktop.user.model;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author boscalent
 */
public class User_AdministratorCheckTest {

    private User user;

    @Before
    public void createUser() {
        user = new User();
    }

    @Test
    public void emptyCanNotBeAdministrator() {
        
        boolean result = user.isAdministrator();

        assertThat(result, equalTo(false));
    }
}
