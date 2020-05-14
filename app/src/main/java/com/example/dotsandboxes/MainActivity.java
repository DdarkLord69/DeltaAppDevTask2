package com.example.dotsandboxes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import views.BoardView;
import views.BoardViewTwo;

public class MainActivity extends AppCompatActivity {

    private BoardViewTwo boardViewTwo;
    public static Button undo;
    public static TextView res;

    public static TextView p1;
    public static TextView p2;
    public static TextView p3;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static int r;
    public static int c;

    public static SoundPool soundPool;
    public static int sound1;

    private static Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        sound1 = soundPool.load(this, R.raw.beep,1);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int w = displayMetrics.widthPixels;

        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        boardViewTwo = findViewById(R.id.bv2);
        undo = findViewById(R.id.btnUndo);
        res = findViewById(R.id.tvResult);

        p1 = findViewById(R.id.tvP1);
        p2 = findViewById(R.id.tvP2);
        p3 = findViewById(R.id.tvP3);

        sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        BoardViewTwo.noOfRows = sharedPreferences.getInt("rows",5);
        BoardViewTwo.noOfColumns = sharedPreferences.getInt("columns",5);

        boardViewTwo.maxi = Math.max(boardViewTwo.noOfColumns, boardViewTwo.noOfRows);

        //int w = boardViewTwo.getWidth();

        //boardViewTwo.spacing = (boardViewTwo.maxi == 3) ? 260 : ((boardViewTwo.maxi == 4) ? 195 : 156) ;
        //boardViewTwo.spacing = (boardViewTwo.maxi == 3) ? 240 : ((boardViewTwo.maxi == 4) ? 192 : 160) ;            //960 divided by 4,5,6
        boardViewTwo.spacing = (boardViewTwo.maxi == 3) ? w/4 : ((boardViewTwo.maxi == 4) ? w/5 : w/6) ;

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoardViewTwo.undobtn();
                boardViewTwo.postInvalidate();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }

    public static void vibrate() {
        vibrator.vibrate(300);
    }
}
