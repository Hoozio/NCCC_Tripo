//package com.exam.administrator.nccc_trip;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
///**
// * Created by GHKwon on 2016-02-17.
// */
//public class BroadcastD extends BroadcastReceiver {
//    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;
//
//    @Override
//    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함
//        //NotificationManager 안드로이드 상태바에 메세지를 던지기위한 서비스 불러오고
//
//        Log.e("BroadcastD","receive in");
//
//        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, NCCCtripSplash.class), PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Notification.Builder builder = new Notification.Builder(context);
//
//        builder.setSmallIcon(R.drawable.ic_launcher).setTicker("HETT")
//                .setContentTitle("눈치코칭_충북여행").setContentText("저희가 고심하여 선정한 관광지 ! 확인해보세요 !")
//                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setAutoCancel(true);
//
//        notificationmanager.notify(1, builder.build());
//
//    }
//
//}