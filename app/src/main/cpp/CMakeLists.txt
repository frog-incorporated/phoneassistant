cmake_minimum_required(VERSION 3.10.2)
project("voice_llm_assistant")

add_library(piper_tts SHARED piper_tts.cpp)
add_library(whisper_stt SHARED whisper_stt.cpp)

find_library(log-lib log)

target_link_libraries(piper_tts ${log-lib})
target_link_libraries(whisper_stt ${log-lib})