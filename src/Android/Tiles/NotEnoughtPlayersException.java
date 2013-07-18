package Android.Tiles;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Quentin
 */
public class NotEnoughtPlayersException extends Exception {
    public NotEnoughtPlayersException(int n) {
        super("This game need more than "+n+ "player (at least two)");
    }
}
