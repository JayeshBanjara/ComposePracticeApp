package com.example.demoappcompose.ui.dashboard.home.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.NoRippleTheme
import com.example.demoappcompose.ui.theme.WhiteText

@Composable
fun MenusLayout(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
    onItemClick: (menuTitle: String) -> Unit
) {
    Box(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .background(color = WhiteText, shape = RoundedCornerShape(10.dp))
    ) {

        val menus = listOf(
            stringResource(R.string.profile),
            stringResource(R.string.my_subscription),
            stringResource(R.string.my_paper_history),
            stringResource(R.string.register_to_purchase_the_book),
            stringResource(R.string.rate_app),
            stringResource(R.string.logout)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(10.dp),
        ) {
            itemsIndexed(menus) { index, menu ->
                MenuItem(menuTitle = menu, index) { menuTitle ->
                    onItemClick(menuTitle)
                }
            }
        }
    }
}

@Composable
fun MenuItem(menuTitle: String, index: Int, onItemClick: (menuTitle: String) -> Unit) {
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp)
                .clickable { onItemClick(menuTitle) }
        ) {
            Image(
                painter = painterResource(id = getMenuDrawable(index = index)),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            VerticalSpacer(size = 5)
            Text(
                text = menuTitle,
                style = TextStyle(
                    color = Blue,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = FontFamily(Font(R.font.quicksand_semi_bold)),
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

fun getMenuDrawable(index: Int): Int {
    Log.e("TAG", "getMenuDrawable: $index", )
    var menuDrawable: Int = R.drawable.ic_menu
    when (index) {
        0 -> menuDrawable = R.drawable.ic_profile_menu
        1 -> menuDrawable = R.drawable.ic_subscription
        2 -> menuDrawable = R.drawable.ic_paper_history
        3 -> menuDrawable = R.drawable.ic_purchase_book
        4 -> menuDrawable = R.drawable.ic_rate_us_menu
        5 -> menuDrawable = R.drawable.ic_logout_menu
        else -> R.drawable.ic_menu
    }
    return menuDrawable
}