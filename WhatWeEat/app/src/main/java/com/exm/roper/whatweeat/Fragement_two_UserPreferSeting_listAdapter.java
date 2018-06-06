package com.exm.roper.whatweeat;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Roper on 2018/4/22.
 */

public class Fragement_two_UserPreferSeting_listAdapter extends BaseAdapter{
    private ArrayList data;
    private Context mContext;
    public Fragement_two_UserPreferSeting_listAdapter(Context mContext, ArrayList data) {
        super();
        this.mContext = mContext;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size ();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(mContext);
        textView.setText((CharSequence) data.get (position));
        return textView;
    }
}
