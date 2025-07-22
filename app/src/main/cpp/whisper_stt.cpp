#include <jni.h>
#include <android/log.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_froginc_voiceassistant_stt_WhisperSTT_nativeRecognize(JNIEnv *env, jobject thiz) {
    __android_log_print(ANDROID_LOG_INFO, "WhisperSTT", "STT simulated.");
    return env->NewStringUTF("Simulated speech to text result.");
}