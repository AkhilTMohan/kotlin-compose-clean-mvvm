package com.interview.planets.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.paging.compose.collectAsLazyPagingItems
import com.interview.planets.presentation.theme.PlanetsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val uiState by mainViewModel.uiState.collectAsState()
            PlanetsTheme {
                BaseUI(uiState,
                    updateBaseUI = {
                        mainViewModel.updateBaseUIState(it)
                    }, getPlanets = {
                        mainViewModel.getPlanets()
                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        /***/
        mainViewModel.getPlanets()
    }
}