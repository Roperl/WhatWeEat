package com.exm.roper.whatweeat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roper on 2018/6/8.
 * 店名的POST从上一个页面MainFragment获得,店名以店铺位置为准
 */

public class Res_detail_Activity extends AppCompatActivity{

    private String Res_name;
    private String user_id;
    private ArrayList<Deatial_bean> itemlist;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_res_detail);

        RecyclerView recyclerView=findViewById (R.id.detail_recycle);
        init ();
        List<JSONObject> list = JSON.parseArray(init(), JSONObject.class);
        itemlist=new ArrayList<Deatial_bean>();
        for (JSONObject object : list) {
            System.out.println(object.getString("dish_name"));
            System.out.println(object.getString("dish_price"));
            itemlist.add (
                    new Deatial_bean (object.getString ("dish_name"),
                    object.getString("dish_price")));
        }
        recyclerView.setLayoutManager (new LinearLayoutManager (this,LinearLayoutManager.VERTICAL,false));
        Deatial_recy_adapter adapter= new Deatial_recy_adapter ();
        adapter.setData (itemlist);
        recyclerView.setAdapter (adapter);

    }


    public String init(){
        String data= null;
        Res_name=getIntent ().getStringExtra ("res_name");
//        Res_name="新餐厅二楼18窗";
        user_id="0";//这里需要把user_id的值拿过来，今天就到这里。时间晚了
        try {
            data=getResultForHttpGet (user_id,Res_name);
        } catch (IOException e) {
            e.printStackTrace ( );
        }
        System.out.println(data);
        if (data!=null){
            return data;
        }else {
        return data;
        }
    }

    //执行HTTP请求获得店名，菜品列表，菜品图片路径，菜品价格
   public String getResultForHttpGet(String userid,String resname) throws ClientProtocolException, IOException {
       String path="http://59.70.156.12/WhatWeEat/ResDeatil/ResDeatil.php";
       String uri=path+"?userid="+userid+"&resname="+resname;
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

}
