package com.froginc.voiceassistant.tts

object PiperTTS {
    fun init() { System.loadLibrary("piper_tts") }
    external fun nativeSpeak(text: String)
    fun speak(text: String) { nativeSpeak(text) }
}