package com.example.aman.breast_friendapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Resultactivity extends AppCompatActivity {

    TextView txtview;
    Button btnok;
    private long backpressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultactivity);
        int number = getIntent().getExtras().getInt("value");
        txtview = findViewById(R.id.textView3);
        if(number==1){
            txtview.setText("  Malignant  ");
        }
        else{
            txtview.setText("  Benign  ");
        }
         btnok = findViewById(R.id.btnok);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Resultactivity.this,DashboardActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onBackPressed() {

        if(backpressedTime+2000>System.currentTimeMillis()){
           // super.onBackPressed();
           // finish();
            System.exit(0);
            return;
        }
        else{
            Toast.makeText(getBaseContext(),"Press again to exit or OK",Toast.LENGTH_SHORT).show();
        }
        backpressedTime = System.currentTimeMillis();
    }
}
