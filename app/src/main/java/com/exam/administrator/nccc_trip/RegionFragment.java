package com.exam.administrator.nccc_trip;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by Administrator on 2017-07-04.
 */

public class RegionFragment extends Fragment {
    public RegionFragment(){
    }
    private static final String apiKey = "P6bhFFBWwGkij2sSFyuE1fYOhmljx2J0qqEjWC65a0BMXkdVEYQo44MRq0yZK7Txgqbp9GbSWfexAXQhBEwtLg%3D%3D";

    ImageButton im;
    ImageButton im2;
    ImageButton im3;
    ImageButton im4;
    ImageButton im5;
    ImageButton im6;
    ImageButton im7;
    ImageButton im9;
    ImageButton im10;
    ImageButton im11;
    ImageButton im12;

    TextView festCount;
    ConstraintLayout map_layout;
    int sigungu;
    RegionOnCLickListener listener;
    Drawable back;
    RegionHandler regionHandler;
    boolean isPicasso = true;

    Thread t;
    String startFest;
    String endFest;
    String total;

    ImageView fim;
    ImageView fim2;
    ImageView fim3;
    ImageView fim4;
    ImageView fim5;
    ImageView fim6;
    ImageView fim7;
    ImageView fim8;
    ImageView fim9;
    ImageView fim10;
    ImageView fim11;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_region, container, false);
        map_layout = (ConstraintLayout) view.findViewById(R.id.map_layout);
        regionHandler = new RegionHandler();
        Picasso.with(getActivity().getApplicationContext()).load("http://222.116.135.79:8080/nccc_t/img/map.png").into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                map_layout.setBackground(new BitmapDrawable(bitmap));
                back = null;
                isPicasso = false;
                map_layout.setBackground(new BitmapDrawable(bitmap));
            }
            @Override public void onBitmapFailed(Drawable drawable) {
                Log.i("fffff", "ffffff");
                isPicasso = false;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Message msgTo = Message.obtain();
                            msgTo.what = 1;
                            regionHandler.sendMessage(msgTo);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
            @Override public void onPrepareLoad(Drawable drawable) {}
        });

        if(isPicasso){
            Log.i("ifif","ifif");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Message msgTo = Message.obtain();
                        msgTo.what = 1;
                        regionHandler.sendMessage(msgTo);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        im = (ImageButton) view.findViewById(R.id.danyang);
        im2 = (ImageButton) view.findViewById(R.id.jecheon);
        im3 = (ImageButton) view.findViewById(R.id.chungju);
        im4 = (ImageButton) view.findViewById(R.id.eumseong);
        im5 = (ImageButton) view.findViewById(R.id.goesan);
        im6 = (ImageButton) view.findViewById(R.id.jincheon);
        im7 = (ImageButton) view.findViewById(R.id.jeungpyeong);
        im9 = (ImageButton) view.findViewById(R.id.cheongju);
        im10 = (ImageButton) view.findViewById(R.id.boeun);
        im11 = (ImageButton) view.findViewById(R.id.ohkcheon);
        im12 = (ImageButton) view.findViewById(R.id.yeongdong);
        festCount = (TextView)view.findViewById(R.id.festival_count);

        fim = (ImageView) view.findViewById(R.id.danyang_fest);
        fim2 = (ImageView) view.findViewById(R.id.jecheon_fest);
        fim3 = (ImageView) view.findViewById(R.id.chungju_fest);
        fim4 = (ImageView) view.findViewById(R.id.eumseong_fest);
        fim5 = (ImageView) view.findViewById(R.id.goesan_fest);
        fim6 = (ImageView) view.findViewById(R.id.jincheon_fest);
        fim7 = (ImageView) view.findViewById(R.id.jeungpyeong_fest);
        fim8 = (ImageView) view.findViewById(R.id.cheongju_fest);
        fim9 = (ImageView) view.findViewById(R.id.boeun_fest);
        fim10 = (ImageView) view.findViewById(R.id.ohkcheon_fest);
        fim11 = (ImageView) view.findViewById(R.id.yeongdong_fest);

        fim.setVisibility(View.GONE);
        fim2.setVisibility(View.GONE);
        fim3.setVisibility(View.GONE);
        fim4.setVisibility(View.GONE);
        fim5.setVisibility(View.GONE);
        fim6.setVisibility(View.GONE);
        fim7.setVisibility(View.GONE);
        fim8.setVisibility(View.GONE);
        fim9.setVisibility(View.GONE);
        fim10.setVisibility(View.GONE);
        fim11.setVisibility(View.GONE);

        listener = new RegionOnCLickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.danyang_fest:
                    case R.id.danyang:
                        Log.e("dddd", "단양 클릭");
                        sigungu = 2;
                        break;
                    case R.id.jecheon_fest:
                    case R.id.jecheon:
                        sigungu = 7;
                        Log.e("dddd", "제천 클릭");
                        break;
                    case R.id.chungju_fest:
                    case R.id.chungju:
                        sigungu = 11;
                        Log.e("dddd", "충주 클릭");
                        break;
                    case R.id.eumseong_fest:
                    case R.id.eumseong:
                        Log.e("dddd", "음성 클릭");
                        sigungu = 6;
                        break;
                    case R.id.goesan_fest:
                    case R.id.goesan:
                        Log.e("dddd", "괴산 클릭");
                        sigungu = 1;
                        break;
                    case R.id.jincheon_fest:
                    case R.id.jincheon:
                        sigungu = 8;
                        Log.e("dddd", "진천 클릭");
                        break;
                    case R.id.jeungpyeong_fest:
                    case R.id.jeungpyeong:
                        sigungu = 12;
                        Log.e("dddd", "증평 클릭");
                        break;
                    case R.id.cheongju_fest:
                    case R.id.cheongju:
                        sigungu = 10;
                        Log.e("dddd", "청주 클릭");
                        break;
                    case R.id.boeun_fest:
                    case R.id.boeun:
                        Log.e("dddd", "보은 클릭");
                        sigungu = 3;
                        break;
                    case R.id.ohkcheon_fest:
                    case R.id.ohkcheon:
                        sigungu = 5;
                        Log.e("dddd", "옥천 클릭");
                        break;
                    case R.id.yeongdong_fest:
                    case R.id.yeongdong:
                        Log.e("dddd", "영동 클릭");
                        sigungu = 4;
                        break;
                }
                MainActivity main = (MainActivity)getActivity();
                main.setSigungu(sigungu);
                main.tabLayout.getTabAt(1).select();
                main.shiftFragment(1,sigungu);
            }
        };

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
        t = new Thread(new Runnable() { // 반드시 스레드 이용 그래야 반복해서 쓸수 있다고 함
            @Override
            public void run() {
                try {
                    URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?ServiceKey="+apiKey+"&eventStartDate="+startFest+"&eventEndDate="+endFest+"&areaCode=33&sigunguCode=&cat1=&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=60&pageNo=1&_type=json");
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
                                Log.e("*(*(", buf.toString());
                                JSONObject totalCount = result.getJSONObject("response").getJSONObject("body"); //가장 큰 테두리부터 갈라갈라 가져오기
                                int count = totalCount.getInt("totalCount");
                                total = String.format("축제 %d건",count);
                                JSONArray results = result.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item"); //가장 큰 테두리부터 갈라갈라 가져오기
                                for (int i=0; i < count; i++){
                                    JSONObject json = results.getJSONObject(i);
                                    switch (json.getInt("sigungucode")){
                                        case 1:
                                            fim5.setVisibility(View.VISIBLE);
                                            break;
                                        case 2:
                                            fim.setVisibility(View.VISIBLE);
                                            break;
                                        case 3:
                                            fim9.setVisibility(View.VISIBLE);
                                            break;
                                        case 4:
                                            fim11.setVisibility(View.VISIBLE);
                                            break;
                                        case 5:
                                            fim10.setVisibility(View.VISIBLE);
                                            break;
                                        case 6:
                                            fim4.setVisibility(View.VISIBLE);
                                            break;
                                        case 7:
                                            fim2.setVisibility(View.VISIBLE);
                                            break;
                                        case 8:
                                            fim6.setVisibility(View.VISIBLE);
                                            break;
                                        case 10:
                                            fim8.setVisibility(View.VISIBLE);
                                            break;
                                        case 11:
                                            fim3.setVisibility(View.VISIBLE);
                                            break;
                                        case 12:
                                            fim7.setVisibility(View.VISIBLE);
                                            break;

                                    }
                                }

                            }
                        }
                        conn.disconnect();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        try {
            t.join();
        }catch(Exception e){
            e.printStackTrace();
        }
        im.setOnClickListener(listener);
        im2.setOnClickListener(listener);
        im3.setOnClickListener(listener);
        im4.setOnClickListener(listener);
        im5.setOnClickListener(listener);
        im6.setOnClickListener(listener);
        im7.setOnClickListener(listener);
        im9.setOnClickListener(listener);
        im10.setOnClickListener(listener);
        im11.setOnClickListener(listener);
        im12.setOnClickListener(listener);

        fim.setOnClickListener(listener);
        fim2.setOnClickListener(listener);
        fim3.setOnClickListener(listener);
        fim4.setOnClickListener(listener);
        fim5.setOnClickListener(listener);
        fim6.setOnClickListener(listener);
        fim7.setOnClickListener(listener);
        fim8.setOnClickListener(listener);
        fim9.setOnClickListener(listener);
        fim10.setOnClickListener(listener);
        fim11.setOnClickListener(listener);

        festCount.setText(total);
        return view;
    }

    class RegionHandler extends Handler{
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    Picasso.with(getActivity().getApplicationContext()).load("http://222.116.135.79:8080/nccc_t/img/map.png").into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                            map_layout.setBackground(new BitmapDrawable(bitmap));
                        }
                        @Override public void onBitmapFailed(Drawable drawable) {}
                        @Override public void onPrepareLoad(Drawable drawable) {}
                    });
                    break;
            }
        }
    }
    @Override
    public void onPause(){
        t.interrupt();
        super.onPause();
    }
}
