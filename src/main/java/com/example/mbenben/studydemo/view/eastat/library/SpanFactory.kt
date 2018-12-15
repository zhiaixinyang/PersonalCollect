package com.example.mbenben.studydemo.view.eastat.library

import android.text.Spannable
import android.text.SpannableString

/**
 * Created by MDOve on 18/12/15
 *
 * 原项目GitHub地址：https://github.com/iYaoy/easy_at/
 */
object SpanFactory {
    fun newSpannable(source: CharSequence, vararg spans: Any): Spannable {
        return SpannableString.valueOf(source).apply {
            spans.forEach {
                setSpan(it, 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }
}