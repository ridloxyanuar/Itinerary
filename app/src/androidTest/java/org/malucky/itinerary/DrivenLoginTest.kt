package org.malucky.itinerary

import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedReader
import java.io.InputStreamReader


@LargeTest
@RunWith(AndroidJUnit4::class)
class DrivenLoginTest{

    @Rule @JvmField
    var mActivityTestRule: ActivityTestRule<SplashScreenActivity?>? = ActivityTestRule(SplashScreenActivity::class.java)

    @Test
    fun loginTest(){

        val inputStream = mActivityTestRule!!.activity!!.resources.openRawResource(R.raw.login)
        var ids: Array<String?>
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        try {
            var csv: String
            while (bufferedReader.readLine().also { csv = it } != null) {
                ids = csv.split(",").toTypedArray()
                try {
                    try {
                        Thread.sleep(3000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }


                    val appCompatEditText = onView(
                        Matchers.allOf(
                            withId(R.id.et_email_login),
                            childAtPosition(
                                childAtPosition(
                                    ViewMatchers.withClassName(Matchers.`is`("android.widget.ScrollView")),
                                    0
                                ),
                                0
                            )
                        )
                    )
                    appCompatEditText.perform(
                        ViewActions.scrollTo(),
                        ViewActions.replaceText(ids[0]),
                        ViewActions.closeSoftKeyboard()
                    )

                    val appCompatEditText2 = onView(
                        Matchers.allOf(
                            withId(R.id.et_pass_login),
                            childAtPosition(
                                childAtPosition(
                                    ViewMatchers.withClassName(Matchers.`is`("android.widget.ScrollView")),
                                    0
                                ),
                                1
                            )
                        )
                    )
                    appCompatEditText2.perform(
                        ViewActions.scrollTo(),
                        ViewActions.replaceText(ids[1]),
                        ViewActions.closeSoftKeyboard()
                    )

                    val appCompatEditText3 = onView(
                        Matchers.allOf(
                            withId(R.id.et_pass_login), withText("abcde123"),
                            childAtPosition(
                                childAtPosition(
                                    ViewMatchers.withClassName(Matchers.`is`("android.widget.ScrollView")),
                                    0
                                ),
                                1
                            )
                        )
                    )
                    appCompatEditText3.perform(ViewActions.pressImeActionButton())

                    SystemClock.sleep(7000)

                    val appCompatButton = onView(
                        Matchers.allOf(
                            withId(R.id.btn_masuk), withText("Login"),
                            childAtPosition(
                                childAtPosition(
                                    ViewMatchers.withClassName(Matchers.`is`("android.widget.ScrollView")),
                                    0
                                ),
                                2
                            )
                        )
                    )
                    appCompatButton.perform(ViewActions.scrollTo(), click())


                    SystemClock.sleep(5000)

                    val bottomNavigationItemView = onView(
                        Matchers.allOf(
                            withId(R.id.profileFragment),
                            ViewMatchers.withContentDescription("Profile"),
                            childAtPosition(
                                childAtPosition(
                                    withId(R.id.bottom_navigation),
                                    0
                                ),
                                2
                            ),
                            ViewMatchers.isDisplayed()
                        )
                    )
                    bottomNavigationItemView.perform(click())

                    SystemClock.sleep(7000)

                    val appCompatTextView = onView(
                        Matchers.allOf(
                            withId(R.id.txt_signOut), withText("Keluar"),
                            childAtPosition(
                                childAtPosition(
                                    withId(R.id.main_container),
                                    0
                                ),
                                4
                            ),
                            ViewMatchers.isDisplayed()
                        )
                    )
                    appCompatTextView.perform(click())

                    SystemClock.sleep(7000)

                    val dialogActionButton = onView(
                        Matchers.allOf(
                            withId(R.id.md_button_positive), withText("Yes"),
                            childAtPosition(
                                Matchers.allOf(
                                    withId(R.id.md_button_layout),
                                    childAtPosition(
                                        withId(R.id.md_root),
                                        2
                                    )
                                ),
                                1
                            ),
                            ViewMatchers.isDisplayed()
                        )
                    )
                    dialogActionButton.perform(click())

                    SystemClock.sleep(7000)
                } catch (e: Exception) {
                    Log.e("unknown", e.toString())
                }
            }
        } catch (e: Exception) {
            throw RuntimeException("Error in reading csv : $e")
        }
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