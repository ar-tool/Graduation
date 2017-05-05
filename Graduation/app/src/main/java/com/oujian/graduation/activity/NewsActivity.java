package com.oujian.graduation.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oujian.graduation.R;
import com.oujian.graduation.base.BaseActivity;
import com.oujian.graduation.net.entity.NewsEntity;
import com.oujian.graduation.utils.ToastUtils;

import butterknife.Bind;

import static com.oujian.graduation.common.Constant.IMG_URL;

/**
 * Created by karel on 2017/5/5.
 */

public class NewsActivity extends BaseActivity {
    public static final String KEY_NEWS = "news";
    @Bind(R.id.news_toolbar)
    Toolbar mToolbar;

    @Bind(R.id.txt_title)
    TextView mTxtTitle;

    @Bind(R.id.img_pic)
    ImageView mImgPic;

    @Bind(R.id.txt_content)
    TextView mTxtContent;

    @Override
    protected View getContentView() {
        return getLayoutInflater().inflate(R.layout.activity_news, null);
    }

    @Override
    protected void initViews() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        NewsEntity newsEntity = (NewsEntity) getIntent().getSerializableExtra(KEY_NEWS);
        if (newsEntity == null) {
            ToastUtils.showShort(this, "Something Wrong");
            return;
        }
        mTxtTitle.setText(newsEntity.getTitle());
        mTxtContent.setText(newsEntity.getContent());
        Glide.with(this)
                .load(IMG_URL + newsEntity.getImgPath())
                .placeholder(R.color.gray)
                .into(mImgPic);
    }
}
