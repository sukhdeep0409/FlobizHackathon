package com.example.flobizhackathon.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flobizhackathon.models.Item
import com.example.flobizhackathon.repository.StackoverflowRepository
import kotlinx.coroutines.*

class StackoverflowViewModel: ViewModel() {
    private var job: Job? = null

    val stackoverflowQuestions: MutableLiveData<List<Item>> = MutableLiveData()

    fun fetchStackoverflowArticles() { displayStackoverflowArticles() }

    private fun displayStackoverflowArticles() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = StackoverflowRepository().getResponse()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    stackoverflowQuestions.value = response.body()?.items
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}