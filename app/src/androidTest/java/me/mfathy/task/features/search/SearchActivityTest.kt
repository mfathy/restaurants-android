package me.mfathy.task.features.search

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import me.mfathy.task.R
import me.mfathy.task.TestApplication
import me.mfathy.task.domain.model.Genre
import me.mfathy.task.factory.MoviesDataFactory
import me.mfathy.task.test.ToastMatcher
import io.reactivex.Flowable
import io.reactivex.Single
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
@LargeTest
@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
class SearchActivityTest {

    private val interval: Long = 1000

    @Rule
    @JvmField
    val mActivityTestRule = ActivityTestRule(SearchActivity::class.java, false, false)
    private val genre = Genre("Action", 2)


    @Test
    fun testSearchForGenreReturnInputError() {

        mActivityTestRule.launchActivity(null)

        val button = onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button_search),
                isDisplayed(),
                withText("Search")
            )
        )
        button.perform(click())

        Thread.sleep(interval)

        onView(withText(R.string.error_required_field)).inRoot(ToastMatcher())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun testSearchForGenreReturnToastError() {

        stubGetGenres(genre)

        mActivityTestRule.launchActivity(null)

        Thread.sleep(interval)

        val editText = onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.editText_search),
                isDisplayed()
            )
        )

        editText.perform(ViewActions.replaceText("fff"), ViewActions.closeSoftKeyboard())

        Thread.sleep(interval)

        val button = onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button_search),
                isDisplayed(),
                withText("Search")
            )
        )
        button.perform(click())

        Thread.sleep(interval)

        editText.check(ViewAssertions.matches(withText("")))

        onView(withText(R.string.error_genre_not_found)).inRoot(ToastMatcher())
            .check(ViewAssertions.matches(isDisplayed()))

    }

    @Test
    fun testSearchForGenreReturnSuccess() {

        stubGetGenres(genre)
        stubGetMovies()

        mActivityTestRule.launchActivity(null)

        Thread.sleep(interval)

        val editText = onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.editText_search),
                isDisplayed()
            )
        )

        editText.perform(ViewActions.replaceText("action"), ViewActions.closeSoftKeyboard())

        Thread.sleep(interval)

        val button = onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button_search),
                isDisplayed(),
                withText("Search")
            )
        )
        button.perform(click())

        Thread.sleep(interval)

        //  Check search text.
        editText.check(ViewAssertions.matches(withText(genre.name)))

        //  Check list is displayed.
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView_List)).check(
            ViewAssertions.matches(isDisplayed())
        )
    }

    @Test
    fun testSearchForGenreReturnNoMovies() {

        stubGetGenres(genre)
        stubGetEmptyMovies()

        mActivityTestRule.launchActivity(null)

        Thread.sleep(interval)

        val editText = onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.editText_search),
                isDisplayed()
            )
        )

        editText.perform(ViewActions.replaceText("action"), ViewActions.closeSoftKeyboard())

        Thread.sleep(interval)

        val button = onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button_search),
                isDisplayed(),
                withText("Search")
            )
        )
        button.perform(click())

        Thread.sleep(interval)

        //  Check search text.
        editText.check(ViewAssertions.matches(withText(genre.name)))

        //  Check error is displayed.
        val editTextError = onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.textView_List_callToAction)
            )
        )

        editTextError.check(ViewAssertions.matches(isDisplayed()))
        editTextError.check(ViewAssertions.matches(withText(R.string.error_no_restaurants)))
    }

    private fun stubGetGenres(genre: Genre) {
        Mockito.`when`(TestApplication.appComponent().repository().getGenres())
            .thenReturn(Single.just(listOf(genre, genre)))
    }

    private fun stubGetMovies() {
        Mockito.`when`(TestApplication.appComponent().repository().getMovies(anyString(), anyInt(), anyInt()))
            .thenReturn(Flowable.just(listOf(MoviesDataFactory.makeMovie())))
    }

    private fun stubGetEmptyMovies() {
        Mockito.`when`(TestApplication.appComponent().repository().getMovies(anyString(), anyInt(), anyInt()))
            .thenReturn(Flowable.just(listOf()))
    }
}