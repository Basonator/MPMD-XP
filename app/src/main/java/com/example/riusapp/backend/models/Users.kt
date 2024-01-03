package com.example.riusapp.backend.models

data class Users (
    val _id: String,
    val username: String,
    val password: String,
    val email: String,
    val liked: List<Any>,
    val reported: List<Any>,
    val posts: Int,
    val rating: Double,
    val reports: Int,
    val verified: Boolean,
    val banned: Boolean,
)

data class LoginRequest(
    val username: String,
    val password: String
)