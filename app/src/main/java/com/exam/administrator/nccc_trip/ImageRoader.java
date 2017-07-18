package com.exam.administrator.nccc_trip;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by user on 2017-07-17.
 */

public class ImageRoader {
    private final String serverUrl = "http://222.116.135.79:8080/nccc_t/img/";

    public ImageRoader(){
        new ThreadPolicy();
    }

    public Bitmap getBitmapImg(String imgStr){
        Bitmap bitmapImg = null;

        try{
            URL url = new URL(serverUrl + URLEncoder.encode(imgStr,"utf-8"));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            bitmapImg = BitmapFactory.decodeStream(is);
        }
        catch (Exception ee){

        }
        return bitmapImg;
    }
}
