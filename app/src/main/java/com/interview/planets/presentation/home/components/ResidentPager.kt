package com.interview.planets.presentation.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.interview.planets.R

// Assume you have a Resident data class with relevant properties (e.g., name, species, etc.)

@Composable
fun ResidentItem(count: Int) {
    Card(
        modifier = Modifier
            .height(80.dp)
            .width(80.dp)
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
        Column(
            Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.resident),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = count.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ResidentsPager(residentUrls: List<String?>?) {
    val state = rememberLazyListState()
    LazyRow(
        state = state,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
        modifier = Modifier.fillMaxWidth()
    ) {
        residentUrls?.let {
            itemsIndexed(residentUrls) { index, value ->
                ResidentItem(index)
            }
        }
    }
}
