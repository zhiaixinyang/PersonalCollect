# 感谢Star

> 增加了最新的Apk文件

# 如果您需要导入工程到AndroidStudio当中，请注意：

> 因为历遗留原因，这个项目是一个module，需要使用import module方式在现有的project中导入

> 因为使用了一些库，请在project.gradle中增加：classpath'com.neenbedankt.gradle.plugins:android-apt:1.8'

> 内部使用了constraint-layout布局，请在app.gradle中改换成您本地拥有的版本

这个项目包含了作者遇到的一些大神们写效果，动画等等，也包含了我自己写的一些小Demo。

并且尽最大的能力拆解这些项目并整合，基本上没有依赖一些不明觉厉的库。

如果需要查看对应源库的源码，在对应包中的注释中也添加了原作者的项目GitHub地址。

---

### 使用指南
fragment包下对应了主页的各个Fragment，每个Fragment内部，包含各个开源库的Activity。
感觉某个效果有用，可以直接在Framgment中找到对应的Activity，就可以定位到这个库的源码了。

简单的演示效果：

![](./asd.gif)
