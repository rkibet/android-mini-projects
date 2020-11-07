package com.example.implicitintents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText mWebsiteEditText;
    private EditText mLocationEditText;
    private EditText mShareTextEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebsiteEditText = findViewById(R.id.website_edittext);
        mLocationEditText = findViewById(R.id.location_edittext);
        mShareTextEditText=findViewById(R.id.share_edittext);
    }

    public void openWebsite(View view) {
        String url = mWebsiteEditText.getText().toString();
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

        } else {
            Log.d("ImplicitIntents", "can't handle this");
        }
    }

    public void openLocation(View view) {
        String loc = mLocationEditText.getText().toString();
        Uri addressUri = Uri.parse("geo:0,o?q" + loc);
        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

        if (getIntent().resolveActivity(getPackageManager()) != null) {

            startActivity(intent);

        } else {
            Log.d("implicitIntent", "Cant handle this intent");

        }

    }


    public void shareText(View view) {
        String txt = mShareTextEditText.getText().toString();
        String mimeText="text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeText)
                .setChooserTitle(R.string.share_text_with)
                .setText(txt)
                .startChooser();
    }
}