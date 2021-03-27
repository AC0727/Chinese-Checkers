import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * MainController
 * @author AdeleChen
 * conroller for the MainPage GUI
 */

public class MainController {
    @FXML private Slider players = new Slider();

    /**
     * changeToSelectColour
     * @param event is action that causes scene change
     * @throws IOException if file does not exist
     * changes scene to SelectColourGUI
     */
    public void changeToSelectColour(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GUI/SelectColourGUI.fxml"));
        Parent newFile = loader.load();
        Scene newFileScene = new Scene(newFile);

        ColourController controller = loader.getController();
        controller.initialize((int) players.getValue());

        //retrieve stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(newFileScene);
        window.show();

    }

}
