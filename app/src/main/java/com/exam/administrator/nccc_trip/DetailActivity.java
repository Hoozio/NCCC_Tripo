package com.exam.administrator.nccc_trip;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    private static final String apiKey = "P6bhFFBWwGkij2sSFyuE1fYOhmljx2J0qqEjWC65a0BMXkdVEYQo44MRq0yZK7Txgqbp9GbSWfexAXQhBEwtLg%3D%3D";

    TourItem items;
    public int contentId;

    private String title;
    private String detailInfo;
    private String address;
    private String homePage;
    private Bitmap img;

    Toolbar toolbar;
    TextView subject;
    ImageView back;
    ImageView searcher;
    TextView titleTv;
    TextView detailShortTv;
    TextView detailLongTv;
    FrameLayout moreTxt;
    TextView addrTv;
    TextView homeTv;
    ImageView imageView;

    DetailOnClickListener listener;
    TextView startDate;
    TextView endDate;
    ImageView addCal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        items = bundle.getParcelable("nccc");
        contentId = items.getContInt();

        toolbar = (Toolbar) findViewById(R.id.etc_toolbar);
        toolbar.setContentInsetsAbsolute(0,0);
        setSupportActionBar(toolbar);

        subject = (TextView) findViewById(R.id.etc_subject);
        back = (ImageView)findViewById(R.id.etc_back);
        searcher = (ImageView)findViewById(R.id.etc_searcher);

        subject.setText("상세 정보");
        titleTv = (TextView) findViewById(R.id.detail_title);
        detailShortTv = (TextView) findViewById(R.id.detailtxt_short);
        detailLongTv = (TextView) findViewById(R.id.detailtxt_long);
        moreTxt = (FrameLayout)findViewById(R.id.detail_more);
        addrTv = (TextView) findViewById(R.id.detail_address);
        homeTv = (TextView) findViewById(R.id.detail_homepage);
        imageView = (ImageView) findViewById(R.id.detail_img);

        startDate = (TextView)findViewById(R.id.detail_start_date);
        endDate = (TextView)findViewById(R.id.detail_end_date);
        addCal = (ImageView)findViewById(R.id.detail_add_calendar);

        startDate.setText(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
        endDate.setText(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));

        listener = new DetailOnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.etc_back:
                        DetailActivity.this.finish();
                        break;
                    case R.id.etc_searcher:
                        Intent i = new Intent(DetailActivity.this, SearchActivity.class);
                        startActivity(i);
                        break;
                    case R.id.detail_start_date:
                        String str = String.valueOf(endDate.getText());
                        Calendar c = Calendar.getInstance();
                        int cyear = c.get(Calendar.YEAR);
                        int cmonth = c.get(Calendar.MONTH);
                        int cday = c.get(Calendar.DAY_OF_MONTH);
                        final String[] data = str.split("/");
                        DatePickerDialog.OnDateSetListener mDateSetListener =
                                new DatePickerDialog.OnDateSetListener() {
                                    // onDateSet method
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        startDate.setText(String.valueOf(year) + "/" + String.valueOf(monthOfYear+1) + "/" + String.valueOf(dayOfMonth));
                                        if(Integer.parseInt(data[0]) < Integer.parseInt(String.valueOf(year))){
                                            data[0]=String.valueOf(year);
                                        }else if(Integer.parseInt(data[1]) < Integer.parseInt(String.valueOf(monthOfYear+1))){
                                            data[1] = String.valueOf(monthOfYear+1);
                                        }else if(Integer.parseInt(data[2]) < Integer.parseInt(String.valueOf(dayOfMonth))){
                                            data[2] = String.valueOf(dayOfMonth);
                                        }
                                        endDate.setText(data[0]+"/"+data[1]+"/"+data[2]);
                                    }
                                };
                        DatePickerDialog alert = new DatePickerDialog(DetailActivity.this, mDateSetListener, cyear, cmonth, cday);
                        alert.show();
                        break;
                    case R.id.detail_end_date:
                        String str1 = String.valueOf(startDate.getText());
                        Calendar c1 = Calendar.getInstance();
                        int cyear1 = c1.get(Calendar.YEAR);
                        int cmonth1 = c1.get(Calendar.MONTH);
                        int cday1 = c1.get(Calendar.DAY_OF_MONTH);
                        final String[] data1 = str1.split("/");
                        DatePickerDialog.OnDateSetListener mDateSetListener1 =
                                new DatePickerDialog.OnDateSetListener() {
                                    // onDateSet method
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        endDate.setText(String.valueOf(year) + "/" + String.valueOf(monthOfYear+1) + "/" + String.valueOf(dayOfMonth));
                                        if(Integer.parseInt(data1[0]) > Integer.parseInt(String.valueOf(year))){
                                            data1[0]=String.valueOf(year);
                                        }else if(Integer.parseInt(data1[1]) > Integer.parseInt(String.valueOf(monthOfYear+1))){
                                            data1[1] = String.valueOf(monthOfYear+1);
                                        }else if(Integer.parseInt(data1[2]) > Integer.parseInt(String.valueOf(dayOfMonth))){
                                            data1[2] = String.valueOf(dayOfMonth);
                                        }
                                        startDate.setText(data1[0]+"/"+data1[1]+"/"+data1[2]);
                                    }
                                };
                        DatePickerDialog alert1 = new DatePickerDialog(DetailActivity.this, mDateSetListener1, cyear1, cmonth1, cday1);
                        alert1.show();
                        break;
                    case R.id.detail_add_calendar:
                        break;
                    case R.id.detail_more:
                        LinearLayout l1 = (LinearLayout) findViewById(R.id.first_detail_laytout);
                        LinearLayout l2 = (LinearLayout) findViewById(R.id.second_detail_layout);
                        l1.setVisibility(LinearLayout.GONE);
                        l2.setVisibility(LinearLayout.VISIBLE);
                        moreTxt.setVisibility(FrameLayout.GONE);
                        break;
                }
            }
        };
        back.setOnClickListener(listener);
        searcher.setOnClickListener(listener);
        startDate.setOnClickListener(listener);
        endDate.setOnClickListener(listener);
        addCal.setOnClickListener(listener);
        moreTxt.setOnClickListener(listener);

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
                                    homePage = "<br>";
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
            detailShortTv.setText(Html.fromHtml(detailInfo));
            detailLongTv.setText(Html.fromHtml(detailInfo));
            homeTv.setText(Html.fromHtml(homePage));
            homeTv.setMovementMethod(LinkMovementMethod.getInstance());
            imageView.setImageBitmap(img);
        }catch (Exception e){

        }
    }
}
