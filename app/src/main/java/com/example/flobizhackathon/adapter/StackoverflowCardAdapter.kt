package com.example.flobizhackathon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.flobizhackathon.databinding.CardTagsBinding

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
        val tag = differ.currentList[position]
        holder.binding.tags.text = tag

        holder.binding.tags.setOnClickListener {
            onItemClickListener?.let { it(tag) }
        }
    }

    override fun getItemCount() = differ.currentList.size

    class CardViewHolder
    constructor(val binding: CardTagsBinding):
    RecyclerView.ViewHolder(binding.root)

    //item click listener
    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    //differ callback
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