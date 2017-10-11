package com.exam.administrator.nccc_trip;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {


    String calendarUrl = "http://222.116.135.79:8080/nccc_t/getSchedule.jsp";
    String displayUrl = "http://222.116.135.79:8080/nccc_t/displaySchedule.jsp";

    Context mContext;

    Toolbar toolbar;
    TextView subject;
    ImageView back;
    ImageView searcher;
    NavigationOnClickListener listener;

    int weekYear;
    int weekMonth;
    int weekDay;
    String id_client;
    Thread calendarThread;



    MaterialCalendarView tourCalendarView;
    private CalendarDialog mCalendarDialog;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mContext = getApplicationContext();
        tourCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);

        id_client = getDeviceId();

        calendarThread = new Thread();
        // Adding Toolbar to the activity
        toolbar = (Toolbar) findViewById(R.id.etc_toolbar);
        toolbar.setContentInsetsAbsolute(0,0);
        setSupportActionBar(toolbar);

        subject = (TextView)findViewById(R.id.etc_subject);
        back = (ImageView)findViewById(R.id.etc_back);
        searcher = (ImageView)findViewById(R.id.etc_searcher);
        listener = new NavigationOnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.etc_back:
                        finish();
                        break;
                    case R.id.etc_searcher:
                        Intent i = new Intent(CalendarActivity.this, SearchActivity.class);
                        startActivity(i);
                        break;
                }
            }
        };
        subject.setText("일정");
        back.setOnClickListener(listener);
        searcher.setOnClickListener(listener);
        OneDayDecorator oneDayDecorator = new OneDayDecorator();

        tourCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2000, 1, 1))
                .setMaximumDate(CalendarDay.from(2100, 12, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        tourCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            private final Calendar calendar = Calendar.getInstance();
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {


                Intent i = new Intent(CalendarActivity.this, ScheduleActivity.class);
                date.copyTo(calendar);
                weekYear = calendar.get(Calendar.YEAR);
                weekMonth = calendar.get(Calendar.MONTH) + 1;
                weekDay = calendar.get(Calendar.DATE);

                final Handler handler = new Handler() {
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 1:

                                try {
                                    ArrayList getTitle = new ArrayList();
                                    String title = msg.obj.toString();

                                    Log.e("^^^^", msg.obj.toString());
                                    Log.e("^^^^", title);
                                    mCalendarDialog = new CalendarDialog(CalendarActivity.this, Integer.toString(weekDay)+"일", "\n나의 여행지 목록\n\n"+title, ScheduleListener, ReturnListener);

                                    mCalendarDialog.setCanceledOnTouchOutside(true);

                                    mCalendarDialog.show();
                                    Log.e("^^^", title);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                break;
                            case 2:
                                try{


                                }
                                catch (Exception ee){
                                    ee.printStackTrace();
                                }
                        }


                    }
                };
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String sendMsg;
                            URL url = new URL(displayUrl);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            conn.setRequestMethod("POST");
                            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                            sendMsg = "id_client=" + id_client + "&year=" + weekYear + "&month=" + weekMonth + "&day=" + weekDay;  //

                            osw.write(sendMsg);
                            osw.flush();
                            Log.e("####i1", "dddddddddd");
                            try {
                                Log.e("####i2", "dddddddddd");
                                if (conn.getResponseCode() >= 200 || conn.getResponseCode() < 300) {
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                                    StringBuffer buf = new StringBuffer();
                                    String str = "";
                                    Log.e("#####123", "" + buf.toString());
                                    while ((str = reader.readLine()) != null) {
                                        buf.append(str);
                                    }
                                    try {
                                        Log.e("####i3", "dddddddddd");
                                        JSONObject json = new JSONObject(buf.toString());
                                        JSONArray jj = json.getJSONArray("List");

                                        Log.e("####i4", "dddddddddd");
                                        String displayTitle = "";
                                        ArrayList getTTitle = new ArrayList();
                                        for (int i = 0; i < jj.length(); i++) {
                                            Log.e("####i5", "dddddddddd");
                                            JSONObject displayJson = jj.getJSONObject(i);
                                            displayTitle += displayJson.getString("title")+"\n\n";

                                            getTTitle.add(displayTitle);
                                            Log.e("^^^", "" + getTTitle);



                                        }
                                        Message msg = Message.obtain();
                                        msg.obj = displayTitle;
                                        msg.what = 1;
                                        handler.sendMessage(msg);

                                    } catch (Exception e) {

                                    }
                                }
                            } catch (Exception ee) {

                            }
                        } catch (Exception eee) {

                        }
                    }
                }).start();


                Log.e("&&&&", ""+weekYear+weekMonth+weekDay);


            }
        });

        tourCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                oneDayDecorator);



        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        try {
                            String highlightDay = (String) msg.obj;

                            Log.e("&&&&", highlightDay);
                            String[] arr = highlightDay.split("/");
                            int year  = Integer.parseInt(arr[0]);
                            int month  = Integer.parseInt(arr[1]);
                            int day  = Integer.parseInt(arr[2]);
                            int time  = Integer.parseInt(arr[3]);

                            tourCalendarView.addDecorator(new tourDecorator(year, month, day));


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        {
                        //String fdfd1 = insertScehdule.execute(id_client, title, "2017", "07", "13", "06").get();
                    }
                }
            }
        };

//        Thread idThread = new Thread(new Runnable() { // 반드시 스레드 이용 그래야 반복해서 쓸수 있다고 함
//            @Override
//            public void run() {
//
//                try {
//                    URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?ServiceKey=P6bhFFBWwGkij2sSFyuE1fYOhmljx2J0qqEjWC65a0BMXkdVEYQo44MRq0yZK7Txgqbp9GbSWfexAXQhBEwtLg%3D%3D&contentTypeId=12&contentId=126006&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y&_type=json");
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.setDefaultUseCaches(false);
//                    conn.setDoInput(true);
//                    conn.setDoOutput(false);
//                    conn.setRequestMethod("GET");
//
//                    if (conn != null) {
//                        conn.setConnectTimeout(10000);
//                        conn.setUseCaches(false);
//
//                        if (conn.getResponseCode() >= 200 || conn.getResponseCode() < 300) { //접속 잘 되었는지 안되었는지 파악
//                            Log.i(TAG, "ii1");
//                            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); // InputStreamReader로 가져온다음 Buffer에 넣으면 이상하게 에러가 남, 그냥 바로 넣을것.
//                            Log.i(TAG, "ii2");
//                            for (; ; ) {
//                                String buf = "";
//                                buf = br.readLine();
//                                Log.i(TAG, buf);
//                                if (buf == null) {
//                                    Log.i(TAG, "break");
//                                    break;
//                                }
//                                JSONObject result = new JSONObject(buf);
//                                JSONObject results = result.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item"); //가장 큰 테두리부터 갈라갈라 가져오기
//                                Log.i(TAG, "ii4");
//
//                                String title = results.getString("title");
//                                Log.e("aaaaaaaaaaaaaaaaaaa", title);
//
//                                handler.sendMessage(Message.obtain(handler, 1, title));
//
//
//                            }
//                            br.close();
//                        }
//                        conn.disconnect();
//                    }
//                } catch (Exception e) {
//                    Log.e(TAG, "ii7");
//                    Log.w(TAG, e.getMessage());
//                }
//            }
//        });
//        idThread.start();



         calendarThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String sendMsg;
                    URL url = new URL(calendarUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");
                    OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                    sendMsg = "id_client=" + id_client;

                    osw.write(sendMsg);
                    osw.flush();
                    Log.e("##i1", "dddddddddd");
                    try {
                        Log.e("##i2", "dddddddddd");
                        if (conn.getResponseCode() >= 200 || conn.getResponseCode() < 300) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                            StringBuffer buf = new StringBuffer();
                            String str= "";
                            while ((str = reader.readLine()) != null) {
                                buf.append(str);
                            }


                            try {
                                Log.e("##i3", "dddddddddd");
                                JSONObject json = new JSONObject(buf.toString());
                                JSONArray jj = json.getJSONArray("List");


                                for (int i = 0; i < jj.length(); i++) {
                                    JSONObject scheduleJson = jj.getJSONObject(i);
                                    String yo = scheduleJson.getString("year");
                                    String mo = scheduleJson.getString("month");
                                    String doo = scheduleJson.getString("day");
                                    String to = scheduleJson.getString("start_time");


                                    String dayDay = yo + "/" + mo + "/" + doo +"/" + to;
                                    handler.sendMessage(Message.obtain(handler, 1, dayDay));

                                }


                            } catch (Exception e) {
                            }
                            reader.close();

                        }
                        conn.disconnect();

                        Log.e("##i10", "dddddddddd");
                    }
                    catch (Exception exxx) {
                        Log.e("##i11", "dddddddddd");
                        exxx.printStackTrace();
                    }
                } catch (Exception e) {
                    Log.e("##i12", "dddddddddd");
                }

            }
        });
        calendarThread.start();




    }

    private View.OnClickListener ScheduleListener = new View.OnClickListener(){
        public void onClick(View v){
            mCalendarDialog.dismiss();
            Intent i = new Intent(CalendarActivity.this, ScheduleActivity.class);
            i.putExtra("year", weekYear);
            i.putExtra("month", weekMonth);
            i.putExtra("day", weekDay);

            startActivity(i);

        }
    };

    private View.OnClickListener ReturnListener = new View.OnClickListener(){
        public void onClick(View v){
            mCalendarDialog.dismiss();
        }
    };


    public String getDeviceId(){
        String idByANDROID_ID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        return idByANDROID_ID;
    }





    public class tourDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        int ccyear;
        int ccmonth;
        int ccday;
        public tourDecorator(int cyear, int cmonth, int cday) {
            ccyear = cyear;
            ccmonth = cmonth - 1;
            ccday = cday;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekYear = calendar.get(Calendar.YEAR);
            int weekMonth = calendar.get(Calendar.MONTH);
            int weekDay = calendar.get(Calendar.DATE);
            return (weekYear == ccyear && weekMonth == ccmonth && weekDay == ccday);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new StyleSpan(Typeface.BOLD));
            view.addSpan(new RelativeSizeSpan(1.4f));
            view.addSpan(new ForegroundColorSpan(Color.rgb(44,220,216)));

        }
    }

    public class SundayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SundayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SUNDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }

    public class SaturdayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SaturdayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SATURDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.BLUE));

        }
    }

    public class OneDayDecorator implements DayViewDecorator {

        private CalendarDay date;

        public OneDayDecorator() {
            date = CalendarDay.today();
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return date != null && day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new StyleSpan(Typeface.BOLD));
            view.addSpan(new RelativeSizeSpan(1.4f));
            view.addSpan(new ForegroundColorSpan(Color.GREEN));
        }

        /**
         * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
         */
        public void setDate(Date date) {
            this.date = CalendarDay.from(date);
        }
    }

    @Override
    public void onPause(){
        calendarThread.interrupt();
        super.onPause();
    }



}













