package com.example.happygps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class editCreate extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText msg;
    CheckBox sendBatterylevel,sendSignallevel,ringDevice;
    Button done,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_create);

        msg=findViewById(R.id.txt_msg);
        done=findViewById(R.id.btn_done);
        delete=findViewById(R.id.btn_delete);
        sendSignallevel=findViewById(R.id.chk_signalLevel);
        sendBatterylevel=findViewById(R.id.chk_batteryLevel);
        ringDevice=findViewById(R.id.chk_ring_phone);

        Intent intent=getIntent();
        int x=intent.getIntExtra("value",0);
        show_details(x);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(x);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(x);
            }
        });
    }

    public void onBackPressed() {
        Intent intent=new Intent(editCreate.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void show_details(int x){
        String message,battery,signal,strringDevice;
        sharedPreferences=getSharedPreferences("sendMessage", Context.MODE_PRIVATE);

        message=sharedPreferences.getString(x+"message","");
        battery=sharedPreferences.getString(x+"battery_level","");
        signal=sharedPreferences.getString(x+"signal_level","");
        strringDevice=sharedPreferences.getString(x+"ring_device","");

        msg.setText(message);
        if(battery.equals("true")){
            sendBatterylevel.setChecked(true);
        }
        if (signal.equals("true")){
            sendSignallevel.setChecked(true);
        }
        if(strringDevice.equals("true")){
            ringDevice.setChecked(true);
        }
    }

    private void saveData(int count){
        sharedPreferences=getSharedPreferences("sendMessage", Context.MODE_PRIVATE);

        if(!msg.getText().toString().equals("")){
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(count+"message",msg.getText().toString());

            if(sendBatterylevel.isChecked()){
                editor.putString(count+"battery_level","true");
            }
            else {
                editor.putString(count+"battery_level","false");
            }
            if(sendSignallevel.isChecked()){
                editor.putString(count+"signal_level","true");
            }
            else {
                editor.putString(count+"signal_level","false");
            }
            if(ringDevice.isChecked()){
                editor.putString(count+"ring_device","true");
            }
            else {
                editor.putString(count+"ring_device","false");
            }
            editor.commit();
            Intent intent=new Intent(editCreate.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        else if(msg.getText().toString().equals("")){
            Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show();
        }
    }

    private void delete(int number){
        String message,battery,signal,strringDevice;
        int count;
        sharedPreferences=getSharedPreferences("sendMessage", Context.MODE_PRIVATE);
        count=sharedPreferences.getInt("count",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        for(int x=1;x<=count;x++){
            if(number!=x){
                message=sharedPreferences.getString(x+"message","");
                battery=sharedPreferences.getString(x+"battery_level","");
                signal=sharedPreferences.getString(x+"signal_level","");
                strringDevice=sharedPreferences.getString(x+"ring_device","");
                editor.putString((x-1)+"message",message);
                editor.putString((x-1)+"battery_level",battery);
                editor.putString((x-1)+"signal_level",signal);
                editor.putString((x-1)+"ring_device",strringDevice);
            }
        }
        editor.putInt("count",count-=1);
        editor.commit();
        Intent intent=new Intent(editCreate.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}