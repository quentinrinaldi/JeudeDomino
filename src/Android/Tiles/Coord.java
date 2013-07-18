/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Android.Tiles;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import java.util.ArrayList;

/**
 *
 * @author Quentin
 */
public class Coord {
    private float x,y;
    
    public Coord(float x,float y) {
        this.x=x;
        this.y=y;
    }
    
    public Coord(Domino d) {
        this.x=d.getX();
        this.y=d.getY();
    }
    
    public Coord coordCenter (Domino d) {
        return new Coord (d.getX()+30,d.getY()+15);
    }
    
    public float getX() {
        return x;
    }
    
    public void setX(float x) {
        this.x=x;
    }
    
    public void setY(float y) {
        this.y=y;
    }

    public float getY() {
        return y;
    }
    
    public void setCoord(int x, int y) {
        this.x=x;
        this.y=y;
    }
    
    public void setDominoCoord(Domino d) {
        d.setX(this.x);
        d.setY(this.y);
    }
    
    public boolean distance (Coord c) {
        float dist= FloatMath.sqrt((float) (Math.pow(c.getX()-x,2)+Math.pow(c.getY()-y,2)));
        return dist<68;
    }
    
    public boolean distanceDomino (Domino d) {
        return distance (new Coord(d));
    }
    
  /*  public int inspectBoard (Plateau pl) {
        if (this.distance(pl.getCoordG())) {
            return 1;
        }
    //    else if (this.distance(pl.getCoordD())) {
     //   else if () 
      //      return 2;
      //  }
        else {
            return -1;
        }
    }
   */ 
    public int inspectHand (ArrayList<Domino> ld) {
        for (int i=0;i<ld.size();i++) {
        if (distance(coordCenter(ld.get(i)))) {
            return i;
        }
    }
        return -1;
    }
    
    public int newinspectHand (ArrayList<Domino> ld) {
        
         for (int i=0;i<ld.size();i++) {
            if (ld.get(i).contains(this)) {
                return i;
            }
        }
        return -1;
    }
    
    
    public static float convertPixelsToDp(float px,Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    
    }
        public static float convertDpToPixel(float dp,Context context){
            Resources resources = context.getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            float px = dp * (metrics.densityDpi/160f);
            return px;
        }
    @Override
    public String toString() {
        return "(x:"+getX()+",y:"+getY()+")\n";
    }
    public static Coord translate(Coord dep, Coord fin) {
        float yres=dep.getY()-fin.getY();
        float xres=dep.getX()-fin.getX();
        Log.d("testapp", "translate : xres="+xres+" yres="+yres+ "res:" + yres/xres);
        float a = yres/xres;
        float b= dep.getY()-(a*dep.getX());
        
        return new Coord(a,b);
    }
    
    public static float fun (float a, float b, float y) {
        float res = (y-b)/a;
        return res;
        
    }
    
}
