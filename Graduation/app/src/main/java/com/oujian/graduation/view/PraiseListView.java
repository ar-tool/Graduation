package com.oujian.graduation.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;


import com.oujian.graduation.R;
import com.oujian.graduation.common.MyContext;
import com.oujian.graduation.entity.Click;
import com.oujian.graduation.utils.DecodeUtils;

import java.util.List;

/**
 * Created by yi on 16/7/9.
 * 点赞集合
 */
public class PraiseListView extends AppCompatTextView {


    private int itemColor;
    private int itemSelectorColor;
    private List<Click> datas;
    private OnItemClickListener onItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PraiseListView(Context context) {
        super(context);
    }

    public PraiseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public PraiseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PraiseListView, 0, 0);
        try {
            //textview的默认颜色
            itemColor = typedArray.getColor(R.styleable.PraiseListView_item_color, getResources().getColor(R.color.praise_item_default));
            itemSelectorColor = typedArray.getColor(R.styleable.PraiseListView_item_selector_color, getResources().getColor(R.color.praise_item_selector_default));

        }finally {
            typedArray.recycle();
        }
    }

    public List<Click> getDatas() {
        return datas;
    }
    public void setDatas(List<Click> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }


    public void notifyDataSetChanged(){
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if(datas != null && datas.size() > 0){
            //添加点赞图标
            builder.append(setImageSpan());
            Click item = null;
            for (int i=0; i<datas.size(); i++){
                item = datas.get(i);
                if(item != null){
                    if(item.getNickname() != null){
                        //有昵称用昵称没有就用账号
                        builder.append(setClickableSpan(DecodeUtils.getString(item.getNickname()), i));
                    }else {
                        builder.append(setClickableSpan(DecodeUtils.getString(item.getAccount()), i));
                    }
                    //如果还有点赞人员，就要加分隔符号
                    if(i != datas.size()-1){
                        builder.append(", ");
                    }
                }
            }
        }

        setText(builder);
        setMovementMethod(new CircleMovementMethod(itemSelectorColor));
    }


    private SpannableString setImageSpan(){
        String text = "  ";
        SpannableString imgSpanText = new SpannableString(text);
        imgSpanText.setSpan(new ImageSpan(MyContext.getInstance().getContext(), R.drawable.icon_like, DynamicDrawableSpan.ALIGN_BASELINE),
                0 , 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return imgSpanText;
    }

    @NonNull
    private SpannableString setClickableSpan(String textStr, final int position) {
        if(textStr == null){
            return null;
        }else {
            SpannableString subjectSpanText = new SpannableString(textStr);
            subjectSpanText.setSpan(new SpannableClickable(itemColor){
                                        @Override
                                        public void onClick(View widget) {
                                            if(onItemClickListener!=null){
                                                onItemClickListener.onClick(position);
                                            }
                                        }
                                    }, 0, subjectSpanText.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return subjectSpanText;
        }
    }


    public static interface OnItemClickListener{
        public void onClick(int position);
    }
}
