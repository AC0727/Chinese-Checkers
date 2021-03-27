import Players.User;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    private User user;

    @Before
    public void setUp() {
        user = new User(Color.RED);
    }


    @Test
    public void getColour() {
        assertEquals(Color.RED, user.getColour());
    }

    @Test
    public void getColourStr() {
        assertEquals("red", user.getColourStr());
    }
}
