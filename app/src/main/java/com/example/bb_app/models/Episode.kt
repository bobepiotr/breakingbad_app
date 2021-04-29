package com.example.bb_app.models

import java.io.Serializable

data class Episode(
        val episode_id: Int,
        val title: String,
        val season: String,
        val air_data: String,
        val characters: List<String>,
        val episode: String,
        val series: String
): Serializable {}