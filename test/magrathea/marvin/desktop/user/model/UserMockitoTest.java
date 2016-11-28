package magrathea.marvin.desktop.user.model;

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

    @Test
    public void UserHasEditorRol() {
        User userMock = mock(User.class);
        when(userMock.getUserRole()).thenReturn("EDITOR");
        
        String result = userMock.getUserRole();

        assertThat(result, equalTo("EDITOR"));
    }
}
