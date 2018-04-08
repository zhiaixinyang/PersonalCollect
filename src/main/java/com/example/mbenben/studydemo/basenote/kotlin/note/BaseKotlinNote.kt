package blog.com.blogstudy.kotlin.note

/**
 * Created by MDove on 18/4/8.
 */
open class BaseKotlinNote {
    //默认情况下属性必须初始化（只有open表示才能被子类所覆盖）
    open val name: String = "aaa"
    //如果不想初始化，必须标明？= null
    val abc: String? = null

    open fun isFun() {}

    open fun isFun2() {}

    /**
     *  伴生函数，类似java中的static调用（ Factory可以省略 ）
     *  只允许声明一个伴生函数
     *
     *  //使用接口的方式
     *  companion object : BaseKotlinInterface<BaseKotlinNote> {
     *      override fun create(): BaseKotlinNote = BaseKotlinNote()
     *  }
     */
    companion object Factory {
        fun create(): BaseKotlinNote = BaseKotlinNote()
    }
}