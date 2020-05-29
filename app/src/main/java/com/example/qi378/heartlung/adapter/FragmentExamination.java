package com.example.qi378.heartlung.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import androidx.fragment.app.Fragment;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qi378.heartlung.BluetoothActivity;
import com.example.qi378.heartlung.ExaminationActivity;
import com.example.qi378.heartlung.MainActivity;
import com.example.qi378.heartlung.R;
import com.example.qi378.heartlung.record.AudioRecorder;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("ValidFragment")
public class FragmentExamination extends Fragment implements View.OnClickListener, OnChartGestureListener, OnChartValueSelectedListener {
    private Context context;
    private ImageView btnRecStart;
    private AudioRecorder audioRecorder;
    private Handler mHandler;
    private LineChart mLineChar;
    private List<Entry> entries;
    private int chartIndex;
    private int timeOut;
    private TextView tvTime;
    private Chronometer ch;
    private Timer mTimer;
    private TimerTask mTask;
    private TextView btnReturn;
    private TextView tvHeart,tvHung;
    private int examinationFlag=1;
    static final int SAMPLE_RATE_IN_HZ = 8000;
    static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ExaminationLayout = inflater.inflate(R.layout.examination, container, false);
        btnRecStart=ExaminationLayout.findViewById(R.id.btn_rec_start);
        tvTime=ExaminationLayout.findViewById(R.id.time);
        btnReturn=ExaminationLayout.findViewById(R.id.btn_return);
        mLineChar = (LineChart) ExaminationLayout.findViewById(R.id.mLineChar);
        tvHeart=ExaminationLayout.findViewById(R.id.heart);
        tvHung=ExaminationLayout.findViewById(R.id.hung);
        btnRecStart.setOnClickListener(this);
        btnReturn.setOnClickListener(this);
        tvHeart.setOnClickListener(this);
        tvHung.setOnClickListener(this);
        audioRecorder=AudioRecorder.getInstance(examinationHandler);
        initLineChar();
        return ExaminationLayout;
    }
    private void initLineChar(){
        //设置手势滑动事件
        mLineChar.setOnChartGestureListener(this);
        //设置数值选择监听
        mLineChar.setOnChartValueSelectedListener(this);
        //后台绘制
        mLineChar.setDrawGridBackground(false);
        //设置描述文本
        mLineChar.getDescription().setEnabled(false);
        //设置支持触控手势
        mLineChar.setTouchEnabled(true);
        //设置缩放
        mLineChar.setDragEnabled(true);
        //设置推动
        mLineChar.setScaleEnabled(true);
        entries = new ArrayList<>();
        //如果禁用,扩展可以在x轴和y轴分别完成
        mLineChar.setPinchZoom(true);

        Legend legend = mLineChar.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        legend.setTextColor(Color.WHITE);

        XAxis xAxis = mLineChar.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置

    }

    @SuppressLint("ValidFragment")
    public FragmentExamination(Context context,Handler mHandler){
        this.context=context;
        this.mHandler=mHandler;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rec_start:
                if ( ExaminationActivity.connectFlag==false){
                    Toast.makeText(context,"设备未连接", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        if (audioRecorder.getStatus() == AudioRecorder.Status.STATUS_NO_READY) {
                            //初始化录音
                            String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                            audioRecorder.createDefaultAudio(fileName);
                            audioRecorder.startRecord(null);

                        } else {
                            //停止录音
                            //audioRecorder.stopRecord();

                        }

                    } catch (IllegalStateException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.btn_return:
                sendMsgMain(0,0);
                break;
            case R.id.heart:
                tvHeart.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                tvHung.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                examinationFlag=1;
                break;
            case R.id.hung:
                tvHeart.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvHung.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                examinationFlag=2;
                break;
        }
    }
    public Handler examinationHandler = new Handler(Looper.myLooper()) {
        @SuppressLint("ResourceAsColor")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:

                    float voice= (float) msg.obj;
                    Log.e("voice",(int)voice+"");
                    entries.add(new Entry(chartIndex,  voice));
                    Log.e("entries",entries.get(chartIndex)+"");

                    LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
                    dataSet.setColor(Color.parseColor("#F70909"));//线条颜色
                    dataSet.setCircleColor(Color.parseColor("#7d7d7d"));//圆点颜色
                    dataSet.setDrawCircles(false);
                    dataSet.setDrawValues(false);
                    dataSet.setLineWidth(1f);//线条宽度
                    LineData lineData = new LineData(dataSet);
                    mLineChar.setData(lineData);
                    mLineChar.invalidate(); // refresh
                    chartIndex++;
                    break;
                case 1:
                    chartIndex=0;
                    break;
                case 2:
                    entries.clear();
                    timeOut=30;
                    if (mTimer == null && mTask == null) {
                        mTimer = new Timer();
                        mTask = new TimerTask() {
                            @Override
                            public void run() {
                                timeOut--;
                                sendMsg(3,timeOut);
                            }
                        };
                        mTimer.schedule(mTask, 0, 1000);
                    }


                    break;
                case 3:
                    tvTime.setText(timeOut+"");
                    if (timeOut==0){
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                        }
                        if (mTask != null) {
                            mTask.cancel();
                            mTask = null;
                        }
                        //停止录音
                        audioRecorder.stopRecord();
                    }
                    break;

            }
        }
    };
    /**
     * 发送Handler
     */
    private void sendMsg(int what, Object object) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = object;
        examinationHandler.sendMessage(msg);
    }
    private void sendMsgMain(int what, Object object) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = object;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
