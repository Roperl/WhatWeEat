package com.exm.roper.whatweeat;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Roper on 2018/3/29.
 */

public class Fragment_two_Login extends AppCompatActivity {

    private TextView mBtnLogin;
    private View progress;
    private View mInputLayout;
    private float mWidth, mHeight;
    private LinearLayout mName, mPsw;
    private EditText User_name;
    private EditText User_pwd;

    private HashMap<String, String> session =new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTitle ("登陆");
        setContentView(R.layout.fragment_tow_login);
        User_name=findViewById(R.id.User_name);
        User_pwd=findViewById(R.id.User_pwd);
        ImageView img_back=findViewById(R.id.login_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Fragment_two_Login.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });
        initView();
        TextView textView_login=findViewById(R.id.main_btn_login);
        textView_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkUser()){
                    Toast.makeText(v.getContext(), "用户登录成功！", Toast.LENGTH_LONG).show();
                    Context context = v.getContext();
                    Intent intent1 = new Intent(Fragment_two_Login.this,
                           MainActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);//禁止返回
                    //传递session参数,在用户登录成功后为session初始化赋值,即传递HashMap的值
                    Bundle map = new Bundle();
                    map.putSerializable("sessionid", session);
                    System.out.println("序列化后的值"+map);
                    intent1.putExtra("session", map);
                    context.startActivity(intent1); // 跳转到成功页面
                }
                else
                    Toast.makeText(v.getContext(), "用户验证失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean checkUser() {
        String username=User_name.getText().toString();
        String pass=User_pwd.getText().toString();

        DefaultHttpClient mHttpClient = new DefaultHttpClient();
        HttpPost mPost = new HttpPost("http://59.70.156.12/WhatWeEat/UserLogin/login.php");
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair> ();
        pairs.add(new BasicNameValuePair("username", username));
        pairs.add(new BasicNameValuePair("password", pass));

        try {
            mPost.setEntity(new UrlEncodedFormEntity (pairs, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            HttpResponse response = mHttpClient.execute(mPost);
            int res = response.getStatusLine().getStatusCode();

            if (res == 200) {
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    String info = EntityUtils.toString(entity);
                    System.out.println("info-----------"+info);
                    //以下主要是对服务器端返回的数据进行解析

                    JSONObject jsonObject=null;
                    //flag为登录成功与否的标记,从服务器端返回的数据
                    String flag="";
                    String name="";
                    String userid="";
                    String sessionid="";
                    try {
                        jsonObject = new JSONObject (info);
                        flag = jsonObject.getString("flag");
                        name = jsonObject.getString("name");
                        userid = jsonObject.getString("userid");
                        sessionid = jsonObject.getString("sessionid");

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        System.out.println("json解析异常"+e);
                    }
                    //根据服务器端返回的标记,判断服务端端验证是否成功
                    System.out.println("服务器端判断返回flag"+String.valueOf (flag));
                    if(flag.equals("success")){
                        //为session传递相应的值,用于在session过程中记录相关用户信息
                        System.out.println("进入到success块");
                        session.put("s_userid", userid);
                        session.put("s_username", name);
                        session.put("s_sessionid", sessionid);
                        System.out.println ("s_session"+session);
                        return true;
                    }
                    else{
                        System.out.println("进入到false块");
                        return false;
                    }
                }
                else{

                    return false;
                }

            }

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }


    private void initView() {
        mBtnLogin = findViewById(R.id.main_btn_login);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        mName = findViewById(R.id.input_layout_name);
        mPsw =  findViewById(R.id.input_layout_psw);

       // mBtnLogin.setOnClickListener(this);
    }

    private void inputAnimator(final View view, float w, float h) {

        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();

    }
}
