package com.example.note_glance_widget.root

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import com.example.note_glance_widget.R
import com.example.note_glance_widget.widget.NOTE_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                RootScreen(intent?.getLongExtra(NOTE_ID, Long.MIN_VALUE) ?: Long.MIN_VALUE)
            }
        }
    }
}