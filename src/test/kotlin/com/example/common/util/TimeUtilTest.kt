package com.example.common.util

import com.example.util.secondToMMSSFormat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TimeUtilTest {
    @Test
    fun `초에서 mmss 변환은 정상 동작되어야 한다`() {
        assertEquals(1800L.secondToMMSSFormat(), "30:00")
        assertEquals(333L.secondToMMSSFormat(), "5:33")
    }
}