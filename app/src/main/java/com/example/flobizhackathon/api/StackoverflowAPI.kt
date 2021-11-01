package com.example.flobizhackathon.api

import com.example.flobizhackathon.models.TotalItems
import com.example.flobizhackathon.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StackoverflowAPI {

    @GET("2.2/questions")
    suspend fun getQuestions(
        @Query("key") key: String = API_KEY,
        @Query("order") order: String = "desc",
        @Query("sort") sort: String = "activity",
        @Query("site") site: String = "stackoverflow"
    ): Response<TotalItems>

    @GET("2.2/questions")
    suspend fun searchTags(
        @Query("key") key: String = API_KEY,
        @Query("order") order: String = "desc",
        @Query("sort") sort: String = "activity",
        @Query("tagged") tagged: String,
        @Query("site") site: String = "stackoverflow"
    ): Response<TotalItems>
}