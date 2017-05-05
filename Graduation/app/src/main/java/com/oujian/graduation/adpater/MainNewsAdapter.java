package com.oujian.graduation.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oujian.graduation.R;
import com.oujian.graduation.net.entity.NewsEntity;
import com.oujian.graduation.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面消息适配器
 * Created by yi on 2017-04-24.
 */

public class MainNewsAdapter extends RecyclerView.Adapter<MainNewsAdapter.MyViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<NewsEntity> mDatas = new ArrayList<NewsEntity>();


    private OnItemClick mOnItemClick;
    public void setItemCick(OnItemClick cick){
        this.mOnItemClick = cick;
    }
    public MainNewsAdapter(Context context) {
        this.mContext =context;
        mLayoutInflater =LayoutInflater.from(mContext);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //foot布局与正常一致,不需要根据type设置不同的布局
        View itemView = mLayoutInflater.inflate(R.layout.item_main,parent,false);
        //传递了type值和view
        MyViewHolder viewHolder = new MyViewHolder(itemView, viewType);
       return viewHolder ;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

            holder.textView.setText(mDatas.get(position).getTitle());
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
        RelativeLayout view;
        public int type = 0;
        public MyViewHolder(View itemView, int type) {
            super(itemView);
            this.type = type;
            view = (RelativeLayout) itemView.findViewById(R.id.rl_item_main);
            textView = (TextView) itemView.findViewById(R.id.item_news);
        }
    }

    public interface OnItemClick{
        public void onClick(NewsEntity entity);
    }
}
