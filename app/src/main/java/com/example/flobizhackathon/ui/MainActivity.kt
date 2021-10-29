package com.example.flobizhackathon.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.flobizhackathon.R
import com.example.flobizhackathon.viewmodels.StackoverflowViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: StackoverflowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get()

        viewModel.fetchStackoverflowArticles()
    }
}