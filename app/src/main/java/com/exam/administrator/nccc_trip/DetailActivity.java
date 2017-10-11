package com.exam.administrator.nccc_trip;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    private static final String apiKey = "P6bhFFBWwGkij2sSFyuE1fYOhmljx2J0qqEjWC65a0BMXkdVEYQo44MRq0yZK7Txgqbp9GbSWfexAXQhBEwtLg%3D%3D";
    private final String mapKey = "4c67c726a2a2fcd5dca77e3e381d9629";

    TourItem items;
    int contentId;
    int contTypeId;
    private String cat2;
    private String cat3;

    private String title;
    private String detailInfo;
    private String address;
    private String homePage;
    private Bitmap img = null;
    String imgUrl;
    private double mapx;
    private double mapy;

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
    TextView startTime;
    TextView endTime;
    ImageView addCal;
    ImageView deleteCal;
    TextView tel;
    Thread t;
    ScrollView sv;
    DetailHandler detailHandler;

    boolean ismap = false;
    TextView detail_map_more;
    FrameLayout map_view_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        t = new Thread();

        toolbar = (Toolbar) findViewById(R.id.etc_toolbar);
        toolbar.setContentInsetsAbsolute(0,0);
        setSupportActionBar(toolbar);

        detailHandler = new DetailHandler();

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

        sv = (ScrollView) findViewById(R.id.scroll);
        sv.post(new Runnable() {
            @Override
            public void run() {
                sv.scrollTo(0,0);
            }
        });

        startDate = (TextView)findViewById(R.id.detail_start_date);
        startTime = (TextView)findViewById(R.id.detail_start_time);
        endTime = (TextView)findViewById(R.id.detail_end_time);
        addCal = (ImageView)findViewById(R.id.detail_add_calendar);
        deleteCal = (ImageView)findViewById(R.id.detail_delete_calendar);
        tel = (TextView)findViewById(R.id.detail_tel);


        startDate.setText(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));

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
                        Calendar c = Calendar.getInstance();
                        int cyear = c.get(Calendar.YEAR);
                        int cmonth = c.get(Calendar.MONTH);
                        int cday = c.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog.OnDateSetListener mDateSetListener =
                                new DatePickerDialog.OnDateSetListener() {
                                    // onDateSet method
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        startDate.setText(String.valueOf(year) + "/" + String.format("%02d",monthOfYear+1) + "/" + String.format("%02d",dayOfMonth));
                                    }
                                };
                        DatePickerDialog alert = new DatePickerDialog(DetailActivity.this, mDateSetListener, cyear, cmonth, cday);
                        alert.show();
                        break;
                    case R.id.detail_start_time:
                        TimePickerDialog.OnTimeSetListener mTimeSetListener =
                                new TimePickerDialog.OnTimeSetListener() {
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        startTime.setText(String.format("%02d",hourOfDay)+":00");
                                    }
                                };
                        CustomTimePickerDialog tAlert = new CustomTimePickerDialog(DetailActivity.this, mTimeSetListener, 0, 0, true);
                        tAlert.show();
                        break;
                    case R.id.detail_end_time:
                        TimePickerDialog.OnTimeSetListener mTimeSetListener1 =
                                new TimePickerDialog.OnTimeSetListener() {
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        endTime.setText(String.format("%02d",hourOfDay)+":00");
                                    }
                                };
                        CustomTimePickerDialog tAlert1 = new CustomTimePickerDialog(DetailActivity.this, mTimeSetListener1, 0, 0, true);
                        tAlert1.show();
                        break;
                    case R.id.detail_add_calendar:
                        AlertDialog.Builder alt_bld = new AlertDialog.Builder(DetailActivity.this);
                        alt_bld.setMessage("일정을 추가하시겠습니까?").setCancelable(
                                false).setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        String str = null;
                                        String id = getDeviceId();
                                        String title = titleTv.getText().toString();
                                        String date[] = startDate.getText().toString().split("/");
                                        String start[] = startTime.getText().toString().split(":");
                                        String end[] = endTime.getText().toString().split(":");
                                        if (Integer.parseInt(start[0]) < Integer.parseInt(end[0])) {
                                            Dbload dbload = new Dbload();
                                            try {
                                                str = dbload.execute(id, title, date[0], date[1], date[2], start[0], end[0],getCat2(),getCat3(), String.valueOf(getContentId()), String.valueOf(getContTypeId())).get();
                                                Log.e("ddddddd", "DDDDDDDDDDdddddddd            "+str);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            if(str==null) {
                                                AlertDialog.Builder ab = new AlertDialog.Builder(DetailActivity.this);
                                                ab.setMessage("날짜가 겹칩니다. 다시 확인해주세요.");
                                                ab.setPositiveButton("확인", null);
                                                ab.show();
                                            }else{
                                                AlertDialog.Builder ab = new AlertDialog.Builder(DetailActivity.this);
                                                ab.setMessage("추가되었습니다.");
                                                ab.setPositiveButton("확인", null);
                                                ab.show();
                                            }
                                        }else {
                                            AlertDialog.Builder ab = new AlertDialog.Builder(DetailActivity.this);
                                            ab.setMessage("시간이 잘못되었습니다. 다시 확인해주세요!");
                                            ab.setPositiveButton("확인", null);
                                            ab.show();
                                        }
                                    }
                                }).setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Action for 'NO' Button
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog aAlert = alt_bld.create();
                        // Title for AlertDialog
                        aAlert.setTitle("알림");
                        aAlert.show();
                        break;
                    case R.id.detail_delete_calendar:
                        AlertDialog.Builder ab = new AlertDialog.Builder(DetailActivity.this);
                        ab.setTitle("삭제");
                        ab.setMessage("일정 삭제할까요?").setCancelable(
                                false).setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        DeleteSchedule del = new DeleteSchedule();
                                        try {
                                            del.execute(getDeviceId(), title).get();
                                        }catch (Exception e){

                                        }
                                        AlertDialog.Builder ab = new AlertDialog.Builder(DetailActivity.this);
                                        ab.setMessage("삭제되었습니다.");
                                        ab.setPositiveButton("확인", null);
                                        ab.show();
                                    }
                                }).setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Action for 'NO' Button
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dAlert = ab.create();
                        // Title for AlertDialog
                        dAlert.show();
                        break;
                    case R.id.detail_more:
                        LinearLayout l1 = (LinearLayout) findViewById(R.id.first_detail_laytout);
                        LinearLayout l2 = (LinearLayout) findViewById(R.id.second_detail_layout);
                        l1.setVisibility(LinearLayout.GONE);
                        l2.setVisibility(LinearLayout.VISIBLE);
                        moreTxt.setVisibility(FrameLayout.GONE);
                        break;
                    case R.id.detail_tel:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { /** * 사용자 단말기의 권한 중 "전화걸기" 권한이 허용되어 있는지 확인한다. * Android는 C언어 기반으로 만들어졌기 때문에 Boolean 타입보다 Int 타입을 사용한다. */
                            int permissionResult = checkSelfPermission(android.Manifest.permission.CALL_PHONE); /** * 패키지는 안드로이드 어플리케이션의 아이디이다. * 현재 어플리케이션이 CALL_PHONE에 대해 거부되어있는지 확인한다. */
                            if (permissionResult == PackageManager.PERMISSION_DENIED) { /** * 사용자가 CALL_PHONE 권한을 거부한 적이 있는지 확인한다. * 거부한적이 있으면 True를 리턴하고 * 거부한적이 없으면 False를 리턴한다. */
                                if (shouldShowRequestPermissionRationale(android.Manifest.permission.CALL_PHONE)) {
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(DetailActivity.this);
                                    dialog.setTitle("권한이 필요합니다.")
                                            .setMessage("이 기능을 사용하기 위해서는 단말기의 \"전화걸기\" 권한이 필요합니다. 계속 하시겠습니까?")
                                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                                @Override public void onClick(DialogInterface dialog, int which) { /** * 새로운 인스턴스(onClickListener)를 생성했기 때문에 * 버전체크를 다시 해준다. */
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // CALL_PHONE 권한을 Android OS에 요청한다.
                                                        requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 1000);
                                                    }
                                                }
                                            })
                                            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                                @Override public void onClick(DialogInterface dialog, int which) {

                                                }
                                            }).create() .show();
                                } // 최초로 권한을 요청할 때
                                else { // CALL_PHONE 권한을 Android OS에 요청한다.
                                    requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 1000);
                                }
                            } // CALL_PHONE의 권한이 있을 때
                            else { // 즉시 실행
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-1111-2222"));
                                startActivity(intent);
                            }
                        } // 마시멜로우 미만의 버전일 때
                        else { // 즉시 실행
                             Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-1111-2222"));
                            startActivity(intent);
                        }
                }
            }
        };
        back.setOnClickListener(listener);
        searcher.setOnClickListener(listener);
        startDate.setOnClickListener(listener);
        addCal.setOnClickListener(listener);
        deleteCal.setOnClickListener(listener);
        moreTxt.setOnClickListener(listener);
        startTime.setOnClickListener(listener);
        endTime.setOnClickListener(listener);
        switch (getIntent().getIntExtra("what",0)){
            case 0:
                contentId = getIntent().getIntExtra("tour",0);
                t = new Thread(new Runnable() { // 반드시 스레드 이용 그래야 반복해서 쓸수 있다고 함
                    @Override
                    public void run() {
                        try {
                            Log.e("t", "@@@@@@@@@@@@@@###########"+ Process.myTid());
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
                                        setMap(json.getDouble("mapx"),json.getDouble("mapy"));
                                        setCat2(json.getString("cat2"));
                                        setCat3(json.getString("cat3"));
                                        setContTypeId(Integer.parseInt(json.getString("contenttypeid")));
                                        try{
                                            tel.setText(json.getString("tel"));
                                        }catch (Exception e){
                                            tel.setText("");
                                            tel.setVisibility(View.GONE);
                                        }
                                        try{
                                            homePage = json.getString("homepage");
                                        }catch (Exception e){
                                            homePage = "<br>";
                                        }
                                        try {
                                            imgUrl = json.getString("firstimage");
                                            Message msgTo = Message.obtain();
                                            msgTo.what = 1;
                                            msgTo.obj = imgUrl;
                                            detailHandler.sendMessage(msgTo);

                                        }catch (Exception e){
                                            imgUrl = "http://222.116.135.79:8080/nccc_t/img/default_img.jpg";
                                            Message msgTo = Message.obtain();
                                            msgTo.what = 1;
                                            msgTo.obj = imgUrl;
                                            detailHandler.sendMessage(msgTo);
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
                break;
            case 1:
                contentId = getIntent().getIntExtra("tour",0);
                t = new Thread(new Runnable() { // 반드시 스레드 이용 그래야 반복해서 쓸수 있다고 함
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?ServiceKey="+apiKey+"&contentTypeId=39&contentId="+contentId+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y&_type=json");
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
                                        setMap(json.getDouble("mapx"),json.getDouble("mapy"));
                                        setCat2(json.getString("cat2"));
                                        setCat3(json.getString("cat3"));
                                        setContTypeId(Integer.parseInt(json.getString("contenttypeid")));
                                        try{
                                            tel.setText(json.getString("tel"));
                                        }catch (Exception e){
                                            tel.setText("");
                                            tel.setVisibility(View.GONE);
                                        }
                                        try{
                                            homePage = json.getString("homepage");
                                        }catch (Exception e){
                                            homePage = "<br>";
                                        }
                                        try {
                                            imgUrl = json.getString("firstimage");
                                            Message msgTo = Message.obtain();
                                            msgTo.what = 1;
                                            msgTo.obj = imgUrl;
                                            detailHandler.sendMessage(msgTo);

                                        }catch (Exception e){
                                            imgUrl = "http://222.116.135.79:8080/nccc_t/img/default_img.jpg";
                                            Message msgTo = Message.obtain();
                                            msgTo.what = 1;
                                            msgTo.obj = imgUrl;
                                            detailHandler.sendMessage(msgTo);
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
                break;
            case 2:
                contentId = getIntent().getIntExtra("tour",0);
                t = new Thread(new Runnable() { // 반드시 스레드 이용 그래야 반복해서 쓸수 있다고 함
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?ServiceKey="+apiKey+"&contentTypeId=32&contentId="+contentId+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y&_type=json");
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
                                        setMap(json.getDouble("mapx"),json.getDouble("mapy"));
                                        setCat2(json.getString("cat2"));
                                        setCat3(json.getString("cat3"));
                                        setContTypeId(Integer.parseInt(json.getString("contenttypeid")));
                                        try{
                                            tel.setText(json.getString("tel"));
                                        }catch (Exception e){
                                            tel.setText("");
                                            tel.setVisibility(View.GONE);
                                        }
                                        try{
                                            homePage = json.getString("homepage");
                                        }catch (Exception e){
                                            homePage = "<br>";
                                        }
                                        try {
                                            imgUrl = json.getString("firstimage");
                                            Message msgTo = Message.obtain();
                                            msgTo.what = 1;
                                            msgTo.obj = imgUrl;
                                            detailHandler.sendMessage(msgTo);

                                        }catch (Exception e){
                                            imgUrl = "http://222.116.135.79:8080/nccc_t/img/default_img.jpg";
                                            Message msgTo = Message.obtain();
                                            msgTo.what = 1;
                                            msgTo.obj = imgUrl;
                                            detailHandler.sendMessage(msgTo);
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
                break;
            case 3:
                final int subId = getIntent().getIntExtra("detail",0);
                contentId = getIntent().getIntExtra("course",0);
                setMap(getIntent().getDoubleExtra("lon",0.0), getIntent().getDoubleExtra("lat",0.0));
                t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailInfo?ServiceKey="+apiKey+"&contentTypeId=25&contentId="+contentId+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&listYN=Y&_type=json");
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
                                        JSONArray results = result.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
                                        for(int i = 0; i< results.length(); i++) {
                                            JSONObject json = results.getJSONObject(i);
                                            if( subId == json.getInt("subcontentid")) {
                                                title = json.getString("subname");
                                                detailInfo = json.getString("subdetailoverview");
                                                address = "";
                                                try{
                                                    tel.setText(json.getString("tel"));
                                                }catch (Exception e){
                                                    tel.setText("");
                                                    tel.setVisibility(View.GONE);
                                                }
                                                try {
                                                    homePage = json.getString("homepage");
                                                } catch (Exception e) {
                                                    homePage = "<br>";
                                                }
                                                try {
                                                    URL imgurl = new URL(json.getString("subdetailimg"));
                                                    InputStream is = (InputStream)imgurl.getContent();
                                                    img = BitmapFactory.decodeStream(is);
                                                } catch (Exception e) {
                                                    BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.default_img);
                                                    img = drawable.getBitmap();
                                                }
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
                break;
        }
        t.start();

        try {
            t.join();
        }catch (Exception e){
            Log.i("sexsexsexsexsex",""+e);
        }


        titleTv.setText(title);
        addrTv.setText(address);
        detailShortTv.setText(Html.fromHtml(detailInfo));
        detailLongTv.setText(Html.fromHtml(detailInfo));
        if(img != null){
            imageView.setImageBitmap(img);
        }
        homeTv.setText(Html.fromHtml(homePage));
        homeTv.setMovementMethod(LinkMovementMethod.getInstance());

        detail_map_more = (TextView) findViewById(R.id.detail_map_more);
        detail_map_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intetnt = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + mapy +"," + mapx));
                startActivity(intetnt);
            }
        });
    }
    public void setMap(double mapx, double mapy){
        this.mapx = mapx;
        this.mapy = mapy;
    }
    public double getMapx(){
        return mapx;
    }
    public double getMapy(){
        return mapy;
    }
    @Override
    public void onPause(){
        t.interrupt();
        super.onPause();
    }





    private Drawable createDrawableFromUrl(String url) {
        try {
            InputStream is = (InputStream) this.fetch(url);
            Drawable d = Drawable.createFromStream(is, "src");
            return d;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Object fetch(String address) throws MalformedURLException,IOException {
        URL url = new URL(address);
        Object content = url.getContent();
        return content;
    }



    class Dbload extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg, checkMsg;
        protected String doInBackground(String... strings){
            try{
                String str;
                URL url = new URL("http://222.116.135.79:8080/nccc_t/checkSchedule.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "id_client=" + strings[0] + "&title=" + strings[1] + "&year=" + strings[2] + "&month=" + strings[3]+ "&day=" + strings[4]+ "&startTime=" + strings[5]+ "&endTime=" + strings[6];
                Log.e("@@@@@@@@##", "!!!!!!!!!!!!!!!!!!!!!!!@@@@@@@@@@@@@@@@@@@@@@"+strings[0]+" !!! "+strings[1]+" !!! "+strings[2]+" !!! "+strings[3]+" !!! "+strings[4]+" !!! "+strings[5]+" !!! "+strings[6]);
                osw.write(sendMsg);
                osw.flush();

                if(conn.getResponseCode() >= 200 && conn.getResponseCode() < 300){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    StringBuffer buffer = new StringBuffer();
                    Log.e("dd", "dddddddddd");
                    while((str = reader.readLine()) != null ){
                        buffer.append(str);
                    }
                    checkMsg = buffer.toString();
                    Log.e("ddddd","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+checkMsg);
                    conn.disconnect();
                    if (checkMsg.equals("")) {
                        Log.e("checkckeck", "dddddddddd");
                        URL url1 = new URL("http://222.116.135.79:8080/nccc_t/scheduleInsert.jsp");
                        HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
                        conn1.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        conn1.setRequestMethod("POST");
                        OutputStreamWriter osw1 = new OutputStreamWriter(conn1.getOutputStream());

                        sendMsg = "id_client=" + strings[0] + "&title=" + strings[1] + "&year=" + strings[2] + "&month=" + strings[3]+ "&day=" + strings[4]+ "&startTime=" + strings[5]+ "&endTime=" + strings[6]+"&category2="+strings[7] + "&category3=" +strings[8] + "&cont_id=" + strings[9] + "&cont_type_id="+ strings[10];
                        Log.e("", sendMsg);
                        osw1.write(sendMsg);
                        osw1.flush();
                        if(conn1.getResponseCode() >=200 && conn1.getResponseCode() < 300 ) {
                            InputStreamReader tmp = new InputStreamReader(conn1.getInputStream(), "UTF-8");
                            BufferedReader reader1 = new BufferedReader(tmp);
                            StringBuffer buffer1 = new StringBuffer();

                            while ((str = reader1.readLine()) != null) {
                                buffer1.append((str));
                            }
                            receiveMsg = buffer1.toString();
                            conn1.disconnect();
                        } else {
                            Log.i("통신 결과", conn.getResponseCode()+"에러");
                        }
                    } else {
                        Log.e("checkckeck", "YYYYYYYYYYYY");
                    }
                }
                else{
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }

            }
            catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }
    class DeleteSchedule extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg, checkMsg;
        protected String doInBackground(String... strings){
            try{
                String str;
                URL url = new URL("http://222.116.135.79:8080/nccc_t/deleteSchedule.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "id_client=" + strings[0] + "&title=" + strings[1];
                osw.write(sendMsg);
                osw.flush();

                if(conn.getResponseCode() >= 200 && conn.getResponseCode() < 300){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    StringBuffer buffer = new StringBuffer();
                    Log.e("dd", "dddddddddd");
                    while((str = reader.readLine()) != null ){
                        buffer.append(str);
                    }
                    checkMsg = buffer.toString();
                    Log.e("ddddd","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+checkMsg);
                    conn.disconnect();
                }
                else{
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }
            }
            catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }
    public String getDeviceId(){
        String idByANDROID_ID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        return idByANDROID_ID;
    }

    public String getCat2() {
        return cat2;
    }
    public String getCat3() {
        return cat3;
    }

    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }
    public void setCat3(String cat3) {
        this.cat3 = cat3;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getContTypeId() {
        return contTypeId;
    }

    public void setContTypeId(int contTypeId) {
        this.contTypeId = contTypeId;
    }
    class DetailHandler extends Handler{
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    Picasso.with(DetailActivity.this).load(msg.obj.toString()).into(imageView);
                    break;
            }
        }
    }
}