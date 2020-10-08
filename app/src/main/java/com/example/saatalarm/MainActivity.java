package com.example.saatalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtSaat;
    Handler handle =null;
    Runnable runnable= null;
    String zaman;
    Button btnAlarm;
    private TimePickerDialog timePickerDialog;
    final static int islem_kodu = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSaat= findViewById(R.id.textSaat);


        btnAlarm=findViewById(R.id.btnAyarla);
        btnAlarm.setOnClickListener(this);

        final SimpleDateFormat bicim = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        zaman = bicim.format(new Date());
        txtSaat.setText(zaman);

        handle = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                zaman = bicim.format(new Date());
                txtSaat.setText(zaman);
                handle.postDelayed(runnable, 1000);
            }
        };
        runnable.run();
    }


    public void onClick(View view) {
        openPickerDialog(false);
    }

    private void openPickerDialog(boolean tumgunsaat){
        Calendar calendar =Calendar.getInstance();

        timePickerDialog=new TimePickerDialog(
                MainActivity.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                tumgunsaat);
        timePickerDialog.setTitle("Alarm Ayarla");

        timePickerDialog.show();

    }
    TimePickerDialog.OnTimeSetListener onTimeSetListener
            =new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int saat, int dakika){


        Calendar calNow =Calendar.getInstance();
        Calendar calSet =(Calendar)calNow.clone();

        calSet.set(Calendar.HOUR_OF_DAY,saat);
        calSet.set(Calendar.MINUTE,dakika);
        calSet.set(Calendar.SECOND,0);
        calSet.set(Calendar.MILLISECOND,0);

        if (calSet.compareTo(calNow)<=0){
            calSet.add(Calendar.DATE,1);
        }
        setAlarm(calSet);
    }};

    private void setAlarm(Calendar alarmCalendar){
        Toast.makeText(getApplicationContext(),"Alarm Ayarlandı",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),islem_kodu,intent,0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,alarmCalendar.getTimeInMillis(),pendingIntent);

    }



}
