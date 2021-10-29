package com.example.flobizhackathon.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.flobizhackathon.repository.StackoverflowRepository
import kotlinx.coroutines.*

class StackoverflowViewModel
constructor(application: Application):
AndroidViewModel(application) {

    private var job: Job? = null

    fun fetchStackoverflowArticles() {
        displayStackoverflowArticles()
    }

    private fun displayStackoverflowArticles() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = StackoverflowRepository().getResponse()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.i("@@@@@", response.body().toString())
                }
            }
        }
    }
}