package com.example.riusapp.backend.models

import java.util.Date

data class Comments (
    val _id: String,
    val contents: String,
    val date: Date,
    val postedBy: Users,
    val postedOn: String
)