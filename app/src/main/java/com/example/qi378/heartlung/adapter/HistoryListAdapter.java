package com.example.qi378.heartlung.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qi378.heartlung.R;
import com.example.qi378.heartlung.bean.HistoryItem;

import java.util.List;

public class HistoryListAdapter extends ArrayAdapter<HistoryItem> {
    private int resourceId;
    private List<HistoryItem> historyItemList;
    public HistoryListAdapter(Context context, int resource, List<HistoryItem> objects) {
        super(context, resource,objects);
        resourceId = resource;
        historyItemList=objects;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HistoryItem historyItem = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView time = (TextView) view.findViewById( R.id.time);
        TextView value = (TextView) view.findViewById( R.id.value);
        time.setText(historyItem.time);
        value.setText(historyItem.value);

        return view;
    }

}
