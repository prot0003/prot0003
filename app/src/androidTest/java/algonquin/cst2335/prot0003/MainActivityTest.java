package algonquin.cst2335.prot0003;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.textView),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.textView),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("12345"), closeSoftKeyboard());

        pressBack();

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.textView), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView), withText("You shall not pass!"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("You shall not pass!")));
    }
    @Test
    public void testFindMissingUpperCase() {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.EditText));
        // type in password123#$*
        appCompatEditText.perform(replaceText("prot123!"));

        //find the button
        ViewInteraction materialButton = onView(withId(R.id.LoginButton));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView));
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * test the password whether includes the LowerCase Letter, if not includes the LowerCase letter,
     * textView displays "You shall not pass!"
     */
    @Test
    public void testFindMissingLowerCase() {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.EditText));
        // type in PASSWORD123#$*
        appCompatEditText.perform(replaceText("PROT123!"));

        // close the keyboard
        //appCompatEditText.perform(closeSoftKeyboard());
//        appCompatEditText.check(matches (withText("Prot123!)));

        //find the button
        ViewInteraction materialButton = onView(withId(R.id.LoginButton));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView));
        //check the text
        textView.check(matches(withText("You shall not pass!")));

    }

    /**
     * test the password whether includes the Number, if not includes the Number, testView
     * displays "You shall not pass!"
     */
    @Test
    public void testFindMissingNumber() {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.EditText));
        // type in password#$*
        appCompatEditText.perform(replaceText("Protasova!"));

        //find the button
        ViewInteraction materialButton = onView(withId(R.id.LoginButton));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView));
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * test the password whether includes the Special Symbol, if not includes the Special Symbol,
     * textView displays "You shall not pass!"
     */
    @Test
    public void testFindMissingSpecialSymbol() {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.EditText));
        // type in Password123
        appCompatEditText.perform(replaceText("Password123"));

        //find the button
        ViewInteraction materialButton = onView(withId(R.id.LoginButton));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView));
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }
    /**
     * test the password which is fit for all requirements, at least one UpperCase letter, one LowerCase
     * letter, one number and one Special symbol, textView displays "Your password is complex enough"
     */
    @Test
    public void testMeetRequirementPW() {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.EditText));
        // type in Password123#$*
        appCompatEditText.perform(replaceText("Prot123!"));

        //find the button
        ViewInteraction materialButton = onView(withId(R.id.LoginButton));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView));
        //check the text
        textView.check(matches(withText("Your password meets the requirements.")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
