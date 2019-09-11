package me.mfathy.task.features.restaurants

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import io.reactivex.Single
import konveyor.base.randomBuild
import me.mfathy.task.R
import me.mfathy.task.TestApplication
import me.mfathy.task.data.model.Restaurant
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class RestaurantsActivityTest {
    @Rule
    @JvmField
    val mActivityTestRule = ActivityTestRule(RestaurantsActivity::class.java, false, false)

    private lateinit var mIdlingResource: IdlingResource
    private val restaurantOne =
        randomBuild(Restaurant::class.java).copy(isFavorite = true, status = "open")
    private val restaurantTwo =
        randomBuild(Restaurant::class.java).copy(isFavorite = true, status = "closed")
    private val restaurants = listOf(restaurantOne, restaurantTwo)

    @Before
    fun registerIdlingResource() {

        stubGetRestaurants(restaurants)

        mActivityTestRule.launchActivity(null)

        mIdlingResource = mActivityTestRule.activity.getIdlingResource()

        IdlingRegistry.getInstance().register(mIdlingResource)
    }

    @After
    fun unRegisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(mIdlingResource)
    }


    @Test
    fun testShowRestaurantsList() {

        Espresso.onView(ViewMatchers.withId(R.id.recyclerView_List)).check(
            ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(restaurantOne.name)))
        )

        Espresso.onView(ViewMatchers.withId(R.id.recyclerView_List)).check(
            ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(restaurantTwo.name)))
        )

        Espresso.onView(ViewMatchers.withId(R.id.recyclerView_List)).check(
            ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(restaurantOne.status)))
        )

        Espresso.onView(ViewMatchers.withId(R.id.recyclerView_List)).check(
            ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(restaurantTwo.status)))
        )

    }

    private fun stubGetRestaurants(restaurants: List<Restaurant>) {
        Mockito.`when`(
            TestApplication.appComponent().repository().getRestaurants()
        ).thenReturn(Single.just(restaurants))
    }

}