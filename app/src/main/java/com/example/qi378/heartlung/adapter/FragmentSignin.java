package com.example.qi378.heartlung.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qi378.heartlung.R;

@SuppressLint("ValidFragment")
public class FragmentSignin extends Fragment implements View.OnClickListener{
    private Context context;
    private Handler mHandler;
    private TextView btnReturn;
    private EditText etPhone,etPasswd,etPasswdAgain;
    private ImageButton btnSignin;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View loginLayout = inflater.inflate(R.layout.signin, container, false);
        btnSignin=loginLayout.findViewById(R.id.btn_go);
        btnReturn=loginLayout.findViewById(R.id.btn_return);
        etPhone=loginLayout.findViewById(R.id.et_phone);
        etPasswd=loginLayout.findViewById(R.id.et_passwd);
        etPasswdAgain=loginLayout.findViewById(R.id.et_passwd_again);
        btnSignin.setOnClickListener(this);
        etPasswdAgain.setOnClickListener(this);
        etPhone.setOnClickListener(this);
        etPasswd.setOnClickListener(this);
        btnReturn.setOnClickListener(this);

        return loginLayout;
    }
    @SuppressLint("ValidFragment")
    public FragmentSignin(Context context, Handler mHandler) {
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
            case R.id.btn_go:
                sendMsg(0,"");
                break;
            case R.id.btn_return:
                sendMsg(0,"");
                break;
            case R.id.et_phone:

                break;
            case R.id.et_user_passwd:

                break;
            case R.id.et_passwd_again:

                break;
        }
    }
    /**
     * 得到自定义的progressDialog
     * @param context
     * @param msg
     * @return
     */
    public static Dialog createLoadingDialog(Context context, String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;

    }
}
