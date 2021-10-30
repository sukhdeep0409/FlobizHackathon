package com.example.flobizhackathon.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flobizhackathon.R
import com.example.flobizhackathon.adapter.StackoverflowAdapter
import com.example.flobizhackathon.databinding.ActivityMainBinding
import com.example.flobizhackathon.models.Item
import com.example.flobizhackathon.viewmodels.StackoverflowViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: StackoverflowViewModel
    private lateinit var binding: ActivityMainBinding

    private val stackoverflowAdapter = StackoverflowAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply { setContentView(root) }

        viewModel = ViewModelProvider(this).get(StackoverflowViewModel::class.java)
        viewModel.fetchStackoverflowArticles()

        setUpRecyclerView()

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.stackoverflowQuestions.observe(this, { item ->
            item?.let { stackoverflowAdapter.differ.submitList(it) }
        })
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = stackoverflowAdapter
        }
    }
}