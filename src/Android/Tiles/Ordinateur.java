package Android.Tiles;

import com.myfirstapp.domino.DominoApplication;
import com.myfirstapp.domino.MainActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/** This class define the way the computer plays **/
public class Ordinateur extends Joueur{
    
    /**Instanciate a computer, linked to the referee arbitre, and whose name is nom
     * @param arbitre The referee linked to this player
     * @param nom Name of this player **/
    public Ordinateur (Arbitre arbitre, String nom, DominoApplication app) {
        super(arbitre, nom, app);
    }
    
    public ArrayList<Domino> getMain () {
    	return arbitre.getMain();
    }
    
    /** Define the strategy of this computer: presently this is a blind strategy, the computer will try and play each and every one of his tiles until one works or he finds himself out of tiles and is required to draw**/
    @Override
    public void jeu() throws PiocheVideException, MainJouableException, AucunCoupPossibleException {
    	Log.d("testapp","jeu simple");
    	super.content=this.getMain();
   // 	System.out.println("main Ordinateur :" +super.content); //Show the player's hand
    	int size = arbitre.getNbDominos();
    	for (int i = 0; (i < size); i++) {
              /** try to play a tile until it works **/
    		try {
                    super.addG(super.content.get(i));
                    break;
                }
                catch (TileDoesntMatchException e1) {
                    try {
                        super.addD(super.content.get(i));
                       // arbitre.refresh();
                        break;
                    }
                    catch (TileDoesntMatchException e2) {
                        continue;
                    }
                }
        }
             
        
    	if(size == arbitre.getNbDominos()) //if no tile was played, it means that the player has to draw
    	{ 
    		try {
    			super.draw();		//drawing
    			app.fixComputerHand();
    			
    		}
    		catch (PiocheVideException e) {	// If the stock is empty, the player has to pass
    			throw new AucunCoupPossibleException();
    			
    		}
    	}
    //	app.fixComputerHand();
    }
    
    public void jeu_v2() throws PiocheVideException, MainJouableException, AucunCoupPossibleException {
    	Log.d("testapp","jeu compliquŽ");
    	boolean actionDone = false;
        super.content=arbitre.getMain();
        Collections.sort(content,new Comparator<Domino>() {
            public int compare(Domino d1, Domino d2) {
                return d1.compareTo(d2,arbitre);
            }
        });
        Collections.reverse(content);
        int i=0;
        while ((i<content.size()) && (content.get(i).isDouble())) {
                try {
                    super.addG(super.content.get(i));
                    actionDone=true;
                    break;
                }
                catch (TileDoesntMatchException e1) {
                    try {
                        super.addD(super.content.get(i));
                        actionDone=true;
                        break;
                    }
                    catch (TileDoesntMatchException e2) {
                        i++;
                        continue;
                    }
                }
        }
        if ((i<content.size()) && (actionDone == false)) {
            int plG=arbitre.getplG();
            int plD=arbitre.getplD();
            Domino dspec = content.get(i);
            if ((dspec.exist(plG) != 0) && (dspec.exist(plD) != 0)) {
                
                int[] tab=new int[2];
                tab[0]=0;
                tab[1]=0;
                for (Domino d :content) {
                    if (d != dspec) {
                        if (d.exist(plG) != 0) {
                            tab[0]++;
                        }
                        else if (d.exist(plD) != 0) {
                            tab[1]++;
                        }
                    }
                }
                if (tab[0] > tab[1]) {
                    try {
                    super.addD(dspec);
                    }
                    catch (Exception e) {
                        Log.d("Testapp", "Erreur !");
                    }
                }
                else {
                    try {
                    super.addG(dspec);
                    }
                    catch (Exception e) {
                        Log.d("Testapp","Erreur !");
                    }
                }
            }
            else {
                this.jeu();
            }
        }
        else {
            if (actionDone == false) {
                this.jeu();
            }
        }
      //fixComputerHand();
      }
    
    /** Return a String which indicate that the player is a computer
     * @return A String : "Machine" **/
    public String type() {
    	return "Machine";
    }
    public void jeu (int i, int j) {
    }
    
    public boolean isHuman() {
        return false;
    }
    public boolean isComputer() {
        return true;
    }
}