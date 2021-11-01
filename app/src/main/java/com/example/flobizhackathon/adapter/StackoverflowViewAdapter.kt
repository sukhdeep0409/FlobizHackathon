package com.example.flobizhackathon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.flobizhackathon.databinding.StackoverflowCardBinding
import com.example.flobizhackathon.models.Item
import com.example.flobizhackathon.utils.loadImage

class StackoverflowViewAdapter:
RecyclerView.Adapter<StackoverflowViewAdapter.StackoverflowViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StackoverflowViewHolder(
        StackoverflowCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: StackoverflowViewHolder, position: Int) {
        val currentQuestion = differ.currentList[position]
        val currentView = holder.binding

        currentView.displayName.text = currentQuestion.owner.display_name
        currentView.title.text = currentQuestion.title
        currentView.answerCount.text = currentQuestion.answer_count.toString()
        currentView.profile.loadImage(currentQuestion.owner.profile_image)
    }

    override fun getItemCount() = differ.currentList.size

    class StackoverflowViewHolder
    constructor(val binding: StackoverflowCardBinding):
    RecyclerView.ViewHolder(binding.root)

    private val differCallback = object: DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.question_id == newItem.question_id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}