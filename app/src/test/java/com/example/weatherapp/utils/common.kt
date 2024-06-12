package com.example.weatherapp.utils

import com.example.weatherapp.common.Resource

fun <T> compareResourceLists(expected: List<Resource<T>>, actual: List<Resource<T>>): Boolean {
    if (expected.size != actual.size) return false
    return expected.zip(actual).all { (exp, act) ->
        when {
            exp is Resource.Loading && act is Resource.Loading -> exp.data == act.data
            exp is Resource.Success && act is Resource.Success -> exp.data == act.data
            exp is Resource.Error && act is Resource.Error -> exp.data == act.data && exp.message == act.message
            else -> false
        }
    }
}