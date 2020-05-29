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
import android.widget.ListView;
import android.widget.TextView;

import com.example.qi378.heartlung.BluetoothActivity;
import com.example.qi378.heartlung.R;
import com.example.qi378.heartlung.bean.HistoryItem;
import com.example.qi378.heartlung.bean.UserName;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class FragmentUserManagement extends Fragment implements View.OnClickListener{
    private Context context;
    private Handler mHandler;
    private UserListAdapter userListAdapter;
    private List<UserName> userList=new ArrayList<>();
    private ListView userListView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View userLayout = inflater.inflate(R.layout.user_management, container, false);
        Button btnOk=userLayout.findViewById(R.id.btn_ok);
        TextView btnReturn=userLayout.findViewById(R.id.btn_return);
        userListView=userLayout.findViewById(R.id.list_user);
        btnOk.setOnClickListener(this);
        btnReturn.setOnClickListener(this);
        UserName userName1=new UserName("小巫",true);
        UserName userName2=new UserName("小戚",false);
        userList.add(userName1);
        userList.add(userName2);
        userListAdapter=new UserListAdapter(context,R.layout.user_item,userList);
        userListView.setAdapter(userListAdapter);
        return userLayout;
    }
    @SuppressLint("ValidFragment")
    public FragmentUserManagement(Context context, Handler mHandler) {
        this.context=context;
        this.mHandler=mHandler;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                sendMsg(1,0);
                break;
            case R.id.btn_return:
                sendMsg(0,0);
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
