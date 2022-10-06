package com.hfad.lab5;

import androidx.appcompat.app.AppCompatActivity;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Chronometer stopwatch;
    private boolean running = false;
    private long offset = 0;

    public static final String OFFSET_KEY = "offset";
    public static final String RUNNING_KEY = "running";
    public static final String BASE_KEY = "base";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopwatch = findViewById(R.id.chr_stopwatch);

        Button btnStart = findViewById(R.id.btn_start);
        Button btnPause = findViewById(R.id.btn_pause);
        Button btnReset = findViewById(R.id.btn_reset);

        if (savedInstanceState != null)
        {
            offset = savedInstanceState.getLong(OFFSET_KEY);
            running = savedInstanceState.getBoolean(RUNNING_KEY);

            if (running)
            {
                stopwatch.setBase(savedInstanceState.getLong(BASE_KEY));
                stopwatch.stop();
            }
            else
            {
                setBaseTime();
            }
        }


        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!running)
                {
                    setBaseTime();
                    stopwatch.start();
                    running = true;

                    /**
                    System.out.println("START:");
                    System.out.println("\trunning: " + running);
                    System.out.println("\toffset: " + offset);
                    System.out.println("\tbase: " + stopwatch.getBase());
                    **/

                }
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
               if (running)
               {
                   saveOffset();
                   stopwatch.stop();
                   running = false;

                   /**
                   System.out.println("PAUSE:");
                   System.out.println("\trunning: " + running);
                   System.out.println("\toffset: " + offset);
                   System.out.println("\tbase: " + stopwatch.getBase());
                   **/
               }
           }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                offset = 0;
                setBaseTime();

                /**
                System.out.println("RESET:");
                System.out.println("\trunning: " + running);
                System.out.println("\toffset: " + offset);
                System.out.println("\tbase: " + stopwatch.getBase());
                **/
            }
        });

    }

    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putLong(OFFSET_KEY, offset);
        savedInstanceState.putBoolean(RUNNING_KEY, running);
        savedInstanceState.putLong(BASE_KEY, stopwatch.getBase());
    }


    public void setBaseTime()
    {
        stopwatch.setBase(SystemClock.elapsedRealtime() - offset);
    }

    public void saveOffset()
    {
        offset = SystemClock.elapsedRealtime() - stopwatch.getBase();
    }

}