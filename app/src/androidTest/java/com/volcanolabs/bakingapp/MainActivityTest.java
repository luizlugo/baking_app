package com.volcanolabs.bakingapp;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.volcanolabs.bakingapp.entities.Recipe;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityTestRule<RecipesActivity> mRecipesActivity =
            new ActivityTestRule<>(RecipesActivity.class);

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(mRecipesActivity.getActivity().getIdlingResource());
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(mRecipesActivity.getActivity().getIdlingResource());
    }

    @Test
    public void clickGridViewItem_OpenRecipeDetailsActivity() {
        onView(withId(R.id.rv_recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.app_bar)).check(matches(isDisplayed()));
        onView(withText("Nutella Pie")).check(matches(withParent(withId(R.id.app_bar))));
    }

    public static Matcher<Object> withName(final String name) {
        return new BoundedMatcher<Object, Recipe>(Recipe.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("with expectedName: " + name);
            }

            @Override
            protected boolean matchesSafely(Recipe recipe) {
                return recipe.getName().equals(name);
            }
        };
    }
}
