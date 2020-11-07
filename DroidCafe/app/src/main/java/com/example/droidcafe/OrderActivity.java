package com.example.droidcafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG =MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent =getIntent();
        String message = "Order :" + intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView textView = findViewById(R.id.order_textView);
        textView.setText(message);

        Spinner spinner = findViewById(R.id.label_spinner);
        if (spinner !=null){
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.labels_array,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinner !=null){
            spinner.setAdapter(adapter);
        }
        EditText editText =findViewById(R.id.phone_number_text);

        if (editText !=null) editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
               boolean handled=false;
               if (actionId ==EditorInfo.IME_ACTION_SEND){
                   dialNumber();
                   handled=true;
               }
               return handled;
            }
        });
        }
    private  void dialNumber(){
        EditText editText =findViewById(R.id.phone_number_text);
        String phoneNo =null;
        if (editText !=null) phoneNo="tel" + editText.getText().toString();
        Log.d(TAG,"dialnumber: "+ phoneNo);
        Intent intent=new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(phoneNo));
        if (intent.resolveActivity(getPackageManager()) !=null){
            startActivity(intent);
        }
        else{
            Log.d(TAG,"ImplicitIntents : Cant handle this!");
        }

    }

    public void displayToast(String message){
        Toast.makeText(getApplicationContext(),message,
                Toast.LENGTH_SHORT).show();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.sameday:
                if (checked){
                    displayToast(getString(R.string.same_day_messanger_service));
                }
                break;
            case R.id.nextday:
                if (checked){
                    displayToast(getString(R.string.next_day_ground_delivery));
                }
                break;
            case R.id.pickup:
                if (checked){
                    displayToast(getString(R.string.pick_up));
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
         String spinnerLabel = adapterView.getItemAtPosition(i).toString();
        displayToast(spinnerLabel);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}