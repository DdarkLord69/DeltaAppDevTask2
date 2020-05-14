package com.example.dotsandboxes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import views.BoardViewTwo;

public class StartScreen extends AppCompatActivity {

    EditText rows;
    EditText columns;
    Button start2p;
    Button start3p;
    Button easy;
    Button hard;

    private int r;
    private int c;

    private int row;
    private int column;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        rows = findViewById(R.id.etRows);
        columns = findViewById(R.id.etColumns);
        start2p = findViewById(R.id.btnStart);
        start3p = findViewById(R.id.btn3p);
        easy = findViewById(R.id.btnEasy);
        hard = findViewById(R.id.btnHard);


        sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        start2p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rows.getText().toString().trim().length() > 0 && columns.getText().toString().trim().length() > 0) {
                    int row = Integer.parseInt(rows.getText().toString());
                    int column = Integer.parseInt(columns.getText().toString());

                    if (row > 5 || row < 3 || column > 5 || column < 3) {
                        Toast.makeText(StartScreen.this, "Enter value between 3 and 5", Toast.LENGTH_SHORT).show();
                    } else {
                        editor.putInt("rows", row);
                        editor.putInt("columns", column);

                        editor.commit();

                        startGame2p();
                    }
                }
            }
        });

        start3p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rows.getText().toString().trim().length() > 0 && columns.getText().toString().trim().length() > 0) {
                    int row = Integer.parseInt(rows.getText().toString());
                    int column = Integer.parseInt(columns.getText().toString());

                    if (row > 5 || row < 3 || column > 5 || column < 3) {
                        Toast.makeText(StartScreen.this, "Enter value between 3 and 5", Toast.LENGTH_SHORT).show();
                    } else {
                        editor.putInt("rows", row);
                        editor.putInt("columns", column);

                        editor.commit();

                        startGame3p();
                    }
                }
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rows.getText().toString().trim().length() > 0 && columns.getText().toString().trim().length() > 0) {
                    int row = Integer.parseInt(rows.getText().toString());
                    int column = Integer.parseInt(columns.getText().toString());

                    if (row > 5 || row < 3 || column > 5 || column < 3) {
                        Toast.makeText(StartScreen.this, "Enter value between 3 and 5", Toast.LENGTH_SHORT).show();
                    } else {
                        editor.putInt("rows", row);
                        editor.putInt("columns", column);

                        editor.commit();

                        startGameEasy();
                    }
                }
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rows.getText().toString().trim().length() > 0 && columns.getText().toString().trim().length() > 0) {
                    int row = Integer.parseInt(rows.getText().toString());
                    int column = Integer.parseInt(columns.getText().toString());

                    if (row > 5 || row < 3 || column > 5 || column < 3) {
                        Toast.makeText(StartScreen.this, "Enter value between 3 and 5", Toast.LENGTH_SHORT).show();
                    } else {
                        editor.putInt("rows", row);
                        editor.putInt("columns", column);

                        editor.commit();

                        startGameHard();
                    }
                }
            }
        });
    }

    void startGame2p() {
        BoardViewTwo.noOfPlayers = 2;
        //BoardViewTwo.noOfRows=row;
        //BoardViewTwo.noOfColumns=column;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    void startGame3p() {
        BoardViewTwo.noOfPlayers = 3;
        //BoardViewTwo.noOfRows=row;
        //BoardViewTwo.noOfColumns=column;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    void startGameEasy() {
        BoardViewTwo.noOfPlayers = 1;
        BoardViewTwo.difficulty = 1;
        //BoardViewTwo.noOfRows=row;
        //BoardViewTwo.noOfColumns=column;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    void startGameHard() {
        BoardViewTwo.noOfPlayers = 1;
        BoardViewTwo.difficulty = 2;
        //BoardViewTwo.noOfRows=row;
        //BoardViewTwo.noOfColumns=column;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
