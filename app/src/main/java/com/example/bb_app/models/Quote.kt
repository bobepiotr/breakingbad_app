package com.example.bb_app.models

import java.io.Serializable

data class Quote(
    val quote_id: Int,
    val quote: String,
    val author: String,
    val series: String
): Serializable {}