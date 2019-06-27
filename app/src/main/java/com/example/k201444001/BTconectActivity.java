package com.example.k201444001;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class BTconectActivity extends AppCompatActivity {

    private BluetoothSPP bt;
    LinearLayout lay,hidelay1,hidelay2;
    ImageView bluet;
    int mCount2;
    TextView onofftext,dial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btconect);
        bluet = (ImageView)findViewById(R.id.bluetoothdc);
        onofftext = (TextView)findViewById(R.id.onofftext);
        bt = new BluetoothSPP(this); //Initializing
        final String userBio = getIntent().getExtras().getString("USER_BIO");
        Gson gson = ((CustomApplication) getApplication()).getGsonObject();
        UserObject mUserObject = gson.fromJson(userBio, UserObject.class);
        String name = mUserObject.getBTname();
        TextView name1 = (TextView)findViewById(R.id.doorlockname);
        name1.setText(name);

        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getApplicationContext()
                    , "Bluetooth를 사용할 수 없습니다"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
            public void onDataReceived(byte[] data, String message) {
                Toast.makeText(BTconectActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "도어락 " + name + "\n" + address + "에 연결 되었습니다."
                        , Toast.LENGTH_SHORT).show();
                bluet.setImageResource(R.drawable.bluetooth);
                onofftext.setText("도어락 " + name + "에 연결");
                hidelay2.setBackgroundResource(R.drawable.mainback);
                mCount2=1;
            }

            public void onDeviceDisconnected() { //연결해제
                Toast.makeText(getApplicationContext()
                        , "도어락 연결이 해제되었습니다.", Toast.LENGTH_SHORT).show();
                bluet.setImageResource(R.drawable.bluetoothdc);
                onofftext.setText("도어락 연결해제");
                hidelay2.setBackgroundResource(R.drawable.mainback2);
            }

            public void onDeviceConnectionFailed() { //연결실패
                Toast.makeText(getApplicationContext()
                        , "도어락 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                bluet.setImageResource(R.drawable.bluetoothdc);
                hidelay2.setBackgroundResource(R.drawable.mainback2);
            }
        });

        if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
            bt.disconnect();
        } else {
            Intent intent = new Intent(getApplicationContext(), DeviceList.class);
            startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
        }

        final ImageView onoff = (ImageView) findViewById(R.id.onoff);
        onoff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Gson gson = ((CustomApplication) getApplication()).getGsonObject();
                    UserObject mUserObject = gson.fromJson(userBio, UserObject.class);
                    String pass = mUserObject.getBtpassword();
                    bt.send(pass, false);
                    onofftext.setText("도어락이 열렸습니다.");
                    hidelay2.setBackgroundResource(R.drawable.mainback);
                    dial.setText("");
            }

        });

        final ImageView off = (ImageView) findViewById(R.id.off);
        off.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.send("A", false);
                onofftext.setText("도어락이 잠겼습니다.");
                hidelay2.setBackgroundResource(R.drawable.mainback2);
                dial.setText("");
            }

        });




        final ImageButton profile = (ImageButton) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent profileIntent = new Intent(BTconectActivity.this, UserProfileActivity.class);
                profileIntent.putExtra("USER_BIO", userBio);
                startActivity(profileIntent);
            }
        });

        final ImageButton BTconnect = (ImageButton) findViewById(R.id.BTconnect);
        BTconnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
            }
        });

        lay = (LinearLayout)findViewById(R.id.lay);
        hidelay1 = (LinearLayout)findViewById(R.id.hidelay1);
        hidelay2 = (LinearLayout)findViewById(R.id.hidelay2);
        final ImageButton editbutton = (ImageButton) findViewById(R.id.editbutton);
        editbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lay.getVisibility() == View.GONE) {
                    lay.setVisibility(View.VISIBLE); // or GONE
                    hidelay1.setVisibility(View.GONE);
                    hidelay2.setVisibility(View.GONE);
                }
            }
        });
        final Button close = (Button) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lay.setVisibility(View.GONE);
                hidelay1.setVisibility(View.VISIBLE);
                hidelay2.setVisibility(View.VISIBLE);
            }
        });




        //블루투스 번호판
        dial = (TextView)findViewById(R.id.dial);
        final Button button1 = (Button) findViewById(R.id.button5);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.send("1", false);
                dial.append("1");
            }
        });
        final Button button2 = (Button) findViewById(R.id.button6);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.send("2", false);
                dial.append("2");
            }
        });
        final Button button3 = (Button) findViewById(R.id.button7);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.send("3", false);
                dial.append("3");
            }
        });
        final Button button4 = (Button) findViewById(R.id.button8);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.send("4", false);
                dial.append("4");
            }
        });
        final Button button5 = (Button) findViewById(R.id.button9);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.send("5", false);
                dial.append("5");
            }
        });
        final Button button6 = (Button) findViewById(R.id.button10);
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.send("6", false);
                dial.append("6");
            }
        });
        final Button button7 = (Button) findViewById(R.id.button11);
        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.send("7", false);
                dial.append("7");
            }
        });
        final Button button8 = (Button) findViewById(R.id.button12);
        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.send("8", false);
                dial.append("8");
            }
        });
        final Button button9 = (Button) findViewById(R.id.button13);
        button9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.send("9", false);
                dial.append("9");
            }
        });
        final Button button10 = (Button) findViewById(R.id.button14);
        button10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.send("*", false);
                dial.setText("");
            }
        });
        final Button button11 = (Button) findViewById(R.id.button15);
        button11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.send("0", false);
                dial.append("0");
            }
        });
        final Button button12 = (Button) findViewById(R.id.button16);
        button12.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.send("A", false);
                dial.setText("LOCK");
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        bt.stopService(); //블루투스 중지
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) { //
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
            }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}