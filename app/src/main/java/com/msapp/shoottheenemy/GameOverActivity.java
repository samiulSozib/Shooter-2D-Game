package com.msapp.shoottheenemy;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


public class GameOverActivity extends AppCompatActivity {

    TextView personal_best,current_score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_over);

        int score=getIntent().getExtras().getInt("score");
        SharedPreferences sharedPreferences=getSharedPreferences("MyPref",0);
        int scoreSP=sharedPreferences.getInt("scoreSP",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if (score>scoreSP){
            scoreSP=score;
            editor.putInt("scoreSP",scoreSP);
            editor.commit();
        }

        personal_best=findViewById(R.id.personal_best_score_text);
        current_score=findViewById(R.id.score_text);
        personal_best.setText("Best Score : "+scoreSP);
        current_score.setText("Current Score : "+score);


    }

    public void exit_game(View view) {
        finish();
    }

    public void restart_game(View view) {
        startActivity(new Intent(GameOverActivity.this,MainActivity.class));
        finish();
    }
}
