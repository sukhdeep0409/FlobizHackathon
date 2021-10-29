package com.example.flobizhackathon.models

data class TotalItems(
    val has_more: Boolean,
    val items: List<Item>,
    val quota_max: Int,
    val quota_remaining: Int
)