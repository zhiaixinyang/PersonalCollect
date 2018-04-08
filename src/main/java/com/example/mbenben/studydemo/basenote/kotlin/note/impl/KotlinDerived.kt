package blog.com.blogstudy.kotlin.note.impl

import android.util.Log
import blog.com.blogstudy.kotlin.note.inteface.BaseKotlinInterface

/**
 * Created by MDove on 18/4/8.
 */
//by：base 将会在 KotlinDerived 中内部存储
class KotlinDerived(base: IKotlinImpl) : BaseKotlinInterface<String> by base