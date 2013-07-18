package Android.Tiles;

/*
 * The player doesn't have any domino which can be played considering the board
 */
public class AucunCoupPossibleException extends Exception {
	public AucunCoupPossibleException () {
		super("Aucun de vos domino ne vous permet de jouer");
	}

}
