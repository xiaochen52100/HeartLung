package com.example.qi378.heartlung;

import android.Manifest;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.clj.fastble.BleManager;
import com.clj.fastble.data.ScanResult;
import com.clj.fastble.scan.ListScanCallback;
import com.example.qi378.heartlung.adapter.FragmentExamination;
import com.example.qi378.heartlung.adapter.FragmentHistory;
import com.example.qi378.heartlung.adapter.FragmentHome;
import com.example.qi378.heartlung.adapter.FragmentMine;
import com.example.qi378.heartlung.bean.SystemConfig;
import com.example.qi378.heartlung.record.AudioRecorder;
import com.example.qi378.heartlung.util.ObjectSaveUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.net.sip.SipErrorCode.TIME_OUT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,EasyPermissions.PermissionCallbacks{
    private ImageView btnRecStart;
    private ImageView imgVoice;
    private AudioRecorder audioRecorder;
    private FragmentExamination fragmentExamination;
    private FragmentHistory fragmentHistory;
    private FragmentHome fragmentHome;
    private FragmentMine fragmentMine;
    private SystemConfig systemConfig;
    private ObjectSaveUtils objectSaveUtils;
    private long exitTime=0;//双击退出时间
    public static final int WRITE_EXTERNAL_STORAGE = 100;
    private boolean isFirst = false;
    String[] params = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO}; //权限参数
    public static final int requestCode = 1;//自定义申请码
    public static final String KEY = "key";
    private void addListener() {

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (fragmentHome==null)
                        fragmentHome=new FragmentHome(MainActivity.this,mHandler);
                    changeFragment(fragmentHome);
                    return true;
                case R.id.navigation_notifications:
                    if (fragmentMine==null)
                        fragmentMine=new FragmentMine(MainActivity.this,mHandler);
                    changeFragment(fragmentMine);
                    return true;
            }
            return false;
        }
    };
    /**
     * 按键监听
     */
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode== KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode,event);
    }
    /**
     * 双击返回键推出
     */
    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(),
                    "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();}
        else{
            MyApplication.getInstance().exit();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.getInstance().addActivity(this);
        if (fragmentHome==null)
            fragmentHome=new FragmentHome(MainActivity.this,mHandler);
        changeFragment(fragmentHome);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        objectSaveUtils=new ObjectSaveUtils();
        systemConfig=new SystemConfig();
        systemConfig=(SystemConfig)objectSaveUtils.getObject(MainActivity.this,"sys");
        Log.e("tag",systemConfig+"");
        isFirst = true;
        checkPerm();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (isFirst) {
            //因为要通过一个Fragment来弹出弹出框，所以activity这里的onResume执行了两次，这里进行判断
            isFirst = false;
            if (!EasyPermissions.hasPermissions(this, params)) {
                EasyPermissions.requestPermissions(this, "需要读写本地权限", WRITE_EXTERNAL_STORAGE, params);
            }
        }
    }
    /**
     * 发送Handler
     */
    public void sendMsg(int what, Object object) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = object;
        mHandler.sendMessage(msg);
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
                    int value= (int) msg.obj;
                    switch (value){
                        case 1:
                            Intent intent1 = new Intent(MainActivity.this, ExaminationActivity.class);
                            intent1.putExtra(KEY, 1);
                            startActivityForResult(intent1,1);
                            break;
                        case 2:
                            Intent intent2 = new Intent(MainActivity.this, ExaminationActivity.class);
                            intent2.putExtra(KEY, 2);
                            startActivityForResult(intent2,1);
                            break;
                        case 3:
                            Intent intent3 = new Intent(MainActivity.this, ExaminationActivity.class);
                            intent3.putExtra(KEY, 3);
                            startActivityForResult(intent3,1);
                            break;
                        case 4:
                            Intent intent4 = new Intent(MainActivity.this, ExaminationActivity.class);
                            intent4.putExtra(KEY, 4);
                            startActivityForResult(intent4,1);
                            break;
                    }

                    break;
                case 2:
                    if (fragmentHistory==null)
                        fragmentHistory=new FragmentHistory(MainActivity.this,mHandler);
                    changeFragment(fragmentHistory);
                    break;
                case 3:
                    if (fragmentHome==null)
                        fragmentHome=new FragmentHome(MainActivity.this,mHandler);
                    changeFragment(fragmentHome);
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

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
     * 检查权限
     */
    @AfterPermissionGranted(WRITE_EXTERNAL_STORAGE)
    private void checkPerm() {

        if (EasyPermissions.hasPermissions(this, params)) {
            //已经获取到权限
            Toast.makeText(MainActivity.this, "获取到权限，正常进入", Toast.LENGTH_LONG).show();
        } else {
            EasyPermissions.requestPermissions(this, "需要获取一些权限", WRITE_EXTERNAL_STORAGE, params);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            //这个方法有个前提是，用户点击了“不再询问”后，才判断权限没有被获取到
            new AppSettingsDialog.Builder(this)
                    .setRationale("没有该权限，此应用程序可能无法正常工作。打开应用设置界面以修改应用权限")
                    .setTitle("必需权限")
                    .build()
                    .show();
        } else if (!EasyPermissions.hasPermissions(this, params)) {
            //这里响应的是除了AppSettingsDialog这个弹出框，剩下的两个弹出框被拒绝或者取消的效果
            finish();
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (!EasyPermissions.hasPermissions(this, params)) {
                //这里响应的是AppSettingsDialog点击取消按钮的效果
                finish();
            }
        }
    }
}
