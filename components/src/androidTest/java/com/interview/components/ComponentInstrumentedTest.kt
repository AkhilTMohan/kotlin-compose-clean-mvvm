package com.interview.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ComponentInstrumentedTest {

    @get:Rule
    val androidRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun checkFullScreenLoadingHaveDefaultText() {
        androidRule.setContent {
            FullScreenLoader()
        }
        val defaultMessage = androidRule.activity.getString(R.string.loading)
        androidRule.onNodeWithText(defaultMessage).assertExists()
    }
}