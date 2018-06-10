package com.exm.roper.whatweeat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Roper on 2018/3/30.
 */

class MyAdapter extends RecyclerView.Adapter {

    private OnItemClickListener mOnItemClickListener;
    //数据
    private ArrayList<MainFragment_recy_data> itemList;
    //        private ImageResizer imgResizer = new ImageResizer();
    //设置数据
    public void setData(ArrayList<MainFragment_recy_data> itemList){
        this.itemList = itemList;
    }


    public interface OnItemClickListener{
        void onClick( String data);
//        void onLongClick( String data);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //得到item的view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        //返回viewholder
        return new mViewHolder(view);
    }


    //绑定数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MainFragment_recy_data it = itemList.get(position);
        mViewHolder mholder = (mViewHolder)holder;
        //设置imageView的图片
        mholder.iv_item_img.setImageBitmap (it.getImg());
//            mholder.iv_item_img.setImageBitmap(imgResizer.decodeSampledBitmapFromResource(getResources(),it.getImg()));
        //设置textView的文字
        mholder.tv_item_desc.setText(it.getRes_name());
        mholder.tv_item_body.setText (it.getRes_location());

        if (mOnItemClickListener !=null){
            holder.itemView.setOnClickListener (new View.OnClickListener (){
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick (it.getRes_location ());
                }
            });
//            长按的方法，有问题所以先禁用
//            holder.itemView.setOnClickListener (new View.OnClickListener ( ) {
//                @Override
//                public void onClick(View view) {
//                    mOnItemClickListener.onLongClick (it.getRes_location ());
//                }
//            });
        }
    }



    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class mViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_item_img;
        TextView tv_item_desc;
        TextView tv_item_body;

        public mViewHolder(View itemView) {
            super(itemView);
            iv_item_img = (ImageView) itemView.findViewById(R.id.iv_image);
            tv_item_desc = (TextView)itemView.findViewById(R.id.Item_TextView_Tile);
            tv_item_body=itemView.findViewById (R.id.Item_TextView_body);
        }
    }


}

