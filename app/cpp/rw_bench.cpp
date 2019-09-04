//
// Created by wvvw on 2019-07-26.
//

#include <jni.h>
#include <string>

#include "rw_bench.h"
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <time.h>

#define KB 1024
#define MB 1024*1024
#define GB 1024*1024*1024

string tmp_file_path = "test";

int io_size = 4 * KB;
int file_size = 1 * GB;
int cnt = file_size / io_size;

double bench_read_seq() {
    clock_t start, end;
    unsigned char *buf = (unsigned char*)malloc( sizeof(unsigned char)*io_size );

    FILE *fp = fopen(tmp_file_path.c_str(), "rt");

    start = clock();
    for (int i=cnt; i != 0; i--) {
        fread(buf, sizeof(unsigned char), sizeof(buf), fp);
    }
    end = clock();

    fclose(fp);
    free(buf);

    return (end - start)/CLOCKS_PER_SEC/KB;
};

double bench_write_seq() {
    clock_t start, end;
    unsigned char *buf = (unsigned char*)malloc( sizeof(unsigned char)*io_size );

    FILE *fp = fopen("test", "rt");

    start = clock();
    for (int i=cnt; i != 0; i--) {
        fwrite(buf, sizeof(unsigned char), sizeof(buf), fp);
    }
    end = clock();

    fclose(fp);
    free(buf);

    return (end - start)/CLOCKS_PER_SEC/KB;
};

extern "C" JNIEXPORT jstring JNICALL
Java_com_gp_1android_12019_bench_DispBenchActivity_seqRead(JNIEnv *env, jobject /* this */) {
    double res_val = bench_read_seq();
    string res = to_string(res_val) + " KB/s";

    return env->NewStringUTF(res.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_gp_1android_12019_bench_DispBenchActivity_seqWrite(JNIEnv *env, jobject /* this */) {
    printf("test\n");
    double res_val = bench_write_seq();
    string res = to_string(res_val) + " KB/s";

    return env->NewStringUTF(res.c_str());
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

extern "C" JNIEXPORT jstring JNICALL
Java_com_gp_1android_12019_bench_DispBenchActivity_gen_file(JNIEnv *env, jobject /* this */) {
    int tmp_file_size = 4*GB;
    int tmp_io_size = 4*KB;
    unsigned char *buf = (unsigned char *)malloc(sizeof(unsigned char *) * tmp_io_size);

    int cnt = tmp_file_size/sizeof(int);
    int *tmp = (int *)buf;
    for (int i = cnt; i != 0; i--) {
        int r = rand();
        tmp[i] = r;
    }

    FILE *fp = fopen(tmp_file_path.c_str(), "wt");

    cnt = file_size/io_size;
    for (int i=cnt; i != 0; i--) {
        fwrite(buf, sizeof(unsigned char), sizeof(buf), fp);
    }
}
