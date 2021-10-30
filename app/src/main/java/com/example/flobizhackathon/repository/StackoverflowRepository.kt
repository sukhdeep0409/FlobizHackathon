package com.example.flobizhackathon.repository

import com.example.flobizhackathon.api.StackoverflowInstance
import com.example.flobizhackathon.models.TotalItems
import retrofit2.Response

class StackoverflowRepository {
    private val stackoverflowInstance = StackoverflowInstance.API

    suspend fun getResponse(): Response<TotalItems> {
        return stackoverflowInstance.getQuestions()
    }
}