package blog.com.blogstudy.kotlin.note

import android.util.Log
import blog.com.blogstudy.kotlin.note.impl.IKotlinImpl
import blog.com.blogstudy.kotlin.note.impl.KotlinDerived

/**
 * Created by MDove on 18/4/8.
 *
 * 基础用法笔记
 */
/**
 * 包含俩个参数的主构造函数,constructor在没有注解/可见性修饰词的时候是可以省略的
 * 如果加注解：class AbstractKotlinNote private @Inject constructor 此时必须要存在
 *
 * 如果该类有一个主构造函数,其基类型必须用(基类型的)主构造函数参数进行初始化。
 *     open class Base(p: Int) //只有被open修饰才能被继承/重写（ 默认类都是 final）
 *     class Derived(p: Int) : Base(p)
 *
 * 重写父类的方法/属性，必须显示表明override
 */
abstract class AbstractKotlinNote constructor(override val name: String, val age: Int) : BaseKotlinNote() {
    /**
     *  位运算符使用：
     *
     *  Java：
     *   int bitwiseOr = FLAG1 | FLAG2;
     *   int bitwiseAnd = FLAG1 & FLAG2;
     *  Kotlin：
     *   val bitwiseOr = FLAG1 or FLAG2
     *   val bitwiseAnd = FLAG1 and FLAG2
     *
     */

    val TAG = "AbstractKotlinNote"
    //属性age/name可以直接使用(val不可再重复赋值，var可以)
    val i: Int = age
    var ii: Int? = null
    val d: Double = i.toDouble()
    //延时加载，只在被调用的时候被初始化(默认是线程安全的，为了性能可以手动关闭)
    val no: Int by lazy(LazyThreadSafetyMode.NONE) {
        666
    }
    //声明一个为null的对象（在自己使用的时候再初始化）
    lateinit var av: String

    //伴生函数调用（java中的static）
    val aaa = BaseKotlinNote.create()

    //次级构造函数（ this()相当于super()的调用 ）
    constructor(title: String) : this(title, 0) {
        //委托模型（代理）
        val derived = IKotlinImpl()
        //此时的create本质调用的是IKotlinImpl中的create()的实现
        val abc = KotlinDerived(derived).create()
        ii = 33;
    }

    fun when1() {
        //setOf返回一个只读集合(没有任何允许我们改动的方法)
        val items = setOf("apple", "banana", "kiwi")

        when {
            "orange" in items -> Log.d(TAG, "juicy")
            "apple" in items -> Log.d(TAG, "apple is fine too")
        }
    }

    //final表示不允许再次被重写
    final override fun isFun() = Unit    //同    final override fun isFun() {}

    //将基类的方法重写成抽象方法
    override abstract fun isFun2()

    /**
     * Any类似于java的Object
     * 返回值是String，通过when作为表示式的形式直接返回出来
     */
    fun when2(obj: Any): String =
            when (obj) {
                1 -> "111"
                2 -> "222"
                is String -> obj
                else -> "UnKnow"
            }

    //for循环的用法
    fun for1() {
        val data = listOf("aaa", "bbb", "ccc", "ddd")
        for (abc in data) {
            //遍历data并赋值给abc(可自定义)
        }

        //从1-10遍历，一次增加2个长度
        for (x in 1..10 step 2) {
            //打印顺序为1、3、5、7、9
            Log.d(TAG, x.toString())
        }

        //从9-3遍历，一次增加3个长度
        for (x in 9 downTo 3 step 3) {
            Log.d(TAG, x.toString())
        }
    }

    //in的用法
    fun funIn() {
        val list = listOf("a", "b", "c")
        //-1不在0-2之前么？
        if (-1 !in 0..list.lastIndex) {
            Log.d(TAG, "-1不在区间内")
        }

        val x = 10
        val y = 9
        //x在1-10之间么？
        if (x in 1..y + 1) {
            Log.d(TAG, "在区间内")
        }
    }

    fun filter() {
        //类似于Rx的链式调用
        val fruits = listOf("banana", "avocado", "apple", "kiwi")
        fruits.filter { it.startsWith("a") }
                .sortedBy { it }
                .map { it.toUpperCase() }
                .forEach { Log.d(TAG, it) }
    }



}