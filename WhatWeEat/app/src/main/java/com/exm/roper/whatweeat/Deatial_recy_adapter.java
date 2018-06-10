package com.exm.roper.whatweeat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Roper on 2018/6/10.
 */

class Deatial_recy_adapter extends RecyclerView.Adapter {
    private ArrayList<Deatial_bean> list;

    public void setData(ArrayList<Deatial_bean> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Deatial_bean it=list.get (position);
        ViewHolder mholder=(ViewHolder)holder;
        mholder.dish_name.setText(it.getDish_name ());
        mholder.dish_price.setText(it.getDish_price ());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView dish_name;
        TextView dish_price;
      public ViewHolder(View itemView) {
            super(itemView);
            dish_name = itemView.findViewById(R.id.Item_TextView_Tile);
            dish_price =itemView.findViewById (R.id.Item_TextView_body);
        }
    }
}
