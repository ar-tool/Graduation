package com.oujian.graduation.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oujian.graduation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yi on 2017-04-24.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<String> mDatas = new ArrayList<String>();

    public static final int TYPE_FOOTER = 1;
    public static final int TYPE_DATA = TYPE_FOOTER + 1;

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
        View itemView = mLayoutInflater.inflate(R.layout.item_home,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
       return viewHolder ;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.textView.setText(mDatas.get(position));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClick != null) {
                    mOnItemClick.onClick(v);
                }
            }
        });
    }
    public void setDataList(List<String> list) {
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
        public MyViewHolder(View itemView) {
            super(itemView);
            view = (RelativeLayout) itemView.findViewById(R.id.rl_item_home);
            textView = (TextView) itemView.findViewById(R.id.item_home_tv);
        }
    }

    public interface OnItemClick{
        public void onClick(View view);
    }
}
