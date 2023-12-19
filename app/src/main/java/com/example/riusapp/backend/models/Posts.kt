package com.example.riusapp.backend.models

import java.util.Date

data class Posts (
    val _id: String,
    val name: String,
    val description: String,
    val tags: List<String>,
    val link: String,
    val postedBy: Users,
    val views: Int,
    val rating: Double,
    val reports: Int,
    val inappropriate: Boolean,
    val numberOfRatings: Int,
    val date: Date,
    val location: Any
)