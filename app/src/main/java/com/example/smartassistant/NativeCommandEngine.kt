package com.example.smartassistant

data class ParsedCommand(
    val action: String,
    val confidence: Float
)

object NativeCommandEngine {
    init {
        System.loadLibrary("assistant-core")
    }

    external fun parseCommand(spokenText: String): ParsedCommand
}
