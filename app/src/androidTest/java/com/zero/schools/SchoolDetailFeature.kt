package com.zero.schools

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.hamcrest.CoreMatchers
import org.junit.Test

class SchoolDetailFeature : BaseUiTest() {
    @Test
    fun displayNameAndDetails() {
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.name),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(
                        ViewMatchers.withId(R.id.schoolRecyclerView),
                        1
                    )
                )
            )
        ).perform(ViewActions.click())
        assertDisplayed("School Name: LIBERATION DIPLOMA PLUS")
        assertDisplayed("Avg Score: 384.3")
    }
}