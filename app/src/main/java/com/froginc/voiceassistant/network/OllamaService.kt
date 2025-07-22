package com.froginc.voiceassistant.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface OllamaService {
    @POST("api/v1/stream")
    suspend fun requestLLM(@Body body: Map<String, String>): Map<String, Any>

    companion object {
        fun create(): OllamaService {
            return Retrofit.Builder()
                .baseUrl("http://10.0.2.2:11434/") // Use 10.0.2.2 for Android emulator, localhost for device
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OllamaService::class.java)
        }
        suspend fun requestLLM(model: String, prompt: String, system: String): String {
            val resp = create().requestLLM(mapOf("model" to model, "prompt" to prompt, "system" to system))
            return resp["text"] as? String ?: "No response"
        }
    }
}