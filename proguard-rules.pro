# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\SDK_/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile, LineNumberTable
-keepattributes JavascriptInterface
-dontobfuscate

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
    native <methods>;
}
-ignorewarnings
#移除log代码
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
    public static boolean isLoggable(java.lang.String, int);
}

-assumenosideeffects class * extends java.lang.Throwable {
    public void printStackTrace();
}

-keep class * implements java.io.Serializable {
    *;
}

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-dontwarn okhttp3.**
-dontwarn okio.**

-keep class rx.**{*;}
-keep class okhttps.**{*;}
-keep class okio.**{*;}
-keep class retrofit2.**{*;}
-keep class android.support.** {*;}
-keep class com.google.** {*;}
-keep class com.google.gson.**
-keep interface com.google.gson.**
-keep class android.os.**{*;}

#如果开启混淆，model下的类，我们肯定也要keep，不然混淆之后gson之类的框架是没办法解析到的