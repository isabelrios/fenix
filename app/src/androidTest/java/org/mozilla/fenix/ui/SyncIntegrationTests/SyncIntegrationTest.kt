/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.ui.SyncIntegrationTests

import android.os.SystemClock.sleep
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText

import org.junit.Rule
import org.junit.Test
import org.mozilla.fenix.helpers.HomeActivityTestRule
import org.mozilla.fenix.ui.robots.homeScreen
import br.com.concretesolutions.kappuccino.actions.ClickActions
import org.hamcrest.Matchers.allOf
import org.mozilla.fenix.helpers.click

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.mozilla.fenix.R

class SyncIntegrationTest {
    val mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @get:Rule
    val activityTestRule = HomeActivityTestRule()

    @Test
    fun checkHistoryFromDesktopTest() {
        signInFxSync()
        // Instead of waiting we will wait for the connected screen
        sleep(5000)
        tapReturnToPreviousApp()
        sleep(5000)
        homeScreen {
        }.openThreeDotMenu {}
        libraryButton()
        historyButton()
        sleep(5000)
        historyAfterSyncIsShown()
    }

    @Test
    fun checkBookmarkFromDesktopTest() {
        signInFxSync()
        // Instead of waiting we will wait for the connected screen
        sleep(5000)
        tapReturnToPreviousApp()

        sleep(5000)
        homeScreen {
        }.openThreeDotMenu {}
        libraryButton()
        bookmarkButton()
        bookmarkAfterSyncIsShown()
    }

    @Test
    fun checkBookmarkFromDeviceTest() {
        tapInToolBar()
        typeInToolBar()
        seeBookmark()
        mDevice.pressBack()
        signInFxSync()
    }

    @Test
    fun checkHistoryFromDeviceTest() {
        tapInToolBar()
        typeInToolBar()
        sleep(3000)
        mDevice.pressBack()
        signInFxSync()
    }

    // Useful functions for the tests
    fun typeEmail() {
        val emailInput = mDevice.findObject(UiSelector()
                .instance(0)
                .className(EditText::class.java))
        emailInput.waitForExists(1000)

        val emailAddress = javaClass.classLoader.getResource("email.txt").readText()
        emailInput.setText(emailAddress)
        // Use prod test account until stage can be set and so the generated account can be used
        // emailInput.setText("test-123456@restmail.net")
    }

    fun tapOnContinueButton() {
        val continueButton = mDevice.findObject(By.res("submit-btn"))
        continueButton.clickAndWait(Until.newWindow(), 50000)
    }

    fun typePassowrd() {
        mDevice.wait(Until.findObjects(By.text("Sign in")), 3000)
        val passwordInput = mDevice.findObject(UiSelector()
                .instance(0)
                .className(EditText::class.java))
        // passwordInput.waitForExists(10000)

        val passwordValue = javaClass.classLoader.getResource("password.txt").readText()
        passwordInput.setText(passwordValue)
        // Same for password
        // passwordInput.setText("testGet1")
    }

    fun tapOnSygIn() {
        val signInButton = mDevice.findObject(UiSelector()
                .instance(0)
                .className(Button::class.java))
        signInButton.waitForExists(10000)
        signInButton.click()
    }

    fun typeInToolBar() {
        val toolbarInput = mDevice.findObject(UiSelector()
                .instance(0)
                .className(EditText::class.java))
        // toolbarInput.setText("bbc.com" + "\r")
        awesomeBar().perform(replaceText("example.com"),
                pressImeActionButton())
    }

    fun historyAfterSyncIsShown() {
        // val historyEntry = mDevice.findObject(By.text("www.example.com"))
        // historyEntry.isClickable()
        mDevice.wait(Until.findObject(By.text("www.example.com")), 5000)
    }

    fun bookmarkAfterSyncIsShown() {
        val bookmarkyEntry = mDevice.findObject(By.text("Example Domain"))
        bookmarkyEntry.isClickable()
    }

    fun seeBookmark() {
        mDevice.wait(Until.findObjects(By.text("Bookmark")), 3000)
        val bookmarkButton = mDevice.findObject(By.text("Bookmark"))
        bookmarkButton.click()
    }

    fun tapReturnToPreviousApp() {
        // mDevice.wait(Until.findObjects(By.text("Connected")), 20000)

        mDevice.wait(Until.findObject(By.text("Settings")), 20000)

        val tapXButton = mDevice.findObject(UiSelector()
                .instance(0)
                .className(ImageButton::class.java))
        tapXButton.waitForExists(10000)
        tapXButton.click()
        tapXButton.click()
        mDevice.pressBack()
    }

    fun signInFxSync() {
        homeScreen {
        }.openThreeDotMenu {
            verifySettingsButton()
        }.openSettings {}
        settingsAccount()
        // clickSignIn()
        newScreenClickOnEmail()

        typeEmail()
        tapOnContinueButton()
        typePassowrd()
        tapOnSygIn()
    }
}

fun settingsAccount() = ClickActions.click { text("Turn on Sync") }
fun clickSignIn() = onView(allOf(withId(android.R.id.title), withText("Turn on Sync"))).click()
fun tapInToolBar() = ClickActions.click { text("Search or enter address") }
fun awesomeBar() = onView(withId(org.mozilla.fenix.R.id.mozac_browser_toolbar_edit_url_view))
fun libraryButton() = ClickActions.click { text("Your Library") }
fun historyButton() = ClickActions.click { text("History") }
fun bookmarkButton() = ClickActions.click { text("Bookmarks") }
fun newScreenClickOnEmail() = onView(withId(R.id.sign_in_email_button)).perform(click())
