import Players.PlayerList;
import Players.User;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PlayerListTest {
    private PlayerList list = PlayerList.getInstance();

    @Before
    public void setUp() {list.addPlayer(new User(Color.RED));}

    @Test
    public void getInstance() {assertNotNull(list);}

    @Test
    public void length() {assertEquals(1, list.length());}

    @Test
    public void addPlayer() {
        User user = new User(Color.GREEN);
        list.addPlayer(user);

        assertEquals(user, list.getPlayer(list.length() - 1));

    }

    @Test
    public void getPlayer() {
        for(int i = 0; i < list.length(); i++) {
            assertNotNull(list.getPlayer(i));
        }
    }

}
