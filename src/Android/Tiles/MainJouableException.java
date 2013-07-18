package Android.Tiles;

public class MainJouableException extends Exception {
	public MainJouableException () {
		super("Error : you possess tile(s) which allows you to play, so don't draw !");
	}
}
