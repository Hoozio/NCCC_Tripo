package com.exam.administrator.nccc_trip;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class NCCCtripSplash extends AppCompatActivity {
    public final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;

    LocationManager locationManager;
    String id_client;
    int age_client;
    int sex_client;
    int group_client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nccctrip_splash);

        id_client  = getDeviceId();

        GetUserInfo getUserInfo = new GetUserInfo();
        getUserInfo.execute(id_client);




        try {
            getPermissionId();
        }
        catch (Exception e){}
        finally{
            final String myDeviceId = getDeviceId();
            Log.e(TAG, "iiiiiiiiiiiii        " + myDeviceId);

            try {
                UserCheck userCheck = new UserCheck();
                String noob = userCheck.execute(myDeviceId).get();
                Log.e("df", noob);
                if(noob.equals("N")) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(NCCCtripSplash.this, TutorialActivity.class);
                            i.putExtra("deviceId", myDeviceId);
                            startActivity(i);
                            NCCCtripSplash.this.finish();
                        }
                    },4000);
                } else{
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(NCCCtripSplash.this, MainActivity.class);
                            intent.putExtra("age_client", age_client);
                            intent.putExtra("sex_client", sex_client);
                            intent.putExtra("group_client", group_client);
                            startActivity(intent);
                            NCCCtripSplash.this.finish();
                        }
                    },4000);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }


    public void getPermissionId()
    {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);

        }
        else{

        }

    }
    public String getDeviceId(){
        String idByANDROID_ID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        return idByANDROID_ID;
    }

    public void onRequestPermissionResult(int requestCode, String permission[], int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
                break;
            default:
                break;

        }
    }


    class GetUserInfo extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        protected String doInBackground(String... strings){
            try{
                String str;
                URL url = new URL("http://222.116.135.79:8080/nccc_t/getClientInfo.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "id_client=" + strings[0];

                osw.write(sendMsg);
                osw.flush();

                if(conn.getResponseCode() >= 200 || conn.getResponseCode() < 300) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    StringBuffer buffer = new StringBuffer();
                    Log.e("dd", "yesyesyes");
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    try {
                        JSONObject json = new JSONObject(buffer.toString());
                        age_client = json.getInt("age_client");
                        sex_client = json.getInt("sex_client");
                        group_client = json.getInt("group_client");



                    } catch (Exception exex) {

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


    class UserCheck extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        protected String doInBackground(String... strings){
            try{
                String str;
                URL url = new URL("http://222.116.135.79:8080/nccc_t/checkUser.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "id=" + strings[0];

                osw.write(sendMsg);
                osw.flush();

                if(conn.getResponseCode() >= 200 || conn.getResponseCode() < 300){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    StringBuffer buffer = new StringBuffer();
                    Log.e("dd", "yesyesyes");
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




}