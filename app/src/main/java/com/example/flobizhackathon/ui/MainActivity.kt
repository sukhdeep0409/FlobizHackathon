package com.example.flobizhackathon.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flobizhackathon.R
import com.example.flobizhackathon.adapter.StackoverflowCardAdapter
import com.example.flobizhackathon.adapter.StackoverflowViewAdapter
import com.example.flobizhackathon.databinding.ActivityMainBinding
import com.example.flobizhackathon.models.Item
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

        binding.searchTagQuery.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun afterTextChanged(editable: Editable?) {
                filterTags(editable.toString())
            }
        })

        stackoverflowViewAdapter.setOnClickListener { item ->
            Intent(this, StackoverflowAnswerActivity::class.java).also { intent ->
                intent.putExtra("stackoverflow_question_link", item.link)
                startActivity(intent)
            }
        }

        binding.filter.setOnClickListener { tagsFilter() }
    }

    private fun tagsFilter() {
        val bottomSheet = BottomSheetDialog(this)
        bottomSheet.setContentView(R.layout.dialog_tags)
        bottomSheet.show()

        val dialogRecyclerView = bottomSheet.findViewById<RecyclerView>(R.id.recyclerview_tags)
        dialogRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = stackoverflowCardAdapter
        }

        stackoverflowCardAdapter.setOnClickListener {
            filterTags(it)
            bottomSheet.dismiss()
            binding.searchTagQuery.setText(it)
        }

        observeStackoverflowTagsViewModel()
    }

    private fun observeStackoverflowViewModel() {
        viewModel.stackoverflowQuestions.observe(this, {
            it?.let { stackoverflowViewAdapter.differ.submitList(it) }
            displayTheCounts(it)
        })
    }

    private fun observeStackoverflowTagsViewModel() {
        viewModel.stackoverflowViewTags.observe(this, {
            it?.let { stackoverflowCardAdapter.differ.submitList(it) }
        })
    }

    private fun observeStackoverflowSearchedTagsViewModel() {
        viewModel.stackoverflowSearchTags.observe(this, {
            it?.let { stackoverflowViewAdapter.differ.submitList(it) }
            displayTheCounts(it)
        })
    }

    fun filterTags(tags: String) {
        viewModel.fetchStackoverflowTags(tags)
        observeStackoverflowSearchedTagsViewModel()
    }

    private fun displayTheCounts(list: List<Item>?) {
        var averageViewCount = 0
        var averageAnswerCount = 0

        if(list?.isEmpty() == true) return

        list?.let { itemList ->
            for (item in itemList) {
                averageAnswerCount += item.answer_count
                averageViewCount += item.view_count
            }
            val len = itemList.size
            binding.averageAnswerCount.text = (averageAnswerCount / len).toString()
            binding.averageViewCount.text = (averageViewCount / len).toString()
        }


        Log.i("COUNTS_COUNTS", "$averageAnswerCount $averageViewCount")
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = stackoverflowViewAdapter
        }
    }
}