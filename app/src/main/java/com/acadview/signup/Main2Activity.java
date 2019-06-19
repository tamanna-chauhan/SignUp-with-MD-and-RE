package com.acadview.signup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    EditText OTPText;
    Button OTPBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        OTPText = findViewById(R.id.otp);
        OTPBtn = findViewById(R.id.OTPbtn);

        final String OTP = getIntent().getStringExtra("OTP_Number");

        final String SendOTP = String.valueOf(OTP);

        OTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String EnterOTP = OTPText.getText().toString().trim();
                String EnteredOTP = String.valueOf(EnterOTP);

                if(SendOTP.equals(EnteredOTP)){
                    Toast.makeText(Main2Activity.this,"OTP Confirmed Successfully",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(Main2Activity.this,"You Entered Wrong OTP",Toast.LENGTH_LONG).show();
                }


            }
        });

    }
}
