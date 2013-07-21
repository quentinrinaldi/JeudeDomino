/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myfirstapp.domino;


import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Window;

/**
 *
 * @author Quentin
 */
public class PreferenceAct extends PreferenceActivity {

    /**
     * Called when the activity is first created.
     */
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle icicle) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.preference);
        

        // ToDo add your GUI initialization code here        
    }

}
