package com.exm.roper.whatweeat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Roper on 2018/4/10.
 */

public class Fragment_two_userinfo extends AppCompatActivity {
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>> ( );
    User_bean user_bean = new User_bean ( );
    private String Username, Sessionid, Userid;


      @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_userinfo);
        setTitle ("个人信息");
        Data ( );
        Username = user_bean.getUsername ( );
        Userid = user_bean.getUserid ( );
        Sessionid = user_bean.getSessionid ( );
        System.out.println ("userinfo中的数据:" + Username+","+Userid+","+Sessionid);
        TextView tv_username=findViewById (R.id.Info_UserName);
        TextView tv_userid=findViewById (R.id.Info_UserID);
        tv_username.setText (Username);
        tv_userid.setText (Userid);
    }

    public void Data() {
        Bundle session = getIntent ( ).getBundleExtra ("Session");
        if (session != null)
            session.getBundle ("sessionid");
        System.out.println ("UserInfo中的数据：" + session);
        if (session != null) {
            String data = String.valueOf (session);
            String pattern = "Bundle..sessionid=.(.+), (.+), (.+)...";
            Pattern rPattern = Pattern.compile (pattern);
            Matcher matcher = rPattern.matcher (data);
            if (matcher.find ( )) {
                System.out.println (matcher.group (1));
                System.out.println (matcher.group (2));
                System.out.println (matcher.group (3));
                user_bean.setUsername (matcher.group (1));
                user_bean.setSessionid (matcher.group (2));
                user_bean.setUserid (matcher.group (3));
            }
        }
    }
}
