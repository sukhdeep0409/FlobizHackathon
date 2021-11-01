package com.example.flobizhackathon.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flobizhackathon.models.Item
import com.example.flobizhackathon.repository.StackoverflowRepository
import kotlinx.coroutines.*

class StackoverflowViewModel: ViewModel() {
    private var job: Job? = null

    val stackoverflowQuestions: MutableLiveData<List<Item>> = MutableLiveData()
    val stackoverflowSearchTags: MutableLiveData<List<Item>> = MutableLiveData()
    val stackoverflowViewTags: MutableLiveData<List<String>> = MutableLiveData()

    fun fetchStackoverflowArticles() { displayStackoverflowArticles() }

    fun fetchStackoverflowTags(tag: String) { displayStackoverflowSearchTags(tag) }

    private fun displayStackoverflowArticles() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = StackoverflowRepository().getResponse()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    stackoverflowQuestions.value = response.body()?.items
                    stackoverflowViewTags.value = response.body()!!.items[0].tags
                }
            }
        }
    }

    private fun displayStackoverflowSearchTags(tag: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = StackoverflowRepository().searchTags(tag)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    stackoverflowSearchTags.value = response.body()?.items
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}