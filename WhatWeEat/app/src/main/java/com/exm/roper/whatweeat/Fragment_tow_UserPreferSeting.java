package com.exm.roper.whatweeat;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Roper on 2018/4/5.
 */

public class Fragment_tow_UserPreferSeting extends AppCompatActivity {
    View getlistview;
    User_bean user_bean = new User_bean ( );
    String[] mlistText = {"全选", "洋芋", "鸡肉", "牛肉", "猪肉", "玉米", "青菜", "萝卜"};
    private String[] PostList = new String[mlistText.length];
    private ArrayList postList = new ArrayList ( );
    private List<Fragement_two_UserPreferSeting_bean> data = null;
    ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>> ( );
    AlertDialog.Builder builder;
    SimpleAdapter adapter;
    Boolean[] bl = {false, false, false, false, false, false, false, false};
    private List<Fragement_two_UserPreferSeting_bean> beans = new ArrayList<> ( );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_userperfer);
        setTitle ("喜好设置");
        /**
        之后将数据放到表上，和对准bl的位置加入false
         **/
        try {
            mlistText=initData ();
            for (String str1 : mlistText) {
                System.out.println (str1);
            }
        } catch (IOException e) {
            e.printStackTrace ( );
        }

        Button button = (Button) findViewById (R.id.btn);
        for (int i = 0; i < mlistText.length; i++) {
            Map<String, Object> item = new HashMap<String, Object> ( );
            item.put ("text", mlistText[i]);
            mData.add (item);
        }
        //listview结束
        button.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                CreateDialog ( );// 点击创建Dialog
            }
        });
    }

    class ItemOnClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
            CheckBox cBox = view.findViewById (R.id.X_checkbox);
            if (cBox.isChecked ( )) {
                cBox.setChecked (false);
            } else {
                Log.i ("TAG", "取消该选项");
                cBox.setChecked (true);
            }

            if (position == 0 && (cBox.isChecked ( ))) {
                //如果是选中 全选  就把所有的都选上 然后更新
                for (int i = 0; i < bl.length; i++) {
                    bl[i] = true;
                }
                adapter.notifyDataSetChanged ( );//更新
            } else if (position == 0 && (!cBox.isChecked ( ))) {
                //如果是取消全选 就把所有的都取消 然后更新
                for (int i = 0; i < bl.length; i++) {
                    bl[i] = false;
                }
                adapter.notifyDataSetChanged ( );
            }
            if (position != 0 && (!cBox.isChecked ( ))) {
                // 如果把其它的选项取消   把全选取消
                bl[0] = false;
                bl[position] = false;
                adapter.notifyDataSetChanged ( );
            } else if (position != 0 && (cBox.isChecked ( ))) {
                //如果选择其它的选项，看是否全部选择
                //先把该选项选中 设置为true
                bl[position] = true;
                int a = 0;
                for (int i = 1; i < bl.length; i++) {
                    if (bl[i] == false) {
                        //如果有一个没选中  就不是全选 直接跳出循环
                        break;
                    } else {
                        //计算有多少个选中的
                        a++;
                        if (a == bl.length - 1) {
                            //如果选项都选中，就把全选 选中，然后更新
                            bl[0] = true;
                            adapter.notifyDataSetChanged ( );
                        }
                    }
                }
            }
        }

    }

    public void CreateDialog() {

        // 动态加载一个listview的布局文件进来
        LayoutInflater inflater = LayoutInflater.from (Fragment_tow_UserPreferSeting.this);
        getlistview = inflater.inflate (R.layout.activity_userperfer_listview, null);

        // 给ListView绑定内容
        ListView listview = (ListView) getlistview.findViewById (R.id.X_listview);
        adapter = new SetSimpleAdapter (Fragment_tow_UserPreferSeting.this, mData, R.layout.activity_userperfer_checkbox, new String[]{"text"},
                new int[]{R.id.X_item_text});
        // 给listview加入适配器
        listview.setAdapter (adapter);
        listview.setItemsCanFocus (false);
        listview.setChoiceMode (ListView.CHOICE_MODE_MULTIPLE);
        listview.setOnItemClickListener (new ItemOnClick ( ));

        builder = new AlertDialog.Builder (this);
        builder.setTitle ("不喜欢的菜");
        builder.setIcon (R.drawable.ic_launcher);
        //设置加载的listview
        builder.setView (getlistview);
        builder.setPositiveButton ("确定", new DialogOnClick ( ));
        builder.setNegativeButton ("取消", new DialogOnClick ( ));
        builder.create ( ).show ( );
    }

    class DialogOnClick implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    //确定按钮的事件
                    ListView listViewShow = (ListView) findViewById (R.id.PerferList);
                    PostList = new String[mlistText.length];
                    postList = new ArrayList ( );
                    for (int i = 0; i < bl.length; i++) {
                        if (bl[i] == true) {
                            postList.add (mlistText[i]);
                        }
                    }
                    Fragement_two_UserPreferSeting_listAdapter mylistAdapter = new Fragement_two_UserPreferSeting_listAdapter (
                            getApplicationContext ( ),
                            postList);
                    //  接下来将postlist  Post到后端中

                    Data ();
                    String user_id=user_bean.getUserid ().replace ("s_userid=","");
                    listViewShow.setAdapter (mylistAdapter);
                    for (int i = 0; i < postList.size ( ); i++) {
                        System.out.println ("user_id=" + user_id + "&Dish_name=" + postList.get (i));
                        try {
                            getResultForHttpGet (user_id,String.valueOf (postList.get (i)));
                        } catch (IOException e) {
                            e.printStackTrace ();
                            Toast.makeText (getApplicationContext (),"服务器无响应，请于联系管理员",Toast.LENGTH_LONG).show ();
                        }
                    }
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    //取消按钮的事件
                    break;
                default:
                    break;
            }
        }
    }


    //重写simpleadapterd的getview方法
    class SetSimpleAdapter extends SimpleAdapter {

        public SetSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from,
                                int[] to) {
            super (context, data, resource, from, to);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LinearLayout.inflate (getBaseContext ( ), R.layout.activity_userperfer_checkbox, null);
            }
            CheckBox ckBox = (CheckBox) convertView.findViewById (R.id.X_checkbox);
            //每次都根据 bl[]来更新checkbox
            if (bl[position] == true) {
                ckBox.setChecked (true);
            } else if (bl[position] == false) {
                ckBox.setChecked (false);
            }
            return super.getView (position, convertView, parent);
        }
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
    public String getResultForHttpGet(String user_id,String Dish_name) throws ClientProtocolException, IOException {
        String path="http://59.70.156.12/WhatWeEat/Activity_UserPerfer/userperferdata.php";
        String uri=path+"?user_id="+user_id+"&Dish_name="+Dish_name;
        //注意字符串连接时不能带空格
        String result="";
        HttpGet httpGet=new HttpGet(uri);
        HttpResponse response=new DefaultHttpClient ().execute(httpGet);
        if(response.getStatusLine().getStatusCode()==200){
            HttpEntity entity=response.getEntity();
            result= EntityUtils.toString(entity, HTTP.UTF_8);
            System.out.println(result);
        }
        return result;
    }

    private String[] initData() throws IOException {
        String result = "";
        String url = "http://59.70.156.12/WhatWeEat/Activity_UserPerfer/Dish_data.php";
        HttpGet httpGet = new HttpGet (url);
        String[] str = new String[0];
        HttpResponse response = new DefaultHttpClient ( ).execute (httpGet);
        if (response.getStatusLine ( ).getStatusCode ( ) == 200) {
            HttpEntity entity = response.getEntity ( );
            result = EntityUtils.toString (entity,HTTP.UTF_8);
            System.out.println (result);
            String listdata = result.replace ("[", "")
                    .replace ("]", "")
                    .replace ("\"","");
            str = listdata.split (",");
            return str;
        }else {
            return str;
        }
    }
}

