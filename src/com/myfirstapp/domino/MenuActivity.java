/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myfirstapp.domino;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 *
 * @author Quentin
 */
public class MenuActivity extends Activity {
    
    /**
     * Called when the activity is first created.
     */
    private DominoApplication app;
    private boolean resizedDone;
    double space_p,space_l;
    int config;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("testapp","oncreate(menu)");
        
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
        app= (DominoApplication) this.getApplicationContext();
   
        
        Button b1=(Button) findViewById(R.id.button1);
        Button b2=(Button) findViewById(R.id.button2);
        Button b3=(Button) findViewById(R.id.button3);
        Button b4=(Button) findViewById(R.id.button4);
        space_p=0;space_l=0;
        config=(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT)?(Configuration.ORIENTATION_PORTRAIT):(Configuration.ORIENTATION_LANDSCAPE);
        
        
        
        ViewTreeObserver vto1 = b1.getViewTreeObserver();
        vto1.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                
                Button b1=(Button) findViewById(R.id.button1);
                
                 
                resize(b1,b1.getHeight(),b1.getHeight(),1);
            }
        });
        ViewTreeObserver vto2 = b2.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                Button b1=(Button) findViewById(R.id.button1);
                Button b2=(Button) findViewById(R.id.button2);
                resize(b2,b1.getHeight(),b2.getHeight(),2);
                
            }
        });
        
        ViewTreeObserver vto3 = b3.getViewTreeObserver();
        vto3.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                
                Button b2=(Button) findViewById(R.id.button2);
                Button b3=(Button) findViewById(R.id.button3);
                resize(b3,b2.getHeight(), b3.getHeight(),3);
            }
        });
        
        ViewTreeObserver vto4 = b4.getViewTreeObserver();
        vto4.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {                
                Button b3=(Button) findViewById(R.id.button3);
                Button b4=(Button) findViewById(R.id.button4);
                resize(b4,b3.getHeight(),b4.getHeight(),4);
            }
        });
        

        
        // ToDo add your GUI initialization code here
    }
    public void resize(Button b,int prev_height,int height, int num) {
        if (resizedDone==false) {
            RelativeLayout.LayoutParams layoutParam=(RelativeLayout.LayoutParams) b.getLayoutParams();
            double space;
            if (space_p==0) {
                if (config==Configuration.ORIENTATION_PORTRAIT) {
                    space_p= ((double) findViewById(R.id.LinearLayout1).getHeight())/8.0;
                    space_l= ((double) findViewById(R.id.LinearLayout1).getWidth()-25.0)/8.0;
                }
                else {
                    space_l= ((double) findViewById(R.id.LinearLayout1).getHeight())/8.0;
                    space_p= ((double) findViewById(R.id.LinearLayout1).getWidth()-25)/8.0;
                }
            }
            if (this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
                space=space_p;
                Log.d("testapp","portrait" + space);
                if (num==1) {
                    
                    ImageView iv=(ImageView) findViewById(R.id.imageView1);
                    iv.setMaxHeight((int)(space*1.5));
                    iv.setMaxWidth((int)space*3);
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.topMargin=(int)space;
                    lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    iv.setLayoutParams(lp);
                    
                    layoutParam.topMargin=(int) ((space-height)/2.0 + (3*space)+space/25.0);
                }
                else {
                    layoutParam.topMargin=(int) ((space-prev_height)/2.0+(space-height)/2.0);
                }
            }
            else {
                space=space_l;
                Log.d("testapp","paysage" + space);
                if (num==1) {
                    
                    ImageView iv=(ImageView) findViewById(R.id.imageView1);
                    iv.setMaxHeight((int)(space*2.5));
                    iv.setMaxWidth((int)space*5);
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    //lp.topMargin=0;
                   //lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    lp.addRule(RelativeLayout.ALIGN_BOTTOM);
                    lp.addRule(RelativeLayout.ALIGN_LEFT);
                    lp.addRule(RelativeLayout.CENTER_VERTICAL);
                    iv.setLayoutParams(lp);
                    
                    layoutParam.topMargin=(int) (space);
                }
                else {
                    layoutParam.topMargin=(int) (space/3.0);
                }
            }
            b.setLayoutParams(layoutParam);
            if (num==4) {
                resizedDone=true;
            }
        }
    }
    
        
    
    
    public void onClickButton1(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    
    public void onClickButton2(View v) {
        Intent intent = new Intent(this, PreferenceAct.class);
        startActivity(intent);
    }
    
    public void onClickButton3(View v) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }
    
    public void onClickButton4(View v) {
        android.os.Process.killProcess(android.os.Process.myPid());
        
    }
    
    @Override
    public void onResume() {
       super.onResume();
       Button b = (Button) findViewById(R.id.button1);
       Log.d("testapp","onResume");
       if (app.getGaming()==true) {
            b.setText(R.string.resume);       
        }
       else {
            b.setText(R.string.newGame);  
       }
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d("testapp","changed");
    super.onConfigurationChanged(newConfig);
    resizedDone=false;
    Button b1=(Button) findViewById(R.id.button1);
        Button b2=(Button) findViewById(R.id.button2);
        Button b3=(Button) findViewById(R.id.button3);
        Button b4=(Button) findViewById(R.id.button4);
        resize(b1,b1.getHeight(),b1.getHeight(),1);
        resize(b2,b1.getHeight(),b2.getHeight(),2);
        resize(b3,b2.getHeight(), b3.getHeight(),3);
        resize(b4,b3.getHeight(),b4.getHeight(),4);
        
  }
}
