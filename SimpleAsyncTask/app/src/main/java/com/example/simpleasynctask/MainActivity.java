package com.example.simpleasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private WeakReference <TextView> mTextView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView=findViewById(R.id.textView1);
    }

    public void startTask(View view) {
        mTextView.setText
    }
    public  class SimpleAsyncTask extends AsyncTask<Void,Void,String>{

    }
    SimpleAsyncTask(TextView tv){
        mTextView=new WeakReference<>(tv);
    }

    @Override
    protected String doInBackground(Void... voids){
        Random r = new Random();
        int n = r.nextInt(11);
        int s=n*200;
        try {
            Thread.sleep(s);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        return "Awake At Last After Sleeping For "+s+"Miliseconds";

    }
    protected  void onPostExcecute(String results){
        mTextView.get().setText(results);
    }

}