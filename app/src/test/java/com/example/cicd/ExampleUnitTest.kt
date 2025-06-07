package com.example.cicd

import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun subtraction_isCorrect() {
        assertEquals(2, 4 - 2)
    }

    @Test
    fun multiplication_isCorrect() {
        assertEquals(6, 2 * 3)
    }

    @Test
    fun division_isCorrect() {
        assertEquals(2, 6 / 3)
    }

    @Test
    fun string_concatenation_works() {
        val result = "CI" + "/" + "CD"
        assertEquals("CI/CD", result)
    }

    @Test
    fun string_length_isCorrect() {
        assertEquals(5, "Hello".length)
    }
}

