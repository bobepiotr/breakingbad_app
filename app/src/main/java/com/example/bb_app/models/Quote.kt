package com.example.bb_app.models

import java.io.Serializable

data class Quote(
    val quote_id: Int,
    val quote: String,
    var author: String,
    val series: String
): Serializable {
    //Names of characters from /quote/random endpoint doesn't match with ones from /characters endpoint from which I'm downloading photos
    fun fixQuoteAuthor() {
        author = when {
            author == "Chuck McGill" -> {"Charles McGill"}
            author == "Jimmy McGill" -> {"Saul Goodman"}
            author == "Hank Schrader" -> {"Henry Schrader"}
            author == "Gus Fring" -> {"Gustavo Fring"}
            author == "Kim Wexler" -> {"Kimberly Wexler"}
            else -> author
        }
    }
}