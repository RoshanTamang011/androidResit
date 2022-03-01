package com.dipesh.onlinegadgetsstore

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.dipesh.onlinegadgetsstore.ui.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class DisplayProfile {
    @get:Rule
    val testRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun displayProfile(){
        onView(ViewMatchers.withId(R.id.etLoginEmail))
            .perform(ViewActions.typeText("ram@gmail.com"))

        Thread.sleep(1000)

        onView((ViewMatchers.withId(R.id.etLoginPassword)))
            .perform(ViewActions.typeText("roshan"))

        Thread.sleep(1000)


        Espresso.closeSoftKeyboard()

        onView(ViewMatchers.withId(R.id.btnLogin))
            .perform(ViewActions.click())
        Thread.sleep(2000)

        onView(ViewMatchers.withId(R.id.icAccount))
            .perform(ViewActions.click())

        Thread.sleep(2000)

        onView(ViewMatchers.withId(R.id.tvProfileUserName)).check(
                ViewAssertions.matches(
                        ViewMatchers.isDisplayed()
                )
        )


    }
}