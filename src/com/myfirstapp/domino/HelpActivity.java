/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myfirstapp.domino;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;


/**
 *
 * @author Quentin
 */
public class HelpActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        this.setContentView(R.layout.helpscreen);
        WebView wb=(WebView) findViewById(R.id.webview);
        wb.loadUrl(getString(R.string.htmlHelp));
        //wb.loadUrl("file:///android_asset/help-string.html");
                
    }
}
