package com.example.qi378.heartlung.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.qi378.heartlung.R;

@SuppressLint("ValidFragment")
public class FragmentAddUser extends Fragment implements View.OnClickListener {
    private Context context;
    private Handler mHandler;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View userLayout = inflater.inflate(R.layout.add_user, container, false);
        Button btnOk=userLayout.findViewById(R.id.btn_ok);
        TextView btnReturn=userLayout.findViewById(R.id.btn_return);
        btnOk.setOnClickListener(this);
        btnReturn.setOnClickListener(this);
        return userLayout;
    }
    @SuppressLint("ValidFragment")
    public FragmentAddUser(Context context, Handler mHandler) {
        this.context=context;
        this.mHandler=mHandler;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:

                break;
            case R.id.btn_return:
                sendMsg(2,0);
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
