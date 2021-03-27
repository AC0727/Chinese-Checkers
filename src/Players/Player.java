package Players;

import javafx.scene.paint.Color;

/**
 * Player
 * @author AdeleChen
 * All the players in the game. It is an abstract class in case an AI wants to be implemented
 */

public abstract class Player {
    protected Color colour;

    public Player() {}

    public Player(Color colour) {
        this.colour = colour;
    }

    public Color getColour() {return colour;}

    public String getColourStr() {
        if(colour == Color.RED) {
            return "red";
        }
        else if(colour == Color.GREEN) {
            return "green";
        }
        else if(colour == Color.YELLOW) {
            return "yellow";
        }
        return null;
    }

}


