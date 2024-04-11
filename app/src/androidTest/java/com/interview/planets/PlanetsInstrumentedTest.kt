package com.interview.planets

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.interview.planets.data.models.Planet
import com.interview.planets.presentation.MainViewModel
import com.interview.planets.presentation.home.PlanetDetailsScreen
import com.interview.planets.presentation.home.components.PlanetCardItem
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class PlanetsInstrumentedTest {

    //TODO Not all cases covered..
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.interview.planets", appContext.packageName)
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val composeTestRuleAndroid = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun checkPlanetListItemHaveViewMoreButton() {
        composeTestRule.setContent {
            PlanetCardItem(Planet(1, 1))
        }
        // Find the button and perform a click action
        composeTestRule.onNodeWithText("View More >>", ignoreCase = true).assertExists()
    }

    @Test
    fun checkPlanetDetailsScreenHaveFilmsAndResidents() {
        composeTestRule.setContent {
            PlanetDetailsScreen(MainViewModel.BaseUIState(planetData = Planet(1, 1)), {}, {}, {})
        }
        // Find the button and perform a click action
        composeTestRule.onNodeWithText("Residents:", ignoreCase = true).assertExists()
        composeTestRule.onNodeWithText("Featured in Films:", ignoreCase = true).assertExists()
    }

}