package com.example.demoappcompose.ui.payment

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Approval
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RoundaboutRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupList() {

    val notesList = remember {
        mutableStateListOf<NotesItem>()
    }

    for (i in 1..10) {
        notesList.add(NotesItem(id = i, title = "Title $i"))
    }

    LazyColumn {

        items(notesList, { notesList: NotesItem -> notesList.id }) { item ->
            val dismissState = rememberDismissState()
            val swipeDirection = remember { mutableStateOf(0f) }

            if (dismissState.isDismissed(DismissDirection.EndToStart)) {

                notesList.remove(item)
            } else if (dismissState.isDismissed(DismissDirection.StartToEnd)) {

                notesList.remove(item)
            }
            SwipeToDismiss(
                state = dismissState,
                modifier = Modifier
                    .padding(vertical = Dp(1f)),
                directions = setOf(
                    DismissDirection.EndToStart,
                    DismissDirection.StartToEnd
                ),
                background = {

                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> Color.White
                                else -> Color.Blue
                            }
                        )
                        val alignment = Alignment.CenterEnd
                        val icon = Icons.Default.RoundaboutRight

                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                        )

                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = Dp(20f)),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                icon,
                                contentDescription = "Approve Icon",
                                modifier = Modifier.scale(scale),
                                tint = Color.White
                            )
                        }

                    } else if(dismissState.isDismissed(DismissDirection.StartToEnd)) {
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> Color.White
                                else -> Color.Red
                            }
                        )
                        val alignment = Alignment.CenterEnd
                        val icon = Icons.Default.Close

                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                        )

                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = Dp(20f)),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                icon,
                                contentDescription = "Reject Icon",
                                modifier = Modifier.scale(scale),
                                tint = Color.White
                            )
                        }

                    }
                },
                dismissContent = {

                    Card(
                        elevation = CardDefaults.cardElevation(
                            animateDpAsState(
                                if (dismissState.dismissDirection != null) 4.dp else 0.dp
                            ).value
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dp(50f))
                            .align(alignment = Alignment.CenterVertically)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = item.title,
                                modifier = Modifier.wrapContentSize(),
                                fontSize = TextUnit(value = 16f, type = TextUnitType.Sp)
                            )

                        }
                    }
                }
            )


        }
    }
}