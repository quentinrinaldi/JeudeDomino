package Android.Tiles;

public class PartieGagneException extends Exception {
    
    Joueur j;
    
	public PartieGagneException(Joueur j) {
		super("Bravo ! "+j.toString()+" a gagne !");
                this.j=j;
	}
        
        public Joueur getJoueur() {
            return j;
        }
}
