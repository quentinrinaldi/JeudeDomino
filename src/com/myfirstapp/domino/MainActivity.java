package com.myfirstapp.domino;

import Android.Tiles.MatchNullException;
import Android.Tiles.PartieGagneException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    private DominoApplication app;
    private TilesPlayerView tilesv;
    private TextView tv;
    private Button newGame,menu,stock;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        /** Loading preferences */
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        
        /** Screen Preferences */
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (preferences.getBoolean("full_screen", false) == true) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        
        
        /** Initialisation */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        

        /** Loading app elements */
        this.tv= (TextView) findViewById(R.id.info);
        this.app= (DominoApplication) this.getApplicationContext();
        this.tilesv= (TilesPlayerView) findViewById(R.id.tilesPlayerView);
        this.newGame=(Button) findViewById(R.id.newgame);
        this.menu=(Button) findViewById(R.id.back);
        this.stock=(Button) findViewById(R.id.pioche);
      
        /** Difficulty Preferences */
        int d = Integer.parseInt(preferences.getString("diff_pref", "0"));
        app.setDiff(d);
        
        /** Background Preferences */
       
        int background_pref=Integer.parseInt(preferences.getString("background_pref", "0"));          
        switch (background_pref) {
            case 0:
                tilesv.setBackgroundResource(R.drawable.greenfelt);
                break;
                
            case 1:
                tilesv.setBackgroundResource(R.drawable.purplefelt);
                break;
            case 2:
                tilesv.setBackgroundResource(R.drawable.blackfelt);
                break;
            case 3:
                tilesv.setBackgroundResource(R.drawable.bluelightfelt);
                break;
            case 4:
                tilesv.setBackgroundResource(R.drawable.redfelt);
                break;
        }
        
        
        
        /** Communicates color preferences to the SurfaceView */
        int c = Integer.parseInt(preferences.getString("color_pref", "0"));
        tilesv.setColor(c);
        Log.d("testapp","color : "+c);
        
        
        app.setNewGameButton(newGame);
        app.setExitButton(menu);
        app.setTv(tv);
        app.setButtonPioche(stock);
        app.setTilesv(tilesv);
        app.setAct(this);
        
        Log.d("testapp","activity !");
        
        if (app.getGaming()) {
            app.refreshTv();
        }
        
        /** Communicates drawing preferences */
        app.setPiocheParam(preferences.getBoolean("limite_pioche", false));
        
        if (app.getGaming()) {
            app.stock_button();
        }
        
        /** Communicates transition effect preferences */
        app.setTransPref(preferences.getBoolean("moving", true));
        
        }
    
    public void onPiocheClick(View view) {
        try {
            
            app.getA().jeu(0,0);
            tilesv.invalidate();
        }
        catch (PartieGagneException e) {
            AlertDialog alert = new AlertDialog.Builder(this).create();
            alert.setCanceledOnTouchOutside(false);
            alert.setOwnerActivity(this);
            if (e.getJoueur().isComputer()) {
                // Log.d("Testapp", "TEST2222");
                alert.setTitle(getString(R.string.lostTitle));
                alert.setMessage(getString(R.string.lostMessage));
            }
            else {
                alert.setTitle(getString(R.string.wonTitle));
                alert.setMessage(getString(R.string.wonMessage));
            }
            
            alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.newGame),new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    newGame.performClick();
                }
            });
            
            alert.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.menu),new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    menu.performClick();
                }
            });
            alert.show();
        }
        catch (MatchNullException e3) {
                                            
                                        AlertDialog alert = new AlertDialog.Builder(this).create();
                                        alert.setCanceledOnTouchOutside(false);
                                        alert.setTitle(getString(R.string.endDrawTitle));
                                        alert.setMessage(getString(R.string.endDrawMessage));
                                        alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.newGame) ,new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                newGame.performClick();
                                        }
                                        });
                                        
                                        alert.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.menu) ,new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                app.setGaming(false);
                                                menu.performClick();
                                        }
                                        });
                                        alert.show();
                             
                                    }
        catch (Exception e) {
           
        }
    }
    

    public void onNewGameClick(View view) {
    	Log.d("testapp", "newgame clicked");
        tilesv.reset(this);
        app.init_game();
        app.onGame();    
    }
    
    public void onMenuClick(View view) throws InterruptedException {
        finish();
    }
  
    
    @Override
    public void onResume() {
        super.onResume();
        Log.d("testapp","resume !!");
            if (! app.getGaming()) {
             //   this.onNewGameClick(tilesv);
        }
    }
    
    @Override
    public void onRestart() {
        super.onRestart();
        finish();   //Forces user to go back to the menu before coming back to the game
    }
    
    
    
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.main);
                               /** Loading preferences */
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (preferences.getBoolean("full_screen", false) == true) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        /** Loading app elements */
        this.tv= (TextView) findViewById(R.id.info);
        this.app= (DominoApplication) this.getApplicationContext();
        this.tilesv= (TilesPlayerView) findViewById(R.id.tilesPlayerView);
        this.newGame=(Button) findViewById(R.id.newgame);
        this.menu=(Button) findViewById(R.id.back);
        this.stock=(Button) findViewById(R.id.pioche);
        
        /** Communicates color preferences to the SurfaceView */
        int c = Integer.parseInt(preferences.getString("color_pref", "0"));
        tilesv.setColor(c);
        Log.d("testapp","color : "+c);
        
        
        app.setNewGameButton(newGame);
        app.setExitButton(menu);
        app.setTv(tv);
        app.setButtonPioche(stock);
        app.setTilesv(tilesv);
        app.setAct(this);
        
        
        /** Communicates drawing preferences */
        app.setPiocheParam(preferences.getBoolean("limite_pioche", false));
        
        if (app.getGaming()) {
            app.stock_button();
        }
            app.refreshTv();
 
    }
    

 @Override
        protected void onSaveInstanceState(Bundle savedInstanceState){
 
                super.onSaveInstanceState(savedInstanceState);
        }  
 

 
}
