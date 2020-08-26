package com.example.alarmer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class SetAlarm extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener  {
    Button startAlarm, cancelAlarm;
    TextView alarmTextView;
    Spinner spinner;
    MediaPlayer mediaPlayer;
    CheckBox sun, mon, tue, wed, thu, fri, sat;
    String[] ringtones = {"Default Ringtone", "Extreme Alarm", "Let me love U", "LoveStory", "Moonlight Sonata", "See You Again", "Swing Jazz", "Tomorrowland"};
    int ringtone;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor texteditor;

    AlarmFragment alarmFragment=new AlarmFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);
        startAlarm = (Button) findViewById(R.id.start);
        cancelAlarm = (Button) findViewById(R.id.cancel);
        alarmTextView = (TextView) findViewById(R.id.textview_alarm);
        sun = (CheckBox) findViewById(R.id.sun);
        mon = (CheckBox) findViewById(R.id.mon);
        tue = (CheckBox) findViewById(R.id.tue);
        wed = (CheckBox) findViewById(R.id.wed);
        thu = (CheckBox) findViewById(R.id.thu);
        fri = (CheckBox) findViewById(R.id.fri);
        sat = (CheckBox) findViewById(R.id.sat);

        sharedPreferences = this.getSharedPreferences("Alarm",Context.MODE_PRIVATE);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, ringtones);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        ringtone = 0;
                        break;
                    case 1:
                        ringtone = 1;

                        break;
                    case 2:
                        ringtone = 2;

                        break;
                    case 3:
                        ringtone = 3;

                        break;
                    case 4:
                        ringtone = 4;

                        break;
                    case 5:
                        ringtone = 5;

                        break;
                    case 6:
                        ringtone = 6;

                        break;
                    case 7:
                        ringtone = 7;

                        break;
                    default:
                        ringtone = 0;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mediaPlayer.stop();
            }
        });




        startAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sun.isChecked()&&!mon.isChecked()&&!tue.isChecked()&&!wed.isChecked()&&!thu.isChecked()&&!fri.isChecked()&&!sat.isChecked()){
                    Toast.makeText(SetAlarm.this, "Please Select Day", Toast.LENGTH_SHORT).show();
                }else {

                    DialogFragment timePicker = new TimePickerFragment();
                    timePicker.show(getSupportFragmentManager(), "time picker");
                }

            }
        });
        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
                alarmTextView.setText("NO ALARM");
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        texteditor = sharedPreferences.edit();
        texteditor.clear();
        texteditor.putInt("hour",hourOfDay);
        texteditor.putInt("min",minute);
        texteditor.apply();
         alarmFragment.setTimeTextView(hourOfDay,minute);
        checkAlarm(hourOfDay, minute);
        Toast.makeText(this, "ALARM SET", Toast.LENGTH_SHORT).show();


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startAlarm(Calendar c, int requestcode) {
        alarmTextView.setText(DateFormat.format("hh:mm aa",c));
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReciever.class);
        intent.putExtra("ringtone", ringtone);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,requestcode, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void checkAlarm(int hourOfDay, int minute) {
        if (sun.isChecked()) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);
            c.set(Calendar.SECOND, 0);
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 7);
                Log.i("time", c.getTime().toString());
            }

            startAlarm(c, 1);
        }
        if (mon.isChecked()) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);
            c.set(Calendar.SECOND, 0);
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 7);
                Log.i("time", c.getTime().toString());
            }

            startAlarm(c, 2);
        }
        if (tue.isChecked()) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);
            c.set(Calendar.SECOND, 0);
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 7);
                Log.i("time", c.getTime().toString());
            }

            startAlarm(c, 3);
        }
        if (wed.isChecked()) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);
            c.set(Calendar.SECOND, 0);
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 7);
                Log.i("time", c.getTime().toString());

            }

            startAlarm(c, 4);
        }
        if (thu.isChecked()) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);
            c.set(Calendar.SECOND, 0);
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 7);
                Log.i("time", c.getTime().toString());
            }

            startAlarm(c, 5);
        }
        if (fri.isChecked()) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);
            c.set(Calendar.SECOND, 0);
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 7);
                Log.i("time", c.getTime().toString());
            }


            startAlarm(c, 6);
        }
        if (sat.isChecked()) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);
            c.set(Calendar.SECOND, 0);
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 7);
                Log.i("time", c.getTime().toString());
            }

            startAlarm(c, 7);
        }

    }


}
