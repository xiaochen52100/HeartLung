package com.example.qi378.heartlung.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qi378.heartlung.R;
import com.example.qi378.heartlung.bean.HistoryItem;
import com.example.qi378.heartlung.bean.UserName;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<UserName> {
    private int resourceId;
    private List<UserName> userNameList;
    public UserListAdapter(Context context, int resource, List<UserName> objects) {
        super(context, resource,objects);
        resourceId = resource;
        userNameList=objects;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        UserName userName = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView name = (TextView) view.findViewById( R.id.username);
        final TextView check = (TextView) view.findViewById( R.id.check);
        LinearLayout user=(LinearLayout) view.findViewById( R.id.user);
        name.setText(userName.username);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0;i<userNameList.size();i++){
                    userNameList.get(i).check=false;
                }
                userNameList.get(position).check=true;
                setupList();
            }
        });

        Log.e("check123","  "+userNameList.get(position).check+"+"+position);
        if (userName.check){
            check.setText("✔");
        }else {
            check.setText(" ");
        }
        return view;
    }
    private void setupList(){
        this.notifyDataSetChanged();
    }
    private void sendMsg(int what, Object object) {
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

                    break;
            }
        }
    };

}
