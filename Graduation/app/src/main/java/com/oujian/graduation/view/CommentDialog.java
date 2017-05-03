package com.oujian.graduation.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.oujian.graduation.R;
import com.oujian.graduation.common.MyContext;
import com.oujian.graduation.entity.PingLun;

/**
 * 
* @ClassName: CommentDialog 
* @Description: 评论长按对话框，保护复制和删除 
* @author yiw
* @date 2015-12-28 下午3:36:39 
*
 */
public class CommentDialog extends Dialog implements
		View.OnClickListener {

	private Context mContext;
	private PingLun mCommentItem;
	private int mPosition;
	private String mNoteId;
	public CommentDialog(String noteId, Context context,
                         PingLun commentItem, int position) {
		super(context, R.style.comment_dialog);
		this.mNoteId = noteId;
		mContext = context;
		this.mCommentItem = commentItem;
		this.mPosition = position;
	}
	private OnDeleteCommitClick commitClick;
	public void setCommitClick(OnDeleteCommitClick click){
		this.commitClick = click;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_comment);
		initWindowParams();
		initView();
	}

	private void initWindowParams() {
		Window dialogWindow = getWindow();
		// 获取屏幕宽、高用
		WindowManager wm = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.width = (int) (display.getWidth() * 0.65); // 宽度设置为屏幕的0.65

		dialogWindow.setGravity(Gravity.CENTER);
		dialogWindow.setAttributes(lp);
	}

	private void initView() {
		TextView copyTv = (TextView) findViewById(R.id.copyTv);
		copyTv.setOnClickListener(this);
		TextView deleteTv = (TextView) findViewById(R.id.deleteTv);
		//两者userId相同，就不没有删除项
		if (mCommentItem != null
				&& MyContext.getInstance().getUserId().equals(

						mCommentItem.getCreateUser())) {
			deleteTv.setVisibility(View.VISIBLE);
		} else {
			deleteTv.setVisibility(View.GONE);
		}
		deleteTv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			//复制
		case R.id.copyTv:
			if (mCommentItem != null) {
				ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboard.setText(mCommentItem.getComment());
			}
			dismiss();
			break;
		//删除
		case R.id.deleteTv:
			if (commitClick!= null && mCommentItem != null) {
				commitClick.deleteCommit(mPosition,mNoteId,mCommentItem.getId());
			}
			dismiss();
			break;
		default:
			break;
		}
	}
	public interface OnDeleteCommitClick{
		void deleteCommit(int postion , String noteId, final String pinglunId);
	}
}
