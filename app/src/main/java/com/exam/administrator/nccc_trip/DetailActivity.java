package com.exam.administrator.nccc_trip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {
    private static final String apiKey = "P6bhFFBWwGkij2sSFyuE1fYOhmljx2J0qqEjWC65a0BMXkdVEYQo44MRq0yZK7Txgqbp9GbSWfexAXQhBEwtLg%3D%3D";

    TourItem items;
    public int contentId;

    private String title;
    private String detailInfo;
    private String address;
    private String homePage;
    private Bitmap img;

    TextView subject;
    ImageView back;
    ImageView searcher;
    TextView titleTv;
    TextView detailShortTv;
    TextView detailLongTv;
    TextView addrTv;
    TextView homeTv;
    ImageView imageView;
    DetailOnClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        items = bundle.getParcelable("nccc");
        contentId = items.getContInt();

        subject = (TextView) findViewById(R.id.etc_subject);
        back = (ImageView)findViewById(R.id.etc_back);
        searcher = (ImageView)findViewById(R.id.etc_searcher);

        subject.setText("상세 정보");
        titleTv = (TextView) findViewById(R.id.detail_title);
        detailShortTv = (TextView) findViewById(R.id.detailtxt_short);
        detailLongTv = (TextView) findViewById(R.id.detailtxt_long);
        addrTv = (TextView) findViewById(R.id.detail_address);
        homeTv = (TextView) findViewById(R.id.detail_homepage);
        imageView = (ImageView) findViewById(R.id.detail_img);

        listener = new DetailOnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.etc_back:
                        DetailActivity.this.finish();
                        break;
                    case R.id.searcher:
                        Intent i = new Intent(DetailActivity.this, SearchActivity.class);
                        startActivity(i);
                        break;
                }
            }
        };
        back.setOnClickListener(listener);
        searcher.setOnClickListener(listener);

        Thread t = new Thread(new Runnable() { // 반드시 스레드 이용 그래야 반복해서 쓸수 있다고 함
            @Override
            public void run() {
                try {
                    URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?ServiceKey="+apiKey+"&contentId="+contentId+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y&_type=json");
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
                                JSONObject json = result.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item");
                                title = json.getString("title");
                                detailInfo = json.getString("overview");
                                address = json.getString("addr1");
                                try{
                                    homePage = json.getString("homepage");
                                }catch (Exception e){
                                    homePage = null;
                                }
                                try {
                                    URL imgurl = new URL(json.getString("firstimage"));
                                    HttpURLConnection imgConn = (HttpURLConnection) imgurl.openConnection();
                                    imgConn.setDoInput(true);
                                    imgConn.connect();
                                    InputStream is = imgConn.getInputStream();
                                    img = BitmapFactory.decodeStream(is);
                                    imgConn.disconnect();
                                }catch (Exception e){
                                    BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.menu);
                                    img = drawable.getBitmap();
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
            titleTv.setText(title);
            addrTv.setText(address);
            detailShortTv.setText(detailInfo);
            detailLongTv.setText(detailInfo);
            homeTv.setText(homePage);
            imageView.setImageBitmap(img);
        }catch (Exception e){

        }
    }
}
