# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/rei_m/android-sdk/tools/proguard/proguard-android.txt
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

-keepattributes SourceFile,LineNumberTable,Signature,Exceptions,*Annotation*,*Table
-keepnames class * extends java.lang.Throwable

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep class **.R$*

# Android Support Library
-dontwarn android.support.**
-dontwarn android.databinding.**
-dontwarn android.arch.**
-keep class android.support.** { *; }
-keep class android.databinding.** { *; }
-keep class android.arch.** { *; }

-keep class com.crashlytics.** { *; }
-keep class com.google.firebase.*.* { *; }

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-dontwarn org.antlr.**
-dontwarn okio.**
-dontwarn java.lang.invoke.**
-dontwarn javax.annotation.**

-dontnote android.**
-dontnote com.google.**
-dontnote org.antlr.**
-dontnote org.apache.**

-dontwarn com.google.errorprone.annotations.*

-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}
-keep @com.squareup.moshi.JsonQualifier interface *
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-dontwarn kotlin.reflect.jvm.internal.**
-keep class kotlin.reflect.jvm.internal.** { *; }
# Must keep all model fields and constructors
-keepclassmembers class me.rei_m.hyakuninisshu.infrastructure.database.KarutaSchema {
  <init>(...);
  <fields>;
}
-keepnames @com.squareup.moshi.JsonClass class *
