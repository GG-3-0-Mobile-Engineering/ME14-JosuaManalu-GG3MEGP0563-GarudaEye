package com.yosha10.final_project.core.utils

import org.junit.Assert.*
import org.junit.Test

class DateFormatterTest {
    @Test
    fun `use formatDate when given date string should return formatted date string`()  {
        val input = "2023-08-14T07:09:10.538Z"
        val expectedOutput = "14 Aug 2023 | 14:09"
        val result = DateFormatter.formatDate(input)
        assertEquals(expectedOutput, result)
    }
}