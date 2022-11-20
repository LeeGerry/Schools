package com.zero.schools

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.zero.schools.utils.EspressoIdlingResource
import org.hamcrest.Matchers
import org.junit.Test

class SchoolListFeature : BaseUiTest() {

    @Test
    fun displayScreenTitle() {
        val appName =
            InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.app_name)
        assertDisplayed(appName)
    }

    @Test
    fun displayLoading() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        assertDisplayed(R.id.loading)
    }

    @Test
    fun hideLoading() {
        assertNotDisplayed(R.id.loading)
    }

    @Test
    fun displaySchoolList() {
        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.schoolRecyclerView, 440)
    }

    @Test
    fun navigateToDetailScreen() {
        Espresso.onView(
            Matchers.allOf(
                withId(R.id.name),
                ViewMatchers.isDescendantOfA(nthChildOf(withId(R.id.schoolRecyclerView), 1))
            )
        )
            .perform(ViewActions.click())
        assertDisplayed(R.id.schoolDetailRoot)
    }
}