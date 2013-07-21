package com.myfirstapp.domino;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;


public class TilesPlayThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private TilesPlayerView game;
    private boolean running = false;
    @SuppressWarnings("unused")
	private Bitmap bitmap;
    private Bitmap buffCanvasBitmap;
    private Canvas buffCanvas;
    
    public TilesPlayThread(SurfaceHolder holder, TilesPlayerView game) {
        this.surfaceHolder = holder;
        this.game = game; 
        bitmap=Bitmap.createBitmap(game.getWidth(),game.getHeight(), Bitmap.Config.RGB_565);
       // bitmap=Bitmap.createBitmap(game.getWidth(),game.getHeight(), Bitmap.Config.ARGB_4444);
    }
    
    public void setRunning(boolean run) {
       this.running = run; }
   

    
    
    @Override
    public void run() {
        buffCanvasBitmap=Bitmap.createBitmap(game.getWidth(), game.getHeight(), Bitmap.Config.ARGB_8888);
        buffCanvas=new Canvas();
        buffCanvas.setBitmap(buffCanvasBitmap);
        
        Canvas c = null;
        while(running) {

         
                try {
                    
                    c = this.surfaceHolder.lockCanvas(null);
                    
                    synchronized (this.surfaceHolder) {
                         if ((c != null) && (running)) {
                              
                              c.drawBitmap(buffCanvasBitmap, 0, 0, null);
                        
                            }        
                        }
                }
                finally {
                    if (c != null) {
                        this.surfaceHolder.unlockCanvasAndPost(c);
                    } 
                } 
        } 
   }
    
    
}
