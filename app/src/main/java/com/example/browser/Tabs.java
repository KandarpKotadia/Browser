package com.example.browser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Tabs extends AppCompatActivity {

    ArrayList<String> urls;
    ListView tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);
        urls = new ArrayList<>();
        Button addTab = findViewById(R.id.bAdd);
        tabs = findViewById(R.id.tabs_list);

        urls = getIntent().getStringArrayListExtra("storageUrls");

        tabs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Tabs.this, SimpleBrowser.class);
                i.putExtra("url", urls.get(position));
                i.putExtra("position",position);
                i.putExtra("storageUrls", urls);
                startActivity(i);
                finish();
            }
        });

        addTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tabs.this, SimpleBrowser.class);
                intent.putExtra("url", "https://www.google.com");
                intent.putExtra("storageUrls", urls);
                startActivity(intent);
                finish();
            }
        });

        ArrayAdapter tabUrlList = new ArrayAdapter(getApplicationContext(), R.layout.tabs_design, urls);
        tabs.setAdapter(tabUrlList);

    }
}
