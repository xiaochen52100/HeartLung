package com.example.qi378.heartlung.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qi378.heartlung.BluetoothActivity;
import com.example.qi378.heartlung.MainActivity;
import com.example.qi378.heartlung.R;

@SuppressLint("ValidFragment")
public class FragmentMine extends Fragment implements View.OnClickListener{
    private View mineLayout;
    private Context context;
    private Handler mHandler;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mineLayout = inflater.inflate(R.layout.mine, container, false);

        LinearLayout tvChangePasswd=mineLayout.findViewById(R.id.change_passwd);
        LinearLayout tvChangeUser=mineLayout.findViewById(R.id.change_user);
        LinearLayout tvLogOut=mineLayout.findViewById(R.id.log_out);
        LinearLayout tvBtConnect=mineLayout.findViewById(R.id.bt_connect);
        tvChangePasswd.setOnClickListener(this);
        tvChangeUser.setOnClickListener(this);
        tvLogOut.setOnClickListener(this);
        tvBtConnect.setOnClickListener(this);
        return mineLayout;
    }
    @SuppressLint("ValidFragment")
    public FragmentMine(Context context,Handler mHandler){
        this.context=context;
        this.mHandler=mHandler;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_connect:
//                Intent intent1 = new Intent(context, BluetoothActivity.class);
//                startActivity(intent1);
                startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));//直接跳转到设置界面
                break;
            case R.id.change_passwd:
                sendMsgMain(1,2);
                break;
            case R.id.change_user:
                sendMsgMain(1,4);
                break;
            case R.id.log_out:
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("提示");
                dialog.setMessage("确定要退出吗？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendMsgMain(0,0);
                    }
                });

                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog.show();

                break;
        }
    }
    private void sendMsgMain(int what, Object object) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = object;
        mHandler.sendMessage(msg);
    }

}
