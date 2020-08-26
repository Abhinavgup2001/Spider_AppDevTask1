package com.example.alarmer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmFragment extends Fragment {
    private static Calendar calendar;
    SharedPreferences sharedPreferences;
    private static TextView timetextview;


    public AlarmFragment() {

    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alarm, container, false);
        Button setAlarm = (Button) v.findViewById(R.id.add_alarm);
        timetextview=(TextView)v.findViewById(R.id.alarmsettext);

        sharedPreferences = getActivity().getSharedPreferences("Alarm", Context.MODE_PRIVATE);
        calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,sharedPreferences.getInt("hour",0),sharedPreferences.getInt("min",0));

         if(calendar!=null)
            timetextview.setText(DateFormat.format("hh:mm aa",calendar));

        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SetAlarm.class);
                startActivity(intent);
            }
        });


        return v;
    }

    public void setTimeTextView(int hour,int min) {
        calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, hour, min);
                timetextview.setText(DateFormat.format("hh:mm aa", calendar));

    }

}

/**
 mTextView = (TextView) v.findViewById(R.id.textview_alarm);
 **/


