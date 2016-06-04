package com.example.facundo.recorridaszotp.NewTests;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by gonzalo on 02/06/16.
 */
@RunWith(AndroidJUnit4.class)
public class LoginTest {

   @Rule
   public ActivityTestRule <MainActivity> mActivityRule =
           new ActivityTestRule<MainActivity>(MainActivity.class);

        @Test
        public void LoginFail(){
            onView(ViewMatchers.withId(R.id.ETEmail)).perform(typeText("admin@gmail.com"));
            onView(withId(R.id.ETPassword)).perform(typeText("1234567890"));
            onView(withId(R.id.bLogin)).perform(click());
            onView(ViewMatchers.withId(R.id.ETEmail)).check(matches(isDisplayed()));
        }

        @Test
        public void LoginCorrect(){
            onView(ViewMatchers.withId(R.id.ETEmail)).perform(typeText("admin@gmail.com"));
            onView(withId(R.id.ETPassword)).perform(typeText("123456789"));
            onView(withId(R.id.bLogin)).perform(click());
            onView(ViewMatchers.withId(R.id.ETEmail)).check(doesNotExist());
        }
}
