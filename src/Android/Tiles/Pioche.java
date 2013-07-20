package Android.Tiles;


import com.myfirstapp.domino.DominoApplication;
import java.util.ArrayList;
import java.util.Random;

/** Define the stock **/
public class Pioche {

    /** ArrayList that contains all of the tiles  in the stack **/
    private ArrayList <Domino> p_content;

    /** A random number generator **/
    private Random r ;
    @SuppressWarnings("unused")
	private DominoApplication app;
    
    
    //Instance a stack, and fill in it with all 28 tiles
    public Pioche(DominoApplication app){
        
            p_content = new ArrayList<Domino>(28);
            this.app=app;
            
            for (int i=6;i>=0;i--) {
                for (int j=i;j>=0;j--) {
                	Domino d = new Domino(i,j,0,app);
                	Coord originCoord = new Coord(app.getLimite_xD()*1.5f, app.getLimite_down()/2f);
                	d.setFather(3); // Indique que le domino est dans la pioche
                	d.setCoord(originCoord);
                	d.setOriginCoord(originCoord);
                    p_content.add(d);
                }
            }
    }
    
    /**
     * Randomly draw a tile from the stack
     * @return A tile from the stack
     * @throws PiocheVideException This exception is thrown if the stack is found empty
     */
    public Domino get() throws PiocheVideException{
    	r = new Random();
    	
    	if (p_content.isEmpty()) {
    		throw new PiocheVideException();
    	}
    	else {
    		int i = r.nextInt(p_content.size());
    		
    		Domino d = p_content.get(i);
    		p_content.remove(i);
    		return d;
    	}
    }
    /**
     * Allows one to know whether the stack is empty
     * @return A boolean : true if the stack is empty
     */
    public boolean isEmpty() {	
        return p_content.isEmpty();
    }

    /**
     * Display the content of the stack
     * @return A String which show all of the tiles contained in the stack
     */
    @Override
    public String toString() { 
    	return p_content.toString();
    }
    
    public int getNbDominos() {
        return p_content.size();
    }
    
    public ArrayList<Domino> getContent() {
        return p_content;
    }
}