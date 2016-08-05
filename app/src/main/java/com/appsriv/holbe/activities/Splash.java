package com.appsriv.holbe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.appsriv.holbe.R;
import com.crashlytics.android.Crashlytics;

import java.util.regex.Pattern;

import io.fabric.sdk.android.Fabric;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.splash);

    }

    public void login(View v)
    {
        Intent i=new Intent(Splash.this,Login.class);
        startActivity(i);
    }

    public void tour(View v)
    {
        Intent i=new Intent(Splash.this,Tour.class);
        startActivity(i);
    }

    public void signup(View v)
    {
        Intent i=new Intent(Splash.this,SignUpActivity.class);
        startActivity(i);
    }

    public static boolean isValidEmaillId(String email)
    {
        return Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+").matcher(email).matches();
    }
}
