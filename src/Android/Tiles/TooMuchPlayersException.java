package Android.Tiles;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Quentin
 */
public class TooMuchPlayersException extends Exception {
    public TooMuchPlayersException (int n) {
        super("There are too much players ! (there are "+n+" players, max = 4)");
    }
}
