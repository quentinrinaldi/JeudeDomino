package Android.Tiles;

import com.myfirstapp.domino.DominoApplication;
import android.graphics.Rect;



/** Represents a tile, divided in two parts : faceA and faceB **/
public class Domino { 
    
    /** faceA and faceB are the two parts of the tile **/
    private int faceA, faceB;
    private float x,y;
    private int rotate;
    private DominoApplication app;
    private int father;
    private boolean trans;
    /** Creates a tile whose parts are a and b
     * @param a First part of the tile
     * @param b Second part of the tile **/
    public Domino(int a, int b,int father, DominoApplication app)
    {
        
        
        this.faceA=a;
        this.faceB=b;
        this.x=-100;
        this.y=-100;
        this.rotate=0;
        this.app=app;
        this.father=father;
        this.trans=false;
       // this.image=BitmapFactory.decodeResource(act.getResources(),R.drawable.s);

    }
    /** Switch parts of the domino. The fist part become the second one, and the second one become the first one **/
    public void swap() {      
        int tmp=faceA;
        faceA=faceB;
        faceB=tmp;
    }
    
    /** Allows to get the value of the first part of the tile
     * @return The integer of the first part of the tile **/
    public int getA () { 
        return this.faceA;
    }
    
    
    /** Allows to get the value of the first part of the tile
    * @return The integer of the first part of the tile **/
    public int getB () {
        return this.faceB;
    }
    
    public int getId() {
        return Integer.parseInt(getA()+""+getB());

    }
   
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public void setX(float x) {
        if (this.father==1) {
            this.setTrans(true);
        }
        this.x=x;
    }
    
    public void setY(float y) {
        if (this.father==1) {
            this.setTrans(true);
        }
        this.y=y;
    }
    
    public int getR(){
        return this.rotate;
    }
    
    public void setR(int r) {
        this.rotate=r;
    }
    
     /** Allows to get the sum of the two parts of the tile
      * @return The integer of the sum of the two parts of the tile **/
    public int getSum() {
    	return this.faceA+this.faceB;
    }
    
    /** Allows to get the highest value of the tile
     * @return The integer of the highest value of the tile **/
    public int getMaxFace() {
    	if (faceA > faceB)
    		return faceA;
    	else
    		return faceB;
    }
    
    /**Allows to know whether the tile is a double or not
     * @return A boolean that indicate whether the tile is a double or not **/
    public boolean isDouble() {
    	return faceA==faceB;
    }
    
    /**Allows to know if a tile d is equal to this domino (the same parts)
     * @param d Tile which is compared to the actual tile
     * @return A boolean which indicates whether these tiles are equals or not **/
    public boolean equals(Domino d) {
    	return ((faceA==d.getA()) && faceB==d.getB());
    }
    
    /**Displays the tile
     * @return The string that represent the tile : |faceA - faceB| **/
    public String toString() {
    	return "|"+getA()+"-"+getB()+"|"+"(x:"+getX()+",y:"+getY();
    }
    
    
     /**Returns a number that indicates if an integer n is equal to one of the part of the tile
     * @return 3 if n is equal to the two parts of the tile, 2 if n is equal to is the face B, 1 if n is equal to the face A, then else 0
     * @param n The number which is compared to the tile values**/
    public int exist (int n){
    	if (n==-1) 
    		return n;
 
        if(n == faceA && n == faceB){
            return 3;
        } else if (n == faceB) {
            return 2;
        } else if (n == faceA) {
            return 1;
        }else {
            return 0;
        }
    }
    
    public void setCoord(Coord c) {
        this.setX(c.getX());
        this.setY(c.getY());
    }
    
    public Coord getCoord() {
        return new Coord(getX(),getY());
    }
    
    public boolean contains(Coord c) {
        Rect zone= new Rect ((int)getX(),(int)getY(),(int)getX()+app.getLongd(),(int)getY()+(1 * app.getLargd()));
        return zone.contains((int)c.getX(),(int)c.getY());
    }
    public boolean intersects(Rect r) {
        Rect zone= new Rect ((int)getX(),(int)getY(),(int)getX()+app.getLongd(),(int)getY()+app.getLargd());
        return Rect.intersects(zone, r);
    }
    
    public int getIdDouble() {
        return (Integer.parseInt(getA()+""+getB()))+100;
    }
    
    public int compareTo (Domino d, Arbitre a) {
        
        if (d.isDouble() && this.isDouble()) {
            if (this.getA() > d.getA()) {
                return 1;
            }
            else {
                return -1;
            }
        }
        
        else if (this.isDouble()) {
            return 1;
        }
        
        else if (d.isDouble()) {
            return -1;
        }
        else {
            if ((a != null)) {
                if ((this.exist(a.getplG()) != 0) && (this.exist(a.getplD()) != 0)) {
                 return 1;
                }
                else if ((d.exist(a.getplG()) != 0) && (d.exist(a.getplD()) != 0)) {
                    return -1;
                }
            }
        
            if (this.getSum() > d.getSum()) {
                return 1;
            }
            else if (this.getSum() < d.getSum()) {
                return -1;
            }
            else {
                if (this.getA() > d.getA()) {
                    return 1;
                }
                else {
                    return -1;
                }
            }       
        }
    }
    
    public void translate(float xfin, float yfin) {
        
        this.setTrans(true);
        this.setX(xfin);
        this.setY(yfin);
       
    }
    
    public int getFather() {
        return this.father;
    }
    
    public void setFather(int f) {
        this.father=f;
    }
    
    public boolean getTrans() {
        return trans;
    }
    
    public void setTrans(boolean trans) {
        this.trans=trans;
    }
}