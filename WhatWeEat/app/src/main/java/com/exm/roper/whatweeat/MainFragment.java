package com.exm.roper.whatweeat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Roper on 2018/3/29.
 */
public class MainFragment extends Fragment implements OnBannerListener {
    private Banner banner;
    private RecyclerView recyclerView;
    private ArrayList<MainFragment_recy_data> itemList;

    public MainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate (R.layout.fragment_main, container, false);
        recyclerView = rootView.findViewById (R.id.recyclerview);
        banner = (Banner) rootView.findViewById (R.id.banner);
        final PullRefreshLayout  layout = rootView.findViewById (R.id.swipeRefreshLayout);
        ArrayList Namelist=new ArrayList();
        ArrayList ImgList=new ArrayList();
        initData ( );
        recyclerView.setLayoutManager (new LinearLayoutManager (getActivity ( )));
        final MyAdapter myAdapter = new MyAdapter ( );
        myAdapter.setData (itemList);
        recyclerView.setAdapter (myAdapter);

        try {
            String pattern = "..\"ac_name\":\"(.+)\",\"ac_img\":\"(.+)\".,.\"ac_name\":\"(.+)\",\"ac_img\":\"(.+)\".,.\"ac_name\":\"(.+)\",\"ac_img\":\"(.+)\".,.\"ac_name\":\"(.+)\",\"ac_img\":\"(.+)\"..";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(BannerData ().replace ("\\",""));
            while(m.find()){
                Namelist.add(m.group(1));
                Namelist.add(m.group(3));
                Namelist.add(m.group(5));
                Namelist.add(m.group(7));
                ImgList.add(m.group(2));
                ImgList.add(m.group(4));
                ImgList.add(m.group(6));
                ImgList.add(m.group(8));
                System.out.println(Namelist);
                System.out.println(ImgList);
            }
        } catch (IOException e) {
            e.printStackTrace ( );
        }
        banner.setBannerStyle (BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImageLoader (new MyLoader ( ));
        banner.setImages (ImgList);
        banner.setBannerAnimation (Transformer.Default);
        banner.setBannerTitles (Namelist);
        banner.setDelayTime (5000);
        banner.isAutoPlay (true);
        banner.setIndicatorGravity (BannerConfig.CENTER)
                .setOnBannerListener (this)
                .start ( );

        System.out.println(recyclerView.canScrollVertically (-1));

        layout.setRefreshStyle (PullRefreshLayout.STYLE_RING);
        layout.setOnRefreshListener (new PullRefreshLayout.OnRefreshListener ( ) {
                                         @Override
                                         public void onRefresh() {
                                             layout.postDelayed (new Runnable ( ) {
                                                                     @Override
                                                                     public void run() {
                                                                         // 刷新3秒完成
                                                                         layout.setRefreshing (false);
                                                                     }
                                                                 }
                                                     , 3000);
                                         }
                                     }
        );
        return rootView;
    }

    public void OnBannerClick(int position) {
        Log.i ("tag", "你点了第" + position + "张轮播图");
    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with (context).load ((String) path).into (imageView);
        }
    }

    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder ( );
        try {
            URL oracle = new URL (url);
            URLConnection yc = oracle.openConnection ( );
            BufferedReader in = new BufferedReader (new InputStreamReader (
                    yc.getInputStream ( )));
            String inputLine = null;
            while ((inputLine = in.readLine ( )) != null) {
                json.append (inputLine);
            }
            in.close ( );
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString ( );
    }

    public static class GsonUtil {
        // 将Json数据解析成相应的映射对象
        public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
            Gson gson = new Gson ( );
            T result = gson.fromJson (jsonData, type);
            return result;
        }

        // 将Json数组解析成相应的映射对象列表
        public static <T> List<T> parseJsonArrayWithGson(String jsonData,
                                                         Class<T> type) {
            Gson gson = new Gson ( );
            List<T> result = gson.fromJson (jsonData, new TypeToken<List<T>> ( ) {
            }
                    .getType ( ));
            return result;
        }
    }

    class Windows_data {
        private String res_name;
        private String res_location;
        private int img;
    }

    private void initData() {
        String url = "http://59.70.156.12/WhatWeEat/Fragment_main_recommend/recommerd_json.php";
        String jsons = loadJSON (url);
        List<Windows_data> windows_data = GsonUtil.parseJsonArrayWithGson (jsons, MainFragment.Windows_data.class);
        System.out.println (windows_data);
        itemList = new ArrayList<MainFragment_recy_data> ( );
        if (windows_data != null)
            for (int i = 0; i < windows_data.size ( ); i++) {
                String sgson = String.valueOf (windows_data.get (i))
                        .replace ("[", "")
                        .replace ("]", "")
                        .replace ("=", ":")
                        .replace ("}", "")
                        .replace ("localhost", "59.70.156.12");
                String pattern = "res_name:(.*), res_url:(.*), res_location:(.*), res_draw:(.*)";
                Pattern r = Pattern.compile (pattern);
                Matcher m = r.matcher (sgson);
                if (m.find ( )) {
                    Bitmap bitmaps = getHttpBitmap (m.group (4));
                    System.out.println (m.group (4) + " " + "bitmap" + " " + bitmaps);
                    itemList.add (new MainFragment_recy_data (m.group (1), m.group (3), bitmaps));
                } else
                    System.out.println ("none");
            }
    }

    public static Bitmap getHttpBitmap(String url) {
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            System.out.println ("进入getHttpdBitmap Try块");
            myFileURL = new URL (url);
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection ( );
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout (6000);
            conn.setDoInput (true);
            InputStream is = conn.getInputStream ( );
            //解析得到图片
            bitmap = BitmapFactory.decodeStream (is);
            is.close ( );
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return bitmap;
    }

    private String BannerData() throws IOException {
        String result = "";
        String url = "http://59.70.156.12/WhatWeEat/Fragment_main_banner/banner.php";
        HttpGet httpGet = new HttpGet (url);
        HttpResponse response = new DefaultHttpClient ( ).execute (httpGet);
        if (response.getStatusLine ( ).getStatusCode ( ) == 200) {
            HttpEntity entity = response.getEntity ( );
            result = EntityUtils.toString (entity, HTTP.UTF_8);
            System.out.println (result);
            //result正则之后数组
           return result;
        }else {
            return result;
        }
    }
}