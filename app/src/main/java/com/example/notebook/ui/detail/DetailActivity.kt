package com.example.notebook.ui.detail

import android.app.framework.base.collectEffect
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : ComponentActivity() {
    private var noteId :Long? = null

    private val detailViewModel: DetailViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        noteId = intent.getLongExtra("noteId", -1)

        setContent {
            LaunchedEffect(Unit) {
                if (noteId != -1L && noteId != null)
                {
                    detailViewModel.loadNote(noteId!!)

                }
            }
            detailViewModel.collectEffect { effect ->
                when(effect)
                {
                    is DetailEffect.back -> finish()
                }
            }
            val detailUiState = detailViewModel.uiState.collectAsStateWithLifecycle()
            DetailScreen(
                detailUiState.value,
                detailViewModel::dispatch
            )
        }
    }
}