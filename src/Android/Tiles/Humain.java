package Android.Tiles;

import com.myfirstapp.domino.MainActivity;
import java.util.Scanner;
/** This class is used for the interactions with the humans players **/
public class Humain extends Joueur {
	
	static Scanner scanner = new Scanner( System.in );

	/**Instanciates a human player
         * @param a The Referee of the game
         * @param nom Name of the human player **/
	public Humain(Arbitre a, String nom, MainActivity act) {
		super(a, nom, act);
	}
	/*
	 * Allows one to know whether the player is human or a computer	
         * @return String : "human" **/
	public String type() {
		return "Humain";
	}
	
        public void jeu() {}
        public void jeu_v2() {}
        /**
         * Defines the gamplay for human player 
         * @throws PiocheVideException The player needs to draw and the stock is empty
         * @throws MainJouableException The player wants to draw but the stock is not empty 
	 * 
         */
	public void jeu(int i, int j) throws PiocheVideException, MainJouableException, AucunCoupPossibleException, TileDoesntMatchException {
            super.content=arbitre.getMain();
		/* System.out.println("Main:" + super.content);
		arbitre.afficher_plateau();
		System.out.println("You may :\n0 draw\n1 put a tile to the right of the board\n2 put a tile to the left of the board");
		*/
            

		
		if(i == 0) {			//if i = 0, that mean that the player wishes to draw
			try {
				super.draw();
                         //       return true;
			}
			catch (PiocheVideException e) { //If the stack is empty, that means the player is unable to play
				throw new AucunCoupPossibleException(); 
			}
			catch (MainJouableException e) { //If the player can play, he must do so
				throw new MainJouableException();
			}
                }
                
                else if(i == 2) {
                 //   arbitre.afficher(""+content.get(j-1));
                   
                        super.addD(content.get(j));
                 
                }
                else if (i==1) {
                        super.addG(content.get(j));
                }
              //  return false;
        }
        
    public boolean isHuman() {
        return true;
    }
    public boolean isComputer() {
        return false;
    }
}


