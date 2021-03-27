import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * ColourController
 * @author AdeleChen
 * controller for the selectColour GUI
 */

public class ColourController {
    @FXML private AnchorPane colourPane = new AnchorPane();
    @FXML private ComboBox<String> playerOne = new ComboBox<>();

    private ArrayList<ComboBox<String>> playersColours = new ArrayList<>(); //to keep track of the colours
    private ObservableList<String> colours = FXCollections.observableArrayList("Green", "Yellow", "Red");

    private int labelX = 25;
    private int comboX = 125;
    private int Y = 0;


    /**
     * initialize
     * @param numPlayers is the number of playersColours
     * initializes the GUI
     */
    public void initialize(int numPlayers) {
        playerOne.getItems().addAll(colours);
        playersColours.add(playerOne); //add the extra player's combo box to the playersColours list

        setColours(numPlayers);
    }

    /**
     * setColours
     * @param numPlayers is the number of playersColours
     * sets up the GUI so that all users can choose their game piece colour
     */
    private void setColours(int numPlayers) {

        for(int i = 1; i < numPlayers; i++) {
            int num = i + 1;
            Label playerStr = new Label("Player " + num); //set up label
            playerStr.setFont(Font.font("Arial", FontWeight.BOLD, 20));

            ComboBox<String> newPlayer = new ComboBox<>(); //set up combo box
            newPlayer.getItems().addAll(colours);

            //set up location of the two
            playerStr.setLayoutX(labelX);
            playerStr.setLayoutY(Y + (150 * i));

            newPlayer.setLayoutX(comboX);
            newPlayer.setLayoutY(Y + (150 * i));

            colourPane.getChildren().addAll(playerStr, newPlayer);
            playersColours.add(newPlayer);
        }

    }


    /**
     * changeToGame
     * Changes to the Game GUI
     * @param event is event being handled by user
     * @throws IOException in case file does not exist
     */
    public void changeToGame(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GUI/GameGUI.fxml"));
        Parent newFile = loader.load();
        Scene newFileScene = new Scene(newFile);

        GameController controller = loader.getController();
        controller.initialize(getColoursStr());

        //retrieve stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(newFileScene);
        window.show();
    }


    /**
     * changeToMenu
     * @param event is action causing scene change
     * @throws IOException if file does not exist
     * Changes back to main page
     */
    public void changeToMenu(ActionEvent event) throws  IOException{
        Parent newFile = FXMLLoader.load(getClass().getResource("GUI/MainMenu.fxml"));
        Scene newFileScene = new Scene(newFile);

        //retrieve stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newFileScene);
        window.show();
    }


    /**
     * getColourStr
     * @return arraylist of player's colours in strong form
     */
    private ArrayList<String> getColoursStr() {
        ArrayList<String> coloursStr = new ArrayList<>();

        for(ComboBox<String> col: playersColours) {
            coloursStr.add(col.getValue());
        }

        return  coloursStr;
    }

}
