package com.example.qi378.heartlung.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.qi378.heartlung.ExaminationActivity;
import com.example.qi378.heartlung.MainActivity;
import com.example.qi378.heartlung.R;

@SuppressLint("ValidFragment")
public class FragmentHome extends Fragment implements View.OnClickListener{
    private Context context;
    private Handler mHandler;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View homeLayout = inflater.inflate(R.layout.home_menu, container, false);
        LinearLayout btnHeart=homeLayout.findViewById(R.id.btn_heart);
        LinearLayout btnHistory=homeLayout.findViewById(R.id.btn_history);
        LinearLayout btnHung=homeLayout.findViewById(R.id.btn_help);
        btnHeart.setOnClickListener(this);
        btnHung.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
        return homeLayout;
    }
    @SuppressLint("ValidFragment")
    public FragmentHome(Context context, Handler mHandler) {
        this.context=context;
        this.mHandler=mHandler;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_heart:
                sendMsg(1,1);
                break;
            case R.id.btn_help:
                break;
            case R.id.btn_history:
                sendMsg(1,3);
                break;
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

}
