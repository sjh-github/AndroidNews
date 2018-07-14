package com.sjh.news.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sjh.news.R;

public class ContentActivity extends AppCompatActivity {
    private WebView webView;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        /*Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);*/

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }

        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        //webSettings
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        String uri = getIntent().getStringExtra("uri");
        String title = getIntent().getStringExtra("title");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
        webView.loadUrl(uri);
        System.out.println(uri);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                //编写 javaScript方法
               /* String javascript =  "javascript:function hideOther() {" +
                        "document.getElementsByTagName('body')[0].innerHTML;" +
                        "document.getElementsByTagName('div')[0].style.display='none';" +
                        "document.getElementsByTagName('div')[3].style.display='none';" +
                        "document.getElementsByClassName('dropdown')[0].style.display='none';" +
                        "document.getElementsByClassName('min')[0].remove();" +
                        "var divs = document.getElementsByTagName('div');" +
                        "var lastDiv = divs[divs.length-1];" +
                        "lastDiv.remove();" +
                        "document.getElementsByClassName('showme')[0].remove();" +
                        "document.getElementsByClassName('nei-t3')[1].remove();}";*/

                String javascript =  "javascript:function hideOther() {" +
                        "document.getElementsByTagName('img')[0].style.display='none';" +
                        "document.getElementsByTagName('img')[1].remove();";


                //创建方法
                view.loadUrl(javascript);
                System.out.println(javascript);
                //加载方法
                view.loadUrl("javascript:hideOther();");
            }
        });

    }



    /**
     * 点击返回键做了处理
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case android.R.id.home:
                    finish();
                    break;
                default:
            }
            return true;
    }
}