package com.example.qi378.heartlung;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qi378.heartlung.adapter.FragmentLogin;
import com.example.qi378.heartlung.adapter.FragmentSignin;

public class UserActivity extends AppCompatActivity {
    private boolean loginFlag=false;
    private FragmentLogin fragmentLogin;
    private FragmentSignin fragmentSignin;
    public String userId="";
    public String passwd="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        MyApplication.getInstance().addActivity(this);
        fragmentLogin=new FragmentLogin(UserActivity.this, mHandler);
        fragmentSignin=new FragmentSignin(UserActivity.this, mHandler);
        if(loginFlag){
            Intent intent = new Intent(UserActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            changeFragment(fragmentLogin);
        }


    }
    public  void changeFragment(Fragment fragment){
        //实例化碎片管理器对象
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        //选择fragment替换的部分
        ft.replace(R.id.content,fragment);
        ft.commit();
    }
    /**
     * Handler
     */
    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    changeFragment(fragmentLogin);
                    break;
                case 1:
                    changeFragment(fragmentSignin);
                    break;
            }
        }
    };
}
