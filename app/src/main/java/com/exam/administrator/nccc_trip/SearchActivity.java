package com.exam.administrator.nccc_trip;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SearchActivity extends AppCompatActivity {

    String TourApiKey = "P6bhFFBWwGkij2sSFyuE1fYOhmljx2J0qqEjWC65a0BMXkdVEYQo44MRq0yZK7Txgqbp9GbSWfexAXQhBEwtLg%3D%3D";

    private Toolbar toolbar;
    EditText keyword;
    ImageView back;
    ImageView search;
    RadioGroup radioGroup;
    SearchOnClickListener listener;

    Context mcontext;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<TourItem> items;

    InputMethodManager imm;
    String name;
    String address;
    String imgUrl;
    int contId;
    int contTypeId;
    int what;
    Bitmap bmimg;
    Thread t;
    boolean isEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mcontext = getApplicationContext();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        t = new Thread();

        toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);

        keyword = (EditText) findViewById(R.id.searchKeyword);
        keyword.setFocusable(true);
        back = (ImageView) findViewById(R.id.backHome);
        search = (ImageView) findViewById(R.id.realSearch);

        contTypeId = 12;
        what = 0;
        radioGroup = (RadioGroup)findViewById(R.id.radio_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(group.getId()==R.id.radio_group){
                    switch (checkedId){
                        case R.id.trip_radio:
                            contTypeId = 12;
                            what = 0;
                            break;
                        case R.id.taste_radio:
                            contTypeId = 39;
                            what = 1;
                            break;
                        case R.id.hotel_radio:
                            contTypeId = 32;
                            what = 2;
                            break;
                        case R.id.festival_radio:
                            contTypeId = 15;
                            break;
                    }
                }
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
        recyclerView.setHasFixedSize(true);
        items = new ArrayList();
        layoutManager = new LinearLayoutManager(this);
        adapter = new CalendarAdapter(items, mcontext);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        final GestureDetector gestureDetector = new GestureDetector(SearchActivity.this,new GestureDetector.SimpleOnGestureListener()
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
                View child = rv.findChildViewUnder(e.getX(),e.getY());
                if(child!=null&&gestureDetector.onTouchEvent(e)){
                    Intent i = new Intent(SearchActivity.this, DetailActivity.class);

                    i.putExtra("what", what);
                    Log.e("!!!", ""+rv.getChildLayoutPosition(child));
                    i.putExtra("tour",items.get(rv.getChildLayoutPosition(child)).getContInt());
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

        listener = new SearchOnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.backHome:
                        SearchActivity.this.finish();
                    case R.id.realSearch:
                        isEmpty = true;
                        final TextView tv = (TextView) findViewById(R.id.no_list);
                        tv.setVisibility(View.GONE);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                t.interrupt();
                                items.clear();
                                adapter.notifyDataSetChanged();
                            }
                        });

                        Log.e("ㅇㅇ", "dddd");
                        final String searchQuery = keyword.getText().toString();
                        Log.e("ㅇㅇ", searchQuery);
                        hideKeyboard();

                        if(!searchQuery.equals("")) {
                            t = new Thread(new Runnable() { // 반드시 스레드 이용 그래야 반복해서 쓸수 있다고 함
                                @Override
                                public void run() {

                                    try {
                                        URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey=P6bhFFBWwGkij2sSFyuE1fYOhmljx2J0qqEjWC65a0BMXkdVEYQo44MRq0yZK7Txgqbp9GbSWfexAXQhBEwtLg%3D%3D&contentTypeId="+contTypeId+"&areaCode=33&sigunguCode=&cat1=&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=B&numOfRows=634&pageNo=1&_type=json");
                                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                        conn.setDefaultUseCaches(false);
                                        conn.setDoInput(true);
                                        conn.setDoOutput(false);
                                        conn.setRequestMethod("GET");

                                        if (conn != null) {
                                            conn.setConnectTimeout(10000);
                                            conn.setUseCaches(false);

                                            if (conn.getResponseCode() >= 200 || conn.getResponseCode() < 300) { //접속 잘 되었는지 안되었는지 파악
                                                Log.i(TAG, "ii1");
                                                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); // InputStreamReader로 가져온다음 Buffer에 넣으면 이상하게 에러가 남, 그냥 바로 넣을것.
                                                Log.i(TAG, "ii2");
                                                for (; ; ) {
                                                    String buf = "";
                                                    buf = br.readLine();
                                                    StringBuffer sb = new StringBuffer();
                                                    sb.append(buf);
                                                    Log.i(TAG, buf);
                                                    if (buf == null) {
                                                        Log.i(TAG, "break");
                                                        break;
                                                    }
                                                    JSONObject result = new JSONObject(buf);
                                                    JSONArray results = result.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item"); //가장 큰 테두리부터 갈라갈라 가져오기
                                                    Log.i("^^", "" + result.toString());

                                                    for (int i = 0; i < results.length(); i++) {
                                                        JSONObject json = results.getJSONObject(i);
                                                        name = json.getString("title");
                                                        address = json.getString("addr1");
                                                        contId = json.getInt("contentid");
                                                        if (name.contains(searchQuery) || address.contains(searchQuery)) {
                                                            isEmpty = false;
                                                            try {
                                                                Log.i("%%%%%%", "ii7" + name);
                                                                imgUrl = json.getString("firstimage");
                                                                URL imgurl = new URL(imgUrl);
                                                                InputStream is = (InputStream)imgurl.getContent();
                                                                bmimg = BitmapFactory.decodeStream(is);
                                                                items.add(new TourItem(name, address, bmimg, contId));

                                                            } catch (Exception ee) {
                                                                Log.i("%%%%%%", "ii9" + name);
                                                                BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.default_img);
                                                                bmimg = drawable.getBitmap();
                                                                items.add(new TourItem(name, address, bmimg, contId));

                                                            }
                                                        }

                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                if(isEmpty) {
                                                                    tv.setVisibility(View.VISIBLE);
                                                                }else {
                                                                    tv.setVisibility(View.GONE);
                                                                }
                                                                adapter.notifyDataSetChanged();
                                                            }
                                                        });

                                                    }

                                                }
                                                br.close();
                                            }
                                            conn.disconnect();
                                        }
                                    } catch (Exception e) {
                                        Log.e(TAG, "ii7");
                                        Log.w(TAG, e.getMessage());
                                    }
                                }
                            });
                            t.start();
                        }
                        else{

                        }
                }
            }

        };



        back.setOnClickListener(listener);
        search.setOnClickListener(listener);




    }

    private void hideKeyboard(){
        imm.hideSoftInputFromWindow(keyword.getWindowToken(), 0);
    }



    public void onPause(){
        t.interrupt();
        super.onPause();
    }
}

//지금 카드뷰 클릭해도 스레드가 백그라운드에서 계속 돌고 있다.