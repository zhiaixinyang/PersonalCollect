# 写在前面

> 因为历史遗留原因，这个项目是一个Module，一直也没有花功夫改成Project。因此如果看官也clone下来，那么就需要使用import module的方式，引入。

> 项目使用了ButterKnife，因此需要在根gradle中加上`classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'`

> 项目中使用了GreedDao，因此需要在根gradle中加上`classpath 'org.greenrobot:greendao-gradle-plugin:3.2.0'`

> 如果是AndroidStudio3.0的话，可以需要花一番功夫调整一下gradle。

> 此外此项目最初用的gradle很老，所以如果gradle使用2.3及以上需要调整一下gradle的依赖方式了。

> 祝你好运。

这个项目本质并非是一个实现特定功能的App，是我拆解了很多前辈大神们写的项目于一身的综合性的App。（大部分在注释中添加了原GitHub地址）

内部以自定义View为主，也涵盖一些常用的效果，动画...

目前一直在保持更新。
新版本增加了Kotlin相关笔记，记得在根gradle下加上`classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:你的Kotlin版本"`

效果简单展示：

![](https://github.com/zhiaixinyang/PersonalCollect/blob/master/asd.gif)

### 小Tips：

提高报错信息至1000行。（作用，我们在使用DataBinding的时候可能会发现，报错信息很多，但找不到问题所在。其实不是报错信息没出来，而是AS默认只打印100行错误日志。所以我们让AS多大几行，就可以看到了。）

```xml
//根目录下
allprojects {
    repositories {
        jcenter()
    }
    //提高报错信息至1000行
    gradle.projectsEvaluated {
        tasks.withType(JavaCompile) {
            options.compilerArgs << "-Xmaxerrs" << "1000"
        }
    }
}
```
