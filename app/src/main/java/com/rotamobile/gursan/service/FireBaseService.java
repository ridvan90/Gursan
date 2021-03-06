package com.rotamobile.gursan.service;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rotamobile.gursan.Main;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.model.bildirim.BildirimModel;
import com.rotamobile.gursan.model.eventBus.MessageEvent;
import com.rotamobile.gursan.model.homeItemClick.ItemClickCheck;
import com.rotamobile.gursan.ui.activity.Bildirimler;

import org.greenrobot.eventbus.EventBus;

import io.fabric.sdk.android.Fabric;
import io.paperdb.Paper;
import io.realm.Realm;
import io.realm.RealmResults;
import me.leolin.shortcutbadger.ShortcutBadger;

public class FireBaseService extends Service {

    private DatabaseReference mDatabase;
    private Realm realm;
    private String get_userID;
    private String getInserTime = "",getSubject = "",getText = "",getWorkId = "";
    private int getType = 0,getUserId = 0;
    private BildirimModel bildirim;
    private RealmResults<BildirimModel> is_emri;
    ValueEventListener valueEventListener;
    private RealmResults<ItemClickCheck> realmResults;
    private ItemClickCheck itemClickCheck;
    private static final String NOTIFICATION_CHANNEL = "me.leolin.shortcutbadger.example";
    private NotificationManager notificationManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(FireBaseService.this, new Crashlytics());
        // Get a Realm instance for this thread
        realm = Realm.getDefaultInstance();
        Paper.init(FireBaseService.this);
        get_userID =  Paper.book().read("user_id");

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_02";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);



        }

        //for Running application at Startup
        ComponentName receiver = new ComponentName(this, StartMyServiceAtBootReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        //FireBase RealTime Database initialize
        mDatabase = FirebaseDatabase.getInstance().getReference(get_userID);

        valueEventListener =  mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Tag:", "App title updated");

                for (DataSnapshot node : dataSnapshot.getChildren()) {

                    String get_node_tip = node.getKey();
                    if(get_node_tip.equals("InsertTime")){
                        getInserTime = node.getValue().toString();
                        Log.i("InserTime",getInserTime);
                    }else if(get_node_tip.equals("SubjectText")){
                        getSubject = node.getValue().toString();
                        Log.i("Subject",getSubject);
                    }else if(get_node_tip.equals("Text")){
                        getText = node.getValue().toString();
                        Log.i("Text",getText);
                    }else if(get_node_tip.equals("Type")){
                        getType = node.getValue(Integer.class);
                        Log.i("Type",""+getType);
                    }else if(get_node_tip.equals("UserID")){
                        getUserId = (int) node.getValue(Integer.class);
                        Log.i("UserId",""+getUserId);
                    }else if(get_node_tip.equals("WorkID")){
                        getWorkId = (String) node.getValue();
                        Log.i("WorkId",getWorkId);
                    }

                }

                if(dataSnapshot.getValue() != null) {

                    //send notification
                    sendNotification(getWorkId, getText);

                    //Insert value in Realm Database
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {

                            //creating auto increment of primary key
                            Number maxId = realm.where(BildirimModel.class).max("id");
                            int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                            //Saving process to Realm Databse
                            bildirim = realm.createObject(BildirimModel.class, nextId);
                            bildirim.setInsertTime(getInserTime);
                            bildirim.setSubjectText(getSubject);
                            bildirim.setText(getText);
                            bildirim.setType(getType);
                            bildirim.setUserId(getUserId);
                            bildirim.setWorkId(getWorkId);

                            //commit transaction
                            realm.copyToRealm(bildirim);

                        }
                    });

                 //Save item is new and its not opened
/*                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {

                            realmResults = realm.where(ItemClickCheck.class).findAll();
                            Log.i("Items Count:", "" + realmResults);

                                    //creating auto increment of primary key
                                    Number maxId = realm.where(ItemClickCheck.class).max("id");
                                    int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                                    //Saving process to Realm Databse
                                    itemClickCheck = realm.createObject(ItemClickCheck.class, nextId);
                                    itemClickCheck.setWorkID(getWorkId);
                                    itemClickCheck.setChecable(false);





                        }
                    });*/

                 //getting all data from realm DB
                    is_emri = realm.where(BildirimModel.class).findAll();
                    Log.i("İş Emirleri:", "ds" + is_emri);

                    EventBus.getDefault().post(new MessageEvent(is_emri.size()));
                    //Change count of badger of app icon
                    ShortcutBadger.applyCount(FireBaseService.this, is_emri.size());

                    //Remove value at FireBase RealDatabase
                    mDatabase.removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("TAG", "Failed to read app title value.", error.toException());
            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

/*    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // TODO Auto-generated method stub
        Intent restartService = new Intent(getApplicationContext(),
                this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);

        //Restart the service once it has been killed android


        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +100, restartServicePI);

    }*/

    private void sendNotification(String messageTitle, String messageBody) {

        //When Click Notification
        Intent intent = new Intent(this, Bildirimler.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        long[] pattern = {500,500,500,500};//Titreşim ayarı

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_logo)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupNotificationChannel();

        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + this.getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(this, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

            notificationBuilder.setChannelId(NOTIFICATION_CHANNEL);
        }

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationBuilder.setSound(alarmSound);
        }



        //getting all data from realm DB
        is_emri = realm.where(BildirimModel.class).findAll();
        Log.i("İş Emirleri:", "ds" + is_emri);
        if(is_emri != null) {
            //Post Value with EventBus

            Notification notification = notificationBuilder.build();
            ShortcutBadger.applyNotification(getApplicationContext(), notification, is_emri.size());
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void setupNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL, "ShortcutBadger Sample",
                NotificationManager.IMPORTANCE_LOW);

        channel.setLightColor(Color.RED);

        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabase.removeEventListener(valueEventListener);
    }
}
