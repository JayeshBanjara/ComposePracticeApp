package com.example.demoappcompose.ui.dashboard.bottom_nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.demoappcompose.R

sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val iconId: Int
) {
    object Home : BottomNavigationScreens("home", R.string.home, R.drawable.ic_home)
    object AboutUs : BottomNavigationScreens("about_us", R.string.about_us, R.drawable.ic_about_us)
    object ContactUs :
        BottomNavigationScreens("contact_us", R.string.contact_us, R.drawable.ic_contact_us)

    object Feedback : BottomNavigationScreens("feedback", R.string.feedback, R.drawable.ic_feedback)
}
