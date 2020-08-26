package com.example.alarmer;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class TimerFragment extends Fragment {
    private EditText hourText, minText, secText;
    private Button startPauseTimer, resetTimer;
    private ProgressBar progressBar;
    private int prog = 0;
    private Boolean isRunning = false;
    private Boolean isPaused=false;
    private long timeset, timeLeft, hour, min, sec,pausedTime;
    private CountDownTimer mCountDownTimer;
    private TextView timeText;
    Ringtone tune;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timer, container, false);
        resetTimer = (Button) v.findViewById(R.id.cancel_timer);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        hourText = (EditText) v.findViewById(R.id.hours);
        minText = (EditText) v.findViewById(R.id.minutes);
        secText = (EditText) v.findViewById(R.id.seconds);
        timeText=(TextView) v.findViewById(R.id.timetext);
        progressBar.setProgress(100);
        Uri ringTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        tune = RingtoneManager.getRingtone(getActivity(), ringTone);


        startPauseTimer = (Button) v.findViewById(R.id.pause_timer);

        startPauseTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    timerStart();

                } else {
                    timerPause();
                }
            }
        });
resetTimer.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        timereset();
    }
});
updateCountdownText();
        return v;
    }

    public void timerStart() {
if(!isPaused) {
    hour = Integer.parseInt(hourText.getText().toString()) + 0;
    min = Integer.parseInt(minText.getText().toString()) + 0;
    sec = Integer.parseInt(secText.getText().toString()) + 0;

    timeset = (hour * 3600 + min * 60 + sec) * 1000;
    timeLeft = (hour * 3600 + min * 60 + sec) * 1000;
}
        if (timeset != 0) {
            hourText.setVisibility(View.INVISIBLE);
            minText.setVisibility(View.INVISIBLE);
            secText.setVisibility(View.INVISIBLE);
            mCountDownTimer = new CountDownTimer(timeLeft, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeft = millisUntilFinished;
                    updateCountdownText();
                    if(!isPaused)
                        prog = (int) ((millisUntilFinished * 100) / (timeset));
                    else
                        prog = (int) ((100 * pausedTime) / (timeset));
                    progressBar.setProgress(prog);
                    isPaused=false;
                }

                @Override
                public void onFinish() {
                    tune.play();
                    isRunning=false;
                    progressBar.setProgress(0);
                    progressBar.setProgress(100);
                    startPauseTimer.setText("START");
                    startPauseTimer.setVisibility(View.INVISIBLE);
                    resetTimer.setVisibility(View.VISIBLE);
                    resetTimer.setText("STOP");
                    Toast.makeText(getContext(), "Time over", Toast.LENGTH_SHORT).show();
                }
            }.start();
            isRunning = true;
            startPauseTimer.setText("PAUSE");
            resetTimer.setVisibility(View.INVISIBLE);
        }
        else{
            Toast.makeText(getContext(),"ENTER TIME",Toast.LENGTH_SHORT).show();
        }
    }

    public void timerPause() {
        pausedTime=timeLeft;
        isPaused=true;
        mCountDownTimer.cancel();
        isRunning = false;
        startPauseTimer.setText("START");
        resetTimer.setVisibility(View.VISIBLE);
    }
    public void timereset(){
        tune.stop();
        timeLeft=timeset;
        isRunning=false;
        resetTimer.setText("RESET");
        startPauseTimer.setVisibility(View.VISIBLE);
        hourText.setVisibility(View.VISIBLE);
        minText.setVisibility(View.VISIBLE);
        secText.setVisibility(View.VISIBLE);
        isPaused=false;
        progressBar.setProgress(100);
        timeText.setText("00:00:00");
        hourText.setText("00");
        minText.setText("00");
        secText.setText("00");
    }


    public void updateCountdownText(){
        int hours= (int) (((timeLeft)/1000)/3600);
        int minutes=(int) (((timeLeft)/1000)/60);
        int seconds=(int) (((timeLeft)/1000)%60);
        String timetextvalue= String.format(Locale.getDefault(),"%02d:%02d:%02d",hours,minutes,seconds);
        timeText.setText(timetextvalue);

    }

}
