package com.dipesh.onlinegadgetsstore

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.dipesh.onlinegadgetsstore.ui.LoginActivity
import org.junit.Rule
import org.junit.Test

@LargeTest
class LoginTest {
    @get:Rule
    val testRule =ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun testLoginUi(){
        onView(withId(R.id.etLoginEmail))
                .perform(typeText("ram@gmail.com"))

        Thread.sleep(1000)

        onView((withId(R.id.etLoginPassword)))
                .perform(typeText("roshan"))

        Thread.sleep(1000)


        closeSoftKeyboard()

        onView(withId(R.id.btnLogin))
                .perform(click())
        Thread.sleep(2000)

        onView(withId(R.id.mainActivity)).check(
            matches(
                isDisplayed()
            )
        )
    }


}