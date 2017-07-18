package com.exam.administrator.nccc_trip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class HotelFragment extends Fragment {
    public HotelFragment(){
    }

    String apiKey = "P6bhFFBWwGkij2sSFyuE1fYOhmljx2J0qqEjWC65a0BMXkdVEYQo44MRq0yZK7Txgqbp9GbSWfexAXQhBEwtLg%3D%3D";
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<TourItem> items;
    private String name;
    private String address;
    private Bitmap bmimg;
    private int contId;
    String sigungu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_hotel, container, false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.best_bar_image);



        if(Integer.parseInt(getArguments().getString("sigungu"))<=12&&Integer.parseInt(getArguments().getString("sigungu"))>0){
            sigungu = getArguments().getString("sigungu");
        } else{
            sigungu = "";
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        items = new ArrayList();
        layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new TourAdapter(items, view.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        final GestureDetector gestureDetector = new GestureDetector(view.getContext(),new GestureDetector.SimpleOnGestureListener()
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
                    Intent i = new Intent(view.getContext(),DetailActivity.class);
                    i.putExtra("what",2);
                    i.putExtra("hotel",items.get(rv.getChildAdapterPosition(child)).getContInt());
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
        Thread t = new Thread(new Runnable() { // 반드시 스레드 이용 그래야 반복해서 쓸수 있다고 함
            @Override
            public void run() {
                try {
                    URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey="+apiKey+"&contentTypeId=32&areaCode=33&sigunguCode="+sigungu+"&cat1=&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=12&pageNo=1&_type=json");
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

                                for (int i = 0; i < results.length(); i++) {
                                    JSONObject json = results.getJSONObject(i);
                                    name = json.getString("title");
                                    address = json.getString("addr1");
                                    contId = json.getInt("contentid");
                                    Log.e("d33333fd", name);
                                    Log.e("33333dfd", address);
                                    try {
                                        URL imgurl = new URL(json.getString("firstimage"));
                                        HttpURLConnection imgConn = (HttpURLConnection) imgurl.openConnection();
                                        imgConn.setDoInput(true);
                                        imgConn.connect();
                                        InputStream is = imgConn.getInputStream();
                                        bmimg = BitmapFactory.decodeStream(is);
                                        items.add(new TourItem(name, address, bmimg, contId));
                                        imgConn.disconnect();
                                    }catch (Exception e){
                                        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.default_img);
                                        bmimg = drawable.getBitmap();
                                        items.add(new TourItem(name, address, bmimg, contId));
                                    }

                                }

                            }
                        }
                        conn.disconnect();
                    }
                }
                catch(Exception e) {
                }
            }
        });
        t.start();
        try{
            t.join();
        }
        catch (Exception eoeo){

        }
        t.interrupt();
        return view;
    }

}