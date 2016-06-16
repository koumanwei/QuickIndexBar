package com.koumanwei.quickindexbar.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    private static Toast mToast;

    public static void showShort(Context mcontext, String text) {
        Toast.makeText(mcontext, text, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context mcontext, String text) {
        Toast.makeText(mcontext, text, Toast.LENGTH_LONG).show();
    }

    public static void singleShowShort(Context mContext, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }

    public static void singleShowLong(Context mContext, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, "", Toast.LENGTH_LONG);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
