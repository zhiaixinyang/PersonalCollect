package com.iyao.sample.method

import android.text.Spannable
import android.view.KeyEvent
import android.widget.EditText
import com.example.mbenben.studydemo.view.eastat.library.KeyCodeDeleteHelper
import com.example.mbenben.studydemo.view.eastat.library.NoCopySpanEditableFactory
import com.example.mbenben.studydemo.view.eastat.library.SpanFactory
import com.iyao.sample.User
import com.iyao.eastat.span.DirtySpan
import com.iyao.eastat.watcher.DirtySpanWatcher

/**
 * Created by MDOve on 18/12/15
 *
 * 原项目GitHub地址：https://github.com/iYaoy/easy_at/
 */
object WeChat: Method {
    override fun init(editText: EditText) {
        editText.text = null
        editText.setEditableFactory(NoCopySpanEditableFactory(
                DirtySpanWatcher { it is DirtySpan }))
        editText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                KeyCodeDeleteHelper.onDelDown((v as EditText).text)
            }
            return@setOnKeyListener false
        }
    }

    override fun newSpannable(user: User): Spannable {
        return SpanFactory.newSpannable("@${user.name}", user)
    }
}