package com.example.snycdata

data class TrasactionDetails(val name: String, val timestamp: String)

fun createObjects(): List<TrasactionDetails> {
    return listOf(
        TrasactionDetails("Object 1", "Time: ${System.currentTimeMillis()}"),
        TrasactionDetails("Object 2", "Time: ${System.currentTimeMillis()}"),
        TrasactionDetails("Object 3", "Time: ${System.currentTimeMillis()}"),
        TrasactionDetails("Object 4", "Time: ${System.currentTimeMillis()}")
    )
}