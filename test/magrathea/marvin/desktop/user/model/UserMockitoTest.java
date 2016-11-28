package magrathea.marvin.desktop.user.model;

import magrathea.marvin.desktop.user.dao.UserRole;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author boscalent
 */
// Fake Test, only to prove mockito
// Here mockito isn't necessary, we have control of the code from User
// It's only a Test
public class UserMockitoTest {

    private User userMock;

    @Before
    public void setUp() {
        userMock = mock(User.class);                       // ARRANGE MOCK CLASS
    }

    @Test
    public void UserHasEditorRol() {
        when(userMock.getUserRole()).thenReturn("EDITOR");      // ARRANGE

        String result = userMock.getUserRole();                 // ACT

        assertThat(result, equalTo("EDITOR"));                  // ASSERT
    }

    @Test
    public void UserHasLegalRol() {
        // Return a different String
        when(userMock.getUserRole()).thenReturn("USER", "EDITOR", "ADMINISTRATOR");

        for (int i = 0; i < UserRole.values().length ; i++) {

            boolean result = false;
            for (UserRole userRole : UserRole.values()) {
                if (userRole.name().equalsIgnoreCase(userMock.getUserRole())) {
                    result = true;
                }
            }
            assertThat(result, is(true));

        }
    }
}
