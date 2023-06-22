package com.example.demoappcompose.ui.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt


@Composable
fun SwipeToDeleteScreen() {
    val notesList = remember { mutableStateListOf<NotesItem>() }

    for (i in 1..10) {
        notesList.add(NotesItem(id = i, title = "Title $i"))
    }

    LazyColumn {
        items(notesList, { notesList: NotesItem -> notesList.id }) { item ->
            SwipeToDeleteItem(
                item = item,
                onSwipeToDelete = { notesList.remove(item) }
            )
        }
    }
}


@Composable
fun SwipeToDeleteItem(
    item: NotesItem,
    onSwipeToDelete: () -> Unit
) {
    var swipeOffset by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumePositionChange()
                    swipeOffset += dragAmount.x
                }
            }
    ) {
        // Left swipe background
        if (swipeOffset < 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color.Red),
                contentAlignment = Alignment.CenterStart
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        }

        // Right swipe background
        if (swipeOffset > 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color.Green),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        }

        // Item content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .offset { IntOffset(swipeOffset.roundToInt(), 0) }
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = item.title,
                fontSize = 20.sp,
                color = Color.Blue
            )
        }
    }
}
