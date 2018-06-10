package com.exm.roper.whatweeat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Roper on 2018/3/29.
 */

public class Fragment_two extends Fragment {
    private TextView tv_username;
    private LinearLayout User_regist_login;
    private List<Fragment_two_list_entity> fruitList = new ArrayList<> ( );
    private Bundle session;
    User_bean user_bean = new User_bean ( );

    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate (R.layout.fragment_tow, container, false);
        initFruits ( );
        tv_username = rootView.findViewById (R.id.tv_user_name);
        tv_username.setText (user_bean.getUsername ( ));
        User_regist_login = rootView.findViewById (R.id.action_button);
        TextView tv_login = rootView.findViewById (R.id.tv_login);
        TextView tv_register=rootView.findViewById (R.id.tv_register);

        final Fragment_two_list_adapter adapter = new Fragment_two_list_adapter (getActivity ( ),
                R.layout.fragement_two_listview_item,
                fruitList);
        ListView listView = rootView.findViewById (R.id.listview_msg);
        listView.setAdapter (adapter);
        //Item点击事件
        listView.setOnItemClickListener (new AdapterView.OnItemClickListener ( ) {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String Postion = String.valueOf (i);
                switch (Postion) {
                    case "0":
                        if (getSession ( ) != null) {
                            Intent InfoIntent = new Intent (getActivity ( ), Fragment_two_userinfo.class);
                            InfoIntent.putExtra ("Session", getSession ( ));
                            startActivity (InfoIntent);
                        } else {
                            Toast.makeText (getContext ( ), "请登录后再使用该功能", Toast.LENGTH_SHORT).show ( );
                        }
                        break;
                    case "1":
                        if (getSession ( ) != null) {
                            Intent PerferIntent = new Intent (getActivity ( ), Fragment_tow_UserPreferSeting.class);
                            PerferIntent.putExtra ("Session", getSession ( ));
                            startActivity (PerferIntent);
                        } else {
                            Toast.makeText (getContext ( ), "请登录后再使用该功能", Toast.LENGTH_SHORT).show ( );
                        }
                        break;
                    case "2":
                        Toast.makeText (getActivity ( ), "功能暂未开放" + Postion, Toast.LENGTH_SHORT).show ( );
                        break;
                }
            }
        });
        tv_login.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity ( ), Fragment_two_Login.class);
                startActivity (intent);
            }
        });
        tv_register.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                Toast.makeText (getActivity (),"Beta Version，注册接口尚未开放,请使用分配账号进行登陆",Toast.LENGTH_SHORT);
            }
        });
        if (user_bean.getUsername ( ) != null) {
            User_regist_login.setVisibility (View.GONE);
            User_regist_login.setTop (10);
        }
        return rootView;
    }

    private void initFruits() {
        Fragment_two_list_entity apple = new Fragment_two_list_entity ("个人信息", R.drawable.ic_userinfo);
        fruitList.add (apple);
        Fragment_two_list_entity banana = new Fragment_two_list_entity ("喜好设置", R.drawable.ic_userperfer);
        fruitList.add (banana);
        Fragment_two_list_entity orange = new Fragment_two_list_entity ("系统设置", R.drawable.orange_pic);
        fruitList.add (orange);
    }

    public void onAttach(Activity activity) {
        super.onAttach (activity);
        session = ((MainActivity) activity).getTitles ( );
        setSession (session);
        System.out.println ("个人中心接收到的session:" + session);
        if (session != null) {
            String data = String.valueOf (session);
            System.out.println (data);
            String pattern = "Bundle..sessionid=.(.+), (.+), (.+)...";
            Pattern rPattern = Pattern.compile (pattern);
            Matcher matcher = rPattern.matcher (data);
            if (matcher.find ( )) {
                System.out.println (matcher.group (1));
                user_bean.setUsername (matcher.group (1));
                System.out.println (matcher.group (2));
                user_bean.setUserid (matcher.group (2));
                System.out.println (matcher.group (3));
            }
        }
    }

    public Bundle getSession() {
        return session;
    }

    public void setSession(Bundle session) {
        this.session = session;
    }

}
