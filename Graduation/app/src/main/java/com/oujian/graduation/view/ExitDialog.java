package com.oujian.graduation.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oujian.graduation.R;


/**
 * Created by yi on 2016/11/28.
 */

public class ExitDialog extends Dialog {

    public ExitDialog(Context context) {
        super(context);
    }

    public ExitDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;

        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }


        public Builder setPositiveButton(OnClickListener listener) {
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(OnClickListener listener) {
            this.negativeButtonClickListener = listener;
            return this;
        }

        public ExitDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final ExitDialog dialog = new ExitDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_exit, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            // set the dialog title
            // set the confirm button

                if (positiveButtonClickListener != null) {
                    ((TextView) layout.findViewById(R.id.sure_exit_btn))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });

            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.sure_exit_btn).setVisibility(
                        View.GONE);
            }

                if (negativeButtonClickListener != null) {
                    ((TextView) layout.findViewById(R.id.cancel_exit_btn))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.cancel_exit_btn).setVisibility(
                        View.GONE);
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }
}
