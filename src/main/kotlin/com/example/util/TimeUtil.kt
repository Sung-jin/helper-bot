package com.example.util

class TimeUtil private constructor() {}

fun Long.secondToMMSSFormat(): String {
    return "${this / 60}:${(this % 60).toString().padStart(2, '0')}"
}
