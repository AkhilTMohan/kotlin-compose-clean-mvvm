package com.interview.planets.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.interview.planets.presentation.theme.PlanetsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel: MainViewModel by viewModels()
        val uiState = mainViewModel.uiState
        setContent {
            PlanetsTheme {
                BaseUI(uiState){
                    mainViewModel.updateBaseUIState(it)
                }
            }
        }
    }
}