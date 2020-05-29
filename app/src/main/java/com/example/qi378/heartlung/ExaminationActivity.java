package com.example.qi378.heartlung;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qi378.heartlung.adapter.FragmentAddUser;
import com.example.qi378.heartlung.adapter.FragmentChangePasswd;
import com.example.qi378.heartlung.adapter.FragmentExamination;
import com.example.qi378.heartlung.adapter.FragmentHistory;
import com.example.qi378.heartlung.adapter.FragmentHome;
import com.example.qi378.heartlung.adapter.FragmentUserManagement;
import com.example.qi378.heartlung.util.BluetoothSco;
import com.example.qi378.heartlung.util.BluetoothUtil;

public class ExaminationActivity extends AppCompatActivity {
    private FragmentExamination fragmentExamination;
    private FragmentChangePasswd fragmentChangePasswd;
    private FragmentHistory fragmentHistory;
    private FragmentUserManagement fragmentUserManagement;
    private FragmentAddUser fragmentAddUser;
    public static boolean connectFlag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination);
        MyApplication.getInstance().addActivity(this);
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int value = bundle.getInt(MainActivity.KEY);

        switch (value){
            case 1:
                if (fragmentExamination==null)
                fragmentExamination=new FragmentExamination(ExaminationActivity.this,mHandler);
                changeFragment(fragmentExamination);
                if (BluetoothUtil.getBlueProfile(ExaminationActivity.this)!=-1){//判断连接状态
                    BluetoothSco.getInstance(ExaminationActivity.this).openSco(new BluetoothSco.IBluetoothConnectListener() {
                        @Override
                        public void onError(String error) {
                            //Log.e("blue",error);
                            connectFlag=false;
                        }

                        @Override
                        public void onSuccess() {
                            //Log.e("blue","ok");
                            connectFlag=true;
                        }
                    });
                }
                Log.e("blue",connectFlag+"ok");
                break;
            case 2:
                if (fragmentChangePasswd==null)
                    fragmentChangePasswd=new FragmentChangePasswd(ExaminationActivity.this,mHandler);
                changeFragment(fragmentChangePasswd);
                break;
            case 3:
                if (fragmentHistory==null)
                    fragmentHistory=new FragmentHistory(ExaminationActivity.this,mHandler);
                changeFragment(fragmentHistory);
                break;
            case 4:
                if (fragmentUserManagement==null)
                    fragmentUserManagement=new FragmentUserManagement(ExaminationActivity.this,mHandler);
                changeFragment(fragmentUserManagement);
                break;

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
    public Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    finish();
                    break;
                case 1:
                    if (fragmentAddUser==null)
                        fragmentAddUser=new FragmentAddUser(ExaminationActivity.this,mHandler);
                    changeFragment(fragmentAddUser);
                    break;
                case 2:
                    if (fragmentUserManagement==null)
                        fragmentUserManagement=new FragmentUserManagement(ExaminationActivity.this,mHandler);
                    changeFragment(fragmentUserManagement);
                    break;
            }
        }
    };
}
