import GameBoard.Board;
import Players.PlayerList;
import Players.User;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest {
    private Board board = Board.getInstance();
    private PlayerList players = PlayerList.getInstance();

    @Before
    public void setUpTests() {
        players.addPlayer(new User(Color.GREEN));
        players.addPlayer(new User(Color.RED));
        players.addPlayer(new User(Color.YELLOW));


        board.setUp();
    }

    @Test
    public void getInstance() {
        assertNotNull(board);
    }

    @Test
    public void setUp() {

        String setUpModel = "EMPTY \n" +
                "EMPTY EMPTY \n" +
                "EMPTY EMPTY EMPTY \n" +
                "YELLOW YELLOW YELLOW YELLOW EMPTY EMPTY GREEN GREEN GREEN GREEN \n" +
                "YELLOW YELLOW YELLOW EMPTY EMPTY EMPTY GREEN GREEN GREEN \n" +
                "YELLOW YELLOW EMPTY EMPTY EMPTY EMPTY GREEN GREEN \n" +
                "YELLOW EMPTY EMPTY EMPTY EMPTY EMPTY GREEN \n" +
                "EMPTY EMPTY EMPTY EMPTY EMPTY EMPTY EMPTY EMPTY \n" +
                "EMPTY EMPTY EMPTY EMPTY EMPTY EMPTY EMPTY EMPTY EMPTY \n" +
                "EMPTY EMPTY EMPTY RED RED RED RED EMPTY EMPTY EMPTY \n" +
                "RED RED RED \n" +
                "RED RED \n" +
                "RED \n";

        String setUp = "";

        for(int i = 0; i < board.getBoardRows(); i++) {
            for(int x = 0; x < board.getBoardRowLength(i); x++) {
                setUp += board.getBoardPosition(i, x).getState() + " ";
            }
            setUp += "\n";
        }

        assertEquals(setUpModel, setUp);

    }

    @Test
    public void validMove() {
        boolean hop = board.validMove(board.getBoardPosition(10, 0), board.getBoardPosition(8, 2));
        boolean slide = board.validMove(board.getBoardPosition(4, 6), board.getBoardPosition(5, 5));
        boolean invalid = board.validMove(board.getBoardPosition(5, 0), board.getBoardPosition(6, 0));

        assertTrue(hop);
        assertTrue(slide);
        assertFalse(invalid);
    }


    @Test
    public void move() {
        board.movePiece(board.getBoardPosition(9, 3), board.getBoardPosition(8, 3));
        board.movePiece(board.getBoardPosition(11, 0), board.getBoardPosition(7, 3));

        board.movePiece(board.getBoardPosition(6, 0), board.getBoardPosition(7, 1));

        String model = "EMPTY \n" +
                "EMPTY EMPTY \n" +
                "EMPTY EMPTY EMPTY \n" +
                "YELLOW YELLOW YELLOW YELLOW EMPTY EMPTY GREEN GREEN GREEN GREEN \n" +
                "YELLOW YELLOW YELLOW EMPTY EMPTY EMPTY GREEN GREEN GREEN \n" +
                "YELLOW YELLOW EMPTY EMPTY EMPTY EMPTY GREEN GREEN \n" +
                "EMPTY EMPTY EMPTY EMPTY EMPTY EMPTY GREEN \n" +
                "EMPTY YELLOW EMPTY RED EMPTY EMPTY EMPTY EMPTY \n" +
                "EMPTY EMPTY EMPTY RED EMPTY EMPTY EMPTY EMPTY EMPTY \n" +
                "EMPTY EMPTY EMPTY EMPTY RED RED RED EMPTY EMPTY EMPTY \n" +
                "RED RED RED \n" +
                "EMPTY RED \n" +
                "RED \n";



        String moves = "";

        for(int i = 0; i < board.getBoardRows(); i++) {
            for(int x = 0; x < board.getBoardRowLength(i); x++) {
                moves += board.getBoardPosition(i, x).getState() + " ";
            }
            moves += "\n";
        }

        assertEquals(model, moves);
    }


    @Test
    public void win() {
        board.movePiece(board.getBoardPosition(6, 6), board.getBoardPosition(6, 5));
        board.movePiece(board.getBoardPosition(4, 8), board.getBoardPosition(6, 4));
        board.movePiece(board.getBoardPosition(5, 7), board.getBoardPosition(7, 4));
        board.movePiece(board.getBoardPosition(7, 4), board.getBoardPosition(7, 3));
        board.movePiece(board.getBoardPosition(3, 7), board.getBoardPosition(7, 2));
        board.movePiece(board.getBoardPosition(3,9), board.getBoardPosition(7,4));
        board.movePiece(board.getBoardPosition(7,3), board.getBoardPosition(7,1));
        board.movePiece(board.getBoardPosition(7,4), board.getBoardPosition(7,3));
        board.movePiece(board.getBoardPosition(6,5), board.getBoardPosition(5,5));
        board.movePiece(board.getBoardPosition(4,7), board.getBoardPosition(8,3));
        board.movePiece(board.getBoardPosition(3,8), board.getBoardPosition(4,7));
        board.movePiece(board.getBoardPosition(8,3), board.getBoardPosition(6,1));
        board.movePiece(board.getBoardPosition(4,7), board.getBoardPosition(8,3));
        board.movePiece(board.getBoardPosition(6,4), board.getBoardPosition(7,4));
        board.movePiece(board.getBoardPosition(4,6), board.getBoardPosition(8,2));
        board.movePiece(board.getBoardPosition(3,6), board.getBoardPosition(4,6));
        board.movePiece(board.getBoardPosition(7,2), board.getBoardPosition(9,2));
        board.movePiece(board.getBoardPosition(7,3), board.getBoardPosition(9,3));
        board.movePiece(board.getBoardPosition(8,3), board.getBoardPosition(8,1));
        board.movePiece(board.getBoardPosition(4,6), board.getBoardPosition(8,4));
        board.movePiece(board.getBoardPosition(5,5), board.getBoardPosition(6,5));
        board.movePiece(board.getBoardPosition(5,6), board.getBoardPosition(7,3));
        board.movePiece(board.getBoardPosition(6,5), board.getBoardPosition(7,5));
        board.movePiece(board.getBoardPosition(7,3), board.getBoardPosition(7,2));
        board.movePiece(board.getBoardPosition(7,5), board.getBoardPosition(7,3));
        board.movePiece(board.getBoardPosition(8,4), board.getBoardPosition(8,3));
        board.movePiece(board.getBoardPosition(8,2), board.getBoardPosition(8,0));
        board.movePiece(board.getBoardPosition(8,3), board.getBoardPosition(8,2));
        board.movePiece(board.getBoardPosition(7,2), board.getBoardPosition(7,0));
        board.movePiece(board.getBoardPosition(6,1), board.getBoardPosition(6,0));

        board.movePiece(board.getBoardPosition(9,3), board.getBoardPosition(9,1));
        board.movePiece(board.getBoardPosition(7,0), board.getBoardPosition(9,0));
        board.movePiece(board.getBoardPosition(7,4), board.getBoardPosition(7,0));
        board.movePiece(board.getBoardPosition(7,3), board.getBoardPosition(8,3));
        board.movePiece(board.getBoardPosition(8,3), board.getBoardPosition(9,3));

        assertTrue(board.win(players.getPlayer(0)));

    }
}
