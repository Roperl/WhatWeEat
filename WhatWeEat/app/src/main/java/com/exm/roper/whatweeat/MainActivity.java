package com.exm.roper.whatweeat;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener{
    private MainFragment fg1;
    private Fragment_two fg2;
    private RelativeLayout course_layout;
    private RelativeLayout found_layout;
    private ImageView course_image;
    private ImageView found_image;
    private TextView course_text;
    private TextView found_text;
    private int whirt = 0xFFFFFFFF;
    private int gray = 0xFF7597B3;
    private int blue =0xFF0AB2FB;
    FragmentManager fManager;
    Bundle session=new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        fManager = getSupportFragmentManager();
        initViews();
        setChioceItem(0);//默认加载第一个页面
    }

    public Bundle getTitles(){
        session=getIntent().getBundleExtra ("session");
        if (session!=null)
        session.getBundle ("sessionid");
        System.out.println("MainActivity处理后的seesion:"+session);
        return session;
    }

    public void initViews()
    {
        course_image =findViewById(R.id.course_image);
        found_image = findViewById(R.id.found_image);
        course_text = findViewById(R.id.course_text);
        found_text = findViewById(R.id.found_text);
        course_layout =  findViewById(R.id.course_layout);
        found_layout =  findViewById(R.id.found_layout);
        course_layout.setOnClickListener(this);
        found_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.course_layout:
                setChioceItem(0);
                break;
            case R.id.found_layout:
                setChioceItem(1);
                break;
           default:
              break;
        }
    }

    public void setChioceItem(int index)
    {
        FragmentTransaction transaction = fManager.beginTransaction();
        clearChioce();
        hideFragments(transaction);
        switch (index) {
            case 0:
                course_image.setImageResource(R.drawable.ic_home2);
                course_text.setTextColor(blue);
                course_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click_9);
                if (fg1 == null) {
                    fg1 = new MainFragment();
                    transaction.add(R.id.container, fg1);
                } else {
                    transaction.show(fg1);
                }
                break;

            case 1:
                found_image.setImageResource(R.drawable.ic_seting2);
                found_text.setTextColor(blue);
                found_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click_9);
                if (fg2 == null) {
                    fg2 = new Fragment_two();
                    transaction.add(R.id.container, fg2);
                } else {
                    transaction.show(fg2);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (fg1 != null) {
            transaction.hide(fg1);
        }
        if (fg2 != null) {
            transaction.hide(fg2);
        }
    }


    public void clearChioce()
    {
        course_image.setImageResource(R.drawable.ic_home);
        course_layout.setBackgroundColor(whirt);
        course_text.setTextColor(gray);
        found_image.setImageResource(R.drawable.ic_seting);
        found_layout.setBackgroundColor(whirt);
        found_text.setTextColor(gray);
    }

}
