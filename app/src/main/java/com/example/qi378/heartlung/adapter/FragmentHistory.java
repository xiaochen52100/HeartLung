package com.example.qi378.heartlung.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qi378.heartlung.R;
import com.example.qi378.heartlung.bean.HistoryItem;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class FragmentHistory extends Fragment implements View.OnClickListener{
    private Context context;
    private Handler mHandler;
    private TextView btnReturn;
    private TextView tvHeart,tvHung;
    private ListView historyListView;
    private int historyFlag=1;
    private HistoryListAdapter historyListAdapter;
    private List<HistoryItem> historyList=new ArrayList<>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View historyLayout = inflater.inflate(R.layout.history, container, false);
        btnReturn=historyLayout.findViewById(R.id.btn_return);
        tvHeart=historyLayout.findViewById(R.id.heart);
        tvHung=historyLayout.findViewById(R.id.hung);
        historyListView=historyLayout.findViewById(R.id.list_history);
        btnReturn.setOnClickListener(this);
        tvHeart.setOnClickListener(this);
        tvHung.setOnClickListener(this);
        HistoryItem historyItem1=new HistoryItem("20191012","正常");
        HistoryItem historyItem2=new HistoryItem("20191013","正常");
        historyList.add(historyItem1);
        historyList.add(historyItem2);
        historyListAdapter=new HistoryListAdapter(context,R.layout.history_item,historyList);
        historyListView.setAdapter(historyListAdapter);
        return historyLayout;
    }
    @SuppressLint("ValidFragment")
    public FragmentHistory(Context context, Handler mHandler) {
        this.context=context;
        this.mHandler=mHandler;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_return:
                sendMsg(0,0);
                break;
            case R.id.heart:
                tvHeart.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                tvHung.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                historyFlag=1;

                break;
            case R.id.hung:
                tvHeart.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvHung.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                historyFlag=2;
                historyList.get(0).value="异常";
                historyListAdapter.notifyDataSetChanged();
                break;
        }
    }
}
