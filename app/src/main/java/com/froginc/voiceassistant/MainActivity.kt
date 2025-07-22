package com.froginc.voiceassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.froginc.voiceassistant.stt.WhisperSTT
import com.froginc.voiceassistant.tts.PiperTTS
import com.froginc.voiceassistant.network.OllamaService
import com.froginc.voiceassistant.network.ToolCallService
import com.froginc.voiceassistant.settings.SettingsScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WhisperSTT.init()
        PiperTTS.init()
        setContent { VoiceAssistantApp() }
    }

    @Composable
    fun VoiceAssistantApp() {
        var showSettings by remember { mutableStateOf(false) }
        var conversation by remember { mutableStateOf(listOf<String>()) }
        var modelName by remember { mutableStateOf("llama3") }
        var systemPrompt by remember { mutableStateOf("You are a helpful AI.") }
        var wakeWord by remember { mutableStateOf("Hey Assistant") }
        val scope = rememberCoroutineScope()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Voice LLM Assistant") },
                    actions = {
                        IconButton(onClick = { showSettings = true }) {
                            Icon(Icons.Filled.Settings, contentDescription = "Settings")
                        }
                    }
                )
            }
        ) { padding ->
            if (showSettings) {
                SettingsScreen(
                    modelName, systemPrompt, wakeWord,
                    onModelNameChange = { modelName = it },
                    onSystemPromptChange = { systemPrompt = it },
                    onWakeWordChange = { wakeWord = it },
                    onDismiss = { showSettings = false }
                )
            } else {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    LazyColumn(
                        Modifier.weight(1f).fillMaxWidth().padding(8.dp)
                    ) {
                        items(conversation) { msg -> Text(msg, Modifier.padding(4.dp)) }
                    }
                    Row(Modifier.fillMaxWidth().padding(8.dp), Arrangement.SpaceEvenly) {
                        Button(onClick = {
                            conversation = conversation + "Listening for wake word: $wakeWord"
                            scope.launch {
                                val transcript = WhisperSTT.recognize()
                                conversation = conversation + "You: $transcript"
                                val response = OllamaService.create().requestLLM(modelName, transcript, systemPrompt)
                                conversation = conversation + "LLM: $response"
                                PiperTTS.speak(response)
                                ToolCallService.create().callTool(response)
                            }
                        }) { Text("Start Listening") }
                        Button(onClick = {
                            conversation = conversation + "Conversation ended."
                        }) { Text("End Conversation") }
                    }
                }
            }
        }
    }
}