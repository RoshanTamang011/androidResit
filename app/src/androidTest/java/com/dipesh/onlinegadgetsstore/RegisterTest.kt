package com.dipesh.onlinegadgetsstore

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.dipesh.onlinegadgetsstore.ui.RegisterActivity
import org.junit.Rule
import org.junit.Test

@LargeTest
class RegisterTest {
    @get:Rule
    val testRule= ActivityScenarioRule(RegisterActivity::class.java)

    @Test
    fun testRegisterUi(){
        onView(withId(R.id.etFirstName))
                .perform(typeText("Anil"))

        Thread.sleep(1000)

        onView(withId(R.id.etLastName))
                .perform(typeText("Subedi"))

        Thread.sleep(1000)


        onView(withId(R.id.etUserName))
                .perform(typeText("Anil Subedi"))

        Thread.sleep(1000)


        onView(withId(R.id.etEmail))
                .perform(typeText("anil@gmail.com"))

        Thread.sleep(1000)


        onView(withId(R.id.etPassword))
                .perform(typeText("appleapple"))

        Thread.sleep(1000)


        closeSoftKeyboard()


        onView(withId(R.id.etConfirmPassword))
                .perform(typeText("appleapple"))

        Thread.sleep(1000)


        closeSoftKeyboard()


        onView(withId(R.id.btnRegister))
                .perform(click())
        Thread.sleep(1000)

        onView(withId(R.id.mainActivity)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )

    }
}