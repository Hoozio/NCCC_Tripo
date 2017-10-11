package com.exam.administrator.nccc_trip;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TutorialActivity extends AppCompatActivity {

    final String TAG = "DBDBDBDBDBDB";
    String myDeviceId;

    ImageView manIv;
    ImageView womanIv;
    EditText ageEt;
    ImageView aloneIv;
    ImageView coupleIv;
    ImageView friendIv;
    ImageView familyIv;
    ImageView groupIv;
    TextView conform;

    boolean manSelected;
    boolean womanSelected;
    boolean aloneSelected;
    boolean coupleSelected;
    boolean friendSelected;
    boolean familySelected;
    boolean groupSelected;

    TutorialOnClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        myDeviceId = getIntent().getStringExtra("deviceId");

        manIv = (ImageView)findViewById(R.id.man_tuto);
        womanIv = (ImageView)findViewById(R.id.woman_tuto);
        ageEt = (EditText)findViewById(R.id.age_tuto);
        aloneIv = (ImageView)findViewById(R.id.alone_tuto);
        coupleIv = (ImageView)findViewById(R.id.couple_tuto);
        friendIv = (ImageView)findViewById(R.id.friend_tuto);
        familyIv = (ImageView)findViewById(R.id.family_tuto);
        groupIv = (ImageView)findViewById(R.id.group_tuto);
        conform = (TextView)findViewById(R.id.conform_tuto);

        manSelected = false;
        womanSelected = false;
        aloneSelected = false;
        coupleSelected = false;
        friendSelected = false;
        familySelected = false;
        groupSelected = false;

        listener = new TutorialOnClickListener(){
            @Override
            public void onClick(View v){
                switch (v.getId()){
                    case R.id.man_tuto:
                        if(manSelected) {
                            manSelected = false;
                            manIv.setImageResource(R.drawable.man_tuto);
                            break;
                        }
                        if(womanSelected) {
                            womanSelected = false;
                            womanIv.setImageResource(R.drawable.woman_tuto);
                        }
                        manIv.setImageResource(R.drawable.man_tuto_select);
                        manSelected = true;
                        break;
                    case R.id.woman_tuto:
                        if(manSelected) {
                            manSelected = false;
                            manIv.setImageResource(R.drawable.man_tuto);
                        }
                        if(womanSelected) {
                            womanSelected = false;
                            womanIv.setImageResource(R.drawable.woman_tuto);
                            break;
                        }
                        womanIv.setImageResource(R.drawable.woman_tuto_select);
                        womanSelected = true;
                        break;
                    case R.id.alone_tuto:
                        if(aloneSelected) {
                            aloneSelected = false;
                            aloneIv.setImageResource(R.drawable.alone_tuto);
                            break;
                        }
                        if(coupleSelected) {
                            coupleSelected = false;
                            coupleIv.setImageResource(R.drawable.couple_tuto);
                        }
                        if(friendSelected) {
                            friendSelected = false;
                            friendIv.setImageResource(R.drawable.friend_tuto);
                        }
                        if(familySelected) {
                            familySelected = false;
                            familyIv.setImageResource(R.drawable.family_tuto);
                        }
                        if(groupSelected) {
                            groupSelected = false;
                            groupIv.setImageResource(R.drawable.group_tuto);
                        }
                        aloneIv.setImageResource(R.drawable.alone_tuto_select);
                        aloneSelected = true;
                        break;
                    case R.id.couple_tuto:
                        if(aloneSelected) {
                            aloneSelected = false;
                            aloneIv.setImageResource(R.drawable.alone_tuto);
                        }
                        if(coupleSelected) {
                            coupleSelected = false;
                            coupleIv.setImageResource(R.drawable.couple_tuto);
                            break;
                        }
                        if(friendSelected) {
                            friendSelected = false;
                            friendIv.setImageResource(R.drawable.friend_tuto);
                        }
                        if(familySelected) {
                            familySelected = false;
                            familyIv.setImageResource(R.drawable.family_tuto);
                        }
                        if(groupSelected) {
                            groupSelected = false;
                            groupIv.setImageResource(R.drawable.group_tuto);
                        }
                        coupleIv.setImageResource(R.drawable.couple_tuto_select);
                        coupleSelected = true;
                        break;
                    case R.id.friend_tuto:
                        if(aloneSelected) {
                            aloneSelected = false;
                            aloneIv.setImageResource(R.drawable.alone_tuto);
                        }
                        if(coupleSelected) {
                            coupleSelected = false;
                            coupleIv.setImageResource(R.drawable.couple_tuto);
                        }
                        if(friendSelected) {
                            friendSelected = false;
                            friendIv.setImageResource(R.drawable.friend_tuto);
                            break;
                        }
                        if(familySelected) {
                            familySelected = false;
                            familyIv.setImageResource(R.drawable.family_tuto);
                        }
                        if(groupSelected) {
                            groupSelected = false;
                            groupIv.setImageResource(R.drawable.group_tuto);
                        }
                        friendIv.setImageResource(R.drawable.friend_tuto_select);
                        friendSelected = true;
                        break;
                    case R.id.family_tuto:
                        if(aloneSelected) {
                            aloneSelected = false;
                            aloneIv.setImageResource(R.drawable.alone_tuto);
                        }
                        if(coupleSelected) {
                            coupleSelected = false;
                            coupleIv.setImageResource(R.drawable.couple_tuto);
                        }
                        if(friendSelected) {
                            friendSelected = false;
                            friendIv.setImageResource(R.drawable.friend_tuto);
                        }
                        if(familySelected) {
                            familySelected = false;
                            familyIv.setImageResource(R.drawable.family_tuto);
                            break;
                        }
                        if(groupSelected) {
                            groupSelected = false;
                            groupIv.setImageResource(R.drawable.group_tuto);
                        }
                        familyIv.setImageResource(R.drawable.family_tuto_select);
                        familySelected = true;
                        break;
                    case R.id.group_tuto:
                        if(aloneSelected) {
                            aloneSelected = false;
                            aloneIv.setImageResource(R.drawable.alone_tuto);
                        }
                        if(coupleSelected) {
                            coupleSelected = false;
                            coupleIv.setImageResource(R.drawable.couple_tuto);
                        }
                        if(friendSelected) {
                            friendSelected = false;
                            friendIv.setImageResource(R.drawable.friend_tuto);
                        }
                        if(familySelected) {
                            familySelected = false;
                            familyIv.setImageResource(R.drawable.family_tuto);
                        }
                        if(groupSelected) {
                            groupSelected = false;
                            groupIv.setImageResource(R.drawable.group_tuto);
                            break;
                        }
                        groupIv.setImageResource(R.drawable.group_tuto_select);
                        groupSelected = true;
                        break;
                    case R.id.conform_tuto:
                        int age;
                        int group;
                        int sex;
                        if(manSelected||womanSelected){
                            sex = manSelected ? 1 : 2;
                        }else{
                            AlertDialog.Builder ab = new AlertDialog.Builder(TutorialActivity.this);
                            ab.setMessage("성별을 선택해주세요");
                            ab.setPositiveButton("확인", null);
                            ab.show();
                            break;
                        }
                        if(ageEt.getText().toString().replace(" ","").equals("")){
                            AlertDialog.Builder ab = new AlertDialog.Builder(TutorialActivity.this);
                            ab.setMessage("나이를 입력해주세요");
                            ab.setPositiveButton("확인", null);
                            ab.show();
                            break;
                        }else{
                            age = Integer.parseInt(ageEt.getText().toString());
                        }
                        if(aloneSelected||coupleSelected||friendSelected||familySelected||groupSelected){
                            group = aloneSelected ? 1 : (coupleSelected ? 2 : (friendSelected ? 3 : (familySelected ? 4 : 5)));
                        }else{
                            AlertDialog.Builder ab = new AlertDialog.Builder(TutorialActivity.this);
                            ab.setMessage("그룹을 선택해주세요");
                            ab.setPositiveButton("확인", null);
                            ab.show();
                            break;
                        }


                        try {
                            Dbload dbload = new Dbload();
                            String result = dbload.execute(myDeviceId, Integer.toString(age), Integer.toString(group), Integer.toString(sex)).get();
                            TourPreferAdd tourPreferAdd = new TourPreferAdd();
                            TastePreferAdd tastePreferAdd = new TastePreferAdd();
                            AccomPreferAdd accomPreferAdd = new AccomPreferAdd();
                            if (group == 1 && sex == 1) { //혼자, 남자
                                String result1 = tourPreferAdd.execute(myDeviceId, "0.2", "0.4", "0.1", "0.1", "0.1", "0.3").get();
                                String result2 = tastePreferAdd.execute(myDeviceId, "0.3", "0.1", "0.2", "0.4", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1").get();
                                String result3 = accomPreferAdd.execute(myDeviceId, "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.2", "0.1", "0.4", "0.3", "0.1", "0.1", "0.1", "0.1").get();
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result1);
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result2);
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result3);
                            } else if (group == 1 && sex == 2) { //혼자, 여자
                                String result1 = tourPreferAdd.execute(myDeviceId, "0.2", "0.4", "0.1", "0.1", "0.1", "0.3").get();
                                String result2 = tastePreferAdd.execute(myDeviceId, "0.4", "0.2", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.3", "0.1").get();
                                String result3 = accomPreferAdd.execute(myDeviceId, "0.4", "0.1", "0.1", "0.1", "0.1", "0.2", "0.1", "0.1", "0.3", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1").get();
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result1);
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result2);
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result3);
                            } else if (group == 2 && sex == 1) { //연인, 남자
                                String result1 = tourPreferAdd.execute(myDeviceId, "0.2", "0.1", "0.4", "0.3", "0.1", "0.1").get();
                                String result2 = tastePreferAdd.execute(myDeviceId, "0.3", "0.2", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.4", "0.1").get();
                                String result3 = accomPreferAdd.execute(myDeviceId, "0.2", "0.1", "0.3", "0.1", "0.1", "0.1", "0.1", "0.1", "0.4", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1").get();

                            } else if (group == 2 && sex == 2) { //연인, 여자
                                String result1 = tourPreferAdd.execute(myDeviceId, "0.2", "0.1", "0.4", "0.3", "0.1", "0.1").get();
                                String result2 = tastePreferAdd.execute(myDeviceId, "0.1", "0.3", "0.2", "0.1", "0.1", "0.1", "0.1", "0.1", "0.4", "0.1").get();
                                String result3 = accomPreferAdd.execute(myDeviceId, "0.3", "0.1", "0.1", "0.1", "0.4", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.2").get();
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result1);
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result2);
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result3);
                            } else if (group == 3 && sex == 1) {//친구, 남자
                                String result1 = tourPreferAdd.execute(myDeviceId, "0.2", "0.1", "0.3", "0.4", "0.1", "0.1").get();
                                String result2 = tastePreferAdd.execute(myDeviceId, "0.1", "0.1", "0.1", "0.4", "0.1", "0.1", "0.3", "0.1", "0.1", "0.2").get();
                                String result3 = accomPreferAdd.execute(myDeviceId, "0.1", "0.1", "0.1", "0.1", "0.1", "0.3", "0.4", "0.1", "0.1", "0.1", "0.2", "0.1", "0.1", "0.1", "0.1", "0.1").get();
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result1);
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result2);
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result3);
                            } else if (group == 3 && sex == 2) { //친구, 여자
                                String result1 = tourPreferAdd.execute(myDeviceId, "0.2", "0.1", "0.3", "0.4", "0.1", "0.1").get();
                                String result2 = tastePreferAdd.execute(myDeviceId, "0.1", "0.4", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.3", "0.2").get();
                                String result3 = accomPreferAdd.execute(myDeviceId, "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.3", "0.2", "0.1", "0.1", "0.1", "0.4").get();
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result1);
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result2);
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result3);
                            } else if (group == 4 && sex == 1) { //가족. 남자
                                String result1 = tourPreferAdd.execute(myDeviceId, "0.3", "0.1", "0.2", "0.4", "0.1", "0.1").get();
                                String result2 = tastePreferAdd.execute(myDeviceId, "0.4", "0.1", "0.2", "0.3", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1").get();
                                String result3 = accomPreferAdd.execute(myDeviceId, "0.3", "0.1", "0.2", "0.4", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1").get();
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result1);
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result2);
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result3);
                            } else if (group == 4 && sex == 2) { //가족, 여자
                                String result1 = tourPreferAdd.execute(myDeviceId, "0.3", "0.1", "0.2", "0.4", "0.1", "0.1").get();
                                String result2 = tastePreferAdd.execute(myDeviceId, "0.4", "0.1", "0.2", "0.3", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1").get();
                                String result3 = accomPreferAdd.execute(myDeviceId, "0.3", "0.1", "0.2", "0.4", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1").get();
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result1);
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result2);
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result3);
                            } else if (group == 5 && sex == 1) { //단체, 남자
                                String result1 = tourPreferAdd.execute(myDeviceId, "0.2", "0.1", "0.1", "0.1", "0.4", "0.3").get();
                                String result2 = tastePreferAdd.execute(myDeviceId, "0.4", "0.1", "0.1", "0.1", "0.1", "0.2", "0.3", "0.1", "0.1", "0.1").get();
                                String result3 = accomPreferAdd.execute(myDeviceId, "0.2", "0.1", "0.1", "0.1", "0.1", "0.1", "0.4", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.3", "0.1").get();
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result1);
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result2);
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result3);
                            } else if (group == 5 && sex == 2) { //단체, 여자
                                String result1 = tourPreferAdd.execute(myDeviceId, "0.2", "0.1", "0.1", "0.1", "0.4", "0.3").get();
                                String result2 = tastePreferAdd.execute(myDeviceId, "0.4", "0.1", "0.1", "0.1", "0.1", "0.2", "0.3", "0.1", "0.1", "0.1").get();
                                String result3 = accomPreferAdd.execute(myDeviceId, "0.2", "0.1", "0.1", "0.1", "0.1", "0.1", "0.4", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.1", "0.3", "0.1").get();
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result1);
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result2);
                                Log.i(TAG, "iiiiiiiiiiiii 1 " + result3);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Intent i = new Intent(TutorialActivity.this, MainActivity.class);
                        i.putExtra("age_client", age);
                        i.putExtra("sex_client", sex);
                        i.putExtra("group_client", group);
                        startActivity(i);
                        finish();
                        break;
                }
            }
        };

        manIv.setOnClickListener(listener);
        womanIv.setOnClickListener(listener);
        aloneIv.setOnClickListener(listener);
        coupleIv.setOnClickListener(listener);
        friendIv.setOnClickListener(listener);
        familyIv.setOnClickListener(listener);
        groupIv.setOnClickListener(listener);
        conform.setOnClickListener(listener);
    }
    class Dbload extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        protected String doInBackground(String... strings){
            try{
                String str;
                URL url = new URL("http://222.116.135.79:8080/nccc_t/connectDb.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "id=" + strings[0] + "&age=" + strings[1] + "&group=" + strings[2] + "&sex=" + strings[3];

                osw.write(sendMsg);
                osw.flush();

                if(conn.getResponseCode() >= 200 || conn.getResponseCode() < 300){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    StringBuffer buffer = new StringBuffer();
                    Log.e("dd", "dddddddddd");
                    while((str = reader.readLine()) != null ){
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
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

    class TourPreferAdd extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        protected String doInBackground(String... strings){
            try{
                String str;
                URL url = new URL("http://222.116.135.79:8080/nccc_t/insertTourPrefer.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "id=" + strings[0] + "&natureTour=" + strings[1] + "&historyTour=" + strings[2] + "&vacationTour=" + strings[3] + "&experienceTour=" + strings[4] +"&industryTour=" + strings[5] +"&scluptureTour=" + strings[6];

                osw.write(sendMsg);
                osw.flush();

                if(conn.getResponseCode() >= 200 && conn.getResponseCode() < 300){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    StringBuffer buffer = new StringBuffer();
                    Log.e("dd", "dddddddddd");
                    while((str = reader.readLine()) != null ){
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
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
    class TastePreferAdd extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        protected String doInBackground(String... strings){
            try{
                String str;
                URL url = new URL("http://222.116.135.79:8080/nccc_t/insertTastePrefer.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "id=" + strings[0] + "&A05020100=" + strings[1] + "&A05020200=" + strings[2] + "&A05020300=" + strings[3] + "&A05020400=" + strings[4] +"&A05020500=" + strings[5] +"&A05020600=" + strings[6] +"&A05020700="+ strings[7] +"&A05020800="+ strings[8] +"&A05020900="+ strings[9] +"&A05021000="+ strings[10];

                osw.write(sendMsg);
                osw.flush();

                if(conn.getResponseCode() >= 200 && conn.getResponseCode() < 300){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    StringBuffer buffer = new StringBuffer();
                    Log.e("dd", "dddddddddd");
                    while((str = reader.readLine()) != null ){
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
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
    class AccomPreferAdd extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        protected String doInBackground(String... strings){
            try{
                String str;
                URL url = new URL("http://222.116.135.79:8080/nccc_t/insertAccomPrefer.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "id=" + strings[0] + "&B02010100=" + strings[1] + "&B02010200=" + strings[2] + "&B02010300=" + strings[3] + "&B02010400=" + strings[4] +"&B02010500=" + strings[5] +"&B02010600=" + strings[6] +"&B02010700="+ strings[7] +"&B02010800="+ strings[8] +"&B02010900="+ strings[9] +"&B02011000="+ strings[10] +"&B02011100="+ strings[11] +"&B02011200="+ strings[12] +"&B02011300="+ strings[13] +"&B02011400="+ strings[14] +"&B02011500="+ strings[15] +"&B02011600="+ strings[16];

                osw.write(sendMsg);
                osw.flush();

                if(conn.getResponseCode() >= 200 && conn.getResponseCode() < 300){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    StringBuffer buffer = new StringBuffer();
                    Log.e("dd", "dddddddddd");
                    while((str = reader.readLine()) != null ){
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
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
    class TutorialOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v){
        }
    }
}