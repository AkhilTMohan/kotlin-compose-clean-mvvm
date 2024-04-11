package com.interview.planets.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
                    updateBaseUIState = {
                        mainViewModel.updateBaseUIState(it)
                    },
                    fetchPlanetDataFromServer = {
                        mainViewModel.fetchPlanetDataFromServer(it)

                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        /** Always calling the list of planets on on resume. This will always retrieve fresh data
         *  from the server asynchronously. I am not going with a viewmodel for a composable approach
         *  because it will again time consuming for displaying the data. */
        mainViewModel.getPlanets()
    }
}