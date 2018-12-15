package com.iyao.eastat.watcher

import android.text.Spannable
import com.iyao.eastat.span.DirtySpan

/**
 * Created by MDOve on 18/12/15
 *
 * 原项目GitHub地址：https://github.com/iYaoy/easy_at/
 */
class DirtySpanWatcher(private val removePredicate: (Any) -> Boolean) : SpanWatcherAdapter() {

    override fun onSpanChanged(text: Spannable, what: Any, ostart: Int, oend: Int, nstart: Int,
                               nend: Int) {
        if (what is DirtySpan && what.isDirty(text)) {
            val spanStart = text.getSpanStart(what)
            val spanEnd = text.getSpanEnd(what)
            text.getSpans(spanStart, spanEnd, Any::class.java).filter {
                removePredicate.invoke(it)
            }.forEach {
                text.removeSpan(it)
            }
        }
    }
}