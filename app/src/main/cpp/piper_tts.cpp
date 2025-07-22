#include <jni.h>
#include <android/log.h>

extern "C"
JNIEXPORT void JNICALL
Java_com_froginc_voiceassistant_tts_PiperTTS_nativeSpeak(JNIEnv *env, jobject thiz, jstring text) {
    const char *nativeText = env->GetStringUTFChars(text, 0);
    __android_log_print(ANDROID_LOG_INFO, "PiperTTS", "TTS: %s", nativeText);
    env->ReleaseStringUTFChars(text, nativeText);
}