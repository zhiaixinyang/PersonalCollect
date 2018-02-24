package com.example.mbenben.studydemo.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.mbenben.studydemo.R;

/**
 * Created by MDove on 2018/2/26.
 */
public class ClipboardUtils {

    public static void copyToClipboard(Context context, String text) {
        if (context == null || TextUtils.isEmpty(text)) {
            return;
        }
        ClipData clipDate = ClipData.newPlainText("simple text", text);
        setToClipboard(context, clipDate);
    }

    private static void setToClipboard(Context context, ClipData clip) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, R.string.toast_copy_success, Toast.LENGTH_SHORT).show();
    }
}
