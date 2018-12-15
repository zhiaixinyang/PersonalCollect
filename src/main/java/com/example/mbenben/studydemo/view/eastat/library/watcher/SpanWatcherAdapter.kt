package com.iyao.eastat.watcher

import android.text.SpanWatcher
import android.text.Spannable

/**
 * Created by MDOve on 18/12/15
 *
 * 原项目GitHub地址：https://github.com/iYaoy/easy_at/
 */
open class SpanWatcherAdapter: SpanWatcher {
    override fun onSpanChanged(text: Spannable, what: Any, ostart: Int, oend: Int, nstart: Int,
                               nend: Int) {}

    override fun onSpanRemoved(text: Spannable, what: Any, start: Int, end: Int) {
    }

    override fun onSpanAdded(text: Spannable, what: Any, start: Int, end: Int) {
    }
}