package GameBoard;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * BoardPosition
 * @author AdeleChen
 *  BoardPosition is a singular part of the board. Depending on its state, it can either be empty or a game piece
 */

public class BoardPosition {
    private Circle circle;
    private BoardPositionState state;
    private int row;
    private int index;


    public BoardPosition(BoardPositionState state, int row, int index) {
        this.row = row;
        this.index = index;
        this.state = state;

        circle = new Circle();
        circle.setRadius(10);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
    }


    public Circle getCircle() {return circle;}

    public BoardPositionState getState() {return state;}

    public int getArrayRow() {return row;}

    public int getArrayIndex() {return index;}


    /**
     * setGUI
     * @param x is x position of circle
     * @param y is y position of circle
     * Sets up position of circle in GUI
     */
    public void setGUI(int x, int y) {
        circle.setCenterX(x);
        circle.setCenterY(y);
    }

    /**
     * setState
     * @param state is new state of BoardPosition
     * sets state and updates colour of cricle for BoardPosition
     */
    public void setState(BoardPositionState state) {
        this.state = state;

        if(state == BoardPositionState.RED) {
            circle.setFill(Color.RED);
        }
        else if(state == BoardPositionState.GREEN) {
            circle.setFill(Color.GREEN);
        }
        else if(state == BoardPositionState.YELLOW) {
            circle.setFill(Color.YELLOW);
        }
        else {
            circle.setFill(Color.WHITE);
        }


    }


}
