package com.oujian.graduation.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.oujian.graduation.R;
import com.oujian.graduation.adpater.MessageAdapter;
import com.oujian.graduation.base.BaseActivity;
import com.oujian.graduation.common.Constant;
import com.oujian.graduation.entity.Message;
import com.oujian.graduation.net.RetrofitClient;
import com.oujian.graduation.net.base.BaseSubscriber;
import com.oujian.graduation.net.base.ExceptionHandle;
import com.oujian.graduation.net.entity.ChatEntity;
import com.oujian.graduation.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class RobotChatActivity extends BaseActivity {
    private final String TAG = this.getClass().getSimpleName();

    @Bind(R.id.chat_toolBar)
    Toolbar mToolbar;
    @Bind(R.id.rv_chat)
    RecyclerView recyclerView;
    @Bind(R.id.bt_send)
    Button bt_send;
    @Bind(R.id.et_context)
    EditText et_context;
    private MessageAdapter adapter;
    private List<Message> messages =new ArrayList<>();

    @Override
    protected View getContentView() {
        View view =getLayoutInflater().inflate(R.layout.activity_robot_chat,null);
        return view;
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

        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        adapter =new MessageAdapter(RobotChatActivity.this,messages);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void initListeners() {

    }
    @OnClick(R.id.bt_send)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_send:
                if(!et_context.getText().equals("") && et_context.getText()!=null){
                    Message message =new Message(et_context.getText().toString(),Message.TYPE_MY);
                    messages.add(message);
                    adapter.notifyItemInserted(messages.size()-1);//有新消息时刷新recycleview中的显示
                    recyclerView.scrollToPosition(messages.size()-1);//将recycleview定位到最后一行
                    et_context.setText("");
                    //同时还需要获取服务端返回的对话
                    RetrofitClient.getInstance(RobotChatActivity.this, Constant.TULING_URL).createBaseApi().chat(Constant.TULING_KEY, et_context.getText().toString(),
                            new BaseSubscriber<ChatEntity>(RobotChatActivity.this) {
                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.showToast(RobotChatActivity.this,"服务器错误");
                                }
                            });
                        }

                        @Override
                        public void onNext(ChatEntity chatEntity) {
                            if(chatEntity != null){
                                Message msg = new Message(chatEntity.getText(),Message.TYPE_SERVICE);
                                messages.add(msg);
                                adapter.notifyItemInserted(messages.size()-1);//有新消息时刷新recycleview中的显示
                                recyclerView.scrollToPosition(messages.size()-1);//将recycleview定位到最后一行
                            }
                        }
                    });
                }

                break;
            default:
                break;
        }
    }
    @Override
    protected void initData() {
        Message message1 =new Message("有什么问题吗？",Message.TYPE_SERVICE);
        messages.add(message1);
    }

    public void loadData(){

    }
}
