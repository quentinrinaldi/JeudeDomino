package com.myfirstapp.domino;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import Android.Tiles.*;
import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

/**
 *
 * @author Quentin
 */
public class DominoApplication extends Application {

    private boolean gaming;
    private Pioche pi;
    private Plateau pl;
    private Arbitre a;
    private Activity act;
    private Joueur j1;
    private Joueur j2;
    private SparseArray<Bitmap> map;
    private TextView tv;
    private Button newGame, exit, stock;
    private ArrayList<Domino> mainVide;
    private TilesPlayerView tilesv;
    private int width, height;
    private int longd, largd;
    private int limite_up, limite_down, limite_xG, limite_xD;
    private int diff;
    private boolean piocheParam;
    private boolean transPref;

    @Override
    public void onCreate() {

        piocheParam = false;
        gaming = false;
        init_map();


    }

    /**
     * @return the stock
     */
    public Pioche getPi() {
        return pi;
    }

    /**
     * @param pi the stock to set
     */
    public void setPi(Pioche pi) {
        this.pi = pi;
    }

    /**
     * @return the board
     */
    public Plateau getPl() {
        return this.pl;
    }

    /**
     * @param pl the board to set
     */
    public void setPl(Plateau pl) {
        this.pl = pl;
    }

    /**
     * @return the referee
     */
    public Arbitre getA() {
        return a;
    }

    /**
     * @param a the referee to set
     */
    public void setA(Arbitre a) {
        this.a = a;
    }

    public void init_map() {
    	map = new SparseArray<Bitmap>(60);


        map.put(0, BitmapFactory.decodeResource(getResources(), R.drawable.tile00));
        map.put(100, BitmapFactory.decodeResource(getResources(), R.drawable.tile00r));
        map.put(1, BitmapFactory.decodeResource(getResources(), R.drawable.tile01));
        map.put(2, BitmapFactory.decodeResource(getResources(), R.drawable.tile02));
        map.put(3, BitmapFactory.decodeResource(getResources(), R.drawable.tile03));
        map.put(4, BitmapFactory.decodeResource(getResources(), R.drawable.tile04));
        map.put(5, BitmapFactory.decodeResource(getResources(), R.drawable.tile05));
        map.put(6, BitmapFactory.decodeResource(getResources(), R.drawable.tile06));
        map.put(10, BitmapFactory.decodeResource(getResources(), R.drawable.tile10));
        map.put(11, BitmapFactory.decodeResource(getResources(), R.drawable.tile11));
        map.put(111, BitmapFactory.decodeResource(getResources(), R.drawable.tile11r));
        map.put(12, BitmapFactory.decodeResource(getResources(), R.drawable.tile12));
        map.put(13, BitmapFactory.decodeResource(getResources(), R.drawable.tile13));
        map.put(14, BitmapFactory.decodeResource(getResources(), R.drawable.tile14));
        map.put(15, BitmapFactory.decodeResource(getResources(), R.drawable.tile15));
        map.put(16, BitmapFactory.decodeResource(getResources(), R.drawable.tile16));
        map.put(20, BitmapFactory.decodeResource(getResources(), R.drawable.tile20));
        map.put(21, BitmapFactory.decodeResource(getResources(), R.drawable.tile21));
        map.put(22, BitmapFactory.decodeResource(getResources(), R.drawable.tile22));
        map.put(122, BitmapFactory.decodeResource(getResources(), R.drawable.tile22r));
        map.put(23, BitmapFactory.decodeResource(getResources(), R.drawable.tile23));
        map.put(24, BitmapFactory.decodeResource(getResources(), R.drawable.tile24));
        map.put(25, BitmapFactory.decodeResource(getResources(), R.drawable.tile25));
        map.put(26, BitmapFactory.decodeResource(getResources(), R.drawable.tile26));
        map.put(30, BitmapFactory.decodeResource(getResources(), R.drawable.tile30));
        map.put(31, BitmapFactory.decodeResource(getResources(), R.drawable.tile31));
        map.put(32, BitmapFactory.decodeResource(getResources(), R.drawable.tile32));
        map.put(33, BitmapFactory.decodeResource(getResources(), R.drawable.tile33));
        map.put(133, BitmapFactory.decodeResource(getResources(), R.drawable.tile33r));
        map.put(34, BitmapFactory.decodeResource(getResources(), R.drawable.tile34));
        map.put(35, BitmapFactory.decodeResource(getResources(), R.drawable.tile35));
        map.put(36, BitmapFactory.decodeResource(getResources(), R.drawable.tile36));
        map.put(40, BitmapFactory.decodeResource(getResources(), R.drawable.tile40));
        map.put(41, BitmapFactory.decodeResource(getResources(), R.drawable.tile41));
        map.put(42, BitmapFactory.decodeResource(getResources(), R.drawable.tile42));
        map.put(43, BitmapFactory.decodeResource(getResources(), R.drawable.tile43));
        map.put(44, BitmapFactory.decodeResource(getResources(), R.drawable.tile44));
        map.put(144, BitmapFactory.decodeResource(getResources(), R.drawable.tile44r));
        map.put(45, BitmapFactory.decodeResource(getResources(), R.drawable.tile45));
        map.put(46, BitmapFactory.decodeResource(getResources(), R.drawable.tile46));
        map.put(50, BitmapFactory.decodeResource(getResources(), R.drawable.tile50));
        map.put(51, BitmapFactory.decodeResource(getResources(), R.drawable.tile51));
        map.put(52, BitmapFactory.decodeResource(getResources(), R.drawable.tile52));
        map.put(53, BitmapFactory.decodeResource(getResources(), R.drawable.tile53));
        map.put(54, BitmapFactory.decodeResource(getResources(), R.drawable.tile54));
        map.put(55, BitmapFactory.decodeResource(getResources(), R.drawable.tile55));
        map.put(155, BitmapFactory.decodeResource(getResources(), R.drawable.tile55r));
        map.put(56, BitmapFactory.decodeResource(getResources(), R.drawable.tile56));
        map.put(60, BitmapFactory.decodeResource(getResources(), R.drawable.tile60));
        map.put(61, BitmapFactory.decodeResource(getResources(), R.drawable.tile61));
        map.put(62, BitmapFactory.decodeResource(getResources(), R.drawable.tile62));
        map.put(63, BitmapFactory.decodeResource(getResources(), R.drawable.tile63));
        map.put(64, BitmapFactory.decodeResource(getResources(), R.drawable.tile64));
        map.put(65, BitmapFactory.decodeResource(getResources(), R.drawable.tile65));
        map.put(66, BitmapFactory.decodeResource(getResources(), R.drawable.tile66));
        map.put(166, BitmapFactory.decodeResource(getResources(), R.drawable.tile66r));
        map.put(99, BitmapFactory.decodeResource(getResources(), R.drawable.tile99));
    }

    /**
     * 
     * @return the hashmap containing all tiles bitmap 
     */
    public SparseArray<Bitmap> getmap() {
        return map;
    }
/**
 * 
 * @param tileId The tile id to return
 * @return The tile which id is tileId
 */
    public Bitmap getBitmap(int tileId) {
        return map.get(tileId);
    }

    public ArrayList<Domino> getHumanHand() {
        if (gaming) {
            return a.getplayerhand(getJ1().getId());
        } else {
            return mainVide;
        }
    }

    /**
     * @return the j1
     */
    public Joueur getJ1() {
        return j1;
    }
    
    public Joueur getJ2() {
        return j2;
    }

    public void init_game() {
    	Log.d("testapp", "initialisation du jeu");
        this.pi = new Pioche(this);
        this.pl = new Plateau(this);
        this.a = new Arbitre(pi, pl, this);
    }

    public void onGame() {
        try {
        	Log.d("testapp","debut du jeu");
            setJ1(getA().newPlayerH("testH"));
            setJ2(getA().newPlayerO("test0"));
            this.gaming = true;

            tv.setText(getString(R.string.firstPlayerWarning));
            fixHumanHand();
            fixComputerHand();
            getA().start();
            getA().setAdvId(j2.getId());
            if (a.getFirstPlay() == false) {
                stock.setEnabled(true);
            }

        } catch (Exception e) {
        }
    }

    public void surfaceChanged() {
    	Log.d("testapp", "surcacechanged : longd : "+longd+"largd:"+largd);
    	this.limite_up=largd/2;
        this.limite_xG=5+largd;
        this.limite_xD=width-5-largd;
        
        float n = (float) 7;
        int slongd = this.longd + 6;
        int slargd = this.largd + 6;
        int nb = (width - 15) / slongd;


        int col = (int) Math.ceil(n / nb);

        int yfin = this.height - largd / 2;
        int ydebut = yfin - (col * (slargd));
        this.limite_down = ydebut - largd;
    }

    public void reset() {
    }

    /**
     * @param j1 the j1 to set
     */
    public void setJ1(Joueur j1) {
        this.j1 = j1;
    }

    public void fixHumanHand() {
    	Log.d("testapp" ,"fix human hand :");
        ArrayList<Domino> l = getHumanHand();
        float n = (float) l.size();
        int slongd = this.longd + 6;
        int slargd = this.largd + 6;
        int nb = (width - 15) / slongd;


        int col = (int) Math.ceil(n / nb);

        int yfin = this.height - largd / 2;
        int cptd = 0;
        int ydebut = yfin - (col * (slargd));
        this.limite_down = ydebut - largd / 2;
        
        int xdebut = 15;


        for (int i = 0; i < col; i++) {
            for (int j = 0; ((j < nb) && (cptd < n)); j++) {
                l.get(cptd).setX(xdebut + (j * slongd));
                l.get(cptd).setY(ydebut + (i * (slargd)));
                cptd++;
            }
        }
        this.updateLimiteDown();

        if ((pl.getNbDominos() > 2) && (ydebut < (pl.getposyD() + largd + largd / 2))) {
        	Log.d("testapp", "on monte ! fix");
            pl.move_up((int) Math.abs(pl.getposyD() + largd + largd / 2 - ydebut));
        }
    }
    
    public void fixComputerHand() {
    	Log.d("testapp","computer hand");
    	ArrayList<Domino> l = getComputerHand();
        float n = (float) l.size();
        int slongd = this.longd/2 + 6;
        int slargd = this.largd/2 + 6;
        int nb = (width - 15) / slongd;


        int col = (int) Math.ceil(n / nb);
        int ydebut = 5;
        
        int yfin = ydebut + (col * slargd);
        int cptd = 0;
  
        this.limite_up = yfin + largd / 2;
        
        int xdebut = 15;
        int x,y;

        for (int i = 0; i < col; i++) {
            for (int j = 0; ((j < nb) && (cptd < n)); j++) {
            	x=xdebut + (j * slongd);
            	y=ydebut + (i * (slargd));
                l.get(cptd).setX(x);
                l.get(cptd).setY(y);
             //   l.get(cptd).setFather(1);
                cptd++;
            }
        }
      /*  this.updateLimiteDown();

        if ((pl.getNbDominos() > 2) && (ydebut < (pl.getposyD() + largd + largd / 2))) {
        	Log.d("testapp", "on monte ! fix");
            pl.move_up((int) Math.abs(pl.getposyD() + largd + largd / 2 - ydebut));
        }
        */
    }

    public void setJ2(Joueur j2) {
        this.j2 = j2;
    }
    
    public ArrayList<Domino> getComputerHand() {
        if (gaming) {
            return a.getplayerhand(getJ2().getId());
        } else {
            return mainVide;
        }
    }

    public int getNbDominosj2() {
        return this.j2.getNbDominos();
    }

    public void setTv(TextView tv) {
        this.tv = tv;
    }

    public void move(float dy) {
    	Log.d("testapp", "move ! from app");
        ArrayList<Domino> l = getHumanHand();
        Domino d;
        for (int i = 0; i < j1.getNbDominos(); i++) {
            d = l.get(i);
            d.setY(d.getY() + dy);
        }
    }

    public void setNewGameButton(Button b) {
        this.newGame = b;
    }

    public Button getNewGameButton() {
        return this.newGame;
    }

    public void setExitButton(Button b) {
        this.exit = b;
    }

    public Button getExitButton() {
        return this.exit;
    }

    public void setGaming(boolean b) {
        this.gaming = b;
    }


    public void updateLimiteDown() {
    	Log.d("testapp","update limite down :" + limite_down);
        pl.updateLimiteDown();
    }

    public void setConfig(int width, int height, int longd, int largd) {
    	
    	Log.d("testapp", "setconfig");
        this.width = width;
        this.height = height;
        this.longd=longd;
        this.largd=largd;
        this.limite_up=largd/2;
        this.limite_xG=5+largd;
        this.limite_xD=width-5-largd;
        
        float n = (float) 7;
        int slongd = this.longd + 6;
        int slargd = this.largd + 6;
        int nb = (width - 15) / slongd;


        int col = (int) Math.ceil(n / nb);

        int yfin = this.height - largd / 2;
        int ydebut = yfin - (col * (slargd));
        this.limite_down = ydebut - largd;  //A vŽrifier
    }

    public void setTileSize(int longd, int largd) {
        this.longd = longd;
        this.largd = largd;
    }

    public boolean getGaming() {
        return gaming;
    }

    public void setPiocheParam(boolean b) {
        this.piocheParam = b;
    }

    public boolean getPiocheParam() {
        return this.piocheParam;
    }

    public void refreshTv() {
        if (gaming == true) {
            if (a.getFirstPlay()) {
                tv.setText("\t"+this.getString(R.string.firstPlayerWarning));
            }
            else {  
            tv.setText("\t"+this.getString(R.string.remaining) + "\t"+ this.getString(R.string.stock) + "" + pi.getNbDominos() + " / " + this.getString(R.string.opponent) + "" + getNbDominosj2());
            }
        }
    }

    public void setButtonPioche(Button pioche) {
        this.stock = pioche;
    }

    public Button getButtonPioche() {
        return stock;
    }

    public void stock_button() {
        if (((this.getPiocheParam() == false) && getA().verifCoups() == true) || getA().getFirstPlay() == true) {
            stock.setEnabled(false);
        } else {
            stock.setEnabled(true);
        }
    }

    public int getLongd() {
        return longd;
    }

    public int getLargd() {
        return largd;
    }
    
    public int getLimite_up() {
    	return this.limite_up;
    }
    
    public int getLimite_down() {
    	return this.limite_down;
    }
    
    public void setTilesv(TilesPlayerView tilesv) {
        this.tilesv=tilesv;
    }
    
    public TilesPlayerView getTilesv() {
        return tilesv;
    }
    

    public Activity getActivity() {
        return act;
    }
    
    public void setAct(Activity a) {
        this.act=a;
    }
    
    public void setTransPref(boolean b) {
        this.transPref=b;
    }
    
    public boolean getTransPref() {
        return this.transPref;
    }

	public int getDiff() {
		return diff;
	}

	public void setDiff(int diff) {
		this.diff = diff;
	}

	public int getLimite_xG() {
		return limite_xG;
	}
	
	public int getLimite_xD() {
		return limite_xD;
	}


}