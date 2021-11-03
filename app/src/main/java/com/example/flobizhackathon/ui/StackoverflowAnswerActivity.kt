package com.example.flobizhackathon.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.webkit.WebViewClient
import com.example.flobizhackathon.R
import com.example.flobizhackathon.databinding.ActivityStackoverflowAnswerBinding
import com.example.flobizhackathon.models.Item

class StackoverflowAnswerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStackoverflowAnswerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStackoverflowAnswerBinding.inflate(layoutInflater)

        supportActionBar?.hide()

        val stackoverflowQuestionLink = intent.getStringExtra("stackoverflow_question_link")

        Log.i("STACKOVERFLOW_QUESTION_LINK", stackoverflowQuestionLink!!)

        binding.webView.apply {
            settings.javaScriptEnabled
            settings.loadWithOverviewMode
            settings.useWideViewPort
            settings.domStorageEnabled
            settings.userAgentString
            settings.allowFileAccess
            settings.allowContentAccess
            webViewClient = WebViewClient()
            loadUrl(stackoverflowQuestionLink)
        }
    }
}