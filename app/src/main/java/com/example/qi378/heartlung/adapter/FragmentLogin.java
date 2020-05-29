package com.example.qi378.heartlung.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.qi378.heartlung.MainActivity;
import com.example.qi378.heartlung.R;
import com.example.qi378.heartlung.UserActivity;
import com.example.qi378.heartlung.bean.SystemConfig;
import com.example.qi378.heartlung.util.ObjectSaveUtils;

@SuppressLint("ValidFragment")
public class FragmentLogin extends Fragment implements View.OnClickListener{
    private Context context;
    private Handler mHandler;
    private TextView btnReturn;
    private EditText etUserid,etPasswd;
    private Button btnLogin;
    private TextView tvForget,tvSignin;
    private SystemConfig systemConfig;
    private ObjectSaveUtils objectSaveUtils;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View loginLayout = inflater.inflate(R.layout.login, container, false);
        btnLogin=loginLayout.findViewById(R.id.btn_go);
        tvForget=loginLayout.findViewById(R.id.tv_forget_passwd);
        tvSignin=loginLayout.findViewById(R.id.tv_signin);
        etUserid=loginLayout.findViewById(R.id.et_user_id);
        etPasswd=loginLayout.findViewById(R.id.et_user_passwd);
        btnLogin.setOnClickListener(this);
        tvForget.setOnClickListener(this);
        tvSignin.setOnClickListener(this);
        etUserid.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        etPasswd.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        objectSaveUtils=new ObjectSaveUtils();
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        sp.edit().putString("id", "15133977695").putString("passwd", "123456").commit();
        SharedPreferences preferences=context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String id=preferences.getString("id", "");
        String passwd=preferences.getString("passwd", "");
        Log.e("tag",id+","+passwd);
        if (id!=""){
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }
        return loginLayout;
    }
    @SuppressLint("ValidFragment")
    public FragmentLogin(Context context, Handler mHandler) {
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
                SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
                sp.edit().putString("id", etUserid.getText().toString()).putString("passwd", etPasswd.getText().toString()).commit();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
//                createLoadingDialog(context,"登陆中").show();
                break;
            case R.id.tv_forget_passwd:
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("提示");
                dialog.setMessage("请联系管理员更改密码");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog.show();
                break;
            case R.id.tv_signin:
                sendMsg(1,"");
                break;
            case R.id.et_user_id:

                break;
            case R.id.et_user_passwd:

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
