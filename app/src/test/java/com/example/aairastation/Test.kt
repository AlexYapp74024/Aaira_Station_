package com.example.aairastation

import org.junit.Test

class Test {

    @Test
    fun test() {
        val bools = listOf(true, true, true, true, false)
        val ands = bools.fold(true) { acc, bool ->
            acc && bool
        }

        val ors = bools.fold(true) { acc, bool ->
            acc || bool
        }

        println()
    }
}