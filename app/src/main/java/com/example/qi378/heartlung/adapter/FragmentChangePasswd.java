package com.example.qi378.heartlung.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qi378.heartlung.R;

@SuppressLint("ValidFragment")
public class FragmentChangePasswd extends Fragment implements View.OnClickListener {
    private Context context;
    private Handler mHandler;
    private EditText etLastPasswd,etNewPasswd,etAgainPasswd;
    private Button btnOk;
    private TextView btnReturn;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View changePasswdLayout = inflater.inflate(R.layout.change_passwd, container, false);
        etLastPasswd =changePasswdLayout.findViewById(R.id.et_last_passed);
        etNewPasswd =changePasswdLayout.findViewById(R.id.et_new_passed);
        etAgainPasswd=changePasswdLayout.findViewById(R.id.et_again_passed);
        btnReturn=changePasswdLayout.findViewById(R.id.btn_return);
        btnOk=changePasswdLayout.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
        btnReturn.setOnClickListener(this);
        return changePasswdLayout;
    }
    @SuppressLint("ValidFragment")
    public FragmentChangePasswd(Context context, Handler mHandler) {
        this.context=context;
        this.mHandler=mHandler;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_return:
                sendMsg(0,0);
                break;
            case R.id.btn_ok:
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
