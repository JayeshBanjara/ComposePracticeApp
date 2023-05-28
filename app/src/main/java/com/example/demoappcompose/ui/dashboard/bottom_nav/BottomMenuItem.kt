package com.example.demoappcompose.ui.dashboard.bottom_nav

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.TitleColor

@Composable
fun BottomMenuItem(
    item: BottomNavigationScreens,
    isSelected: Boolean = false,
    onItemClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable {
            onItemClick()
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .padding(5.dp)
        ) {
            Icon(
                painter = painterResource(id = item.iconId),
                contentDescription = stringResource(id = item.resourceId),
                tint = if (isSelected) Blue else TitleColor,
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = stringResource(id = item.resourceId),
            style = TextStyle(
                color = if (isSelected) Blue else TitleColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.W600,
                fontFamily = FontFamily(Font(R.font.quicksand_semi_bold))
            )
        )
    }
}