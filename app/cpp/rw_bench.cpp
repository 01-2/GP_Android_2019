//
// Created by wvvw on 2019-07-26.
//

#include <jni.h>
#include <string>

#include "rw_bench.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_gp_1android_12019_bench_DispBenchActivity_seqRead(JNIEnv *env, jobject /* this */) {
    std::string res = " B/s";
    return env->NewStringUTF(res.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_gp_1android_12019_bench_DispBenchActivity_seqWrite(JNIEnv *env, jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_gp_1android_12019_bench_DispBenchActivity_ranRead(JNIEnv *env, jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_gp_1android_12019_bench_DispBenchActivity_ranWrite(JNIEnv *env, jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_gp_1android_12019_bench_DispBenchActivity_dbInsert(JNIEnv *env, jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_gp_1android_12019_bench_DispBenchActivity_dbUpdate(JNIEnv *env, jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_gp_1android_12019_bench_DispBenchActivity_dbDelete(JNIEnv *env, jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


