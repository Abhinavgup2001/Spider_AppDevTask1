package com.example.alarmer;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StopwatchFragment extends Fragment {
    Chronometer chronometer;
    ImageButton start, stop, lap;

    private boolean isResume;
    Handler handler;
    long tMillisec, tStart, tBuff, tUpdate = 0L;
    int sec, min, millisec;
    ViewGroup finalContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stopwatch, container, false);

        chronometer = v.findViewById(R.id.chronometer);
        start = v.findViewById(R.id.start);
        stop = v.findViewById(R.id.stop);
        lap = v.findViewById(R.id.lap);
        container=v.findViewById(R.id.container);
                handler = new Handler();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isResume) {
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    isResume = true;
                    stop.setVisibility(View.INVISIBLE);
                    start.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_pause_24));
                    lap.setVisibility(View.VISIBLE);

                } else {
                    tBuff += tMillisec;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    isResume = false;
                    stop.setVisibility(View.VISIBLE);
                    start.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24));

                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24));
                tMillisec = 0L;
                tStart = 0L;
                tBuff = 0L;
                tUpdate = 0L;
                sec = 0;
                min = 0;
                millisec = 0;
                chronometer.setText("00:00:000");
                finalContainer.removeAllViews();
            }
        });
        finalContainer = container;
        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater1 = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView = inflater1.inflate(R.layout.flags,null);
                TextView txtView = addView.findViewById(R.id.txtcontent);
                txtView.setText(chronometer.getText());
                if(txtView.getParent() != null) {
                    ((ViewGroup)txtView.getParent()).removeView(txtView); // <- fix
                }
                finalContainer.addView(txtView);

            }
        });

        return v;
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMillisec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMillisec;
            sec = (int) (tUpdate / 1000);
            min = sec / 60;
            sec = sec % 60;
            millisec = (int) (tUpdate % 1000);
            chronometer.setText(String.format("%02d", min) + ":" + String.format("%02d", sec) + ":" + String.format("%03d", millisec));
            handler.postDelayed(this, 60);
        }
    };


}
