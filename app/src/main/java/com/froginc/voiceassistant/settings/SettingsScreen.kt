package com.froginc.voiceassistant.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    modelName: String,
    systemPrompt: String,
    wakeWord: String,
    onModelNameChange: (String) -> Unit,
    onSystemPromptChange: (String) -> Unit,
    onWakeWordChange: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text("Settings", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = modelName, onValueChange = onModelNameChange,
            label = { Text("Ollama Model Name") }, modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = systemPrompt, onValueChange = onSystemPromptChange,
            label = { Text("System Prompt") }, modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = wakeWord, onValueChange = onWakeWordChange,
            label = { Text("Wake Word") }, modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(18.dp))
        Button(onClick = onDismiss, Modifier.fillMaxWidth()) { Text("Save & Back") }
    }
}