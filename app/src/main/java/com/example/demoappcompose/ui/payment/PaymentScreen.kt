package com.example.demoappcompose.ui.payment

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.components.ScreenBackground
import com.example.demoappcompose.ui.screenPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen() {
    Scaffold(topBar = {
        CustomTopAppBar(
            title = "Payment",
            showBack = true,
            onBackClick = {
                //  navController.popBackStack()
            }
        )
    }) { innerPadding ->

        val notesList = remember {
            mutableStateListOf<NotesItem>()
        }

        for (i in 1..10) {
            notesList.add(NotesItem(id = i, title = "Title $i", "Pending"))
        }

        val context = LocalContext.current

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            ScreenBackground()

            Surface(
                modifier = Modifier
                    .padding(
                        start = screenPadding(),
                        top = innerPadding.calculateTopPadding(),
                        end = screenPadding(),
                        bottom = screenPadding()
                    )
            ) {
                LazyColumn(
                    content = {
                        item { Spacer(modifier = Modifier.height(10.dp)) }
                        items(
                            items = notesList,
                            key = { toDoItem -> toDoItem.id },
                            itemContent = { item ->

                                val currentItem by rememberUpdatedState(newValue = item)
                                val dismissState = rememberDismissState(
                                    confirmValueChange = {
                                        when (it) {
                                            DismissValue.DismissedToEnd -> {
                                                //Do something when swipe Start to End
                                                currentItem.status = "Rejected"
                                                notesList.add(NotesItem(0, "", ""))
                                                notesList.remove(NotesItem(0, "", ""))
                                                Toast.makeText(
                                                    context,
                                                    "Rejected",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                true
                                            }

                                            DismissValue.DismissedToStart -> {
                                                //Do something when swipe End to Start
                                                currentItem.status = "Approved"
                                                Toast.makeText(
                                                    context,
                                                    "Approved",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                true
                                            }

                                            else -> {
                                                false
                                            }
                                        }
                                    }
                                )

                                SwipeToDismiss(
                                    state = dismissState,
                                    background = {
                                        SwipeBackground(dismissState = dismissState)
                                    },
                                    dismissContent = {
                                        MyPaymentItem(item = item)
                                    }
                                )

                            }
                        )
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBackground(dismissState: DismissState) {
    val direction = dismissState.dismissDirection ?: return

    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.Default -> Color.LightGray
            DismissValue.DismissedToEnd -> Color.Red
            DismissValue.DismissedToStart -> Color.Green
        }
    )
    val alignment = when (direction) {
        DismissDirection.StartToEnd -> Alignment.CenterStart
        DismissDirection.EndToStart -> Alignment.CenterEnd
    }
    val icon = when (direction) {
        DismissDirection.StartToEnd -> Icons.Default.Cancel
        DismissDirection.EndToStart -> Icons.Default.Done
    }
    val scale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 20.dp),
        contentAlignment = alignment
    ) {
        Icon(
            icon,
            contentDescription = "Localized description",
            modifier = Modifier.scale(scale)
        )
    }
}