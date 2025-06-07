package com.example.cicd

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedPackageName = "com.example.cicd"
        assert(appContext.packageName.startsWith(expectedPackageName))
    }

    @Test
    fun checkMainTitle_exists() {
        // Kiểm tra tiêu đề chính có tồn tại
        composeTestRule.onNodeWithText("CI/CD Demo App").assertIsDisplayed()
    }

    @Test
    fun checkCounterInitialState() {
        // Kiểm tra trạng thái ban đầu của bộ đếm
        composeTestRule.onNodeWithText("Số lần nhấn: 0").assertIsDisplayed()
    }

    @Test
    fun checkButtonIncreasesCounter() {
        // Kiểm tra nút tăng số hoạt động đúng
        composeTestRule.onNodeWithText("Tăng số").performClick()
        composeTestRule.onNodeWithText("Số lần nhấn: 1").assertIsDisplayed()

        // Nhấn thêm một lần nữa
        composeTestRule.onNodeWithText("Tăng số").performClick()
        composeTestRule.onNodeWithText("Số lần nhấn: 2").assertIsDisplayed()
    }

    @Test
    fun checkVersionInfoCardExists() {
        // Kiểm tra card thông tin phiên bản tồn tại
        composeTestRule.onNodeWithText("Thông tin phiên bản").assertIsDisplayed()
    }
}

