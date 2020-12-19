#ifndef KEEPALIVE_COMMON_H
#define KEEPALIVE_COMMON_H

#include <android/log.h>
#include <stdint.h>
#include <sys/types.h>

#define TAG        "phonix-native"
#define LOGW(...)    __android_log_print(ANDROID_LOG_WARN, TAG, __VA_ARGS__)
#define LOGE(...)    __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

#define LIB_DEBUG 1
#ifdef LIB_DEBUG
#define LOGI(...)    __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
//#define LOGD(...)    __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
//#define LOGD(format, ...)   __android_log_print(ANDROID_LOG_DEBUG, TAG, \
//                                    "%s : %s : %d ---> " format "%s", \
//                                    __FILE__, __FUNCTION__, __LINE__, \
//                                    ##__VA_ARGS__, "\n");
#define LOGD(format, ...)   __android_log_print(ANDROID_LOG_DEBUG, TAG, \
                                    "[%s] : %d ---> " format "%s", \
                                    __FUNCTION__, __LINE__, \
                                    ##__VA_ARGS__, "\n");
#else
#define LOGI(...)
#define LOGD(...)
#endif //~~~ LIB_DEBUG

#define JAVA_CLASS "com/keepalive/daemon/core/NativeKeepAlive"

#endif //KEEPALIVE_COMMON_H
