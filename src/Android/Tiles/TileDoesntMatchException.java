/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Android.Tiles;

/**
 *
 * @author Quentin
 */
public class TileDoesntMatchException extends Exception {
    
    public TileDoesntMatchException () {
        super("Le domino ne correspond pas à l'extremité sélectionné");
    }
    
}
