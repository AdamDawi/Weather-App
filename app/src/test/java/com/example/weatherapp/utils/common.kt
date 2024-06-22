package com.example.weatherapp.utils

import com.example.weatherapp.common.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.fail

fun <T> assertResourceListsEquals(expected: List<Resource<T>>, actual: List<Resource<T>>) {
    assertEquals( "Size mismatch: expected ${expected.size} but got ${actual.size}", expected.size, actual.size)

    expected.zip(actual).forEachIndexed { index, (exp, act) ->
        when {
            exp is Resource.Loading && act is Resource.Loading -> {
                assertEquals( "Data mismatch at index $index: expected Loading data ${exp.data} but got ${act.data}", exp.data, act.data)
            }
            exp is Resource.Success && act is Resource.Success -> {
                assertEquals("Data mismatch at index $index: expected Success data ${exp.data} but got ${act.data}", exp.data, act.data)
            }
            exp is Resource.Error && act is Resource.Error -> {
                assertEquals("Data mismatch at index $index: expected Error data ${exp.data} but got ${act.data}", exp.data, act.data)
                assertEquals("Message mismatch at index $index: expected Error message ${exp.message} but got ${act.message}", exp.message, act.message)
            }
            else -> {
                fail("Type mismatch at index $index: expected $exp but got $act")
            }
        }
    }
}

