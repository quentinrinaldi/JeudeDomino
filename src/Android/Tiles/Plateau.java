package Android.Tiles;

import android.graphics.Rect;
import android.util.Log;
import java.util.ArrayList;

import com.myfirstapp.domino.DominoApplication;
import com.myfirstapp.domino.TilesPlayerView;

/** This class represent the board of the game
 * @author Jonathan and Quentin **/
public class Plateau {
    /**
     * ArrayList which contain all tiles on the board*/
    private ArrayList <Domino> p_content;
    private int ilongd,limite_down,longd, largd ;
    final private int limite_xG, limite_xD, limite_up, centerX, centerY;
    private boolean transG,transD;
    private int dxG,dxD,nbG,nbD;
    private DominoApplication app;
    public boolean isPlayingAtRight;
    public boolean isPlayingAtLeft;
    public Domino dominoBeingPlaced;
    private boolean sizeIsCorrect;
    
    /** Instance a board **/
    public Plateau (DominoApplication app) {
    	this.app=app;
    	
        p_content= new ArrayList<Domino>(28);

        limite_xG=app.getLimite_xG();
        limite_xD=app.getLimite_xD();
        
        longd=app.getLongd();
        largd=app.getLargd();
        limite_up=app.getLimite_up();
        limite_down=app.getLimite_down();
       
        this.centerX=(int)((limite_xD)*(1.0)/(2.0));
        this.centerY=(int)((limite_down)*(1.0)/(2.0));
        transG=false;
        transD=false;
        ilongd=0;
        dxG=-1;
        dxD=1;
        nbG=-1;
        nbD=0;
        
        sizeIsCorrect=true;
        
        dominoBeingPlaced=null;
        isPlayingAtRight=false;
        isPlayingAtLeft=false;
        Log.d("testapp", "plateau crée ! centerX : "+centerX + "centerY: "+ centerY +"longd : "+longd+" largd:"+ largd + "adresse: "+ super.toString());
        
        
        
        
    }
    
    public String adress() {
    	return "adress : "+ super.toString();
    }
    
    /**
     * Put a domino at the left end of the board
     * @param d Domino which will be put on the left of the board
     */
    public void addG(Domino d) {
    	Log.d("testapp", "ajout de "+d+" a gauche");
    	isPlayingAtRight=false;
        isPlayingAtLeft=true;
        dominoBeingPlaced=d;
    	Log.d("testapp", "longd:" +longd + "largd: "+ largd);
        fixposG(d);
        p_content.add(0, d);    //The domino is put at the begining of the ArrayList p_content
        nbG++;
        isPlayingAtRight=false;
        isPlayingAtLeft=false;
        dominoBeingPlaced=null;
    }
    
    /**
     * Put a domino at the right end of the board
     * @param d Domino which will be put at the right of the board
     */
    public void addD(Domino d) { //The domino is put at the end of the ArrayList p_content   	  Domino-->void
    	Log.d("testapp", "ajout de "+d+" a droite");
    	isPlayingAtRight=true;
        isPlayingAtLeft=false;
        dominoBeingPlaced=d;
    	fixposD(d);
        p_content.add(d);
        nbD++;
        isPlayingAtRight=false;
        isPlayingAtLeft=false;
        dominoBeingPlaced=null;
    }
    
    
    /**
     * Get the number of the right face of the tile at the right of the board
     * @return The integer of the right face of the tile at the right of the board
     */
    public int getD() {     // Renvoie la face du domino dispos√©e √† l'extremit√©e droite du plateau
        if (isEmpty()) {
            return -1;
        }
        return (p_content.get(p_content.size()-1)).getB();
    }
    //Exception √† faire !!
    /**
     * Get the value of the left part of the tile at the left end of the board
     * @return The integer of the left part of the tile at the left of the board **/
    public int getG() {
        if (isEmpty()) {
            return -1;
        }
        return (p_content.get(0)).getA();
    }
    
    /** Check whether the table is empty or not
     * @return Boolean : true if the table is empty**/
    public boolean isEmpty() {
        return p_content.isEmpty();
    }
    
    
    /** Show a representation of the board
     * @return A string representing the board */
    @Override
    public String toString() {
        return p_content.toString();
    }
    
    public int getNbDominos() {
        return p_content.size();
    }
    
    public Domino getDomino(int n) {
        return (p_content.get(n));
    }
    public Domino getDominoG() {
        return (p_content.get(0));
    }
    public Domino getDominoD() {
        return (p_content.get(p_content.size()-1));
    }
    public int getSize() {
        return (p_content.size());
    }
    //Attention !! exception requise
    public float getposxG() {
        return (p_content.get(0)).getX();
    }
    
    public float getposyG() {
        return (p_content.get(0)).getY();
    }
    
    public float getposxD() {
        return (p_content.get(getNbDominos()-1).getX());
    }
    
    public float getposyD() {
        return (p_content.get(getNbDominos()-1).getY());
    }
    public int getRG() {
        return (p_content.get(0)).getR();
    }
     public int getRD() {
        return (p_content.get((getNbDominos()-1))).getR();
    }
    public void fixposG(Domino d) {
        int dec;
        int n = getNbDominos();
        if (n==0) {
            if (d.isDouble()) {
                d.setR(2);
            }
            d.setX(centerX);
            d.setY(centerY);
        }
        else {
            float x = getposxG();
            float y = getposyG();
            if (dxG==-1) {      // Vers la gauche (en montant)
                if (transG==false) {     //On v√©rifie la limite
                    if (x-longd<limite_xG) {        //Il faut renverser
                        d.setR(1);
                        d.setX(x-largd);
                        d.setY(y-largd);
                        transG=true;
                        if (y-largd -largd<limite_up) { //plus de place
                            dec=Math.abs((int)(y-largd-largd-limite_up));
                            d.setY(d.getY()+dec);
                            this.move_down(dec);
                            
                        }
                    }
                    else {                  //Sinon on avance normalement
                        if (d.isDouble() && (x-largd-longd > limite_xG)) {  //C'est un double et il est pas au bord d'une limite
                            d.setX(x-largd);
                            d.setY(y);
                            d.setR(2);
                        }
                        else {
                            d.setR(0);
                            if (d.getFather()==2) {
                                d.setX(x-longd);
                                d.setY(y);
                            }
                            else {
                                d.translate(x-longd, y);
                            }
                        }
                    }
                }
                else if (transG==true) {     //On vient de renverser
                        d.setX(x);
                        d.setY(y-largd);
                        d.setR(-1);
                        dxG=1;
                        transG=false;
                    }
                }
            
          else if (dxG==1) { // vers la droite (en montant)
            if (getRG()==2) {
                ilongd=largd;
            }
            else {
                ilongd=longd;
            }
            
             if (transG==false) {
                 if (x+ilongd+longd>limite_xD) {    //Il faut renverser
                     d.setX(x+longd);
                     d.setY(y-largd);
                     d.setR(1);
                     transG=true;
                     if (y-largd-largd<limite_up) { // plus de place
                         dec=Math.abs((int)(y-largd-largd-limite_up));
                         d.setY(d.getY()+dec);
                         this.move_down(dec);
                         
                     }
                 }
                 else {
                     if (d.isDouble() && (x+largd+longd+longd <= limite_xD)) {
                         d.setR(2);
                     }
                     else {
                         d.setR(-1);
                     }
                      d.setX(x+ilongd);
                      d.setY(y);
                     
                 }
            
                }
             else if (transG==true) {    //On vient de renverser
                        d.setX(x-largd);
                        d.setY(y-largd);
                        d.setR(0);
                        dxG=-1;
                        transG=false;
             }
                
                } 
            }
        }
  
    public void fixposD(Domino d) {
        int dec;
        int n = getNbDominos();
        if (n==0) {
            if (d.isDouble()) {
                d.setR(2);
            }
            d.setX(centerX);
            d.setY(centerY);
        }
        else {
            float x = getposxD();
            float y = getposyD();
            if (dxD==-1) {  //vers la gauche (en descendant)
                if (transD==false) {     //On v√©rifie la limite
                    if (x-longd<limite_xG) {        //Il faut renverser
                        d.setR(1);
                        d.setX(x-largd);
                        d.setY(y);
                        transD=true;
                        if (y+longd+largd >limite_down) {
                        	dec=Math.abs((int)(y+longd+largd-limite_down));
                        	d.setY(d.getY()-dec);
                            this.move_up(dec);
                 //           this.move_up();
                            
                        }
                    }
                     else {
                         if (d.isDouble() && (x-largd-longd > limite_xG)) {
                            d.setR(2);
                            d.setX(x-largd);
                            d.setY(y);
                          }
                     else {
                
                       //Sinon on avance normalement
                        d.setX(x-longd);
                        d.setY(y);
                        d.setR(-1);
                         }
                    }
                }
                else if (transD==true) {     //On vient de renverser
                        d.setX(x);
                        d.setY(y+longd);
                        d.setR(0);
                      //  d.setR(-1);
                        dxD=1;
                        transD=false;
                    }
                }
        else if (dxD==1) { //A droite (en descendant)
            if (getRD()==2) {
                ilongd=largd;
            }
            else {
                ilongd=longd;
            }
             if (transD==false) {
                 if (x+ilongd+longd>limite_xD) {    //il faut renverser
                     d.setX(x+longd);                     
                     d.setY(y);                    
                     d.setR(1);
                     transD=true;
                     if (y+longd+largd >limite_down) {
                    	 dec=Math.abs((int)(y+longd+largd-limite_down));
                    	 d.setY(d.getY()-dec);
                         this.move_up(dec);
                            
                     }
                 }
                 else {
                     if (d.isDouble() && (x+largd+longd+longd < limite_xD)) {
                         d.setR(2);
                     }
                     else {
                         d.setR(0);
                     } 
                     d.setY(y);
                     d.setX(x+ilongd);
                     
                 }
            
                }
             else if (transD==true) {    //On vient de renverser
                        d.setX(x-largd);
                        d.setY(y+longd);
                        dxD=-1;
                        d.setR(-1);
                        transD=false;
             }
                
                } 
            }

            Log.v("Coucou !!!!!!"," (A: "+d.getA()+"B: "+d.getB()+" R:"+d.getR()+"(x,y): ("+d.getX()+","+d.getY()+") dx:"+dxD);
        }
   public Rect getCoordD() {
        Domino d;
    if (getNbDominos()>0) {
         d=getDominoD();
         float x=d.getX();
         float y=d.getY();
         if (dxD == -1) {
            
         
            if (d.getR()==1) {
                return new Rect((int)x,(int)y+longd,(int)(x+longd),(int)(y+largd+longd));
            }
            else if (d.getR()==2) {
                return new Rect((int)x-longd,(int)y,(int)x,(int)y+largd);
            }
            else {
                if (x-longd<limite_xG) {
                    return new Rect((int)x-largd,(int)y,(int)x,(int)y+longd);
                }
                else {
                    return new Rect((int)x-longd,(int)y,(int)x,(int)y+largd);
                }
            }
         }
         else {
            if (d.getR()==1) {
                return new Rect((int)x-largd,(int)y+longd,(int)x-largd+longd,(int)y+longd+largd);
            }
            else if (d.getR()==2) {
                return new Rect((int)x+largd,(int)y,(int)x+largd+longd,(int)y+largd);
            }
            else {
                if (x+longd+longd>limite_xD) {
                    return new Rect((int)x+longd,(int)y,(int)(x+longd+largd),(int)y+longd);
                }
                else { 
                    return new Rect((int)x+longd,(int)y,(int)x+longd+longd,(int)y+largd);
                }
            }
         }
    }
    else {
        return new Rect(centerX,centerY-largd/2,centerX+largd,centerY-largd/2+longd);
    }
}
    

    public Rect getCoordG() {
        Domino d;
    if (getNbDominos()>0) {
         d=getDominoG();
         float x=d.getX();
         float y=d.getY();
         if (dxG == 1) {
            
         
            if (d.getR()==1) {
                return new Rect((int)x-largd,(int)y-largd,(int)(x-largd+longd),(int)(y));
            }
            else if (d.getR()==2) {
                return new Rect((int)x+largd,(int)y,(int)x+largd+longd,(int)y+largd);
            }
            else {
                if (x+longd+longd>limite_xD) {
                    return new Rect((int)x+longd,(int)y-(longd-largd),(int)x+longd+largd,(int)y+largd);
                }
                else {
                    return new Rect((int)x+longd,(int)y,(int)x+longd+longd,(int)y+largd);
                }
            }
         }
         else {
            if (d.getR()==1) {
                return new Rect((int)x,(int)y-largd,(int)x+longd,(int)y);
            }
            else if (d.getR()==2) {
                return new Rect((int)x-longd,(int)y,(int)x,(int)y+largd);
            }
            else {
                if (x-longd<limite_xG) {
                    return new Rect((int)x-largd,(int)y-(longd-largd),(int)x,(int)y+largd);
                }
                else { 
                    return new Rect((int)x-longd,(int)y,(int)x+longd,(int)y+largd);
                }
            }
         }
    }
    else {
        return new Rect(centerX,centerY-largd/2,centerX+largd,centerY-largd/2+longd);
    }
}
    public void updateLimiteDown() {
        this.limite_down=this.app.getLimite_down();
        
    }
    
    public void affiche_limite() {
        Log.d("Testapp", "xG:"+limite_xG+" xD:"+limite_xD+" up:"+ limite_up+" down:"+limite_down);
    }
    

    

    
    public int move_up(int y) {
    	Log.d("testapp","move_up de " + y);
        for (Domino d:p_content) {
            d.setY(d.getY()-y);
        }
        if (! verifLimite()) {
        	Log.d("testapp", "un remplacement est exigé");
        	resizedTiles();
        }
        return y;
    }
    
    public int move_down(int y) {
    	Log.d("testapp","move_down de " + y);
        for (Domino d: p_content) {
            d.setY(d.getY()+y);
        }
        if (! verifLimite()) {
        	Log.d("testapp", "un remplacement est exigé");
        	resizedTiles();
        }
        return y;
    }
    
    public boolean sizeIsCorrect() {
    	return sizeIsCorrect;
    }
    
    public Plateau fixPl() { /// A faire par rapport au appDomino
    	Log.d("testapp","création du nouveau plateau pour remplacement");
        if (nbG != -1) {
            Plateau pl= new Plateau(app);
            pl.dominoBeingPlaced = this.dominoBeingPlaced;
            pl.isPlayingAtLeft=this.isPlayingAtLeft;
            pl.isPlayingAtRight=this.isPlayingAtRight;
            for (int i=this.nbG;i>=0;i--) {
               pl.addG(p_content.get(i));
            }
            for (int j=nbG+1;j<=nbG+nbD;j++) {
                pl.addD(p_content.get(j));
            }
            
            if (pl.sizeIsCorrect()) {
            	if (pl.isPlayingAtRight==true) {
                	pl.addD(pl.dominoBeingPlaced);
                	
                }
                else if (pl.isPlayingAtLeft)
                {
                	pl.addG(pl.dominoBeingPlaced);
                }
            	
            	TilesPlayerView tv = this.app.getTilesv();
            	tv.clearMap();
                tv.setTileSize(app.getLongd(),app.getLargd());
                app.setPl(pl);
                app.getA().setPl(pl);
        	    app.fixHumanHand();

            }
            return pl;
        }
        else {
            return null;
        }
    }
    
    public int getLongd() {
        return longd;
    }
    public int getLargd() {
        return largd;
    }
    
    public boolean verifLimite_up()
    {
    	if (nbG==-1) {
    		return true;
    	}
        return (getposyG() >= this.limite_up);
    }
    
    public boolean verifLimite_down() {
    	if (nbD==0) {
    		return true;
    	}
        return (getposyD()<=limite_down);
    }
    
    public boolean verifLimite() {
        return (this.verifLimite_down() && this.verifLimite_up());
    }
    
    public void resizedTiles() 
    {
    //	this.longd=this.longd-10;
      //  this.largd=this.largd-5;
        this.app.setTileSize(this.longd-10, this.largd-5);
        sizeIsCorrect=false;
        
        this.fixPl();
    	Log.d("testapp", "preparation des parametres pour remplacement");
      //  TilesPlayerView tv = this.app.getTilesv();
        
       // tv.setTileSize(this.longd-10,this.largd-5);
      //  app.setPl(this);
      //  app.getA().setPl(this);
        
       // tv.refreshSurface();
    }

}