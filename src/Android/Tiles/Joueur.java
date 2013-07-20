package Android.Tiles;

import com.myfirstapp.domino.DominoApplication;
import com.myfirstapp.domino.MainActivity;
import java.util.ArrayList;

/** Represent a player, with his hand of tiles. He is described by a name, and can only interact with the referee 
 * @see Humain and Ordinateur **/
public abstract class Joueur {
    
    protected ArrayList <Domino> content; //Main du joueur
    protected Arbitre arbitre;
    protected String nom;
    protected DominoApplication app;
    protected int id;
    
   /** Generates a player whose name is "nom" and who is linked to the referee "arbitre"
    * @param arbitre Referee linked to this player
    * @param nom Name of the player
    **/
    public Joueur(Arbitre arbitre, String nom, DominoApplication app) {
        content = new ArrayList<Domino>(7);
        this.nom=nom;
        this.arbitre=arbitre;
        this.app=app;
        this.id=-1;
    }
    
    
     /**Defines the player's strategy if it's a computer, or contains method which allows players to interact with this game (gameplay) 
     * @throws PiocheVideException
     * @throws MainJouableException
     * @throws AucunCoupPossibleException 
     * @see Human and Ordinateur */
    public abstract void jeu() throws PiocheVideException, MainJouableException, AucunCoupPossibleException, TileDoesntMatchException;
    public abstract void jeu_v2() throws PiocheVideException, MainJouableException, AucunCoupPossibleException, TileDoesntMatchException;
    
    /**Draw a tile from the stock
     * @see Arbitre and Pioche **/
    public void draw() throws PiocheVideException, MainJouableException{
    	content.add(arbitre.draw());
    }
    
    /**Return True if tile d can be put to the right end of the board, or false
     * @param d Domino that the player wants on the right end of the board
     * @return a boolean that reveal if this action was successful or not **/
    public void addD(Domino d) throws TileDoesntMatchException 
    {
        if(arbitre.placeD(d)) {
            content.remove(d);
           // return true;
        }
        else {
        	throw new TileDoesntMatchException ();
        }
   
    }
    
     /**Returns True if tile d can be put to the left of the board, or false
     * @param d Domino that the player wants on the left end of the board
     * @return a boolean that reveal if this action was successful or not **/
    public void addG(Domino d) throws TileDoesntMatchException 
    {
        if (arbitre.placeG(d)) {
            content.remove(d);
        	//return true;        
        }
        else {
          //  return false;
            throw new TileDoesntMatchException ();
        }
    }

    /** Finds in the player's hand the highest double-value tile
     * @return Return an Integer Array which first element is the index of the hihest double-value tile, and second element is it's value. 
     * If no double tile was found, this array contain {-1,-1}  **/
    public int[] maxDouble() {
    	int[] max ={-1, -1};  // [incice du domino, valeur]
    
    	for (int i=0;i<content.size();i++) {
    		if ((content.get(i).isDouble()) && (content.get(i).getA() > max[1])) {
    			max[1]=content.get(i).getA();
    			max[0]=i;
    		}
    	}
    	return max;
    }
    
    /** Finds in the payer's hand the highest no-double tile. The value of a non-double tile is defined by the sum of its two parts.
     * If two tiles have the same sum, the highest tile is the one which have the greatest number.
     * @return Return and integer array, whose first value is the index of the tile found in the player's hand.
     * The second one is the sum of the two parts of the tile (its value)
     * The third one is the greatest number of the tile **/
    public int[] maxSum() {
    	int[] max ={-1, -1, -1};			//max=[indice, somme, poids]
    	
    	for (int i=0; i<content.size(); i++) {
    		if (content.get(i).getSum() == max[1]) {
    			if (content.get(i).getMaxFace() > max[2]) {
    				max[2]=content.get(i).getMaxFace();
    				max[0]=i;
    			}
    			
    		}
    		else if (content.get(i).getSum() > max[1]) {
    			max[1] = content.get(i).getSum();
    			max[0]=i;
    			max[2]=content.get(i).getMaxFace();    			
    		}
    	}
    	return max;
    }
    

    /** Name of player
     * @return Return the string of the name of player
       **/
    @Override
    public String toString() { //Renvoie le nom du joueur
        return nom;
    }

    /** Asks the referee to refresh his hand (this is a security measure, because the referee is sure to have the reals hands) **/
  
    /** Returns a String representing the player's hand
     * @return A string of player's hand **/
    public String afficher_main() {
        return (""+content);
    }
    /**
     * Returns the number of tiles in the player's hand
     * @return A integer representing the number of tiles in the player's hand
     */
    public int getNbDominos() {
        return content.size();
    }
    
    public abstract String type ();
    public abstract void jeu(int i, int j)throws PiocheVideException, MainJouableException, AucunCoupPossibleException, TileDoesntMatchException ;


    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id=id;
    }
    
    public abstract boolean isHuman();
    public abstract boolean isComputer();
    
}
