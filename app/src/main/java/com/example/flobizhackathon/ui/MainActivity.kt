package com.example.flobizhackathon.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flobizhackathon.R
import com.example.flobizhackathon.adapter.StackoverflowCardAdapter
import com.example.flobizhackathon.adapter.StackoverflowViewAdapter
import com.example.flobizhackathon.databinding.ActivityMainBinding
import com.example.flobizhackathon.viewmodels.StackoverflowViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: StackoverflowViewModel
    private lateinit var binding: ActivityMainBinding

    private val stackoverflowViewAdapter = StackoverflowViewAdapter()
    private val stackoverflowCardAdapter = StackoverflowCardAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply { setContentView(root) }

        viewModel = ViewModelProvider(this).get(StackoverflowViewModel::class.java)
        viewModel.fetchStackoverflowArticles()

        setUpRecyclerView()

        observeStackoverflowViewModel()

        binding.filter.setOnClickListener {
            val bottomSheet = BottomSheetDialog(this)
            bottomSheet.setContentView(R.layout.dialog_tags)
            bottomSheet.show()

            val dialogRecyclerView = bottomSheet.findViewById<RecyclerView>(R.id.recyclerview_tags)
            dialogRecyclerView?.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = stackoverflowCardAdapter
            }

            observeStackoverflowTagsViewModel()
        }
    }

    private fun observeStackoverflowViewModel() {
        viewModel.stackoverflowQuestions.observe(this, { item ->
            item?.let { stackoverflowViewAdapter.differ.submitList(it) }
        })
    }

    private fun observeStackoverflowTagsViewModel() {
        viewModel.stackoverflowViewTags.observe(this, { item ->
            item?.let { stackoverflowCardAdapter.differ.submitList(it) }
        })
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = stackoverflowViewAdapter
        }
    }
}