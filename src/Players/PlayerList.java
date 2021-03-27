package Players;

import javafx.scene.paint.Color;

import java.util.ArrayList;


/**
 * PlayerList
 * @author AdeleChen
 * The list of players currently playing the game
 */

public class PlayerList {
    private static PlayerList singleton;
    private ArrayList<Player> players = new ArrayList<>();

    private PlayerList() {}


    public static PlayerList getInstance() {
        if (singleton == null) {
            singleton = new PlayerList();
        }
        return singleton;
    }


    public int length() {return players.size();}


    public Player getPlayer(int index) {return players.get(index);}


    /**
     * addPlayer
     * @param player is the player being added
     * adds a player to the playerList
     */
    public void addPlayer(Player player) {
        if(players.size() < 3 && !duplicates(player.getColour())) { //max of 3 players, no duplicate colours
            players.add(player);
        }

    }


    /**
     * duplicates
     * @param colour is colour that is being checked for duplicates
     * @return whether or not another player is already that colour
     */
    private boolean duplicates(Color colour) {
        for(Player player : players) {
            if(player.getColour().equals(colour)) {
                return true;
            }
        }

        return false;
    }


    public static void main(String[] args) {
        PlayerList pl = PlayerList.getInstance();
        pl.addPlayer(new User(Color.RED));
        pl.addPlayer(new User(Color.GREEN));
        System.out.println(pl.length());

        for(int i = 0; i < pl.length(); i++) {
            System.out.println(pl.getPlayer(i).getColourStr());
        }
    }


}
