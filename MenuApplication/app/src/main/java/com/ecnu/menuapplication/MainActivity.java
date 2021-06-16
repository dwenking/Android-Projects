package com.ecnu.menuapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView soft;
    TextView edu;
    TextView eco;
    WebView web;

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        web=findViewById(R.id.web);
        WebSettings webSettings = web.getSettings();
        webSettings.setDomStorageEnabled(true);//主要是这句
        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        web.setWebViewClient(new WebViewClient());
        web.setWebChromeClient(new WebChromeClient());//这行最好不要丢掉


        soft=findViewById(R.id.soft);
        edu=findViewById(R.id.edu);
        eco=findViewById(R.id.eco);
        drawer=findViewById(R.id.drawer);

        soft.setOnClickListener(this);
        edu.setOnClickListener(this);
        eco.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.soft:
                drawer.closeDrawers();
                web.loadUrl("http://www.sei.ecnu.edu.cn");
				break;
            case R.id.edu:
                drawer.closeDrawers();
                web.loadUrl("http://www.ed.ecnu.edu.cn");
                break;
            case R.id.eco:
                drawer.closeDrawers();
                web.loadUrl("http://fem.ecnu.edu.cn");
                break;
        }
    }
}