package com.interview.planets.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interview.planets.data.models.Planet

@Preview
@Composable
fun PlanetCardItem(planetItem: Planet? = null, itemWidth: Double = 0.0) {
    planetItem?.let { planet ->
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.elevatedCardElevation()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = planet.name?.uppercase() ?: "NA",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    PlanetDetailItem("Rotation Period", planet.rotationPeriod ?: "NA", itemWidth)
                    PlanetDetailItem("Orbital Period", planet.orbitalPeriod ?: "NA", itemWidth)
                    PlanetDetailItem("Diameter", planet.diameter ?: "NA", itemWidth)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    PlanetDetailItem("Climate", planet.climate ?: "NA", itemWidth)
                    PlanetDetailItem("Gravity", planet.gravity ?: "NA", itemWidth)
                    PlanetDetailItem("Terrain", planet.terrain ?: "NA", itemWidth)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    PlanetDetailItem("Surface Water", planet.surfaceWater ?: "NA", itemWidth)
                    PlanetDetailItem("Population", planet.population ?: "NA", itemWidth)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Card(
                        modifier = Modifier
                            .weight(.4f)
                            .padding(4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardColors(
                            containerColor = Color.White,
                            contentColor = Color.Black,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.White
                        ),
                        elevation = CardDefaults.elevatedCardElevation()
                    ) {
                        Column(Modifier.padding(4.dp)) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = planet.residents?.size.toString(),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = "Residents",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .weight(.4f)
                            .padding(4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardColors(
                            containerColor = Color.White,
                            contentColor = Color.Black,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.White
                        ),
                        elevation = CardDefaults.elevatedCardElevation()
                    ) {
                        Column(Modifier.padding(4.dp)) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = planet.films?.size.toString(),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = "Films",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlanetDetailItem(label: String, value: String, itemWidth: Double) {

    Column(modifier = Modifier.width(itemWidth.dp)) {
        Text(
            text = label,
            color = Color.Gray,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = value,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}