package me.mfathy.task.interactors.favorites

import io.reactivex.Completable
import konveyor.base.randomBuild
import me.mfathy.task.any
import me.mfathy.task.base.BaseUnitTest
import me.mfathy.task.data.model.Restaurant
import me.mfathy.task.data.repository.RestaurantsRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
@RunWith(MockitoJUnitRunner::class)
class BookmarkRestaurantTest : BaseUnitTest() {

    @Mock
    lateinit var mockRepository: RestaurantsRepository

    private lateinit var bookmarkRestaurant: BookmarkRestaurant

    override fun postSetup() {
        bookmarkRestaurant = BookmarkRestaurant(mockRepository)
    }

    @Test
    fun testBookmarkRestaurant_Completes() {

        val entity = randomBuild(Restaurant::class.java)

        stubRepositoryBookmark()

        bookmarkRestaurant
            .buildUseCaseCompletable(BookmarkRestaurant.Params(entity))
            .test()
            .assertComplete()
    }

    @Test
    fun testBookmarkRestaurant_CallRepository() {

        val entity = randomBuild(Restaurant::class.java)

        stubRepositoryBookmark()

        bookmarkRestaurant.buildUseCaseCompletable(BookmarkRestaurant.Params(entity))
            .test()

        verify(mockRepository).bookmarkRestaurant(any())
    }

    private fun stubRepositoryBookmark() {
        `when`(mockRepository.bookmarkRestaurant(any())).thenReturn(Completable.complete())
    }
}