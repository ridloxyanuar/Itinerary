package org.malucky.itinerary;


import android.os.SystemClock;
import android.util.Log;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DrivenLogin {

    @Rule
    public ActivityTestRule<SplashScreenActivity> mActivityTestRule = new ActivityTestRule<>(SplashScreenActivity.class);

    @Test
    public void loginAkunTest() {
        InputStream inputStream = mActivityTestRule.getActivity().getResources().openRawResource(R.raw.login);
        String[] ids;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csv;
            while ((csv = bufferedReader.readLine()) != null) {
                ids = csv.split(",");
                try {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Espresso.onView(ViewMatchers.withId(R.id.et_email_login)).perform(ViewActions.clearText());
                    Espresso.onView(ViewMatchers.withId(R.id.et_pass_login)).perform(ViewActions.clearText());

                    Espresso.onView(ViewMatchers.withId(R.id.et_email_login)).perform(ViewActions.replaceText(ids[0]),
                            ViewActions.closeSoftKeyboard());
                    Espresso.onView(ViewMatchers.withId(R.id.et_pass_login)).perform(ViewActions.replaceText(ids[1]),
                            ViewActions.closeSoftKeyboard());

                    Espresso.onView(withId(R.id.btn_masuk)).perform(click());
                    SystemClock.sleep(3000);

                    Espresso.onView(ViewMatchers.withId(R.id.profileFragment)).perform(ViewActions.click());
                    SystemClock.sleep(3000);

                    Espresso.onView(ViewMatchers.withId(R.id.txt_signOut)).perform(ViewActions.click());
                    SystemClock.sleep(3000);

                    Espresso.onView(ViewMatchers.withText("Yes")).perform(ViewActions.click());
                    SystemClock.sleep(3000);


                } catch (Exception e) {
                    Log.e("unknown", e.toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error in reading csv : "+e);
        }


        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html


    }
}
