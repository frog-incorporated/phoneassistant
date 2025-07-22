package com.froginc.voiceassistant.stt

object WhisperSTT {
    fun init() { System.loadLibrary("whisper_stt") }
    external fun nativeRecognize(): String
    fun recognize(): String = nativeRecognize()
}