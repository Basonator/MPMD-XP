package com.example.riusapp.backend

import com.example.riusapp.backend.models.Comments
import com.example.riusapp.backend.models.LoginRequest
import com.example.riusapp.backend.models.Posts
import com.example.riusapp.backend.models.Users
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    //  users
    @POST("users/loginAdmin")
    suspend fun login(@Body loginRequest: LoginRequest): Response<Users>

    @GET("users/leaderboard")
    suspend fun getUsers(): Response<List<Users>>

    @GET("users/verify/{id}")
    suspend fun verifyUser(@Path("id") userId: String): Response<Users>

    @GET("users/ban/{id}")
    suspend fun banUser(@Path("id") userId: String): Response<Users>

    //  posts
    @GET("posts")
    suspend fun getPosts(): Response<List<Posts>>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") postId: String): Response<Posts>

    @GET("posts/user/{id}")
    suspend fun getPostsByUser(@Path("id") userId: String): Response<List<Posts>>

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") postId: String): Response<Void>

    //  comments
    @GET("comments/photo/{id}")
    suspend fun getCommentsByPost(@Path("id") postId: String): Response<List<Comments>>

    @DELETE("comments/{id}")
    suspend fun deleteComment(@Path("id") commentId: String): Response<Void>
}