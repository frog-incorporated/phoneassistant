package com.froginc.voiceassistant.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ToolCallService {
    @POST("api/tool")
    suspend fun callTool(@Body body: Map<String, String>): Map<String, Any>

    companion object {
        fun create(): ToolCallService {
            return Retrofit.Builder()
                .baseUrl("http://192.168.1.100/") // Pico W IP
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ToolCallService::class.java)
        }
        suspend fun callTool(command: String) {
            try {
                create().callTool(mapOf("command" to command))
            } catch (_: Exception) {}
        }
    }
}