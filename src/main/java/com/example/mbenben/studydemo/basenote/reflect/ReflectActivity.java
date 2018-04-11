package com.example.mbenben.studydemo.basenote.reflect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.utils.log.LogUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by MDove on 2018/4/11.
 */

public class ReflectActivity extends BaseActivity {
    public static final String TAG = "ReflectActivity";
    private static final String EXTRA_ACTION = "extra_action";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, ReflectActivity.class);
        intent.putExtra(EXTRA_ACTION, title);
        context.startActivity(intent);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(EXTRA_ACTION));
        setContentView(R.layout.activity_reflect);
        fun();
    }

    public void fun() {
        //第一种方式获取Class对象：产生一个Student对象的实例，以及一个唯一的Class对象。
        ReflectModel model1 = new ReflectModel();
        //获取Class对象：这种方式没什么意义，既然有了对象的实例，何必再去反射
        Class modelClass = model1.getClass();

        //第二种方式获取Class对象：需要我们导包，但是有些时候这个类是隐藏的（比如很多系统不稳定的类，@Hide）
        Class modelClass2 = ReflectModel.class;

        Class modelClass3 = null;
        try {
            //第三种方式获取Class对象：类的全路径
            modelClass3 = Class.forName("com.example.mbenben.studydemo.basenote.reflect.ReflectModel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //开始进行具体内部操作，此时我们仅仅是拿到了ReflectModel的class这个对象，而非是这个ReflectModel对象的实例
        if (modelClass3 != null) {
            //获取ReflectModel.class对应的所有构造方法(public的)
            Constructor[] constructors = modelClass3.getConstructors();
            for (Constructor constructor : constructors) {
                LogUtils.d(TAG, "当前获取的构造方法：" + constructor.getName());
            }
            LogUtils.d(TAG, "----以上是public的----");
            //所有构造方法(包括：私有、受保护、默认、公有)
            constructors = modelClass3.getDeclaredConstructors();
            for (Constructor constructor : constructors) {
                LogUtils.d(TAG, "当前获取的构造方法：" + constructor);
            }

            Object object = null;
            try {
                // 获取共有无参构造函数
                Constructor con = modelClass3.getConstructor(null);
                // 调用此无参构造方法，那么此时我们就会获取到Reflect的实例对象了(默认返回object)
                ReflectModel reflectModel = (ReflectModel) con.newInstance();
                // 有了ReflectModel对象实例，我们就可以正常使用了
                /**
                 * 但是此时我们知道，我们强制类型转成了我们想要的类型，但是我们上文中提到，有些类是hide的。
                 * 因此我们很多情况下，我们被限制只能得到object对象。
                 */
                reflectModel.fun();
                // 获取私有的含有String和int参数的构造方法
                con = modelClass3.getDeclaredConstructor(new Class[]{String.class, int.class});
                // 此时我们获取到了这个private的含参构造方法对象，但是因为private权限原因，我们没办法直接调用newInstance()
                // 我们需要调用下面的方法，无视private修饰符（调用后，我们就可以执行private的构造方法）
                con.setAccessible(true);
                //调用私有俩参构造方法
                object = con.newInstance("B", 2);


                /**
                 * 反射调用方法
                 */
                //上诉提到，如果我们反射的类是hide，此时我们肯定没办法把Object转成ReflectModel类型。因此，我们拿到这个实例后想要调用其方法，还需要使用反射的方式
                // private方法应该使用getDeclaredMethod()去获取
                Method printContent = modelClass3.getDeclaredMethod("printContent", new Class[]{});
                //同样因为private的原因，我们在执行方法时，要先清除权限问题
                printContent.setAccessible(true);
                //使用object实例对象，调用printContent方法，因为没有参数，所以传null
                printContent.invoke(object, null);
                //生成名为setContent的含有一个String参数的方法对象
                Method setContent = modelClass3.getDeclaredMethod("setContent", new Class[]{String.class});
                setContent.setAccessible(true);
                //调用setContent方法，去改变mContent的值
                setContent.invoke(object, "C");
                //再次执行打印操作
                printContent.invoke(object, null);

                //调用静态方法
                Method staticMethod = modelClass3.getMethod("staticFun", new Class[]{});
                //因为静态方法属于类，所以我们不需要传实例对象，因为它在class被加载的时候，就已经被创建了。
                staticMethod.invoke(null, null);


                /**
                 * 反射调用变量
                 */
                //获取所有public变量封装的Field对象
                Field[] fields = modelClass3.getFields();
                //获取所有变量封装的Field对象
                fields = modelClass3.getDeclaredFields();

                //获取private的mContent的变量
                Field content = modelClass3.getDeclaredField("mContent");
                content.setAccessible(true);
                Field num = modelClass3.getField("mNum");
                num.set(object, 3);
                //获取static变量
                Field sNum = modelClass3.getField("sNum");
                LogUtils.d(TAG, "mNum在object实例对象中的值:" + num.get(object) + "-static变量sNum的值:" + sNum.get(null));
                //将object实例的mContent对象，设置为D
                content.set(object, "D");
                printContent.invoke(object, null);

                Field reflectBean = modelClass3.getDeclaredField("mReflectBean");
                Class reflectBeanClass = Class.forName("com.example.mbenben.studydemo.basenote.reflect.ReflectBean");
                Constructor beanCon = reflectBeanClass.getConstructor(new Class[]{String.class});
                Object beanObject = beanCon.newInstance("b");
                //给mReflectBean赋值
                reflectBean.setAccessible(true);
                reflectBean.set(object, beanObject);
                Method printBean = modelClass3.getMethod("printBean", new Class[]{});
                printBean.invoke(object, null);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
