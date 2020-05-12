package org.malucky.itinerary


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SplashScreenActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashScreenActivity::class.java)

    @Test
    fun splashScreenActivityTest() {

        Thread.sleep(7000)

        val appCompatEditText = onView(
            allOf(
                withId(R.id.et_email_login),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    0
                )
            )
        )
        appCompatEditText.perform(
            scrollTo(),
            replaceText("tespertama@gmail.com"),
            closeSoftKeyboard()
        )

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.et_pass_login),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    1
                )
            )
        )
        appCompatEditText2.perform(scrollTo(), replaceText("abcde123"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.et_pass_login), withText("abcde123"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    1
                )
            )
        )
        appCompatEditText3.perform(pressImeActionButton())

        val appCompatButton = onView(
            allOf(
                withId(R.id.btn_masuk), withText("Login"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    2
                )
            )
        )
        appCompatButton.perform(scrollTo(), click())


        Thread.sleep(7000)

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.profileFragment), withContentDescription("Profile"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val appCompatTextView = onView(
            allOf(
                withId(R.id.txt_signOut), withText("Keluar"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_container),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())

        val dialogActionButton = onView(
            allOf(
                withId(R.id.md_button_positive), withText("Yes"),
                childAtPosition(
                    allOf(
                        withId(R.id.md_button_layout),
                        childAtPosition(
                            withId(R.id.md_root),
                            2
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        dialogActionButton.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
