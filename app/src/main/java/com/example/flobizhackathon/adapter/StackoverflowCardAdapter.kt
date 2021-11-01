package com.example.flobizhackathon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.flobizhackathon.databinding.CardTagsBinding
import com.example.flobizhackathon.models.TaggedCards

class StackoverflowCardAdapter:
RecyclerView.Adapter<StackoverflowCardAdapter.CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CardViewHolder(
        CardTagsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.binding.tags.text = differ.currentList[position]
    }

    override fun getItemCount() = differ.currentList.size

    class CardViewHolder
    constructor(val binding: CardTagsBinding):
    RecyclerView.ViewHolder(binding.root)

    private val differCallback = object: DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.length == newItem.length
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}