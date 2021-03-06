package com.exam.administrator.nccc_trip;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class HotelFragment extends Fragment {
    public HotelFragment(){
    }

    String apiKey = "P6bhFFBWwGkij2sSFyuE1fYOhmljx2J0qqEjWC65a0BMXkdVEYQo44MRq0yZK7Txgqbp9GbSWfexAXQhBEwtLg%3D%3D";
    String getWeightUrl = "http://222.116.135.79:8080/nccc_t/getAccomPrefer.jsp";
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<TourItem> items;
    private String name;
    private String address;
    private Bitmap bmimg;
    String imgUrl;
    private int contId;
    String sigungu;
    String id_client;
    List<TourWeight> tourArrList = new ArrayList<>();
    Thread t;

    private double mapx;
    private double mapy;
    LocationManager locManager;
    LocationListener locationListener;
    Location location;
    double longi;
    double lati;

    double distance;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_hotel, container, false);

        id_client = getDeviceId();
        locManager = (LocationManager)container.getContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                longi = location.getLongitude();
                lati = location.getLatitude();
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {}
            @Override
            public void onProviderDisabled(String provider) {
                new AlertDialog.Builder(container.getContext())
                        .setMessage("GPS가 꺼져있습니다. 위치 서비스에서 Google 위치 서비스를 체크해주세요")
                        .setPositiveButton("설정",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int id){
                                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("취소", null).show();
            }
        };


        t = new Thread();
        if(Integer.parseInt(getArguments().getString("sigungu"))<=12&&Integer.parseInt(getArguments().getString("sigungu"))>0){
            sigungu = getArguments().getString("sigungu");
        } else{
            sigungu = "";
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_best);
        items = new ArrayList();
        layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new TourAdapter(items, view.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);


        GetWeight getWeight = new GetWeight();

        int permissionCheck_fine = ContextCompat.checkSelfPermission(HotelFragment.this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCheck_coarse = ContextCompat.checkSelfPermission(HotelFragment.this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if(permissionCheck_fine == PackageManager.PERMISSION_GRANTED && permissionCheck_coarse == PackageManager.PERMISSION_GRANTED){
            if(!locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) && !locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000000,0,locationListener);
            }
            else {
                while(longi == 0.0 && lati == 0.0){
                    if (locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) && !locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000000, 0, locationListener);
                        if (locManager != null) {
                            location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                lati = location.getLatitude();
                                longi = location.getLongitude();
                            }
                        }
                    } else if (!locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) && locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000000, 0, locationListener);
                        if (locManager != null) {
                            location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                lati = location.getLatitude();
                                longi = location.getLongitude();
                            }
                        }
                    } else if (locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) && locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        if(locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000000, 0, locationListener);
                            if (locManager != null) {
                                location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    lati = location.getLatitude();
                                    longi = location.getLongitude();
                                }
                            }
                        }
                        if(locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000000, 0, locationListener);
                            if (locManager != null) {
                                location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                                if (location != null) {
                                    lati = location.getLatitude();
                                    longi = location.getLongitude();
                                }
                            }
                        }

                    } else {
                        lati = 36.9492896;
                        longi = 127.90739039999994;
                    }
                }
            }
        }
        else{
            //Toast.makeText(HotelFragment.this.getContext(), "권한을 허용해주세요.", Toast.LENGTH_SHORT).show();
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
                if((permissionCheck_fine == PackageManager.PERMISSION_DENIED) && (permissionCheck_coarse == PackageManager.PERMISSION_GRANTED)){
                    ActivityCompat.requestPermissions(HotelFragment.this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                }
                else if((permissionCheck_fine == PackageManager.PERMISSION_GRANTED) && (permissionCheck_coarse == PackageManager.PERMISSION_DENIED)){
                    ActivityCompat.requestPermissions(HotelFragment.this.getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
                }
                else if((permissionCheck_fine == PackageManager.PERMISSION_DENIED) && (permissionCheck_coarse == PackageManager.PERMISSION_DENIED)){
                    ActivityCompat.requestPermissions(HotelFragment.this.getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                    ActivityCompat.requestPermissions(HotelFragment.this.getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
                }
            }
        }
        getWeight.execute(id_client);


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
                    i.putExtra("tour",items.get(rv.getChildAdapterPosition(child)).getContInt());
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



        return view;
    }

    class GetWeight extends AsyncTask<String, Void, String> {
        String sendMsg;
        String receiveMsg;
        protected String doInBackground(String... strings){
            try{

                URL url = new URL(getWeightUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "id_client=" + strings[0];
                osw.write(sendMsg);
                osw.flush();

                if(conn.getResponseCode() >= 200 && conn.getResponseCode() < 300){
                    String str = "";
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    StringBuffer buffer = new StringBuffer();
                    Log.e("dd", "dddddddddd");
                    while((str = reader.readLine()) != null ){
                        buffer.append(str);
                    }
                    Log.e("^^^^^",buffer.toString());
                    try{
                        JSONObject json = new JSONObject(buffer.toString());
                        double B02010100 = json.getDouble("B02010100");
                        double B02010200 = json.getDouble("B02010200");
                        double B02010300 = json.getDouble("B02010300");
                        double B02010400 = json.getDouble("B02010400");
                        double B02010500 = json.getDouble("B02010500");
                        double B02010600 = json.getDouble("B02010600");
                        double B02010700 = json.getDouble("B02010700");
                        double B02010800 = json.getDouble("B02010800");
                        double B02010900 = json.getDouble("B02010900");
                        double B02011000 = json.getDouble("B02011000");
                        double B02011100 = json.getDouble("B02011100");
                        double B02011200 = json.getDouble("B02011200");
                        double B02011300 = json.getDouble("B02011300");
                        double B02011400 = json.getDouble("B02011400");
                        double B02011500 = json.getDouble("B02011500");
                        double B02011600 = json.getDouble("B02011600");

                        TourWeight w1 = new TourWeight();
                        w1.setName("B02010100");
                        w1.setWeight(B02010100);
                        tourArrList.add(w1);

                        TourWeight w2 = new TourWeight();
                        w2.setName("B02010200");
                        w2.setWeight(B02010200);
                        tourArrList.add(w2);

                        TourWeight w3 = new TourWeight();
                        w3.setName("B02010300");
                        w3.setWeight(B02010300);
                        tourArrList.add(w3);

                        TourWeight w4 = new TourWeight();
                        w4.setName("B02010400");
                        w4.setWeight(B02010400);
                        tourArrList.add(w4);

                        TourWeight w5 = new TourWeight();
                        w5.setName("B02010500");
                        w5.setWeight(B02010500);
                        tourArrList.add(w5);

                        TourWeight w6 = new TourWeight();
                        w6.setName("B02010600");
                        w6.setWeight(B02010600);
                        tourArrList.add(w6);

                        TourWeight w7 = new TourWeight();
                        w7.setName("B02010700");
                        w7.setWeight(B02010700);
                        tourArrList.add(w7);

                        TourWeight w8 = new TourWeight();
                        w8.setName("B02010800");
                        w8.setWeight(B02010800);
                        tourArrList.add(w8);

                        TourWeight w9 = new TourWeight();
                        w9.setName("B02010900");
                        w9.setWeight(B02010900);
                        tourArrList.add(w9);

                        TourWeight w10 = new TourWeight();
                        w10.setName("B02011000");
                        w10.setWeight(B02011000);
                        tourArrList.add(w10);

                        TourWeight w11 = new TourWeight();
                        w11.setName("B02011100");
                        w11.setWeight(B02011100);
                        tourArrList.add(w11);

                        TourWeight w12 = new TourWeight();
                        w12.setName("B02011200");
                        w12.setWeight(B02011200);
                        tourArrList.add(w12);

                        TourWeight w13 = new TourWeight();
                        w13.setName("B02011300");
                        w13.setWeight(B02011300);
                        tourArrList.add(w13);

                        TourWeight w14 = new TourWeight();
                        w14.setName("B02011400");
                        w14.setWeight(B02011400);
                        tourArrList.add(w14);

                        TourWeight w15 = new TourWeight();
                        w15.setName("B02011500");
                        w15.setWeight(B02011500);
                        tourArrList.add(w15);

                        TourWeight w16 = new TourWeight();
                        w16.setName("B02011600");
                        w16.setWeight(B02011600);
                        tourArrList.add(w16);

                        Collections.sort(tourArrList, new Comparator<TourWeight>() {
                            @Override
                            public int compare(TourWeight o1, TourWeight o2) {
                                if (o1.getWeight() > o2.getWeight()) {
                                    return 1;
                                }
                                else if (o1.getWeight() < o2.getWeight()) {
                                    return -1;
                                }
                                else{
                                    return 0;
                                }
                            }
                        });
                        Collections.reverse(tourArrList);


                        for(int i = 0 ; i< 16; i++) {
                            Log.e("&&&&&&", tourArrList.get(i).getName());
                            Log.e("&&&&&&", ""+tourArrList.get(i).getWeight());
                        }

                        for(int io = 0 ; io< 16; io++){
                            final String cat3 = tourArrList.get(io).getName();
                            t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try{
                                        URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey="+apiKey+"&contentTypeId=32&areaCode=33&sigunguCode="+sigungu+"&cat1=&cat2=&cat3="+cat3+"&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=B&numOfRows=12&pageNo=1&_type=json");
                                        Log.e("^^^^", cat3);
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
                                                    Random random = new Random();
                                                    JSONObject result = new JSONObject(buf);
                                                    Log.e("*(*(", buf.toString());
                                                    JSONObject totalCount = result.getJSONObject("response").getJSONObject("body"); //가장 큰 테두리부터 갈라갈라 가져오기
                                                    int count = totalCount.getInt("totalCount");
                                                    int subcount = count / 4;
                                                    if(subcount == 0){
                                                        subcount = 1;
                                                    }
                                                    int randomcount = random.nextInt(subcount);
                                                    if(randomcount == 0){
                                                        randomcount = 1;
                                                    }
                                                    Log.e("^^^^$", ""+randomcount);
                                                    try{
                                                        URL url1 = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey="+apiKey+"&contentTypeId=32&areaCode=33&sigunguCode="+sigungu+"&cat1=&cat2=&cat3="+cat3+"&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=B&numOfRows=12&pageNo="+randomcount+"&_type=json");
                                                        HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
                                                        conn1.setDefaultUseCaches(false);
                                                        conn1.setDoInput(true);
                                                        conn1.setDoOutput(false);
                                                        conn1.setRequestMethod("GET");

                                                        if (conn1 != null) {
                                                            conn1.setConnectTimeout(10000);
                                                            conn1.setUseCaches(false);

                                                            if (conn1.getResponseCode() >= 200 || conn1.getResponseCode() < 300) { //접속 잘 되었는지 안되었는지 파악
                                                                BufferedReader br1 = new BufferedReader(new InputStreamReader(conn1.getInputStream(), "UTF-8")); // InputStreamReader로 가져온다음 Buffer에 넣으면 이상하게 에러가 남, 그냥 바로 넣을것.

                                                                String buf1 = "";
                                                                buf1 = br1.readLine();
                                                                if (buf1 == null) {

                                                                }else{
                                                                    JSONObject result1 = new JSONObject(buf1);
                                                                    JSONArray results = result1.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item"); //가장 큰 테두리부터 갈라갈라 가져오기
                                                                    for (int i = 0; i < results.length(); i++) {
                                                                        JSONObject json = results.getJSONObject(i);
                                                                        name = json.getString("title");
                                                                        address = json.getString("addr1");
                                                                        contId = json.getInt("contentid");
                                                                        mapx = json.getDouble("mapx");
                                                                        mapy = json.getDouble("mapy");
                                                                        distance = calDistance(longi, lati, mapx, mapy);
                                                                        Log.e("d33333fd", name);
                                                                        Log.e("33333dfd", address);
                                                                        try{
                                                                            imgUrl = json.getString("firstimage");
                                                                            items.add(new TourItem(name, address, contId, distance, imgUrl));
                                                                        }
                                                                        catch (Exception e) {
                                                                            imgUrl = "http://222.116.135.79:8080/nccc_t/img/default_img.jpg";
                                                                            items.add(new TourItem(name, address, contId, distance, imgUrl));
                                                                        }
                                                                        getActivity().runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                adapter.notifyDataSetChanged();
                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            }
                                                            conn1.disconnect();
                                                        }
                                                    }
                                                    catch (Exception er){

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
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        tourArrList.clear();
                    }
                    catch (Exception ee){
                        ee.printStackTrace();
                        Log.e("^^", ""+ee);
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

    public String getDeviceId(){
        String idByANDROID_ID = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        return idByANDROID_ID;
    }

    @Override
    public void onPause(){
        t.interrupt();
        super.onPause();
    }
    public double calDistance(double myX, double myY, double pX, double pY){
        if(myX == 0.0 || myY == 0.0){
            return -99.99;
        }
        else{
            return Math.sqrt((((Math.cos(myY)*6400*2*3.14/360*Math.abs(myX - pX)))*((Math.cos(myY)*6400*2*3.14/360*Math.abs(myX - pX))))+(((111*Math.abs(myY-pY)))*((111*Math.abs(myY-pY)))));
        }
    }
}