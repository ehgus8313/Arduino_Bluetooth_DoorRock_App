package com.example.k201444001;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();

    private TextView displayError;

    private EditText btname;
    private EditText btpassword;
    private EditText password;


    private RadioGroup radioGroup;

    private boolean loginOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle("도어락 등록");

        btname = (EditText)findViewById(R.id.btname);
        btpassword = (EditText)findViewById(R.id.btpassword);
        password = (EditText)findViewById(R.id.password);


        radioGroup = (RadioGroup)findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if(id == R.id.with_fingerprint){
                    loginOption = false;
                }
                if(id == R.id.with_fingerprint_and_password){
                    loginOption = true;
                }
            }
        });

        Button signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btnameValue = btname.getText().toString();
                String btpasswordValue = btpassword.getText().toString();
                String passwordValue = password.getText().toString();

                int selectedButtonId = radioGroup.getCheckedRadioButtonId();

                if(TextUtils.isEmpty(btnameValue) || TextUtils.isEmpty(btpasswordValue)|| TextUtils.isEmpty(passwordValue)){
                    Toast.makeText(SignUpActivity.this, "모든 입력 필드를 입력해야 합니다.", Toast.LENGTH_LONG).show();
                }else if(selectedButtonId == -1){
                    Toast.makeText(SignUpActivity.this, "로그인 옵션을 선택해야 합니다.", Toast.LENGTH_LONG).show();
                }else{
                    Gson gson = ((CustomApplication)getApplication()).getGsonObject();
                    UserObject userData = new UserObject(btnameValue, btpasswordValue, passwordValue, loginOption);
                    String userDataString = gson.toJson(userData);
                    CustomSharedPreference pref = ((CustomApplication)getApplication()).getShared();
                    pref.setUserData(userDataString);

                    btname.setText("");
                    btpassword.setText("");
                    password.setText("");

                    Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }
            }
        });
    }
}
