package com.exam.administrator.nccc_trip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class FestivalActivity extends AppCompatActivity {

    private static final String apiKey = "P6bhFFBWwGkij2sSFyuE1fYOhmljx2J0qqEjWC65a0BMXkdVEYQo44MRq0yZK7Txgqbp9GbSWfexAXQhBEwtLg%3D%3D";

    Thread t;
    Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<FestivalItem> items;
    NavigationOnClickListener listener;
    String startFest;
    String endFest;
    String sigungu;

    TextView subject;
    ImageView back;
    ImageView searcher;

    String name;
    String schedule;
    Bitmap bmimg;
    int contId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festival);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        items = new ArrayList();
        layoutManager = new LinearLayoutManager(this);
        adapter = new FestivalAdapter(items, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        final GestureDetector gestureDetector = new GestureDetector(FestivalActivity.this,new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    Intent i = new Intent(FestivalActivity.this, DetailActivity.class);
                    i.putExtra("what", 0);
                    i.putExtra("tour", items.get(rv.getChildAdapterPosition(child)).getContId());
                    startActivity(i);
                }
                return false;
            }
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        // Adding Toolbar to the activity
        toolbar = (Toolbar) findViewById(R.id.etc_toolbar);
        toolbar.setContentInsetsAbsolute(0,0);
        setSupportActionBar(toolbar);

        subject = (TextView)findViewById(R.id.etc_subject);
        back = (ImageView)findViewById(R.id.etc_back);
        searcher = (ImageView)findViewById(R.id.etc_searcher);
        sigungu = getIntent().getStringExtra("sigungu");
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);
        startFest = String.valueOf(cyear+String.format("%02d",cmonth+1)+String.format("%02d",cday));
        if(cmonth+3>11){
            endFest = String.valueOf(cyear+String.format("%02d",cmonth+3-11)+String.format("%02d",cday));
        }else{
            endFest = String.valueOf(cyear+String.format("%02d",cmonth+3)+String.format("%02d",cday));
        }
        listener = new NavigationOnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.etc_back:
                        finish();
                        break;
                    case R.id.etc_searcher:
                        Intent i = new Intent(FestivalActivity.this, SearchActivity.class);
                        startActivity(i);
                        break;
                }
            }
        };
        subject.setText("축제 리스트");
        back.setOnClickListener(listener);
        searcher.setOnClickListener(listener);

        t = new Thread(new Runnable() { // 반드시 스레드 이용 그래야 반복해서 쓸수 있다고 함
            @Override
            public void run() {
                try {
                    URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?ServiceKey="+apiKey+"&eventStartDate="+startFest+"&eventEndDate="+endFest+"&areaCode=33&sigunguCode="+sigungu+"&cat1=&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=20&pageNo=1&_type=json");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDefaultUseCaches(false);
                    conn.setDoInput(true);
                    conn.setDoOutput(false);
                    conn.setRequestMethod("GET");

                    if (conn != null) {
                        conn.setConnectTimeout(10000);
                        conn.setUseCaches(false);

                        if (conn.getResponseCode() >= 200 || conn.getResponseCode() < 300 ) { //접속 잘 되었는지 안되었는지 파악
                            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); // InputStreamReader로 가져온다음 Buffer에 넣으면 이상하게 에러가 남, 그냥 바로 넣을것.
                            String buf = "";
                            buf = br.readLine();
                            if (buf == null) {

                            }else {
                                JSONObject result = new JSONObject(buf);
                                JSONArray results = result.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item"); //가장 큰 테두리부터 갈라갈라 가져오기
                                for (int i=0; i < results.length(); i++){
                                    JSONObject json = results.getJSONObject(i);
                                    name = json.getString("title");
                                    schedule = String.format("%s  ~  %s",json.getString("eventstartdate"), json.getString("eventenddate"));
                                    contId = json.getInt("contentid");
                                    try {
                                        BitmapFactory.Options options = new BitmapFactory.Options();
                                        options.inSampleSize = 2;
                                        URL imgurl = new URL(json.getString("firstimage"));
                                        InputStream is = (InputStream)imgurl.getContent();
                                        bmimg = BitmapFactory.decodeStream(is, null, options);
                                        items.add(new FestivalItem(name, schedule, bmimg, contId));
                                    }catch (Exception e){
                                        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.default_img);
                                        bmimg = drawable.getBitmap();
                                        items.add(new FestivalItem(name, schedule, bmimg, contId));
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyDataSetChanged();
                                        }
                                    });

                                }
                            }
                        }
                        conn.disconnect();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                    try {
                        URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?ServiceKey=" + apiKey + "&eventStartDate=" + startFest + "&eventEndDate=" + endFest + "&areaCode=33&sigunguCode=" + sigungu + "&cat1=&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=20&pageNo=1&_type=json");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDefaultUseCaches(false);
                        conn.setDoInput(true);
                        conn.setDoOutput(false);
                        conn.setRequestMethod("GET");

                        if (conn != null) {
                            conn.setConnectTimeout(10000);
                            conn.setUseCaches(false);

                            if (conn.getResponseCode() >= 200 || conn.getResponseCode() < 300) { //접속 잘 되었는지 안되었는지 파악
                                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); // InputStreamReader로 가져온다음 Buffer에 넣으면 이상하게 에러가 남, 그냥 바로 넣을것.
                                String buf = "";
                                buf = br.readLine();
                                if (buf == null) {

                                } else {
                                    JSONObject result = new JSONObject(buf);
                                    JSONObject json = result.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item"); //가장 큰 테두리부터 갈라갈라 가져오기
                                    name = json.getString("title");
                                    schedule = String.format("%s  ~  %s", json.getString("eventstartdate"), json.getString("eventenddate"));
                                    contId = json.getInt("contentid");
                                    try {
                                        BitmapFactory.Options options = new BitmapFactory.Options();
                                        options.inSampleSize = 2;
                                        URL imgurl = new URL(json.getString("firstimage"));
                                        InputStream is = (InputStream) imgurl.getContent();
                                        bmimg = BitmapFactory.decodeStream(is, null, options);
                                        items.add(new FestivalItem(name, schedule, bmimg, contId));
                                    } catch (Exception ee1) {
                                        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.default_img);
                                        bmimg = drawable.getBitmap();
                                        items.add(new FestivalItem(name, schedule, bmimg, contId));
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                            }
                            conn.disconnect();
                        }
                    }catch (Exception ee){
                        ee.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }
    @Override
    public void onPause(){
        t.interrupt();
        super.onPause();
    }
}
