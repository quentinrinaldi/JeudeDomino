package Android.Tiles;

import com.myfirstapp.domino.DominoApplication;
import com.myfirstapp.domino.MainActivity;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;

public class Arbitre {
	/** Stock of the game
	 * @see Pioche*/
	private Pioche pioche;
	/** Table of the game
	 * @see Plateau*/
	private Plateau plateau;
	/** List of players */
	private ArrayList<Joueur> players;
	/**List of the players Hand **/
	private ArrayList<ArrayList<Domino>> mains;
	/**Set to true when the first player is added, until the first turn**/
	private boolean firstPlay = true;
	/** This integer will refer to the index of the first tile that shall be played at the begining of the game **/ 
	private Domino first;
	/** Keeps track of whether or not the game is lauched **/
	private boolean gaming = false;
	/**
	 * Counter raised after each player's turn
	 */
	private int i=0; 
	private int b=0;
	@SuppressWarnings("unused")
	private int advId;

	private MainActivity act;
	private DominoApplication app;
	/**
	 * Instanciates a referee to oversee the game and links it to the stock and the board
	 * @param pioche
	 * @param plateau 
	 */
	public Arbitre(Pioche pioche, Plateau plateau, DominoApplication app) {
		// this.act=act;
		this.pioche=pioche;
		this.plateau=plateau;
		players=new ArrayList<Joueur>(2);
		mains=new ArrayList<ArrayList<Domino>>(2);
		this.app=app;
	}

	/**
	 * Adds a computer player in the game supervised by the current referee. After having joined the game, the computer receves his hand from the referee
	 * @param nom Name of the computer
	 * @return A new computer player
	 * @throws PiocheVideException If the stock is empty, a new player can't be added to the game
	 * @throws MainJouableException 
	 */
	public Ordinateur newPlayerO(String nom) throws PiocheVideException, MainJouableException, TooMuchPlayersException{
		Ordinateur j = new Ordinateur(this, nom, app);
		this.addJoueur(j);
		j.setId(i);
		if (i==0) {
			firstPlay=true;
		}
		try {
			for(int k = 0; k < 7; k++){ //drawing
				draw(); 
			}
		}
		catch (PiocheVideException e) {
			throw new TooMuchPlayersException (players.size());
		}
		i++;
		return j;
	}

	/**
	 * Adds a human player in the game supervised by the actual referee. After having joined the game, the human player receves his hand from the referee
	 * @param nom Name of the human player
	 * @return A new Human player
	 * @throws PiocheVideException If the stock is empty, a new player can't be added to the game
	 * @throws MainJouableException
	 */
	public Humain newPlayerH(String nom) throws PiocheVideException, MainJouableException, TooMuchPlayersException{
		Humain j = new Humain(this, nom, app);
		this.addJoueur(j);

		if (i==0) {
			firstPlay=true;
		}
		try {
			for(int k = 0; k < 7; k++){ //drawing
				draw(); 
			}
		}
		catch (PiocheVideException e) {
			throw new TooMuchPlayersException (players.size());
		}
		i++;
		return j;
	}



	/**
	 *  Returns the number of players currently involved in the game
	 * @return An integer which refers to the number of players.
	 */
	public int getNbJoueurs() {
		return players.size();
	}

	public ArrayList<Domino> getplayerhand(int n) {
		return mains.get(n);
	}

	public Joueur getCurrentPlayer() {
		return players.get(i);
	}

	/**
	 *  Returns the number of tiles the player has got in his hand
	 * @return  Number of tiles that the current player have
	 */
	public int getNbDominos() {        
		return (mains.get(i)).size();
	}

	/** Displays all player's hands
	 */
	public void afficher_mains() { 
		for (int k=0;k<mains.size();k++) {
			Log.d("Testapp","Joueur "+k+ " "+mains.get(k) + "\n");
		}
	}


	/**Display the index of the actual player */
	public void afficher_index() {
		System.out.println("test/ Joueur :" + i);
	}
	/**
	 * This is the main part of the game. The referee asks by turns the players to play. According to their hand, players may ask the referee to draw, to put a tile on the table, or to pass their turns. 
	 * Then, according to the table and the stock, the referee will decide what to do
	 * @throws PiocheVideException If the stock is empty, the referee will inform the player that he must pass his turn
	 * @throws MainJouableException If the play ask to draw despite being able to play, the referee will force his hand.
	 * @throws AucunCoupPossibleException  If the player is unable to play or draw, the referee will inform the player that he may skip his turn
	 * @throws PartieGagneException  Someone has won, end of the game
	 * @throws MatchNullException If no one has been able to play for one turn, the game ends in a draw
	 */
	public void jeu_auto() throws PiocheVideException, MainJouableException, AucunCoupPossibleException, PartieGagneException, MatchNullException, NotEnoughtPlayersException, TooMuchPlayersException, TileDoesntMatchException {
		if (players.size() < 2) {
			throw new NotEnoughtPlayersException(players.size());
		}

		i=0;
		gaming=true; // the game has begun and must go on
		b=0; // Keeps track of whether or not all the players has passed their turn
		afficher_mains();
		//    afficher_plateau();
		if (firstPlay) {    //first play
			first=init();
			(players.get(0)).jeu();
			firstPlay = false;

			i=-1; //Because the first play is out of the normal process, we have to know that the first player has already played.
		}
		while (gaming) {

			if (i==-1) {
				i=1; //Avoid that the first player plays twice. 
			}
			else {
				i=0;
			}
			b=0;
			while (i < players.size()) {
				try {
					//         afficher_plateau();
					System.out.println(i);
					(players.get(i)).jeu();
					b=1; // // The player has played a tile, he didn't passed his turn
					if (mains.get(i).isEmpty()) { // If his hand is empty, he wins
						gaming=false;
						throw new PartieGagneException(this.getCurrentPlayer());
					}
				}
				catch (AucunCoupPossibleException e) {          ////The player is unable to play
					System.out.println(players.get(i) +" "+ i+ " passe son tour");
					i++; //The game goes on
					continue;
				}
				i++;
			}

			if (b==0) { // if b=0 at the end of the loop, that means that no one put b at 1, so no one has managed to play
				//     afficher_plateau();
				afficher_mains();

				throw new MatchNullException();

			}

		}
	}
	// Ne marche que pour les ordinateurs (execution du jeu pas à pas)


	public void jeu() throws PiocheVideException, MainJouableException, AucunCoupPossibleException, PartieGagneException, MatchNullException, NotEnoughtPlayersException, TooMuchPlayersException, TileDoesntMatchException {


		try {
			int d = app.getDiff();
			if (d==0) {
				(players.get(i)).jeu();
			}
			else {
				(players.get(i)).jeu_v2();
			}
			b=0; // // The player has played a tile, he didn't passed his turn
			if (mains.get(i).isEmpty()) { // If his hand is empty, he wins
				gaming=false;

				throw new PartieGagneException(this.getCurrentPlayer());
			}


			i=(i+1)%players.size();
			this.refresh();
			app.stock_button();
		}
		catch (AucunCoupPossibleException e) {          ////The player is unable to play
			//System.out.println(players.get(i) +" "+ i+ " passe son tour");
			//The game goes on
			b++;
			if (b==2) {
				throw new MatchNullException();
			}
			i=(i+1)%players.size();
			app.stock_button();

		}


		catch (PartieGagneException e) {
			this.refresh();
			Log.d("Testapp", "test2!");
			throw new PartieGagneException(this.getCurrentPlayer());
		}



		//    afficher_main();
		//    afficher_plateau();

	}

	public void start() throws Exception{
		if (firstPlay) {
			i=0;
			first=init();
			gaming=true;
			if (players.get(0).isComputer()) {
				(players.get(0)).jeu();
				i=(i+1)%players.size();
				firstPlay=false;
				this.refresh();
			}
			else {
				app.stock_button();
			}
		}
	}

	public void jeu(int k, int j) throws PiocheVideException, MainJouableException, AucunCoupPossibleException, PartieGagneException, MatchNullException, NotEnoughtPlayersException, TooMuchPlayersException, TileDoesntMatchException {
		try {
			Log.d("Testapp","plop"+players.size()+"test "+i);
			players.get(i).jeu(k,j);

			b=0;
			if (firstPlay) {
				firstPlay=false;
			}

			app.fixHumanHand();
			if (this.mains.get(i).isEmpty()) {
				this.refresh();
				throw new PartieGagneException(this.getCurrentPlayer());
			}
			i=(i+1)%players.size();
			this.jeu();
		}

		catch (TileDoesntMatchException e1) 
		{
			throw new TileDoesntMatchException ();
		}
		catch (MainJouableException e2) {

		}
		catch (AucunCoupPossibleException e3) {
			b++;
			if (b==2) {
				throw new MatchNullException ();
			}
			i=(i+1)%players.size();
			this.jeu();
		}
		catch (PartieGagneException e) {
			Log.d("Testapp", "test4!");
			throw new PartieGagneException(this.getCurrentPlayer());
		}
	}

	/**
	 * Returns a copy of actual player's hand
	 * @return A copy of the ArrayList representing the hand of the actual player
	 */
	public ArrayList<Domino> getMain() 
	{ 
		return new ArrayList<Domino>(mains.get(i));
	}
	/**
	 * Add a new player to the ArrayList 'players'  
	 * @param j - New player to add
	 */
	public void addJoueur(Joueur j){
		//add the player to the arraylist
		players.add(j);
		j.setId(players.size()-1);

		//give an empty hand to the player
		mains.add(new ArrayList<Domino>(7));
	}

	/**Ask the bord whether the tile can be put at the right end of the table.  If it's possible this method places it, removes it from the player's hand and returns true, but if not returns false
	 * @param d Domino to add to the right of the table
	 * @return Boolean : True if the action is possible, false if it isn't **/
	public boolean placeD(Domino d){

		if (firstPlay == true) {
			if (first==d) {
				plateau.addD(d);
				mains.get(0).remove(d);
				return true;
			}
			else {
				return false;
			}
		}

		int res = d.exist(plateau.getD()); //Compares the tile to the value at the right end of the board

		if (res == 2) {
			d.swap(); //if it's necessary, it swaps the tile
		}

		if (res == 1 ||res == 3 || res == 2) {
			plateau.addD(d);
			(mains.get(i)).remove(d);
			return true;
		}
		if (res == -1) { // Just for JUNIT test
			plateau.addD(d);
			(mains.get(i)).remove(d);
			return false;
		}

		return false;
	}

	/**Ask the board whether the tile can be put at the left end of the board. If it's possible this method places it, removes it from the player's hand and returns true, if not returns false
	 * @param d Domino to add to the left of the table
	 * @return Boolean : True if the action is possible, false if it isn't **/
	public boolean placeG(Domino d) {

		if (firstPlay == true) {
			if (first==d) {
				plateau.addG(d);
				mains.get(0).remove(d);
				return true;
			}
			else {
				return false;
			}
		}

		int res = d.exist(plateau.getG()); //Compares the tile to the value of the left end of the table

		if(res == 1)
		{
			d.swap(); ////if it's necessary, it swaps the tile
		}

		if(res == 1 ||res == 3 || res == 2)
		{
			plateau.addG(d);
			(mains.get(i)).remove(d); // removes the tile from the player's hand
			return true;
		}
		if (res == -1) {        // Just for JUNIT test
			plateau.addG(d);
			(mains.get(i)).remove(d);
			return false;
		}

		return false;
	}
	/**
	 * Return a tile drawn from the stock
	 * @return Draws a tile from the stock, after having checked if the player could play or not
	 * @throws PiocheVideException Stock is empty
	 * @throws MainJouableException The current player was able to play a tile but asked to draw
	 */
	public Domino draw () throws PiocheVideException, MainJouableException{
		try {

			if ((gaming==true) && (app.getPiocheParam()==false)) {
				if (verifCoups()) {
					throw new MainJouableException();
				}
			}
			Domino d = pioche.get();
			if (players.get(i).isComputer()) {
				d.setR(9);
			}
			else {
				d.setFather(2);
			}
			(mains.get(i)).add(d);

			return d;
		}
		catch (PiocheVideException e) {
			throw new PiocheVideException();
		}

	}
	/**
	 * Check if there is a tile which can be played in the hand of the player
	 * @return Boolean : true if a tile is playable, false if it isn't
	 */
	public boolean verifCoups () {
		for (int k=0; k<mains.get(i).size();k++) {
			if ((mains.get(i).get(k).exist(plateau.getG()) != 0) || (mains.get(i).get(k).exist(plateau.getD()) != 0)) {
				return true;
			}
		}
		return false;
	}


	public Domino init() throws NotEnoughtPlayersException {
		if (players.size() < 2) {
			throw new NotEnoughtPlayersException(players.size());
		}
		int numJ=0;
		Domino firstD=mains.get(0).get(0);
		for (int k=0;k<players.size();k++) {
			for (int j=0; j<7; j++) {
				if (firstD.compareTo(mains.get(k).get(j), null) < 0) {
					firstD=mains.get(k).get(j);
					numJ=k;
				}
			}
		}
		// On échange l'ID des joueurs
		int old_id = players.get(0).getId();
		players.get(0).setId(players.get(numJ).getId());
		players.get(numJ).setId(old_id);

		//On échange leur place dans l'arraylist player
		// Joueur old_j=players.get(0);
		//players.set(0,players.get(numJ));
		//players.set(numJ,old_j);

		//On échange la place de leur mains pour qu'elles correspondent à nouveau
		//  ArrayList<Domino> old_main = mains.get(0);
		//mains.set(0, mains.get(numJ));
		//mains.set(numJ, old_main);

		Collections.swap(mains, 0, numJ);
		Collections.swap(players, 0, numJ);

		return firstD;
	}

	public void refresh() {
		app.refreshTv();
	}
	public void setAdvId(int id) {
		this.advId=id;
	}

	public void setPl(Plateau pl) {
		this.plateau=pl;
	}

	public int getplG() {
		return plateau.getG();
	}
	public int getplD() {
		return plateau.getD();
	}

	public boolean getFirstPlay() {
		return firstPlay;
	}



	public void setApp(DominoApplication app) {
		this.app=app;
	}


}
