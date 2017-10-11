//package com.exam.administrator.nccc_trip;
//
//import android.os.Handler;
//import android.os.Message;
//
///**
// * Created by user on 2017-07-22.
// */
//
//public class ServiceThread extends Thread {
//    Handler handler;
//    boolean isRun = true;
//    int i = 0;
//    public ServiceThread(Handler handler){
//        this.handler = handler;
//    }
//
//    public void stopForever(){
//        synchronized (this){
//            this.isRun = false;
//        }
//    }
//
//    public void run(){
//
//        Message msg = new Message();
//
//            handler.sendMessage(msg);
//
//
//    }
//}
