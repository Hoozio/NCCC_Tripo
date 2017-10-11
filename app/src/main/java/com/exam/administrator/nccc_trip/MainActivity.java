package com.exam.administrator.nccc_trip;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 2;
    TabLayout tabLayout;
    private Toolbar toolbar;
    ImageView menu;
    ImageView searcher;
    MenuOnClickListner menuListener;
    LocationManager locationManager;

    LinearLayout calendar;
    LinearLayout inventory;
    LinearLayout guide;
    LinearLayout setting;
    TextView age;
    ImageView sex;
    ImageView group;
    NavigationOnClickListener naviListener;
    DrawerLayout dLayout;

    RegionFragment regionFragment;
    TripFragment tripFragment;
    TasteFragment tasteFragment;
    HotelFragment hotelFragment;
    CourseFragment courseFragment;
    public static Context mContext;
    int sigungu;
    String id_client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermissionGPS();
        Intent splashIntent = getIntent();
        int age_client = splashIntent.getIntExtra("age_client", 0);
        int sex_client = splashIntent.getIntExtra("sex_client", 0);
        int group_client = splashIntent.getIntExtra("group_client", 0);

        Log.e("&*&*", ""+age_client);
        Log.e("&*&*", ""+sex_client);
        Log.e("&*&*", ""+group_client);


        sigungu = 0;
        menu = (ImageView) findViewById(R.id.menu);
        searcher = (ImageView) findViewById(R.id.searcher);
        dLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this,dLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        dLayout.setDrawerListener(toggle);
        toggle.syncState();
        GPSDialog();
        menuListener = new MenuOnClickListner() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.menu:
                        dLayout.openDrawer(GravityCompat.START,true);
                        break;
                    case R.id.searcher:
                        Intent i = new Intent(MainActivity.this,SearchActivity.class);
                        startActivity(i);
                        break;
                }
            }
        };
        menu.setOnClickListener(menuListener);
        searcher.setOnClickListener(menuListener);





        // Adding Toolbar to the activity
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setContentInsetsAbsolute(0,0);
        setSupportActionBar(toolbar);

        calendar = (LinearLayout)findViewById(R.id.calendar_icon);
        inventory = (LinearLayout)findViewById(R.id.inventory_icon);
        guide = (LinearLayout)findViewById(R.id.guide_icon);
        setting = (LinearLayout)findViewById(R.id.setting_icon);
        sex = (ImageView)findViewById(R.id.sex_button);
        age = (TextView)findViewById(R.id.age_button);
        group = (ImageView)findViewById(R.id.group_button);


        if(age_client != 0){
            age.setText(""+age_client);
        }
        if(sex_client == 1){
            sex.setImageResource(R.drawable.man);
        }else{
            sex.setImageResource(R.drawable.woman);
        }
        TextView tv = (TextView)findViewById(R.id.group_text);
        switch (group_client){
            case 1:
                group.setImageResource(R.drawable.alone);
                tv.setText("혼자");
                break;
            case 2:
                group.setImageResource(R.drawable.couple);
                tv.setText("커플");
                break;
            case 3:
                group.setImageResource(R.drawable.friend);
                tv.setText("친구");
                break;
            case 4:
                group.setImageResource(R.drawable.family);
                tv.setText("가족");
                break;
            case 5:
                group.setImageResource(R.drawable.group);
                tv.setText("단체");
                break;
        }
        id_client = getDeviceId();


        naviListener = new NavigationOnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                switch(v.getId()){
                    case R.id.calendar_icon:
                        i = new Intent(MainActivity.this,CalendarActivity.class);
                        startActivity(i);
                        dLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.inventory_icon:
                        i = new Intent(MainActivity.this,InventoryActivity.class);
                        startActivity(i);
                        dLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.guide_icon:
                        i = new Intent(MainActivity.this,GuideActivity.class);
                        startActivity(i);
                        dLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.setting_icon:
                        i = new Intent(MainActivity.this,SettingActivity.class);
                        startActivity(i);
                        dLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.age_button:
                        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                        ad.setTitle("나이 설정");       // 제목 설정
                        final EditText et = new EditText(MainActivity.this);
                        et.setFocusable(true);
                        et.setInputType(InputType.TYPE_CLASS_NUMBER);
                        InputFilter[] filter = new InputFilter[1];
                        filter[0] = new InputFilter.LengthFilter(2);
                        et.setHint("나이를 입력하세요.");
                        et.setFilters(filter);
                        ad.setView(et);

                        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String value = et.getText().toString();
                                if(!value.equals("")) {
                                    age.setText(value);
                                    //디비 접근해서 데이터 저장할 부분 넣기
                                    UpdateAge updateAge = new UpdateAge();
                                    updateAge.execute(id_client, value);
                                }
                                dialog.dismiss();     //닫기
                            }
                        });

                        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();     //닫기
                            }
                        });
                        ad.show();
                        break;
                    case R.id.sex_button:
                        final CharSequence[] items = {"남자", "여자"};
                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("성별 설정")        // 제목 설정
                                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener(){    // 목록 클릭시 설정
                                    public void onClick(DialogInterface dialog, int index){
                                        switch (index){       //디비에 각 케이스마다 저장할 부분 넣기
                                            case 0:
                                                sex.setImageResource(R.drawable.man);
                                                UpdateSex updateSex1 = new UpdateSex();
                                                updateSex1.execute(id_client, "1");
                                                break;
                                            case 1:
                                                sex.setImageResource(R.drawable.woman);
                                                UpdateSex updateSex2 = new UpdateSex();
                                                updateSex2.execute(id_client, "2");
                                                break;
                                        }
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog dialog = builder.create();    // 알림창 객체 생성
                        dialog.show();    // 알림창 띄우기
                        break;
                    case R.id.group_button:
                        final CharSequence[] gItems = {"혼자", "커플", "친구", "가족", "단체"};
                        final AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                        final TextView tv = (TextView)findViewById(R.id.group_text);
                        b.setTitle("자주가는 그룹 설정")        // 제목 설정
                                .setSingleChoiceItems(gItems, -1, new DialogInterface.OnClickListener(){    // 목록 클릭시 설정
                                    public void onClick(DialogInterface dialog, int index){
                                        switch (index){       //디비에 각 케이스마다 저장할 부분 넣기
                                            case 0:
                                                group.setImageResource(R.drawable.alone);
                                                tv.setText(gItems[index]);
                                                UpdateGroup updateGroup1 = new UpdateGroup();
                                                updateGroup1.execute(id_client, "1");
                                                break;
                                            case 1:
                                                group.setImageResource(R.drawable.couple);
                                                tv.setText(gItems[index]);
                                                UpdateGroup updateGroup2 = new UpdateGroup();
                                                updateGroup2.execute(id_client, "2");
                                                break;
                                            case 2:
                                                group.setImageResource(R.drawable.friend);
                                                tv.setText(gItems[index]);
                                                UpdateGroup updateGroup3 = new UpdateGroup();
                                                updateGroup3.execute(id_client, "3");
                                                break;
                                            case 3:
                                                group.setImageResource(R.drawable.family);
                                                tv.setText(gItems[index]);
                                                UpdateGroup updateGroup4 = new UpdateGroup();
                                                updateGroup4.execute(id_client, "4");
                                                break;
                                            case 4:
                                                group.setImageResource(R.drawable.group);
                                                tv.setText(gItems[index]);
                                                UpdateGroup updateGroup5 = new UpdateGroup();
                                                updateGroup5.execute(id_client, "5");
                                                break;
                                        }
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog dialog1 = b.create();    // 알림창 객체 생성
                        dialog1.show();    // 알림창 띄우기
                        break;
                }
            }
        };

        calendar.setOnClickListener(naviListener);
        inventory.setOnClickListener(naviListener);
        guide.setOnClickListener(naviListener);
        setting.setOnClickListener(naviListener);
        sex.setOnClickListener(naviListener);
        group.setOnClickListener(naviListener);
        age.setOnClickListener(naviListener);

        regionFragment = new RegionFragment();
        tripFragment = new TripFragment();
        tasteFragment = new TasteFragment();
        hotelFragment = new HotelFragment();
        courseFragment = new CourseFragment();

        // Initializing the TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("지역"));
        tabLayout.addTab(tabLayout.newTab().setText("관광"));
        tabLayout.addTab(tabLayout.newTab().setText("맛집"));
        tabLayout.addTab(tabLayout.newTab().setText("숙박"));
        tabLayout.addTab(tabLayout.newTab().setText("코스"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, regionFragment).commit();
        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                shiftFragment(tab.getPosition(), sigungu);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }

    class UpdateAge extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg, checkMsg;
        protected String doInBackground(String... strings){
            try{
                String str;
                URL url = new URL("http://222.116.135.79:8080/nccc_t/updateAge.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "id_client=" + strings[0] + "&age_client=" + strings[1];
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
    class UpdateGroup extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg, checkMsg;
        protected String doInBackground(String... strings){
            try{
                String str;
                URL url = new URL("http://222.116.135.79:8080/nccc_t/updateGroup.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "id_client=" + strings[0] + "&group_client=" + strings[1];
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
    class UpdateSex extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg, checkMsg;
        protected String doInBackground(String... strings){
            try{
                String str;
                URL url = new URL("http://222.116.135.79:8080/nccc_t/updateSex.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "id_client=" + strings[0] + "&sex_client=" + strings[1];
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

    public void getPermissionGPS(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_ACCESS_FINE_LOCATION);

        }
        else{

        }
    }
    public void onRequestPermissionResult(int requestCode, String permission[], int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_ACCESS_FINE_LOCATION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
                break;
            default:
                break;

        }
    }
    @Override
    public void onBackPressed(){
        if(dLayout.isDrawerOpen(GravityCompat.START)){
            dLayout.closeDrawer(GravityCompat.START,true);
        }else if(tabLayout.getTabAt(1).isSelected()||tabLayout.getTabAt(2).isSelected()||tabLayout.getTabAt(3).isSelected()||tabLayout.getTabAt(4).isSelected()) {
            tabLayout.getTabAt(0).select();
            shiftFragment(0,sigungu);
        }
        else {
            AlertDialog.Builder d = new AlertDialog.Builder(this);
            d.setMessage("정말 종료하시겠습니까?");
            d.setPositiveButton("예", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // process전체 종료
                    finish();
                }
            });
            d.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            d.show();
        }
    }

    private void GPSDialog(){
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alert_image, null);
        TextView custumTitle = (TextView)view.findViewById(R.id.customtitle);
        custumTitle.setText("무선 네트워크와 GPS을 모두 사용하셔야 정확한 눈치코칭의 서비스가 가능합니다 ! \nGPS를 키시겠습니까?");
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        AlertDialog.Builder gpsDialog = new AlertDialog.Builder(MainActivity.this);
        gpsDialog.setView(view);
        gpsDialog.setPositiveButton("네", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent1.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(intent1);
            }
        }).setNegativeButton("아니요", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                return;
            }
        });


        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //GPS 설정화면으로 이동
            Log.e("dfdf", "들어왔따 반갑다");
            gpsDialog.create().show();
        }

    }
    public void shiftFragment(int position, int sigungu){
        Fragment selected = null;
        Bundle bundle = new Bundle(1);
        bundle.putString("sigungu", String.valueOf(sigungu));

        if (position == 0) {
            selected = regionFragment;
        } else if (position == 1) {
            selected = tripFragment;
            selected.setArguments(bundle);
        } else if (position == 2) {
            selected = tasteFragment;
            selected.setArguments(bundle);
        } else if (position == 3) {
            selected = hotelFragment;
            selected.setArguments(bundle);
        } else if (position == 4) {
            selected = courseFragment;
            selected.setArguments(bundle);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
    }

    public void setSigungu(int sigungu){
        this.sigungu = sigungu;
    }


    public String getDeviceId(){
        String idByANDROID_ID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        return idByANDROID_ID;
    }
}

