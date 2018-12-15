package com.iyao.eastat.span

import android.text.Spannable

/**
 * Created by MDOve on 18/12/15
 *
 * 原项目GitHub地址：https://github.com/iYaoy/easy_at/
 */
interface DirtySpan {
    fun isDirty(text: Spannable): Boolean
}