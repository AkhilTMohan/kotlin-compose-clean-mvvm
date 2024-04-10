package com.interview.planets.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interview.planets.data.models.Planet

@Preview
@Composable
fun PlanetItem(planet: Planet? = null) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 18.dp, horizontal = 8.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            text = planet?.name ?: ""
        )
        Text(
            modifier = Modifier
                .padding(vertical = 18.dp, horizontal = 8.dp)
                .fillMaxWidth(),
            text = planet?.climate ?: ""
        )
        Text(
            modifier = Modifier
                .padding(vertical = 18.dp, horizontal = 8.dp)
                .fillMaxWidth(),
            text = planet?.created ?: ""
        )
    }
    HorizontalDivider()
}