package GameBoard;

import Players.Player;
import Players.PlayerList;
import Players.User;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Board
 * @author AdeleChen
 * Holds the 2D array that acts as the board and the game logic
 */

public class Board {
    private static Board singleton;
    private PlayerList players = PlayerList.getInstance();

    private BoardPosition[][] board = new BoardPosition[13][]; //13 rows, length varies
    private int[] boardRows =  {1, 2, 3, 10, 9, 8, 7, 8, 9, 10, 3, 2, 1};

    private ArrayList<BoardPosition> possibleMoves = new ArrayList<>();

    private Board() {
        setUpRows();
    }

    /**
     * setUpRows
     * sets up length of rows
     */
    private void setUpRows() {
        for(int i = 0; i < 13; i++) { //set up length of each row
            board[i] = new BoardPosition[boardRows[i]];
        }
    }

    public static Board getInstance() {
        if (singleton == null) {
            singleton = new Board();
        }
        return singleton;
    }




    public int getBoardRows() {return board.length;}


    public int getBoardRowLength(int row) {return  board[row].length;}


    public BoardPosition getBoardPosition(int row, int index) {return board[row][index];}


    /**
     * getBoardPosition
     * @return the BoardPosition based on the circle the user chose
     */
    public BoardPosition getBoardPosition(Circle circle) {
        int circleX = (int) circle.getCenterX(); //coordinates of circle
        int circleY = (int) circle.getCenterY();

        for(int row = 0; row < board.length; row++) {
            for(BoardPosition pos : board[row]) {

                int posX = (int) pos.getCircle().getCenterX(); //coordinates for circle of BoardPosition
                int posY = (int) pos.getCircle().getCenterY();

                if(circleX == posX && circleY == posY) {
                    return pos;
                }

            }
        }

        return null;
    }




    /***
     * setUp
     * sets up the board
     */
    public void setUp() { //must add other BoardPositions
        for(int row = 0; row < boardRows.length; row++) { //set up all board positions as empty
            for(int index = 0; index < board[row].length; index++) {
                board[row][index] = new BoardPosition(BoardPositionState.EMPTY, row, index);
            }
        }


        addPlayerPieces();
        setGUILayout();
    }


    /**
     * addPlayerPieces
     * adds all game pieces to the board
     */
    private void addPlayerPieces() {
        for(int playerIndex = 0; playerIndex < players.length(); playerIndex++) {
            Color colour = players.getPlayer(playerIndex).getColour();

            //set up the pieces
            if(colour == Color.RED) {

                for(int bottom = 3; bottom < 7; bottom++) { //fill the bottom row of the red pieces
                    board[9][bottom].setState(BoardPositionState.RED);
                }

                for(int row = 10; row < 13; row++) { //fill the rest of the red pieces
                    for(BoardPosition boardPos : board[row]) {
                        boardPos.setState(BoardPositionState.RED);
                    }
                }

            }

            else if(colour == Color.YELLOW) {

                for(int row = 3; row < 7; row++) { //set up yellow game pieces
                    for(int index = 0; index < board[row].length - 6; index++) {
                        board[row][index].setState(BoardPositionState.YELLOW);
                    }
                }

            }

            else if(colour == Color.GREEN) {

                for(int row = 3; row < 7; row++) { //set up green game pieces
                    for(int index = board[row].length - 1; index > 5; index--) {
                        board[row][index].setState(BoardPositionState.GREEN);
                    }
                }

            }

        }
    }


    /**
     * setGUILayout
     * Sets the GUI coordinates for the BoardPosition circles
     */
    private void setGUILayout() {
        int initX = 350;
        int initY = 15;

        for(int row = 0; row < board.length; row++) {
            int y = initY + (35 * row); //y of BoardPosition
            int x = initX - (20 * (board[row].length - 1)); //x of first BoardPosition in row

            for(BoardPosition pos : board[row]) {
                pos.setGUI(x, y);
                x = x + 40;
            }
        }
    }




    /**
     * movePiece
     * @param oldPos is where the piece used to be
     * @param newPos is new position as chosen by user
     * If it is a legal move, user can move piece to adjacent space
     */
    public void movePiece(BoardPosition oldPos, BoardPosition newPos) {

        BoardPositionState newState = oldPos.getState();
        newPos.setState(newState);

        oldPos.setState(BoardPositionState.EMPTY);

        possibleMoves.clear();

    }


    /**
     * validMove
     * @param oldPos chosen game piece
     * @param newPos is new BoardPosition of piece chosen by user
     * @return whether or not it is a valid move
     */
    public boolean validMove(BoardPosition oldPos, BoardPosition newPos) {
        possibleSlides(oldPos);
        possibleHops(oldPos);

        return  possibleMoves.contains(newPos);
    }






    /**
     * possibleSlides
     * @param pos is piece chosen by user
     * Gets all possible slides of piece
     */
    private void possibleSlides(BoardPosition pos) {
        HashSet<BoardPosition> neighbours = neighbouringBP(pos);

        for(BoardPosition neighbour : neighbours) {
            if(neighbour.getState() == BoardPositionState.EMPTY) {
                possibleMoves.add(neighbour);
            }
        }
    }





    /**
     * possibleHops
     * @param pos is the game piece
     * Gets all possible BoardPositions that the piece can hop to
     */
    private void possibleHops(BoardPosition pos) {
        HashSet<BoardPosition> neighbours = neighbouringBP(pos);

        for(BoardPosition neighbour : neighbours) {

            if(neighbour.getState() != BoardPositionState.EMPTY) {
                BoardPosition jump = getHop(neighbour, pos);

                //if the jump is empty, add it to possible moves and use recursion with jump
                if(jump != null) {
                    if(!possibleMoves.contains(jump) && jump.getState() == BoardPositionState.EMPTY) {

                        possibleMoves.add(jump);

                        possibleHops(jump); //find all possible jumps from the current position
                    }
                }

            }
        }
    }


    /**
     * getHop
     * @param neighbour is BoardPosition neighbouring the piece
     * @param originalPos is the game piece
     * @return the supposed jump over the neighbour
     */
    private BoardPosition getHop(BoardPosition neighbour, BoardPosition originalPos) {
        //position of piece
        int ogRow = originalPos.getArrayRow();
        int ogIndex = originalPos.getArrayIndex();

        //position of neighbour
        int nRow = neighbour.getArrayRow();
        int nIndex = neighbour.getArrayIndex();

        try {
            //if on the same row
            if(ogRow == nRow) {

                if(ogIndex < nIndex) {
                    return board[ogRow][nIndex + 1]; //jump to right
                }
                else if(ogIndex > nIndex){
                    return board[ogRow][nIndex - 1];
                }
            }

            //if jump is going up on board
            else if(ogRow > nRow) {
               return  diagonalHops(ogRow, nRow - 1, ogIndex, nIndex);
            }
            //jumping is going down on board
            return diagonalHops(ogRow, nRow + 1, ogIndex, nIndex);



        }
        catch(Exception e) {
            System.err.println(e);
        }

        return null;
    }


    /**
     * diagonalHops
     * @param ogRow is original row of piece
     * @param jumpRow is row that piece is jumping to
     * @param ogIndex is original index of piece
     * @param nIndex is index of neighbouring piece (NOT index of jumpRow)
     * @return the diagonal BoardPosition that the piece jumps to
     */
    private BoardPosition diagonalHops(int ogRow, int jumpRow, int ogIndex, int nIndex) {
        int jumpRowL = board[jumpRow].length;
        int ogRowL = board[ogRow].length;

        if(jumpRowL == ogRowL) { //if piece jumps over middle of board

            //determine direction of jump
            if(ogIndex == nIndex) {
                return board[jumpRow][nIndex + 1];
            }
            else if(ogIndex > nIndex) {
                return board[jumpRow][nIndex];
            }

        }

        else if(ogRowL < jumpRowL) { //jumping towards a larger row
            return largerDiagonalHops(ogRowL, jumpRow, ogIndex, nIndex);
        }

        else { //jumping towards a smaller row
            return smallerDiagonalHops(jumpRow, ogIndex, nIndex, jumpRowL);
        }

        return null;
    }


    /**
     * smallerDiagonalHops
     * @param jumpRow is row piece is jumping to
     * @param ogIndex is original row of piece
     * @param nIndex is neighbour BoardPosition's index
     * @param jumpRowL is length of jump row
     * @return New location of piece if jumping towards a smaller row
     */
    private BoardPosition smallerDiagonalHops(int jumpRow, int ogIndex, int nIndex, int jumpRowL) {
        //drastic change in row length
        if(jumpRowL == 3) {

            if(ogIndex == nIndex) { //jumping to the left
                return board[jumpRow][nIndex - 4];
            }
            else if(ogIndex == nIndex - 1) { //jumping to the right
                return board[jumpRow][nIndex - 3];
            }
        }

        //drastic change in row length
        else if(jumpRowL == 2 && ogIndex - 3 == nIndex) {
            return board[jumpRow][nIndex];
        }

        else if(ogIndex == nIndex) {
            return board[jumpRow][nIndex];
        }

        return board[jumpRow][nIndex - 1];

    }


    /**
     * largerDiagonalHops
     * @param ogRowL is original row length
     * @param jumpRow is row piece is jumping to
     * @param ogIndex is original row of piece
     * @param nIndex is neighbour BoardPosition's index
     * @return New location of piece if jumping towards a larger row
     */
    private BoardPosition largerDiagonalHops(int ogRowL, int jumpRow, int ogIndex, int nIndex){
        //drastic change in row length
        if(ogRowL == 3) {

            if(ogIndex == nIndex - 3) { //jumping to the left
                return board[jumpRow][nIndex - 1];
            }
            else if(ogIndex == nIndex - 4) { //jumping to the right
                return board[jumpRow][nIndex];
            }

        }
        //drastic change in row length
        else if(ogRowL == 2) {

            if(ogIndex == nIndex) { //jumping to the left
                return board[jumpRow][nIndex + 3];
            }
            else if(ogIndex == nIndex - 1) { //jumping to the right
                return board[jumpRow][nIndex + 4];
            }

        }
        else if(ogIndex == nIndex) {
            return board[jumpRow][nIndex];
        }

        return board[jumpRow][nIndex + 1];
    }





    /**
     * neighbouringBP
     * @param pos is the game piece
     * @return all neighbouring BoardPositions of piece
     */
    private HashSet<BoardPosition> neighbouringBP(BoardPosition pos) {
        HashSet<BoardPosition> neighbours = new HashSet<>(6);

        int posRow = pos.getArrayRow();
        int posIndex = pos.getArrayIndex();

        //adding the sides
        try {
            neighbours.add(board[posRow][posIndex - 1]);
        }
        catch (Exception e) { //catches the error if the piece is on the edge
            System.err.println(e);
        }

        try {
            neighbours.add(board[posRow][posIndex + 1]);
        }
        catch (Exception e) {
            System.err.println(e);
        }

       addDiagonals(posRow, posIndex, neighbours);

        return  neighbours;
    }


    /**
     * addDiagonal
     * @param posRow is row of BoardPosition
     * @param posIndex is index of BoardPosition
     * @param neighbours is the HashSet with all the neighbours
     * Adds the diagonal neighbours to the HashSet
     */
    private void addDiagonals(int posRow, int posIndex, HashSet<BoardPosition> neighbours) {
        //adding the diagonals
        int rowBelow = posRow - 1;
        int rowAbove = posRow + 1;

        if(rowBelow >= 0) {
            diagonals(posRow, rowBelow, posIndex, neighbours);
        }


        if(rowAbove < board.length) {
            diagonals(posRow, rowAbove, posIndex, neighbours);
        }

    }


    /**
     * diagonals
     * @param posRow is original row of piece
     * @param newRow is chosen row for new position
     * @param posIndex is original index of piece
     * Checks for diagonal positions of moving piece
     */
    private void diagonals(int posRow, int newRow, int posIndex, HashSet<BoardPosition> neighbours) {
        //depending on length of newRow in comparison to posRow, diagonal indexes vary
        try {
            if(posIndex == 0/* && (posRow != 2 && posRow != 10)*/) { //for left side of board
                if((posRow != 2 && newRow != 3) || (posRow != 10 && newRow != 9)) { //special case
                    neighbours.add(board[newRow][0]);
                }
            }

            if(board[posRow].length > board[newRow].length) {

                if(board[newRow].length == 3) { //if the length of the rows change drastically, going from main body to red

                    if(posIndex == 3) {
                        neighbours.add(board[newRow][0]);
                    }
                    else {
                        neighbours.add(board[newRow][posIndex - 4]);

                        if(posIndex != 6) {
                            neighbours.add(board[newRow][posIndex - 3]);
                        }
                    }

                }
                else {
                    neighbours.add(board[newRow][posIndex - 1]);
                }

            }

            else {

                if(board[posRow].length == 3) { //if the length of the rows change drastically
                    neighbours.add(board[newRow][posIndex + 3]);
                    neighbours.add(board[newRow][posIndex + 4]);
                }
                else {
                    neighbours.add(board[newRow][posIndex + 1]);
                }

            }

            neighbours.add(board[newRow][posIndex]);

        }
        catch (Exception e) {
            System.err.println(e);
        }
    }





    /**
     * win
     * @param player the player who's turn it is
     * @return whether or not the player has won
     */
    public boolean win(Player player) {
        boolean win = true;

        if(player.getColour() == Color.RED) {

            for(int row = 0; row < 3; row++) { //checking top 3 rows
                for(BoardPosition pos : board[row]) {
                    if(pos.getState() != BoardPositionState.RED) { // if red piece is not located, return false
                        win = false;
                    }
                }
            }

            for(int index = 3; index < 7; index++) { //checking 4th row
                if(board[3][index].getState() != BoardPositionState.RED) {
                    win = false;
                }
            }
        }



        else if(player.getColour() == Color.YELLOW) {

            for(int row = 7; row < 11; row++) { //checking all four rows
                for(int index = board[row].length - 1; index > 5; index--) {
                    if(board[row][index].getState() != BoardPositionState.YELLOW) { // if yellow piece is not located, return false
                        win = false;
                    }
                }
            }

        }

        else if(player.getColour() == Color.GREEN) {

            for(int row = 7; row < 11; row++) { //checking all four rows
                for(int index = 0; index < board[row].length - 6; index++) {
                    if(board[row][index].getState() != BoardPositionState.GREEN) { // if green piece is not located, return false
                        win = false;
                    }
                }
            }

        }




        return win;
    }




    public static void main(String[] args) {
        PlayerList plist = PlayerList.getInstance();
        plist.addPlayer(new User(Color.RED));
        plist.addPlayer(new User(Color.YELLOW));
        plist.addPlayer(new User(Color.GREEN));

        Board b = Board.getInstance();
        b.setUp();
        //b.movePiece(b.getBoardPosition(10, 2), b.getBoardPosition(8, 4));
        //b.movePiece(b.getBoardPosition(8, 4), b.getBoardPosition(10, 2));
        //b.movePiece(b.getBoardPosition(9, 3), b.getBoardPosition(7, 3));
        //b.movePiece(b.getBoardPosition(8, 3), b.getBoardPosition(6, 3));


        for(int i = 0; i < b.getBoardRows(); i++) {
            for(int x = 0; x < b.getBoardRowLength(i); x++) {
                System.out.print(b.getBoardPosition(i, x).getState() + " ");
            }
            System.out.println();
        }

    }
}
