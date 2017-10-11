package com.exam.administrator.nccc_trip;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class CourseFragment extends Fragment {

    public CourseFragment(){
    }

    private static final String apiKey = "P6bhFFBWwGkij2sSFyuE1fYOhmljx2J0qqEjWC65a0BMXkdVEYQo44MRq0yZK7Txgqbp9GbSWfexAXQhBEwtLg%3D%3D";
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<CourseItem> items;
    ArrayList<CourseDetail> courseItems;

    int contId;
    String name;
    Bitmap bmimg;
    String info;
    int count;
    Thread t;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_course, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_course);
        items = new ArrayList();
        courseItems = new ArrayList();
        layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        adapter = new CourseAdapter(items, view.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        t = new Thread(new Runnable() { // 반드시 스레드 이용 그래야 반복해서 쓸수 있다고 함
            @Override
            public void run() {
                try {
                    URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey="+apiKey+"&contentTypeId=25&areaCode=33&sigunguCode=&cat1=C01&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=B&numOfRows=15&pageNo=1&_type=json");
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
                                    contId = json.getInt("contentid");
                                    try {
                                        BitmapFactory.Options options = new BitmapFactory.Options();
                                        options.inSampleSize = 2;
                                        URL imgurl = new URL(json.getString("firstimage"));
                                        InputStream is = (InputStream)imgurl.getContent();
                                        bmimg = BitmapFactory.decodeStream(is, null, options);
                                        Log.e("ddd", "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@뭐가 문제일까?" + name + "            다른 정보는  " + contId);
                                    } catch (Exception e) {
                                        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.default_img);
                                        bmimg = drawable.getBitmap();
                                    }
                                    try {
                                        URL infoUrl = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?ServiceKey="+apiKey+"&contentTypeId=25&contentId="+contId+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y&_type=json");
                                        HttpURLConnection infoConn = (HttpURLConnection) infoUrl.openConnection();
                                        infoConn.setDoInput(true);
                                        infoConn.connect();
                                        BufferedReader br1 = new BufferedReader(new InputStreamReader(infoConn.getInputStream(), "UTF-8")); // InputStreamReader로 가져온다음 Buffer에 넣으면 이상하게 에러가 남, 그냥 바로 넣을것.

                                        String buf1 = "";
                                        buf1 = br1.readLine();
                                        if (buf1 == null) {

                                        } else {
                                            JSONObject resultInfo = new JSONObject(buf1);
                                            JSONObject jsonInfo = resultInfo.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item"); //가장 큰 테두리부터 갈라갈라 가져오기
                                            info = jsonInfo.getString("overview");
                                            try {
                                                URL courseUrl = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailInfo?ServiceKey="+apiKey+"&contentTypeId=25&contentId="+contId+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&listYN=Y&_type=json");
                                                HttpURLConnection courseConn = (HttpURLConnection) courseUrl.openConnection();
                                                courseConn.setDoInput(true);
                                                courseConn.connect();
                                                BufferedReader br2 = new BufferedReader(new InputStreamReader(courseConn.getInputStream(), "UTF-8")); // InputStreamReader로 가져온다음 Buffer에 넣으면 이상하게 에러가 남, 그냥 바로 넣을것.

                                                String buf2 = "";
                                                buf2 = br2.readLine();
                                                if (buf2 == null) {

                                                } else {
                                                    JSONObject resultCourse = new JSONObject(buf2);
                                                    JSONArray resultsCourse = resultCourse.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item"); //가장 큰 테두리부터 갈라갈라 가져오기
                                                    count = resultsCourse.length();
                                                    courseItems = new ArrayList<>();
                                                    for (int j = 0; j < resultsCourse.length(); j++) {
                                                        JSONObject jsonCourse = resultsCourse.getJSONObject(j);
                                                        CourseDetail d = new CourseDetail(view.getContext());
                                                        d.setNum(String.valueOf(jsonCourse.getInt("subnum")+1));
                                                        d.setName(jsonCourse.getString("subname"));
                                                        d.setDetailInt(jsonCourse.getInt("subcontentid"));
                                                        d.setContInt(jsonCourse.getInt("contentid"));
                                                        d.setLon(jsonInfo.getDouble("mapx"));
                                                        d.setLat(jsonInfo.getDouble("mapy"));
                                                        courseItems.add(d);
                                                    }
                                                    items.add(new CourseItem(bmimg, name, info, courseItems, count));
                                                }
                                                courseConn.disconnect();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                items.add(new CourseItem(bmimg, name, info, courseItems, count));
                                            }

                                        }
                                        infoConn.disconnect();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        info = null;
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
                        conn.disconnect();
                    }
                }
                catch(Exception e) {
                }
            }
        });
        t.start();

        return view;
    }

    @Override
    public void onPause(){
        t.interrupt();
        super.onPause();
    }
}