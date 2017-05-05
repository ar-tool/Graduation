package com.oujian.graduation.adpater;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oujian.graduation.R;
import com.oujian.graduation.common.Constant;
import com.oujian.graduation.net.entity.NewsEntity;
import com.oujian.graduation.utils.GildeUtils;

import java.util.ArrayList;
import java.util.List;

import static com.oujian.graduation.common.Constant.IMG_URL;

/**
 * 主界面消息适配器
 * Created by yi on 2017-04-24.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<NewsEntity> mDatas = new ArrayList<NewsEntity>();


    private OnItemClick mOnItemClick;
    public void setItemCick(OnItemClick cick){
        this.mOnItemClick = cick;
    }
    public HomeAdapter(Context context) {
        this.mContext =context;
        mLayoutInflater =LayoutInflater.from(mContext);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //foot布局与正常一致,不需要根据type设置不同的布局
        View itemView = mLayoutInflater.inflate(R.layout.item_home,parent,false);
        //传递了type值和view
        MyViewHolder viewHolder = new MyViewHolder(itemView, viewType);
       return viewHolder ;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textView.setText(mDatas.get(position).getTitle());
        holder.time.setText(mDatas.get(position).getPublishTime());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClick != null) {
                    mOnItemClick.onClick(mDatas.get(position));
                }
            }
        });
    }
    public void setDataList(List<NewsEntity> list) {
        this.mDatas.clear();
        this.mDatas.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView time;
        CardView view;
        public int type = 0;
        public MyViewHolder(View itemView, int type) {
            super(itemView);
            this.type = type;
            view = (CardView) itemView.findViewById(R.id.rl_item_home);
            textView = (TextView) itemView.findViewById(R.id.item_home_tv);
            time =(TextView) itemView.findViewById(R.id.publish_time);
        }
    }

    public interface OnItemClick{
        public void onClick(NewsEntity entity);
    }
}
