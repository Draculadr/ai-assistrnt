cmake_minimum_required(VERSION 3.22.1)
project("assistant-core")

add_library(assistant-core SHARED assistant_core.cpp)

find_library(log-lib log)
target_link_libraries(assistant-core ${log-lib})
