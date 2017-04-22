package com.oujian.graduation.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.oujian.graduation.R;


/**
 * http请求时候的进度
 * Created by yi on 2016/12/07
 */

public class HttpDialog extends Dialog {

    public HttpDialog(Context context) {
        super(context);
    }

    public HttpDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }


        public HttpDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final HttpDialog dialog = new HttpDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_http, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            // set the dialog title
            // set the confirm button
            dialog.setContentView(layout);
            return dialog;
        }
    }
}
