package com.example.demoappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.demoappcompose.data.PreferencesManager
import com.example.demoappcompose.ui.App
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var prefManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var isLoggedIn: Int? = 0
        var userId: String? = null
        var profilePicUrl: String? = null

        lifecycleScope.launch {
            withContext(lifecycleScope.coroutineContext) {
                isLoggedIn = prefManager.getLoggedIn.first()

                isLoggedIn.let {
                    if (isLoggedIn == 1) {
                        userId = prefManager.getUserId.first()
                        profilePicUrl = prefManager.getUserProfileImage.first()
                        setContent {
                            App(
                                isLoggedIn = true,
                                userId = userId,
                                profilePicUrl = profilePicUrl,
                                prefManager = prefManager
                            )
                        }
                    } else {
                        setContent {
                            App(
                                isLoggedIn = false,
                                userId = userId,
                                profilePicUrl = profilePicUrl,
                                prefManager = prefManager
                            )
                        }
                    }
                }
            }
        }
    }
}