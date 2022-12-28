package com.example.aairastation.core

import java.text.DecimalFormat

fun Double.formatPriceToRM() = "RM ${formatTo2dp()}"
fun Double.formatTo2dp(): String = DecimalFormat("#,##0.00").format(this)
