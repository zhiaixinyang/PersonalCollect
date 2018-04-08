package blog.com.blogstudy.kotlin.note.impl

import blog.com.blogstudy.kotlin.note.inteface.BaseKotlinInterface

/**
 * Created by MDove on 18/4/8.
 */
class IKotlinImpl : BaseKotlinInterface<String> {
    override fun create(): String = "abc"
}