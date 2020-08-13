package com.example.browser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class SimpleBrowser extends AppCompatActivity implements View.OnClickListener {

    EditText url;
    WebView ourBrow;
    String sbUrl = "https://www.google.com";
    Storage storage;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simplebrowser);

        if ( getIntent().getStringArrayListExtra("storageUrls") == null ) {
            storage = new Storage();
        } else {
            storage = new Storage();
            storage.urls = getIntent().getStringArrayListExtra("storageUrls");
        }

        ourBrow = findViewById(R.id.wvBrowser);
        ourBrow.getSettings().setJavaScriptEnabled(true);
        ourBrow.getSettings().getLoadWithOverviewMode();
        ourBrow.getSettings().setUseWideViewPort(true);
        ourBrow.setWebViewClient(new ourViewClient());

        if( getIntent().getStringExtra("url") != null ) {
            sbUrl = getIntent().getStringExtra("url");
            ourBrow.loadUrl(sbUrl);
        }else{
            ourBrow.loadUrl(sbUrl);
        }

        Button go = findViewById(R.id.bGo);
        Button back = findViewById(R.id.bBack);
        Button refresh = findViewById(R.id.bRefresh);
        Button forward = findViewById(R.id.bForward);
        Button clearHistory = findViewById(R.id.bHistory);
        Button tab = findViewById(R.id.bTab);

        url = findViewById(R.id.etURL);
        go.setOnClickListener(this);
        back.setOnClickListener(this);
        refresh.setOnClickListener(this);
        forward.setOnClickListener(this);
        clearHistory.setOnClickListener(this);
        tab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bGo:
                position = getIntent().getIntExtra("position",0);
                sbUrl = "https://" + url.getText().toString();
                storage.urls.set(position,sbUrl);
                ourBrow.loadUrl(sbUrl);
                //hiding the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(url.getWindowToken(), 0);
                //
                break;
            case R.id.bBack:
                if (ourBrow.canGoBack())
                    ourBrow.goBack();
                break;
            case R.id.bForward:
                if (ourBrow.canGoForward())
                    ourBrow.goForward();
                break;
            case R.id.bRefresh:
                ourBrow.reload();
                break;
            case R.id.bHistory:
                ourBrow.clearHistory();
                break;
            case R.id.bTab:
                Intent i = new Intent(SimpleBrowser.this, Tabs.class);
                if ( position == 0 ) {
                    storage.urls.add(sbUrl);
                }
                i.putExtra("storageUrls", storage.urls);
                startActivity(i);
                finish();
                break;
        }
    }
}
