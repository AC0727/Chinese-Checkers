import GameBoard.BoardPosition;
import GameBoard.BoardPositionState;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BoardPositionTest {
    private BoardPosition bp = new BoardPosition(BoardPositionState.EMPTY, 3, 2);

    @Test
    public void getCircle() {assertNotNull(bp.getCircle());}

    @Test
    public void getRow() {assertEquals(3, bp.getArrayRow());}

    @Test
    public void getIndex() {assertEquals(2, bp.getArrayIndex());}

    @Test
    public void getState() {
        assertEquals(BoardPositionState.EMPTY, bp.getState());
    }

    @Test
    public void setState() {
        bp.setState(BoardPositionState.YELLOW);

        assertEquals(BoardPositionState.YELLOW, bp.getState());
        assertEquals("0xffff00ff", bp.getCircle().getFill().toString());
    }



}
