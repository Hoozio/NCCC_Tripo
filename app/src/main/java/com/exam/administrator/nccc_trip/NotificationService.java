//package com.exam.administrator.nccc_trip;
//
//import android.app.AlarmManager;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Handler;
//import android.os.IBinder;
//import android.support.v4.app.NotificationCompat;
//import android.widget.Toast;
//
//import java.util.Calendar;
//
//import static com.exam.administrator.nccc_trip.R.string.calendar;
//
//public class NotificationService extends Service {
//    NotificationManager Notifi_M;
//    ServiceThread thread;
//    Notification Notifi ;
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate(){
//        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        myServiceHandler handler = new myServiceHandler();
//        thread = new ServiceThread(handler);
//        thread.start();
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        return START_STICKY;
//    }
//
//    //서비스가 종료될 때 할 작업
//
//    public void onDestroy() {
//        thread.stopForever();
//        thread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.
//    }
//
//    class myServiceHandler extends Handler {
//        @Override
//        public void handleMessage(android.os.Message msg) {
//
//                Intent intent = new Intent(NotificationService.this, BroadcastD.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                Calendar calendar = Calendar.getInstance();
//                //알람시간 calendar에 set해주기
//
//                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 4,15);//시간을 10시 01분으로 일단 set했음
//                calendar.set(Calendar.SECOND, 0);
//
//
//
//                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//                if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M )
//                    am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//                else if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
//                    am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//                else
//                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//
//
//        }
//    };
//}

