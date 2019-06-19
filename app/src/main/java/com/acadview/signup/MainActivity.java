package com.acadview.signup;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
    Pattern.compile("^" +
            //"(?=.*[0-9])" +         //at least 1 digit
            //"(?=.*[a-z])" +         //at least 1 lower case letter
            //"(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{4,}" +               //at least 4 characters
            "$");
    private static final Pattern NAME =
            Pattern.compile("[A-Z][a-z]+( [A-Z][a-z]+)*");

    private  static  final int REQUEST_CODE_SMS = 101;


    private  TextInputLayout EditInputName;
    private TextInputLayout password;
    private  TextInputLayout confirmPwd;
    private  TextInputLayout MobileNumber;
    Button btnOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditInputName = findViewById(R.id.name);
        password = findViewById(R.id.password);
        confirmPwd = findViewById(R.id.confirmPwd);
        MobileNumber = findViewById(R.id.number);
        btnOTP = findViewById(R.id.otp);

        String[] permissions = {
                Manifest.permission.SEND_SMS

        };

        if(ActivityCompat.checkSelfPermission(MainActivity.this,permissions[0])!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MainActivity.this,permissions,REQUEST_CODE_SMS);
        }

        btnOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = MobileNumber.getEditText().getText().toString().trim();


                Random r = new Random();
                int i1 = r.nextInt(9999 - 1111) + 1111;
                String OTP = i1 + "";

                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(number,null,OTP,null,null);


                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("OTP_Number",OTP);
                startActivity(intent);


            }
        });




    }

    private boolean validateName() {
        String EditName = EditInputName.getEditText().getText().toString().trim();

        if(EditName.isEmpty()){
            EditInputName.setError("Field can't be empty");
            return false;
        }else if (EditName.length() > 20){
            EditInputName.setError("User Name to long");
            return false;
        } else if (!NAME.matcher(EditName).matches()){
            {
                EditInputName.setError("Invalid Name");
                return false;
            }
        } else {
            EditInputName.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput = password.getEditText().getText().toString().trim();

        if(passwordInput.isEmpty()){
            password.setError("Field can't be empty");
            return false;
        }else if ( !PASSWORD_PATTERN.matcher(passwordInput).matches()){
            password.setError("Invalid Name");
            return false;
        }else{
            password.setError(null);
            return true;
        }
    }

    private boolean validateCPassword() {
        String passwordInput = password.getEditText().getText().toString().trim();
        String Cpassword = confirmPwd.getEditText().getText().toString().trim();

        if(Cpassword.isEmpty()) {
            password.setError("Field can't be empty");
            return false;
        }else if(!Cpassword.equals(passwordInput)){
            confirmPwd.setError("password not matched");
            return false;
        }else{
            password.setError(null);
            return true;
        }
    }


    private boolean validateNumber() {
        String PhoneNo = MobileNumber.getEditText().getText().toString().trim();

        if(PhoneNo.isEmpty()){
            MobileNumber.setError("Field can't be empty");
            return false;
        }else if ((PhoneNo.length() != 10)){
            MobileNumber.setError("Invalid Number");
            return false;
        }else{
            MobileNumber.setError(null);
            return true;
        }
    }

    public void confirmInput(View v) {
        if (!validateName() | !validatePassword() | !validateCPassword() | !validateNumber()) {
            return;
        }

        String input = "Name " + EditInputName.getEditText().getText().toString();
        input += "\n";
        input += "Password: " + password.getEditText().getText().toString();
        input += "\n";
        input += "Mobile Number: " + MobileNumber.getEditText().getText().toString();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();

//        OTP();

    }

//   public void OTP(){
//
//        String number = MobileNumber.getEditText().getText().toString().trim();
//
//
//       Random r = new Random();
//       int i1 = r.nextInt(9999 - 1111) + 1111;
//       String OTP = i1 + "";
//
//       SmsManager sms = SmsManager.getDefault();
//       sms.sendTextMessage(number,null,OTP,null,null);
//
//
//       Intent intent = new Intent(MainActivity.this,Main2Activity.class);
//       intent.putExtra("OTP_Number",OTP);
//       startActivity(intent);
//
//   }
//
  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==REQUEST_CODE_SMS){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this,"Permission Granted",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(MainActivity.this,"Not Granted",Toast.LENGTH_LONG).show();
            }
        }
    }

}
