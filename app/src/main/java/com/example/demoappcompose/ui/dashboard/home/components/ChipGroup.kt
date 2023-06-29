package com.example.demoappcompose.ui.dashboard.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.demoappcompose.data.responses.dashboard_response.MediumData
import com.example.demoappcompose.ui.HorizontalSpacer

@Composable
fun ChipGroup(
    mediums: List<MediumData>,
    selectedMedium: MediumData,
    onSelectedChanged: (MediumData) -> Unit,
) {
    Column(modifier = Modifier.padding(8.dp)) {
        LazyRow(
        ) {
            items(mediums) {
                Row {
                    CustomChip(
                        name = it.mediumName,
                        isSelected = selectedMedium.id == it.id,
                        onSelectionChanged = {
                            onSelectedChanged(it)
                        },
                    )
                    HorizontalSpacer(size = 8)
                }
            }
        }
    }
}