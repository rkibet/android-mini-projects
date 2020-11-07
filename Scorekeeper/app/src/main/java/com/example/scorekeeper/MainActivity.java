package com.example.scorekeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int mScores1;
    private int mScores2;

    private TextView mScoresText1;
    private TextView mScoresText2;

    static  final String STATE_SCORE_1 = "Team 1 Score";
    static  final String STATE_SCORE_2 = "Team 2 Score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScoresText1=(TextView)findViewById(R.id.score_1);
        mScoresText2=(TextView)findViewById(R.id.score_2);

        if (savedInstanceState !=null){
            mScores1=savedInstanceState.getInt(STATE_SCORE_1);
            mScores2=savedInstanceState.getInt(STATE_SCORE_2);

            mScoresText1.setText(String.valueOf(mScores1));
            mScoresText2.setText(String.valueOf(mScores2));


        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_SCORE_1,mScores1);
        outState.putInt(STATE_SCORE_2,mScores2);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        int nightMode =AppCompatDelegate.getDefaultNightMode();
        if (nightMode==AppCompatDelegate.MODE_NIGHT_YES){
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
        }
        else{
            menu.findItem(R.string.night_mode).setTitle(R.string.night_mode);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.night_mode){
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if(nightMode == AppCompatDelegate.MODE_NIGHT_YES){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            recreate();
        }
        return super.onOptionsItemSelected(item);
    }

    public void increaseScore(View view) {
        int viewID = view.getId();
        switch (viewID){
            case R.id.increaseTeam1:
                mScores1++;
                mScoresText1.setText(String.valueOf(mScores1));
                break;
            case R.id.increaseTeam2:
                mScores2++;
                mScoresText2.setText(String.valueOf(mScores2));
        }
    }

    public void decreaseScore(View view) {
        int viewID =view.getId();
        switch (viewID){
            case R.id.decreaseTeam1:
                mScores1--;
                mScoresText1.setText(String.valueOf(mScores1));
                break;
            case R.id.decreaseTeam2:
                mScores2--;
                mScoresText2.setText(String.valueOf(mScores2));
        }
    }
}