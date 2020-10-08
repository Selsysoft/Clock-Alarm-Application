package com.example.saatalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Toast.makeText(context,"ALARM!!!",Toast.LENGTH_SHORT).show();
        Uri alarmMelodisi = RingtoneManager.getDefaultUri((RingtoneManager.TYPE_ALARM));
        if(alarmMelodisi == null){
            alarmMelodisi = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context,alarmMelodisi);
        ringtone.play();



    }

}
