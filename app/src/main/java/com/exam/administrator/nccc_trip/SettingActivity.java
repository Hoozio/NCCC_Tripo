package com.exam.administrator.nccc_trip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {
    TextView subject;
    ImageView back;
    ImageView searcher;
    NavigationOnClickListener listener;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        // Adding Toolbar to the activity
        toolbar = (Toolbar) findViewById(R.id.etc_toolbar);
        toolbar.setContentInsetsAbsolute(0,0);
        setSupportActionBar(toolbar);

        subject = (TextView)findViewById(R.id.etc_subject);
        back = (ImageView)findViewById(R.id.etc_back);
        searcher = (ImageView)findViewById(R.id.etc_searcher);
        listener = new NavigationOnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.etc_back:
                        finish();
                        break;
                    case R.id.etc_searcher:
                        Intent i = new Intent(SettingActivity.this, SearchActivity.class);
                        startActivity(i);
                        break;
                }
            }
        };
        subject.setText("개발 정보");
        back.setOnClickListener(listener);
        searcher.setOnClickListener(listener);

    }
}
