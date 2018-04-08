package com.example.mbenben.studydemo.basenote.kotlin.note

import blog.com.blogstudy.kotlin.note.AbstractKotlinNote
import blog.com.blogstudy.kotlin.note.BaseKotlinNote
import blog.com.blogstudy.kotlin.note.inteface.BaseKotlinInterface

/**
 * Created by MDove on 2018/4/8.
 */
class KotlinNote() : AbstractKotlinNote("Haha") {
    override fun isFun2() = Unit

    private val listeners: ArrayList<BaseKotlinInterface<String>> = ArrayList()

    fun addListener(listener: BaseKotlinInterface<String>) {
        listeners.add(listener)
    }
}