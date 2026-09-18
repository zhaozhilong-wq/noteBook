package com.example.notebook.ui.main

import android.app.framework.base.collectEffect
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notebook.ui.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )



        setContent {

            mainViewModel.collectEffect {effect ->
                when(effect){
                    is MainEffect.navigateToDetail -> {
                        startActivity(Intent(this, DetailActivity::class.java)
                            .apply { putExtra("noteId", effect.noteId) })

                    }

                    MainEffect.showToast->{
                        Toast.makeText(this, "分组名已存在", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            val mainUiState = mainViewModel.uiState.collectAsStateWithLifecycle()
            LaunchedEffect(Unit) {
                mainViewModel.dispatch(MainEvent.selectGroup(mainUiState.value.selectedGroup))
            }
            MainScreen(
                mainUiState.value,
                mainViewModel::dispatch
            )
        }

    }
}