package com.example.k201444001;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setTitle("사용자 정보");

        String userBio = getIntent().getExtras().getString("USER_BIO");
        Gson gson = ((CustomApplication)getApplication()).getGsonObject();

        UserObject mUserObject = gson.fromJson(userBio, UserObject.class);
        String name = mUserObject.getBTname();
        String btpassword = mUserObject.getBtpassword();
        String password = mUserObject.getPassword();

        TextView name1 = (TextView)findViewById(R.id.name);
        TextView btpassword1 = (TextView)findViewById(R.id.btpass);
        TextView password1 = (TextView)findViewById(R.id.pass);
        name1.setText(name);
        btpassword1.setText(btpassword);
        password1.setText(password);
    }
}
