package com.ble.ninebot.jsontest01;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by kic on 2015/9/4.
 */
public class PlaySWF extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉信息栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.play_swf);
        WebView mWebFlash = (WebView) findViewById(R.id.webFlash);
        WebSettings settings = mWebFlash.getSettings();
//        settings.setPluginsEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setDefaultTextEncodingName("GBK");
        mWebFlash.setBackgroundColor(0);
        mWebFlash.loadUrl("file:///android_asset/abc.swf");
    }
}
