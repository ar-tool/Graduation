package com.oujian.graduation.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;
import com.google.gson.Gson;
import com.oujian.graduation.R;
import com.oujian.graduation.activity.PublishActivity;
import com.oujian.graduation.adpater.FriendAdapter;
import com.oujian.graduation.adpater.HomePagerAdapter;
import com.oujian.graduation.base.BaseFragment;
import com.oujian.graduation.common.MyContext;
import com.oujian.graduation.entity.Click;
import com.oujian.graduation.entity.CommentConfig;
import com.oujian.graduation.entity.PingLun;
import com.oujian.graduation.net.RetrofitClient;
import com.oujian.graduation.net.base.BaseSubscriber;
import com.oujian.graduation.net.base.ExceptionHandle;
import com.oujian.graduation.net.entity.NoteEntity;
import com.oujian.graduation.net.req.AddLikeReq;
import com.oujian.graduation.net.req.CommentReq;
import com.oujian.graduation.net.req.NoteListReq;
import com.oujian.graduation.net.res.BaseResponse;
import com.oujian.graduation.net.res.BaseResult;
import com.oujian.graduation.utils.CommonUtils;
import com.oujian.graduation.utils.ToastUtils;
import com.oujian.graduation.view.CommentListView;
import com.oujian.graduation.view.SimpleButton;

import java.util.List;

import butterknife.Bind;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends BaseFragment {
    private static final String TAG = "FriendFragment";
    private View mRootView ;
    @Bind(R.id.friendzone_add)
    RelativeLayout mAdd;
    @Bind(R.id.rl_top)
    RelativeLayout mTop;
    @Bind(R.id.friend_recyclerView)
    LRecyclerView mLRecyclerView;
    @Bind(R.id.body_layout)
    RelativeLayout mBodyLayout;
    //输入框区域
    @Bind(R.id.editTextBodyLl)
    LinearLayout edittextbody;
    @Bind(R.id.circleEt)
    EditText editText;
    //发送
    @Bind(R.id.sendIv)
    SimpleButton sendImg;

    private LinearLayoutManager mLinearLayoutManager;
    private FriendAdapter mAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(mRootView == null){
            mRootView = inflater.inflate(R.layout.fragment_friend, container, false);
        }
        return mRootView;
    }

    @Override
    protected void initViews(View rootView) {
        //设置下拉样式
        mLRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new FriendAdapter(getActivity());
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mLRecyclerView.setAdapter(mLRecyclerViewAdapter);
    }


    @Override
    protected void initListeners() {
      mAdd.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              //跳转到发布界面
            startActivity(new Intent(getActivity(), PublishActivity.class));
          }
      });

        mLRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置foot为正常样式
                RecyclerViewStateUtils.setFooterViewState(mLRecyclerView, LoadingFooter.State.Normal);
                mAdapter.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();
                loadData();
            }
        });
        mLRecyclerView.setRefreshing(true);//初始化刷新一下,获取数据

        mAdapter.setFriendZoneListener(new FriendAdapter.FriendZoneListener() {
            @Override
            public void onAddLike(final int postion) {
                //点赞
                final NoteEntity entity = mAdapter.getDataList().get(postion);
                final AddLikeReq req = new AddLikeReq();
                req.setUserId(MyContext.getInstance().getUserId());
                req.setPostId(entity.getId());
                //发送请求
                String json = new Gson().toJson(req);
                RetrofitClient.getInstance(getActivity()).createBaseApi().addLike(json, new BaseSubscriber<BaseResult>(getActivity()) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        ToastUtils.showToast(getActivity(),"点赞失败");
                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        Click click = new Click();
                        click.setPostId(entity.getId());
                        click.setAccount(MyContext.getInstance().getUserInfo().getAccount());
                        click.setCreateTime("");
                        click.setUpvoteUser(MyContext.getInstance().getUserId());
                        click.setId("");
                        entity.getUpvoteList().add(click);
                        mAdapter.notifyItemChanged(postion);
                    }
                });
            }

            @Override
            public void onDeleteLike(int position) {
                //取消点赞
            }

            @Override
            public void onPublishComment(CommentConfig commentConfig) {
                //只是弹出输入框
                updateEditTextBodyVisible(View.VISIBLE, commentConfig);
            }

        });

        //发送评论，这里才是评论请求
        sendImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //发布评论
                final String content =  editText.getText().toString().trim();
                if(TextUtils.isEmpty(content)){
                    Toast.makeText(getActivity(), "评论内容不能为空...", Toast.LENGTH_SHORT).show();
                    return;
                }
                final NoteEntity bbs = (NoteEntity) mAdapter.getDataList().get(commentConfig.itemPosition);
                CommentReq req = new CommentReq();
                req.setPostId(bbs.getId());
                req.setUserId(MyContext.getInstance().getUserId());
                req.setComment(content);
                String json = new Gson().toJson(req);
                RetrofitClient.getInstance(getActivity()).createBaseApi().comment(json, new BaseSubscriber<BaseResult>(getActivity()) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        ToastUtils.showToast(getActivity(),"评论失败");
                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        if(baseResult.getRetCode() == 0){
                            PingLun pingLun = new PingLun();
                            pingLun.setId("");
                            pingLun.setCreateUser(MyContext.getInstance().getUserId());
                            pingLun.setAccount(MyContext.getInstance().getUserInfo().getAccount());
                            pingLun.setComment(content);
                            pingLun.setPostId(bbs.getId());
                            pingLun.setCreateTime("");
                            NoteEntity item = (NoteEntity) mAdapter.getDataList().get(commentConfig.itemPosition);
                            item.getCommentList().add(pingLun);
                            mAdapter.notifyItemChanged(commentConfig.itemPosition);
                        }
                    }
                });
                //清空评论文本
                editText.setText("");
            }
        });

        mLRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (edittextbody.getVisibility() == View.VISIBLE) {
                    updateEditTextBodyVisible(View.GONE, null);
                    return true;
                }
                return false;
            }
        });

        setViewTreeObserver();
    }

    /**
     * @param noteId 删除帖子对话框
     */

    private void createDeleteDialog(final String noteId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("确定删除吗");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //删除帖子的请求
                dialog.cancel();
            }
        });
        builder.show();
    }
    /**
     * 加载数据
     */
    private void loadData() {
        //  获得数据
        mLRecyclerView.refreshComplete();
        NoteListReq req = new NoteListReq();
        req.setUserId(MyContext.getInstance().getUserId());
        String json = new Gson().toJson(req);
        RetrofitClient.getInstance(getActivity()).createBaseApi().getNoteList(json, new BaseSubscriber<BaseResponse<List<NoteEntity>>>(getActivity()) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtils.showToast(getActivity(),"没有帖子内容");
            }

            @Override
            public void onNext(BaseResponse<List<NoteEntity>> baseResponse) {
                if(null != baseResponse){
                    mAdapter.setDataList(baseResponse.getRetBody());
                    mAdapter.notifyDataSetChanged();
                    RecyclerViewStateUtils.setFooterViewState(mLRecyclerView, LoadingFooter.State.Normal);//将foot恢复正常
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void initData() {

    }

    /**
     * 获取状态栏高度
     * @return
     */
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    private CommentConfig commentConfig;
    //更新输入框的显示和隐藏
    private void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig) {
        this.commentConfig = commentConfig;
        edittextbody.setVisibility(visibility);
        if(View.VISIBLE==visibility){
            editText.requestFocus();

            //弹出键盘
            CommonUtils.showSoftInput( editText.getContext(),  editText);

        }else if(View.GONE==visibility){
            //隐藏键盘
            CommonUtils.hideSoftInput( editText.getContext(),  editText);
        }
    }
    //选中item高度
    private int selectCircleItemH;
    //键盘高度
    private int currentKeyboardH;
    /**
     * 选中评论的偏移量
     */
    private int selectCommentItemOffset;
    /**
     * 输入框高度
     */
    private int editTextBodyHeight;
    /**
     * 屏幕高度
     */
    private int screenHeight;
    private void setViewTreeObserver() {
        final ViewTreeObserver swipeRefreshLayoutVTO = edittextbody.getViewTreeObserver();
        swipeRefreshLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //将当前区域传给r
                edittextbody.getWindowVisibleDisplayFrame(r);
                int statusBarH =  getStatusBarHeight();//状态栏高度
                int screenH = mBodyLayout.getRootView().getHeight();
                if(r.top != statusBarH ){
                    //在这个demo中r.top代表的是状态栏高度，在沉浸式状态栏时r.top＝0，通过getStatusBarHeight获取状态栏高度
                    r.top = statusBarH;
                }
                int keyboardH = screenH - (r.bottom - r.top);

                if(keyboardH == currentKeyboardH){//有变化时才处理，否则会陷入死循环
                    return;
                }

                currentKeyboardH = keyboardH;
                screenHeight = screenH;//应用屏幕的高度
                editTextBodyHeight = edittextbody.getHeight();

                if(keyboardH<150){//说明是隐藏键盘的情况
                    updateEditTextBodyVisible(View.GONE, null);
                    return;
                }
                //偏移listview
                if(mLinearLayoutManager!=null && commentConfig != null){
                    mLinearLayoutManager.scrollToPositionWithOffset(commentConfig.itemPosition , getListviewOffset(commentConfig));
                    Log.i("123",""+getListviewOffset(commentConfig));
                }
            }
        });
    }

    /**
     * 测量偏移量
     * @param commentConfig
     * @return
     */
    private int getListviewOffset(CommentConfig commentConfig) {
        if(commentConfig == null)
            return 0;
        //这里如果你的listview上面还有其它占高度的控件，则需要减去该控件高度，listview的headview除外。
        //int listviewOffset = mScreenHeight - mSelectCircleItemH - mCurrentKeyboardH - mEditTextBodyHeight;
        int listviewOffset = screenHeight - selectCircleItemH - currentKeyboardH - editTextBodyHeight - mTop.getHeight();
        if(commentConfig.commentType == CommentConfig.Type.REPLY){
            //回复评论的情况
            listviewOffset = listviewOffset + selectCommentItemOffset;
        }
        Log.i(TAG, "listviewOffset : " + listviewOffset);
        return listviewOffset;
    }

    private void measureCircleItemHighAndCommentItemOffset(CommentConfig commentConfig){
        if(commentConfig == null)
            return;

        int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        //只能返回当前可见区域（列表可滚动）的子项
        View selectCircleItem = mLinearLayoutManager.getChildAt(commentConfig.itemPosition  - firstPosition);

        if(selectCircleItem != null){
            selectCircleItemH = selectCircleItem.getHeight();
        }

        if(commentConfig.commentType == CommentConfig.Type.REPLY){
            //回复评论的情况
            CommentListView commentLv = (CommentListView) selectCircleItem.findViewById(R.id.commentList);
            if(commentLv!=null){
                //找到要回复的评论view,计算出该view距离所属动态底部的距离
                View selectCommentItem = commentLv.getChildAt(commentConfig.commentPosition);
                if(selectCommentItem != null){
                    //选择的commentItem距选择的CircleItem底部的距离
                    selectCommentItemOffset = 0;
                    View parentView = selectCommentItem;
                    do {
                        int subItemBottom = parentView.getBottom();
                        parentView = (View) parentView.getParent();
                        if(parentView != null){
                            selectCommentItemOffset += (parentView.getHeight() - subItemBottom);
                        }
                    } while (parentView != null && parentView != selectCircleItem);
                }
            }
        }
    }
}
