package com.iyao.sample.method

import android.text.Spannable
import android.widget.EditText
import com.iyao.sample.User

/**
 * Created by MDOve on 18/12/15
 *
 * 原项目GitHub地址：https://github.com/iYaoy/easy_at/
 */
interface Method {

    fun init(editText: EditText)
    fun newSpannable(user: User): Spannable
}