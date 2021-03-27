import GameBoard.Board;
import GameBoard.BoardPositionState;
import Players.Player;
import Players.PlayerList;
import Players.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * GameController
 * @author AdeleChen
 * controller for the Game GUI
 */
public class GameController {
    private Board board = Board.getInstance();
    private PlayerList players = PlayerList.getInstance();

    private ArrayList<String> coloursStrArr;
    private Circle currentPosition;
    private Circle newPosition;
    private int clicks = 0;
    private int playerCounter = 0;

    @FXML private AnchorPane boardPane = new AnchorPane();
    @FXML private Label turnText = new Label();
    @FXML private Button restart = new Button();


    /**
     * initialize
     * @param playersColours is the user's choice of colour
     * initializes the game GUI
     */
    public void initialize(ArrayList<String> playersColours) {
        this.coloursStrArr = playersColours;

        //set up backend of game
        createPlayer(playersColours);
        board.setUp();

        //set up GUI for game
        addCircles();
        getSelected();
        turnText.setText("Turn: " + players.getPlayer(0).getColourStr());

        //start game
        setPaneEvent();
    }

    /**
     * restart
     * initializes the scene is the game restarts
     */
    public void restart() {
        //set up backend of game
        createPlayer(coloursStrArr);
        board.setUp();

        //set up GUI for game
        addCircles();
        getSelected();
        turnText.setText("Turn: " + players.getPlayer(0).getColourStr());

        //start game
        setPaneEvent();
    }


    /**
     * addCircles
     * adds BoardPosition circles to GUI
     */
    private void addCircles() {
        for(int row = 0; row < board.getBoardRows(); row++) {
            for(int index = 0; index < board.getBoardRowLength(row); index++) {
                boardPane.getChildren().add(board.getBoardPosition(row, index).getCircle());

            }
        }
    }


    /**
     * displayWin
     * @param player is player who won
     * updates scene if scene won
     */
    private void displayWin(Player player) {
        turnText.setText(player.getColourStr() + " wins!");
        restart.setVisible(true);
    }


    /**
     * quit
     * @param event is the action causing scene change
     * @throws IOException is file does not exist
     * Changes back to the main menu
     */
    public void quit(ActionEvent event) throws IOException {
        Parent newFile = FXMLLoader.load(getClass().getResource("GUI/MainMenu.fxml"));
        Scene newFileScene = new Scene(newFile);

        //retrieve stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newFileScene);
        window.show();
    }



    /**
     * createPlayer
     * creates and adds players to PlayerList
     */
    private void createPlayer(ArrayList<String> playersColours) {

        for(String colourStr : playersColours) { //for every colour choice
            Color pieceColour = returnColour(colourStr);

            if(pieceColour != null) { //if user has chosen a colour
                    User user = new User(pieceColour); //create new user
                    players.addPlayer(user);
            }
        }
    }

    /**
     * returnColour
     * @param colourStr is value from combo box
     * @return Color version of user input
     */
    private Color returnColour(String colourStr) {
        switch(colourStr.toLowerCase()) {
            case "green": return Color.GREEN;
            case "yellow": return  Color.YELLOW;
            case "red": return Color.RED;
        }

        return null;
    }



    /**
     * game
     * moves the game piece the user chooses and switches turns
     */
    private void game(Player player) {

        if(clicks == 2) { //if 2 circles have been chosen
            //if player has chosen their own piece and it is a valid move
            if(convertToState(player.getColour()) == board.getBoardPosition(currentPosition).getState() && board.validMove(board.getBoardPosition(currentPosition), board.getBoardPosition(newPosition))) {

                board.movePiece(board.getBoardPosition(currentPosition), board.getBoardPosition(newPosition));

                if(board.win(player)) { //if player won, finish game
                    displayWin(player);
                }
                else {
                    setUpNextTurn();
                }

            }

            else {
                clicks = 0;
                currentPosition = null;
                newPosition = null;
            }

        }
    }

    /**
     * setUpNextTurn
     * sets up turn for next player
     */
    private void setUpNextTurn() {
        //reset turn
        clicks = 0;
        currentPosition = null;
        newPosition = null;

        //set up next player's turn
        if(playerCounter == players.length() - 1) {
            turnText.setText("Turn: " + players.getPlayer(0).getColourStr());
            playerCounter = 0;
        }
        else {
            playerCounter++;
            turnText.setText("Turn: " + players.getPlayer(playerCounter).getColourStr());
        }
    }




    /**
     * getSelected
     * sets the event handler for every board position
     */
    private void getSelected() {
        for(int row = 0; row < board.getBoardRows(); row++) {
            for(int index = 0; index < board.getBoardRowLength(row); index++) {
                Circle selected = board.getBoardPosition(row, index).getCircle();

                selected.setOnMouseClicked(
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if(currentPosition == null) {
                                currentPosition = selected;
                            }
                            else {
                                newPosition = selected;
                            }

                            clicks++;
                        }
                    }
                );

            }
        }
    }

    /**
     * setPaneEvent
     * sets up boardPane's event handler
     */
    private void setPaneEvent() {
        boardPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                game(players.getPlayer(playerCounter));
            }
        });
    }




    /**
     * convertToState
     * @param colour is colour that will be converted
     * @return BoardPositionState equivalent of colour
     */
    private BoardPositionState convertToState(Color colour) {
        if(colour == Color.YELLOW) {
            return BoardPositionState.YELLOW;
        }
        else if(colour == Color.RED) {
            return BoardPositionState.RED;
        }
        else if(colour == Color.GREEN) {
            return  BoardPositionState.GREEN;
        }

        return null;

    }


}
