package com.exm.roper.whatweeat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Roper on 2018/4/5.
 */

public class Fragment_two_list_adapter extends ArrayAdapter {
    private final int resourceId;

    public Fragment_two_list_adapter(Context context, int textViewResourceId, List<Fragment_two_list_entity> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fragment_two_list_entity fruit = (Fragment_two_list_entity) getItem(position); // 获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        ImageView fruitImage = view.findViewById(R.id.fruit_image);//获取该布局内的图片视图
        TextView fruitName = view.findViewById(R.id.fruit_name);//获取该布局内的文本视图
        fruitImage.setImageResource(fruit.getImageId());//为图片视图设置图片资源
        fruitName.setText(fruit.getName());//为文本视图设置文本内容
        return view;
    }
}
